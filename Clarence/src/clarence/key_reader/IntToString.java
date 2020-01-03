package clarence.key_reader;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class IntToString {
	public static String convert(int integer){
		ByteBuffer buffer = ByteBuffer.allocate(4);
		buffer.order(ByteOrder.LITTLE_ENDIAN);
		buffer.putInt(integer);
		buffer.rewind();
		
		try {
			return new String(buffer.array(), "ASCII").trim();
		} catch (UnsupportedEncodingException e) {
			return "*!*!*!*!*!*!*";
		}
	}
}
