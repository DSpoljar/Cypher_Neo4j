package de.dspoljar.cypher;

import de.unibi.agbi.biodwh2.core.model.graph.Graph;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTree;



import javax.swing.*;
import java.util.Arrays;


public class CypherWalker
{

     CypherExtractor extractor = new CypherExtractor();


    public CypherWalker()
    {
        super();
      //  CypherParser cyphPars = new CypherParser(tokenStream)



    }



    public void acceptQuery(Graph graph, String query, CypherExtractor extractor)
    {

        CharStream stream = (CharStream) CharStreams.fromString(query);

        CypherLexer cyphLexer = new CypherLexer(stream);

        TokenStream tokenStream = new CommonTokenStream(cyphLexer);

        CypherParser cyphPars = new CypherParser(tokenStream);

        final CypherParser.OC_CypherContext context = cyphPars.oC_Cypher();

        this.extractor = extractor;

        // System.out.print(context.toString());

        for(int i = 0; i < context.getChildCount(); i++)
        {


            final ParseTree tree = context.getChild(i);

           // System.out.println(context.getChildCount());

            if (tree instanceof CypherParser.OC_StatementContext)
            {


                // Different executing statements:

                // Starting at the top ...
                 executeStatement((CypherParser.OC_StatementContext) tree);


            }

        }



    }

    private void executeStatement(final CypherParser.OC_StatementContext statement)
    {
        final CypherParser.OC_QueryContext query = statement.oC_Query();

        if (query.oC_StandaloneCall() != null)
        {
            System.out.println("OC_StandaloneCall:"+query.oC_StandaloneCall());
            query.oC_StandaloneCall();
        }
        else if (query.oC_RegularQuery() != null)
        {
            executeSingleQuery(query.oC_RegularQuery());
            System.out.println("OC_RegQuery:"+query.oC_RegularQuery());
        }


    }



    private void executeSingleQuery(CypherParser.OC_RegularQueryContext statement)
    {

        final CypherParser.OC_SingleQueryContext query = statement.oC_SingleQuery();


        if (query.oC_SinglePartQuery() != null)
        {
        //    System.out.println("OC_SingleQuery:"+query.toString()); // Write this in a variable later (if  required)
            executeSinglePartQuery(query.oC_SinglePartQuery());

        }

        // TODO: Add if MultiPartQuery later!



    }

    private void executeSinglePartQuery(CypherParser.OC_SinglePartQueryContext statement)
    {

        final CypherParser.OC_SinglePartQueryContext query = statement;


        for (int i = 0; i < query.getChildCount(); i++)
        {
            if (query.oC_ReadingClause(i) != null)
            {

            //    System.out.println("OC_ReadingClause:"+query.oC_ReadingClause(i).toString()); // Write this in a variable later (if  required)
                executeReadingClause(query.oC_ReadingClause(i));



            }

            else if (query.oC_UpdatingClause(i) != null)
            {

            //    System.out.println("OC_UpdatingClause:"+query.oC_UpdatingClause(i).toString());
                executeUpdatingClause(query.oC_UpdatingClause(i));

            }

            else if(query.oC_Return() != null)
            {

            //    System.out.println("OC_Return:"+query.oC_Return().toString());

          //      System.out.println("return:  "+query.oC_Return().RETURN().toString());  //  Return RETURN easier.

                // System.out.println(query.oC_Return().oC_ProjectionBody().children.toString());  // Contains the blank space (?)

                executeReturnClause(query.oC_Return());





            }

            else
            {

             //   System.out.println("Empty / placeholder in SinglePartQuery"+"\n");

            }

        }

        // Testing variable saving ...
        System.out.print(this.extractor.labelStorage[0]);
        System.out.print(this.extractor.variableStorage[0]);


    }

