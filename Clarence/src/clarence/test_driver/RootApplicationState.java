package clarence.test_driver;

import clarence.key_file.KeywordFile;

public class RootApplicationState implements ApplicationState {

	private KeywordFile file;
	private KeyFileEditorView view;

	public RootApplicationState(KeyFileEditorView view, KeywordFile file) {
		this.view = view;
		this.file = file;
		view.setCanSave(true);
		view.setCanExecute(true);
		showMessage();
	}

	private void showMessage() {
		view.clearPrompt();
		//view.appendPrompt("");
		view.appendPrompt("SELECT AN OPTION" );
		view.appendPrompt("1. PRINT LENGTH DATA SUMMARY" );
		view.appendPrompt("2. PRINT SYMBOL TABLE SUMMARY" );
		view.appendPrompt("3. PRINT KEYWORD TABLE SUMMARY" );
		view.appendPrompt("4. PRINT COMMAND TABLE SUMMARY" );
		view.appendPrompt("5. PRINT DEFAULT TABLE SUMMARY" );
		view.appendPrompt("6. PRINT EXPRESSION TABLE SUMMARY" );
		view.appendPrompt("7. SELECT EXPRESSION" );
		view.appendPrompt("8. SELECT COMMAND" );
		view.appendPrompt("9. CREATE COMMAND");
	}

	@Override
	public void command(String command) throws InvalidCommandException {
		try {
			int option = Integer.parseInt(command);
			if (option == 1) {
				view.clearOutput();
				view.appendOutput(file.lengthDataInfo());
				view.setApplicationState(new RootApplicationState(view, file));
			} else if (option == 2) {
				view.clearOutput();
				view.appendOutput(file.symbolTableSummary());
				view.setApplicationState(new RootApplicationState(view, file));
			} else if (option == 3) {
				view.clearOutput();
				view.appendOutput(file.keywordTableSummary());
				view.setApplicationState(new RootApplicationState(view, file));
			} else if (option == 4) {
				view.clearOutput();
				view.appendOutput(file.commandTableSummary());
				view.setApplicationState(new RootApplicationState(view, file));
			} else if (option == 5) {
				view.clearOutput();
				view.appendOutput(file.defaultTableSummary());
				view.setApplicationState(new RootApplicationState(view, file));
			} else if (option == 6) {
				view.clearOutput();
				view.appendOutput(file.expressionTableSummary());
			} else if (option == 7) {
				view.setApplicationState(new ExpressionSelectionTypeState(view,file));
			} else if (option == 8) {
				view.setApplicationState(new SelectCommandState(view,file));
			}else if (option == 9) {
				view.setApplicationState(new CreateCommandState(view,file));
			}else {
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
