package clarence.test_driver;

import java.util.Scanner;

import clarence.key_file.KeywordFile;
import clarence.key_reader.CommandEntry;
import clarence.key_reader.KeywordEntry;
import clarence.key_reader.KeywordEntryFactory;
import clarence.key_reader.ObjectDefaultCreator;

public class NewObjectKeywordState implements ApplicationState {

	private KeywordFile file;
	private KeyFileEditorView view;
	private int commandIndex;

	public NewObjectKeywordState(KeyFileEditorView view, KeywordFile file, int commandIndex) {
		this.view = view;
		this.file = file;
		this.commandIndex = commandIndex;
		showMessage();
	}

	private void showMessage() {
		view.clearPrompt();
		//view.appendPrompt("");
		view.appendPrompt("ENTER <NAME> <ABBREVIATION> <ALLOWED-COMMAND> FOR OBJECT KEYWORD");
	}

	@Override
	public void command(String commandStr) throws InvalidCommandException {
		Scanner in = new Scanner(commandStr);
		try {
			
			String name = in.next();
			String abbr = in.next();
			String allowedCom = in.next();
			int comInd = file.indexOfCommand(allowedCom);
			if(in.hasNext()) {
				in.close();
				throw new InvalidCommandException();
			}
			CommandEntry command = file.getCommand(commandIndex);
			KeywordEntry lastKey = file.getKeyword(commandIndex, command.endKeys() - command.startKeys() - 1);
			int valPos = lastKey.valPos() + lastKey.length();
			KeywordEntry keyword = KeywordEntryFactory.createObject(name, abbr, 1, comInd, valPos);
			ObjectDefaultCreator defaultCreator = new ObjectDefaultCreator();
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