package logic.languageSettings;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.BufferedTokenStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.ParserRuleContext;


public class LanguageAnalyzer {
	
	private Lexer lexer;
	private Parser parser;
	private BufferedTokenStream tokenStream;
	private ANTLRInputStream inputStream;
	private SpecificLanguage language;
	
	public LanguageAnalyzer(ANTLRInputStream inputStream, SpecificLanguage language) {
		this.inputStream = inputStream;
		this.language = language;
	}

	public Lexer lexer(){
		if( inputStream == null )
			throw new RuntimeException( "Input stream is null" );
		
		if(lexer == null){
			lexer =  language.buildLexer(inputStream);
			tokenStream = new CommonTokenStream(lexer);
		}
		
		return lexer;
	}
	
	public Parser parser(){
		if( tokenStream == null )
			lexer();
		
		if( parser == null )
			parser = language.buildParser(tokenStream);
		
		return parser;
	}
	
	public ParserRuleContext getInitialRule(){
		return language.getInitialRule( parser() );
	}
}
