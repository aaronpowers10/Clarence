package clarence.test_driver;

import javafx.scene.control.TextArea;

public class OutputView extends TextArea {

	private int maxOutput;
	private int outputSize;

	public OutputView() {
		super();
		maxOutput = 200000;
		outputSize = 0;
	}

	@Override
	public void appendText(String text) {
		super.appendText(text);
//		outputSize += text.length();// - countLines(text);
//		if (outputSize > maxOutput) {
//			this.deleteText(0, outputSize - maxOutput);
//			outputSize = maxOutput;
//		}
//		setScrollTop(Double.MAX_VALUE);

		
	}

}
