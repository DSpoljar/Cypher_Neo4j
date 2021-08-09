package de.dspoljar.cypher;

import java.util.*;

public class CypherExtractor
{

    List<String> labelStorage = new ArrayList<String>();
    List<String> variableStorage = new ArrayList<String>();

    public CypherExtractor()
    {

    }

    public List<String> saveLabels(String label)
    {



        this.labelStorage.add(label);

        return this.labelStorage;


    }

    public List<String> saveVariables(String variable)
    {



        this.variableStorage.add(variable);

        return this.variableStorage;


    }

    public class HashMapper
    {
        HashMap<String, String> nodesLabels = new HashMap<String, String>();

        List<String> variableList = new ArrayList<String>();


        public HashMapper()
        {
            super();
        }


        public void mapper(String node, String variable)
        {


            this.nodesLabels.put(node, variable);
            this.variableList.add(variable);


        }

        public HashMap<String, String> getNodesLabels()
        {
            return nodesLabels;
        }

        public String getVariableOutOfList(List<String> variableList, String variable)
        {

            if (variableList.contains(variable))
            {

                return variable;

            }
            else
            {

                return "Variable not in list";

            }

        }

    }


}
