package com.projeto.TesteMf;

import dag.model.RelationshipEdge;
import dag.model.RelationshipEdgeType;
import dag.model.TableColumnVertex;
import dag.model.TableVertex;
import dag.nosql_schema.NoSQLSchema;
import dag.persistence.JSONPersistence;
import dag.persistence.NoSQLSchemaJson;
import jakarta.persistence.Table;
import org.jgrapht.graph.DirectedAcyclicGraph;

import java.util.ArrayList;

public class Graphs {

    private NoSQLSchema schema;
    private int idCount = 1;

    private final String path;
    private final String name;

    private final int profile;

    public Graphs(String name, String path, int profile)
    {
        schema = new NoSQLSchema(name);
        this.name = name;
        this.path = path;
        this.profile = profile;
    }

    public NoSQLSchema generate()
    {
        switch (profile) {
            case 1 -> CreateStruct1();
            case 2 -> CreateStruct2();
            case 3 -> CreateStruct3();
            default -> throw new RuntimeException("Profile not defined");
        }

        return schema;
    }


    private void CreateStruct1()
    {

        DirectedAcyclicGraph<TableVertex, RelationshipEdge> entityGraph = new DirectedAcyclicGraph<>(RelationshipEdge.class);

        TableVertex accontTableS = new TableVertex("accontSource", "accont", "id");
        accontTableS.setId(idCount++); // 1
        accontTableS.getTypeFields().add(new TableColumnVertex("id", "int4", true, false));
        accontTableS.getTypeFields().add(new TableColumnVertex("id_client", "int4", false, true));
        accontTableS.getTypeFields().add(new TableColumnVertex("value", "numeric", false, false));

        TableVertex clientTable = new TableVertex("client", "client", "id");
        clientTable.setId(idCount++); // 2
        clientTable.getTypeFields().add( new TableColumnVertex("id", "int4", true, false));
        clientTable.getTypeFields().add( new TableColumnVertex("cpf", "text", false, false));
        clientTable.getTypeFields().add( new TableColumnVertex("name", "text", false, false));
        clientTable.getTypeFields().add( new TableColumnVertex("address", "text", false, false));
        clientTable.getTypeFields().add( new TableColumnVertex("phone", "text", false, false));

        RelationshipEdge rel = new RelationshipEdge(
                RelationshipEdgeType.EMBED_ONE_TO_MANY,
                "client",
                "accontSource",
                "id",
                "id_client"
        );
        rel.setOneSideEntityId(clientTable.getId());
        rel.setManySideEntityId(accontTableS.getId());

        entityGraph.addVertex(accontTableS);
        entityGraph.addVertex(clientTable);
        //                  source        target
        entityGraph.addEdge(clientTable, accontTableS, rel);
        this.schema.getEntities().add(entityGraph);

        //----------------------------------------------------//

        DirectedAcyclicGraph<TableVertex, RelationshipEdge> entityGraph2 = new DirectedAcyclicGraph<>(RelationshipEdge.class);

        TableVertex accontTableD = new TableVertex("accontDest", "accont", "id");
        accontTableD.setId(idCount++); // 3
        accontTableD.getTypeFields().add(new TableColumnVertex("id", "int4", true, false));
        accontTableD.getTypeFields().add(new TableColumnVertex("id_client", "int4", false, true));
        accontTableD.getTypeFields().add(new TableColumnVertex("value", "numeric", false, false));

        TableVertex clientTable2 = new TableVertex("client", "client", "id");
        clientTable2.setId(idCount++); // 2
        clientTable2.getTypeFields().add( new TableColumnVertex("id", "int4", true, false));
        clientTable2.getTypeFields().add( new TableColumnVertex("cpf", "text", false, false));
        clientTable2.getTypeFields().add( new TableColumnVertex("name", "text", false, false));
        clientTable2.getTypeFields().add( new TableColumnVertex("address", "text", false, false));
        clientTable2.getTypeFields().add( new TableColumnVertex("phone", "text", false, false));

        entityGraph2.addVertex(accontTableD);
        entityGraph2.addVertex(clientTable2);

        RelationshipEdge rel_2 = new RelationshipEdge(
                RelationshipEdgeType.EMBED_ONE_TO_MANY,
                "client",
                "accontDest",
                "id",
                "id_client"
        );
        rel_2.setOneSideEntityId(clientTable2.getId());
        rel_2.setManySideEntityId(accontTableD.getId());


        entityGraph2.addEdge(clientTable2, accontTableD, rel_2);

        addFieldsFromColumnField(accontTableD);

        this.schema.getEntities().add(entityGraph2);

        //---------------------------------------------------------------------//

        DirectedAcyclicGraph<TableVertex, RelationshipEdge> entityGraph3 = new DirectedAcyclicGraph<>(RelationshipEdge.class);
        TableVertex exchangeGraph = new TableVertex("exchange", "exchange", "id");
        exchangeGraph.setId(idCount++); // 5
        exchangeGraph.getTypeFields().add(new TableColumnVertex("id", "int4", true, false));
        exchangeGraph.getTypeFields().add(new TableColumnVertex("id_conta_source", "int4", false, true));
        exchangeGraph.getTypeFields().add(new TableColumnVertex("id_conta_dest", "int4", false, true));
        exchangeGraph.getTypeFields().add(new TableColumnVertex("value", "numeric", false, false));

        entityGraph3.addVertex(exchangeGraph);
        addFieldsFromColumnField(exchangeGraph);

        this.schema.getEntities().add(entityGraph3);

        RelationshipEdge rel2 = new RelationshipEdge(
                RelationshipEdgeType.REF_ONE_TO_MANY,
                "accontSource",
                "exchange",
                "id",
                "id_conta_source"
        );
        rel2.setOneSideEntityId(1);
        rel2.setManySideEntityId(5);

        RelationshipEdge rel3 = new RelationshipEdge(
                RelationshipEdgeType.REF_ONE_TO_MANY,
                "accontDest",
                "exchange",
                "id",
                "id_conta_dest"
        );

        rel3.setOneSideEntityId(3);
        rel3.setManySideEntityId(5);

        this.schema.getRefEntities().add(rel2);
        this.schema.getRefEntities().add(rel3);
    }

