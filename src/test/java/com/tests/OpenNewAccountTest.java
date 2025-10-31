/**
 * Test: Apertura de Nueva Cuenta(UI)

 * Este test automatiza el flujo de apertura de una nueva cuenta bancaria en la aplicación web Parabank.

 * Flujo validado:
 *  1. Acceder a la página principal del banco.
 *  2. Iniciar sesión con credenciales válidas.
 *  3. Ingresar a la sección “Open New Account”.
 *  4. Seleccionar el tipo de cuenta y confirmar la creación.
 *  5. Verificar el mensaje de éxito de apertura.

 * El reporte de ejecución se genera automáticamente con ExtentReports en la ruta: target/REPORTES-NUEVACUENTA.html
 */

package com.tests;

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

public class OpenNewAccountTest {

    private WebDriver driver;
    private WebDriverWait wait;

    // Configuración del reporte ExtentReports
    static ExtentSparkReporter info = new ExtentSparkReporter("target/REPORTES-NUEVACUENTA.html");
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
    void AperturadecuentaExitosa() throws InterruptedException {
        // Creación del reporte individual para este test
        ExtentTest test = extent.createTest("Prueba de apertura de cuenta Exitosa");
        test.log(Status.INFO, "Comienza el Test");

        // Instancia de la página que contiene los elementos y acciones
        NewAccountPage accountPage = new NewAccountPage(driver, wait);
        accountPage.setup();

        // Navega a la URL base del sistema
        accountPage.open("https://parabank.parasoft.com/parabank/index.htm");
        test.log(Status.PASS, "Se ha ingresado exitosamente a la página.");

        // Inicio de sesión con credenciales válidas
            accountPage.escribirUsuario("Prueba2525");
        accountPage.escribirPassword("Prueba2525");
        accountPage.clickBotonLogin();

        // Acceso a la opción para abrir una nueva cuenta
        accountPage.clickOpenNewAccount();
        accountPage.seleccionarTipoCuenta();

        // Envía el formulario de creación
        accountPage.clickSubmit();

        // Validación del mensaje de éxito en pantalla
        assertTrue(accountPage.obtenerMensajeExitoso()
                .contains("Congratulations, your account is now open."));
        test.log(Status.PASS, "Se realizó la apertura de la cuenta exitosamente.");
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
