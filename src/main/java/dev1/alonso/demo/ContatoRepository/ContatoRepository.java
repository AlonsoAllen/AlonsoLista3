package dev1.alonso.demo.ContatoRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import dev1.alonso.demo.model.Contato;


@Repository
public interface ContatoRepository extends JpaRepository<Contato, Long>{

}
