package de.dspoljar.cypher;

import de.unibi.agbi.biodwh2.core.model.graph.Graph;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import javax.swing.*;
import java.util.*;


public class CypherWalker
{

     CypherExtractor extractor = new CypherExtractor();
     CypherExtractor.HashMapper hashCollector = extractor.new HashMapper();
     final List<String> variableList = null;


    final List<String> executeVariableClauseList = new ArrayList<String>();


    public CypherWalker()
    {
        super();
      //  CypherParser cyphPars = new CypherParser(tokenStream)



    }

    public String extractSingleNodeLabel(CypherExtractor extractor, String variable)
    {
        // SOuts for testing purposes
        //System.out.println(this.hashCollector.getNodesLabels()+"\n");
        //System.out.println(this.hashCollector.getVariableOutOfList(extractor.variableStorage, "p")+"\n"); // Returns variable
        //System.out.println(this.hashCollector.getVariableCollector());


        System.out.println(this.hashCollector.variableCollector);
        if (this.hashCollector.variableCollector.containsKey("VARIABLE_CLAUSE"))
        {

            return this.hashCollector.variableCollector.getOrDefault("VARIABLE_CLAUSE", variable);


        }
        else
        {

            return  "Key and/or Variable not in list.";


        }


    }

    public String extractMapNodeLabel(CypherExtractor extractor, String variable)
    {
        // SOuts for testing purposes
        //System.out.println(this.hashCollector.getNodesLabels()+"\n");
        //System.out.println(this.hashCollector.getVariableOutOfList(extractor.variableStorage, "p")+"\n"); // Returns variable
        //System.out.println(this.hashCollector.getVariableCollector());


        if (this.hashCollector.nodesLabels.containsKey("LITERAL_CLAUSE"))
        {

         return this.hashCollector.nodesLabels.getOrDefault("LITERAL_CLAUSE", variable);

        }
        else
        {


            return "Key and/or Variable not in list.";

        }

    }

    private HashMap<String, String> extractEdgeNodeLabels(CypherExtractor extractor)
    {

        //System.out.println(this.hashCollector.variableList);
        List<String> filteredList =  new ArrayList<String>();

        for (int i = 0; i < this.hashCollector.variableList.size(); i++)
        {
            // Can be made more dynamic by supplementing arguments/paramenters


            if (this.hashCollector.variableList.contains("g") && !filteredList.contains("g"))
            {

                filteredList.add("g");

            }

            if (this.hashCollector.variableList.contains("r") && !filteredList.contains("r"))
            {

                filteredList.add("r");


            }

            if (this.hashCollector.variableList.contains("p") && !filteredList.contains("p"))
            {
                filteredList.add("p");

            }

        }

        // Convoluted way of sorting the node and edge list ...


        for (int j = 0; j < this.hashCollector.edgeAndNodeList.size(); j++)
        {

            // Provisorisch?
            this.hashCollector.edgeAndNodeList.set(0, "Gene");
            this.hashCollector.edgeAndNodeList.set(1, "CODES_FOR");
            this.hashCollector.edgeAndNodeList.set(2, "Protein");

             if (this.hashCollector.edgeAndNodeList.get(j) == "Gene")
            {


                this.hashCollector.edgeAndNodeList.set(0, "Gene");

            }

             else if (this.hashCollector.edgeAndNodeList.get(j) == "CODES_FOR")
            {

                this.hashCollector.edgeAndNodeList.set(1, "CODES_FOR");

            }

             else if (this.hashCollector.edgeAndNodeList.get(j) == "Protein")
            {

                this.hashCollector.edgeAndNodeList.set(2, "Protein");
            }


        }

        this.hashCollector.edgeMapper(this.hashCollector.edgeAndNodeList, filteredList);
        return this.hashCollector.edgeCollector;

    }



    public void acceptQuery(Graph graph, String query, CypherExtractor extractor)
    {

        CharStream stream = (CharStream) CharStreams.fromString(query);

        CypherLexer cyphLexer = new CypherLexer(stream);

        TokenStream tokenStream = new CommonTokenStream(cyphLexer);

        CypherParser cyphPars = new CypherParser(tokenStream);

        final CypherParser.OC_CypherContext context = cyphPars.oC_Cypher();

        this.extractor = extractor;


     //    System.out.print(context.children.toString());

        for(int i = 0; i < context.getChildCount(); i++)
        {


            final ParseTree tree = context.getChild(i);
            System.out.println("WHOLE TREE: "+tree.toStringTree());

           // System.out.println(context.getChildCount());

            if (tree instanceof CypherParser.OC_StatementContext)
            {


                // Different executing statements:

                // Starting at the top ...
                 executeStatement((CypherParser.OC_StatementContext) tree);


            }

        }


        // Testing variable saving ...
          //System.out.print(this.extractor.labelStorage.toString()+"\n");
          //System.out.print(this.extractor.variableStorage.toString()+"\n");
        // Testing hashing etc.
           //System.out.println(this.hashCollector.getNodesLabels()+"\n");
           //System.out.println(this.hashCollector.getVariableOutOfList(this.extractor.variableStorage, "p")+"\n"); // Returns variable
        //System.out.println(this.hashCollector.getVariableCollector());


        System.out.println("Variable extraction: " + extractSingleNodeLabel(this.extractor, "n") ); // Extracts variable
        System.out.println("Symbol extraction: " + extractMapNodeLabel(this.extractor, "IL10") ); // Extracts symbol
        System.out.println("Edge/node and variable extraction: " + extractEdgeNodeLabels(this.extractor) ); // Extracts chain

    }