    private void executeReadingClause(CypherParser.OC_ReadingClauseContext statement)
    {
        final CypherParser.OC_ReadingClauseContext query = statement;

        if (query.oC_Match() != null)
        {

            System.out.println("OC_Match (Reading):"+query.oC_Match().toString());
            //System.out.println("Returns CHILDREN:"+query.oC_Match().children.toString());
            System.out.println("match: "+query.oC_Match().MATCH().toString());  //  Return MATCH easier.
            executeMatchClause(query.oC_Match());

            /*
            //  This loop aims to find the operator MATCH in the query.
            for (int j = 0; j  < query.oC_Match().getChildCount(); j ++)
            {
                if (query.oC_Match().getChild(j).toString().startsWith("MATCH"))
                {
                    System.out.println("Return MATCH: "+query.oC_Match().getChild(j).toString());  // Here, we return  the MATCH OPERATOR!
                }
                else
                {
                   // System.out.print("No MATCH");
                }

            }  */


        }

        else if(query.oC_Unwind() != null)
        {

        //    System.out.println("OC_Unwind (Reading):"+query.oC_Unwind().toString());

        }

        else if(query.oC_InQueryCall() != null)
        {

        //    System.out.println("OC_InQueryCall (Reading):"+query.oC_InQueryCall().toString());

        }


    }

