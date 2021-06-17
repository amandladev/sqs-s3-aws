package model;

import java.util.UUID;

public class OrdenVenta {
    private String id;
    private String nombre;
    private String apellido;
    private int monto;

    public OrdenVenta() {
        this.id = UUID.randomUUID().toString();
    }

    public OrdenVenta(String nombre, String apellido, int monto) {
        this.id = UUID.randomUUID().toString();
        this.nombre = nombre;
        this.apellido = apellido;
        this.monto = monto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getMonto() {
        return monto;
    }

    public void setMonto(int monto) {
        this.monto = monto;
    }

    @Override
    public String toString() {
        return "OrdenVenta{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", monto=" + monto +
                '}';
    }
}
