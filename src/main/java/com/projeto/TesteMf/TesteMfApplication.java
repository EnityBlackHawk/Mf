package com.projeto.TesteMf;


import com.projeto.TesteMf.auto.accont.Accont;
import com.projeto.TesteMf.auto.exchange.Exchange;
import dag.nosql_schema.NoSQLSchema;
import mf.customization.spring.MfSpringMongoCustomization;

import mf.generator.RdbTypeEnum;
import mf.schema.MfDagSchemaGenerator;
import mf.schema.MfSchemaGenerator;
import mf.utils.GraphUtils;

import org.modelmapper.ModelMapper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.io.FileNotFoundException;
import java.util.Objects;

@SpringBootApplication
@EnableMongoRepositories
public class TesteMfApplication {

	private static final boolean GENERATE = false;
	private static final boolean RUN_TEST = false;
	private static final boolean MAKE_DAG = true;

	public static void main(String[] args) {
		var context = SpringApplication.run(TesteMfApplication.class, args);

		if(MAKE_DAG) {
			var dag = new Graphs("Bank5", "E:\\Projeto", 1);
			dag.GenerateAndSave();
		}

		if (GENERATE) {
			NoSQLSchema schema = GraphUtils.loadNosqlSchema("E:\\Projeto\\Bank5.json");

			MfSchemaGenerator schemaCodeGenerator = new MfDagSchemaGenerator(
					schema,
					new MfSpringMongoCustomization(),
					RdbTypeEnum.POSTGRES
			);
			schemaCodeGenerator.generate("com.projeto.TesteMf.auto");
			try {
				schemaCodeGenerator.saveFiles();
			} catch (FileNotFoundException e) {
				throw new RuntimeException(e);
			}
		}

		if(RUN_TEST) {
			ModelMapper mm = new ModelMapper();
			var repos = context.getBean(Repositories.class);
			repos.mgAccontRepo.deleteAll();
			repos.mgExchangeRepo.deleteAll();

			for(var a : repos.getClientRepo().findAll())
				repos.mgAccontRepo.insert(
						mm.map(a, Accont.class)
				);

			var exs = repos.exchangeRepo.findAll();
			var accs = repos.mgAccontRepo.findAll();

			for(var ex : exs)
			{
				var m_ex = mm.map(ex, Exchange.class);

				for (var a : accs)
				{
					if(Objects.equals(ex.getAccontDest().getId(), a.getId()))
					{
						m_ex.setAccontDest(a);
					}
					else if(Objects.equals(ex.getAccontSource().getId(), a.getId()))
					{
						m_ex.setAccontSource(a);
					}
				}
				repos.mgExchangeRepo.insert(m_ex);
			}


			for(var a : repos.mgAccontRepo.findAll())
			{
				System.out.println(a.getClient().getName());
			}
			System.out.println();
			for(var e : repos.mgExchangeRepo.findAll())
			{
				System.out.println(e.getAccontDest().getClient().getName());
			}






		}

	}
}