    private void executeStatement(final CypherParser.OC_StatementContext statement)
    {
        final CypherParser.OC_QueryContext query = statement.oC_Query();

        if (query.oC_StandaloneCall() != null)
        {
          //  System.out.println("OC_StandaloneCall:"+query.oC_StandaloneCall());
            query.oC_StandaloneCall();
        }
        else if (query.oC_RegularQuery() != null)
        {
            executeSingleMultipleQuery(query.oC_RegularQuery());
          //  System.out.println("OC_RegQuery:"+query.oC_RegularQuery());

        }


    }



    private void executeSingleMultipleQuery(CypherParser.OC_RegularQueryContext statement)
    {

        final CypherParser.OC_SingleQueryContext query = statement.oC_SingleQuery();


        if (query.oC_SinglePartQuery() != null)
        {
        //    System.out.println("OC_SingleQuery:"+query.toString()); // Write this in a variable later (if  required)
            executeSinglePartQuery(query.oC_SinglePartQuery());

        }

        if (query.oC_MultiPartQuery() != null)
        {

            //     System.out.println("OC_MultiPartQuery:"+query.oC_MultiPartQuery().toString());
            executeMultiPartQuery(query.oC_MultiPartQuery());

        }





    }

    private void executeSinglePartQuery(CypherParser.OC_SinglePartQueryContext statement)
    {

        final CypherParser.OC_SinglePartQueryContext query = statement;


        for (int i = 0; i < query.getChildCount(); i++)
        {
            if (query.oC_ReadingClause(i) != null)
            {

                System.out.println("OC_ReadingClause:"+query.oC_ReadingClause(i).toString()); // Write this in a variable later (if  required)
                executeReadingClause(query.oC_ReadingClause(i));



            }

             if (query.oC_UpdatingClause(i) != null)
            {

                System.out.println("OC_UpdatingClause:"+query.oC_UpdatingClause(i).toString());
                executeUpdatingClause(query.oC_UpdatingClause(i));

            }

             if(query.oC_Return() != null)
            {


                //     System.out.println("return:  "+query.oC_Return().RETURN().toString());  //  Return RETURN easier.

                // System.out.println(query.oC_Return().oC_ProjectionBody().children.toString());  // Contains the blank space (?)

                executeReturnClause(query.oC_Return());



            }


        }




    }

    private void executeMultiPartQuery(CypherParser.OC_MultiPartQueryContext statement)
    {

        final CypherParser.OC_MultiPartQueryContext query = statement;

        if (query.oC_ReadingClause(0) != null)
        {
            for (int i = 0; i < query.getChildCount(); i++)
            {

                executeReadingClause(query.oC_ReadingClause(i));

            }

        }

         if (query.oC_SinglePartQuery() != null)
        {

            executeSinglePartQuery(query.oC_SinglePartQuery());

        }

         if (query.oC_UpdatingClause(0) != null)
        {

            for (int i = 0; i < query.getChildCount(); i++)
            {

                executeUpdatingClause(query.oC_UpdatingClause(i));

            }

        }

         if (query.oC_With(0) != null)
        {

            for (int i = 0; i < query.getChildCount(); i++)
            {

                executeWhithClause(query.oC_With(i));

            }



        }

    }



    private void executeReadingClause(CypherParser.OC_ReadingClauseContext statement)
    {
        final CypherParser.OC_ReadingClauseContext query = statement;

        if (query.oC_Match() != null)
        {

            //System.out.println("OC_Match (Reading):"+query.oC_Match().toString());
            //System.out.println("Returns CHILDREN:"+query.oC_Match().children.toString());
            //System.out.println("match: "+query.oC_Match().MATCH().toString());  //  Return MATCH easier.
            executeMatchClause(query.oC_Match());



        }

         if (query.oC_Unwind() != null)
        {

            System.out.println("OC_Unwind (Reading):"+query.oC_Unwind().toString());

        }

         if (query.oC_InQueryCall() != null)
        {

            System.out.println("OC_InQueryCall (Reading):"+query.oC_InQueryCall().toString());

        }


    }

