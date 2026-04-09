package com.alan.inventario.consumer;

import com.alan.producto.dto.ProductoEvento;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class InventarioConsumer {

    private static final Logger log = LoggerFactory.getLogger(InventarioConsumer.class);

    @KafkaListener(topics = "producto-eventos", groupId = "inventario-group")
    public void consumirEvento(ProductoEvento evento) {
        log.info("Mensaje recibido desde Kafka en inventario-service: {}", evento);

        if ("CREAR".equalsIgnoreCase(evento.getAccion())) {
            log.info("Actualizando inventario para producto ID={}, nombre={}, stock={}",
                    evento.getId(), evento.getNombre(), evento.getStock());
        } else {
            log.info("Acción recibida no controlada: {}", evento.getAccion());
        }
    }
}
