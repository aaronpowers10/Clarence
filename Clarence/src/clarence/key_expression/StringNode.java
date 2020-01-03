package clarence.key_expression;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class StringNode implements ExpressionNode {
	private String string;
	private int i1;
	private int i2;
	private int numChars;
	private byte[] value;

	public StringNode() {

	}

	@Override
	public String printString() {
		return string;
	}

	@Override
	public void read(ByteBuffer buffer, SyntaxTree tree) {
		value = new byte[4];
		for (int i = 0; i < 4; i++) {
			value[i] = buffer.get();
		}
		i1 = buffer.getInt();
		i2 = buffer.getInt();
		numChars = buffer.getInt();
		ByteBuffer strBuf = ByteBuffer.allocate(numChars);
		for (int i = 0; i < numChars; i++) {
			strBuf.put(buffer.get());
		}
		strBuf.rewind();
		try {
			string = new String(strBuf.array(), "ASCII");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void write(ByteBuffer buffer) {
		buffer.put((byte) 2);
		buffer.put(value);
		buffer.putInt(i1);
		buffer.putInt(i2);
		buffer.putInt(numChars);
		buffer.put(string.getBytes());

	}

	@Override
	public int byteSize() {

		return 1 + 4 * 4 + string.length();
	}
	
	public int numChars() {
		return numChars;
	}

	public String string() {
		return string;
	}
}
