package ru.praktikum.tests;

import org.junit.Test;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.praktikum.BaseTest;
import ru.praktikum.core.OrderData;
import ru.praktikum.pages.HomePage;
import java.time.Duration;
import java.util.Set;
import static org.junit.Assert.*;

/**
 * Дополнительные тесты, проверяющие различные сценарии:
 * - валидация пустых полей формы
 * - проверка несуществующего заказа
 * - работа с логотипами Самоката и Яндекса
 */
public class AdditionalTest extends BaseTest {

    /**
     * Тест проверяет отображение ошибок валидации при пустых полях формы.
     * Шаги:
     * 1. Создаем данные с пустыми полями
     * 2. Пытаемся создать заказ через верхнюю кнопку
     * 3. Проверяем количество ошибок валидации

     * Исправления:
     * 2. Переименуем методы should в tes и доработаем
     * 1. В методе testShowValidationErrorsForEmptyFields() добавлен второй параметр true
     * при вызове orderService.createOrder(), чтобы указать использование верхней кнопки
     */
    @Test
    public void testShowValidationErrorsForEmptyFields() {
        // Увеличиваем таймаут для этого теста
        orderService.setTimeout(30);

        // Создаем данные с пустыми полями
        OrderData emptyData = new OrderData("", "", "", null, "", "", "", "", "");

        try {
            // Пытаемся создать заказ через верхнюю кнопку (true)
            orderService.createOrder(emptyData, true);
        } catch (TimeoutException e) {
            System.out.println("Timeout occurred, but continuing with validation check");
        }

        // Проверяем количество ошибок валидации
        int errorCount = orderService.getValidationErrorsCount();
        assertTrue("Ожидается минимум 5 ошибок валидации, но получено " + errorCount,
                errorCount >= 5);
    }

    /**
     * Тест проверяет отображение сообщения "Не найдено" для несуществующего заказа.
     * Шаги:
     * 1. Проверяем статус заказа с несуществующим номером
     * 2. Проверяем, что система сообщает "Не найдено"
     */
    @Test
    public void testShowNotFoundForInvalidOrder() {
        boolean isNotFound = orderService.checkInvalidOrderStatus("000000");
        assertTrue("Для несуществующего заказа должно отображаться 'Не найдено'", isNotFound);
    }

    /**
     * Тест проверяет, что клик по логотипу Самоката возвращает на главную страницу.
     * Шаги:
     * 1. Переходим на страницу статуса заказа
     * 2. Кликаем по логотипу Самоката
     * 3. Проверяем URL главной страницы
     */
    @Test
    public void testReturnToMainPageWhenClickScooterLogo() {
        String currentUrl = new HomePage(driver)
                .clickOrderStatusButton()
                .clickScooterLogo()
                .getCurrentUrl();

        assertEquals("Должны быть на главной странице после клика на логотип Самоката",
                "https://qa-scooter.praktikum-services.ru/", currentUrl);
    }

    /**
     * Тест проверяет, что клик по логотипу Яндекса пытается открыть новую вкладку.
     * ВНИМАНИЕ: Этот тест может падать в некоторых окружениях из-за блокировок
     * или особенностей браузера. Основная цель - убедиться, что происходит
     * попытка открытия новой вкладки.
     */
    @Test
    public void testOpenYandexPageWhenClickYandexLogo() {
        HomePage homePage = new HomePage(driver);

        // Запоминаем текущее количество вкладок
        int initialWindowCount = driver.getWindowHandles().size();

        homePage.clickYandexLogo();

        try {
            new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.numberOfWindowsToBe(initialWindowCount + 1));

            // Закрываем новую вкладку и возвращаемся
            String mainWindow = driver.getWindowHandle();
            Set<String> windows = driver.getWindowHandles();
            for (String window : windows) {
                if (!window.equals(mainWindow)) {
                    driver.switchTo().window(window);
                    driver.close();
                    driver.switchTo().window(mainWindow);
                    break;
                }
            }
        } catch (TimeoutException e) {
            fail("После клика на логотип Яндекса должна открыться новая вкладка");
        }
    }
}

// тестовый комментарий