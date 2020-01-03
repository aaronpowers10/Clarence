package clarence.key_reader;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class BinaryTools {
	
	public static String bitsToDec(int i1, int i2, int i3, int i4) {
		return Double.toString(Math.pow(2, 0)*i1 + Math.pow(2, 1)*i2 + Math.pow(2, 2)*i3 + Math.pow(2, 3)*i4);
	}
	
	public static float bytesToFloat(int i1, int i2, int i3, int i4) {
		byte[] bytes = new byte[4];
		bytes[0] = (byte)i1;
		bytes[1] = (byte)i2;
		bytes[2] = (byte)i3;
		bytes[3] = (byte)i4;
		//System.out.println("B0 " +bytes[0]);
		ByteBuffer buffer = ByteBuffer.allocate(4);
		buffer.order(ByteOrder.LITTLE_ENDIAN);
		buffer.put(bytes);
		buffer.rewind();
		return buffer.getFloat();
	}
	
	public static int bytesToInt(int i1, int i2, int i3, int i4) {
		byte[] bytes = new byte[4];
		bytes[0] = (byte)i1;
		bytes[1] = (byte)i2;
		bytes[2] = (byte)i3;
		bytes[3] = (byte)i4;
		//System.out.println("B0 " +bytes[0]);
		ByteBuffer buffer = ByteBuffer.allocate(4);
		//buffer.order(ByteOrder.LITTLE_ENDIAN);
		buffer.put(bytes);
		buffer.rewind();
		return buffer.getInt();
	}
	
	public static byte[] packString(String s, int size) {
		byte[] sBytes = s.getBytes();
		byte[] result = new byte[size];
		
		for(int i=0;i<result.length;i++) {
			result[i] = 32;
		}
		
		for(int i=0;i<sBytes.length;i++) {
			if(i < size) {
				result[i] = sBytes[i];
			}
		}
		return result;
	}
	
	public static int bytesToInt(byte[] bytes) {
		ByteBuffer buffer = ByteBuffer.allocate(4);
		buffer.order(ByteOrder.LITTLE_ENDIAN);
		for(int i=0;i<4;i++) {
			buffer.put(bytes[i]);
			//System.out.println("BYTE I: " + i + "  " + bytes[i]);
		}
		buffer.rewind();
		return buffer.getInt();
	}
	
	public static void main(String[] args) {
		System.out.println("BINARY TEST");
		float f = bytesToFloat(0,-32,-117,68);
		System.out.println(f);
		float f2  = 105.0f;
		ByteBuffer b = ByteBuffer.allocate(4);
		b.order(ByteOrder.LITTLE_ENDIAN);
		b.putFloat(f2);
		b.rewind();
		for(int i=0;i<4;i++) {
			System.out.println(b.get());
		}
		//System.out.println(Float.floatToRawIntBits(f2));
	}

}
