package org.example;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import static org.example.LoginAndPassword.LGN;

public class OneCity {
    public static void main(String[] args) {
        //driver launch
//        System.setProperty("webdriver.chrome.driver", "F:\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        // go to website, login and put away cookie
        driver.get("https://visa.vfsglobal.com/blr/ru/pol/login");
        new WebDriverWait(driver, 15).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id("mat-input-0")));
        driver.findElement(By.id("mat-input-0")).sendKeys(LGN);
        driver.findElement(By.id("mat-input-1")).sendKeys(LoginAndPassword.PSWD);

        timer(5000L);
        driver.findElement(By.xpath("//*[@id=\"onetrust-close-btn-container\"]/button")).click();

        //Click Login button
        driver.findElement(By.xpath("//button[contains(@class,'ng-star-inserted')]")).click();
        timer(15000L);

        //click booking button
        driver.findElement(By.xpath("//button[contains(@class,'z-index-999')]")).click();
        timer(2000L);

        //select VC
        driver.findElement(By.xpath("//span[contains(text(),'Выберите свой визовый центр')]")).click();
        driver.findElement(By.xpath("//span[contains(text(),'Poland Visa Application Center-Grodno')]")).click();
        timer(10000L);

        //select category
        driver.findElement(By.xpath("//span[contains(text(),'Выберите категорию записи')]")).click();
        driver.findElement(By.xpath("//span[contains(text(), ' National Visa D ')]")).click();
        timer(10000L);


        // loop method 100 times every 8 minutes
        for (int i = 0; i < 100; i++) {
            timeCheckOut(driver);
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

    // change Other D-Visa to Karta Polaka
    public static void timeCheckOut(WebDriver driver) {
        
        timer(10000L);
        driver.findElement(By.xpath("//span[contains(@class,'ng-tns-c85-8')]")).click();
        timer(2000L);
        driver.findElement(By.xpath("//span[contains(text(),'Other D-visa')]")).click();
        timer(10000L);
        driver.findElement(By.xpath("//span[contains(@class,'ng-tns-c85-8')]")).click();
        timer(2000L);
        driver.findElement(By.xpath("//span[contains(text(),' Karta Polaka D-visa ')]")).click();
        timer(10000L);

        // print time stamp
        String dateTime = DateTimeFormatter.ofPattern("MMM dd YYYY, hh:mm:ss a").format(LocalDateTime.now());
        System.out.print( dateTime + " ");

        // print text result
        WebElement webElement = driver.findElement(By.xpath("//div[contains(@class,'alert-info')]"));
        String textResult = webElement.getText();
        System.out.println(textResult);
    }
}


