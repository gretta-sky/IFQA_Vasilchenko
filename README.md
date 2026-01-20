<h1 align="center">Автотесты сайта <a href="https://jira.atlassian.com/" target="_blank">Jira</a> 
<img src="https://images.icon-icons.com/2429/PNG/512/jira_logo_icon_147274.png" height="36"/>
</h1>
<h3 align="center">Учебный проект по автоматизированному тестирования веб-приложения Jira с использованием Java, Selenide, JUnit 5 и Allure.</h3>
<hr>
<h2 align="center">Технологический стек</h2>
<h3 align="center"><li>
Java 17 - язык программирования</li>

<li>Maven - система сборки</li>

<li>Selenide 7.4.2 - фреймворк для автоматизации веб-тестов</li>

<li>JUnit 5 - фреймворк для unit-тестирования</li>

<li>Allure 2.25.0 - система отчетности</li>

<li>WebDriverManager - автоматическое управление драйверами</li>

<li>AspectJ 1.9.24 - для интеграции Allure</li></h3>
<hr>
<h3 align="center">Предварительные требования</h3>
<h3 align="center"><li>
 Java 17 JDK - <a href="https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html" target="_blank">скачать</a> </li>
<li>Maven 3.6+ - <a href="https://maven.apache.org/download.cgi" target="_blank">скачать</a></li></h3>
<hr>
<h3 align="center">Настройка проекта</h3>
<h3 align="center"><li>
Клонируйте репозиторий</li>
<li>Импортируйте в IntelliJIDEA</li></h3>
<hr>
<h3 align="center">Запуск тестов</h3>
```bash
# Очистка и запуск всех тестов
`mvn clean test`
# Сгенерировать отчёт из результатов
`mvn allure:report`
# Сгенерировать и открыть отчёт в браузере
`mvn allure:serve`
# Очистить результаты предыдущих запусков
`mvn clean````
