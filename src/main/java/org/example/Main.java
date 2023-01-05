package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import static org.example.LoginAndPassword.LGN;

public class Main {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "F:\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        driver.get("https://visa.vfsglobal.com/blr/ru/pol/login");
        new WebDriverWait(driver, 15).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id("mat-input-0")));
        driver.findElement(By.id("mat-input-0")).sendKeys(LGN);
        driver.findElement(By.id("mat-input-1")).sendKeys(LoginAndPassword.PSWD);
        driver.findElement(By.xpath("//button[contains(@class,'ng-star-inserted')]")).click();
        System.out.println("До таймаута");



        waitingDriver(15000L);
        System.out.println("После таймаута");
        driver.findElement(By.xpath("//button[contains(@class,'z-index-999')]")).click();


        waitingDriver(10000L);





        driver.findElement(By.xpath("//span[contains(text(),'Выберите свой визовый центр')]")).click();
        System.out.println("Визовый центр ");
        driver.findElement(By.xpath("//span[contains(text(),'Poland Visa Application Center-Grodno')]")).click();


        waitingDriver(10000L);


        driver.findElement(By.xpath("//span[contains(text(),'Выберите категорию записи')]")).click();
        System.out.println("Категория Записи");
        driver.findElement(By.xpath("//span[contains(text(), ' National Visa D ')]")).click();

        waitingDriver(10000L);


        for (int i = 0; i < 10; i++) {
            timeCheckOut(driver);
            waitingDriver(60000L);
        }



    }



    public static void waitingDriver(Long number){
        try {
            Thread.sleep(number);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
   public static void timeCheckOut(WebDriver driver){
       waitingDriver(10000L);
       driver.findElement(By.xpath("//span[contains(@class,'ng-tns-c85-8')]")).click();
       waitingDriver(2000L);
       driver.findElement(By.xpath("//span[contains(text(),'Other D-visa')]")).click();

       waitingDriver(10000L);
       driver.findElement(By.xpath("//span[contains(@class,'ng-tns-c85-8')]")).click();
       waitingDriver(2000L);
       driver.findElement(By.xpath("//span[contains(text(),' Karta Polaka D-visa ')]")).click();

       waitingDriver(10000L);

       WebElement webElement = driver.findElement(By.xpath("//div[contains(@class,'alert-info')]"));
       String textElement = webElement.getText();

       Date date = new Date();
       System.out.println(textElement);
       System.out.print(date);



   }

    }


