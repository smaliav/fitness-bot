# Fitness Bot
version 1.4.0

## Описание
Fitness Bot позволяет вести мониторинг прогресса Ваших тренировок.

## Список доступных команд
1. `/start` - инициирует диалог с ботом
2. `/get_weight` - посмотреть вес за последнее время
3. `/get_weight <date>` - посмотреть вес за дату (ДД.ММ.ГГГГ)
4. `/set_weight <weight>` - установить сегодняшний вес
5. `/set_weight <weight> <date>` - установить вес за дату (ДД.ММ.ГГГГ)
6. `/rm_weight <date>` - удалить вес за дату (ДД.ММ.ГГГГ)
7. `/rm_weight today` - удаляет запись за сегодня
8. `/rm_weight all` - удалить все замеры
9. `/report <text>` - отправить отзыв/пожелания
10. `/help` - выводит сообщение о доступных командах

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
5. JFreeChart 1.5.3

## Автор
Андрей Смалий [@smaliav](http://t.me/smaliav)
