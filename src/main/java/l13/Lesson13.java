package l13;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class Lesson13 {
    public static void main(String[] args) {
    }
    public static WebDriver getDriver()
    {
        System.setProperty("webdriver.chrome.driver","src/main/resources/chromedriver.exe" );
        WebDriver driver = new ChromeDriver();
        driver.get("http://mts.by");
        return driver;
    }
}