package com.example.mysql.data.entity;

import java.time.LocalDate;
import java.util.Date;
import javax.persistence.Entity;

@Entity
public class Partido extends  AbstractEntity{

    private Integer id_partido;
    private Integer jornada;
    private Date fecha;
    private String id_local;
    private String id_visitante;
    private String resultado;

//    private String nombre;
    private Integer id_sede;

    public Partido(Integer id_partido, Integer jornada, Date fecha, String id_local, String id_visitante, String resultado, Integer id_sede) {
        this.id_partido = id_partido;
        this.jornada = jornada;
        this.fecha = fecha;
        this.id_local = id_local;
        this.id_visitante = id_visitante;
        this.resultado = resultado;
//        this.nombre = nombre;
        this.id_sede = id_sede;
    }

    public Partido(){

    }

    public Integer getId_partido() {
        return id_partido;
    }
    public void setId_partido(Integer id_partido) {
        this.id_partido = id_partido;
    }
    public Integer getJornada() {
        return jornada;
    }
    public void setJornada(Integer jornada) {
        this.jornada = jornada;
    }
    public Date getFecha() {
        return fecha;
    }
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getId_local() {
        return id_local;
    }

    public void setId_local(String id_local) {
        this.id_local = id_local;
    }

    public String getId_visitante() {
        return id_visitante;
    }

    public void setId_visitante(String id_visitante) {
        this.id_visitante = id_visitante;
    }

    public String getResultado() {
        return resultado;
    }
    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

//    public String getNombre() {
//        return nombre;
//    }
//
//    public void setNombre(String nombre) {
//        this.nombre = nombre;
//    }

    public Integer getId_sede() {
        return id_sede;
    }
    public void setId_sede(Integer Id_sede) {
        this.id_sede = Id_sede;
    }

}
