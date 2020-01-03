package clarence.test_driver;

import clarence.key_file.KeywordFile;

public class AddKeywordOptionsState implements ApplicationState{
	
	private KeywordFile file;
	private KeyFileEditorView view;
	private int commandIndex;
	
	public AddKeywordOptionsState(KeyFileEditorView view, KeywordFile file, int commandIndex) {
		this.view = view;
		this.file = file;
		this.commandIndex = commandIndex;
		showMessage();
	}
	
	private void showMessage() {
		view.clearPrompt();
		//view.appendPrompt("");
		view.appendPrompt("SELECT KEYWORD TYPE");
		view.appendPrompt("1: INTEGER");
		view.appendPrompt("2: REAL");
		view.appendPrompt("3: CODEWORD");
		view.appendPrompt("4: STRING");
		view.appendPrompt("5: OBJECT");
		view.appendPrompt("6: BACK");
	}

	@Override
	public void command(String command) throws InvalidCommandException {
		try {
			int option = Integer.parseInt(command);
			if(option == 1) {
				view.setApplicationState(new NewIntegerKeywordState(view,file,commandIndex));
			} else if (option == 2) {
				view.setApplicationState(new NewRealKeywordState(view,file,commandIndex));
			} else if (option == 3) {
				view.setApplicationState(new NewCodewordKeywordState(view,file,commandIndex));
			}else if (option == 4) {
				view.setApplicationState(new NewStringKeywordState(view,file,commandIndex));
			} else if (option == 5) {
				view.setApplicationState(new NewObjectKeywordState(view,file,commandIndex));
			}  else if (option == 6) {
				view.setApplicationState(new CommandOptionsState(view,file,commandIndex));
			} else {
				throw new InvalidCommandException();
			}
		} catch(NumberFormatException e) {
			throw new InvalidCommandException();
		}
		
	}

	@Override
	public void reload() {
		showMessage();
		
	}

}
