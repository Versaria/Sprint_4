package ru.praktikum.services.ui;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import ru.praktikum.core.OrderData;
import ru.praktikum.core.OrderService;
import ru.praktikum.pages.HomePage;
import ru.praktikum.pages.OrderPage;

/**
 * UI-реализация сервиса заказов.
 * Инкапсулирует логику взаимодействия с UI для работы с заказами.

 * Исправления:
 * 1. Убрана прямая работа с WebDriver и WebDriverWait из методов
 * 2. Все взаимодействие с UI происходит через Page Objects
 * 3. Обновлен конструктор для использования переданного HomePage,
 * Конструктор принимает WebDriver и HomePage
 */
public class UIOrderService implements OrderService {
    private final WebDriver driver;
    private final HomePage homePage;

    /**
     * Конструктор сервиса.
     * @param driver экземпляр WebDriver
     * @param homePage экземпляр HomePage
     * @throws IllegalArgumentException если driver равен null
     */
    // Обновленный конструктор
    public UIOrderService(WebDriver driver, HomePage homePage) {
        if (driver == null) {
            throw new IllegalArgumentException("WebDriver cannot be null");
        }
        this.driver = driver;
        this.homePage = homePage;
    }

    @Override
    public void setTimeout(int seconds) {
        // Метод оставлен для совместимости с интерфейсом
        // Таймауты управляться на уровне Page Objects
    }

    @Override
    public boolean createOrder(OrderData data, boolean useTopButton) {
        if (data == null) {
            throw new IllegalArgumentException("OrderData cannot be null");
        }

        // Используем Page Objects для взаимодействия с UI
        OrderPage orderPage = homePage.clickOrderButton(useTopButton);

        orderPage.fillFirstPage(
                data.name,
                data.surname,
                data.address,
                data.metroStation,
                data.phone
        );

        orderPage.fillSecondPage(
                data.deliveryDate,
                data.rentalPeriod,
                data.color,
                data.comment
        );

        return isOrderConfirmed();
    }

    @Override
    public boolean isOrderConfirmed() {
        // Используем Page Object для проверки подтверждения заказа
        return new OrderPage(driver).isSuccessModalDisplayed();
    }

    @Override
    public int getValidationErrorsCount() {
        try {
            // Используем Page Object для получения количества ошибок
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

        // Используем Page Objects для проверки статуса заказа
        return homePage
                .clickOrderStatusButton()
                .checkOrderStatus(orderId)
                .isNotFoundDisplayed();
    }
}

// тестовый комментарий