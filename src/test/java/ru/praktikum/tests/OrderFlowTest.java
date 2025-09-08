package ru.praktikum.tests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.praktikum.BaseTest;
import ru.praktikum.core.OrderData;
import static org.junit.Assert.assertTrue;

/**
 * Параметризованный тест позитивного сценария заказа самоката.
 * Проверяет полный флоу оформления заказа с разными наборами данных и кнопками.
 */
@RunWith(Parameterized.class)
public class OrderFlowTest extends BaseTest {
    private final OrderData testData;
    private final boolean useTopButton;
    private final String testName;

    /**
     * Конструктор для параметризованного теста.
     * @param data Данные для тестового заказа
     * @param useTopButton true - использовать верхнюю кнопку, false - нижнюю
     * @param testName Название теста для отображения в отчетах
     */
    public OrderFlowTest(OrderData data, boolean useTopButton, String testName) {
        this.testData = data;
        this.useTopButton = useTopButton;
        this.testName = testName;
    }

    /**
     * Метод предоставляет тестовые данные для параметризованного теста.
     * @return Массив объектов с данными заказов и типом кнопки
     */
    @Parameterized.Parameters(name = "{2}")
    public static Object[][] getTestData() {
        return new Object[][] {
                // Первый набор данных: минимальные обязательные поля + черный цвет + верхняя кнопка
                {new OrderData(
                        "Иван", "Иванов", "Москва, Красная площадь", "Театральная",
                        "89031234567", "15.08.2025", "сутки", "black", ""
                ), true, "Заказ через верхнюю кнопку с минимальными данными"},

                // Второй набор данных: все поля заполнены + серый цвет + нижняя кнопка
                {new OrderData(
                        "Мария", "Петрова", "Санкт-Петербург, Невский проспект", "Невский проспект",
                        "+79101234567", "25.08.2025", "двое суток", "grey", "Комментарий"
                ), false, "Заказ через нижнюю кнопку со всеми данными"}
        };
    }

    /**
     * Тест проверяет успешное создание заказа с разными наборами данных через разные кнопки.
     * Увеличивает таймаут ожидания для этого теста.

     * Исправления:
     * 1. Один ассерт, заказ сформирован.
     * 2. Проверка не зависеть от браузера.
     */
    @Test
    public void testOrderCreationWithDifferentButtons() {
        // Увеличиваем таймаут только для этого теста (из-за возможных задержек)
        orderService.setTimeout(30); // секунд

        // Создаем заказ и проверяем результат
        boolean isCreated = orderService.createOrder(testData, useTopButton);
            // Ожидаем успешное создание заказа
            assertTrue("Заказ должен быть успешно создан для теста: " + testName, isCreated);
    }
}

// тестовый комментарий