    private void executeUpdatingClause(CypherParser.OC_UpdatingClauseContext statement)
    {

        final CypherParser.OC_UpdatingClauseContext query = statement;

        if (query.oC_Create() != null)
        {

        //   System.out.println("OC_Create:"+query.oC_Create().toString());

        }

        else if (query.oC_Delete() != null)
        {
         //   System.out.println("OC_Delete:"+query.oC_Delete().toString());

        }

        else if (query.oC_Merge() != null)
        {
        //    System.out.println("OC_Merge:"+query.oC_Merge().toString());

        }
        else if (query.oC_Set() != null)
        {

        //    System.out.println("OC_Set:"+query.oC_Set().toString());

        }


    }

    private void executeWhithClause(CypherParser.OC_WithContext statement)
    {
        final CypherParser.OC_WithContext query = statement;
        System.out.println("In WITH CLAUSE");

        if (query.oC_ProjectionBody() != null)
        {

            executeProjectionBody(query.oC_ProjectionBody());

        }

        else if (query.oC_Where() != null)
        {

            executeWhereClause(query.oC_Where());

        }

    }

    private void executeWhereClause(CypherParser.OC_WhereContext statement)
    {
        final CypherParser.OC_WhereContext query = statement;

        final String node = "WHERE_CLAUSE";
        System.out.println("OC_WHERE: I am in.");

        if (query.oC_Expression() != null)
        {
            executeExpressionClause(query.oC_Expression());

        }

    }



    private void executeMatchClause(CypherParser.OC_MatchContext statement)
    {
        final CypherParser.OC_MatchContext query = statement;


        if (query.oC_Pattern() != null)
        {

            //System.out.println("OC Pattern: "+query.oC_Pattern().toString());
            executePatternClause(query.oC_Pattern());

        }

        else if (query.oC_Where() != null)
        {

          executeWhereClause(query.oC_Where());

        }

    }

    private void executePatternClause(CypherParser.OC_PatternContext statement)
    {

        final CypherParser.OC_PatternContext query = statement;

        for (int i = 0;  i < query.getChildCount(); i++)
        {

            if (query.oC_PatternPart(i) != null)
            {

                //System.out.println("OC_PatternPartClause: "+query.oC_PatternPart(i).toString());
                executePatternPartClause(query.oC_PatternPart(i));

            }



        }

    }

    private void executePatternPartClause(CypherParser.OC_PatternPartContext statement)
    {
        final CypherParser.OC_PatternPartContext query = statement;

        if (query.oC_Variable() != null)
        {

            //System.out.println("OC_Variable: "+query.oC_Variable().toString());

            executeVariableClause(query.oC_Variable());

        }

         if (query.oC_AnonymousPatternPart() != null)
        {

            //System.out.println("OC_AnonymousPatternPart: "+ query.oC_AnonymousPatternPart().toString());
            executeAnonymousPatternPartClause(query.oC_AnonymousPatternPart());

        }


    }

    private void executeAnonymousPatternPartClause(CypherParser.OC_AnonymousPatternPartContext statement)
    {

        final CypherParser.OC_AnonymousPatternPartContext query = statement;

        if (query.oC_PatternElement() != null)
        {
            //System.out.println("OC_PatternElement: "+ query.oC_PatternElement().toString());
            executePatternElementClause(query.oC_PatternElement());

        }


    }

    private void executePatternElementClause(CypherParser.OC_PatternElementContext statement)
    {

        final CypherParser.OC_PatternElementContext query = statement;


        if (query.oC_NodePattern() != null)
        {

            // System.out.println(query.oC_NodePattern().getChildCount());
            //System.out.println("OC_NodePattern: "+ query.oC_NodePattern().toString());

            executeNodePatternClause(query.oC_NodePattern());


        }

        if (query.oC_PatternElement() != null)
        {
            //System.out.println("OC_PatternElement: "+ query.oC_PatternElement().toString());
            executePatternElementClause(query.oC_PatternElement());

        }

        if (query.oC_PatternElementChain(0)  != null)
        {

            for (int i = 0; i < query.getChildCount(); i++)
            {

                if (query.oC_PatternElementChain(i) != null)
                {
                    executePatternElementChain(query.oC_PatternElementChain(i));
                }
                //System.out.println("OC_PatternElementChain: "+ query.oC_PatternElementChain(i).toString());


            }

        }

    }

    private void executePatternElementChain(CypherParser.OC_PatternElementChainContext statement)
    {
        final CypherParser.OC_PatternElementChainContext query = statement;


        if (query.oC_NodePattern() != null)
        {

            System.out.println(query.oC_NodePattern());
            executeNodePatternClause(query.oC_NodePattern());

        }

        if (query.oC_RelationshipPattern() != null)
        {

            System.out.println("OC_Relationshippattern: "+query.oC_RelationshipPattern().toString());
            executeRelationshipPattern(query.oC_RelationshipPattern());


        }



    }

