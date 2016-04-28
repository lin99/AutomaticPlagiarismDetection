package logic.languageSettings;

import org.antlr.v4.runtime.ANTLRInputStream;

public interface LanguageSettings {
	LanguageAnalyzer buildLanguageAnalyzer(ANTLRInputStream inputStream);
	String getLanguageName();
	String getFileNameExtension();
}
