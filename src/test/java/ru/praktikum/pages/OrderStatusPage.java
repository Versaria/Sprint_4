package ru.praktikum.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

/**
 * Page Object для страницы проверки статуса заказа.
 * Инкапсулирует взаимодействие с элементами страницы статуса заказа.
 */
public class OrderStatusPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // Локаторы элементов страницы
    private final By orderIdField = By.xpath("//input[@placeholder='Введите номер заказа']");
    private final By goButton = By.xpath("//button[text()='Go!']");
    private final By notFoundMessage = By.className("Track_NotFound__6oaoY");
    private final By scooterLogo = By.className("Header_LogoScooter__3lsAR");

    /**
     * Конструктор класса.
     * @param driver экземпляр WebDriver
     */
    public OrderStatusPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    /**
     * Проверяет статус заказа по номеру.
     * @param orderId номер заказа для проверки
     * @return текущий экземпляр OrderStatusPage
     * @throws IllegalArgumentException если orderId равен null или пуст
     */
    public OrderStatusPage checkOrderStatus(String orderId) {
        if (orderId == null || orderId.trim().isEmpty()) {
            throw new IllegalArgumentException("Order ID cannot be null or empty");
        }

        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(orderIdField));
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
                "arguments[0].value = arguments[1];",
                input,
                orderId
        );

        WebElement button = driver.findElement(goButton);
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView(); arguments[0].click();",
                button
        );
        return this;
    }

    /**
     * Проверяет отображение сообщения "Заказ не найден".
     * @return true если сообщение отображается, иначе false
     */
    public boolean isNotFoundDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(notFoundMessage))
                    .isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Нажимает на логотип Самоката для возврата на главную страницу.
     * @return экземпляр HomePage
     */
    public HomePage clickScooterLogo() {
        try {
            WebElement logo = wait.until(ExpectedConditions.elementToBeClickable(scooterLogo));
            logo.click();
            return new HomePage(driver);
        } catch (Exception e) {
            throw new RuntimeException("Failed to click scooter logo: " + e.getMessage(), e);
        }
    }
}