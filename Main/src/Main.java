import Cypher_Files.*;
import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenSource;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;


import java.io.*;
import java.util.stream.IntStream;


public class Main
{
    public static void main(String[] args) throws IOException
    {

        IOHandler handler = new IOHandler(); // Creating a simple IO machinery as base for upcoming tasks.

        String query = handler.inputReader();
        // InputStream inptStream = new FileInputStream(query);

        CharStream test = (CharStream) CharStreams.fromString(query);
        // ANTLRInputStream in = new ANTLRInputStream(inptStream);

        CypherLexer cyphLexer = new CypherLexer(test);

        TokenStream tokenStream = new CommonTokenStream(cyphLexer);

        CypherParser cyphPars = new CypherParser(tokenStream);

        cyphPars.oC_Query();

        /*
        String query2 =
        "MATCH (n)" + "\n" +
        "WHERE id(n) = $id" + "\n" +
           "RETURN n.name";
         */






    }

}
