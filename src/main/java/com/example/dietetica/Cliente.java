package com.example.dietetica;

public class Cliente {
    private Integer id;
    private String nombre;
    private String genero;
    private Double peso;
    private Integer edad;
    private Double talla;
    private String actividad;
    private String observaciones;
    private Double ger;
    private Double get;

    public Cliente() {
        this.observaciones = "";
    }

    public Cliente(Integer id, String nombre, String genero, Double peso, Integer edad, Double talla, String actividad, String observaciones, Double ger, Double get) {
        this.id = id;
        this.nombre = nombre;
        this.genero = genero;
        this.peso = peso;
        this.edad = edad;
        this.talla = talla;
        this.actividad = actividad;
        this.observaciones = observaciones;
        this.ger = ger;
        this.get = get;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public Double getTalla() {
        return talla;
    }

    public void setTalla(Double talla) {
        this.talla = talla;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Double getGer() {
        return ger;
    }

    public void setGer(Double ger) {
        this.ger = ger;
    }

    public Double getGet() {
        return get;
    }

    public void setGet(Double get) {
        this.get = get;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", genero='" + genero + '\'' +
                ", peso=" + peso +
                ", edad=" + edad +
                ", talla=" + talla +
                ", actividad='" + actividad + '\'' +
                ", observaciones='" + observaciones + '\'' +
                ", ger=" + ger +
                ", get=" + get +
                '}';
    }
}
