package de.dspoljar.cypher;

import de.unibi.agbi.biodwh2.core.model.graph.Edge;
import de.unibi.agbi.biodwh2.core.model.graph.Graph;
import de.unibi.agbi.biodwh2.core.model.graph.Node;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class CypherWalkerTest
{

    @Test
    public void matchSingleNodeLabel() throws IOException
    {

        final Graph g = Graph.createTempGraph();
        Node node = g.addNode("Gene");
        node.setProperty("n", "IL10");
        g.update(node);

        String query = "MATCH (n:Gene) RETURN n";

        CypherWalker testWalker = new CypherWalker();

        CypherExtractor extractor = new CypherExtractor();

        CypherResultConstructor results = new CypherResultConstructor();

        results.nodeList.add(node);
        results.nodePropertyList.put("n", node.getProperty("n"));
        results.nodePropertyHashMapList.add(results.nodePropertyList);


        g.findNodes(node.getLabel(), "n");

        testWalker.acceptQuery(g, query, extractor, results);

        Assertions.assertEquals("n", testWalker.extractSingleNodeLabel(extractor, "n"));

        System.out.println(testWalker.extractSingleNodeLabel(extractor, "n"));

       System.out.println(results.concatNodeResults());


       // node = g.findNode("Gene", "test", "n");


    }

    @Test
    public void matchMapNodeLabel() throws IOException
    {

        final Graph g = Graph.createTempGraph();
        Node node = g.addNode("Gene");
        node.setProperty("symbol", "IL10"); //IL10
        g.update(node);
        // node = g.findNode("Gene", "test", "Hello");

        String query = "MATCH (n:Gene {symbol: \"IL10\"}) RETURN n";

        CypherResultConstructor results = new CypherResultConstructor();

        CypherWalker testWalker = new CypherWalker();

        CypherExtractor extractor = new CypherExtractor();

        testWalker.acceptQuery(g, query, extractor, results);

        g.findNodes(node.getLabel(), "symbol", "IL10");


        Assertions.assertEquals("\"IL10\"", testWalker.extractMapNodeLabel(extractor, "IL10"));

        System.out.println(testWalker.extractMapNodeLabel(extractor, "IL10"));

    }

    @Test
    public void matchChainedLabel() throws IOException
    {

        final Graph g = Graph.createTempGraph();
        Node n = g.addNode("Gene");
        Node p = g.addNode("Protein");
        Edge r = g.addEdge(n, p, "CODES_FOR");

        String query = "MATCH (g:Gene)-[r:CODES_FOR]->(p:Protein) RETURN g, r, p";

        CypherResultConstructor results = new CypherResultConstructor();

        // node = g.findNode("Gene", "test", "Hello");

        CypherWalker testWalker = new CypherWalker();

        CypherExtractor extractor = new CypherExtractor();

        g.getAdjacentNodeIdsForEdgeLabel(n.getId(), r.getLabel());

        testWalker.acceptQuery(g, query, extractor, results);

      //  Assertions.assertEquals("{[Protein]=p, [CODES_FOR]=r, [Gene]=g}", testWalker.extractEdgeNodeLabels().toString());

        System.out.println(testWalker.extractEdgeNodeLabels());



    }

    @Test
    public void matchWhereQuery() throws IOException
    {

        final Graph g = Graph.createTempGraph();
        Node n = g.addNode("Gene");
        n.setProperty("name", "Test");
        g.update(n);

        String query = "MATCH (n:Gene) WHERE n.name CONTAINS 'e' RETURN n.name";

        CypherResultConstructor results = new CypherResultConstructor();

        // node = g.findNode("Gene", "test", "Hello");

        CypherWalker testWalker = new CypherWalker();

        CypherExtractor extractor = new CypherExtractor();

        testWalker.acceptQuery(g, query, extractor, results);

        System.out.println(testWalker.extractVariableFromWhereQuery(extractor, "e"));



    }

    @Test
    public void matchWhereQuery2() throws IOException
    {


    }






}