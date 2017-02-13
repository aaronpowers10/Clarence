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

import java.nio.ByteBuffer;

public class LengthData {
	
	private int units;
	private int symbolTableLength;
	private int maxSymbols;
	private int keywordEnd;
	private int keywordStart;
	private int commandTableLength;
	private int defaultStart;
	private int defaultEnd;
	private int expressionStart;
	private int expressionEnd;
	private int totalSize;
	
	public LengthData(ByteBuffer buffer) {
		read(buffer);
	}

	private void read(ByteBuffer buffer) {
		buffer.rewind();
		units = buffer.getInt();
		symbolTableLength = buffer.getInt();
		maxSymbols = buffer.getInt();
		keywordEnd = buffer.getInt();
		keywordStart = buffer.getInt();
		commandTableLength = buffer.getInt();
		defaultStart = buffer.getInt();
		defaultEnd = buffer.getInt();
		expressionStart = buffer.getInt();
		expressionEnd = buffer.getInt();
		totalSize = buffer.getInt();
	}
	
	public int units(){
		return units;
	}
	
	public int maxSymbols(){
		return maxSymbols;
	}
	
	public int totalSize(){
		return totalSize;
	}
	
	public int keywordTableLength(){
		return keywordEnd - keywordStart + 1;
	}
	
	public int defaultTableLength(){
		return defaultEnd - defaultStart + 1;
	}
	
	public int expressionTableLength(){
		return expressionEnd - expressionStart + 1;
	}
	
	public int symbolTableLength(){
		return symbolTableLength;
	}
	
	public int commandTableLength(){
		return commandTableLength;
	}
	
	public int keywordStart(){
		return keywordStart;
	}

}
