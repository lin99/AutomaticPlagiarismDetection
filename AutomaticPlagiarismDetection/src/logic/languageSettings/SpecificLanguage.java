package logic.languageSettings;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.BufferedTokenStream;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Lexer;


public interface SpecificLanguage {
	
	ParserRuleContext getInitialRule( Parser parser ); //parser is the same instance returned by buildParser 
	Lexer buildLexer( ANTLRInputStream inputStream );
	Parser buildParser( BufferedTokenStream tokenStream );
	
}
