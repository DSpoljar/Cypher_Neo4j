package de.dspoljar.cypher;

import java.util.Arrays;

public class CypherExtractor
{

    public String[] labelStorage = new String[2];
    public String[] variableStorage = new String[2];

    public CypherExtractor()
    {


    }

    public String[] saveLabels(String label)
    {



        this.labelStorage[0]= label;

        return this.labelStorage;


    }

    public String[] saveVariables(String variable)
    {



        this.variableStorage[0] = variable;

        return this.variableStorage;


    }


}
