package de.dspoljar.cypher;

import java.io.IOException;


public class Main
{




    public static void main(String[] args) throws IOException
    {

        IOHandler handler = new IOHandler(); // Creating a simple IO machinery as base for upcoming tasks.

        String query = handler.inputReader();

        String testVariable = "MATCH (n:Gene) RETURN n";

        CypherWalker walker = new CypherWalker();

    }




}
