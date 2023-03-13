package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import static org.example.LoginAndPassword.*;

public class AllCities {

    public static void main(String[] args) {

        // Setup 100 iterations of log in log out
        for (int i = 1; i < 100; i++) {
            //driver launch
//        System.setProperty("webdriver.chrome.driver", "F:\\chromedriver.exe");
            WebDriver driver = new ChromeDriver();
            login(driver);
            driver.close();
        }
    }

    public static void login(WebDriver driver) {

        try {
            // open login page, and login
            driver.get("https://visa.vfsglobal.com/blr/ru/pol/login");
            new WebDriverWait(driver, 60).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id("mat-input-0")));
            driver.findElement(By.id("mat-input-0")).sendKeys(LoginAndPassword.LGN);
            driver.findElement(By.id("mat-input-1")).sendKeys(LoginAndPassword.PSWD);
            TimeUnit.SECONDS.sleep(5);

            //close cookie's window
            driver.findElement(By.xpath("//*[@id='onetrust-close-btn-container']/button")).click();

            // click Login button
            driver.findElement(By.xpath("//button[contains(@class,'ng-star-inserted')]")).click();
            TimeUnit.SECONDS.sleep(25);

            //click booking button
            driver.findElement(By.xpath("//button[contains(@class,'z-index-999')]")).click();
            TimeUnit.SECONDS.sleep(15);

            // Setup 3 iterations of get dates
            for (int j = 0; j < 3; j++) {
                System.out.println(DateTimeFormatter.ofPattern("\ndd MMM yyyy, hh:mm:ss a").format(LocalDateTime.now()));
                int time = getDatesFromAllCities(driver);
                TimeUnit.MINUTES.sleep(time);
            }
        } catch (Exception e) {
            System.out.println("Exception login");
        }
    }


    // select category and visa type
    public static Integer getDatesFromAllCities(WebDriver driver) {
        try {
            //city array
            String[] cities = {"Grodno", "Lida", "Pinsk", "Brest", "Baranovichi"};

            for (int i = 0; i < cities.length; i++) {
                //select city
                driver.findElement(By.xpath("//*[@id='mat-select-value-1']/span")).click();
                System.out.println(cities[i]);
                TimeUnit.SECONDS.sleep(2);
                driver.findElement(By.xpath("//span[contains(text(),'Poland Visa Application Center-" + cities[i] + "')]")).click();
                TimeUnit.SECONDS.sleep(5);

                // select kind of visa
                new WebDriverWait(driver, 20)
                        .until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//*[@id='mat-select-value-3']/span")));
                driver.findElement(By.xpath("//*[@id='mat-select-value-3']/span")).click();
                TimeUnit.SECONDS.sleep(2);
                driver.findElement(By.xpath("//span[contains(text(), ' National Visa D ')]")).click();
                TimeUnit.SECONDS.sleep(5);

                // select type of visa
                new WebDriverWait(driver, 20)
                        .until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//*[@id='mat-select-value-5']/span")));
                driver.findElement(By.xpath("//*[@id='mat-select-value-5']/span")).click();
                TimeUnit.SECONDS.sleep(2);
                driver.findElement(By.xpath("//span[contains(text(),' Karta Polaka D-visa ')]")).click();
//            TimeUnit.SECONDS.sleep(10);

                // print text result
                new WebDriverWait(driver, 20)
                        .until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[contains(@class,'alert-info')]")));
                String textElement = driver.findElement(By.xpath("//div[contains(@class,'alert-info')]")).getText();
                System.out.println(textElement);
                TimeUnit.SECONDS.sleep(3);

                //connect to DB and send request
                //CREATE TABLE visacenter(id INT auto_increment primary key, date DATE not null,time TIME not null, city VARCHAR(15) not null, message VARCHAR(80) not null)
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection = DriverManager.getConnection(LoginAndPassword.URL, LoginAndPassword.USER, LoginAndPassword.PASSWORD);
                Statement statement = connection.createStatement();
                statement.execute("insert into visacenter (city,message,date,time) values ('" + cities[i] + "','" + textElement + "',current_date(),current_timestamp())");
//                System.out.println("Add to DB successfully" + "\n");

                if (cities[i].equals("Lida") && (!textElement.equals("В настоящее время нет свободных мест для записи") &&
                        !textElement.equals("Произошла ошибка. Пожалуйста, попробуйте еще раз через некоторое время."))) {
                    PlayAudio.main();
                    SendEmail.sendEmail("ЕСТЬ ДАТЫ !!! - " + cities[i], cities[i] + " " + textElement);
                    System.out.println("ЕСТЬ ДАТЫ !!!");
                    fillForm(driver);
                    authenticationSMS();
                    System.exit(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Exception");
            return 1;
        }
        return 10;
    }

    public static void fillForm(WebDriver driver) throws InterruptedException {
        driver.findElement(By.cssSelector("button.mat-raised-button")).click();
        TimeUnit.SECONDS.sleep(5);

        //identification number
        driver.findElement(By.xpath("//*[@id='mat-input-2']")).sendKeys(identificationNumber);

        //name and surname
        driver.findElement(By.xpath("//*[@id='mat-input-4']")).sendKeys(name);
        driver.findElement(By.xpath("//*[@id='mat-input-5']")).sendKeys(surName);

        //number of pasport
        driver.findElement(By.xpath("//*[@id='mat-input-6']")).sendKeys(passportNumber);

        //country number
        driver.findElement(By.xpath("//*[@id='mat-input-7']")).sendKeys(countryNumber);

        //phone number
        driver.findElement(By.xpath("//*[@id='mat-input-8']")).sendKeys(contactNumber);

        //email
        driver.findElement(By.xpath("//*[@id='mat-input-9']")).sendKeys(LGN);

        //sex
        driver.findElement(By.xpath("//div[@id='mat-select-value-7']/span")).click();
        TimeUnit.SECONDS.sleep(1);
        driver.findElement(By.xpath("//span[contains(text(), '"+ sex + "')]")).click();

        //country
        driver.findElement(By.xpath("//*[@id='mat-select-value-9']/span")).click();
        TimeUnit.SECONDS.sleep(1);
        driver.findElement(By.xpath("//span[contains(text(), 'BELARUS')]")).click();

        //date of birthday
        driver.findElement(By.xpath("//*[@id='dateOfBirth']")).sendKeys(dateOfBirth);

        //passport validity period
        driver.findElement(By.xpath("//input[@id='passportExpirtyDate']")).sendKeys(LoginAndPassword.endDateOfPassport);
    }

    public static void authenticationSMS() throws InterruptedException {
        WebDriver driver2 = new ChromeDriver();
        driver2.get("https://ticketing.raschet.by/vfs/web");
        TimeUnit.SECONDS.sleep(3);

        driver2.findElement(By.xpath("//input[@name='personal_number']")).sendKeys(identificationNumber);
        driver2.findElement(By.xpath("//input[@name='phone']")).sendKeys("+" + countryNumber + contactNumber);
        TimeUnit.SECONDS.sleep(1);
        driver2.findElement(By.xpath("//button[@name='submit']")).click();

    }


}