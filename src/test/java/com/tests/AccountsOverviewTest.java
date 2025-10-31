/**
 * Test: Visualización del Resumen de Cuentas (UI)

 * Este test automatiza el flujo de validación del resumen de cuentas dentro de la aplicación web Parabank.

 * Flujo validado:
 *  1. Acceder a la página principal del banco.
 *  2. Iniciar sesión con credenciales válidas.
 *  3. Ingresar a la sección “Accounts Overview”.
 *  4. Verificar la visualización correcta del mensaje de balance en la página de resumen.

 * El reporte de ejecución se genera automáticamente con ExtentReports en la ruta:
 * target/REPORTES-RESUMEN.html
 */

package com.tests;

import com.pages.NewAccountPage;
import com.pages.ResumenPage;
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

public class AccountsOverviewTest {

    private WebDriver driver;
    private WebDriverWait wait;

    // Configuración del reporte ExtentReports
    static ExtentSparkReporter info = new ExtentSparkReporter("target/REPORTES-RESUMEN.html");
    static ExtentReports extent;

    @BeforeAll
    public static void crearReporte() {
        // Inicializa el reporte antes de ejecutar cualquier test
        extent = ExtentFactory.getInstance();
        extent.attachReporter(info);
    }

    @BeforeEach
    public void setUp() {
        // Configuración inicial del navegador antes de cada prueba
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofMillis(2000));
    }

    @Test
    void VisualizacionResumenExitosa() throws InterruptedException {
        // Creación del reporte individual para este test
        ExtentTest test = extent.createTest("Prueba de visualización del resumen de cuenta Exitosa");
        test.log(Status.INFO, "Comienza el Test");

        // Instancia de la página que contiene los elementos y acciones
        ResumenPage resumenPage = new ResumenPage(driver, wait);
        resumenPage.setup();

        // Navega a la URL base del sistema
        resumenPage.open("https://parabank.parasoft.com/parabank/index.htm");
        test.log(Status.PASS, "Se ha ingresado exitosamente a la página principal.");

        // Inicio de sesión con credenciales válidas
        resumenPage.escribirUsuario("Prueba2525");
        resumenPage.escribirPassword("Prueba2525");
        resumenPage.clickBotonLogin();

        // Acceso a la sección “Accounts Overview”
        resumenPage.clickAccountsOverview();

        // Validación del mensaje de balance mostrado
        assertTrue(resumenPage.obtenerMensajeBalance()
                .contains("*Balance includes deposits that may be subject to holds"));
        test.log(Status.PASS, "Se visualizó correctamente el resumen de cuentas y su balance.");
    }

    @AfterEach
    public void cerrar() {
        // Cierra el navegador después de cada test
        NewAccountPage searchPage = new NewAccountPage(driver, wait);
        searchPage.close();
    }

    @AfterAll
    public static void reporte() {
        // Genera y guarda el reporte final una vez completadas todas las pruebas
        extent.flush();
    }
}

