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


    public CypherWalker()
    {
        super();
      //  CypherParser cyphPars = new CypherParser(tokenStream)


    }



    public void acceptQuery(Graph graph, String query)
    {

        CharStream stream = (CharStream) CharStreams.fromString(query);

        CypherLexer cyphLexer = new CypherLexer(stream);

        TokenStream tokenStream = new CommonTokenStream(cyphLexer);

        CypherParser cyphPars = new CypherParser(tokenStream);

        final CypherParser.OC_CypherContext context = cyphPars.oC_Cypher();

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

                // returnSymbolicName((CypherParser.OC_StatementContext) tree);
                // executeSingularStatement((CypherParser.OC_StatementContext)  tree);
                // returnLabel((CypherParser.OC_StatementContext) tree);
                // System.out.print("in if"+context.getChild(i).toString());


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
            System.out.println("OC_SingleQuery:"+query.toString()); // Write this in a variable later (if  required)
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

                System.out.println("OC_ReadingClause:"+query.oC_ReadingClause(i).toString()); // Write this in a variable later (if  required)
                executeReadingClause(query.oC_ReadingClause(i));



            }

            else if (query.oC_UpdatingClause(i) != null)
            {
                System.out.println("OC_UpdatingClause:"+query.oC_UpdatingClause(i).toString());
                executeUpdatingClause(query.oC_UpdatingClause(i));

            }

            else if(query.oC_Return() != null)
            {

                System.out.println("OC_Return:"+query.oC_Return().toString());

                System.out.println("return:  "+query.oC_Return().RETURN().toString());  //  Return RETURN easier.

                // System.out.println(query.oC_Return().oC_ProjectionBody().children.toString());  // Contains the blank space (?)

                executeReturnClause(query.oC_Return());



                /*
                //  This loop aims to find the operator RETURN in the query.
                for (int j = 0; j  < query.oC_Return().getChildCount(); j ++)
                {
                    if (query.oC_Return().getChild(j).toString().startsWith("RETURN"))
                    {
                        System.out.println("Return RETURN: "+query.oC_Return().getChild(j).toString());  // Return RETURN.
                    }
                    else
                    {
                        // System.out.print("No MATCH");
                    }

                } */



            }

            else
            {
                System.out.println("Empty / placeholder in SinglePartQuery"+"\n");
            }

        }


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

            System.out.println("OC_Unwind (Reading):"+query.oC_Unwind().toString());

        }

        else if(query.oC_InQueryCall() != null)
        {

            System.out.println("OC_InQueryCall (Reading):"+query.oC_InQueryCall().toString());

        }


    }

    private void executeUpdatingClause(CypherParser.OC_UpdatingClauseContext statement)
    {

        final CypherParser.OC_UpdatingClauseContext query = statement;

        if (query.oC_Create() != null)
        {

            System.out.println("OC_Create:"+query.oC_Create().toString());

        }

        else if (query.oC_Delete() != null)
        {
            System.out.println("OC_Delete:"+query.oC_Delete().toString());

        }

        else if (query.oC_Merge() != null)
        {
            System.out.println("OC_Merge:"+query.oC_Merge().toString());

        }
        else if (query.oC_Set() != null)
        {

            System.out.println("OC_Set:"+query.oC_Set().toString());

        }


    }

    private void executeMatchClause(CypherParser.OC_MatchContext statement)
    {
        final CypherParser.OC_MatchContext query = statement;


        if (query.oC_Pattern() != null)
        {

            System.out.println("OC Pattern: "+query.oC_Pattern().toString());
            executePatternClause(query.oC_Pattern());

        }

        else if (query.oC_Where() != null)
        {

         System.out.print("Query OC WHERE - Placeholder");

        }

    }

    private void executePatternClause(CypherParser.OC_PatternContext statement)
    {

        final CypherParser.OC_PatternContext query = statement;

        for (int i = 0;  i < query.getChildCount(); i++)
        {
            if (query.oC_PatternPart(i) != null)
            {
                System.out.println("OC_PatternPart: "+query.oC_PatternPart(i).toString());
                executePatternPartClause(query.oC_PatternPart(i));

            }
            else
            {

                System.out.println("Empty PatternClause");

            }


        }

    }

    private void executePatternPartClause(CypherParser.OC_PatternPartContext statement)
    {
        final CypherParser.OC_PatternPartContext query = statement;

        if (query.oC_Variable() != null)
        {

            System.out.println("OC_Variable: "+query.oC_Variable().toString());

        }

        else if (query.oC_AnonymousPatternPart() != null)
        {

            System.out.println("OC_AnonymousPatternPart: "+ query.oC_AnonymousPatternPart().toString());
            executeAnonymousPatternPartClause(query.oC_AnonymousPatternPart());

        }


    }

    private void executeAnonymousPatternPartClause(CypherParser.OC_AnonymousPatternPartContext statement)
    {

        final CypherParser.OC_AnonymousPatternPartContext query = statement;

        if (query.oC_PatternElement() != null)
        {
            System.out.println("OC_PatternElement: "+ query.oC_PatternElement().toString());

        }

        else
        {

            System.out.println("Empty AnonymousPatternPart");

        }


    }

    private void executeReturnClause(CypherParser.OC_ReturnContext statement)
    {
        final CypherParser.OC_ReturnContext query = statement;

        if (query.oC_ProjectionBody() !=  null)
        {
            System.out.println("OC_ProjectionBody:"+query.oC_ProjectionBody().toString());
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
            System.out.println("OC_ProjectionItems:"+query.oC_ProjectionItems().toString());
            System.out.print("OC_ProjectionItems CHILDREN:"+query.oC_ProjectionItems().children.toString());
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




    // Experimental function.

    private String returnLabel(final CypherParser.OC_StatementContext statement)
    {
        String label =  "";


            System.out.println(statement.oC_Query());
            label  =
                    statement.oC_Query()
                             .oC_RegularQuery()
                             .oC_SingleQuery()
                             .oC_SinglePartQuery().oC_UpdatingClause(0)
                             .toString();
                             /*
                             .oC_Create()
                             .oC_Pattern()
                             .oC_PatternPart(i)
                             .oC_AnonymousPatternPart()
                             .oC_PatternElement()
                             .oC_NodePattern()
                             .oC_Properties()
                             .oC_MapLiteral()
                             .oC_Expression(i)
                             .oC_OrExpression()
                             .oC_XorExpression(i)
                             .oC_AndExpression(i)
                             .oC_NotExpression(i)
                             .oC_ComparisonExpression()
                             .oC_AddOrSubtractExpression()
                             .oC_MultiplyDivideModuloExpression(i)
                             .oC_PowerOfExpression(i)
                             .oC_UnaryAddOrSubtractExpression(i)
                             .oC_StringListNullOperatorExpression()
                             .oC_PropertyOrLabelsExpression()
                             .oC_Atom()
                             .oC_Literal().toString(); */

            System.out.println(label);


    return label;

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
