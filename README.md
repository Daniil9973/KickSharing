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
    - В pgAdmin: откройте файл `src/main/resources/db/schema.sql` и выполните

3. Настройте подключение к БД:
    - Скопируйте `src/main/resources/config.properties.sample` в `config.properties`.
    - Укажите свой пароль в `config.properties`.

4. Запустите приложение:
   - Через IDEA:
        - Убедитесь, что lib/postgresql-42.7.10.jar добавлен в зависимости проекта
        - Запустите appLogic/Main.java

## Функции

- CRUD для каждой сущности
- Просмотр статуса бонусной карты (бронзовая/серебряная/золотая).

## Логи

Логи пишутся в папку `logs/app.log`.
