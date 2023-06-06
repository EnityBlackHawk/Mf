package com.projeto.TesteMf.Repo;

import com.projeto.TesteMf.Model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Repository
public interface AccontRepo extends JpaRepository<Accont, Integer> {

    List<Accont> findByClient(Client c);
}
