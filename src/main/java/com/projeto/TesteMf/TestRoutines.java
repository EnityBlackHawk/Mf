package com.projeto.TesteMf;

import com.projeto.TesteMf.Model.Accont;
import com.projeto.TesteMf.Model.Client;
import com.projeto.TesteMf.Model.Exchange;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class TestRoutines {

    @Autowired
    private Repositories repositories;


    public static final int CLIENTS_SIZE = 10;
    public static final int ACCONT_SIZE = 10;
    public static final int EXCHANGE_SIZE = 10;


    public void RemoveAll()
    {
        repositories.exchangeRepo.deleteAll();
        repositories.accontRepo.deleteAll();
        repositories.clientRepo.deleteAll();
    }


    public void InsertRandonValues()
    {

        var listOfClients = new ArrayList<Client>();
        var listOfAcconts = new ArrayList<Accont>();
        var listOfEchanges = new ArrayList<Exchange>();


        // Create clients
        for(int i = 1; i <= CLIENTS_SIZE; i++)
        {
            var c = new Client(i,
                    "Cliente " + Integer.toString(i),
                    "000.000.000-00",
                    "Rua x",
                    "(46) 99000-0000");
            listOfClients.add(c);
            repositories.clientRepo.save(c);
        }

        // Create Acconts
        for(int i = 1; i <= ACCONT_SIZE; i++)
        {
            var randomClientIndex = (int)((Math.random() * (CLIENTS_SIZE - 1)) + 1);
            var a = new Accont(i, Math.random() * 100, repositories.clientRepo.findById(randomClientIndex).orElseThrow());
            listOfAcconts.add(a);
            repositories.accontRepo.save(a);
        }


        // Create Exchanges
        for(int i = 1; i <= EXCHANGE_SIZE; i++)
        {
            var randomAccont1 = (int)((Math.random() * (ACCONT_SIZE - 1)) + 1);
            var randomAccont2 = (int)((Math.random() * (ACCONT_SIZE - 1)) + 1);

            while (randomAccont2 == randomAccont1)
            {
                randomAccont2 = (int)((Math.random() * (ACCONT_SIZE - 1)) + 1);
            }

            var e = new Exchange(i,
                    listOfAcconts.get(randomAccont1),
                    listOfAcconts.get(randomAccont2),
                    Math.random() * 100);
            listOfEchanges.add(e);
            repositories.exchangeRepo.save(e);
        }
    }

    public void Routine1()
    {
        repositories.mgAccontRepo.deleteAll();


        var listOfAcconts = repositories.accontRepo.findAll();
        var listOfClients = repositories.clientRepo.findAll();

        ModelMapper mm = new ModelMapper();

        for(int i = 0; i < listOfAcconts.size(); i++)
        {
            var a = listOfAcconts.get(i);
            var am = new com.projeto.TesteMf.auto.accont.Accont();
            am.setId(a.getId());

            var c = mm.map(a.getClient(), com.projeto.TesteMf.auto.accont.Client.class);

            am.setClient(
                    c
            );
            am.setValue(a.getValue());

            var l = repositories.getExchangesByAccont(a.getId());

            List<com.projeto.TesteMf.auto.exchange.Exchange> listOfExchanges =
                    l.stream().map((exchange ->
                            mm.map(exchange, com.projeto.TesteMf.auto.exchange.Exchange.class))).toList();

            am.setExchange(listOfExchanges);

            repositories.mgAccontRepo.insert(am);

        }

    }

}
