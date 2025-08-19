package ru.praktikum;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import ru.praktikum.core.FaqService;
import ru.praktikum.core.OrderService;
import ru.praktikum.pages.HomePage;
import ru.praktikum.services.ui.UIFaqService;
import ru.praktikum.services.ui.UIOrderService;
import java.time.Duration;

/**
 * Базовый класс для всех тестов.
 * Содержит общую логику инициализации и завершения работы тестов.
 */
public class BaseTest {
    protected WebDriver driver;
    protected OrderService orderService;
    protected FaqService faqService;

    // Конфигурация таймаутов
    private static final int IMPLICIT_WAIT_SECONDS = 5;
    private static final int EXPLICIT_WAIT_SECONDS = 15;
    private static final String BASE_URL = "https://qa-scooter.praktikum-services.ru/";

    /**
     * Метод инициализации перед каждым тестом.
     * Настраивает драйвер, сервисы и открывает базовую страницу.
     */
    @Before
    public void setUp() {
        initializeDriver();
        initializeServices();
        openBasePage();
    }

    /**
     * Метод завершения работы после каждого теста.
     * Закрывает браузер и освобождает ресурсы.
     */
    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private void initializeDriver() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments(
                "--window-size=1920,1080",
                "--no-sandbox",
                "--disable-dev-shm-usage"
        );
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICIT_WAIT_SECONDS));
    }

    private void initializeServices() {
        this.orderService = new UIOrderService(driver, EXPLICIT_WAIT_SECONDS);
        this.faqService = new UIFaqService(driver);
    }

    private void openBasePage() {
        driver.get(BASE_URL);
        new HomePage(driver).closeCookieBanner();
    }
}

// тестовый комментарий