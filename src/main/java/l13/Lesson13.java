package l13;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class Lesson13 {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver","src/main/resources/chromedriver.exe" );
        WebDriver driver = new ChromeDriver();
        driver.get("http://mts.by");



        WebElement blockTitle = driver.findElement(By.xpath("//*[@id=\"pay-section\"]/div/div/div[2]/section/div/h2"));
        String expectedTitle = "Онлайн пополнение без комиссии";
        WebElement moreInfoLink = driver.findElement(By.xpath("//*[@id=\"pay-section\"]/div/div/div[2]/section/div/a"));
        moreInfoLink.click();
        List<WebElement> logos = driver.findElements(By.xpath("//*[@id=\"pay-section\"]/div/div/div[2]/section/div/div[2]"));
        WebElement phoneNumberField = driver.findElement(By.xpath("//css_selector_for_phone_number_field"));
        WebElement serviceDropDown = driver.findElement(By.xpath("//css_selector_for_service_dropdown"));
        WebElement continueButton = driver.findElement(By.xpath("//css_selector_for_continue_button"));
        phoneNumberField.sendKeys("297777777");
        Select serviceDropdownSelect = new Select(serviceDropDown);
        serviceDropdownSelect.selectByVisibleText("Услуги связи");
        continueButton.click();
    }
}