# Тестирование веб-приложения "Яндекс Самокат" 🛴

![Java](https://img.shields.io/badge/Java-11%2B-blue)
![Selenium](https://img.shields.io/badge/Selenium-4.15.0-green)
![JUnit](https://img.shields.io/badge/JUnit-4.13.2-red)
![Maven](https://img.shields.io/badge/Maven-3.9.11-orange)

Проект содержит автоматизированные тесты веб-приложения "Яндекс Самокат". Тесты покрывают основные сценарии работы с сервисом, включая оформление заказа, проверку статуса заказа и взаимодействие с разделом FAQ.

## 🚀 Быстрый старт

### Требования
- Java JDK 11+
- Apache Maven 3.6+
- Git (для клонирования)
- Браузеры: Google Chrome и Mozilla Firefox
- ChromeDriver (совместимая версия с Chrome)

### Установка и запуск

```bash
# Клонирование репозитория
git clone git@github.com:Versaria/Sprint_4.git
cd scooter-tests
# Сборка проекта
mvn clean compile
# Запуск всех тестов в Chrome по умолчанию
mvn test
# Запуск тестов в Firefox
mvn test -Dbrowser=firefox
# Запуск конкретного тестового класса
mvn test -Dtest=AccordionTest
mvn test -Dtest=OrderFlowTest
mvn test -Dtest=AdditionalTest
```

## 📂 Структура проекта

```
src/test/java/ru/praktikum/
├── tests/                   # Тестовые классы
│   ├── AccordionTest.java   # Тесты раздела FAQ
│   ├── OrderFlowTest.java   # Тесты оформления заказа
│   └── AdditionalTest.java  # Дополнительные тесты
├── pages/                   # Page Object классы
│   ├── HomePage.java        # Главная страница
│   ├── OrderPage.java       # Страница оформления заказа
│   └── OrderStatusPage.java # Страница статуса заказа
├── core/                    # Базовые классы и интерфейсы
│   ├── OrderData.java       # DTO для данных заказа
│   ├── OrderService.java    # Интерфейс сервиса заказов
│   └── FaqService.java      # Интерфейс сервиса FAQ
├── services/ui/             # UI реализации сервисов
│   ├── UIOrderService.java  # Реализация сервиса заказов
│   └── UIFaqService.java    # Реализация сервиса FAQ
└──  BaseTest.java           # Базовый класс для тестов
```

## 📋 Функционал

### Технологический стек:
- Язык программирования: Java 11
- Фреймворк тестирования: JUnit 4
- Браузерная автоматизация: Selenium WebDriver 4.15.0
- Управление зависимостями: Maven
- Управление драйверами: WebDriverManager
- Паттерны проектирования: Page Object, Service Layer

### Основные тестовые сценарии:

1. **Тестирование раздела FAQ** (`AccordionTest`)
    - Проверка корректности текста ответов на все 8 вопросов
    - Параметризованное тестирование с различными наборами данных

2. **Тестирование оформления заказа** (`OrderFlowTest`)
    - Полный флоу позитивного сценария заказа
    - Тестирование через обе кнопки "Заказать" (верхняя и нижняя)
    - Два различных набора тестовых данных

3. **Дополнительные тесты** (`AdditionalTest`)
    - Валидация пустых полей формы
    - Проверка несуществующего заказа
    - Тестирование навигации через логотипы

## 📊 Покрытие тестами

Проект покрывает следующие функциональные области:
- ✅ Работа с выпадающим списком в разделе "Вопросы о важном"
- ✅ Полный флоу оформления заказа самоката
- ✅ Обработка ошибок валидации формы
- ✅ Навигация через логотипы Самоката и Яндекса
- ✅ Проверка статуса несуществующего заказа
- ⚠️ Примечание: В Chrome присутствует баг, который не позволяет оформить заказ. Тесты в Chrome могут падать из-за этого бага.

## 💻 Пример работы

```java
// Параметризованный тест FAQ
@Test
public void testFaqAnswerText() {
    faqService.openQuestion(questionIndex);
    String actualAnswer = faqService.getAnswer(questionIndex);
    assertEquals(expectedAnswer, actualAnswer);
}

// Тест оформления заказа
@Test
public void testOrderCreationWithDifferentButtons() {
    orderService.setTimeout(30);
    boolean isCreated = orderService.createOrder(testData, useTopButton);
    assertTrue("Заказ должен быть успешно создан", isCreated);
}
```

## 📜 Лицензия

MIT License. Полный текст доступен в файле [LICENSE](LICENSE).

## 🤝 Как внести вклад
1. Форкните репозиторий
2. Создайте ветку (`git checkout -b feature/new-dish`)
3. Сделайте коммит (`git commit -am 'Add new menu item'`)
4. Запушьте ветку (`git push origin feature/new-dish`)
5. Откройте Pull Request

---

<details>
<summary>🔧 Дополнительные команды</summary>

```bash
# Запуск тестов с отчетом
mvn test surefire-report:report

# Анализ кода
mvn pmd:pmd checkstyle:checkstyle
```
</details>

