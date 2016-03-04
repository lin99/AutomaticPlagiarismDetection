package model;

import java.io.IOException;

import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.DefaultErrorStrategy;
import org.antlr.v4.runtime.atn.PredictionMode;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.ParseTree;

import grammars.java.*;

/*
 * This class encapsulates the process of reading a source code
 * and translating it into a string.
 * 
 * This class must be instantiated with the filename of the 
 * source code to be opened.
 * 
 * @author: Felipe
 */

public class SourceCodeToStringBuilder {
	private String fileName;
	
	
	
	public SourceCodeToStringBuilder(String fileName) {
		super();
		this.fileName = fileName;
	}


	/*
	 * This method reads the source code of fileName
	 * and returns a tree representation encoded in a
	 * String.
	 */
	public String readSourceCode() throws IOException{
		
		/*
		 * Based on: 
		 * http://stackoverflow.com/questions/32910013/antlr-v4-java8-grammar-outofmemoryexception/32918434#32918434
		 * There are some things that do not compile though..
		 */
		JavaLexer lexer = new JavaLexer(new ANTLRFileStream(fileName));
		CommonTokenStream tokens = new CommonTokenStream(lexer);  // Crear el analizador sintáctico que se alimenta a partir del buffer de tokens  
		JavaParser parser = new JavaParser(tokens);  
		JavaParser.CompilationUnitContext compilationUnit = parser.compilationUnit(); // Compilation unit es la regla inicial de la gramatica de java, si se desea cambiar de gramatica se debe cambiar esta regla inicial.
		ParseTree tree = compilationUnit; // comienza el análisis en la regla inicial
		
		try {
			  // Stage 1: High-speed parsing for correct documents

			  parser.setErrorHandler(new BailErrorStrategy());
			  parser.getInterpreter().setPredictionMode(PredictionMode.SLL);
			  //parser.getInterpreter().tail_call_preserves_sll = false;
			  compilationUnit = parser.compilationUnit();
			} catch (ParseCancellationException e) {
			  // Stage 2: High-accuracy fallback parsing for complex and/or erroneous documents

			  // TODO: reset your input stream
			  parser.setErrorHandler(new DefaultErrorStrategy());
			  parser.getInterpreter().setPredictionMode(PredictionMode.LL);
			  //parser.getInterpreter().tail_call_preserves_sll = false;
			  //parser.getInterpreter().enable_global_context_dfa = true;
			  compilationUnit = parser.compilationUnit();
			}
		
		
		return tree.toStringTree(parser);
	}
}
