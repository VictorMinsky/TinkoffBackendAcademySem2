# Описание проделанной работы по HW2:

1. Настроил генерацию файлов из протобуфок `.proto`
2. Создал gRPC сервера `Handyman`, `Rancher` и клиент `Landscape`
3. В серверах создал `StatusServiceImpl` и унаследовал от `StatusServiceGrpc.StatusServiceImplBase`. Также переопределил
   getVersion и getReadiness.
4. В `Landscape` реализовал ручку `/services/statuses`
5. В серверах новая ручка `/system/readiness/grpc` работает и отдаёт `StatesOfConnectivity`
