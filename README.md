## Запуск

Нужны JDK 21 или новее и работающий Docker Desktop с Linux-контейнерами. Maven доступен через Wrapper. Команды выполняются из этой папки. Java-приложение занимает порт 8080, PostgreSQL занимает локальный порт 5432.

Если `.env` ещё не создан, скопируйте пример:

```powershell
Copy-Item .env.example .env
```

Откройте `.env` и замените `replace-with-local-database-password` отдельным учебным паролем. Не используйте пароль от других систем. Файл `.env` исключён из системы контроля версий. Не перезаписывайте уже заполненный файл при повторном запуске.

```powershell
docker compose config --quiet
docker compose up -d --wait postgres
docker compose ps
docker compose exec postgres psql -U tournament_app -d tournament_management -c "SELECT current_database(), current_user;"
```

Ожидаются состояние `healthy`, база `company_management` и пользователь `company_app`. Docker Compose читает `.env`, но Java-процесс этот файл автоматически не читает. Передайте Java те же значения. В PowerShell 7.1 и новее пароль можно ввести без отображения символов:

```powershell
$env:DB_USERNAME = "tournament_app"
$env:DB_PASSWORD = Read-Host "Локальный пароль из .env" -MaskInput
$env:SPRING_PROFILES_ACTIVE = "database"
.\mvnw.cmd clean package
.\mvnw.cmd spring-boot:run

PS C:\Users\kanyk\IdeaProjects\practice-07> docker compose exec postgres psql -U company_app -d company_management -c "ALTER USER company_app WITH PASSWORD 'password';"
PS C:\Users\kanyk\IdeaProjects\practice-07> .\mvnw.cmd spring-boot:run "-Dspring-boot.run.arguments=--spring.profiles.active=database --spring.datasource.username=company_app --spring.datasource.password=password"
```

В другой оболочке задайте переменные её средствами или через параметры запуска среды разработки. Для Linux и macOS команды сборки и запуска: `sh mvnw clean package` и `sh mvnw spring-boot:run`. При первом запуске потребуется Интернет для загрузки Maven и зависимостей.

Профиль `database` выбран и по умолчанию. Реализации в памяти в этой папке нет: неверная настройка базы должна приводить к ошибке, а не к незаметному переключению на временное хранилище. Если `SPRING_PROFILES_ACTIVE` осталась от другого занятия, явно задайте `database`.

Откройте `http://localhost:8080/companies`. Должны появиться `Orbit Systems` и `Northwind Labs`. На `http://localhost:8080/employees` находится Анна Смирнова, связанная с `Orbit Systems`. Страница `/employees/add` позволяет добавить сотрудника, выбрав компанию. Главный адрес `/` сохраняет текстовый ответ предыдущих работ.

После сборки возможен запуск без Maven:

```powershell
java -jar target/company-course-practice-07-0.0.1-SNAPSHOT.jar
```

Если порт базы занят, измените `POSTGRES_PORT` в `.env`, например на `15432`, и задайте Java соответствующий адрес:

```powershell
$env:DB_URL = "jdbc:postgresql://localhost:15432/company_management"
```

Порт Java можно изменить аргументом `--server.port=8081` при запуске JAR.

## Проверка результата

1. Создайте компанию через форму. Ожидается `302`, затем GET списка. Уведомление появляется один раз. Проверьте изменение, переименование и ошибку занятого имени. HTML-разметка в названии должна отображаться как текст.
2. Отправьте пустые поля, `abc` в бюджете, отрицательный бюджет и число с тремя дробными разрядами. Ожидается та же форма со статусом `200`, ошибками и сохранённым вводом.
3. Добавьте сотрудника в существующую компанию. Проверьте список и внешний ключ. При подмене имени компании на несуществующее, форма должна показать ошибку, а новая строка сотрудника не должна появиться.
4. Попробуйте удалить `Orbit Systems`, у которой есть сотрудник. Ожидается страница со статусом `409`, данные остаются. Компанию без сотрудников можно удалить через POST. GET не выполняет удаление.
5. Остановите Java сочетанием Ctrl+C и запустите снова с теми же настройками. Созданные записи должны остаться. Flyway не вставляет начальные данные повторно.

Для независимой проверки откройте `psql`:

```powershell
docker compose exec postgres psql -U company_app -d company_management
```

В новой базе этого проекта ожидаются ровно две миграции: `V1` и `V2`. Каталог `project` содержит продолжение курса, поэтому его число миграций не является требованием к этой версии.

Чтобы посмотреть SQL локально, перед запуском задайте:

```powershell
$env:LOGGING_LEVEL_ORG_HIBERNATE_SQL = "DEBUG"
```

Откройте список сотрудников и найдите запрос с присоединённой таблицей компаний. Значения параметров включать в журнал не требуется: там могут оказаться пользовательские данные.
