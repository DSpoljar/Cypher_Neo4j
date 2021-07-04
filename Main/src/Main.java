import Cypher_Files.*;
import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenSource;
import org.antlr.v4.gui.TreeViewer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;
import javax.swing.*;
import java.io.*;
import java.util.Arrays;
import java.util.stream.IntStream;


public class Main
{
    public static void main(String[] args) throws IOException
    {

        IOHandler handler = new IOHandler(); // Creating a simple IO machinery as base for upcoming tasks.

        String query = handler.inputReader();

        // This may be used in case we'll work with inputs over files.
        // InputStream inptStream = new FileInputStream(query);

        CharStream test = (CharStream) CharStreams.fromString(query);
        // ANTLRInputStream in = new ANTLRInputStream(inptStream); <- Deprecated?

        CypherLexer cyphLexer = new CypherLexer(test);

        TokenStream tokenStream = new CommonTokenStream(cyphLexer);

        CypherParser cyphPars = new CypherParser(tokenStream);

        // cyphPars.oC_Query();

        ParseTree tree = cyphPars.oC_Query();

        // Output tree in pure String format.
        System.out.println(tree.toStringTree(cyphPars));


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
