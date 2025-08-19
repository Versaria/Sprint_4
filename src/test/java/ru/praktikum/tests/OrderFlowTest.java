package ru.praktikum.tests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.praktikum.BaseTest;
import ru.praktikum.core.OrderData;
import static org.junit.Assert.assertTrue;

/**
 * Параметризованный тест позитивного сценария заказа самоката.
 * Проверяет полный флоу оформления заказа с разными наборами данных.
 * Демонстрирует принцип "тесты не зависят от способа взаимодействия" -
 * использует только интерфейс OrderService.
 */
@RunWith(Parameterized.class)
public class OrderFlowTest extends BaseTest {
    private final OrderData testData;

    /**
     * Конструктор для параметризованного теста.
     * @param data Данные для тестового заказа
     */
    public OrderFlowTest(OrderData data) {
        this.testData = data;
    }

    /**
     * Метод предоставляет тестовые данные для параметризованного теста.
     * @return Массив объектов OrderData с разными наборами данных
     */
    @Parameterized.Parameters(name = "Заказ для {0} {1}")
    public static Object[][] getTestData() {
        return new Object[][] {
                // Первый набор данных: минимальные обязательные поля + черный цвет
                {new OrderData(
                        "Иван", "Иванов", "Москва, Красная площадь", "Театральная",
                        "89031234567", "15.08.2025", "сутки", "black", ""
                )},
                // Второй набор данных: все поля заполнены + серый цвет
                {new OrderData(
                        "Мария", "Петрова", "Санкт-Петербург, Невский проспект", "Невский проспект",
                        "+79101234567", "25.08.2025", "двое суток", "grey", "Комментарий"
                )}
        };
    }

    /**
     * Тест проверяет успешное создание заказа с разными наборами данных.
     * Увеличивает таймаут ожидания для этого теста.
     */
    @Test
    public void shouldCreateOrderSuccessfully() {
        // Увеличиваем таймаут только для этого теста (из-за возможных задержек)
        orderService.setTimeout(30); // секунд

        // Создаем заказ и проверяем результат
        boolean isCreated = orderService.createOrder(testData);
        assertTrue("Заказ должен быть успешно создан", isCreated);
    }
}

// тестовый комментарий