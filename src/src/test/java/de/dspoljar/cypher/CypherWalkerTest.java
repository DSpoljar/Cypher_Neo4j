package de.dspoljar.cypher;

import de.unibi.agbi.biodwh2.core.model.graph.Graph;
import de.unibi.agbi.biodwh2.core.model.graph.Node;
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
        node.setProperty("test", "Hello");
        g.update(node);


        CypherWalker testWalker = new CypherWalker();

        CypherExtractor extractor = new CypherExtractor();

        testWalker.acceptQuery(g, "MATCH (n:Gene) RETURN n", extractor);

        node = g.findNode("Gene", "test", "n");


    }

    @Test
    public void matchMapNodeLabel() throws IOException
    {

        final Graph g = Graph.createTempGraph();
        Node node = g.addNode("Gene");
        node.setProperty("symbol", "IL10");
        g.update(node);
        // node = g.findNode("Gene", "test", "Hello");

        CypherWalker testWalker = new CypherWalker();

        CypherExtractor extractor = new CypherExtractor();

        testWalker.acceptQuery(g, "MATCH (n:Gene {symbol: \"IL10\"}) RETURN n", extractor);


    }

    @Test
    public void matchChainedLabel() throws IOException
    {

        final Graph g = Graph.createTempGraph();
        Node n = g.addNode("Gene");
        Node p = g.addNode("Protein");
        g.addEdge(n, p, "CODES_FOR");

        // node = g.findNode("Gene", "test", "Hello");

        CypherWalker testWalker = new CypherWalker();

        CypherExtractor extractor = new CypherExtractor();

        testWalker.acceptQuery(g, "MATCH (g:Gene)-[r:CODES_FOR]->(p:Protein) RETURN g, r, p", extractor);


    }

    @Test
    public void matchWhereQuery() throws IOException
    {

        final Graph g = Graph.createTempGraph();
        Node n = g.addNode("Gene");

        // node = g.findNode("Gene", "test", "Hello");

        CypherWalker testWalker = new CypherWalker();

        CypherExtractor extractor = new CypherExtractor();

        testWalker.acceptQuery(g, "MATCH (n:Gene) WHERE n.GENE CONTAINS 'e' RETURN n.name", extractor);
    }

    @Test
    public void matchWhereQuery2() throws IOException
    {

        final Graph g = Graph.createTempGraph();
        Node n = g.addNode("Gene");

        // node = g.findNode("Gene", "test", "Hello");
        //  node.setProperty("symbol", "IL10");

        CypherWalker testWalker = new CypherWalker();

        CypherExtractor extractor = new CypherExtractor();

        testWalker.acceptQuery(g, "MATCH (n:Gene_A) WHERE n.GENE CONTAINS 'A' RETURN n.name", extractor);
    }






}