    private void executeRelationshipPattern(CypherParser.OC_RelationshipPatternContext statement)
    {

        final CypherParser.OC_RelationshipPatternContext query = statement;

        if (query.oC_RelationshipDetail() != null)
        {

            System.out.println("OC_Relaionshipdetail: "+query.oC_RelationshipDetail().toString());
            executeRelationshipDetailContext(query.oC_RelationshipDetail());


        }

        if (query.oC_Dash(0) != null)
        {

            for (int i = 0; i < query.getChildCount(); i++)
            {

                if (query.oC_Dash(i)  != null)
                {
                    System.out.println("OC_Dash: "+query.oC_Dash().toString());
                    executeDashClause(query.oC_Dash(i));

                }

            }

        }

        if (query.oC_LeftArrowHead() !=  null)
        {

            System.out.println("LeftArrowhead: "+query.oC_LeftArrowHead().toString());



        }

        if (query.oC_RightArrowHead()  != null)
        {
            System.out.println("Rightarrowhead: "+query.oC_RightArrowHead().toString());
            executeRightArrowhead(query.oC_RightArrowHead());


        }

    }

    private void executeRightArrowhead(CypherParser.OC_RightArrowHeadContext statement)
    {

        final CypherParser.OC_RightArrowHeadContext query = statement;

        System.out.println(query.children.toString());


    }

    private void executeRelationshipDetailContext(CypherParser.OC_RelationshipDetailContext statement)
    {
        final CypherParser.OC_RelationshipDetailContext query = statement;

        if (query.oC_Properties() != null)
        {
            System.out.println("Props");
            executePropertiesClause(query.oC_Properties());

        }

        if (query.oC_Variable()  != null)
        {

            System.out.println("Variable: "+query.oC_Variable().toString());
            executeVariableClause(query.oC_Variable());

        }

        if (query.oC_RelationshipTypes() != null)
        {

            executeRelationsipTypesContext(query.oC_RelationshipTypes());

        }

        if (query.oC_RangeLiteral() != null)
        {
            System.out.println("RangeLiteral");

        }



    }

    private void executeRelationsipTypesContext(CypherParser.OC_RelationshipTypesContext statement)
    {
        final CypherParser.OC_RelationshipTypesContext query = statement;

        if (query.oC_RelTypeName(0) != null)
        {

            for (int i = 0; i < query.getChildCount(); i++)
            {
                if (query.oC_RelTypeName(i) != null)
                {
                    System.out.println("OCReltypes: "+query.oC_RelTypeName(i));
                    executeRelTypeNameContext(query.oC_RelTypeName(i));

                }



            }

        }


    }

    private void executeRelTypeNameContext(CypherParser.OC_RelTypeNameContext statement)
    {
        final CypherParser.OC_RelTypeNameContext query = statement;


        if (query.oC_SchemaName() != null)
        {
            System.out.println("schema name in  ..."+query.oC_SchemaName().toString());
            executeSchemaNameContext(query.oC_SchemaName());
        }



    }


    private void executeDashClause(CypherParser.OC_DashContext statement)
    {
        final CypherParser.OC_DashContext query = statement;


        System.out.println(query.children.toString());

    }


    private void executeNodePatternClause(CypherParser.OC_NodePatternContext statement)
    {
        final CypherParser.OC_NodePatternContext query = statement;


        if (query.oC_NodeLabels() !=  null)
        {


            System.out.println("OC_NodeLabels:"+query.oC_NodeLabels().toString());
            executeNodeLabelClause(query.oC_NodeLabels());


        }


        if (query.oC_Variable() != null)
        {

            System.out.println("OC_Variable:"+query.oC_Variable().toString());
            executeVariableClause(query.oC_Variable());

        }


        if (query.oC_Properties() != null)
        {

            //System.out.println("OC_Properties:"+query.oC_Properties().toString());
            executePropertiesClause(query.oC_Properties());

        }
        


    }

    private void executePropertiesClause(CypherParser.OC_PropertiesContext statement)
    {
        final CypherParser.OC_PropertiesContext query = statement;

        if (query.oC_Parameter() != null)
        {

            //System.out.println("OC_param:"+query.oC_MapLiteral().toString());
            executeParameterClause(query.oC_Parameter());

        }

        if (query.oC_MapLiteral() != null)
        {

            //System.out.println("OC_maplit:"+query.oC_MapLiteral().toString());
            executeMapLiteralClause(query.oC_MapLiteral());


        }

    }

    private void executeParameterClause(CypherParser.OC_ParameterContext statement)
    {
        final CypherParser.OC_ParameterContext query = statement;




    }

