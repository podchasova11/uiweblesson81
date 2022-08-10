package lesson6;


import lesson6.LoginPage;
import lesson6.MainPage;
import lesson6.CustomLogger;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Allure;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.openqa.selenium.support.ui.WebDriverWait;

@Story("Добавление в корзину")
public class AutomationPractiseTest {
    WebDriver driver;
    WebDriverWait webDriverWait;
    Actions actions;

    @BeforeAll
    static void registerDriver() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void initDriver() {
        driver = new EventFiringDecorator(new CustomLogger()).decorate(new ChromeDriver());
    }

    @Test
    @Feature("Работа с корзиной")
    void addToCartTest() throws InterruptedException {
        driver.get("http://automationpractice.com/");

        new MainPage(driver)
                .clickSignInButton()
                .login("spartalex93@test.test", "123456")
                .hoverWomenButton()
                .tShirtsButtonClick()
                .selectSizeInFilters("S")
                .hoverAndClickAddToCartProductByName("Faded")
                .checkSuccessWithSum("$16.51");
    }

    @AfterEach
    void tearDown() {
        LogEntries logEntries = driver.manage().logs().get(LogType.BROWSER);

        for (LogEntry log : logEntries) {
            Allure.addAttachment("Элемент лога браузера", log.getMessage());
        }
        driver.quit();
    }
}

