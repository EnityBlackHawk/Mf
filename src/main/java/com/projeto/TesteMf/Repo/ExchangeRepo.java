package com.projeto.TesteMf.Repo;

import com.projeto.TesteMf.Model.Accont;
import com.projeto.TesteMf.Model.Exchange;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExchangeRepo extends JpaRepository<Exchange, Integer> {
    List<Exchange> findByAccontSource(Accont accont);
    List<Exchange> findByAccontDest(Accont accont);
}
