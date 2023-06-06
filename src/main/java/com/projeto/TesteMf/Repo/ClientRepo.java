package com.projeto.TesteMf.Repo;

import com.projeto.TesteMf.Model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

@org.springframework.stereotype.Repository
public interface ClientRepo extends JpaRepository<Client, Integer> {
}
