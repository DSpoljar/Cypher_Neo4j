import Cypher_Files.CypherLexer;
import Cypher_Files.CypherParser;
import de.unibi.agbi.biodwh2.core.model.graph.Node;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenSource;
import org.antlr.v4.gui.TreeViewer;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import javax.swing.*;
import java.io.*;
import java.util.Arrays;
import java.util.stream.IntStream;


public class CypherWalker
{


    public CypherWalker()
    {
        super();
      //  CypherParser cyphPars = new CypherParser(tokenStream)


    }


    public String testIterator(String variable)
    {
        CharStream stream = (CharStream) CharStreams.fromString(variable);

        CypherLexer cyphLexer = new CypherLexer(stream);

        TokenStream tokenStream = new CommonTokenStream(cyphLexer);

        CypherParser cyphPars = new CypherParser(tokenStream);

        final CypherParser.OC_CypherContext context = cyphPars.oC_Cypher();

        for(int i = 0; i < context.getChildCount(); i++)
        {
            System.out.print( context.getChildCount());
            System.out.print(context.getChild(i)+ "\n");
            final ParseTree tree = context.getChild(i);
            if (variable == context.getChild(i).toString())
            {
                System.out.println(context.getChild(i).toString());
                return context.getChild(i).toString();

            }

        }

        return "No query";

    }


    public void nodeIterator(String query)
    {

        CharStream stream = (CharStream) CharStreams.fromString(query);

        CypherLexer cyphLexer = new CypherLexer(stream);

        TokenStream tokenStream = new CommonTokenStream(cyphLexer);

        CypherParser cyphPars = new CypherParser(tokenStream);

        final CypherParser.OC_CypherContext context = cyphPars.oC_Cypher();

   for(int i = 0; i < context.getChildCount(); i++) {
    final ParseTree tree = context.getChild(i);
    System.out.println(context.getChild(i));
    if (tree instanceof CypherParser.OC_StatementContext)
        executeStatement((CypherParser.OC_StatementContext) tree);
                                                        }


    }

    private void executeStatement(final CypherParser.OC_StatementContext statement)
    {
        final CypherParser.OC_QueryContext query = statement.oC_Query();
        if (query.oC_StandaloneCall() != null)
            query.oC_StandaloneCall();
        else if (query.oC_RegularQuery() != null)
            query.oC_RegularQuery();
    }

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
        TreeViewer viewer = new TreeViewer(Arrays.asList(cyphPars.getRuleNames()), tree);
        panel.add(viewer);
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);


    }




}
