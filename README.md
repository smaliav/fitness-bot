# Fitness Bot
version 1.0.0
## Описание
Fitness Bot позволяет вести мониторинг прогресса Ваших тренировок.

## Список доступных команд
1. `/start` - инициирует диалог с ботом
2. `/weight` - группа команд для контроля веса
   1. `/weight get` - посмотреть все замеры
   2. `/weight get <date>` - посмотреть замер за дату (ДД.ММ.ГГГГ)
   3. `/weight set <weight>` - установить сегодняшний вес
   4. `/weight set <weight> <date>` - установить вес за дату (ДД.ММ.ГГГГ)
   5. `/weight remove <date>` - удалить вес за дату (ДД.ММ.ГГГГ)
   6. `/weight remove all` - удалить все замеры
3. `/help` - выводит сообщение о доступных командах

## Содержимое репозитория
1. [Procfile](Procfile) - конфиг запуска для Heroku
2. [system.properties](system.properties) - определяет версию JDK для Heroku 
3. [src](src) - исходники Fitness Bot
4. [pom.xml](pom.xml) - конфиг для Maven

## Используемые технологии
1. Java 17
2. Spring Boot 2.5.6
3. PostgreSQL 14
4. Telegram Bots 5.3.0

## Автор
Андрей Смалий [@smaliav](http://t.me/smaliav)
