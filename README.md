<h1 align="center">"Мини-банк" чат-бот для Telegram</h1>

<p align="center"> Создано в рамках обучения в академии Backend-разработки <a href="https://gpb.fut.ru/itfactory/backend"> GPB IT Factory</a></p>

<h1 id="contents">Оглавление</h1>

1. [Общая схема проекта](#1)
2. [Начало работы](#2)
3. [Необходимые компоненты](#3)
4. [Скачивание и установка](#4)
5. [Используемые команды и примеры](#5)
6. [Контакты](#6)
7. [To Do](#7)

<h2 id="1">Общая схема проекта</h2>

```@startuml
!theme spacelab
actor "Пользователь" as User
participant "Frontend" as Front
participant Middle
participant "Backend" as Back
rnote over of Front: Телеграмм-бот\nВыполняет роль клиентского приложения\nИнициализирует запросы пользователя
/ rnote over of Middle: Сервис на Java\nПринимает запросы бота\nПроводит валидацию\nВыполняет бизнес-логику\nМаршрутизирует запросы в "банк"
/ rnote over of Back: Выступает в качестве АБС\nОбрабатывает транзакции\nХранит клиентские данные
User -> Front : Обращается к боту
activate Front
Front -> Middle : Инициализация запроса
activate Middle
Middle -> Back : Маршрутизация запроса в "банк"
activate Back
Back -> Back : Сохранение изменений
Back --> Middle : Возвращение данных
deactivate Back
Middle --> Front : Возвращение ответного запроса
deactivate Middle
Front -> User : Вывод ответного сообщения
deactivate Front
@enduml
```

![Диаграмма](https://www.plantuml.com/plantuml/png/VLGzZzD04EtzAmRt9_ehq8c20ecIZcP9Z8Z8sP5ZKbzyEZuKXE4g2iGfIFGR7-QCYJT_OVOVyNO7dx1bdQBOdfsPD_FUZ7sop5IQlJwT1_Uobz5fHCk5caHpzJnGaon9AUGhDlp7Vk0Rt4lMDkVAsI6f9JrTHccm0C1iCbkeEAFmKPh4MHHFlTiRFV-JsNGwZ_ef3zNaLPVXdeCqJhA8abMKKl92G8w9lxkw-5_RCzQyv_qHxzZO_5dCbtR31zScjxRWokQ46DyrSOFJcYjuShk6PVYQ3V414JLSXdyv9zUs0D0N__G67ktJRc2TsNCF2KFxD6FNTWE4KLsAu3xr-nVA8B3b2jab8DU4EfeUgvL2qIlV2Wg3b1uh9JnPIwmlP_Wdrowob4vXQjUt_JWg1-yycHR6-WZd4047ZJr7z5VMuFZEyKIHkpcN509A0wW9XqJzx40JrH6S0-4WT5mQDwZo5XOywBaY_iIVUOlottZd1mfO96tWb2GOxhZrBJH-9ZN2VyYfa1_E5iy49Po_1LGLkFsaemUoGtHCNJNxNggWdG8meZ1QTai_MocGajMLF038484miY8YVD4JY_KjLBVpSWEO5mfG8ygFGmrqxu3bPN6N_q6toBaGaPmoeed3tiFiZdx_I-inksI3i5BqaS2UdlOYc4RzuXsdJk8xO8mC7jTMti41IM0F5RjLtuzHLajsVmmE80Q_NLVK5e0SpFC4tnbyw_u2)

[Вверх :arrow_up:](#contents)

<h2 id="2">Начало работы</h2>

:warning: Раздел в разработке  
[Вверх :arrow_up:](#contents)

<h2 id="3">Необходимые компоненты</h2>

:warning: Новые компоненты будут добавляться по мере развития проекта
- [Java 17+](https://www.java.com/ru/)
- [Gradle 7.4/8.7](https://www.gradle.org/)
- [SpringBoot](https://www.spring.io/projects/spring-boot)  
[Вверх :arrow_up:](#contents)

<h2 id="4">Скачивание и установка</h2>

:warning: Раздел в разработке  
[Вверх :arrow_up:](#contents)
<h2 id="5">Используемые команды и примеры</h2>

:warning: Будут добавляться по мере развития функционала  
[Вверх :arrow_up:](#contents)

<h2 id="6">Контакты</h2>

E-mail: [elnberg2397@gmail.com](mailto:elnberg2397@gmail.com)  
Telegram: [LohmatiyShmel](https://t.me/LohmatiyShmel)  
[Вверх :arrow_up:](#contents)

<h2 id="7">To Do</h2>

Учет текущих задач по проекту
- [x] Задача 1. Создать репозиторий для telegram-бота
- [x] Задача 2. Добавить ReadMe
- [ ] Задача 3. Создать "скелет" бота
- [ ] .... TBD

[Вверх :arrow_up:](#contents)