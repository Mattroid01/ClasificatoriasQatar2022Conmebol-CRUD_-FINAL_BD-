package com.example.mysql.data.entity;

import javax.persistence.Entity;

@Entity
public class Puntaje extends AbstractEntity {

    private Integer ranking;
    private String pais;
    private Integer pj;
    private Integer g;
    private Integer e;
    private Integer p;
    private Integer gf;
    private Integer gc;
    private Integer dg;
    private Integer pts;

    public Integer getRanking() {
        return ranking;
    }
    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }
    public String getPais() {
        return pais;
    }
    public void setPais(String pais) {
        this.pais = pais;
    }
    public Integer getPj() {
        return pj;
    }
    public void setPj(Integer pj) {
        this.pj = pj;
    }
    public Integer getG() {
        return g;
    }
    public void setG(Integer g) {
        this.g = g;
    }
    public Integer getE() {
        return e;
    }
    public void setE(Integer e) {
        this.e = e;
    }
    public Integer getP() {
        return p;
    }
    public void setP(Integer p) {
        this.p = p;
    }
    public Integer getGf() {
        return gf;
    }
    public void setGf(Integer gf) {
        this.gf = gf;
    }
    public Integer getGc() {
        return gc;
    }
    public void setGc(Integer gc) {
        this.gc = gc;
    }
    public Integer getDg() {
        return dg;
    }
    public void setDg(Integer dg) {
        this.dg = dg;
    }
    public Integer getPts() {
        return pts;
    }
    public void setPts(Integer pts) {
        this.pts = pts;
    }

}
