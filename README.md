# spring-cloud-final

## Componentes
- producto-service: publica eventos a Kafka
- inventario-service: consume eventos desde Kafka y envía logs a Logstash
- order-service: microservicio simple con logging
- ELK: Elasticsearch + Logstash + Kibana
- Kafka + Zookeeper

## Requisitos
- OpenJDK 17
- Maven
- Docker
- Docker Compose v2

## Instalación rápida en Linux Mint 22.3
```bash
sudo apt update && sudo apt upgrade -y
sudo apt install -y openjdk-17-jdk maven git curl docker.io docker-compose-v2
sudo systemctl enable docker
sudo systemctl start docker
sudo usermod -aG docker $USER
```

## Levantar infraestructura
```bash
cd spring-cloud-final
docker compose up -d
```

## Compilar microservicios
```bash
cd producto-service && mvn clean package
cd ../inventario-service && mvn clean package
cd ../order-service && mvn clean package
```

## Ejecutar microservicios
En 3 terminales distintas:
```bash
cd producto-service && mvn spring-boot:run
cd inventario-service && mvn spring-boot:run
cd order-service && mvn spring-boot:run
```
## Comprobar Funcionamiento 
Funcionamiento de Docker
```bash
docker ps
```
Verificar servicios en ejecucion
```bash
ss -tulnp | grep -E "2181|9092|5000|9200|5601"
```
Pruebas health check


producto-service
```bash
curl http://localhost:8081/api/productos/health
```
order-service
```bash
curl http://localhost:8083/api/orders/health
```
Verificar consumo en Kafka
```bash
curl -X POST http://localhost:8081/api/productos \
  -H "Content-Type: application/json" \
  -d '{
    "id": 1,
    "nombre": "Laptop Lenovo",
    "stock": 15,
    "accion": "CREAR"
  }'
```
```bash
for i in 1 2 3 4 5
do
  curl -X POST http://localhost:8081/api/productos \
    -H "Content-Type: application/json" \
    -d "{
      \"id\": $i,
      \"nombre\": \"Producto $i\",
      \"stock\": $((10 + i)),
      \"accion\": \"CREAR\"
    }"
  echo
done
```
Después de publicar un producto con `producto-service`, se debe revisar la consola de `inventario-service`.
Debe mostrarse un mensaje similar a:

```bash
2026-04-09 17:57:26 INFO  [org.springframework.kafka.KafkaListenerEndpointContainer#0-0-C-1] c.a.i.consumer.InventarioConsumer - Actualizando inventario para producto ID=3, nombre=Producto 3, stock=13
2026-04-09 17:57:26 INFO  [org.springframework.kafka.KafkaListenerEndpointContainer#0-0-C-1] c.a.i.consumer.InventarioConsumer - Mensaje recibido desde Kafka en inventario-service: ProductoEvento{id=4, nombre='Producto 4', stock=14, accion='CREAR'}
```



Probar los registros guardados
```bash
curl -X GET http://localhost:8081/api/productos
```


## Elasticsearch

```bash
curl http://localhost:9200
```
```bash
curl http://localhost:9200/_cat/indices?v
```

## Kibana
Abrir:
```text
http://localhost:5601
```
Crear Data View:
```text
spring-cloud-logs-*
```
Usar `@timestamp` como campo de tiempo.
