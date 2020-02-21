package clarence.test_driver;

import java.util.ArrayList;

import clarence.key_file.KeywordFile;
import clarence.key_reader.CommandEntry;

public class CommandOptionsState implements ApplicationState{
	
	private int commandIndex;
	private KeywordFile file;
	private KeyFileEditorView view;
	
	public CommandOptionsState(KeyFileEditorView view, KeywordFile file, int commandIndex) {
		this.view = view;
		this.file = file;
		this.commandIndex = commandIndex;
		showMessage();		
	}
	
	private void showMessage() {
		view.clearPrompt();
		CommandEntry command = file.getCommand(commandIndex);
		view.appendPrompt(("SELECT OPTION FOR COMMAND " + command.name() ));
		view.appendPrompt(("1. PRINT COMMAND SUMMARY") );
		view.appendPrompt("2. LIST KEYWORDS" );
		view.appendPrompt("3. VIEW KEYWORD FORMATTED" );
		view.appendPrompt("4. VIEW KEYWORD RAW" );
		view.appendPrompt("5. ADD KEYWORD" );
		view.appendPrompt("6. EDIT KEYWORD" );
		view.appendPrompt("7. BACK" );
	}

	@Override
	public void command(String command) throws InvalidCommandException{
		try {
			int option = Integer.parseInt(command);
			if (option == 1) {
				view.clearOutput();
				view.appendOutput(file.commandSummary(commandIndex));
				reload();
			} else if (option == 2) {
				view.clearOutput();
				view.appendOutput("KEYWORD NAMES" );
				ArrayList<String> names = file.keywordNames(commandIndex);
				for (int i = 0; i < names.size(); i++) {
					view.appendOutput(((i + 1) + ": " + names.get(i) ));
				}
				reload();
			} else if (option == 3) {
				view.setApplicationState(new KeywordFormattedState(view,file,commandIndex));
			} else if (option == 4) {
				view.setApplicationState(new KeywordRawState(view,file,commandIndex));
			} else if (option == 5) {
				view.setApplicationState(new AddKeywordOptionsState(view,file,commandIndex));
			} else if (option == 6) {
				
			} else if (option == 7) {
				view.setApplicationState(new RootApplicationState(view,file));
			} else {
				throw new InvalidCommandException();
			}
		} catch (NumberFormatException e) {
			throw new InvalidCommandException();
		}
		
	}

	@Override
	public void reload() {
		showMessage();		
	}

}