    private void executeMapLiteralClause(CypherParser.OC_MapLiteralContext statement)
    {
        final CypherParser.OC_MapLiteralContext query = statement;

        if (query.oC_Expression(0) != null)
        {


            for (int i = 0; i < query.getChildCount(); i++)
            {


                if (query.oC_Expression(i) != null)
                {

                    //System.out.println("OCExpr:" +query.oC_Expression(i).toString());
                    executeExpressionClause(query.oC_Expression(i));

                }


            }


        }

        if (query.oC_PropertyKeyName(0) != null)
        {

            for (int i = 0; i < query.getChildCount(); i++)
            {


                if (query.oC_PropertyKeyName(i) != null)
                {

                    //System.out.println("OC:_PropertyKeyname:" +query.oC_Expression(i).toString());
                    executePropertyKeyNameContext(query.oC_PropertyKeyName(i));

                }



            }

        }

    }

    private void executePropertyKeyNameContext(CypherParser.OC_PropertyKeyNameContext statement)
    {
        final CypherParser.OC_PropertyKeyNameContext query = statement;

        if (query.oC_SchemaName() != null)
        {

            //System.out.println("OC_schemaname: "+query.oC_SchemaName().toString());
            executeSchemaNameContext(query.oC_SchemaName());

        }


    }


    private void executeNodeLabelClause(CypherParser.OC_NodeLabelsContext statement)
    {
        final CypherParser.OC_NodeLabelsContext query = statement;

        if (query.oC_NodeLabel() != null)
        {
            for (int i = 0; i < query.getChildCount(); i++)
            {

                System.out.println("OC_NodeLabel: "+ query.oC_NodeLabel(i).toString());
                executeNodeLabelNameClause(query.oC_NodeLabel(i));

            }
        }

    }

    private void executeNodeLabelNameClause(CypherParser.OC_NodeLabelContext statement)
    {
        final CypherParser.OC_NodeLabelContext query = statement;

        if (query.oC_LabelName() != null)
        {

            System.out.println("OC_LabelName: "+ query.oC_LabelName().toString());
            executeLabelNameClause(query.oC_LabelName());

        }

    }

    private void executeLabelNameClause(CypherParser.OC_LabelNameContext statement)
    {

        final CypherParser.OC_LabelNameContext query = statement;

        final String node = "LABEL_NAME_CLAUSE";

        if (query.oC_SchemaName() != null)
        {

            System.out.println("OC_SchemaName: (LabelNameClause) "+ query.oC_SchemaName().toString());
            executeSchemaNameContext(query.oC_SchemaName());

        }



    }

    private void executeSchemaNameContext(CypherParser.OC_SchemaNameContext statement)
    {
        final CypherParser.OC_SchemaNameContext query = statement;

        final String node = "SCHEMA_CONTEXT";


        if (query.oC_SymbolicName() != null)
        {
             //System.out.println("OC_SymbolicName: "+ query.oC_SymbolicName().toString());
                System.out.println("OC_SymbolicName_TERMINAL: "+ query.oC_SymbolicName().children.toString()); // Returns the Label ("Gene") in the current example.
            String label = query.oC_SymbolicName().children.toString();
            this.extractor.saveLabels(label);
            this.hashCollector.mapper(node, label);
            this.hashCollector.edgeAndNodeList.add(query.oC_SymbolicName().children.toString());
            executeSymbolicNameClause(query.oC_SymbolicName());


        }

         if (query.oC_ReservedWord() != null)
        {


                executeReservedWord(query.oC_ReservedWord());


        }



    }

    private void executeReservedWord(CypherParser.OC_ReservedWordContext statement)
    {
        final CypherParser.OC_ReservedWordContext query = statement;

        System.out.println("ReservedWord: "+query.children.toString());


    }

    private void executeSymbolicNameClause(CypherParser.OC_SymbolicNameContext statement)
    {
        final CypherParser.OC_SymbolicNameContext query = statement;

        final String node = "SYMBOLIC_NAME_CLAUSE";



    }


    private void executeReturnClause(CypherParser.OC_ReturnContext statement)
    {
        final CypherParser.OC_ReturnContext query = statement;

        if (query.oC_ProjectionBody() !=  null)
        {
            //System.out.println("OC_ProjectionBody:"+query.oC_ProjectionBody().toString());
            executeProjectionBody(query.oC_ProjectionBody());


        }

    }

    private void executeProjectionBody(CypherParser.OC_ProjectionBodyContext statement)
    {

        final CypherParser.OC_ProjectionBodyContext query = statement;

        if (query.oC_Limit() !=  null)
        {

              System.out.println("OC_Limit:"+query.oC_Limit().toString());

        }

        if (query.oC_ProjectionItems() != null)
        {

            System.out.println("OC_ProjectionItems:"+query.oC_ProjectionItems().children.toString());

            executeProjectionItemsClause(query.oC_ProjectionItems());
        }

        if(query.oC_Order() != null)
        {

             System.out.println("OC_Order:"+query.oC_Order().toString());

        }
        if(query.oC_Skip() != null)
        {
               System.out.println("OC_Skip"+query.oC_Skip().toString());

        }

    }

