package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class AllCities {

    public static void main(String[] args) throws ClassNotFoundException, InterruptedException, UnsupportedAudioFileException, LineUnavailableException, IOException {

        // Setup 100 iterations of log in log out
        for (int i = 1; i < 100; i++) {
            //driver launch
//        System.setProperty("webdriver.chrome.driver", "F:\\chromedriver.exe");
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

            // Setup 2 iterations of get dates
            for (int j = 0; j < 3; j++) {
                System.out.println(DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm:ss a").format(LocalDateTime.now()));
                getDatesFromAllCities(driver);
                TimeUnit.MINUTES.sleep(10);
            }
            driver.close();
        }
    }

    // select category and visa type
    public static void getDatesFromAllCities(WebDriver driver) throws InterruptedException, UnsupportedAudioFileException, LineUnavailableException, IOException {

        //city array
        String[] cities = {"Grodno", "Lida", "Minsk", "Baranovichi", "Brest", "Pinsk", "Gomel", "Mogilev"};

        for (int i = 0; i < cities.length; i++) {
            //select city
            driver.findElement(By.xpath("//*[@id=\"mat-select-value-1\"]/span")).click();
            System.out.println(cities[i]);
            TimeUnit.SECONDS.sleep(2);
            driver.findElement(By.xpath("//span[contains(text(),'Poland Visa Application Center-" + cities[i] + "')]")).click();
            TimeUnit.SECONDS.sleep(5);

            // select kind of visa
            driver.findElement(By.xpath("//*[@id=\"mat-select-value-3\"]/span")).click();
            TimeUnit.SECONDS.sleep(2);
            driver.findElement(By.xpath("//span[contains(text(), ' National Visa D ')]")).click();
            TimeUnit.SECONDS.sleep(5);

            // select type of visa
            driver.findElement(By.xpath("//*[@id=\"mat-select-value-5\"]/span")).click();
            TimeUnit.SECONDS.sleep(2);
            driver.findElement(By.xpath("//span[contains(text(),' Karta Polaka D-visa ')]")).click();
            TimeUnit.SECONDS.sleep(5);

            // print text result
            String textElement = driver.findElement(By.xpath("//div[contains(@class,'alert-info')]")).getText();
            System.out.println(textElement);


            //connect to DB and send request
            try {
                //CREATE TABLE visacenter(id INT auto_increment primary key, date DATE not null,time TIME not null, city VARCHAR(15) not null, message VARCHAR(80) not null)
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection = DriverManager.getConnection(LoginAndPassword.URL, LoginAndPassword.USER, LoginAndPassword.PASSWORD);
                Statement statement = connection.createStatement();
                statement.execute("insert into visacenter (city,message,date,time) values ('" + cities[i] + "','" + textElement + "',current_date(),current_timestamp())");
                System.out.println("Add to DB successfully" + "\n");

            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            if (cities[i].equals("Grodno") && (!textElement.equals("В настоящее время нет свободных мест для записи") &&
                    !textElement.equals("Произошла ошибка. Пожалуйста, попробуйте еще раз через некоторое время."))) {
                PlayAudio.main();
                SendEmail.sendEmail("ЕСТЬ ДАТЫ !!! - " + cities[i], cities[i]+ " " + textElement);
                System.out.println("ЕСТЬ ДАТЫ !!!");
            }
        }
    }
}