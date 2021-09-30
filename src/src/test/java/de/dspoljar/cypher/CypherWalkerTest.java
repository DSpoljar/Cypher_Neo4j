package de.dspoljar.cypher;

import de.unibi.agbi.biodwh2.core.model.graph.Edge;
import de.unibi.agbi.biodwh2.core.model.graph.Graph;
import de.unibi.agbi.biodwh2.core.model.graph.Node;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;

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

        testWalker.acceptQuery(g, query, extractor, results, "n");

        Assertions.assertEquals("n", testWalker.extractSingleNodeLabel(extractor, "n"));

        //System.out.println(testWalker.extractSingleNodeLabel(extractor, "n"));

        //System.out.println(results.concatResults(results.nodeList, results.nodePropertyHashMapList));


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


        CypherWalker testWalker = new CypherWalker();

        CypherExtractor extractor = new CypherExtractor();

        CypherResultConstructor results = new CypherResultConstructor();

        results.nodeList.add(node);
        results.nodePropertyList.put("symbol", node.getProperty("symbol"));
        results.nodePropertyHashMapList.add(results.nodePropertyList);


        testWalker.acceptQuery(g, query, extractor, results, "IL10");

        g.findNodes(node.getLabel(), "symbol", "IL10");


        Assertions.assertEquals("\"IL10\"", testWalker.extractMapNodeLabel(extractor, "IL10"));

         System.out.println(testWalker.extractMapNodeLabel(extractor, "IL10"));

    }

    @Test
    public void matchChainedLabel() throws IOException
    {

        final Graph g = Graph.createTempGraph();
        Node n = g.addNode("Gene");
        n.setProperty("key1", "IL10");
        Node p = g.addNode("Protein");
        p.setProperty("key2", "AL10");
        Edge r = g.addEdge(n, p, "CODES_FOR");

        String query = "MATCH (g:Gene)-[r:CODES_FOR]->(p:Protein) RETURN g, r, p";


        // node = g.findNode("Gene", "test", "Hello");

        CypherWalker testWalker = new CypherWalker();

        CypherExtractor extractor = new CypherExtractor();

        g.getAdjacentNodeIdsForEdgeLabel(n.getId(), r.getLabel());

        CypherResultConstructor results = new CypherResultConstructor();

        results.nodeList.add(n);
        results.nodeList.add(p);
        results.edgeList.add(r);
        HashMap<String, String> nProperties = results.hashMapFiller("key1", n.getProperty("key1"));
        HashMap<String, String> pProperties = results.hashMapFiller("key2", p.getProperty("key2"));
        results.nodePropertyHashMapList.add(nProperties);
        results.nodePropertyHashMapList.add(pProperties);


        testWalker.acceptQuery(g, query, extractor, results, null);

      //  Assertions.assertEquals("{[Protein]=p, [CODES_FOR]=r, [Gene]=g}", testWalker.extractEdgeNodeLabels().toString());

        System.out.println(testWalker.extractEdgeNodeLabels());



    }

    @Test
    public void matchWhereQuery() throws IOException
    {

        final Graph g = Graph.createTempGraph();
        Node n = g.addNode("Gene");
        n.setProperty("name", "e");
        g.update(n);

        String query = "MATCH (n:Gene) WHERE n.name CONTAINS 'e' RETURN n.name";


        // node = g.findNode("Gene", "test", "Hello");

        CypherWalker testWalker = new CypherWalker();

        CypherExtractor extractor = new CypherExtractor();

        CypherResultConstructor results = new CypherResultConstructor();

        results.nodeList.add(n);
        results.nodePropertyList.put("name", n.getProperty("name"));
        results.nodePropertyHashMapList.add(results.nodePropertyList);

        testWalker.acceptQuery(g, query, extractor, results, "e");

        System.out.println(testWalker.extractVariableFromWhereQuery(extractor, "e"));



    }

    @Test
    public void matchWhereQuery2() throws IOException
    {


    }






}