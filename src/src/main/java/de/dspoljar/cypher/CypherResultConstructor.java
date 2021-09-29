package de.dspoljar.cypher;

import de.unibi.agbi.biodwh2.core.model.graph.Edge;
import de.unibi.agbi.biodwh2.core.model.graph.Node;
import sun.awt.image.ImageWatched;

import java.util.HashMap;
import java.util.LinkedList;

public class CypherResultConstructor
{

    public LinkedList<String> resultList = new LinkedList<String>();

    public LinkedList<Node> nodeList = new LinkedList<Node>();

    public HashMap<String, String> nodePropertyList = new HashMap<String, String>();

    public LinkedList<HashMap> nodePropertyHashMapList = new LinkedList<HashMap>();

    public LinkedList<Edge> edgeList = new LinkedList<Edge>();

    public HashMap<String, String> edgePropertyList = new HashMap<String, String>();

    public LinkedList<HashMap> edgePropertyHashMapList = new LinkedList<HashMap>();


    public CypherResultConstructor()
    {



       // resultList = this.resultList;


    }


    public HashMap<String, HashMap> concatNodeResults()
    {

        HashMap<String, HashMap> concatLists = new HashMap<String, HashMap>();

        for (int i = 0; i < this.nodeList.size(); i++)
        {
            for (int j = 0; j < this.nodePropertyHashMapList.size(); j++)
            {

                concatLists.put(this.nodeList.get(i).getLabel(), this.nodePropertyHashMapList.get(j));

            }

        }

        return concatLists;
    }



    // Getter and Setter:

    public LinkedList<String> getResultList()
    {

        return resultList;

    }

    public void setResultList(LinkedList<String> resultList)
    {

        this.resultList = resultList;
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