    private void executeProjectionItemsClause(CypherParser.OC_ProjectionItemsContext statement)
    {
        final CypherParser.OC_ProjectionItemsContext query = statement;

        for (int i = 0; i < query.getChildCount(); i++)
        {
            if (query.oC_ProjectionItem(i) !=  null)
            {

              //  System.out.println("OC_ProjectionItem_i:"+query.oC_ProjectionItem(i).children.toString());
                executeProjectionItemContext(query.oC_ProjectionItem(i));

            }

        }


    }

    private void executeProjectionItemContext(CypherParser.OC_ProjectionItemContext statement)
    {
        final CypherParser.OC_ProjectionItemContext query = statement;

        if (query.oC_Variable() != null)
        {

            //System.out.print("OC_Variable: "+query.oC_Variable().toString());
            executeVariableClause(query.oC_Variable());


        }

        if (query.oC_Expression() != null)
        {

         //   System.out.print("OC_Expression:"+query.oC_Expression().children.toString());
            executeExpressionContext(query.oC_Expression());

        }

    }



    private void executeExpressionContext(CypherParser.OC_ExpressionContext statement)
    {
        final CypherParser.OC_ExpressionContext query = statement;

        if (query.oC_OrExpression() != null)
        {

        //    System.out.print("OC_ORExpression:"+query.oC_OrExpression().toString());
            executeOrExpressionClause(query.oC_OrExpression());

        }

    }

    private void executeOrExpressionClause(CypherParser.OC_OrExpressionContext statement)
    {

        final CypherParser.OC_OrExpressionContext query = statement;

        for (int i = 0; i < query.getChildCount();  i++)
        {

            if (query.oC_XorExpression(i) !=  null)
            {

                //System.out.print("OC_XoRExpression:"+query.oC_XorExpression(i).toString());
                executeXorExpressionClause(query.oC_XorExpression(i));

            }


        }


    }

    private void executeXorExpressionClause(CypherParser.OC_XorExpressionContext statement)
    {

        final CypherParser.OC_XorExpressionContext query = statement;

        for (int i = 0; i < query.getChildCount();  i++)
        {

            if (query.oC_AndExpression(i) !=  null)
            {

                //System.out.print("OC_AndExpression:"+query.oC_AndExpression(i).toString());
                executeAndExpressionContext(query.oC_AndExpression(i));

            }


        }

    }

    private void executeAndExpressionContext(CypherParser.OC_AndExpressionContext statement)
    {

        final CypherParser.OC_AndExpressionContext query = statement;

        for (int i = 0; i < query.getChildCount();  i++)
        {

            if (query.oC_NotExpression(i) !=  null)
            {

                //System.out.print("OC_NotExpression:"+query.oC_NotExpression(i).toString());
                executeNotExpressionContext(query.oC_NotExpression(i));

            }


        }

    }

    private void executeNotExpressionContext(CypherParser.OC_NotExpressionContext statement)
    {

        final CypherParser.OC_NotExpressionContext query = statement;


            if (query.oC_ComparisonExpression() != null)
            {

                //System.out.print("OC_ComparisonExpression:"+query.oC_ComparisonExpression().toString());
                executeComparisonExpressionContext(query.oC_ComparisonExpression());

            }

    }

    private void executeComparisonExpressionContext(CypherParser.OC_ComparisonExpressionContext statement)
    {

        final CypherParser.OC_ComparisonExpressionContext query = statement;


        if (query.oC_AddOrSubtractExpression() != null)
        {

            //System.out.print("OC_AddOrSubtractExpression:"+query.oC_AddOrSubtractExpression().toString());
            executeAddOrSubtractExpressionContext(query.oC_AddOrSubtractExpression());

        }

        if (query.oC_PartialComparisonExpression(0) != null)
        {

            for (int i = 0; i < query.getChildCount(); i++)
            {

                //System.out.print("OC_PartialComparisonExpr.:"+query.oC_PartialComparisonExpression(i).children.toString());
                executePartialComparisonExpression(query.oC_PartialComparisonExpression(i));

            }


        }

    }

    private void executePartialComparisonExpression(CypherParser.OC_PartialComparisonExpressionContext statement)
    {
        final CypherParser.OC_PartialComparisonExpressionContext query = statement;

        if (query.oC_AddOrSubtractExpression() != null)
        {

            executeAddOrSubtractExpressionContext(query.oC_AddOrSubtractExpression());

        }

    }

    private void executeAddOrSubtractExpressionContext(CypherParser.OC_AddOrSubtractExpressionContext statement)
    {

        final CypherParser.OC_AddOrSubtractExpressionContext query = statement;

        for (int i= 0; i < query.getChildCount(); i++)
        {

            if (query.oC_MultiplyDivideModuloExpression(i) !=  null)
            {

                //System.out.print("OC_MultiplyDivideModulo:"+query.oC_MultiplyDivideModuloExpression(i).toString());
                executeMultiplyDivideModuloExpressionContext(query.oC_MultiplyDivideModuloExpression(i));

            }

        }

    }

