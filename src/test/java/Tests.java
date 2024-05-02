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
    private String orderSum = "25.00";
    private String email ="alshevsky@gmail.com";
    private String telNumber= "297777777";
    private static WebDriver driver = new ChromeDriver();
    @BeforeAll
    public static void start() {
        System.setProperty("webdriver.chrome.driver","src/main/resources/chromedriver.exe");
        driver.get("http://mts.by");
        driver.findElement(By.xpath("//*[@id=\"cookie-agree\"]")).click();//нажатие кнопки cookie файлов
    }
    @AfterEach
    public void returnToMainPage(){
        driver.get("http://mts.by");
    }
    @Test
    public void checkTextOfFields() throws InterruptedException {
        String[] textOfFields = {"Номер телефона","Номер абонента", "Номер счета на 44" , "Номер счета на 2073"};//прове
        //ряемы текст;
        String[] elemId = {"connection-phone","internet-phone","score-instalment","score-arrears"};//id полей
        driver.findElement(By.xpath("//*[@id=\"pay-section\"]/div/div/div[2]/section/div/div[1]/div[1]/" +
                "div[2]/button")).click();
        WebElement ulElement = driver.findElement(By.className("select__list"));
        List<WebElement> links = ulElement.findElements(By.tagName("li"));// создаем и заполняем список ссылок из dropdown
        for(WebElement link : links){ //проходим по всем элементам dropdown
            String pageText ="";
            int i = links.indexOf(link);
            link.click();
            Thread.sleep(5000);
            pageText = driver.findElement(By.className("pay__forms")).findElement(By.id(elemId[i])).
                    getAttribute("placeholder");//подпись пустого поля на выбранной странице
            assertEquals(pageText, textOfFields[i], "Некорректное отображение подписи поля!");
            String sum = driver.findElement(By.xpath("//*[@id=\"connection-sum\"]")).getAttribute(
                    "placeholder");
            assertEquals(sum, "Сумма", "Некорректное отображение стоимости!");

            String email = driver.findElement(By.xpath("//*[@id=\"instalment-email\"]")).getAttribute(
                    "placeholder");
            assertEquals(email, "E-mail для отправки чека", "Некорректное отображение Email!");

            driver.findElement(By.className("select__header")).click();
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Test
    public void TelecommunicationService() throws InterruptedException {// Задание2 для "Услуги связи"
        driver.findElement(By.xpath("//*[@id=\"connection-phone\"]")).sendKeys(telNumber);
        fillPartOfFormAndClick();
        driver.switchTo().frame(1);
        String sumFrame = getTextOfElem("/html/body/app-root/div/div/div/app-payment-container/section/div/div/di" +
                "v/span[1]").replace("BYN", "").trim();//получаеи сумму из фрейма

        String sumButton = getTextOfElem("/html/body/app-root/div/div/div/app-payment-conta" +
                "iner/section/div/app-card-page/div/div[1]/button").replace("BYN",
                "").replace("Оплатить", "").trim();//получаем сумму из кнопки

        String numberOnPage= getTextOfElem("/html/body/app-root/div/div/div/app-payment-container/section/div/div/s" +
                "pan").replace("Оплата: Услуги связи Номер:375",
                "").replace("Оплатить", "").trim();//получить номер со страницы

        boolean isEmptylogos = driver.findElements(By.xpath("/html/body/app-root/div/div/div/app-payment-" +
                "container/section/div/app-card-page/div/div[1]/app-card-input/form/div[1]/div[1]/app-input/div/div/div" +
                "[2]")).isEmpty();//проверка наличия логотипов

        String cvcText= getTextOfElem("/html/body/app-root/div/div/div/app-payment-container/section/div/app-card" +
                "-page/div/div[1]/app-card-input/form/div[1]/div[2]/div[3]/app-input/div/div/div[1]/label");//cvc текст

        String cardNumberText = getTextOfElem("/html/body/app-root/div/div/div/app-payment-container/section/div/a" +
                "pp-card-page/div/div[1]/app-card-input/form/div[1]/div[1]/app-input/div/div/div[1]/label");//текст номер карты

        String dateText = getTextOfElem("/html/body/app-root/div/div/div/app-payment-container/section/div/app-card" +
                "-page/div/div[1]/app-card-input/form/div[1]/div[2]/div[1]/app-input/div/div/div[1]/label");//дата текст

        String nameText = getTextOfElem("/html/body/app-root/div/div/div/app-payment-container/section/div/app-c" +
                "ard-page/div/div[1]/app-card-input/form/div[1]/div[3]/app-input/div/div/div[1]/label");//имя текст
        assertEquals(orderSum, sumFrame, "Некорректное отображение стоимости!");
        assertEquals(orderSum, sumButton, "Некорректное отображение стоимости в кнопке!");
        assertEquals(telNumber, numberOnPage, "Некорректное отображение номера телефона!");
        assertEquals(cvcText, "CVC", "Некорректное отображение подписи CVV!");
        assertEquals(cardNumberText, "Номер карты", "Некорректное отображение подписи номера карты!");
        assertEquals(dateText, "Срок действия", "Некорректное отображение подписи даты!");
        assertEquals(nameText, "Имя держателя (как на карте)", "Некорректное отображение подписи Имени!");
        assertTrue(!isEmptylogos , "Логотипов нет!");
    }

    public void fillPartOfFormAndClick() throws InterruptedException {
        driver.findElement(By.xpath("//*[@id=\"connection-sum\"]")).sendKeys(orderSum);
        driver.findElement(By.xpath("//*[@id=\"connection-email\"]")).sendKeys(email);
        driver.findElement(By.xpath("//*[@id=\"pay-connection\"]/button")).click();
        Thread.sleep(15000);
    }
    public String getTextOfElem(String path){
        return driver.findElement(By.xpath(path)).getText();
    }
}