import Cypher_Files.*;
import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenSource;
import org.antlr.v4.gui.TreeViewer;
import org.antlr.v4.misc.Graph;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import javax.swing.*;
import java.io.*;
import java.util.Arrays;
import java.util.stream.IntStream;


public class Main
{




    public static void main(String[] args) throws IOException
    {

        IOHandler handler = new IOHandler(); // Creating a simple IO machinery as base for upcoming tasks.

        CypherWalker walker = new CypherWalker();

        String query = handler.inputReader();



        // This may be used in case we'll work with inputs over files.
        // InputStream inptStream = new FileInputStream(query);

        CharStream test = (CharStream) CharStreams.fromString(query);
        // ANTLRInputStream in = new ANTLRInputStream(inptStream); <- Deprecated?

        CypherLexer cyphLexer = new CypherLexer(test);

        TokenStream tokenStream = new CommonTokenStream(cyphLexer);

        CypherParser cyphPars = new CypherParser(tokenStream);



        ParseTree tree = cyphPars.oC_Cypher();






        // Output tree in pure String format. Use CREATE (adam:User {name: 'Adam'}) as test.

        System.out.println(tree.toStringTree(cyphPars));

        //walker.listener.enterEveryRule(cyphPars.getRuleContext());
        //walker.findNode(tree, walker.listener, cyphPars, "Adam");





        // JFrame and Panel for visualization.

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
