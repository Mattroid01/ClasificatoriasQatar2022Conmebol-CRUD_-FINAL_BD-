package com.example.mysql.data.service;


import com.example.mysql.data.entity.Posicion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.dao.DataAccessException;
import java.util.ArrayList;
import java.util.List;

@Component
public class PosicionService {
	
	@Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Posicion> findAll() {
    	try {
			return jdbcTemplate.query("SELECT id_posicion, pos, id_pais, PJ, G, E, P, GF, GC, DG, pts FROM posicion ORDER BY pos",
			        (rs, rowNum) -> new Posicion(rs.getInt("id_posicion"), rs.getInt("pos"), rs.getString("id_pais"), rs.getInt("PJ"), rs.getInt("G"),rs.getInt("E"),rs.getInt("P"),rs.getInt("GF"),rs.getInt("GC"),rs.getInt("DG"),rs.getInt("pts")));

		} catch (Exception e) {
			return new ArrayList<Posicion>();
		}		
    }
    
    public List<Posicion> findById(Integer id_posicion) {
    	try {
			return jdbcTemplate.query("SELECT id_posicion, pos, id_pais, PJ, G, E, P, GF, GC, DG, pts FROM posicion WHERE id_posicion = ?",
					new Object[]{id_posicion},
			        (rs, rowNum) -> new Posicion(rs.getInt("id_posicion"), rs.getInt("pos"),rs.getString("id_pais"),rs.getInt("PJ"),rs.getInt("G"),rs.getInt("E"),rs.getInt("P"),rs.getInt("GF"),rs.getInt("GC"),rs.getInt("DG"),rs.getInt("pts")));
		} catch (Exception e) {
			return new ArrayList<Posicion>();
		}
    }

    public int savePosicion(Posicion posicion) {
    	List<Posicion> posiciones = this.findById(posicion.getId_posicion());
    	if ( posiciones.size() > 0 ) {
    		return updatePosicion(posicion);
    	} else {
    		return 0;
    	}

    }

    private int updatePosicion(Posicion posicion) {
    	try {
			return jdbcTemplate.update("UPDATE posicion SET id_posicion = ?, pos = ?, id_pais = ?, PJ = ?, G = ?, E = ?, P = ?, GF = ?, GC = ?, DG = ?, pts= ? WHERE id_posicion = ? AND id_pais = ?",
                    posicion.getId_posicion(),posicion.getPos(), posicion.getId_pais(), posicion.getPJ(), posicion.getG(), posicion.getE(), posicion.getP(), posicion.getGF(), posicion.getGC(), posicion.getDG(), posicion.getPts(), posicion.getId_posicion(), posicion.getId_pais());
		} catch (Exception e) {
			return 0;
		}
    }

//    private int insertPosicion(Posicion posicion) {
//        try {
//            return jdbcTemplate.update("INSERT INTO employees VALUES (?, ?, ?, ?, ?)",
//                    employee.getFirstname(), employee.getLastname(), employee.getTitle(), employee.getEmail(), "");
//        } catch (Exception e) {
//            return 0;
//        }
//    }
//
//    public int deletePosicion(Posicion posicion) {
//    	try {
//    		return jdbcTemplate.update("DELETE FROM posicion WHERE email = ?",
//    				employee.getEmail());
//    	} catch (Exception e) {
//			return 0;
//		}
//    }

}

