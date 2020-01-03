package clarence.test_driver;

import clarence.key_file.KeywordFile;
import clarence.key_reader.IntToFloat;

public class KeywordRawState implements ApplicationState{
	
	private int commandIndex;
	private KeywordFile file;
	private KeyFileEditorView view;
	
	public KeywordRawState(KeyFileEditorView view, KeywordFile file, int commandIndex) {
		this.view = view;
		this.file = file;
		this.commandIndex = commandIndex;
		showMessage();
	}
	
	private void showMessage() {
		view.clearPrompt();
		//view.appendPrompt("");
		view.appendPrompt("ENTER KEYWORD NUMBER OR NAME");
	}

	@Override
	public void command(String command) throws InvalidCommandException {
		try {
			int option = Integer.parseInt(command);
			view.clearOutput();
			//view.appendOutput("");
			view.appendOutput(file.getKeyword(commandIndex,option-1).getRawInfo());
			int defaultValueInteger = file.defaultValueInt(commandIndex, option-1);
			int defaultTypeInteger = file.defaultTypeInt(commandIndex,option-1);
			view.appendOutput("DEFAULT TYPE: "  + defaultTypeInteger);
			view.appendOutput("DEFAULT VALUE INT: " + defaultValueInteger);
			System.out.println("DEFAULT VALUE FLOAT: " + IntToFloat.convert(defaultValueInteger));
			view.setApplicationState(new CommandOptionsState(view,file,commandIndex));
		} catch (NumberFormatException e) {
			throw new InvalidCommandException();
		}
		
	}

	@Override
	public void reload() {
		showMessage();		
	}
	

}
