/**
 * Test: Registro de nuevo usuario (UI)

 * Este test automatiza el flujo de registro de un nuevo usuario en la aplicación web Parabank.

 * Flujo validado:
 *  1. Ingresar al sitio principal de Parabank.
 *  2. Acceder a la sección “Register”.
 *  3. Completar todos los campos del formulario de registro con datos válidos.
 *  4. Enviar el formulario.
 *  5. Verificar que el mensaje de confirmación de registro sea mostrado correctamente.

 * El reporte de ejecución se genera con ExtentReports en: target/REPORTES-REGISTRO.html

 * Este test forma parte del conjunto de pruebas UI que validan las funcionalidades básicas del flujo bancario dentro del entorno de práctica Parabank.
 */

package com.tests;

import com.pages.RegisterPage;
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

public class RegisterTest {

    private WebDriver driver;
    private WebDriverWait wait;

    // Configuración del reporte ExtentReports
    static ExtentSparkReporter info = new ExtentSparkReporter("target/REPORTES-REGISTRO.html");
    static ExtentReports extent;

    @BeforeAll
    public static void crearReporte() {
        // Se configura el reporte una única vez antes de ejecutar todas las pruebas
        extent = ExtentFactory.getInstance();
        extent.attachReporter(info);
    }

    @BeforeEach
    public void setUp() {
        // Inicializa el navegador y configura los tiempos de espera
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofMillis(5000));
    }

    @Test
    void RegistroExitoso() throws InterruptedException {
        // Crea una nueva entrada en el reporte
        ExtentTest test = extent.createTest("Prueba de Registro Exitoso");
        test.log(Status.INFO, "Comienza el Test");

        // Instancia del Page Object correspondiente
        RegisterPage registerPage = new RegisterPage(driver, wait);
        registerPage.setup();
        registerPage.open("https://parabank.parasoft.com/parabank/register.htm");
        test.log(Status.PASS, "Se ha ingresado exitosamente a la página principal.");

        // Navega a la sección de registro
        registerPage.clickRegister();
        test.log(Status.PASS, "Se accedió correctamente a la página de registro.");

        // Completa el formulario de registro
        registerPage.escribirFirstName("Prueba2525");
        registerPage.escribirLastName("Prueba2525");
        registerPage.escribirAddress("mateo@Prueba2525.com");
        registerPage.escribirCity("UruguaianaTest");
        registerPage.escribirState("BrasilTest");
        registerPage.escribirZipCode("2012Test");
        registerPage.escribirTelephone("523234");
        registerPage.escribirSsn("5421");
        registerPage.escribirUsername("Prueba2525");
        registerPage.escribirPassword("Prueba2525");
        registerPage.escribirConfirmacionPassword("Prueba2525");

        // Envía el formulario
        registerPage.clickSubmit();

        // Verificación de mensaje exitoso
        assertTrue(registerPage.obtenerMensajeRegistrado()
                .equals("Your account was created successfully. You are now logged in."));
        test.log(Status.PASS, "Se ha registrado correctamente el usuario.");
    }

    @AfterEach
    public void cerrar() {
        // Cierra el navegador al finalizar cada prueba
        RegisterPage registerPage = new RegisterPage(driver, wait);
        registerPage.close();
    }

    @AfterAll
    public static void reporte() {
        // Genera el archivo HTML con los resultados de las pruebas
        extent.flush();
    }
}
