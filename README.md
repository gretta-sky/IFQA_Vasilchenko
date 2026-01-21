<h1 align="center">Автотестирование сайта <a href="https://jira.atlassian.com/" target="_blank">Jira</a> 
<img src="https://images.icon-icons.com/2429/PNG/512/jira_logo_icon_147274.png" height="36"/>
</h1>
<h3 align="center">Учебный проект по автоматизированному тестирования веб-приложения Jira с использованием Java, Selenide, JUnit 5 и Allure.</h3>
<hr>
<h3 align="center">Технологический стек</h3>
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
`mvn clean
```
<hr>
<h3 align="center">Структура отчета Allure:</h3>
<h3 align="center"><li>Overview - общая статистика по прогону</li>
<li>Behaviors - группировка по эпикам и фичам</li>
<li>Suites - список тестовых наборов</li>
<li>Graphs - графики и диаграммы</li>
<li>Timeline - временная шкала выполнения</li></h3>
<hr>
<h3 align="center">Возможные проблемы</h3>
<h3 align="center"><li>"No compiler is provided in this environment"</li>
1.Установите JDK 17 (не JRE)
 
2.Проверьте JAVA_HOME переменную среды
 <li>Тесты не видят Page Objects</li>
1.Выполните mvn clean compile test-compile

2.Перезапустите IntelliJ IDEA
</h3>
<hr>
<h4 align="center">Авторы:</h4>
<h4 align="center"><a href="https://github.com/gretta-sky" target="_blank">[Васильченко Маргарита]</a>  - mm8595284@mail.ru
</h4>
<h5>Последнее обновление README: январь 2026го</h5>
<hr>
