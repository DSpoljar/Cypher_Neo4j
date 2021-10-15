package de.dspoljar.cypher;

import de.unibi.agbi.biodwh2.core.model.graph.Edge;
import de.unibi.agbi.biodwh2.core.model.graph.Node;
import sun.awt.image.ImageWatched;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class CypherResultConstructor
{

    public LinkedList<String> variableList = new LinkedList<String>();

    public LinkedList<Node> nodeList = new LinkedList<Node>();

    public HashMap<String, String> nodePropertyList = new HashMap<String, String>();

    public LinkedList<HashMap> nodePropertyHashMapList = new LinkedList<HashMap>();

    public LinkedList<Edge> edgeList = new LinkedList<Edge>();

    public HashMap<String, String> edgePropertyList = new HashMap<String, String>();

    public LinkedList<HashMap> edgePropertyHashMapList = new LinkedList<HashMap>();


    public CypherResultConstructor()
    {

        /*
        this.nodeList = nodeList;
        this.nodePropertyHashMapList = nodePropertyHashMapList;
        this.edgeList = edgeList;
        this.variableList = variableList;
        */





    }

    public Map<String, Node> mapNodes(LinkedList<String> StringVarList, LinkedList<Node> nodes)
    {
        Map<String, Node> nodeLabelVariableList = new HashMap<String, Node>();

        for (int i = 0; i < nodeList.size(); i++)
        {

            if (StringVarList.get(i) == nodes.get(i).getLabel())
            {
                nodeLabelVariableList.put(StringVarList.get(i), nodes.get(i));

            }




        }

        return nodeLabelVariableList;

    }







    public HashMap<String, String> hashMapFiller(String key, String value)
    {

        HashMap<String, String> tempProperties = new HashMap<String, String>();
        tempProperties.put(key, value);


        return tempProperties;

    }


    public HashMap<String, HashMap> concatResults(LinkedList<Node> nodeList, LinkedList<HashMap> hashMapList)
    {

        HashMap<String, HashMap> concatLists = new HashMap<String, HashMap>();

        for (int i = 0; i < nodeList.size(); i++)
        {

                concatLists.put(nodeList.get(i).getLabel(), hashMapList.get(i));


        }

        return concatLists;
    }

    public Map<String, Node> nodeLabel(LinkedList<String> variableOfNodeList, LinkedList<Node> nodeList)
    {
        Map<String, Node> nodeLabelVariableList = new HashMap<String, Node>();

        for (int i = 0; i < nodeList.size(); i++)
        {

            nodeLabelVariableList.put(variableOfNodeList.get(i), nodeList.get(i));


        }

        return nodeLabelVariableList;

    }





    // Getter and Setter:

    public LinkedList<String> getResultList()
    {

        return variableList;

    }

    public void setResultList(LinkedList<String> resultList)
    {

        this.variableList = resultList;
    }

    public LinkedList<Node> getNodeList()
    {
        return nodeList;

    }

    public void setNodeList(LinkedList<Node> nodeList)
    {
        this.nodeList = nodeList;

    }

    public LinkedList<Edge> getEdgeList()
    {
        return edgeList;
    }

    public void setEdgeList(LinkedList<Edge> edgeList)
    {
        this.edgeList = edgeList;
    }





}
