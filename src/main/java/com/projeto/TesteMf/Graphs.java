package com.projeto.TesteMf;

import dag.model.RelationshipEdge;
import dag.model.RelationshipEdgeType;
import dag.model.TableColumnVertex;
import dag.model.TableVertex;
import dag.nosql_schema.NoSQLSchema;
import dag.persistence.JSONPersistence;
import dag.persistence.NoSQLSchemaJson;
import org.jgrapht.graph.DirectedAcyclicGraph;

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
        switch (profile)
        {
            case 1: CreateStruct1();
                break;

            default: throw new RuntimeException("Profile not defined");
        }

        return schema;
    }


    private void CreateStruct1()
    {

        DirectedAcyclicGraph<TableVertex, RelationshipEdge> entityGraph = new DirectedAcyclicGraph<>(RelationshipEdge.class);

        TableVertex accontTableS = new TableVertex("accontSource", "accont", "id");
        accontTableS.setId(idCount++);
        accontTableS.getTypeFields().add(new TableColumnVertex("id", "int4", true, false));
        accontTableS.getTypeFields().add(new TableColumnVertex("id_client", "int4", false, true));
        accontTableS.getTypeFields().add(new TableColumnVertex("value", "numeric", false, false));

        TableVertex accontTableD = new TableVertex("accontDest", "accont", "id");
        accontTableD.setId(idCount++);
        accontTableD.getTypeFields().add(new TableColumnVertex("id", "int4", true, false));
        accontTableD.getTypeFields().add(new TableColumnVertex("id_client", "int4", false, true));
        accontTableD.getTypeFields().add(new TableColumnVertex("value", "numeric", false, false));

        TableVertex clientTable = new TableVertex("client", "client", "id");
        clientTable.setId(idCount++);
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
        rel.setOneSideEntityId(3);
        rel.setManySideEntityId(1);

        entityGraph.addVertex(accontTableS);
        entityGraph.addVertex(clientTable);
        entityGraph.addVertex(accontTableD);
        //                  source        target
        entityGraph.addEdge(clientTable, accontTableS, rel);


        RelationshipEdge rel_2 = new RelationshipEdge(
                RelationshipEdgeType.EMBED_ONE_TO_MANY,
                "client",
                "accontDest",
                "id",
                "id_client"
        );
        rel_2.setOneSideEntityId(3);
        rel_2.setManySideEntityId(2);


        entityGraph.addEdge(clientTable, accontTableD, rel_2);

        addFieldsFromColumnField(accontTableS);
        addFieldsFromColumnField(accontTableD);
        addFieldsFromColumnField(clientTable);

        this.schema.getEntities().add(entityGraph);




        DirectedAcyclicGraph<TableVertex, RelationshipEdge> entityGraph1 = new DirectedAcyclicGraph<>(RelationshipEdge.class);
        TableVertex exchangeGraph = new TableVertex("exchange", "exchange", "id");
        exchangeGraph.setId(idCount++); // 4
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
        rel2.setOneSideEntityId(1);
        rel2.setManySideEntityId(4);

        RelationshipEdge rel3 = new RelationshipEdge(
                RelationshipEdgeType.REF_ONE_TO_MANY,
                "accontDest",
                "exchange",
                "id",
                "id_conta_dest"
        );

        rel3.setOneSideEntityId(2);
        rel3.setManySideEntityId(4);

        this.schema.getRefEntities().add(rel2);
        this.schema.getRefEntities().add(rel3);
    }

    public void CreateStruct3()
    {
        DirectedAcyclicGraph<TableVertex, RelationshipEdge> entityGraph = new DirectedAcyclicGraph<>(RelationshipEdge.class);

        TableVertex accontTable = new TableVertex("accont", "accont", "id");
        accontTable.setId(idCount++);
        accontTable.getTypeFields().add(new TableColumnVertex("id", "int4", true, false));
        accontTable.getTypeFields().add(new TableColumnVertex("id_client", "int4", false, true));
        accontTable.getTypeFields().add(new TableColumnVertex("value", "numeric", false, false));


        TableVertex clientTable = new TableVertex("client", "client", "id");
        clientTable.setId(idCount++);
        clientTable.getTypeFields().add( new TableColumnVertex("id", "int4", true, false));
        clientTable.getTypeFields().add( new TableColumnVertex("cpf", "text", false, false));
        clientTable.getTypeFields().add( new TableColumnVertex("name", "text", false, false));
        clientTable.getTypeFields().add( new TableColumnVertex("address", "text", false, false));
        clientTable.getTypeFields().add( new TableColumnVertex("phone", "text", false, false));

        RelationshipEdge rel = new RelationshipEdge(
                RelationshipEdgeType.REF_ONE_TO_MANY,
                "client",
                "accont",
                "id",
                "id_client"
        );
        rel.setOneSideEntityId(2);
        rel.setManySideEntityId(1);

        entityGraph.addVertex(accontTable);
        entityGraph.addVertex(clientTable);
        //                  source        target
        entityGraph.addEdge(clientTable, accontTable, rel);

        addFieldsFromColumnField(accontTable);
        addFieldsFromColumnField(clientTable);

        this.schema.getEntities().add(entityGraph);




        DirectedAcyclicGraph<TableVertex, RelationshipEdge> entityGraph1 = new DirectedAcyclicGraph<>(RelationshipEdge.class);
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
                "accont",
                "exchange",
                "id",
                "id_conta_source"
        );
        rel2.setOneSideEntityId(1);
        rel2.setManySideEntityId(3);

        RelationshipEdge rel3 = new RelationshipEdge(
                RelationshipEdgeType.REF_ONE_TO_MANY,
                "accont",
                "exchange",
                "id",
                "id_conta_dest"
        );

        rel3.setOneSideEntityId(1);
        rel3.setManySideEntityId(3);

        this.schema.getRefEntities().add(rel2);
        this.schema.getRefEntities().add(rel3);
    }

    @Deprecated
    public void CreateAccont()
    {
        DirectedAcyclicGraph<TableVertex, RelationshipEdge> entityGraph = new DirectedAcyclicGraph<>(RelationshipEdge.class);

        TableVertex accontTable = new TableVertex("accont", "accont", "id");
        accontTable.setId(idCount++);
        accontTable.getTypeFields().add(new TableColumnVertex("id", "int4", true, false));
        accontTable.getTypeFields().add(new TableColumnVertex("id_client", "int4", false, true));
        accontTable.getTypeFields().add(new TableColumnVertex("value", "numeric", false, false));


        TableVertex clientTable = new TableVertex("client", "client", "id");
        clientTable.setId(idCount++);
        clientTable.getTypeFields().add( new TableColumnVertex("id", "int4", true, false));
        clientTable.getTypeFields().add( new TableColumnVertex("cpf", "text", false, false));
        clientTable.getTypeFields().add( new TableColumnVertex("name", "text", false, false));
        clientTable.getTypeFields().add( new TableColumnVertex("address", "text", false, false));
        clientTable.getTypeFields().add( new TableColumnVertex("phone", "text", false, false));

        RelationshipEdge rel = new RelationshipEdge(
            RelationshipEdgeType.EMBED_ONE_TO_MANY,
                "client",
                "accont",
                "id",
                "id_client"
        );
        rel.setOneSideEntityId(2);
        rel.setManySideEntityId(1);

        entityGraph.addVertex(accontTable);
        entityGraph.addVertex(clientTable);
        //                  source        target
        entityGraph.addEdge(clientTable, accontTable, rel);

        addFieldsFromColumnField(accontTable);
        addFieldsFromColumnField(clientTable);

        this.schema.getEntities().add(entityGraph);
    }

    @Deprecated
    private void CreateExchange()
    {
        DirectedAcyclicGraph<TableVertex, RelationshipEdge> entityGraph = new DirectedAcyclicGraph<>(RelationshipEdge.class);
        TableVertex exchangeGraph = new TableVertex("exchange", "exchange", "id");
        exchangeGraph.setId(idCount++); // 3
        exchangeGraph.getTypeFields().add(new TableColumnVertex("id", "int4", true, false));
        exchangeGraph.getTypeFields().add(new TableColumnVertex("id_conta_source", "int4", false, true));
        exchangeGraph.getTypeFields().add(new TableColumnVertex("id_conta_dest", "int4", false, true));
        exchangeGraph.getTypeFields().add(new TableColumnVertex("value", "numeric", false, false));

        entityGraph.addVertex(exchangeGraph);
        addFieldsFromColumnField(exchangeGraph);

        this.schema.getEntities().add(entityGraph);

        RelationshipEdge rel = new RelationshipEdge(
                RelationshipEdgeType.REF_ONE_TO_MANY,
                "accont",
                "exchange",
                "id",
                "id_conta_source"
        );
        rel.setOneSideEntityId(1);
        rel.setManySideEntityId(3);

        RelationshipEdge rel2 = new RelationshipEdge(
                RelationshipEdgeType.REF_ONE_TO_MANY,
                "accont",
                "exchange",
                "id",
                "id_conta_dest"
        );

        rel2.setOneSideEntityId(1);
        rel2.setManySideEntityId(3);

        this.schema.getRefEntities().add(rel);
        this.schema.getRefEntities().add(rel2);
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
