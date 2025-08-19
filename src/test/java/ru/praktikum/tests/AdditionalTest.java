package ru.praktikum.tests;

import org.junit.Test;
import org.openqa.selenium.TimeoutException;
import ru.praktikum.BaseTest;
import ru.praktikum.core.OrderData;
import ru.praktikum.pages.HomePage;

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
     * 2. Пытаемся создать заказ
     * 3. Проверяем количество ошибок валидации
     */
    @Test
    public void shouldShowValidationErrorsForEmptyFields() {
        // Увеличиваем таймаут для этого теста
        orderService.setTimeout(30);

        // Создаем данные с пустыми полями
        OrderData emptyData = new OrderData("", "", "", null, "", "", "", "", "");

        try {
            // Пытаемся создать заказ
            orderService.createOrder(emptyData);
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
    public void shouldShowNotFoundForInvalidOrder() {
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
    public void shouldReturnToMainPageWhenClickScooterLogo() {
        String currentUrl = new HomePage(driver)
                .clickOrderStatusButton()
                .clickScooterLogo()
                .getCurrentUrl();

        assertEquals("Должны быть на главной странице после клика на логотип Самоката",
                "https://qa-scooter.praktikum-services.ru/", currentUrl);
    }

    /**
     * Тест проверяет, что клик по логотипу Яндекса открывает новую вкладку с Дзеном.
     * Шаги:
     * 1. Кликаем по логотипу Яндекса
     * 2. Переключаемся на новую вкладку
     * 3. Проверяем URL
     * 4. Закрываем новую вкладку
     */
    @Test
    public void shouldOpenYandexPageWhenClickYandexLogo() {
        String yandexUrl = new HomePage(driver)
                .clickYandexLogoAndSwitchToNewWindow();

        assertTrue("URL должен содержать dzen.ru или yandex.ru, но получен: " + yandexUrl,
                yandexUrl.contains("dzen.ru") || yandexUrl.contains("yandex.ru"));
    }
}

// тестовый комментарий