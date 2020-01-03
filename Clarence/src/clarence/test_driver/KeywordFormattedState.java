package clarence.test_driver;

import java.util.ArrayList;

import clarence.key_file.KeywordFile;
import clarence.key_reader.CommandEntry;
import clarence.key_reader.DefaultType;
import clarence.key_reader.IntToFloat;
import clarence.key_reader.KeywordEntry;
import clarence.key_reader.KeywordType;

public class KeywordFormattedState implements ApplicationState{
	
	private int commandIndex;
	private KeywordFile file;
	private KeyFileEditorView view;
	
	public KeywordFormattedState(KeyFileEditorView view, KeywordFile file, int commandIndex) {
		this.view = view;
		this.file = file;
		this.commandIndex = commandIndex;
		showMessage();
	}
	
	private void showMessage() {
		//view.clearPrompt();
		view.appendPrompt("");
		view.appendPrompt("ENTER KEYWORD NUMBER OR NAME");
	}

	@Override
	public void command(String command) throws InvalidCommandException {
		try {
			int option = Integer.parseInt(command);
			loadKeyword(commandIndex,option-1);
			view.setApplicationState(new CommandOptionsState(view,file,commandIndex));
		} catch (NumberFormatException e) {
			throw new InvalidCommandException();
		}
		
	}

	@Override
	public void reload() {
		showMessage();		
	}
	
	private void loadKeyword(int commandIndex, int keywordIndex) {
		KeywordEntry keyword = file.getKeyword(commandIndex, keywordIndex);
		view.clearOutput();
		//view.appendOutput("");
		view.appendOutput(("DETAILS FOR KEYWORD " + keyword.name()));
		view.appendOutput(("NUMBER OF ELEMENTS: " + keyword.length()));
		KeywordType type = keyword.keywordType();
		System.out.println("TYPE " + type);
		if (type == KeywordType.INTEGER) {
			showIntegerKeyword(commandIndex, keywordIndex);
		} else if (type == KeywordType.REAL) {
			showRealKeyword(commandIndex, keywordIndex);
		} else if (type == KeywordType.STRING) {
			showStringKeyword(commandIndex, keywordIndex);
		} else if (type == KeywordType.OBJECT) {
			System.out.println("SHOWING OBJ");
			showObjectKeyword(commandIndex, keywordIndex);
		} else if (type == KeywordType.CODEWORD) {
			showCodewordKeyword(commandIndex, keywordIndex);
		}
	}
	
	private void showIntegerKeyword(int commandIndex, int keywordIndex) {
		KeywordEntry keyword = file.getKeyword(commandIndex, keywordIndex);
		view.appendOutput("TYPE: INTEGER" );
		view.appendOutput("MIN: " + keyword.minAsInt());
		view.appendOutput("MAX: " + keyword.maxAsInt());

		DefaultType defaultType = file.defaultType(commandIndex, keywordIndex);
		int defaultValueInteger = file.defaultValueInt(commandIndex, keywordIndex);
		view.appendOutput("DEFAULT TYPE: " + defaultType);
		if (defaultType == DefaultType.VALUE) {
			view.appendOutput("DEFAULT VALUE: " + (int) IntToFloat.convert(defaultValueInteger));
		} else if (defaultType == DefaultType.EXPRESSION) {
			view.appendOutput("DEFAULT EXPRESSION: " + file.defaultExpression(commandIndex, keywordIndex));
		}
	}

	private void showRealKeyword(int commandIndex, int keywordIndex) {
		KeywordEntry keyword = file.getKeyword(commandIndex, keywordIndex);
		view.appendOutput("TYPE: REAL");
		view.appendOutput("MIN: " + keyword.minAsFloat());
		view.appendOutput("MAX: " + keyword.maxAsFloat());

		DefaultType defaultType = file.defaultType(commandIndex, keywordIndex);
		int defaultValueInteger = file.defaultValueInt(commandIndex, keywordIndex);
		view.appendOutput("DEFAULT TYPE: " + defaultType);
		if (defaultType == DefaultType.VALUE) {
			view.appendOutput("DEFAULT VALUE: " + IntToFloat.convert(defaultValueInteger));
		} else if (defaultType == DefaultType.EXPRESSION) {
			view.appendOutput("DEFAULT EXPRESSION: " + file.defaultExpression(commandIndex, keywordIndex));
		}
	}

	private void showStringKeyword(int commandIndex, int keywordIndex) {
		view.appendOutput("TYPE: STRING");

		DefaultType defaultType = file.defaultType(commandIndex, keywordIndex);
		int defaultValueInteger = file.defaultValueInt(commandIndex, keywordIndex);
		view.appendOutput("DEFAULT TYPE: " + defaultType);
		if (defaultType == DefaultType.VALUE) {
			view.appendOutput("DEFAULT VALUE: " + defaultValueInteger);
		} else if (defaultType == DefaultType.EXPRESSION) {
			view.appendOutput("DEFAULT EXPRESSION: " + file.defaultExpression(commandIndex, keywordIndex));
		}

	}

	private void showObjectKeyword(int commandIndex, int keywordIndex) {
		int objectType = file.getKeyword(commandIndex, keywordIndex).min();
		CommandEntry commandEntry = file.getCommandOfType(objectType);
		if (commandEntry == null) {
			showCodewordKeyword(commandIndex, keywordIndex);
		} else {

			view.appendOutput("TYPE: OBJECT");
			view.appendOutput("ALLOWED COMMAND: " + file.allowedCommand(commandIndex, keywordIndex));
			view.appendOutput("ALLOWED TYPES: ");
			ArrayList<String> allowedTypes = file.allowedTypes(commandIndex, keywordIndex);
			for (int i = 0; i < allowedTypes.size(); i++) {
				view.appendOutput((i + 1) + ": " + allowedTypes.get(i));
			}

			DefaultType defaultType = file.defaultType(commandIndex, keywordIndex);
			int defaultValueInteger = file.defaultValueInt(commandIndex, keywordIndex);
			view.appendOutput("DEFAULT TYPE: " + defaultType);
			if (defaultType == DefaultType.VALUE) {
				view.appendOutput("DEFAULT VALUE: " + defaultValueInteger);
			} else if (defaultType == DefaultType.EXPRESSION) {
				view.appendOutput("DEFAULT EXPRESSION: " + file.defaultExpression(commandIndex, keywordIndex));
			}
		}

	}

	private void showCodewordKeyword(int commandIndex, int keywordIndex) {
		view.appendOutput("TYPE: CODEWORD");

		ArrayList<String> allowedVals = file.allowedValues(commandIndex, keywordIndex);
		view.appendOutput("NUMBER OF CHOICES: " + allowedVals.size());
		view.appendOutput("CHOICES:");
		for (int i = 0; i < allowedVals.size(); i++) {
			view.appendOutput((i + 1) + ": " + allowedVals.get(i));
		}

		DefaultType defaultType = file.defaultType(commandIndex, keywordIndex);
		int defaultValueInteger = file.defaultValueInt(commandIndex, keywordIndex);
		view.appendOutput("DEFAULT TYPE: " + defaultType);
		if (defaultType == DefaultType.VALUE) {
			view.appendOutput("DEFAULT VALUE: " + file.symbol(defaultValueInteger));
		} else if (defaultType == DefaultType.EXPRESSION) {
			view.appendOutput("DEFAULT EXPRESSION: " + file.defaultExpression(commandIndex, keywordIndex));
		}
	}

}
