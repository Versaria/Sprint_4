package ru.praktikum.core;

/**
 * Интерфейс для работы с разделом FAQ (Часто задаваемые вопросы).
 * Определяет контракт для взаимодействия с вопросами и ответами в UI.
 */
public interface FaqService {
    /**
     * Открывает вопрос по указанному индексу.
     * @param index порядковый номер вопроса (должен быть в диапазоне 0-7)
     * @throws IllegalArgumentException если индекс выходит за допустимые пределы
     */
    void openQuestion(int index);

    /**
     * Получает текст ответа на вопрос.
     * @param index порядковый номер вопроса (должен быть в диапазоне 0-7)
     * @return текст ответа на вопрос
     * @throws IllegalArgumentException если индекс выходит за допустимые пределы
     * @throws IllegalStateException если ответ не найден или не отображается
     */
    String getAnswer(int index);
}