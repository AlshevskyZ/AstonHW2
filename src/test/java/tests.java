import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class tests {

    @Test
    public void verifyBlockTitle() {
        System.setProperty("webdriver.chrome.driver","src/main/resources/chromedriver.exe" );
        WebDriver driver = new ChromeDriver();
        driver.get("http://mts.by");
        String expectedTitle = "Онлайн пополнение без комиссии";
        String actualTitle = driver.findElement(By.xpath("//*[@id=\"pay-section\"]/div/div/div[2]/section/div/h2")).getText();
        assertEquals(actualTitle, expectedTitle, "Название блока не соответствует ожидаемому");

    }

}
