package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.example.LoginAndPassword.LGN;

public class AllCities {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "F:\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("https://visa.vfsglobal.com/blr/ru/pol/login");
        new WebDriverWait(driver, 15).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id("mat-input-0")));
        driver.findElement(By.id("mat-input-0")).sendKeys(LGN);
        driver.findElement(By.id("mat-input-1")).sendKeys(LoginAndPassword.PSWD);
        driver.findElement(By.xpath("//button[contains(@class,'ng-star-inserted')]")).click();
        waitingDriver(15000L);
        driver.findElement(By.xpath("//button[contains(@class,'z-index-999')]")).click();
        waitingDriver(10000L);
        String[] cities = {"Grodno", "Baranovichi", "Brest", "Gomel", "Lida", "Minsk", "Mogilev", "Pinsk"};

        for (int i = 0; i < cities.length; i++) {
            driver.findElement(By.xpath("//*[@id=\"mat-select-value-1\"]/span")).click();
            System.out.println("Визовый центр " + cities[i]);
            waitingDriver(2000L);
            driver.findElement(By.xpath("//span[contains(text(),'Poland Visa Application Center-" + cities[i] + "')]")).click();
            timeCheckOut(driver);
        }
    }
    public static void waitingDriver(Long number) {
        try {
            Thread.sleep(number);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static void timeCheckOut(WebDriver driver) {
        waitingDriver(10000L);
        driver.findElement(By.xpath("//*[@id=\"mat-select-value-3\"]/span")).click();
        driver.findElement(By.xpath("//span[contains(text(), ' National Visa D ')]")).click();
        waitingDriver(10000L);
        driver.findElement(By.xpath("//*[@id=\"mat-select-value-5\"]/span")).click();
        waitingDriver(2000L);
        driver.findElement(By.xpath("//span[contains(text(),' Karta Polaka D-visa ')]")).click();
        waitingDriver(10000L);
        WebElement webElement = driver.findElement(By.xpath("//div[contains(@class,'alert-info')]"));
        String textElement = webElement.getText();
        System.out.println(textElement + "\n");
    }
}


