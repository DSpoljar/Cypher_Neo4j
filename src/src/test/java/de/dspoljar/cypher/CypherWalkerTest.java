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
        CypherResultConstructor results = testWalker.acceptQuery(g, query);
       // System.out.println("Testing... "+results.resultVarsNodes.get("n").getId());

        assertEquals(node.getId(), results.resultVarsNodes.get("n").getId());




    }

    @Test
    public void matchMapNodeLabel() throws IOException
    {

        final Graph g = Graph.createTempGraph();
        Node node = g.addNode("Gene");
        node.setProperty("symbol", "IL10"); //IL10
        g.update(node);

        String query = "MATCH (n:Gene {symbol: \"IL10\"}) RETURN n";


        CypherWalker testWalker = new CypherWalker();


        CypherResultConstructor results = testWalker.acceptQuery(g, query);



    }

    @Test
    public void matchChainedLabel() throws IOException
    {

        final Graph g = Graph.createTempGraph();
        Node n = g.addNode("Gene");
        n.setProperty("n", "IL10");
        g.update(n);
        Node p = g.addNode("Protein");
        p.setProperty("p", "AL10");
        g.update(p);
        Edge r = g.addEdge(n, p, "CODES_FOR");
        String query = "MATCH (g:Gene)-[r:CODES_FOR]->(p:Protein) RETURN g, r, p";
        g.update(r);
        CypherWalker testWalker = new CypherWalker();

       // g.getAdjacentNodeIdsForEdgeLabel(n.getId(), r.getLabel());


        CypherResultConstructor results = testWalker.acceptQuery(g, query);

        //System.out.println("Testing... "+results.resultVarsNodes.get("g").getId());
       // System.out.println("Testing... "+results.resultVarsNodes.get("p").getId());

      //  assertEquals(n.getId(), results.resultVarsNodes.get("g").getId());
       // assertEquals(p.getId(), results.resultVarsNodes.get("p").getId());
      //  Assertions.assertEquals("{[Protein]=p, [CODES_FOR]=r, [Gene]=g}", testWalker.extractEdgeNodeLabels().toString());




    }

    @Test
    public void matchWhereQuery() throws IOException
    {

        final Graph g = Graph.createTempGraph();
        Node n = g.addNode("Gene");
        n.setProperty("name", "e");
        g.update(n);
        String query = "MATCH (n:Gene) WHERE n.name CONTAINS 'e' RETURN n.name";
        CypherWalker testWalker = new CypherWalker();
        CypherResultConstructor resultObject = testWalker.acceptQuery(g, query);


       // testWalker.acceptQuery(g, query, extractor, results, "e");

       // System.out.println(testWalker.extractVariableFromWhereQuery("e"));



    }







}