    private void executeMultiplyDivideModuloExpressionContext(CypherParser.OC_MultiplyDivideModuloExpressionContext statement)
    {

        final CypherParser.OC_MultiplyDivideModuloExpressionContext query = statement;

        for (int i= 0; i < query.getChildCount(); i++)
        {

            if (query.oC_PowerOfExpression(i) !=  null)
            {

                //System.out.print("OC_PowerOf:"+query.oC_PowerOfExpression(i).toString());
                executePowerOfExpressionContext(query.oC_PowerOfExpression(i));

            }

        }

    }

    private void executePowerOfExpressionContext(CypherParser.OC_PowerOfExpressionContext statement)
    {

        final CypherParser.OC_PowerOfExpressionContext query = statement;

        for (int i= 0; i < query.getChildCount(); i++)
        {

            if (query.oC_UnaryAddOrSubtractExpression(i) !=  null)
            {

                //System.out.print("OC_UnaryAddOrSubtractExpression:"+query.oC_UnaryAddOrSubtractExpression(i).toString());
                executeUnaryAddOrSubtractExpressionContext(query.oC_UnaryAddOrSubtractExpression(i));

            }

        }

    }

    private void executeUnaryAddOrSubtractExpressionContext(CypherParser.OC_UnaryAddOrSubtractExpressionContext statement)
    {

        final CypherParser.OC_UnaryAddOrSubtractExpressionContext query = statement;


            if (query.oC_StringListNullOperatorExpression() !=  null)
            {

                //System.out.print("OC_StringListNullOperatorExpression():"+query.oC_StringListNullOperatorExpression().toString());
                executeStringListNullOperatorExpressionContext(query.oC_StringListNullOperatorExpression());

            }


    }

    private void executeStringListNullOperatorExpressionContext(CypherParser.OC_StringListNullOperatorExpressionContext statement)
    {

        final CypherParser.OC_StringListNullOperatorExpressionContext query = statement;


        if (query.oC_PropertyOrLabelsExpression() !=  null)
        {


            //System.out.print("OC_PropertyOrLabelExpression():"+query.oC_PropertyOrLabelsExpression().toString());
            executePropertyOrLabelsExpressionContext(query.oC_PropertyOrLabelsExpression());

        }

        if (query.oC_ListOperatorExpression(0) != null)
        {


        }

        if (query.oC_NullOperatorExpression(0) != null)
        {



        }

         if (query.oC_StringOperatorExpression(0) != null )
        {



        }



    }

    private void executePropertyOrLabelsExpressionContext(CypherParser.OC_PropertyOrLabelsExpressionContext statement)
    {

        final CypherParser.OC_PropertyOrLabelsExpressionContext query = statement;



        if (query.oC_Atom() !=  null)
        {

            //System.out.print("Oc_AtomExpression(): "+query.oC_Atom().children.toString());
            executeAtomContext(query.oC_Atom());

        }

         if (query.oC_NodeLabels() != null)
        {


            //System.out.println("OC_NodeLabels  (executeAtom): "+query.oC_NodeLabels().toString());
            executeNodeLabelClause(query.oC_NodeLabels());

        }

         if (query.oC_PropertyLookup(0) != null)
        {

            for (int i = 0; i < query.getChildCount(); i++)
            {

                //System.out.println("OC_PropertyLookup: "+query.oC_PropertyLookup(i));
                executePropertyLookup(query.oC_PropertyLookup(i));


            }

        }


    }

    private void executePropertyLookup(CypherParser.OC_PropertyLookupContext statement)
    {

        final CypherParser.OC_PropertyLookupContext query = statement;

        if (query.oC_PropertyKeyName() != null)
        {

            executePropertyKeyNameContext(query.oC_PropertyKeyName());

        }


    }

    private void executeExpressionClause(CypherParser.OC_ExpressionContext statement)
    {
        final CypherParser.OC_ExpressionContext query = statement;
        // System.out.print("ocorexpression: "+query.oC_OrExpression().toString());


        if (query.oC_OrExpression() != null)
        {


            //System.out.print("OC_OrExpress: "+query.oC_OrExpression().toString());
            executeOrExpressionClause(query.oC_OrExpression());

        }



    }



    // Can extract variable  ("n"  here)!

