package logic.languageSettings;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.BufferedTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.ParserRuleContext;

import grammars.java.JavaLexer;
import grammars.java.JavaParser;

public class JavaLanguageSettings implements LanguageSettings {

	@Override
	public String getLanguageName() {
		return "Java 7";
	}
	
	@Override
	public String getFileNameExtension() {
		return "java";
	}
	
	@Override
	public LanguageAnalyzer buildLanguageAnalyzer(ANTLRInputStream inputStream) {
		
		
		return new LanguageAnalyzer(inputStream,  new SpecificLanguage() {
			
			@Override
			public ParserRuleContext getInitialRule(Parser parser) {
				return ( (JavaParser) parser ).compilationUnit();
			}
			
			@Override
			public Parser buildParser(BufferedTokenStream tokenStream) {
				return new JavaParser(tokenStream);
			}
			
			@Override
			public Lexer buildLexer(ANTLRInputStream inputStream) {
				return new JavaLexer(inputStream);
			}

			
		} );
	}
}
