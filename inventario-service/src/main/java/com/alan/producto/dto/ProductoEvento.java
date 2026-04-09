package com.alan.producto.dto;

public class ProductoEvento {

    private Long id;
    private String nombre;
    private Integer stock;
    private String accion;

    public ProductoEvento() {
    }

    public ProductoEvento(Long id, String nombre, Integer stock, String accion) {
        this.id = id;
        this.nombre = nombre;
        this.stock = stock;
        this.accion = accion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    @Override
    public String toString() {
        return "ProductoEvento{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", stock=" + stock +
                ", accion='" + accion + '\'' +
                '}';
    }
}
