# KickSharing

Консольное приложение для управления поездками и платежами сервиса шеринга.

## Требования

- Java 17+
- PostgreSQL 14+
- JDBC
- Repository Pattern

## Установка и запуск

1. Создайте базу данных PostgreSQL:

   ```sql
    CREATE DATABASE KickSharing;
   ```

2. Выполните скрипт создания таблиц:
    - Откройте файл `src/main/resources/db/schema.sql` в pgAdmin.

3. Настройте подключение к БД:
    - Скопируйте `src/main/resources/config.properties.sample` в `config.properties`.
    - Укажите свой пароль в `config.properties`.

4. Запустите приложение:
    - Через IDEA либо скомпилируйте и запустите через командную строку:

      ```bash
      javac -d out src/main/java/**/*.java
      java -cp out appLogic.Main
      ```

## Функции

- Создание пользователя.
- Создание поездки.
- Создание платежа.
- Просмотр статуса бонусной карты (бронзовая/серебряная/золотая).

## Логи

Логи пишутся в папку `logs/app.log`.
