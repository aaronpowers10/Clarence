package clarence.key_expression;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class ReservedWordNode implements ExpressionNode{
	
	private float value;
	private int i1;
	private int i2;
	private ArrayList<Byte> remBytes;
	private boolean lastNotInt;
	
	public ReservedWordNode() {
		remBytes = new ArrayList<Byte>();
		lastNotInt = false;
	}
	
	public ResWords word() {
		if((int)value == 3 ) {
			return ResWords.REQUIRED;
		}else if((int)value == 5 ) {
			return ResWords.UNUSED;
		}else if((int)value == 6 ) {
			return ResWords.UNUSED;
		} else if((int)value == 7 ) {
			return ResWords.NODEF;
		}else if((int)value == 8 ) {
			return ResWords.UNFILD;
		}else if((int)value == 9 ) {
			return ResWords.UNFILLED;
		} else {
			return ResWords.UNKNOWN;
		}
	}

	@Override
	public String printString() {
		if(word() == ResWords.UNKNOWN) {
			return "Unknown Res Word: " + (int)value;
		} else {
			return word().toString();
		}
	}

	@Override
	public void read(ByteBuffer buffer,SyntaxTree tree) {
		value = buffer.getFloat();
		i1 = buffer.getInt();
		try {
		i2 = buffer.getInt();
		} catch (BufferUnderflowException e) {
			lastNotInt = true;
			while (buffer.hasRemaining()) {
				remBytes.add(buffer.get());
			}
		}
		
	}
	
	@Override
	public void write(ByteBuffer buffer) {
		buffer.put(((byte) 4));
		buffer.putFloat(value);
		buffer.putInt(i1);
		if (lastNotInt) {
			for (Byte b : remBytes) {
				buffer.put(b);
			}
		} else {
			buffer.putInt(i2);
		}
	}
	
	@Override
	public int byteSize() {
		if (lastNotInt) {
			return 1 + 4 * 2 + remBytes.size();
		} else {
			return 1 + 4 * 3;
		}
	}

}
