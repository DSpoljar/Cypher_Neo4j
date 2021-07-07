import Cypher_Files.CypherLexer;
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

    }


    public String findNode(ParseTree tree, ParseTreeListener listener, Parser parser, String entry)
    {

        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(listener, tree);

        
        if (tree.toStringTree(parser).matches(entry)){
            System.out.println(entry);
            return entry;
        }

        else{
            System.out.print("Entry not found");
            return null;
        }


    }

    ParseTreeListener listener = new ParseTreeListener()
    {
        @Override
        public void visitTerminal(TerminalNode terminalNode) {

        }

        @Override
        public void visitErrorNode(ErrorNode errorNode) {

        }

        @Override
        public void enterEveryRule(ParserRuleContext parserRuleContext)
        {
            parserRuleContext.getText();

        }

        @Override
        public void exitEveryRule(ParserRuleContext parserRuleContext)
        {
            parserRuleContext.exitRule(listener);
        }
    };

}
