/**
 * Test: Visualización del Detalle de Cuenta(UI)

 * Este test automatiza la validación del detalle de una cuenta bancaria en la aplicación web Parabank.

 * Flujo validado:
 *  1. Acceder a la página principal del sistema.
 *  2. Iniciar sesión con credenciales válidas.
 *  3. Ingresar a la sección “Accounts Overview”.
 *  4. Verificar que el mensaje de balance se muestre correctamente.
 *  5. Seleccionar una cuenta específica y comprobar que se despliega el detalle.
 *  6. Filtrar movimientos por período y tipo de actividad.

 * El reporte de ejecución se genera con ExtentReports en: target/REPORTES-DETALLECUENTA.html

 * Este test forma parte de un conjunto de pruebas UI para demostrar automatización de flujos bancarios dentro de un entorno de práctica.
 */

package com.tests;

import com.pages.DetallePage;
import com.pages.NewAccountPage;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.extentreports.ExtentFactory;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AccountDetailsTest {

    private WebDriver driver;
    private WebDriverWait wait;

    // Configuración del reporte ExtentReports
    static ExtentSparkReporter info = new ExtentSparkReporter("target/REPORTES-DETALLECUENTA.html");
    static ExtentReports extent;

    @BeforeAll
    public static void crearReporte() {
        // Inicializa el reporte Extent antes de ejecutar los tests
        extent = ExtentFactory.getInstance();
        extent.attachReporter(info);
    }

    @BeforeEach
    public void setUp() {
        // Configuración inicial del navegador y del tiempo de espera
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofMillis(2000));
    }

    @Test
    void DetalleDeCuentaExitosa() throws InterruptedException {
        // Crea una entrada en el reporte para este caso de prueba
        ExtentTest test = extent.createTest("Prueba de detalle de cuenta Exitosa");
        test.log(Status.INFO, "Comienza el Test");

        // Instancia del Page Object correspondiente
        DetallePage detallePage = new DetallePage(driver, wait);
        detallePage.setup();
        detallePage.open("https://parabank.parasoft.com/parabank/index.htm");
        test.log(Status.PASS, "Se ha ingresado exitosamente a la página.");

        // Inicio de sesión
        detallePage.escribirUsuario("Prueba2525");
        detallePage.escribirPassword("Prueba2525");
        detallePage.clickBotonLogin();

        // Accede al resumen de cuentas
        detallePage.clickAccountsOverview();

        // Validación del mensaje de balance
        assertTrue(detallePage.obtenerMensajeBalance()
                .contains("*Balance includes deposits that may be subject to holds"));
        test.log(Status.PASS, "Se visualizó el detalle de balance de cuentas exitosamente.");

        // Acceso al número de cuenta
        detallePage.clickAccountNumber();

        // Validación del título en la página de detalle
        assertTrue(detallePage.obtenerMensajeBalance().contains("Account Details"));
        test.log(Status.PASS, "Se visualizó el título 'Account Details' exitosamente.");

        // Selección de filtros de actividad
        detallePage.seleccionarPeriodoActividad();
        detallePage.seleccionarTipoActividad();
        detallePage.clickSubmit();

        test.log(Status.PASS, "Finalizó el test de detalle de cuenta exitosamente.");
    }

    @AfterEach
    public void cerrar() {
        // Cierra el navegador al finalizar cada test
        NewAccountPage searchPage = new NewAccountPage(driver, wait);
        searchPage.close();
    }

    @AfterAll
    public static void reporte() {
        // Genera el reporte HTML con los resultados de ejecución
        extent.flush();
    }
}

