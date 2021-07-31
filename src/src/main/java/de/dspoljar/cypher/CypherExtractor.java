package de.dspoljar.cypher;

import java.util.Arrays;

public class CypherExtractor
{

    public CypherExtractor()
    {


    }

    public String[] saveLabels(String label)
    {

        String[] storage = new String[2]; // Only 2 for testing purposes.

        storage[0] = label;

        return storage;


    }

    public String[] saveVariables(String variable)
    {

        String[] storage = new String[2]; // Only 2 for testing purposes.

        storage[0] = variable;

        return storage;


    }


}
