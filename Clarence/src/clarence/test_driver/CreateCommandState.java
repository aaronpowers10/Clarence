package clarence.test_driver;

import java.util.ArrayList;
import java.util.Scanner;

import clarence.key_file.KeywordFile;
import clarence.key_reader.CodewordDefaultCreator;
import clarence.key_reader.CommandEntry;
import clarence.key_reader.KeywordEntry;

public class CreateCommandState implements ApplicationState{
	private KeywordFile file;
	private KeyFileEditorView view;
	
	public CreateCommandState(KeyFileEditorView view, KeywordFile file) {
		this.view = view;
		this.file = file;
		showMessage();		
	}
	
	private void showMessage() {
		view.clearPrompt();
		view.appendPrompt("ENTER <NAME> <ABBREVIATION> <TYPE-SYMB> FOR COMMAND");
	}

	@Override
	public void command(String commandStr) throws InvalidCommandException {
			Scanner in = new Scanner(commandStr);
			
			String name = in.next();
			String abbr = in.next();
			int typeSym = in.nextInt();
			
			CommandEntry lastCommand = file.getCommand(file.numCommands()-1);
			
			CommandEntry newCommand = new CommandEntry(name,abbr,typeSym,lastCommand,lastCommand,file.lengthData());
			file.addCommand(newCommand);
			
			in.close();
			view.setApplicationState(new RootApplicationState(view,file));
		
	}

	@Override
	public void reload() {
		showMessage();
		
	}

}
