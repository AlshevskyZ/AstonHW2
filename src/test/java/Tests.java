import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Tests {
    private static WebDriver driver;
    static {
        System.setProperty("webdriver.chrome.driver","src/main/resources/chromedriver.exe" );
        driver = new ChromeDriver();
        driver.get("http://mts.by");
    }
    @Test
    public void checkBlockTitle() {
        String expectTitle = "Онлайн пополнение без комиссии";
        String actualTitle = driver.findElement(By.xpath("//*[@id=\"pay-section\"]" +
                "/div/div/div[2]/section/div/h2")).getText().replace("\n", " ");//из-за тега br
        // получаеи значение с переносом строки(так правильно?)
        assertEquals(expectTitle, actualTitle, "Некорректное название блока!");
    }
    @Test
    public void checkPayLogos() {
       List<WebElement> logos = driver.findElements(By.xpath("//*[@id=\"pay-section\"]" +
               "/div/div/div[2]/section/div/div[2]/ul"));
       assertTrue(!logos.isEmpty(), "Логотипов нет!");
    }
    @Test
    public void checkLink() {
        WebElement moreInfoLink = driver.findElement(By.xpath("//*[@id=\"pay-section\"]/div/div/div[2]/section/div/a"));
        moreInfoLink.click();
        String expectUrl = "https://www.mts.by/help/poryadok-oplaty-i-bezopasnost-internet-platezhey/";
        String actualUrl = driver.getCurrentUrl();
        assertEquals( expectUrl, actualUrl, "Ссылка не работает!");
    }
    @Test
    public void checkButton() throws InterruptedException {
        driver.findElement(By.xpath("//*[@id=\"connection-phone\"]")).sendKeys("297777777");
        driver.findElement(By.xpath("//*[@id=\"connection-sum\"]")).sendKeys("25");
        driver.findElement(By.xpath("//*[@id=\"connection-email\"]")).sendKeys("alshevsky@gmail.com");
        driver.findElement(By.xpath("//*[@id=\"pay-connection\"]/button")).click();
        Thread.sleep(5000);
        boolean isVisible = driver.switchTo().frame(0).findElement(By.name("/html/body/app-root/div/div/div/app-payment-container/section")).isDisplayed();
        assertEquals(isVisible,true);
    }
}