    private void executeAtomContext(CypherParser.OC_AtomContext statement)
    {

        final CypherParser.OC_AtomContext query = statement;

        final String node = "ATOM_CONTEXT";



        if (query.oC_Literal() !=  null)
        {

            //System.out.print("Oc_Literal()_VARIABLE: " + query.oC_Literal().toString());
            executeLiteralClause(query.oC_Literal());


        }

        if (query.oC_Variable() != null)
        {
            // System.out.print("Oc_Variable()_VARIABLE:"+query.oC_Variable().children.toString());
            // System.out.print("Oc_SymbolicName_VARIABLE:"+query.oC_Variable().oC_SymbolicName().children.toString());  // <-- VARIABLE N STORED HERE !!
          //  System.out.println(query.oC_Variable().oC_SymbolicName().children.get(0).toString());

            executeVariableClause(query.oC_Variable());

        }

         if (query.oC_CaseExpression() != null)
        {

           System.out.print("OC_CASEEXPRESSION: "+query.oC_Variable().children.toString());

        }

         if (query.oC_FilterExpression() != null)
        {
            System.out.println("OC_FilterExpression: "+query.oC_FilterExpression().children.toString());

        }

         if (query.oC_Parameter() != null)
        {
            System.out.println("OC_Parameter: "+query.oC_Parameter().children.toString());

        }

         if (query.oC_FunctionInvocation() != null)
        {

            System.out.println("OC_FunctionInvo: "+query.oC_FunctionInvocation().children.toString());

        }

         if (query.oC_ListComprehension() != null)
        {

            System.out.println("OC_ListComprehension: "+query.oC_ListComprehension().children.toString());

        }

         if (query.oC_ParenthesizedExpression() != null)
        {

           // System.out.println("OC_ParenthesizedExpression");
            executeParenthesizedExpressionContext(query.oC_ParenthesizedExpression());

        }

         if (query.oC_PatternComprehension() != null)
        {

            System.out.println("oc_patterncomprehension: "+query.oC_PatternComprehension().children.toString());



        }

         if (query.oC_RelationshipsPattern() != null)
        {

            System.out.println("OC_RelationshipPattern: "+query.oC_RelationshipsPattern().children.toString());


        }




    }


    private void executeLiteralClause(CypherParser.OC_LiteralContext statement)
    {
        final CypherParser.OC_LiteralContext query = statement;

        final String node = "LITERAL_CLAUSE";
        //System.out.println("LITERALCHILDREN: "+query.children.toString()); // <-- IL10 / SYMBOL is here!
        System.out.println("Symbol: "+query.getChild(0).toString());
        String label = query.getChild(0).toString();
        this.extractor.saveLabels(label);
        this.hashCollector.mapper(node, label);

        if (query.oC_MapLiteral() != null)
        {

            System.out.println("OC_MaLiteral_CLAUSE "+query.oC_MapLiteral().toString());

        }

        if (query.oC_ListLiteral() != null)
        {


            System.out.println("OC_ListLiteral "+query.oC_ListLiteral().toString());

        }

        if (query.oC_BooleanLiteral() != null)
        {

            System.out.println("OC_BooleanLiteral "+query.oC_MapLiteral().toString());

        }

        if (query.oC_NumberLiteral() != null)
        {

            System.out.println("OC_NumberLiteral "+query.oC_NumberLiteral().toString());

        }


    }

    private void executeParenthesizedExpressionContext(CypherParser.OC_ParenthesizedExpressionContext statement)
    {
        final CypherParser.OC_ParenthesizedExpressionContext query = statement;

        if (query.oC_Expression() != null)
        {

            //    System.out.println("OC_ParenthExpr.: "+query.oC_Expression().toString());
            executeExpressionClause(query.oC_Expression());

        }

    }

    private void executeVariableClause(CypherParser.OC_VariableContext statement)
    {
        final CypherParser.OC_VariableContext query = statement;

        final String node = "VARIABLE_CLAUSE";



        if (query.oC_SymbolicName() != null)
        {



            //System.out.println("Variableclause: "+query.oC_SymbolicName().toString());
            String variable = query.oC_SymbolicName().children.get(0).toString();
            String edgeVar =  query.oC_SymbolicName().children.get(0).toString();
            System.out.print("var:"+query.oC_SymbolicName().children.toString());


            /*
            if (!executeVariableClauseList.contains(variable))
            {

                executeVariableClauseList.add(variable);


            } */


            this.hashCollector.variableCollector.put(node, variable);
            this.extractor.saveVariables(variable); // Saving variable "N"

            this.hashCollector.variableList.add(query.oC_SymbolicName().children.get(0).toString());








        }

    }


    /*
     JFrame and Panel for visualization.
     TODO: Possible re-integration of TreeViewer at later point
     */

    public void treeViewing(String query)
    {

        CharStream stream = (CharStream) CharStreams.fromString(query);

        CypherLexer cyphLexer = new CypherLexer(stream);

        TokenStream tokenStream = new CommonTokenStream(cyphLexer);

        CypherParser cyphPars = new CypherParser(tokenStream);

        ParseTree tree = cyphPars.oC_Cypher();

        CypherParser.OC_CypherContext context = cyphPars.oC_Cypher();

        JFrame frame = new JFrame("Testing the tree ...");
        JPanel panel = new JPanel();
       // TreeViewer viewer = new TreeViewer(Arrays.asList(cyphPars.getRuleNames()), tree);
       // panel.add(viewer);
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);


    }




}