    public void CreateStruct2() {
        var entityGraph = new DirectedAcyclicGraph<TableVertex, RelationshipEdge>(RelationshipEdge.class);

        TableVertex exchangeGraph = new TableVertex("exchange", "exchange", "id");
        exchangeGraph.setId(idCount++);
        exchangeGraph.getTypeFields().add(new TableColumnVertex("id", "int4", true, false));
        exchangeGraph.getTypeFields().add(new TableColumnVertex("id_conta_source", "int4", false, true));
        exchangeGraph.getTypeFields().add(new TableColumnVertex("id_conta_dest", "int4", false, true));
        exchangeGraph.getTypeFields().add(new TableColumnVertex("value", "numeric", false, false));

        TableVertex accontTable = new TableVertex("accontSource", "accont", "id");
        accontTable.setId(idCount++);
        accontTable.getTypeFields().add(new TableColumnVertex("id", "int4", true, false));
        accontTable.getTypeFields().add(new TableColumnVertex("id_client", "int4", false, true));
        accontTable.getTypeFields().add(new TableColumnVertex("value", "numeric", false, false));

        TableVertex accontTableDest = new TableVertex("accontDest", "accont", "id");
        accontTableDest.setId(idCount++);
        accontTableDest.getTypeFields().add(new TableColumnVertex("id", "int4", true, false));
        accontTableDest.getTypeFields().add(new TableColumnVertex("id_client", "int4", false, true));
        accontTableDest.getTypeFields().add(new TableColumnVertex("value", "numeric", false, false));

        TableVertex clientTable = new TableVertex("client", "client", "id");
        clientTable.setId(idCount++);
        clientTable.getTypeFields().add(new TableColumnVertex("id", "int4", true, false));
        clientTable.getTypeFields().add(new TableColumnVertex("cpf", "text", false, false));
        clientTable.getTypeFields().add(new TableColumnVertex("name", "text", false, false));
        clientTable.getTypeFields().add(new TableColumnVertex("address", "text", false, false));
        clientTable.getTypeFields().add(new TableColumnVertex("phone", "text", false, false));

        var listOfTableVertex = new ArrayList<TableVertex>();
        listOfTableVertex.add(exchangeGraph);
        listOfTableVertex.add(accontTable);
        listOfTableVertex.add(accontTableDest);
        listOfTableVertex.add(clientTable);

        for (var t : listOfTableVertex) {
            if (!entityGraph.addVertex(t))
                throw new RuntimeException("Erro na adicao do vertex: " + t.getName());
        }


        var rel1 = new RelationshipEdge(
                RelationshipEdgeType.EMBED_ONE_TO_MANY,
                "client",
                "accontSource",
                "id",
                "id_client"
        );
        rel1.setOneSideEntityId(clientTable.getId());
        rel1.setManySideEntityId(accontTable.getId());

        assert entityGraph.addEdge(clientTable, accontTable, rel1) : "Erro na adicao da rel1";


        var rel2 = new RelationshipEdge(
                RelationshipEdgeType.EMBED_ONE_TO_MANY,
                "client",
                "accontDest",
                "id",
                "id_client"
        );
        rel2.setOneSideEntityId(clientTable.getId());
        rel2.setManySideEntityId(accontTableDest.getId());

        assert entityGraph.addEdge(clientTable, accontTableDest, rel2) : "Erro na adicao da rel2";

        RelationshipEdge rel3 = new RelationshipEdge(
                RelationshipEdgeType.EMBED_ONE_TO_MANY,
                "accontSource",
                "exchange",
                "id",
                "id_conta_source"
        );
        rel3.setOneSideEntityId(accontTable.getId());
        rel3.setManySideEntityId(exchangeGraph.getId());

        assert entityGraph.addEdge(accontTable, exchangeGraph, rel3) : "Erro na adicao da rel3";

        RelationshipEdge rel4 = new RelationshipEdge(
                RelationshipEdgeType.EMBED_ONE_TO_MANY,
                "accontDest",
                "exchange",
                "id",
                "id_conta_dest"
        );

        rel4.setOneSideEntityId(accontTableDest.getId());
        rel4.setManySideEntityId(exchangeGraph.getId());
        assert entityGraph.addEdge(accontTableDest, exchangeGraph, rel4) : "Erro na adicao da rel4";

        this.schema.getEntities().add(entityGraph);
    }

