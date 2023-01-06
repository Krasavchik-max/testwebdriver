package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AllCities {
    public static void main(String[] args) {

        //driver launch
        System.setProperty("webdriver.chrome.driver", "F:\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        // open login page, and login
        driver.get("https://visa.vfsglobal.com/blr/ru/pol/login");
        new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id("mat-input-0")));
        driver.findElement(By.id("mat-input-0")).sendKeys(LoginAndPassword.LGN);
        driver.findElement(By.id("mat-input-1")).sendKeys(LoginAndPassword.PSWD);
        timer(5000L);

        //close cookie's window
        driver.findElement(By.xpath("//*[@id=\"onetrust-close-btn-container\"]/button")).click();

        // click Login button
        driver.findElement(By.xpath("//button[contains(@class,'ng-star-inserted')]")).click();
        timer(15000L);

        //click booking button
        driver.findElement(By.xpath("//button[contains(@class,'z-index-999')]")).click();
        timer(10000L);

        for (int i = 0; i < 100; i++) {
            changeCity(driver);
            timer(480000L);

        }
    }


    //a method that pauses the driver
    public static void timer(Long number) {
        try {
            Thread.sleep(number);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // select category and visa type
    public static void changeCity(WebDriver driver) {

        //select city from array
        String[] cities = {"Grodno", "Baranovichi", "Brest", "Gomel", "Lida", "Minsk", "Mogilev", "Pinsk"};

        //replace element of cities
        for (int i = 0; i < cities.length; i++) {
            timer(5000L);
            driver.findElement(By.xpath("//*[@id=\"mat-select-value-1\"]/span")).click();
            System.out.println("Визовый центр " + cities[i]);
            timer(5000L);
            driver.findElement(By.xpath("//span[contains(text(),'Poland Visa Application Center-" + cities[i] + "')]")).click();

        // select type and kind of visa
            timer(10000L);
            driver.findElement(By.xpath("//*[@id=\"mat-select-value-3\"]/span")).click();
            timer(2000L);
            driver.findElement(By.xpath("//span[contains(text(), ' National Visa D ')]")).click();
            timer(10000L);
            driver.findElement(By.xpath("//*[@id=\"mat-select-value-5\"]/span")).click();
            timer(2000L);
            driver.findElement(By.xpath("//span[contains(text(),' Karta Polaka D-visa ')]")).click();
            timer(10000L);

            // print text result
            String textElement = driver.findElement(By.xpath("//div[contains(@class,'alert-info')]")).getText();
            System.out.println(textElement + "\n");
        }
    }
}





