package ru.praktikum.tests;

import org.junit.Test;
import ru.praktikum.BaseTest;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

/**
 * Тесты для раздела "Вопросы о важном" (FAQ).
 * Проверяют корректность работы аккордеона с вопросами:
 * - что каждый вопрос можно открыть
 * - что каждый ответ содержит непустой текст
 */
public class AccordionTest extends BaseTest {
    /**
     * Тест проверяет, что все вопросы в разделе FAQ имеют непустые ответы.
     * Проходит по всем 8 вопросам, открывает их и проверяет текст ответа.
     */
    @Test
    public void testAllFaqItemsHaveNonEmptyAnswers() {
        // Проверка инициализации faqService
        assertNotNull("FaqService должен быть инициализирован", faqService);

        // Проверка всех 8 вопросов (индексы 0-7)
        for (int i = 0; i < 8; i++) {
            // Открываем вопрос
            faqService.openQuestion(i);

            // Получаем текст ответа
            String answer = faqService.getAnswer(i);

            // Проверяем, что ответ не null и не пустой
            assertNotNull("Ответ на вопрос " + (i + 1) + " не должен быть null", answer);
            assertFalse("Ответ на вопрос " + (i + 1) + " не должен быть пустым",
                    answer.isEmpty());
        }
    }
}

// тестовый комментарий