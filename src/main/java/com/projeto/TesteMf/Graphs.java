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

    public Graphs(String name, String path)
    {
        schema = new NoSQLSchema(name);
        this.name = name;
        this.path = path;
    }

    public NoSQLSchema generate()
    {
        CreateAccont();
        CreateExchange();
        return schema;
    }

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
