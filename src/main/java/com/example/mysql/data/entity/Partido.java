package com.example.mysql.data.entity;

import java.time.LocalDate;
import javax.persistence.Entity;

@Entity
public class Partido extends AbstractEntity {

    private Integer id_partido;
    private LocalDate fecha_hora;
    private String local;
    private String visitante;
    private String resultado;
    private Integer id_sede;

    public Integer getId_partido() {
        return id_partido;
    }
    public void setId_partido(Integer id_partido) {
        this.id_partido = id_partido;
    }
    public LocalDate getFecha_hora() {
        return fecha_hora;
    }
    public void setFecha_hora(LocalDate fecha_hora) {
        this.fecha_hora = fecha_hora;
    }
    public String getLocal() {
        return local;
    }
    public void setLocal(String local) {
        this.local = local;
    }
    public String getVisitante() {
        return visitante;
    }
    public void setVisitante(String visitante) {
        this.visitante = visitante;
    }
    public String getResultado() {
        return resultado;
    }
    public void setResultado(String resultado) {
        this.resultado = resultado;
    }
    public Integer getId_sede() {
        return id_sede;
    }
    public void setId_sede(Integer id_sede) {
        this.id_sede = id_sede;
    }

}
