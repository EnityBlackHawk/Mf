package com.projeto.TesteMf.Repo.Mongo;

import com.projeto.TesteMf.auto.exchange.Exchange;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MgExchangeRepo
    extends MongoRepository<Exchange, String>
{
}
