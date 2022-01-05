package com.vacuna.vacuna;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.vacuna.vacuna.DAO.pacienteDAO;
import com.vacuna.vacuna.model.Paciente;

@SpringBootApplication
public class AccessingDataMongodbApplication implements CommandLineRunner {

  @Autowired
  private pacienteDAO repository;

  public static void main(String[] args) {
    SpringApplication.run(AccessingDataMongodbApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {

    //repository.deleteAll();

    // save a couple of customers
    repository.save(new Paciente("Mario","Perez Galan", "05789302F","Administrador", "Hospitalillo", "0", "Madridejos", "Toledo"));
    repository.save(new Paciente("Jose Antonio","Arias Ramos", "05937392B","Administrador", "Hospitalillo", "0", "Puertollano", "Ciudad Real"));
    //repository.save(new Paciente("Bob", "04733782W"));

    // fetch all customers
    System.out.println("Pacientes found with findAll():");
    System.out.println("-------------------------------");
    for (Paciente p : repository.findAll()) {
      System.out.println(p.getNombre());
    }
    System.out.println();

    // fetch an individual customer
    System.out.println("Pacientes found with findByDni ('05789302F'):");
    System.out.println("--------------------------------");
    System.out.println(repository.findByDni("05789302F").getDni());

  }

}