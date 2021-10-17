package de.dspoljar.cypher;

import de.unibi.agbi.biodwh2.core.model.graph.Node;

import java.util.HashMap;
import java.util.Map;

public class CypherResultConstructor
{




    public CypherResultConstructor()
    {


    }

    public HashMap<String, Node> variableNodeMapper()
    {
        
        return null;

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
