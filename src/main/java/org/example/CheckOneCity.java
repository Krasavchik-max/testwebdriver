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

public class CheckOneCity {
    public static void main(String[] args) {
        //driver launch
        System.setProperty("webdriver.chrome.driver", "F:\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        // go to website, login and put away cookie
        driver.get("https://visa.vfsglobal.com/blr/ru/pol/login");
        new WebDriverWait(driver, 15).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id("mat-input-0")));
        driver.findElement(By.id("mat-input-0")).sendKeys(LGN);
        driver.findElement(By.id("mat-input-1")).sendKeys(LoginAndPassword.PSWD);
        driver.findElement(By.xpath("//*[@id=\"onetrust-close-btn-container\"]/button")).click();
        waitingDriver(5000L);
        driver.findElement(By.xpath("//button[contains(@class,'ng-star-inserted')]")).click();


        //click on button and go to choose center of this city and fill all them(by xpath)
        waitingDriver(15000L);
        driver.findElement(By.xpath("//button[contains(@class,'z-index-999')]")).click();
        waitingDriver(2000L);
        driver.findElement(By.xpath("//span[contains(text(),'Выберите свой визовый центр')]")).click();
        System.out.println("Визовый центр ");
        driver.findElement(By.xpath("//span[contains(text(),'Poland Visa Application Center-Grodno')]")).click();
        waitingDriver(10000L);
        driver.findElement(By.xpath("//span[contains(text(),'Выберите категорию записи')]")).click();
        System.out.println("Категория Записи");
        driver.findElement(By.xpath("//span[contains(text(), ' National Visa D ')]")).click();
        waitingDriver(10000L);


        // loop method 100 times every 8 minutes
        for (int i = 0; i < 100; i++) {
            timeCheckOut(driver);
            waitingDriver(480000L);
        }


    }


    //a method that pauses the driver
    public static void waitingDriver(Long number) {
        try {
            Thread.sleep(number);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // change Other D-Visa to Karta Polaka
    public static void timeCheckOut(WebDriver driver) {
        waitingDriver(10000L);
        driver.findElement(By.xpath("//span[contains(@class,'ng-tns-c85-8')]")).click();
        waitingDriver(2000L);
        driver.findElement(By.xpath("//span[contains(text(),'Other D-visa')]")).click();
        waitingDriver(10000L);
        driver.findElement(By.xpath("//span[contains(@class,'ng-tns-c85-8')]")).click();
        waitingDriver(2000L);
        driver.findElement(By.xpath("//span[contains(text(),' Karta Polaka D-visa ')]")).click();
        waitingDriver(10000L);

        // output to the console the time when the method happened
        String dateTime = DateTimeFormatter.ofPattern("MMM dd YYYY, hh:mm:ss a").format(LocalDateTime.now());
        System.out.println(" " + dateTime);

        // console output text
        WebElement webElement = driver.findElement(By.xpath("//div[contains(@class,'alert-info')]"));
        String textElement = webElement.getText();
        System.out.println(textElement);
    }
}


