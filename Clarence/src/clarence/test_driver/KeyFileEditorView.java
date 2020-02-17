package clarence.test_driver;

import java.io.File;
import java.io.IOException;

import clarence.key_file.KeywordFile;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.ToolBar;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class KeyFileEditorView extends Application {
	private VBox root;
	private Button loadButton;
	private Button saveButton;
	private Button commandButton;
	private Stage primaryStage;
	private OutputView output;
	private OutputView prompt;
	private TextField commandText;

	private KeywordFile file;
	private ApplicationState state;

	public void start(Stage primaryStage) {

		this.primaryStage = primaryStage;
		Platform.runLater(new Runnable() {
			public void run() {
				primaryStage.setTitle("BDLKEY Edit");

				ToolBar toolbar = new ToolBar();
				loadButton = new Button("LOAD");
				saveButton = new Button("SAVE");
				toolbar.getItems().addAll(loadButton, saveButton);
				
				prompt = new OutputView();
				prompt.setEditable(false);
				appendPrompt("Please load BDLKEY.bin file");
				prompt.setPrefHeight(250);
				prompt.setPrefWidth(2000);

				output = new OutputView();
				output.setEditable(false);
				//appendOutput("Please load BDLKEY.bin file");
				output.setPrefHeight(350);
				output.setPrefWidth(2000);
				//output.setTextFormatter(new TextFormatter<String>(change -> 
	            //change.getControlNewText().length() <= 10 ? change : null));

				HBox commandBox = new HBox();
				commandBox.setSpacing(10);
				commandText = new TextField();
				commandText.setPrefWidth(500);
				commandButton = new Button("Execute");

				commandText.setOnKeyPressed(new EventHandler<KeyEvent>() {
					@Override
					public void handle(KeyEvent event) {
						if (event.getCode() == KeyCode.ENTER) {
							execute();
						}
					}
				});

				commandBox.getChildren().addAll(commandText, commandButton);

				commandBox.setPadding(new Insets(0, 0, 10, 10));

				HBox clBox = new HBox();
				clBox.getChildren().addAll(new Label("Command:"));
				clBox.setPadding(new Insets(10, 0, 0, 10));
				
				HBox outBox = new HBox();
				outBox.getChildren().addAll(new Label("Output:"));
				outBox.setPadding(new Insets(10, 0, 0, 10));

				root = new VBox();
				// root.setSpacing(3);

				root.getChildren().addAll(toolbar, prompt,clBox,commandBox,outBox,output);

				loadButton.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent e) {
						load();
					}
				});
				saveButton.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent e) {
						save();
					}
				});
				commandButton.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent e) {
						execute();
					}
				});
				
				state = new NotLoadedState();
				setCanSave(false);
				setCanExecute(false);

				primaryStage.setScene(new Scene(root, 800, 600));
				primaryStage.show();
			}
		});

	}

	public void setApplicationState(ApplicationState state) {
		this.state = state;
	}

	private void load() {
		try {
			
			FileChooser fileChooser = new FileChooser();

			fileChooser.getExtensionFilters().addAll(
			     new FileChooser.ExtensionFilter("BIN File", "*.BIN")
			);
			
			File keyFil = fileChooser.showOpenDialog(primaryStage);
			file = new KeywordFile(keyFil.getAbsolutePath());
			state = new RootApplicationState(this, file);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void save() {
		try {
			FileChooser fileChooser = new FileChooser();

			fileChooser.getExtensionFilters().addAll(
			     new FileChooser.ExtensionFilter("BIN File", "*.BIN")
			);
			
			File keyFil = fileChooser.showSaveDialog(primaryStage);
			file = new KeywordFile(keyFil.getAbsolutePath());
			file.write(keyFil.getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void execute() {
		String command = commandText.getText();
		commandText.setText("");
		try {
			state.command(command);
		} catch (InvalidCommandException e) {
			appendOutput("INVALID COMMAND" + System.lineSeparator());
			state.reload();
		}

	}

	public void appendOutput(String msg) {
		output.appendText(msg + "\n");
	}
	
	public void clearOutput() {
		output.clear();
	}
	
	public void appendPrompt(String msg) {
		prompt.appendText(msg + "\n");
	}
	
	public void clearPrompt() {
		prompt.clear();
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	public void setCanSave(boolean canSave) {
		if(canSave) {
			saveButton.setDisable(false);
		} else {
			saveButton.setDisable(true);
		}
	}
	
	public void setCanExecute(boolean canExecute) {
		if(canExecute) {
			commandText.setDisable(false);
			commandButton.setDisable(false);
		} else {
			commandText.setDisable(true);
			commandButton.setDisable(true);
		}
	}

}
