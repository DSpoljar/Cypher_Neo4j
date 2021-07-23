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

                // Starting on the top ...
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


        if (query != null)
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
            System.out.println("OC_Match:"+query.oC_Match().toString());

        }
        else
        {

            System.out.println("Empty / Placeholder in ReadingClause" + "\n");

        }


    }

    private void executeUpdatingClause(CypherParser.OC_UpdatingClauseContext statement)
    {

        // TODO: The usual stuff

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
     TODO: Possible rei-integration of TreeViewer at later point
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
