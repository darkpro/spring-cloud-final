package com.alan.producto.controller;

import com.alan.producto.dto.ProductoEvento;
import com.alan.producto.service.ProductoPublisherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;



@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private static final Logger log = LoggerFactory.getLogger(ProductoController.class);
    private final ProductoPublisherService publisherService;

    public ProductoController(ProductoPublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @PostMapping
    public ProductoEvento crearProducto(@RequestBody ProductoEvento evento) {
        log.info("Solicitud recibida para publicar producto: {}", evento);
        return publisherService.publicarEvento(evento);
    }

    @GetMapping("/health")
    public String health() {
        return "producto-service OK";
    }
    @GetMapping
    public List<ProductoEvento> listarProductos() {
        return publisherService.listarProductos();
    }

}
