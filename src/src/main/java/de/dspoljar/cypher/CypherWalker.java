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

        for(int i = 0; i < context.getChildCount(); i++)
        {


            final ParseTree tree = context.getChild(i);
            //System.out.print("before if:"+tree.toString());
            if (tree instanceof CypherParser.OC_StatementContext)

                // Different executing statements:

               //executeRegularStatement((CypherParser.OC_StatementContext) tree);
               // returnSymbolicName((CypherParser.OC_StatementContext) tree);
                //executeSingularStatement((CypherParser.OC_StatementContext)  tree);
                returnLabel((CypherParser.OC_StatementContext) tree);
                // System.out.print("in if"+context.getChild(i).toString());


        }

    }

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


    private void executeRegularStatement(final CypherParser.OC_StatementContext statement)
    {
        final CypherParser.OC_QueryContext query = statement.oC_Query();
        if (query.oC_StandaloneCall() != null)
        {
            System.out.println("OC_StandaloneCall:"+query.oC_StandaloneCall());
            query.oC_StandaloneCall();
        }
        else if (query.oC_RegularQuery() != null)
        {
            query.oC_RegularQuery();
            System.out.println("OC_RegQuery:"+query.oC_RegularQuery());
        }


    }

    private void executeSingularStatement(final CypherParser.OC_StatementContext statement)
    {

        if (statement.oC_Query().oC_StandaloneCall() != null)
        {
            System.out.println("OC_StandaloneCall:"+statement.oC_Query().oC_StandaloneCall());
            statement.oC_Query().oC_StandaloneCall();
        }
        else if (statement.oC_Query().oC_RegularQuery() != null)
        {
            statement.oC_Query().oC_RegularQuery();
            System.out.println("OC_SingQuery:"+statement.oC_Query().oC_RegularQuery().oC_SingleQuery());
        }


    }

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



    // TODO: Für jedes Statement eine Methode



    /*
    // JFrame and Panel for visualization.
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
