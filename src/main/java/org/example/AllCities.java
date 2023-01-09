package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class AllCities {
    public static void main(String[] args) throws InterruptedException {

        //driver launch
        System.setProperty("webdriver.chrome.driver", "F:\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        // open login page, and login
        driver.get("https://visa.vfsglobal.com/blr/ru/pol/login");
        new WebDriverWait(driver, 60).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id("mat-input-0")));
        driver.findElement(By.id("mat-input-0")).sendKeys(LoginAndPassword.LGN);
        driver.findElement(By.id("mat-input-1")).sendKeys(LoginAndPassword.PSWD);
        TimeUnit.SECONDS.sleep(5);

        //close cookie's window
        driver.findElement(By.xpath("//*[@id=\"onetrust-close-btn-container\"]/button")).click();

        // click Login button
        driver.findElement(By.xpath("//button[contains(@class,'ng-star-inserted')]")).click();
        TimeUnit.SECONDS.sleep(15);

        //click booking button
        driver.findElement(By.xpath("//button[contains(@class,'z-index-999')]")).click();
        TimeUnit.SECONDS.sleep(10);

        for (int i = 0; i < 100; i++) {
            System.out.println(DateTimeFormatter.ofPattern("dd MMM YYYY, hh:mm:ss a").format(LocalDateTime.now()));
            getDatesFromAllCities(driver);
            TimeUnit.MINUTES.sleep(14);
        }
    }

    // select category and visa type
    public static void getDatesFromAllCities(WebDriver driver) throws InterruptedException {

        //city array
        String[] cities = {"Grodno", "Lida", "Minsk", "Baranovichi", "Brest", "Pinsk", "Gomel", "Mogilev"};

        for (int i = 0; i < cities.length; i++) {
            //select city
            driver.findElement(By.xpath("//*[@id=\"mat-select-value-1\"]/span")).click();
            System.out.println(cities[i]);
            TimeUnit.SECONDS.sleep(5);
            driver.findElement(By.xpath("//span[contains(text(),'Poland Visa Application Center-" + cities[i] + "')]")).click();
            TimeUnit.SECONDS.sleep(10);

            // select kind of visa
            driver.findElement(By.xpath("//*[@id=\"mat-select-value-3\"]/span")).click();
            TimeUnit.SECONDS.sleep(2);
            driver.findElement(By.xpath("//span[contains(text(), ' National Visa D ')]")).click();
            TimeUnit.SECONDS.sleep(10);

            // select type of visa
            driver.findElement(By.xpath("//*[@id=\"mat-select-value-5\"]/span")).click();
            TimeUnit.SECONDS.sleep(2);
            driver.findElement(By.xpath("//span[contains(text(),' Karta Polaka D-visa ')]")).click();
            TimeUnit.SECONDS.sleep(10);

            // print text result
            String textElement = driver.findElement(By.xpath("//div[contains(@class,'alert-info')]")).getText();
            System.out.println(textElement + "\n");
        }
    }
}