    public void CreateStruct3()
    {
        DirectedAcyclicGraph<TableVertex, RelationshipEdge> entityGraph1 = new DirectedAcyclicGraph<>(RelationshipEdge.class);

        TableVertex accontTable = new TableVertex("accontSource", "accont", "id");
        accontTable.setId(idCount++);
        accontTable.getTypeFields().add(new TableColumnVertex("id",         "int4",     true,   false));
        accontTable.getTypeFields().add(new TableColumnVertex("id_client",  "int4",     false,  true));
        accontTable.getTypeFields().add(new TableColumnVertex("value",      "numeric",  false,  false));

        entityGraph1.addVertex(accontTable);
        addFieldsFromColumnField(accontTable);
        this.schema.getEntities().add(entityGraph1);

        var entityGraph1_2 = new DirectedAcyclicGraph<TableVertex, RelationshipEdge>(RelationshipEdge.class);
        TableVertex accontTableDest = new TableVertex("accontDest", "accont", "id");
        accontTableDest.setId(idCount++);
        accontTableDest.getTypeFields().add(new TableColumnVertex("id", "int4", true, false));
        accontTableDest.getTypeFields().add(new TableColumnVertex("id_client", "int4", false, true));
        accontTableDest.getTypeFields().add(new TableColumnVertex("value", "numeric", false, false));

        entityGraph1_2.addVertex(accontTableDest);
        addFieldsFromColumnField(accontTableDest);
        this.schema.getEntities().add(entityGraph1_2);


        var entityGraph2 = new DirectedAcyclicGraph<TableVertex, RelationshipEdge>(RelationshipEdge.class);

        TableVertex clientTable = new TableVertex("client", "client", "id");
        clientTable.setId(idCount++);
        clientTable.getTypeFields().add( new TableColumnVertex("id",        "int4", true, false));
        clientTable.getTypeFields().add( new TableColumnVertex("cpf",       "text", false, false));
        clientTable.getTypeFields().add( new TableColumnVertex("name",      "text", false, false));
        clientTable.getTypeFields().add( new TableColumnVertex("address",   "text", false, false));
        clientTable.getTypeFields().add( new TableColumnVertex("phone",     "text", false, false));

        entityGraph2.addVertex(clientTable);
        addFieldsFromColumnField(clientTable);

        RelationshipEdge rel = new RelationshipEdge(
                RelationshipEdgeType.REF_ONE_TO_MANY,
                "client",
                "accontSource",
                "id",
                "id_client"
        );
        rel.setOneSideEntityId(clientTable.getId());
        rel.setManySideEntityId(accontTable.getId());

        this.schema.getEntities().add(entityGraph2);
        this.schema.getRefEntities().add(rel);

        RelationshipEdge rel1 = new RelationshipEdge(
                RelationshipEdgeType.REF_ONE_TO_MANY,
                "client",
                "accontDest",
                "id",
                "id_client"
        );
        rel1.setOneSideEntityId(clientTable.getId());
        rel1.setManySideEntityId(accontTableDest.getId());

        this.schema.getRefEntities().add(rel1);



        DirectedAcyclicGraph<TableVertex, RelationshipEdge> entityGraph3 = new DirectedAcyclicGraph<>(RelationshipEdge.class);
        TableVertex exchangeGraph = new TableVertex("exchange", "exchange", "id");
        exchangeGraph.setId(idCount++); // 3
        exchangeGraph.getTypeFields().add(new TableColumnVertex("id", "int4", true, false));
        exchangeGraph.getTypeFields().add(new TableColumnVertex("id_conta_source", "int4", false, true));
        exchangeGraph.getTypeFields().add(new TableColumnVertex("id_conta_dest", "int4", false, true));
        exchangeGraph.getTypeFields().add(new TableColumnVertex("value", "numeric", false, false));

        entityGraph1.addVertex(exchangeGraph);
        addFieldsFromColumnField(exchangeGraph);
        this.schema.getEntities().add(entityGraph1);

        RelationshipEdge rel2 = new RelationshipEdge(
                RelationshipEdgeType.REF_ONE_TO_MANY,
                "accontSource",
                "exchange",
                "id",
                "id_conta_source"
        );
        rel2.setOneSideEntityId(accontTable.getId());
        rel2.setManySideEntityId(exchangeGraph.getId());

        RelationshipEdge rel3 = new RelationshipEdge(
                RelationshipEdgeType.REF_ONE_TO_MANY,
                "accontDest",
                "exchange",
                "id",
                "id_conta_dest"
        );

        rel3.setOneSideEntityId(accontTableDest.getId());
        rel3.setManySideEntityId(exchangeGraph.getId());

        this.schema.getRefEntities().add(rel2);
        this.schema.getRefEntities().add(rel3);

    }


    private void addFieldsFromColumnField(TableVertex tableVertex){
        for (TableColumnVertex columnVertex : tableVertex.getTypeFields()){
            tableVertex.getFields().add( columnVertex.getColumnName() );
        }
    }

    public void GenerateAndSave()
    {
        String finalPath = path + "\\" + name + ".json";
        JSONPersistence.saveJSONtoFile(
            NoSQLSchemaJson.toJSON(generate()),
            finalPath
        );

        NoSQLSchema schema = NoSQLSchemaJson.fromJSON(JSONPersistence.loadJSONfromFile(finalPath));
        schema.printSchema();
    }

}
