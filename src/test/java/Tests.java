import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Tests {
    private static WebDriver driver = new ChromeDriver();
    @BeforeAll
    public static void start() {
        System.setProperty("webdriver.chrome.driver","src/main/resources/chromedriver.exe" );
        driver.get("http://mts.by");
        driver.findElement(By.xpath("//*[@id=\"cookie-agree\"]")).click();//нажатие кнопки cookie файлов
    }
    @AfterEach
    private void setDriver(){
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
        WebElement moreInfoLink = driver.findElement(By.xpath("//*[@id=\"pay-section\"]/div/div/div[2]/" +
                "section/div/a"));
        moreInfoLink.click();
        String expectUrl = "https://www.mts.by/help/poryadok-oplaty-i-bezopasnost-internet-platezhey/";
        String actualUrl = driver.getCurrentUrl();
        assertEquals( expectUrl, actualUrl, "Ссылка не работает!");
    }
    @Test
    public void checkButton() throws InterruptedException {
        driver.findElement(By.xpath("//*[@id=\"connection-phone\"]")).sendKeys("297777777");
        driver.findElement(By.xpath("//*[@id=\"connection-sum\"]")).sendKeys("25");
        driver.findElement(By.xpath("//*[@id=\"connection-email\"]")).sendKeys("alshevs" +
                "ky@gmail.com");
        driver.findElement(By.xpath("//*[@id=\"pay-connection\"]/button")).click();
        Thread.sleep(15000);
        boolean isVisible = driver.switchTo().frame(1).findElement(By.xpath("/html/body/app-root/d"+
                "iv/div/div/app-payment-container/section/div/div/span")).isDisplayed();
        assertEquals(isVisible,true);
    }
}