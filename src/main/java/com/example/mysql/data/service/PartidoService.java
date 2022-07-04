package com.example.mysql.data.service;

import com.example.mysql.data.entity.Partido;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.example.mysql.data.entity.Posicion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.http.Part;

@Component
public class PartidoService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Partido> findAll() {
        try {
            return jdbcTemplate.query("SELECT id_partido, jornada, fecha, id_local, resultado, id_visitante, id_sede FROM partido ORDER BY fecha",
                    (rs, rowNum) -> new Partido(rs.getInt("id_partido"), rs.getInt("jornada"),rs.getDate("fecha"),rs.getString("id_local"),rs.getString("id_visitante"), rs.getString("resultado") ,rs.getInt("id_sede")));

        } catch (Exception e) {
            return new ArrayList<Partido>();
        }
    }
    public List<Partido> findById(Integer id_partido) {
        try {
            return jdbcTemplate.query("SELECT id_partido, jornada, fecha, id_local, resultado, id_visitante, id_sede FROM partido WHERE id_partido = ?",
                    new Object[]{id_partido},
                    (rs, rowNum) -> new Partido(rs.getInt("id_partido"), rs.getInt("jornada"),rs.getDate("fecha"),rs.getString("id_local" ),rs.getString("id_visitante"), rs.getString("resultado"), rs.getInt("id_sede")));
        } catch (Exception e) {
            return new ArrayList<Partido>();
        }
    }

    public int savePartido(Partido partido) {
        List<Partido> partidos = this.findById(partido.getId_partido());
        if ( partidos.size() > 0 ) {
            return updatePartido(partido);
        } else {
            return insertPartido(partido);
        }

    }

    private int updatePartido(Partido partido) {
        try {
            return jdbcTemplate.update("UPDATE partido SET id_partido = ?, jornada = ?, fecha = ?, id_local =?, id_visitante =?, resultado = ?, id_sede = ? WHERE id_partido = ?",
                    partido.getId_partido(), partido.getJornada(), partido.getFecha(), partido.getId_local(), partido.getId_visitante(), partido.getResultado(), partido.getId_sede(), partido.getId_partido());
        } catch (Exception e) {
            return 0;
        }
    }

    private int insertPartido(Partido partido) {
        try {
            return jdbcTemplate.update("INSERT INTO partido VALUES (?, ?, ?, ?, ?, ?, ?)",
                    partido.getId_partido(), partido.getJornada(), partido.getFecha(), partido.getId_local(), partido.getId_visitante(), partido.getResultado(), partido.getId_sede());
        } catch (Exception e) {
            return 0;
        }
    }

    public int deletePartido(Partido partido) {
    	try {
    		return jdbcTemplate.update("DELETE FROM partido WHERE id_partido = ?",
    				partido.getId_partido());
    	} catch (Exception e) {
			return 0;
		}
    }

}
