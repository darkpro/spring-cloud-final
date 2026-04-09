package com.alan.producto.service;

import com.alan.producto.dto.ProductoEvento;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductoPublisherService {

    private static final Logger log = LoggerFactory.getLogger(ProductoPublisherService.class);

    private final KafkaTemplate<String, ProductoEvento> kafkaTemplate;

    private final List<ProductoEvento> productos = new ArrayList<>();

    public ProductoPublisherService(KafkaTemplate<String, ProductoEvento> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public ProductoEvento publicarEvento(ProductoEvento evento) {
        kafkaTemplate.send("producto-eventos", evento.getId().toString(), evento);
        productos.add(evento);

        log.info("Evento publicado en Kafka: {}", evento);
        return evento;
    }

  
    public List<ProductoEvento> listarProductos() {
        return productos;
    }
}
