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

      //  CypherExtractor extractor = new CypherExtractor();

        CypherResultConstructor results = testWalker.acceptQuery(g, query);

        //System.out.println(testWalker.extractSingleNodeLabel(extractor, "n"));

       // System.out.println(testWalker.extractEdgeNodeLabelsAndVariables());

     //   assertEquals(node.getId(), results.get(0).get("n").getId());

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

        CypherResultConstructor results = testWalker.acceptQuery(g, query);



     //   testWalker.acceptQuery(g, query, extractor, results, "IL10");

     //   g.findNodes(node.getLabel(), "symbol", "IL10");




        //Assertions.assertEquals("\"IL10\"", testWalker.extractMapNodeLabel(extractor, "IL10"));

        // System.out.println(testWalker.extractMapNodeLabel());

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


        CypherWalker testWalker = new CypherWalker();

        CypherExtractor extractor = new CypherExtractor();

       // g.getAdjacentNodeIdsForEdgeLabel(n.getId(), r.getLabel());




        CypherResultConstructor results = testWalker.acceptQuery(g, query);
      //  Assertions.assertEquals("{[Protein]=p, [CODES_FOR]=r, [Gene]=g}", testWalker.extractEdgeNodeLabels().toString());

       // System.out.println(testWalker.extractEdgeNodeLabelsAndVariables());



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

        CypherResultConstructor resultObject = testWalker.acceptQuery(g, query);


       // testWalker.acceptQuery(g, query, extractor, results, "e");

       // System.out.println(testWalker.extractVariableFromWhereQuery("e"));



    }







}