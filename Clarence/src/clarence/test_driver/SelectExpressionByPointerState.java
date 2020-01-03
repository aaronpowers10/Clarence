package clarence.test_driver;

import clarence.key_file.KeywordFile;

public class SelectExpressionByPointerState implements ApplicationState{
	
	private KeyFileEditorView view;
	private KeywordFile file;
	
	public SelectExpressionByPointerState(KeyFileEditorView view, KeywordFile file) {
		this.view = view;
		this.file = file;
		showMessage();
	}
	
	private void showMessage() {
		view.clearPrompt();
		//view.appendPrompt("");
		view.appendPrompt("ENTER EXPRESSION POINTER");
	}

	@Override
	public void command(String command) throws InvalidCommandException {
		try {
			int option = Integer.parseInt(command);
			view.setApplicationState(new ExpressionOptionsState(view,file,file.getExpressionByPointer(option)));
		} catch (NumberFormatException e) {
			throw new InvalidCommandException();
		}
		
	}

	@Override
	public void reload() {
		showMessage();
		
	}

}
