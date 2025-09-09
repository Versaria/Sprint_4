package ru.praktikum;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import ru.praktikum.core.FaqService;
import ru.praktikum.core.OrderService;
import ru.praktikum.pages.HomePage;
import ru.praktikum.services.ui.UIFaqService;
import ru.praktikum.services.ui.UIOrderService;
import java.time.Duration;

/**
 * Базовый класс для всех тестов.
 * Содержит общую логику инициализации и завершения работы тестов.
 * Инициализирует WebDriver, сервисы и открывает базовую страницу.
 */
public class BaseTest {
    protected WebDriver driver;
    protected OrderService orderService;
    protected FaqService faqService;
    protected HomePage homePage;
    // Конфигурация таймаутов
    private static final int IMPLICIT_WAIT_SECONDS = 5;
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

    /**
     * Инициализирует WebDriver в зависимости от выбранного браузера.
     * Поддерживает Chrome (по умолчанию) и Firefox.
     * Настройки браузера передаются через системное свойство "browser".
     */
    private void initializeDriver() {
        String browser = System.getProperty("browser", "chrome");

        switch (browser.toLowerCase()) {
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments("--width=1920");
                firefoxOptions.addArguments("--height=1080");
                driver = new FirefoxDriver(firefoxOptions);
                break;
            case "chrome":
            default:
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();
                options.addArguments(
                        "--window-size=1920,1080",
                        "--no-sandbox",
                        "--disable-dev-shm-usage"
                );
                driver = new ChromeDriver(options);
                break;
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICIT_WAIT_SECONDS));
    }

    /**
     * Инициализирует сервисы для работы с заказами и FAQ.
     * Сервисы используют паттерн Page Object для взаимодействия с UI.
     */
    private void initializeServices() {
        this.homePage = new HomePage(driver);
        this.orderService = new UIOrderService(driver, homePage);
        this.faqService = new UIFaqService(homePage);
    }

    /**
     * Открывает базовую страницу и закрывает баннер с куки.
     */
    private void openBasePage() {
        driver.get(BASE_URL);
        homePage.closeCookieBanner();
    }
}