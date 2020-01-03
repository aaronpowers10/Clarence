package clarence.test_driver;

import java.util.ArrayList;
import java.util.Scanner;

import clarence.key_file.KeywordFile;
import clarence.key_reader.CodewordDefaultCreator;
import clarence.key_reader.CommandEntry;
import clarence.key_reader.KeywordEntry;

public class NewCodewordKeywordState implements ApplicationState {

	private KeywordFile file;
	private KeyFileEditorView view;
	private int commandIndex;

	public NewCodewordKeywordState(KeyFileEditorView view, KeywordFile file, int commandIndex) {
		this.view = view;
		this.file = file;
		this.commandIndex = commandIndex;
		showMessage();
	}

	private void showMessage() {
		view.clearPrompt();
		//view.appendPrompt("");
		view.appendPrompt("ENTER <NAME> <ABBREVIATION> <DEFAULT> <ALLOWED-VALUES>... FOR CODEWORD KEYWORD");
	}

	@Override
	public void command(String commandStr) throws InvalidCommandException {
		Scanner in = new Scanner(commandStr);
		try {
			
			String name = in.next();
			String abbr = in.next();
			String def = in.next();
			ArrayList<String> allowedVals = new ArrayList<String>();
			allowedVals.add(in.next());
			while(in.hasNext()) {
				allowedVals.add(in.next());
			}
			int defInd = file.numSymbols() + 1 +allowedVals.indexOf(def);
					
			CommandEntry command = file.getCommand(commandIndex);
			KeywordEntry lastKey = file.getKeyword(commandIndex, command.endKeys() - command.startKeys() - 1);
			int valPos = lastKey.valPos() + lastKey.length();
			int type = file.getUniqueSymbolType();
			file.addSymbols(allowedVals,type); 
			
			KeywordEntry keyword = new KeywordEntry(name, abbr, valPos,type);
			CodewordDefaultCreator defaultCreator = new CodewordDefaultCreator(defInd);
			file.addKeyword(command, keyword, defaultCreator);
			in.close();
			view.setApplicationState(new CommandOptionsState(view,file,commandIndex));
		} catch (NumberFormatException e) {
			in.close();
			throw new InvalidCommandException();
		}
	}

	@Override
	public void reload() {
		showMessage();

	}

}
