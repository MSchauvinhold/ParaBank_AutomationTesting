/**
 * Test: Transferencia de Fondos (UI)

 * Este test automatiza el flujo de transferencia de fondos entre cuentas dentro de la aplicación web Parabank.

 * Flujo validado:
 *  1. Acceder a la página principal del banco.
 *  2. Iniciar sesión con credenciales válidas.
 *  3. Ingresar a la sección “Transfer Funds”.
 *  4. Completar el formulario de transferencia indicando el monto y la cuenta destino.
 *  5. Confirmar la transferencia y validar el mensaje de éxito mostrado.

 * El reporte de ejecución se genera automáticamente con ExtentReports en la ruta: target/REPORTES-TRANSFER.html
 */

package com.tests;

import com.pages.NewAccountPage;
import com.pages.TransferPage;
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

public class FundsTransferTest {

    private WebDriver driver;
    private WebDriverWait wait;

    // Configuración del reporte ExtentReports
    static ExtentSparkReporter info = new ExtentSparkReporter("target/REPORTES-TRANSFER.html");
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
    void TransferenciaExitosa() throws InterruptedException {
        // Creación del reporte individual para este test
        ExtentTest test = extent.createTest("Prueba de transferencia Exitosa");
        test.log(Status.INFO, "Comienza el Test");

        // Instancia de la página que contiene los elementos y acciones
        TransferPage transferPage = new TransferPage(driver, wait);
        transferPage.setup();

        // Navega a la URL base del sistema
        transferPage.open("https://parabank.parasoft.com/parabank/index.htm");
        test.log(Status.PASS, "Se ha ingresado exitosamente a la página principal.");

        // Inicio de sesión con credenciales válidas
        transferPage.escribirUsuario("MateSc");
        transferPage.escribirPassword("MateSc123");
        transferPage.clickBotonLogin();

        // Acceso a la opción “Transfer Funds”
        transferPage.clickTransferFunds();

        // Completa los campos de la transferencia
        transferPage.escribirCantidad("1000");
        transferPage.seleccionarDestinatario();

        // Envía la solicitud de transferencia
        transferPage.clickSubmit();

        // Validación del mensaje de éxito en pantalla
        assertTrue(transferPage.obtenerMensajeExitoso().contains("Transfer Complete!"));
        test.log(Status.PASS, "Se realizó la transferencia exitosamente.");
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