    private void executeUpdatingClause(CypherParser.OC_UpdatingClauseContext statement)
    {

        final CypherParser.OC_UpdatingClauseContext query = statement;

        if (query.oC_Create() != null)
        {

        //    System.out.println("OC_Create:"+query.oC_Create().toString());

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

    private void executeMatchClause(CypherParser.OC_MatchContext statement)
    {
        final CypherParser.OC_MatchContext query = statement;


        if (query.oC_Pattern() != null)
        {

         //   System.out.println("OC Pattern: "+query.oC_Pattern().toString());
            executePatternClause(query.oC_Pattern());

        }

        else if (query.oC_Where() != null)
        {

       //  System.out.print("Query OC WHERE - Placeholder");

        }

    }

    private void executePatternClause(CypherParser.OC_PatternContext statement)
    {

        final CypherParser.OC_PatternContext query = statement;

        for (int i = 0;  i < query.getChildCount(); i++)
        {
            if (query.oC_PatternPart(i) != null)
            {
            //    System.out.println("OC_PatternPart: "+query.oC_PatternPart(i).toString());
                executePatternPartClause(query.oC_PatternPart(i));

            }
            else
            {

            //    System.out.println("Empty PatternClause");

            }


        }

    }

    private void executePatternPartClause(CypherParser.OC_PatternPartContext statement)
    {
        final CypherParser.OC_PatternPartContext query = statement;

        if (query.oC_Variable() != null)
        {

        //    System.out.println("OC_Variable: "+query.oC_Variable().toString());

        }

        else if (query.oC_AnonymousPatternPart() != null)
        {

         //   System.out.println("OC_AnonymousPatternPart: "+ query.oC_AnonymousPatternPart().toString());
            executeAnonymousPatternPartClause(query.oC_AnonymousPatternPart());

        }


    }

    private void executeAnonymousPatternPartClause(CypherParser.OC_AnonymousPatternPartContext statement)
    {

        final CypherParser.OC_AnonymousPatternPartContext query = statement;

        if (query.oC_PatternElement() != null)
        {
       //     System.out.println("OC_PatternElement: "+ query.oC_PatternElement().toString());
            executePatternElementClause(query.oC_PatternElement());

        }

        else
        {

        //    System.out.println("Empty AnonymousPatternPart");

        }


    }

    private void executePatternElementClause(CypherParser.OC_PatternElementContext statement)
    {

        final CypherParser.OC_PatternElementContext query = statement;

        if (query.oC_NodePattern() != null)
        {

        //    System.out.println("OC_NodePattern: "+ query.oC_NodePattern().toString());
            executeNodePatternClause(query.oC_NodePattern());


        }

        else if (query.oC_PatternElement() != null)
        {
       //     System.out.println("OC_PatternElement: "+ query.oC_PatternElement().toString());
            executePatternElementClause(query.oC_PatternElement());

        }
        else if (query.oC_PatternElementChain(0)  != null)
        {

            for (int i = 0; i < query.getChildCount(); i++)
            {

            //    System.out.println("OC_PatternElementChain: "+ query.oC_PatternElementChain(i).toString());

            }

        }

    }

    private void executeNodePatternClause(CypherParser.OC_NodePatternContext statement)
    {
        final CypherParser.OC_NodePatternContext query = statement;

        if (query.oC_NodeLabels() !=  null)
        {
        //    System.out.println("OC_NodeLabels:"+query.oC_NodeLabels().toString());
            executeNodeLabelClause(query.oC_NodeLabels());


        }
        
        else if (query.oC_Variable() != null)
        {
       //     System.out.println("OC_Variable:"+query.oC_Variable().toString());

        }

        else if (query.oC_Properties() != null)
        {

        //    System.out.println("OC_Properties:"+query.oC_Properties().toString());

        }

    }

    private void executeNodeLabelClause(CypherParser.OC_NodeLabelsContext statement)
    {
        final CypherParser.OC_NodeLabelsContext query = statement;

        if (query.oC_NodeLabel() != null)
        {
            for (int i = 0; i < query.getChildCount(); i++)
            {

            //    System.out.println("OC_NodeLabel: "+ query.oC_NodeLabel(i).toString());
                executeLabelNameClause(query.oC_NodeLabel(i));

            }
        }

    }

    private void executeLabelNameClause(CypherParser.OC_NodeLabelContext statement)
    {
        final CypherParser.OC_NodeLabelContext query = statement;

        if (query.oC_LabelName() != null)
        {
        //    System.out.println("OC_LabelName: "+ query.oC_LabelName().toString());
            executeSchemaNameClause(query.oC_LabelName());

        }

    }

    private void executeSchemaNameClause(CypherParser.OC_LabelNameContext statement)
    {

        final CypherParser.OC_LabelNameContext query = statement;

        if (query.oC_SchemaName() != null)
        {

        //    System.out.println("OC_SchemaName: "+ query.oC_SchemaName().toString());
            executeSymbolicWordClause(query.oC_SchemaName());

        }



    }

    private void executeSymbolicWordClause(CypherParser.OC_SchemaNameContext statement)
    {
        final CypherParser.OC_SchemaNameContext query = statement;


        if (query.oC_SymbolicName() != null)
        {
         //   System.out.println("OC_SymbolicName: "+ query.oC_SymbolicName().toString());
         //   System.out.println("OC_SymbolicName_TERMINAL: "+ query.oC_SymbolicName().children.toString()); // Returns the Label ("Gene") in the current example.
            this.extractor.saveLabels(query.oC_SymbolicName().children.toString());



        }
        else if (query.oC_ReservedWord() != null)
        {

        //    System.out.println("OC_ReserveWord: "+ query.oC_ReservedWord().toString());

        }



    }


    private void executeReturnClause(CypherParser.OC_ReturnContext statement)
    {
        final CypherParser.OC_ReturnContext query = statement;

        if (query.oC_ProjectionBody() !=  null)
        {
        //    System.out.println("OC_ProjectionBody:"+query.oC_ProjectionBody().toString());
            executeProjectionBody(query.oC_ProjectionBody());


        }

    }

    private void executeProjectionBody(CypherParser.OC_ProjectionBodyContext statement)
    {

        final CypherParser.OC_ProjectionBodyContext query = statement;

        if(query.oC_Limit() !=  null)
        {
            System.out.println("OC_Limit:"+query.oC_Limit().toString());
        }

        else if (query.oC_ProjectionItems() != null)
        {

          //  System.out.println("OC_ProjectionItems:"+query.oC_ProjectionItems().toString());

            executeProjectionItemsClause(query.oC_ProjectionItems());
        }

        else if(query.oC_Order() != null)
        {
            System.out.println("OC_Order:"+query.oC_Order().toString());
        }
        else if(query.oC_Skip() != null)
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

            //    System.out.println("OC_ProjectionItem_i:"+query.oC_ProjectionItem(i).toString());
                executeVariableClause(query.oC_ProjectionItem(i));

            }

        }


    }

    private void executeVariableClause(CypherParser.OC_ProjectionItemContext statement)
    {
        final CypherParser.OC_ProjectionItemContext query = statement;

        if (query.oC_Variable() != null)
        {

            System.out.print("OC_Variable:"+query.oC_Variable().toString());
            //TODO: Execute variable

        }

        else if (query.oC_Expression() != null)
        {

       //     System.out.print("OC_Expression:"+query.oC_Expression().toString());
            executeOrExpressionClause(query.oC_Expression());

        }

    }

