package ru.praktikum.services.ui;

import ru.praktikum.core.FaqService;
import ru.praktikum.pages.HomePage;

/**
 * UI-реализация сервиса для работы с FAQ.
 * Использует Page Object HomePage для взаимодействия с элементами страницы.
 */
public class UIFaqService implements FaqService {
    private final HomePage homePage;

    /**
     * Конструктор сервиса.
     * @param homePage экземпляр HomePage для взаимодействия с браузером
     */
    public UIFaqService(HomePage homePage) {
        this.homePage = homePage;
    }

    @Override
    public void openQuestion(int index) {
        validateIndex(index);
        homePage.openFaqQuestion(index);
    }

    @Override
    public String getAnswer(int index) {
        validateIndex(index);
        return homePage.getFaqAnswer(index);
    }

    /**
     * Проверяет корректность индекса вопроса.
     * @param index проверяемый индекс
     * @throws IllegalArgumentException если индекс вне допустимого диапазона
     */
    private void validateIndex(int index) {
        if (index < 0 || index > 7) {
            throw new IllegalArgumentException("FAQ index must be between 0 and 7");
        }
    }
}