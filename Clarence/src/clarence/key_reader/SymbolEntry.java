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
		buffer.getInt();
		buffer.getInt();
		value = buffer.getInt();
		buffer.getInt();
		buffer.getInt();
		buffer.getInt();
		buffer.getInt();
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