    private void executeOrExpressionClause(CypherParser.OC_ExpressionContext statement)
    {
        final CypherParser.OC_ExpressionContext query = statement;

        if (query.oC_OrExpression() != null)
        {

        //    System.out.print("OC_ORExpression:"+query.oC_OrExpression().toString());
            executeXorExpressionClause(query.oC_OrExpression());

        }

    }

    private void executeXorExpressionClause(CypherParser.OC_OrExpressionContext statement)
    {

        final CypherParser.OC_OrExpressionContext query = statement;

        for (int i = 0; i < query.getChildCount();  i++)
        {

            if (query.oC_XorExpression(i) !=  null)
            {
             //   System.out.print("OC_XoRExpression:"+query.oC_XorExpression(i).toString());
                executeAndExpressionClause(query.oC_XorExpression(i));

            }


        }


    }

    private void executeAndExpressionClause(CypherParser.OC_XorExpressionContext statement)
    {

        final CypherParser.OC_XorExpressionContext query = statement;

        for (int i = 0; i < query.getChildCount();  i++)
        {

            if (query.oC_AndExpression(i) !=  null)
            {

            //    System.out.print("OC_AndExpression:"+query.oC_AndExpression(i).toString());
                executeNotExpressionClause(query.oC_AndExpression(i));

            }


        }

    }

    private void executeNotExpressionClause(CypherParser.OC_AndExpressionContext statement)
    {

        final CypherParser.OC_AndExpressionContext query = statement;

        for (int i = 0; i < query.getChildCount();  i++)
        {

            if (query.oC_NotExpression(i) !=  null)
            {

           //     System.out.print("OC_NotExpression:"+query.oC_NotExpression(i).toString());
                executeComparisonExpressionClause(query.oC_NotExpression(i));

            }


        }

    }

    private void executeComparisonExpressionClause(CypherParser.OC_NotExpressionContext statement)
    {

        final CypherParser.OC_NotExpressionContext query = statement;


            if (query.oC_ComparisonExpression() != null)
            {

           //     System.out.print("OC_ComparisonExpression:"+query.oC_ComparisonExpression().toString());
                executeAddOrSubtractExpressionClause(query.oC_ComparisonExpression());

            }

    }

    private void executeAddOrSubtractExpressionClause(CypherParser.OC_ComparisonExpressionContext statement)
    {

        final CypherParser.OC_ComparisonExpressionContext query = statement;


        if (query.oC_AddOrSubtractExpression() != null)
        {

       //     System.out.print("OC_AddOrSubtractExpression:"+query.oC_AddOrSubtractExpression().toString());
            executeMultiplyDivideModuloExpressionClause(query.oC_AddOrSubtractExpression());

        }

        else if (query.oC_PartialComparisonExpression(0) != null)
        {
         //   TODO: Usual stuff later
        }

    }

    private void executeMultiplyDivideModuloExpressionClause(CypherParser.OC_AddOrSubtractExpressionContext statement)
    {

        final CypherParser.OC_AddOrSubtractExpressionContext query = statement;

        for (int i= 0; i < query.getChildCount(); i++)
        {

            if (query.oC_MultiplyDivideModuloExpression(i) !=  null)
            {

            //    System.out.print("OC_MultiplyDivideModulo:"+query.oC_MultiplyDivideModuloExpression(i).toString());
                executePowerOfExpressionClause(query.oC_MultiplyDivideModuloExpression(i));

            }

        }

    }

    private void executePowerOfExpressionClause(CypherParser.OC_MultiplyDivideModuloExpressionContext statement)
    {

        final CypherParser.OC_MultiplyDivideModuloExpressionContext query = statement;

        for (int i= 0; i < query.getChildCount(); i++)
        {

            if (query.oC_PowerOfExpression(i) !=  null)
            {

            //    System.out.print("OC_PowerOf:"+query.oC_PowerOfExpression(i).toString());
                executeUnaryAddOrSubtractExpressionClause(query.oC_PowerOfExpression(i));

            }

        }

    }

