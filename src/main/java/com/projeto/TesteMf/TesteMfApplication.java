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

	private static final boolean MAKE_DAG = false;
	private static final boolean GENERATE = false;
	private static final boolean RUN_TEST = true;
	private static final boolean RUN_ROUTINE = false;

	private static final int PROFILE_NUMBER = 1;


	public static void main(String[] args) {
		var context = SpringApplication.run(TesteMfApplication.class, args);

		if(MAKE_DAG) {
			var dag = new Graphs("Bank5", "E:\\Projeto", PROFILE_NUMBER);
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


		if(RUN_ROUTINE)
		{
			var tr = context.getBean(TestRoutines.class);
			switch (PROFILE_NUMBER)
			{
				case 1 -> tr.Routine1();
				case 2 -> tr.Routine2();
				case 3 -> tr.Routine3();
			}
		}

		if(RUN_TEST) {
			Accont a = new Accont();
			a.setId(1);
			a.set_id("1");
			a.setClient(null);
			a.setExchange(null);
			a.setId_client(1);
			a.setValue(15.0);
			Accont b = new Accont();
			b.setId(1);
			b.set_id("1");
			b.setClient(null);
			b.setExchange(null);
			b.setId_client(1);
			b.setValue(15.0);

			var rest =  ObjectEqualsKt.hasSameValues(a, b);
			System.out.println(rest);
		}

	}
}
