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



    public class HashMapper
    {


        public LinkedList<String> NodeStringList = new LinkedList<String>();

        public List<String> variableNodeList = new ArrayList<String>(); // Variables as "n, g, ..." pertaining to Nodes. Node Gene -> n etc.

        public List<String> variableEdgeList = new ArrayList<String>(); // Variables ... pertaining to Edges.

        public LinkedList<String> EdgeStringList = new LinkedList<String>();

        public HashMap<String, String> nodesLabelsMap = new HashMap<String, String>(); // Labels of nodes related to their variables.

        public HashMap<String, String> edgesLabelsMap = new HashMap<String, String>(); // Labels of Edges related to their variables.

        public List<String> propertyKeyList = new ArrayList<String>();  // Properties of Nodes (Key). Keyword can be "symbol"

        public List<String> propertyLabelList = new ArrayList<String>(); // Properties of Nodes (Variable/Label). Can be a gene identifier etc.

        public HashMap<String, String> propertyCollector = new HashMap<String, String>(); // Collects and saves properties.

        public HashMap<String, String> variableCollector = new HashMap<String, String>();

        public List<String> whereVarList = new ArrayList<String>(); // Will contain extracted variables from WHERE query.

        public HashMapper()
        {
            super();
        }



        // Maps Variable -> Nodes

        public void NodeLabelMapper()
        {


            for (int i = 0; i < variableNodeList.size(); i++)
            {
                this.nodesLabelsMap.put(variableNodeList.get(i), NodeStringList.get(i));

            }

           // this.nodesLabels.put(node, variable);
           // this.variableList.add(variable);
            System.out.println(this.nodesLabelsMap);


        }

        // Maps Variable -> Edges

        public void EdgeLabelMapper()
        {

            for (int i = 0; i < variableEdgeList.size(); i++)
            {
                this.edgesLabelsMap.put(variableEdgeList.get(i), EdgeStringList.get(i));

            }

            System.out.println(this.edgesLabelsMap);

        }

        // Maps Properties to each other.

        public void PropertyMapper()
        {


            //System.out.println(propertyKeyList.toString());




            for (int i = 0; i < propertyLabelList.size(); i++)
            {
                this.propertyCollector.put(propertyKeyList.get(i), propertyLabelList.get(i));

            }

            System.out.println(this.propertyCollector);

        }



    }




}
