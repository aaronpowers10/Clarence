/*
 *
 *  Copyright (C) 2017 Aaron Powers
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
package clarence.key_reader;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class SymbolEntry {
	
	private String symbol;
	private int type;
	private int value;
	private int i1,i2,i3,i4,i5,i6;
	
	public SymbolEntry(String symbol, int type, int value) {
		this.symbol = symbol;
		this.type = type;
		this.value = value;
		i1 = -1;
		i2 = -1;
	}
	
	public SymbolEntry(ByteBuffer buffer){
		read(buffer);
	}
	
	private void read(ByteBuffer buffer){
		ByteBuffer symbolBuffer = ByteBuffer.allocate(8*4);
		symbolBuffer.order(ByteOrder.LITTLE_ENDIAN);
		for (int i = 0; i < 8; i++) {
			symbolBuffer.putInt(buffer.getInt());
		}
		
		try {
			symbol = new String(symbolBuffer.array(), "ASCII").trim();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		type = buffer.getInt();		
		i1 = buffer.getInt();
		i2 = buffer.getInt();
		value = buffer.getInt();
		i3 = buffer.getInt();
		i4 = buffer.getInt();
		i5 = buffer.getInt();
		i6 = buffer.getInt();
	}
	
	public void write(ByteBuffer buffer) {
		buffer.put(BinaryTools.packString(symbol,32));
		buffer.putInt(type);		
		buffer.putInt(i1);
		buffer.putInt(i2);
		buffer.putInt(value);
		buffer.putInt(i3);
		buffer.putInt(i4);
		buffer.putInt(i5);
		buffer.putInt(i6);
	}
	
	public int byteSize() {
		return 32 + 8*4;
	}
	
	public String symbol(){
		return symbol;
	}
	
	public int type(){
		return type;
	}
	
	public int value(){
		return value;
	}

}
