package de.dspoljar.cypher;

import de.unibi.agbi.biodwh2.core.model.graph.Node;

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
        public HashMap<String, String> nodesLabels = new HashMap<String, String>();

        public List<String> variableList = new ArrayList<String>();

        public HashMap<String, String> variableCollector = new HashMap<String, String>();

        public HashMap<String, String> edgeCollector = new HashMap<String, String>();

        public List<String> edgeAndNodeList = new ArrayList<String>();

        public HashMap<String, String> whereVarCollector = new HashMap<String, String>();

        public List<String> whereVarList = new ArrayList<String>();

        public HashMapper()
        {
            super();
        }


        public void mapper(String node, String variable)
        {


            this.nodesLabels.put(node, variable);
            this.variableList.add(variable);


        }

        public void edgeMapper(List<String> nodeEdgeList, List<String> variableList)
        {



            for (int i = 0; i  < nodeEdgeList.size(); i++)
            {


                this.edgeCollector.put(nodeEdgeList.get(i), variableList.get(i));


            }


        }

        public void edgeMapper_2(String nodeLabel, String content)
        {



                this.edgeCollector.put(nodeLabel, content);


        }

        public void whereMapper(String node, String variable)
        {

            this.whereVarCollector.put(node, variable);
            this.whereVarList.add(variable);

        }


        public HashMap<String, String> getVariableCollector()
        {
            return variableCollector;
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
