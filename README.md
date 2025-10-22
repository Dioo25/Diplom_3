# Stellar Burgers — UI tests (JUnit4 + Selenium + Allure)


- Тесты регистрации, входа и конструктора (булки/соусы/начинки).
- Page Object-ы для основных страниц.
- Запуск в Chrome и Yandex Browser
- Allure отчёты.

 Предварительные требования
- Java 11+
- Maven
 Запуск тестов

 Запуск в Google Chrome (по умолчанию)
bash
mvn clean test -Dbrowser=chrome


 Запуск в Yandex Browser

bash
mvn clean test -Dbrowser=yandex 


Аllure отчет
После запуска тестов в `target/allure-results` появятся результаты.

Чтобы посмотреть отчёт локально:
bash
allure serve target/allure-results

