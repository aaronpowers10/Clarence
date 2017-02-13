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
import java.util.Arrays;

public class ExpressionEntry {
	
	private int index;
	private String code;
	private int numChars;
	private int entryLength;
	
	public ExpressionEntry(ByteBuffer buffer, int index){
		this.index = index;
		code = "";
		read(buffer);
	}
	
	private void read(ByteBuffer buffer){
		buffer.getInt();
		entryLength = (int)Math.ceil(buffer.getInt()/4.0);		
		buffer.getInt();
		buffer.getInt();
		numChars = buffer.getInt();
		buffer.getInt();
		
		int numInts = (int)Math.ceil(numChars / 4.0);
		
		ByteBuffer codeBuffer = ByteBuffer.allocate(4 * (numChars+1));
		codeBuffer.order(ByteOrder.LITTLE_ENDIAN);
		for (int i = 0; i < numInts; i++) {
			codeBuffer.putInt(buffer.getInt());
		}
		
		try {
			code = new String(Arrays.copyOfRange(codeBuffer.array(),0,numChars), "ASCII") ;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		for(int i=numInts+6;i<entryLength;i++){
			buffer.getInt();
		}
		
	}
	
	public int entryLength(){
		return entryLength;
	}
	
	public int index(){
		return index;
	}
	
	public String code(){
		return code;
	}

}
