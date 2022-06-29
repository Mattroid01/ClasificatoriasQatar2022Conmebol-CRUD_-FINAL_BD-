package com.example.mysql.data.generator;

import com.example.mysql.data.Role;
import com.example.mysql.data.entity.Partido;
import com.example.mysql.data.entity.Puntaje;
import com.example.mysql.data.entity.User;
import com.example.mysql.data.service.PartidoRepository;
import com.example.mysql.data.service.PuntajeRepository;
import com.example.mysql.data.service.UserRepository;
import com.vaadin.exampledata.DataType;
import com.vaadin.exampledata.ExampleDataGenerator;
import com.vaadin.flow.spring.annotation.SpringComponent;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringComponent
public class DataGenerator {

    @Bean
    public CommandLineRunner loadData(PasswordEncoder passwordEncoder, UserRepository userRepository,
            PuntajeRepository puntajeRepository, PartidoRepository partidoRepository) {
        return args -> {
            Logger logger = LoggerFactory.getLogger(getClass());
            if (userRepository.count() != 0L) {
                logger.info("Using existing database");
                return;
            }
            int seed = 123;

            logger.info("Generating demo data");

            logger.info("... generating 2 User entities...");
            User user = new User();
            user.setName("John Normal");
            user.setUsername("user");
            user.setHashedPassword(passwordEncoder.encode("user"));
            user.setProfilePictureUrl(
                    "https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=128&h=128&q=80");
            user.setRoles(Collections.singleton(Role.USER));
            userRepository.save(user);
            User admin = new User();
            admin.setName("Emma Powerful");
            admin.setUsername("admin");
            admin.setHashedPassword(passwordEncoder.encode("admin"));
            admin.setProfilePictureUrl(
                    "https://images.unsplash.com/photo-1607746882042-944635dfe10e?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=128&h=128&q=80");
            admin.setRoles(Set.of(Role.USER, Role.ADMIN));
            userRepository.save(admin);
            logger.info("... generating 100 Puntaje entities...");
            ExampleDataGenerator<Puntaje> puntajeRepositoryGenerator = new ExampleDataGenerator<>(Puntaje.class,
                    LocalDateTime.of(2022, 6, 29, 0, 0, 0));
            puntajeRepositoryGenerator.setData(Puntaje::setRanking, DataType.NUMBER_UP_TO_10);
            puntajeRepositoryGenerator.setData(Puntaje::setPais, DataType.WORD);
            puntajeRepositoryGenerator.setData(Puntaje::setPj, DataType.NUMBER_UP_TO_10);
            puntajeRepositoryGenerator.setData(Puntaje::setG, DataType.NUMBER_UP_TO_100);
            puntajeRepositoryGenerator.setData(Puntaje::setE, DataType.NUMBER_UP_TO_100);
            puntajeRepositoryGenerator.setData(Puntaje::setP, DataType.NUMBER_UP_TO_100);
            puntajeRepositoryGenerator.setData(Puntaje::setGf, DataType.NUMBER_UP_TO_100);
            puntajeRepositoryGenerator.setData(Puntaje::setGc, DataType.NUMBER_UP_TO_100);
            puntajeRepositoryGenerator.setData(Puntaje::setDg, DataType.NUMBER_UP_TO_100);
            puntajeRepositoryGenerator.setData(Puntaje::setPts, DataType.NUMBER_UP_TO_100);
            puntajeRepository.saveAll(puntajeRepositoryGenerator.create(100, seed));

            logger.info("... generating 100 Partido entities...");
            ExampleDataGenerator<Partido> partidoRepositoryGenerator = new ExampleDataGenerator<>(Partido.class,
                    LocalDateTime.of(2022, 6, 29, 0, 0, 0));
            partidoRepositoryGenerator.setData(Partido::setId_partido, DataType.NUMBER_UP_TO_1000);
            partidoRepositoryGenerator.setData(Partido::setFecha_hora, DataType.DATE_OF_BIRTH);
            partidoRepositoryGenerator.setData(Partido::setLocal, DataType.WORD);
            partidoRepositoryGenerator.setData(Partido::setVisitante, DataType.WORD);
            partidoRepositoryGenerator.setData(Partido::setResultado, DataType.WORD);
            partidoRepositoryGenerator.setData(Partido::setId_sede, DataType.NUMBER_UP_TO_100);
            partidoRepository.saveAll(partidoRepositoryGenerator.create(100, seed));

            logger.info("Generated demo data");
        };
    }

}