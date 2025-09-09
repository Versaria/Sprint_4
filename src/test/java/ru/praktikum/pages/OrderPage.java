package ru.praktikum.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

/**
 * Page Object для страницы оформления заказа.
 * Содержит методы для работы с формой заказа.
 */
public class OrderPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // Локаторы элементов формы заказа
    private final By nameField = By.xpath("//input[@placeholder='* Имя']");
    private final By surnameField = By.xpath("//input[@placeholder='* Фамилия']");
    private final By addressField = By.xpath("//input[@placeholder='* Адрес: куда привезти заказ']");
    private final By metroField = By.xpath("//input[@placeholder='* Станция метро']");
    private final By phoneField = By.xpath("//input[@placeholder='* Телефон: на него позвонит курьер']");
    private final By nextButton = By.xpath("//button[text()='Далее']");
    private final By dateField = By.xpath("//input[@placeholder='* Когда привезти самокат']");
    private final By rentalPeriod = By.className("Dropdown-placeholder");
    private final By colorBlack = By.id("black");
    private final By colorGrey = By.id("grey");
    private final By commentField = By.xpath("//input[@placeholder='Комментарий для курьера']");
    private final By orderButton = By.xpath("//button[contains(@class, 'Button_Middle') and text()='Заказать']");
    private final By confirmButton = By.xpath("//button[text()='Да']");
    private final By successTitle = By.className("Order_ModalHeader__3FDaJ");
    private final By validationErrors = By.cssSelector(".Input_ErrorMessage__3HvIb");

    // Локаторы для периода аренды
    private static final String RENTAL_PERIOD_OPTION = "//div[text()='%s']";

    /**
     * Конструктор класса.
     * @param driver экземпляр WebDriver
     */
    public OrderPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    /**
     * Заполняет первую страницу формы заказа.
     * @param name Имя
     * @param surname Фамилия
     * @param address Адрес
     * @param metro Станция метро
     * @param phone Телефон
     */
    public void fillFirstPage(String name, String surname, String address, String metro, String phone) {
        setFieldValue(nameField, name);
        setFieldValue(surnameField, surname);
        setFieldValue(addressField, address);

        if (metro != null && !metro.isEmpty()) {
            selectMetroStation(metro);
        }

        setFieldValue(phoneField, phone);
        clickNextButton();
    }

    /**
     * Заполняет вторую страницу формы заказа.
     * @param date Дата доставки
     * @param period Период аренды
     * @param color Цвет самоката
     * @param comment Комментарий
     */
    public void fillSecondPage(String date, String period, String color, String comment) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[text()='Про аренду']")));

        setDate(date);
        selectRentalPeriod(period);
        selectColor(color);
        setComment(comment);
        clickOrderButton();
        confirmOrder();
    }

    /**
     * Проверяет отображение модального окна успешного заказа.
     * @return true если окно отображается, иначе false
     */
    public boolean isSuccessModalDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(successTitle))
                    .isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Получает количество ошибок валидации.
     * @return количество отображаемых ошибок
     */
    public int getValidationErrorsCount() {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(validationErrors));
            return driver.findElements(validationErrors).size();
        } catch (TimeoutException e) {
            return 0;
        }
    }

    /**
     * Выбирает станцию метро из выпадающего списка.
     * Использует String.format для формирования динамического локатора.
     */
    private void selectMetroStation(String stationName) {
        WebElement metroInput = driver.findElement(metroField);
        metroInput.click();
        metroInput.clear();
        metroInput.sendKeys(stationName);

        String xpath = String.format(
                "//div[contains(@class, 'select-search__select')]//*[contains(text(), '%s')]",
                stationName
        );

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".select-search__select")));

        List<WebElement> options = driver.findElements(By.xpath(xpath));
        if (!options.isEmpty()) {
            options.get(0).click();
        } else {
            driver.findElements(By.cssSelector(".select-search__option"))
                    .stream()
                    .findFirst()
                    .ifPresent(WebElement::click);
        }
    }

    private void clickNextButton() {
        wait.until(ExpectedConditions.elementToBeClickable(nextButton)).click();
    }

    private void setDate(String date) {
        WebElement dateElement = driver.findElement(dateField);
        dateElement.clear();
        dateElement.sendKeys(date, Keys.ENTER);
    }

    /**
     * Выбирает период аренды из выпадающего списка.
     * Использует String.format для формирования динамического локатора.
     */
    private void selectRentalPeriod(String period) {
        driver.findElement(rentalPeriod).click();

        String periodOptionXpath = String.format(RENTAL_PERIOD_OPTION, period);
        driver.findElement(By.xpath(periodOptionXpath)).click();
    }

    private void selectColor(String color) {
        if ("black".equals(color)) {
            driver.findElement(colorBlack).click();
        } else if ("grey".equals(color)) {
            driver.findElement(colorGrey).click();
        }
    }

    private void setComment(String comment) {
        setFieldValue(commentField, comment);
    }

    private void clickOrderButton() {
        WebElement button = driver.findElement(orderButton);
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView();", button);
        button.click();
    }

    private void confirmOrder() {
        wait.until(ExpectedConditions.elementToBeClickable(confirmButton)).click();
    }

    private void setFieldValue(By locator, String value) {
        if (value != null && !value.isEmpty()) {
            WebElement element = driver.findElement(locator);
            element.clear();
            element.sendKeys(value);
        }
    }
}