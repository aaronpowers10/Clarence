package clarence.test_driver;

import clarence.key_file.KeywordFile;

public class ExpressionSelectionTypeState implements ApplicationState{
	
	private KeywordFile file;
	private KeyFileEditorView view;
	
	public ExpressionSelectionTypeState(KeyFileEditorView view, KeywordFile file) {
		this.view = view;
		this.file = file;
		showMessage();
	}
	
	private void showMessage() {
		view.clearPrompt();
		//view.appendPrompt("");
		view.appendPrompt("SELECT EXPRESSION BY INDEX OR MEMORY POINTER?");
		view.appendPrompt("1: BY INDEX");
		view.appendPrompt("2: BY MEMORY POINTER");
		view.appendPrompt("3: BACK");
	}

	@Override
	public void command(String command)throws InvalidCommandException {
		try {
			int option = Integer.parseInt(command);
			if(option == 1) {
				view.setApplicationState(new SelectExpressionByIndexState(view,file));
			} else if (option == 2) {
				view.setApplicationState(new SelectExpressionByPointerState(view,file));
			} else if (option == 3){
				view.setApplicationState(new RootApplicationState(view,file));
			}else {
				
			}
		}catch(NumberFormatException e) {
			throw new InvalidCommandException();
		}
		
	}

	@Override
	public void reload() {
		showMessage();
		
	}


}
