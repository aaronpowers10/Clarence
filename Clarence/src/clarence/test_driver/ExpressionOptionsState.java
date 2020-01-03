package clarence.test_driver;

import clarence.key_file.KeywordFile;
import clarence.key_reader.ExpressionEntry;

public class ExpressionOptionsState implements ApplicationState{
	
	private KeywordFile file;
	private KeyFileEditorView view;
	private ExpressionEntry expr;
	
	public ExpressionOptionsState(KeyFileEditorView view, KeywordFile file, ExpressionEntry expr) {
		this.file = file;
		this.view = view;
		this.expr = expr;
		showMessage();
	}
	
	private void showMessage() {
		view.clearPrompt();
		//view.appendPrompt("");
		view.appendPrompt("SELECT AN OPTION FOR EXPRESSION");
		view.appendPrompt("1. VIEW RAW EXPRESSION SUMMARY");
		view.appendPrompt("2. VIEW PROCESSED SYNTAX TREE");
		view.appendPrompt("3. BACK");
	}

	@Override
	public void command(String command) throws InvalidCommandException {
		try {
			int option = Integer.parseInt(command);
			if(option == 1) {
				view.clearOutput();
				//view.appendOutput("");
				view.appendOutput(expr.summary());
				reload();
			} else if (option == 2) {
				view.clearOutput();
				//view.appendOutput("");
				view.appendOutput(expr.summaryProcessed());
				reload();
			} else if (option == 3) {
				view.setApplicationState(new RootApplicationState(view,file));
			} else {
				throw new InvalidCommandException();
			}
		} catch (NumberFormatException e ) {
			throw new InvalidCommandException();
		}
		
	}

	@Override
	public void reload() {
		showMessage();
		
	}

}
