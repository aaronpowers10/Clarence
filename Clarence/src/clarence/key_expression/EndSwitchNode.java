package clarence.key_expression;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class EndSwitchNode implements ExpressionNode{
	
	private float value;
	private int i1;
	private int i2;
	private ArrayList<Byte> remBytes;
	private boolean lastNotInt;
	
	public EndSwitchNode() {
		remBytes = new ArrayList<Byte>();
		lastNotInt = false;
	}

	@Override
	public String printString() {
		return "EndSwitch";
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
		buffer.put(((byte) 14));
		buffer.putFloat(value);
		buffer.putInt(i1);
		if (lastNotInt) {
			for(Byte b: remBytes) {
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
