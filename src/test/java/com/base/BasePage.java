package com.base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Clase base que centraliza las acciones comunes utilizadas por todas las páginas del proyecto.
 * Implementa métodos genéricos para interactuar con elementos web mediante Selenium WebDriver.
 */
public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;

    //Constructor de la clase BasePage
    public BasePage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    //Abre una URL específica en el navegador.
    public void open(String url) {
        driver.get(url);
    }

    //Configura el entorno inicial del navegador.
    public void setup() {
        System.setProperty("webdriver.chrome.driver",
                // Ruta local del driver para ejecución en entorno personal
                "C:\\Users\\mateo\\OneDrive\\Documentos\\chromedriver.exe");
        driver.manage().window().maximize();
    }

    //Busca un elemento en la página y espera a que esté presente en el DOM.
    public WebElement findElement(By locator) {
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        return driver.findElement(locator);
    }

    // Envía texto a un campo input después de limpiarlo.
    public void sendText(String input, By locator) {
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        WebElement element = driver.findElement(locator);
        element.clear();
        element.sendKeys(input);
    }

    // Envía una tecla o secuencia de teclas al elemento indicado.
    public void sendKey(CharSequence input, By locator) {
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        driver.findElement(locator).sendKeys(input);
    }

    //Hace clic en un elemento cuando esté presente.
    public void clickear(By locator) {
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        driver.findElement(locator).click();
    }


    // Obtiene el texto visible de un elemento.
    public String getText(By locator) {
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        return driver.findElement(locator).getText();
    }


    // Cierra todas las ventanas del navegador y termina la sesión de WebDriver.
    public void close() {
        driver.quit();
    }
}
