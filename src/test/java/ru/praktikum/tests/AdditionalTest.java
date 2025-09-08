package ru.praktikum.tests;

import org.junit.Test;
import org.openqa.selenium.TimeoutException;
import ru.praktikum.BaseTest;
import ru.praktikum.core.OrderData;
import static org.junit.Assert.*;

/**
 * Дополнительные тесты, проверяющие различные сценарии:
 * - валидация пустых полей формы
 * - проверка несуществующего заказа
 * - работа с логотипами Самоката и Яндекса

 * Исправления:
 * Использует только готовые методы Page Objects.
 * Нет прямого взаимодействия с WebDriver/WebDriverWait.
 * Все локаторы и методы работы с элементами вынесены в Page Objects.
 */
public class AdditionalTest extends BaseTest {

    /**
     * Тест проверяет отображение ошибок валидации при пустых полях формы.
     * Шаги:
     * 1. Создаем данные с пустыми полями
     * 2. Пытаемся создать заказ через верхнюю кнопку
     * 3. Проверяем количество ошибок валидации
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
        String currentUrl = homePage
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
        String mainWindow = homePage.getCurrentWindowHandle();
        homePage.clickYandexLogo();

        // Ждем появления нового окна
        homePage.waitForNewWindow(2);

        // Переключаемся на новое окно
        homePage.switchToNewWindow();

        // Проверяем, что переключились на другое окно
        assertNotEquals("Должно открыться новое окно", mainWindow, homePage.getCurrentWindowHandle());

        // Закрываем новое окно и возвращаемся
        homePage.closeCurrentWindow();
        homePage.switchToWindow(mainWindow);
    }
}

// тестовый комментарий