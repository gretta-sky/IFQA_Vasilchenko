<h1 align="center">Rick and Morty <a href="https://rickandmortyapi.com/api" target="_blank">API</a> Testing Framework
<img src="https://img.icons8.com/plasticine/1200/morty-smith.jpg" height="36"/>
</h1>
<h3 align="center">Фреймворк для автоматизированного тестирования REST API сервисов Rick and Morty и системы авторизации, реализованный на Java с использованием современных инструментов тестирования.</h3>
<hr>
<h3 align="center">Основные возможности</h3>
<h3 align="center"><li>
API тестирование Rick and Morty (публичное API)</li>

<li>API тестирование системы авторизации (локальный сервер)</li>

<li>Интеграция с Allure для красивых отчетов</li>

<li>BDD подход с использованием Cucumber</li>

<li>Параллельный запуск тестов</li></h3>
<hr>
<h3 align="center">Технологический стек</h3>
<h3 align="center"><li>
Java 17 - язык программирования</li>

<li>Maven - система сборки</li>

<li>Selenide 7.4.2 - фреймворк для автоматизации веб-тестов</li>

<li>JUnit 5 - фреймворк для unit-тестирования</li>

<li>Allure 2.25.0 - система отчетности</li>

<li>REST Assured 5.4.0 - для HTTP запросов</li>

<li>Cucumber 7.15.0 - BDD фреймворк</li>

<li>SLF4J + Simple - логирование</li>

<li>AspectJ 1.9.24 - для интеграции Allure</li></h3>
<hr>
<h3 align="center">Предварительные требования</h3>
<h3 align="center"><li>
 Java 17 JDK - <a href="https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html" target="_blank">скачать</a> </li>
<li>Maven 3.6+ - <a href="https://maven.apache.org/download.cgi" target="_blank">скачать</a></li>
<li>Для AuthAPI тестов нужно иметь доступ к приложению для авторизации для запуска на локальном сервере</li></h3>
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
<hr>
<h3 align="center"><img src="https://img.icons8.com/plasticine/512/rick-sanchez.png" height="38"/>  Rick and Morty API покрывает тестами:</h3>
<h3 align="center"><li>
Получение персонажей по ID и имени</li>

<li>Получение эпизодов</li>

<li>Извлечение данных из URL</li>

<li>Сравнение атрибутов персонажей</li>
<hr>
<hr>
<h3 align="center">Auth API покрывает тестами:</h3>
<h3 align="center"><li>
Регистрацию пользователей</li>

<li>Авторизацию (успешную и негативные сценарии)</li>

<li>Выход из системы</li>

<li>Валидацию токенов</li>
<hr>
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
<h3 align="center"><li>"Connection refused: connect"</li>
  1. Проверьте запущен ли сервер (для Auth тестов). При подключении к локальному серверу для AuthAPI тестов запустите сервер на порту 8080 либо исправьте ссылку на локальный сервер в properties
  
  2.Проверьте интернет-соединение (для Rick and Morty тестов)
</h3>
<hr>
<h4 align="center">Авторы:</h4>
<h4 align="center"><a href="https://github.com/gretta-sky" target="_blank">[Васильченко Маргарита]</a>  - mm8595284@mail.ru
</h4>
<h5>Последнее обновление README: январь 2026го</h5>
<hr>
