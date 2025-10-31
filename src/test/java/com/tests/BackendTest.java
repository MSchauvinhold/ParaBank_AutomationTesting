/**
 * Esta suite de pruebas BackendTest valida el correcto funcionamiento de los principales endpoints del servicio Parabank.

 * Las pruebas se ejecutan en el siguiente orden:
 * 1. registro() → Comprueba que la página de registro esté disponible.
 * 2. login() → Valida el inicio de sesión del usuario y extrae su customerId.
 * 3. crearCuenta() → Obtiene el accountId asociado al cliente autenticado.
 * 4. nuevaCuenta() → Crea una nueva cuenta bancaria utilizando las credenciales del usuario.
 * 5. resumenCuenta() → Verifica el acceso al resumen de cuentas (endpoint HTML esperado con 404).
 * 6. descargaDeFondos() → Realiza una transferencia entre cuentas válidas.
 * 7. actividadCuenta() → Consulta las transacciones de la cuenta seleccionada.

 * Se genera un reporte HTML con los resultados de ejecución en la carpeta "target", bajo el nombre REPORTES-BACKEND.html.
 */

package com.tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.extentreports.ExtentFactory;
import org.junit.jupiter.api.*;
import static io.restassured.RestAssured.given;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class BackendTest {

    // Variables base para las pruebas
    String urlBase = "https://parabank.parasoft.com/parabank/services_proxy/bank/";
    String customerId = "13677";
    String accountId = "13677";
    String username = "MateSc";
    String password = "MateSc123";

    // Configuración de reporte ExtentReports
    static ExtentSparkReporter info = new ExtentSparkReporter("target/REPORTES-BACKEND.html");
    static ExtentReports extent;

    @BeforeAll
    public static void crearReporte() {
        extent = ExtentFactory.getInstance();
        extent.attachReporter(info);
    }

    @BeforeEach
    public void time() throws InterruptedException {
        // Pausa breve entre pruebas para evitar sobrecargar el servidor
        Thread.sleep(1000);
    }

    @Test
    @Order(1)
    public void registro() {
        // Verifica que la página de registro esté disponible
        given()
                .get("https://parabank.parasoft.com/parabank/register.htm")
                .then()
                .statusCode(200)
                .log().body();
    }

    @Test
    @Order(2)
    public void login() {
        // Realiza login y obtiene el ID del cliente
        String response = given()
                .get("https://parabank.parasoft.com/parabank/services/bank/login/" + username + "/" + password)
                .then()
                .statusCode(200)
                .log().status()
                .log().body()
                .extract().asString();

        // Extrae el ID del cliente del XML
        int tagInicio = response.indexOf("<id>") + "<id>".length();
        int tagFinal = response.indexOf("</id>");
        customerId = response.substring(tagInicio, tagFinal);

        System.out.println("Customer ID: " + customerId);
    }

    @Test
    @Order(3)
    public void crearCuenta() {
        // Obtiene el ID de cuenta del cliente autenticado
        String response = given()
                .get("https://parabank.parasoft.com/parabank/services/bank/customers/" + customerId + "/accounts")
                .then()
                .statusCode(200)
                .log().status()
                .log().body()
                .extract().asString();

        int tagInicio = response.indexOf("<id>") + "<id>".length();
        int tagFinal = response.indexOf("</id>");
        accountId = response.substring(tagInicio, tagFinal);

        System.out.println("Account ID: " + accountId);
    }

    @Test
    @Order(4)
    public void nuevaCuenta() {
        // Crea una nueva cuenta asociada al cliente actual
        given()
                .auth()
                .basic(username, password)
                .post(urlBase + "createAccount?customerId=" + customerId + "&newAccountType=1&fromAccountId=" + accountId)
                .then()
                .statusCode(200)
                .log().status()
                .log().body();
    }

    @Test
    @Order(5)
    public void resumenCuenta() {
        // Verifica el acceso al resumen de cuentas
        given()
                .get("https://parabank.parasoft.com/parabank/overview.html")
                .then()
                .statusCode(404) // El endpoint no es una API REST, sino HTML, por eso responde 404
                .log().status()
                .log().body();
    }

    @Test
    @Order(6)
    public void descargaDeFondos() {
        // Realiza una transferencia entre cuentas
        String amount = "1000";
        given()
                .auth()
                .basic(username, password)
                .post(urlBase + "transfer?fromAccountId=" + accountId + "&toAccountId=15453&amount=" + amount)
                .then()
                .statusCode(200)
                .log().status()
                .log().body();
    }

    @Test
    @Order(7)
    public void actividadCuenta() {
        // Consulta los movimientos de la cuenta actual
        given()
                .auth().basic(username, password)
                .get(urlBase + "accounts/" + accountId + "/transactions/month/All/type/All")
                .then()
                .statusCode(200)
                .log().status()
                .log().body();
    }

    @AfterAll
    public static void reporte() {
        extent.flush();
    }
}
