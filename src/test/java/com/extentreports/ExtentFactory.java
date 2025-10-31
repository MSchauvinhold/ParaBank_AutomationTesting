package com.extentreports;

import com.aventstack.extentreports.ExtentReports;

/**
 * Clase responsable de inicializar la instancia principal de ExtentReports.
 * Permite centralizar la configuración del reporte y definir la información del entorno.
 */
public class ExtentFactory {

    //Retorna una instancia configurada de ExtentReports.
    //Los datos de entorno se establecen para contextualizar los reportes generados.

    public static ExtentReports getInstance() {
        ExtentReports extent = new ExtentReports();
        extent.setSystemInfo("Selenium Version", "4.25.0");
        extent.setSystemInfo("SO", "Windows 11");
        extent.setSystemInfo("Navegador", "Chrome");
        extent.setSystemInfo("Entorno", "QA");
        return extent;
    }
}
