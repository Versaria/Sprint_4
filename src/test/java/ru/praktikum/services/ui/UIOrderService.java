package ru.praktikum.services.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.praktikum.core.OrderService;
import ru.praktikum.core.OrderData;
import ru.praktikum.pages.HomePage;
import ru.praktikum.pages.OrderPage;
import org.openqa.selenium.WebDriver;
import java.time.Duration;

/**
 * UI-реализация сервиса заказов.
 * Инкапсулирует логику взаимодействия с UI для работы с заказами.
 */
public class UIOrderService implements OrderService {
    private WebDriverWait wait;
    private final WebDriver driver;

    /**
     * Конструктор с настраиваемым таймаутом ожидания.
     * @param driver экземпляр WebDriver
     * @param timeoutSeconds таймаут ожидания в секундах
     * @throws IllegalArgumentException если driver равен null или timeout <= 0
     */
    public UIOrderService(WebDriver driver, int timeoutSeconds) {
        if (driver == null) {
            throw new IllegalArgumentException("WebDriver cannot be null");
        }
        if (timeoutSeconds <= 0) {
            throw new IllegalArgumentException("Timeout must be positive");
        }
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
    }

    /**
     * Конструктор с таймаутом по умолчанию (15 секунд).
     * @param driver экземпляр WebDriver
     */
    public UIOrderService(WebDriver driver) {
        this(driver, 15);
    }

    @Override
    public void setTimeout(int seconds) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
    }

    @Override
    public boolean createOrder(OrderData data) {
        if (data == null) {
            throw new IllegalArgumentException("OrderData cannot be null");
        }

        new HomePage(driver)
                .clickOrderButton(true)
                .fillFirstPage(
                        data.name,
                        data.surname,
                        data.address,
                        data.metroStation,
                        data.phone
                )
                .fillSecondPage(
                        data.deliveryDate,
                        data.rentalPeriod,
                        data.color,
                        data.comment
                );
        return isOrderConfirmed();
    }

    @Override
    public boolean isOrderConfirmed() {
        return new OrderPage(driver).isSuccessModalDisplayed();
    }

    @Override
    public int getValidationErrorsCount() {
        try {
            setTimeout(30);
            wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.cssSelector(".Input_ErrorMessage__3HvIb")));
            return new OrderPage(driver).getValidationErrorsCount();
        } catch (TimeoutException e) {
            return 0;
        }
    }

    @Override
    public boolean checkInvalidOrderStatus(String orderId) {
        if (orderId == null || orderId.trim().isEmpty()) {
            throw new IllegalArgumentException("Order ID cannot be null or empty");
        }

        return new HomePage(driver)
                .clickOrderStatusButton()
                .checkOrderStatus(orderId)
                .isNotFoundDisplayed();
    }
}

// тестовый комментарий