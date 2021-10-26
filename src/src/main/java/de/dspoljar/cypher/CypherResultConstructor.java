package de.dspoljar.cypher;

import de.unibi.agbi.biodwh2.core.model.graph.Edge;
import de.unibi.agbi.biodwh2.core.model.graph.Node;

import java.util.HashMap;
import java.util.Map;

public class CypherResultConstructor
{

    public final HashMap<String, Node> resultVarsNodes = new HashMap<String, Node>();
    public final HashMap<String, Edge> resultVarsEdges = new HashMap<String, Edge>();
    public final HashMap<Node, String> resultsWHEREVars= new HashMap<Node, String>();
    public HashMap<Node, String> resultPropertyMap = new HashMap<>();





    public CypherResultConstructor()
    {


    }

    public HashMap<String, Node> variableNodeMapper(String var, Node n)
    {
        HashMap<String, Node> resultMap = new HashMap<>();

        resultMap.put(var, n);

        return resultMap;

    }


    // Maps node and symbol to their respective properties. For one property only now.

    public Map<HashMap<String, String>, HashMap<String, String>> nodesToPropertiesMapper(HashMap<String, String> nodes, HashMap<String, String> properties)
    {


         Map<HashMap<String, String>, HashMap<String, String>> resultMap = new HashMap<HashMap<String,String>, HashMap<String, String>>();

         for (int i = 0; i < nodes.size(); i++)
         {

             resultMap.put(nodes, properties);




         }


        return resultMap;

    }






}
