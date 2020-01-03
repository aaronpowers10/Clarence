package clarence.test_driver;

import clarence.key_file.KeywordFile;

public class SelectCommandState implements ApplicationState {

	private KeywordFile file;
	private KeyFileEditorView view;

	public SelectCommandState(KeyFileEditorView view, KeywordFile file) {
		this.file = file;
		this.view = view;
		showMessage();
	}

	private void showMessage() {
		view.clearPrompt();
		//view.appendPrompt("");
		view.appendPrompt("ENTER COMMAND NUMBER OR NAME");
	}

	@Override
	public void command(String command) throws InvalidCommandException {
		int comInd = file.indexOfCommand(command);
		if (comInd == -1) {

			try {
				int option = Integer.parseInt(command);
				try {
					file.getCommand(option - 1);
				} catch (IndexOutOfBoundsException e) {
					throw new InvalidCommandException();
				}
				view.setApplicationState(new CommandOptionsState(view, file, option - 1));
			} catch (NumberFormatException e) {
				throw new InvalidCommandException();
			}
		} else {
			view.setApplicationState(new CommandOptionsState(view, file, comInd));
		}
	}

	@Override
	public void reload() {
		showMessage();

	}

}
