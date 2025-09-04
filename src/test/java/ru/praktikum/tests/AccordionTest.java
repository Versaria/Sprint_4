package ru.praktikum.tests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.praktikum.BaseTest;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Тесты для раздела "Вопросы о важном" (FAQ).
 * Проверяют корректность работы аккордеона с вопросами.

 * Исправления:
 * 1. Добавлена параметризация для независимого тестирования каждого вопроса
 * 2. Добавлена проверка конкретного текста ответа, а не только на пустоту
 */
@RunWith(Parameterized.class)
public class AccordionTest extends BaseTest {
    private final int questionIndex;
    private final String expectedAnswer;

    /**
     * Конструктор для параметризованного теста.
     * @param questionIndex индекс вопроса (0-7)
     * @param expectedAnswer ожидаемый текст ответа
     */
    public AccordionTest(int questionIndex, String expectedAnswer) {
        this.questionIndex = questionIndex;
        this.expectedAnswer = expectedAnswer;
    }

    /**
     * Метод предоставляет тестовые данные для параметризованного теста.
     * @return Массив с индексами вопросов и ожидаемыми ответами
     */
    @Parameterized.Parameters(name = "Вопрос {0}")
    public static Object[][] getTestData() {
        return new Object[][] {
                {0, "Сутки — 400 рублей. Оплата курьеру — наличными или картой."},
                {1, "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим."},
                {2, "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30."},
                {3, "Только начиная с завтрашнего дня. Но скоро станем расторопнее."},
                {4, "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010."},
                {5, "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится."},
                {6, "Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои."},
                {7, "Да, обязательно. Всем самокатов! И Москве, и Московской области."}
        };
    }

    /**
     * Тест проверяет, что ответ на вопрос FAQ соответствует ожидаемому тексту.
     * Каждый вопрос проверяется независимо благодаря параметризации.

     * Исправления:
     * 1. Добавлена проверка конкретного текста ответа, а не только на пустоту
     * 2. Тест теперь параметризованный, каждый вопрос проверяется независимо
     */
    @Test
    public void testFaqAnswerText() {
        // Проверка инициализации faqService
        assertNotNull("FaqService должен быть инициализирован", faqService);

        // Открываем вопрос
        faqService.openQuestion(questionIndex);

        // Получаем текст ответа
        String actualAnswer = faqService.getAnswer(questionIndex);

        // Проверяем, что ответ соответствует ожидаемому тексту
        assertEquals("Текст ответа на вопрос " + (questionIndex + 1) + " не совпадает",
                expectedAnswer, actualAnswer);
    }
}

// тестовый комментарий