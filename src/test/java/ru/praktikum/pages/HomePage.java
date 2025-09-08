package ru.praktikum.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;
import java.util.Set;

/**
 * Page Object для главной страницы Яндекс. Самоката.
 * Содержит методы для взаимодействия со всеми элементами главной страницы.
 */
public class HomePage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // Локаторы элементов
    private final By cookieBanner = By.className("App_CookieConsent__1yUIN");
    private final By cookieConfirmButton = By.id("rcc-confirm-button");
    private final By orderTopButton = By.xpath(".//button[text()='Заказать' and contains(@class, 'Button_Button__ra12g')]");
    private final By orderBottomButton = By.xpath(".//div[contains(@class, 'Home_FinishButton')]/button[text()='Заказать']");
    private final By yandexLogo = By.className("Header_LogoYandex__3TSOI");
    private final By orderStatusButton = By.xpath(".//button[text()='Статус заказа']");
    private final By faqQuestions = By.cssSelector("[id^='accordion__heading-']");

    /**
     * Конструктор класса HomePage.
     * @param driver экземпляр WebDriver
     */
    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    /**
     * Закрывает баннер с куки, если он отображается.
     */
    public void closeCookieBanner() {
        try {
            List<WebElement> banners = wait.until(
                    ExpectedConditions.presenceOfAllElementsLocatedBy(cookieBanner));
            if (!banners.isEmpty() && banners.get(0).isDisplayed()) {
                wait.until(ExpectedConditions.elementToBeClickable(cookieConfirmButton)).click();
            }
        } catch (Exception e) {
            System.out.println("Cookie banner not found or already closed: " + e.getMessage());
        }
    }

    /**
     * Нажимает кнопку "Заказать".
     * @param isTopButton true - верхняя кнопка, false - нижняя
     * @return экземпляр OrderPage
     */
    public OrderPage clickOrderButton(boolean isTopButton) {
        By locator = isTopButton ? orderTopButton : orderBottomButton;
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(locator));
        scrollToElement(button);
        button.click();
        return new OrderPage(driver);
    }

    /**
     * Нажимает на логотип Яндекса.
     */
    public void clickYandexLogo() {
        WebElement logo = wait.until(ExpectedConditions.elementToBeClickable(yandexLogo));
        scrollToElement(logo);
        logo.click();
    }

    /**
     * Нажимает кнопку "Статус заказа".
     * @return экземпляр OrderStatusPage
     */
    public OrderStatusPage clickOrderStatusButton() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(orderStatusButton));
        scrollToElement(button);
        button.click();
        return new OrderStatusPage(driver);
    }

    /**
     * Открывает вопрос в FAQ по индексу.
     * @param index индекс вопроса (0-7)
     */
    public void openFaqQuestion(int index) {
        List<WebElement> questions = wait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(faqQuestions));

        if (index >= 0 && index < questions.size()) {
            try {
                scrollAndClick(questions.get(index));
            } catch (StaleElementReferenceException e) {
                questions = driver.findElements(faqQuestions);
                scrollAndClick(questions.get(index));
            }
        }
    }

    /**
     * Получает текст ответа на вопрос FAQ.
     * @param index индекс вопроса (0-7)
     * @return текст ответа
     */
    public String getFaqAnswer(int index) {
        WebElement answer = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("accordion__panel-" + index)));
        return answer.getText().trim();
    }

    /**
     * Получает текущий URL страницы.
     * @return текущий URL
     */
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    /**
     * Получает handle текущего окна.
     * @return handle текущего окна
     */
    // Добавлены методы для работы с окнами
    public String getCurrentWindowHandle() {
        return driver.getWindowHandle();
    }

    /**
     * Переключается на указанное окно.
     * @param windowHandle handle окна для переключения
     */
    public void switchToWindow(String windowHandle) {
        driver.switchTo().window(windowHandle);
    }

    /**
     * Закрывает текущее окно.
     */
    public void closeCurrentWindow() {
        driver.close();
    }

    /**
     * Ожидает открытия нового окна.
     * @param expectedWindowCount ожидаемое количество окон
     */
    public void waitForNewWindow(int expectedWindowCount) {
        wait.until(driver -> driver.getWindowHandles().size() == expectedWindowCount);
    }

    /**
     * Переключается на новое окно (отличное от текущего).
     */
    public void switchToNewWindow() {
        String currentWindow = driver.getWindowHandle();
        Set<String> windows = driver.getWindowHandles();

        for (String window : windows) {
            if (!window.equals(currentWindow)) {
                driver.switchTo().window(window);
                break;
            }
        }
    }

    // Вспомогательные методы
    private void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center', behavior: 'smooth'});", element);
    }

    private void scrollAndClick(WebElement element) {
        scrollToElement(element);
        element.click();
    }
}

// тестовый комментарий