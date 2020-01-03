package clarence.key_reader;

import java.nio.ByteBuffer;

public class DoubleToInt {
	
	public static int convert(double value){
		ByteBuffer buffer = ByteBuffer.allocate(4);
		buffer.putFloat((float)value); 
		buffer.rewind();
		return (int)buffer.getInt();
	}

}
