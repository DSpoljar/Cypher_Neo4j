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

        String query = handler.inputReader();

        CypherWalker walker = new CypherWalker();

        walker.treeViewing(query);

        walker.nodeIterator(query);





    }




}
