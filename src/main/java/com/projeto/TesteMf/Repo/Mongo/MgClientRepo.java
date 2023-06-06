package com.projeto.TesteMf.Repo.Mongo;

import com.projeto.TesteMf.auto.accont.Client;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MgClientRepo
        extends MongoRepository<Client, String>
{
}
