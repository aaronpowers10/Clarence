package clarence.key_expression;

import java.nio.ByteBuffer;

public class ThenNode implements ExpressionNode {

	private float value;
	private int i1;
	private int i2;

	public ThenNode() {

	}

	@Override
	public String printString() {
		return "Then";
	}

	@Override
	public void read(ByteBuffer buffer, SyntaxTree tree) {
		value = buffer.getFloat();
		i1 = buffer.getInt();
		i2 = buffer.getInt();

	}

	@Override
	public void write(ByteBuffer buffer) {
		buffer.put(((byte) 8));
		buffer.putFloat(value);
		buffer.putInt(i1);
		buffer.putInt(i2);

	}

	@Override
	public int byteSize() {
		return 1 + 4 * 3;
	}

}
