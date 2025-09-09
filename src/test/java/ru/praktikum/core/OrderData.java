package ru.praktikum.core;

/**
 * Data Transfer Object (DTO) для передачи данных о заказе.
 * Содержит все поля, необходимые для создания заказа.
 * Используется для передачи данных между тестами и сервисами.
 */
public class OrderData {
    public String name;
    public String surname;
    public String address;
    public String metroStation;
    public String phone;
    public String deliveryDate;
    public String rentalPeriod;
    public String color;
    public String comment;

    public OrderData(String name, String surname, String address,
                     String metroStation, String phone, String deliveryDate,
                     String rentalPeriod, String color, String comment) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.deliveryDate = deliveryDate;
        this.rentalPeriod = rentalPeriod;
        this.color = color;
        this.comment = comment;
    }
}