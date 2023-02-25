## HW2:

> Работа должна быть оформлена следующим образом:
>- Необходимо подготовить GitHub репозиторий и предоставить доступ всем проверяющим - семинаристам и ассистентам.
   Разработка
   > должна вестись в соответствии
   с [GitFlow](https://www.atlassian.com/ru/git/tutorials/comparing-workflows/gitflow-workflow).
>- Под каждое домашнее задание необходимо
   заводить [PullRequest](https://docs.gitlab.com/ee/user/project/merge_requests/creating_merge_requests.html),
   > в список апрруверов необходимо добавить всех проверяющих. После првоедения ревью и простановки оценки или апррува
   от проверяющего
   > (если в задании не сказано иного).
>- Каждое домашнее задание должно повышать [минорную версию приложения](https://semver.org/lang/ru/).
>- В корне репозитория должен находиться файл [readme.md](https://www.markdownguide.org/basic-syntax/) в котором должны
   быть
   > описани шаги по запуску и настройке проекта с "чистого листа". Так же должны быть интерактивные ссылки на папку *
   */docs**
   > с отдельными папками под описание проделанных работы по каждому домашнему заданию, в том же формате **.md**.
>- Код должен быть оформлен в соответствии дефолтным **IntellijIdea Codestyle** -"_File | Default Settings | Editor |
   Code Style_" -
   > отформатирован, импорт оптимизирован. Код должен соответствовать пройденным ранее принципам - _KISS, DRY, YAGNI,
   BDUF, SOLID, APO etc._
>- Все публичные методы сервисов должны быть снабжены [JavaDoc](https://ru.wikipedia.org/wiki/Javadoc).
>- Публичные методы сервисов должны быть покрыты интеграционными тестами.

### Заданиe:

> ##### Сервис "VOgorode"
> ![./image.webp](./image.webp)  
> Проект представляет из себя систему предоставления сервиса по оказанию ландшафтных работ клиентам распределённым
> географически. Сервис должен подбирать исполинтелей на основании географических координат исполнителя и локации
> заказчика,
> списка оказываемых исполнителем работ, а так же ретинга исполнителя.
> - HandymanService - бэк для клиентской части, обслуживающий людей которые предоставляют сервис.
    > есть набор характеристик - что умеет (копать, сажать картошку, поливать грядки), где находиться,
    > зафиксированное расписание, оплата за час
> - RancherService - бэк обслуживающий участки, координаты, размеры поля, что посажено - когда что созреет и т.п.
> - LandscapeService - Управление пользователями, назначение на работы, управление ценой за работы,
    > проставление рейтинга, сбор статистики

1. В Landscape сервисе необходимо реализовать endpoint, возвращающий хост, текущее состояние, версию и количество
   сервисов Handyman и Rancher.
   (Теоритечески сервисов Handyman и Rancher может быть больше одного, если рассматриваем распределенную систему).
   Коннект к сервисам должен быть прописан в конфигурационном файле сервиса Landscape.
   Пример ответа:
   ```json
   {
       "HandymanService": [
           {   
               "host" : "localhost:8090",
               "status" : "OK",
               "artifact" : "handymanService",
               "name" : "handymanService",
               "group" : "ru.tinkoff",
               "version" : "1.0.0"  
           }
       ],
        "RancherService": [
           {   
               "host" : "localhost:8091",
               "status" : "OK",
               "artifact" : "rancherService",
               "name" : "rancherService",
               "group" : "ru.tinkoff",
               "version" : "1.0.0" 
           }
        ] 
   }
   ``` 
2. Взаимодействие сервиса Landscape с сервисами Handyman и Rancher должно производиться с
   помощью [gRPC](https://grpc.io/docs/languages/java/).
   В файле status_service.proto описаны структура сообщений и сервис-контракт.
3. В сервисе Landscape нееобходимо реализовать gRPC client.
4. В сервисах Handyman и Rancher необходимо реализовать gRPC server.

4.* Вместо проверки состояния сервиса через /readiness, следует
использовать [StatesOfConnectivity](https://github.com/grpc/grpc/blob/master/doc/connectivity-semantics-and-api.md#channel-state-api)

### Ресурсы для самостоятельного ознакомления

- [gRPC-intoduction by Baeldung](https://www.baeldung.com/grpc-introduction)
- [gRPC-Spring-Boot-Starter](https://yidongnan.github.io/grpc-spring-boot-starter/en/server/getting-started.html)
- [ProtoBuf](https://protobuf.dev/)
