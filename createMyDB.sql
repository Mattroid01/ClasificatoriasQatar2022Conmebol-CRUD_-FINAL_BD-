-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`pais`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`pais` (
  `id_pais` CHAR(3) NOT NULL,
  `nombre_pais` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id_pais`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`posicion`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`posicion` (
  `id_posicion` INT NOT NULL,
  `pos` INT NOT NULL,
  `pais_id_pais` CHAR(3) NOT NULL,
  `PJ` INT NULL,
  `G` INT NULL,
  `E` INT NULL,
  `P` INT NULL,
  `GF` INT NULL,
  `GC` INT NULL,
  `DG` INT NULL,
  `pts` INT NULL,
  PRIMARY KEY (`id_posicion`, `pais_id_pais`),
  INDEX `fk_puntaje_pais_idx` (`pais_id_pais` ASC) VISIBLE,
  CONSTRAINT `fk_puntaje_pais`
    FOREIGN KEY (`pais_id_pais`)
    REFERENCES `mydb`.`pais` (`id_pais`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`jugador`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`jugador` (
  `id_jugador` INT NOT NULL,
  `id_pais` CHAR(3) NOT NULL,
  `apellido` VARCHAR(45) NOT NULL,
  `nombre` VARCHAR(45) NOT NULL,
  `posicion` VARCHAR(3) NOT NULL,
  `goles` INT NULL,
  `asistencias` INT NULL,
  PRIMARY KEY (`id_jugador`),
  INDEX `fk_jugador_pais1_idx` (`id_pais` ASC) VISIBLE,
  CONSTRAINT `fk_jugador_pais1`
    FOREIGN KEY (`id_pais`)
    REFERENCES `mydb`.`pais` (`id_pais`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`sede`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`sede` (
  `id_sede` INT NOT NULL,
  `nombre` VARCHAR(45) NOT NULL,
  `localidad` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id_sede`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`partido`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`partido` (
  `id_partido` INT NOT NULL,
  `fecha` DATE NOT NULL,
  `id_local` CHAR(3) NOT NULL,
  `id_visitante` CHAR(3) NOT NULL,
  `resultado` VARCHAR(5) NULL,
  `id_sede` INT NOT NULL,
  PRIMARY KEY (`id_partido`, `id_local`, `id_visitante`),
  INDEX `fk_partido_pais1_idx` (`id_local` ASC) VISIBLE,
  INDEX `fk_partido_pais2_idx` (`id_visitante` ASC) VISIBLE,
  INDEX `fk_partido_sede1_idx` (`id_sede` ASC) VISIBLE,
  CONSTRAINT `fk_partido_pais1`
    FOREIGN KEY (`id_local`)
    REFERENCES `mydb`.`pais` (`id_pais`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_partido_pais2`
    FOREIGN KEY (`id_visitante`)
    REFERENCES `mydb`.`pais` (`id_pais`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_partido_sede1`
    FOREIGN KEY (`id_sede`)
    REFERENCES `mydb`.`sede` (`id_sede`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

INSERT INTO `pais` (`id_pais`, `nombre_pais`) 
VALUES ('BRA', 'Brasil'), ('ARG', 'Argentina'),
 ('URU', 'Uruguay'), ('ECU', 'Ecuador'), 
 ('PAR', 'Paraguay'), ('CHI', 'Chile'), 
 ('COL', 'Colombia'), ('PER', 'Perú'), 
 ('VEN', 'Venezuela'), ('BOL', 'Bolivia');

INSERT INTO `sede` (`id_sede`, `nombre`, `localidad`) VALUES 
('2201', 'Estadio Defensores del Chaco', 'Paraguay'), 
('2202', 'Estadio Centenario', 'Uruguay'),
 ('2203', 'La Bombonera', 'Argentina'),
 ('2204', 'Estadio Metropolitano Roberto Meléndez', 'Colombia'),
 ('2205', 'Arena de São Paulo', 'Brasil'),
 ('2206', 'Hernando Siles Reyes ', 'Bolivia'),
 ('2207', 'Estadio Rodrigo Paz Delgado', 'Ecuador'),
 ('2208', 'Estadio Metropolitano de Mérida', 'Venezuela'),
 ('2209', 'Estadio Nacional del Perú', 'Perú'),
 ('2210', 'Estadio Nacional de Chile', 'Chile');
 
 INSERT INTO `posicion` (`id_posicion`, `pos`, `id_pais`, `PJ`, `G`, `E`, `P`, `GF`, `GC`, `DG`, `pts`) VALUES 
 ('20200', '1', 'BRA', '3', '3', '0', '0', '10', '2', '8', '9'), 
 ('20201', '2', 'ARG', '3', '2', '1', '0', '4', '2', '2', '7'), 
 ('20202', '3', 'ECU', '3', '2', '1', '0', '7', '5', '2', '6'),
 ('20203', '4', 'URU', '3', '2', '0', '1', '7', '6', '1', '6'),
 ('20204', '5', 'PAR', '3', '1', '2', '0', '4', '3', '1', '5'),
 ('20205', '6', 'CHI', '6', '1', '1', '1', '5', '4', '1', '4'), 
 ('20206', '7', 'COL', '3', '1', '1', '1', '5', '3', '1', '4'), 
 ('20207', '8', 'PER', '3', '0', '1', '2', '4', '8', '-4', '1'), 
 ('20208', '9', 'VEN', '3', '0', '0', '3', '0', '5', '-5', '0'),
 ('20209', '10', 'BOL', '3', '0', '0', '3', '3', '9', '-6', '0');
 
 INSERT INTO `partido` (`id_partido`, `fecha`, `id_local`, `id_visitante`, `resultado`, `id_sede`) VALUES 
 ('20200101', '2020-10-08', 'PAR', 'PER', '2 - 2', '2201'), 
 ('20200102', '2020-10-08', 'ARG', 'ECU', '1 - 0', '2203'), 
 ('20200103', '2020-10-08', 'URU', 'CHI', '2 - 1', '2202'), 
 ('20200104', '2020-10-09', 'BRA', 'BOL', '5 - 0', '2205'), 
 ('20200105', '2020-10-09', 'COL', 'VEN', '3 - 0', '2204'), 
 ('20200206', '2020-10-13', 'BOL', 'ARG', '1 - 2', '2206'), 
 ('20200207', '2020-10-13', 'VEN', 'PAR', '0 - 1', '2208'), 
 ('20200208', '2020-10-14', 'CHI', 'COL', '2 - 2', '2210'), 
 ('20200209', '2020-10-14', 'ECU', 'URU', '4 - 2', '2207'), 
 ('20200210', '2020-10-15', 'PER', 'BRA', '2 - 4', '2209'), 
 ('20200311', '2020-11-12', 'BOL', 'ECU', '2 - 3', '2206'),
 ('20200313', '2020-11-13', 'COL', 'URU', '0 - 3', '2204'), 
 ('20200314', '2020-11-14', 'CHI', 'PER', '2 - 0', '2210'),
 ('20200315', '2020-11-14', 'BRA', 'VEN', '1 - 0', '2205');
