package com.pages;

import com.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Página de resumen de cuentas.
 * Permite al usuario iniciar sesión y acceder al resumen general de sus cuentas.
 */
public class ResumenPage extends BasePage {

    // Localizadores de elementos
    private By usuarioId = By.xpath("//input[@name='username']");
    private By password = By.xpath("//input[@name='password']");
    private By botonLogin = By.xpath("//input[@value='Log In']");
    private By accountsOverview = By.linkText("Accounts Overview");
    private By mensajeBalance = By.xpath("//TD[@colspan='3'][text()='*Balance includes deposits that may be subject to holds\n" +
            "        ']");

    public ResumenPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    // Acciones de login y navegación
    public void escribirUsuario(String usuario) throws InterruptedException {
        sendText(usuario, usuarioId);
    }

    public void escribirPassword(String pass) throws InterruptedException {
        sendText(pass, password);
    }

    public void clickBotonLogin() throws InterruptedException {
        this.clickear(botonLogin);
    }

    public void clickAccountsOverview() throws InterruptedException {
        this.clickear(accountsOverview);
    }

    // Validación de mensaje en el resumen
    public String obtenerMensajeBalance() {
        System.out.println("MENSAJE: " + this.getText(mensajeBalance));
        return this.getText(mensajeBalance);
    }
}
