package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import static org.example.ConnectionToDB.connectToDB;
import static org.example.ConnectionToDB.putToDB;
import static org.example.SendEmail.sendEmail;

public class AllCities {

    public static void main(String[] args) throws UnsupportedAudioFileException, LineUnavailableException, IOException, InterruptedException, SQLException {


        // Setup 100 iterations of log in log out
        for (int i = 1; i <= 2; i++) {
            //driver launch
            System.setProperty("webdriver.chrome.driver", "F:\\chromedriver.exe");
            WebDriver driver = new ChromeDriver();

            // open login page, and login
            driver.get("https://visa.vfsglobal.com/blr/ru/pol/login");
            new WebDriverWait(driver, 60).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id("mat-input-0")));
            TimeUnit.SECONDS.sleep(2);
            driver.findElement(By.xpath("/html/body/app-root/div/app-login/section/div/div/mat-card/form/div[1]/mat-form-field/div/div[1]/div[3]/input")).sendKeys(LoginAndPassword.LGN);
            driver.findElement(By.xpath("/html/body/app-root/div/app-login/section/div/div/mat-card/form/div[2]/mat-form-field/div/div[1]/div[3]/input")).sendKeys(LoginAndPassword.PSWD);
            TimeUnit.SECONDS.sleep(5);

            //close cookie's window
            driver.findElement(By.xpath("//*[@id=\"onetrust-close-btn-container\"]/button")).click();

            // click Login button
            driver.findElement(By.xpath("/html/body/app-root/div/app-login/section/div/div/mat-card/form/button")).click();
            TimeUnit.SECONDS.sleep(15);

            //click booking button
            driver.findElement(By.xpath("/html/body/app-root/div/app-dashboard/section[1]/div/div[1]/div[2]/button/span[1]")).click();
            TimeUnit.SECONDS.sleep(10);
            System.out.println("Start Session " + DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm:ss a").format(LocalDateTime.now()));
            getDatesFromAllCities(driver);
            TimeUnit.SECONDS.sleep(5);
            driver.close();
            System.out.println("End Session " + DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm:ss a").format(LocalDateTime.now()));
            TimeUnit.MINUTES.sleep(15);


        }


    }

    public static String textElement;


    //city array
    public static String[] cities = {"Grodno", "Lida", "Minsk", "Baranovichi", "Brest", "Pinsk", "Gomel", "Mogilev"};

    // select category and visa type
    public static void getDatesFromAllCities(WebDriver driver) throws
            InterruptedException, UnsupportedAudioFileException, LineUnavailableException, IOException, SQLException {


        for (int i = 0; i < cities.length; i++) {
            //select city
            driver.findElement(By.xpath("//*[@id=\"mat-select-value-1\"]/span")).click();
            System.out.println(cities[i]);
            driver.findElement(By.xpath("//span[contains(text(),'Poland Visa Application Center-" + cities[i] + "')]")).click();
            TimeUnit.SECONDS.sleep(5);

            // select kind of visa
            driver.findElement(By.xpath("//*[@id=\"mat-select-value-3\"]/span")).click();
            driver.findElement(By.xpath("//span[contains(text(), ' National Visa D ')]")).click();
            TimeUnit.SECONDS.sleep(5);

            // select type of visa
            driver.findElement(By.xpath("//*[@id=\"mat-select-value-5\"]/span")).click();
            driver.findElement(By.xpath("//span[contains(text(),' Karta Polaka D-visa ')]")).click();
            TimeUnit.SECONDS.sleep(5);


            // print text result
            textElement = driver.findElement(By.xpath("/html/body/app-root/div/app-eligibility-criteria/section/form/mat-card[1]/form/div[4]/div")).getText();
            System.out.println(textElement);

            try {
                connectToDB();
                putToDB();
            } catch (SQLException e) {
                e.printStackTrace();

            }

            //check date and play sound
            if (cities[i].equals("Gomel") && (!textElement.equals("В настоящее время нет свободных мест для записи") &&
                    !textElement.equals("Произошла ошибка. Пожалуйста, попробуйте еще раз через некоторое время."))) {
                System.out.println("ЕСТЬ ДАТЫ !!!");
                sendEmail();
            }
        }
    }

}


