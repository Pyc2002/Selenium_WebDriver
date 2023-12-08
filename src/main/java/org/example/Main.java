package org.example;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Main {
    public static void main(String[] args) {
        System.setProperty("webodriver.chrome.driver","src/main/resources/chromedriver.exe");

        ChromeOptions chromeOptions = new ChromeOptions();
//        chromeOptions.addArguments("--headless");

        WebDriver webDriver = new ChromeDriver(chromeOptions);
        webDriver.manage().window().minimize();
        webDriver.get("https://test-stand.gb.ru/login");

        System.out.println("Title: " + webDriver.getTitle());




//        webDriver.quit();


    }
}