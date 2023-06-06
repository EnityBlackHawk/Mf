package com.projeto.TesteMf;


import com.projeto.TesteMf.Repo.AccontRepo;
import com.projeto.TesteMf.Repo.ClientRepo;
import com.projeto.TesteMf.Repo.ExchangeRepo;
import com.projeto.TesteMf.Repo.Mongo.MgAccontRepo;
import com.projeto.TesteMf.Repo.Mongo.MgClientRepo;
import com.projeto.TesteMf.Repo.Mongo.MgExchangeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Controller
public class Repositories {

    @Autowired
    public AccontRepo accontRepo;
    @Autowired
    private ClientRepo clientRepo;

    @Autowired
    public ExchangeRepo exchangeRepo;

    @Autowired
    public MgAccontRepo mgAccontRepo;
    @Autowired
    public MgClientRepo mgClientRepo;

    @Autowired
    public MgExchangeRepo mgExchangeRepo;

    public ClientRepo getClientRepo() {
        return clientRepo;
    }
}
