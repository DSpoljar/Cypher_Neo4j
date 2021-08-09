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
       // node = g.findNode("Gene", "test", "Hello");

        CypherWalker testWalker = new CypherWalker();

        CypherExtractor extractor = new CypherExtractor();

        testWalker.acceptQuery(g, "MATCH (n:Gene) RETURN n", extractor);


    }

    @Test
    public void matchMapNodeLabel() throws IOException
    {

        final Graph g = Graph.createTempGraph();
        Node node = g.addNode("Gene");
        node.setProperty("n", "IL10");
        g.update(node);
        // node = g.findNode("Gene", "test", "Hello");

        CypherWalker testWalker = new CypherWalker();

        CypherExtractor extractor = new CypherExtractor();

        testWalker.acceptQuery(g, "MATCH (n:Gene {\"symbol\": \"IL10\"})", extractor);


    }

    @Test
    public void matchChainedLabel() throws IOException
    {

        final Graph g = Graph.createTempGraph();
        Node node = g.addNode("Gene");
        node.setProperty("p", "Protein");
        node.setProperty("g", "Gene");
        node.setProperty("r", "CODES_FOR");
        g.update(node);
        // node = g.findNode("Gene", "test", "Hello");

        CypherWalker testWalker = new CypherWalker();

        CypherExtractor extractor = new CypherExtractor();

        testWalker.acceptQuery(g, "MATCH (g:Gene)-[r:CODES_FOR]->(p:Protein) RETURN g, r, p", extractor);


    }

    @Test
    public void matchExtendedLabel() throws IOException
    {

    // TODO: Later.

    }






}