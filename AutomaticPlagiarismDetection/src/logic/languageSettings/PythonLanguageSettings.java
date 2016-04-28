package logic.languageSettings;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.BufferedTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.ParserRuleContext;

import grammars.python.Python3Lexer;
import grammars.python.Python3Parser;

public class PythonLanguageSettings implements LanguageSettings {

	@Override
	public LanguageAnalyzer buildLanguageAnalyzer(ANTLRInputStream inputStream) {
		return new LanguageAnalyzer(inputStream, new SpecificLanguage() {
			
			@Override
			public ParserRuleContext getInitialRule(Parser parser) {
				return ( (Python3Parser) parser ).file_input();
			}
			
			@Override
			public Parser buildParser(BufferedTokenStream tokenStream) {
				return new Python3Parser(tokenStream);
			}
			
			@Override
			public Lexer buildLexer(ANTLRInputStream inputStream) {
				return new Python3Lexer(inputStream);
			}
		});
	}

	@Override
	public String getLanguageName() {
		return "Python 3";
	}

	@Override
	public String getFileNameExtension() {
		return "py";
	}

}
