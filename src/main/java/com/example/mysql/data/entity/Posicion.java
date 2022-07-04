package com.example.mysql.data.entity;

public class Posicion extends AbstractEntity{

    private Integer id_posicion;

    private Integer pos;
    private String id_pais;
    private Integer PJ;
    private Integer G;
    private Integer E;
    private Integer P;
    private Integer GF;
    private Integer GC;
    private Integer DG;
    private Integer pts;

    public Posicion(Integer id_posicion, Integer pos, String id_pais, Integer PJ, Integer g, Integer e, Integer p, Integer GF, Integer GC, Integer DG, Integer pts) {
        this.id_posicion = id_posicion;
        this.pos = pos;
        this.id_pais = id_pais;
        this.PJ = PJ;
        G = g;
        E = e;
        P = p;
        this.GF = GF;
        this.GC = GC;
        this.DG = DG;
        this.pts = pts;
    }

    public Posicion(){

    }

    public Integer getId_posicion() {
        return id_posicion;
    }

    public void setId_posicion(Integer id_posicion) {
        this.id_posicion = id_posicion;
    }

    public Integer getPos() {
        return pos;
    }

    public void setPos(Integer pos) {
        this.pos = pos;
    }

    public String getId_pais() {
        return id_pais;
    }

    public void setId_pais(String id_pais) {
        this.id_pais = id_pais;
    }

    public Integer getPJ() {
        return PJ;
    }

    public void setPJ(Integer PJ) {
        this.PJ = PJ;
    }

    public Integer getG() {
        return G;
    }

    public void setG(Integer g) {
        G = g;
    }

    public Integer getE() {
        return E;
    }

    public void setE(Integer e) {
        E = e;
    }

    public Integer getP() {
        return P;
    }

    public void setP(Integer p) {
        P = p;
    }

    public Integer getGF() {
        return GF;
    }

    public void setGF(Integer GF) {
        this.GF = GF;
    }

    public Integer getGC() {
        return GC;
    }

    public void setGC(Integer GC) {
        this.GC = GC;
    }

    public Integer getDG() {
        return DG;
    }

    public void setDG(Integer DG) {
        this.DG = DG;
    }

    public Integer getPts() {
        return pts;
    }

    public void setPts(Integer pts) {
        this.pts = pts;
    }
}

//    public Posicion(Integer idpuntaje, String pais_id_pais, Integer PJ, Integer G, Integer E, Integer P, Integer GF, Integer GC, Integer DG, Integer puntos) {
//        super();
//        this.idpuntaje = idpuntaje;
//        this.pais_id_pais = pais_id_pais;
//        this.PJ = PJ;
//        this.G = G;
//        this.E = E;
//        this.P = P;
//        this.GF = GF;
//        this.GC = GC;
//        this.DG = DG;
//        this.puntos = puntos;
//
//    }
//
//    public Puntaje() {
//
//    }
//
//    public Integer getIdpuntaje() {
//        return idpuntaje;
//    }
//
//    public void setIdpuntaje(Integer idpuntaje) {
//        this.idpuntaje = idpuntaje;
//    }
//
//    public String getPais_id_pais() {
//        return pais_id_pais;
//    }
//
//    public void setPais_id_pais(String pais_id_pais) {
//        this.pais_id_pais = pais_id_pais;
//    }
//
//    public Integer getPJ() {
//        return PJ;
//    }
//
//    public void setPJ(Integer PJ) {
//        this.PJ = PJ;
//    }
//
//    public Integer getG() {
//        return G;
//    }
//
//    public void setG(Integer g) {
//        G = g;
//    }
//
//    public Integer getE() {
//        return E;
//    }
//
//    public void setE(Integer e) {
//        E = e;
//    }
//
//    public Integer getP() {
//        return P;
//    }
//
//    public void setP(Integer p) {
//        P = p;
//    }
//
//    public Integer getGF() {
//        return GF;
//    }
//
//    public void setGF(Integer GF) {
//        this.GF = GF;
//    }
//
//    public Integer getGC() {
//        return GC;
//    }
//
//    public void setGC(Integer GC) {
//        this.GC = GC;
//    }
//
//    public Integer getDG() {
//        return DG;
//    }
//
//    public void setDG(Integer DG) {
//        this.DG = DG;
//    }
//
//    public Integer getPuntos() {
//        return puntos;
//    }
//
//    public void setPuntos(Integer puntos) {
//        this.puntos = puntos;
//    }
//}
////    @Override
////    public String toString() {
////        return firstname + " " + lastname + "(" + email + ")";
////    }