    private void executeUnaryAddOrSubtractExpressionClause(CypherParser.OC_PowerOfExpressionContext statement)
    {

        final CypherParser.OC_PowerOfExpressionContext query = statement;

        for (int i= 0; i < query.getChildCount(); i++)
        {

            if (query.oC_UnaryAddOrSubtractExpression(i) !=  null)
            {

            //    System.out.print("OC_UnaryAddOrSubtractExpression:"+query.oC_UnaryAddOrSubtractExpression(i).toString());
                executeStringListNullOperatorExpressionClause(query.oC_UnaryAddOrSubtractExpression(i));

            }

        }

    }

    private void executeStringListNullOperatorExpressionClause(CypherParser.OC_UnaryAddOrSubtractExpressionContext statement)
    {

        final CypherParser.OC_UnaryAddOrSubtractExpressionContext query = statement;


            if (query.oC_StringListNullOperatorExpression() !=  null)
            {

            //    System.out.print("OC_StringListNullOperatorExpression():"+query.oC_StringListNullOperatorExpression().toString());
                executePropertyOrLabelspressionClause(query.oC_StringListNullOperatorExpression());

            }


    }

    private void executePropertyOrLabelspressionClause(CypherParser.OC_StringListNullOperatorExpressionContext statement)
    {

        final CypherParser.OC_StringListNullOperatorExpressionContext query = statement;


        if (query.oC_PropertyOrLabelsExpression() !=  null)
        {

        //    System.out.print("OC_PropertyOrLabelExpression():"+query.oC_PropertyOrLabelsExpression().toString());
            executeAtomClause(query.oC_PropertyOrLabelsExpression());

        }

        // TODO: Other else ifs.


    }

    private void executeAtomClause(CypherParser.OC_PropertyOrLabelsExpressionContext statement)
    {

        final CypherParser.OC_PropertyOrLabelsExpressionContext query = statement;


        if (query.oC_Atom() !=  null)
        {

           // System.out.print("Oc_AtomExpression():"+query.oC_Atom().toString());
            executeLiteralClause(query.oC_Atom());

        }
        else if (query.oC_NodeLabels() != null)
        {
        //    System.out.println("OC_NodeLabels  (executeAtom): "+query.oC_NodeLabels().toString());

        }

        // TODO: Other else ifs.


    }

    // Can extract variable  ("n"  here)!

    private void executeLiteralClause(CypherParser.OC_AtomContext statement)
    {

        final CypherParser.OC_AtomContext query = statement;


        if (query.oC_Literal()!=  null)
        {

         //   System.out.print("Oc_Literal()_VARIABLE:"+query.oC_Literal().children.toString());

        }
        else if (query.oC_Variable() != null)
        {
            // System.out.print("Oc_Variable()_VARIABLE:"+query.oC_Variable().children.toString());
            // System.out.print("Oc_SymbolicName_VARIABLE:"+query.oC_Variable().oC_SymbolicName().children.toString());  // <-- VARIABLE N STORED HERE !!
            System.out.println(query.oC_Variable().oC_SymbolicName().children.get(0).toString());
            this.extractor.saveVariables(query.oC_Variable().oC_SymbolicName().children.get(0).toString());

        }

        // TODO: Other else ifs.


    }

    // Experimental function.

    private void returnSymbolicName(final CypherParser.OC_StatementContext statement)
    {

        String label = "";

        System.out.println(statement.oC_Query());
        label  = statement.oC_Query()
                          .oC_RegularQuery()
                          .oC_SingleQuery()
                         .oC_SinglePartQuery()
                          .oC_ReadingClause(0)
                          .oC_Match()
                          .oC_Pattern()
                          .oC_PatternPart(0)
                          .oC_AnonymousPatternPart()
                          .oC_PatternElement()
                          .oC_NodePattern()
                          .oC_NodeLabels()
                          .oC_NodeLabel(0)
                          .oC_LabelName()
                          .oC_SchemaName().oC_SymbolicName()
                          .toString();
        System.out.println("TESTING"+label+"\n");


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
      //  TreeViewer viewer = new TreeViewer(Arrays.asList(cyphPars.getRuleNames()), tree);
      //  panel.add(viewer);
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);


    }




}
