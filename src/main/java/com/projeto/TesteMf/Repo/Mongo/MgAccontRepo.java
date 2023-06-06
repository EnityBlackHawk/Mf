package com.projeto.TesteMf.Repo.Mongo;

import com.projeto.TesteMf.auto.accont.Accont;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MgAccontRepo
        extends MongoRepository<Accont, String>
{
}
