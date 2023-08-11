package com.projeto.TesteMf;

import com.projeto.TesteMf.Model.Accont;
import com.projeto.TesteMf.Model.Client;
import com.projeto.TesteMf.Model.Exchange;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
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
        RemoveAll();
        InsertRandonValues();

        var listOfAcconts = repositories.accontRepo.findAll();
        var listOfExchanges = repositories.exchangeRepo.findAll();

        ModelMapper mm = new ModelMapper();

        for(int i = 0; i < listOfAcconts.size(); i++)
        {
            var a = listOfAcconts.get(i);
            var am = new com.projeto.TesteMf.auto.accont.Accont();
            am.setId(a.getId());


            var lm = Arrays.stream(am.getClass().getDeclaredMethods()).filter(m -> m.getName().equals("setClient")).toList();

            if(lm.isEmpty()) throw new RuntimeException("Could not find method");
            var paramType = Arrays.stream(lm.get(0).getParameterTypes()).findFirst().orElse(null);
            if(paramType == null) throw new RuntimeException("Param was null");
            var c = mm.map(a.getClient(), paramType);

            try {
                lm.get(0).invoke(am, c);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }

            am.setValue(a.getValue());

            List<com.projeto.TesteMf.auto.exchange.Exchange> e = new ArrayList<>();

            repositories.mgAccontRepo.insert(am);
        }

        listOfExchanges.forEach(e -> {
            var em = mm.map(e, com.projeto.TesteMf.auto.exchange.Exchange.class);
            em.setId_conta_source(e.getAccontSource().getId());
            em.setId_conta_dest(e.getAccontDest().getId());
            repositories.mgExchangeRepo.insert(em);
        });

        System.out.print("Insersão concluida");
        System.out.print("Iniciando teste de integridade");

    }

    public void Routine2()
    {
        RemoveAll();

        var listOfExchanges = repositories.exchangeRepo.findAll();

        ModelMapper mm = new ModelMapper();

        listOfExchanges.forEach( e -> {
            var em = mm.map(e, com.projeto.TesteMf.auto.exchange.Exchange.class);
            repositories.mgExchangeRepo.insert(em);
        });
    }


    public void Routine3()
    {
        RemoveAll();

        var listOfAcconts = repositories.accontRepo.findAll();
        var listOfExchanges = repositories.exchangeRepo.findAll();
        var listOfClients = repositories.clientRepo.findAll();

        ModelMapper mm = new ModelMapper();

        listOfClients.forEach( c -> {
            repositories.mgClientRepo.insert(mm.map(c, com.projeto.TesteMf.auto.client.Client.class));
        });

        listOfAcconts.forEach( a -> {
            var am = mm.map(a, com.projeto.TesteMf.auto.accont.Accont.class);
            am.setId_client(a.getClient().getId());
            repositories.mgAccontRepo.insert(am);
        });

        listOfExchanges.forEach(e -> {
            var em = mm.map(e, com.projeto.TesteMf.auto.exchange.Exchange.class);
            em.setId_conta_source(e.getAccontSource().getId());
            em.setId_conta_dest(e.getAccontDest().getId());
            repositories.mgExchangeRepo.insert(em);
        });

    }

}
