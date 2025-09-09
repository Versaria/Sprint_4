package ru.praktikum.core;

/**
 * Интерфейс сервиса для работы с заказами.
 * Определяет абстракцию для создания и проверки заказов,
 * позволяя реализовать разные способы взаимодействия (UI, API и т.д.).

 * Преимущества использования:
 * 1. Абстракция - тесты не зависят от конкретной реализации (UI/API)
 * 2. Возможность легкого переключения между реализациями
 * 3. Упрощение тестирования через mock-объекты
 * 4. Соблюдение принципа DIP (Dependency Inversion Principle)
 * 5. Четкое разделение контракта и реализации
 * 6. Упрощение поддержки и расширения кода
 */
public interface OrderService {
    /**
     * Создает новый заказ с указанными данными.
     * @param data объект с данными для создания заказа
     * @param useTopButton true - использовать верхнюю кнопку, false - нижнюю
     * @return true если заказ успешно создан, false в случае ошибки
     * @throws IllegalArgumentException если данные заказа невалидны
     */
    boolean createOrder(OrderData data, boolean useTopButton);

    /**
     * Проверяет, подтвержден ли последний созданный заказ.
     * @return true если заказ подтвержден, false в противном случае
     */
    boolean isOrderConfirmed();

    /**
     * Возвращает количество ошибок валидации после попытки создания заказа.
     * @return количество обнаруженных ошибок валидации
     */
    int getValidationErrorsCount();

    /**
     * Проверяет статус несуществующего заказа.
     * @param orderId номер заказа для проверки
     * @return true если система корректно сообщает, что заказ не найден
     * @throws IllegalArgumentException если номер заказа невалиден
     */
    boolean checkInvalidOrderStatus(String orderId);

    /**
     * Устанавливает кастомный таймаут для ожиданий в сервисе.
     * @param seconds время ожидания в секундах
     * @throws IllegalArgumentException если значение меньше или равно 0
     */
    void setTimeout(int seconds);
}