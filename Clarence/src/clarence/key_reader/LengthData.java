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
		System.out.println("KEY END " + keywordEnd);
		commandTableLength = buffer.getInt();
		defaultStart = buffer.getInt();
		defaultEnd = buffer.getInt();
		expressionStart = buffer.getInt();
		expressionEnd = buffer.getInt();
		totalSize = buffer.getInt();
	}
	
	public void write(ByteBuffer buffer) {
		buffer.putInt(units);
		buffer.putInt(symbolTableLength);
		buffer.putInt(maxSymbols);
		buffer.putInt(keywordEnd);
		buffer.putInt(keywordStart);
		buffer.putInt(commandTableLength);
		buffer.putInt(defaultStart);
		buffer.putInt(defaultEnd);
		buffer.putInt(expressionStart);
		buffer.putInt(expressionEnd);
		buffer.putInt(totalSize);
		
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
	
	public int defaultEnd() {
		return defaultEnd;
	}
	
	public int expressionStart(){
		return expressionStart;
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
	
	public int defaultStart(){
		return defaultStart;
	}
	
	public void setSymbolTableLength(int length) {
		this.symbolTableLength = length;
	}
	
	public void setKeywordStart(int start) {
		this.keywordStart = start;
	}
	
	public void setKeywordEnd(int end) {
		this.keywordEnd = end;
	}
	
	public void setCommandTableLength(int length) {
		this.commandTableLength = length;
	}
	
	public void setDefaultStart(int start) {
		this.defaultStart = start;
	}
	
	public void setDefaultEnd(int end) {
		this.defaultEnd = end;
	}
	
	public void setExpressionStart(int start) {
		this.expressionStart = start;
	}
	
	public void setExpressionEnd(int end) {
		this.expressionEnd = end;
	}
	
	public void setTotalSize(int size) {
		this.totalSize = size;
	}
	
	public void setMaxSymbols(int maxSymbols) {
		this.maxSymbols = maxSymbols;
	}
	
	public String getInfo() {
		String info = "LENGTH DATA:" + System.lineSeparator();
		info = info + "UNITS: " + units + System.lineSeparator();
		info = info + "SYMBOL TABLE LENGTH: " + symbolTableLength + System.lineSeparator();
		info = info + "MAX SYMBOLS: " + maxSymbols + System.lineSeparator();
		info = info + "KEYWORD START: " + keywordStart + System.lineSeparator();
		info = info + "KEYWORD END: " + keywordEnd + System.lineSeparator();
		info = info + "COMMAND TABLE LENGTH: " + commandTableLength + System.lineSeparator();
		info = info + "DEFAULT START: " + defaultStart + System.lineSeparator();
		info = info + "DEFAULT END: " + defaultEnd + System.lineSeparator();
		info = info + "EXPRESSION START: " + expressionStart + System.lineSeparator();
		info = info + "EXPRESSION END: " + expressionEnd + System.lineSeparator();
		info = info + "TOTAL SIZE: " + totalSize + System.lineSeparator();
		return info;
	}
	
	public int update(SymbolTable symbolTable,KeywordTable keywordTable,CommandTable commandTable,DefaultTable defaultTable, ExpressionTable expressionTable) {
		
		int prevExprStart = expressionStart;
		symbolTableLength = symbolTable.size();
		System.out.println("SYM TABLE LENGTH " + symbolTableLength);
		System.out.println("KEY START: " + keywordStart);
		keywordEnd = keywordStart + (keywordTable.size()-1);
		System.out.println("KEY END: " + keywordEnd);
		defaultStart = 16*keywordEnd + 1;
		System.out.println("DEF START "  + defaultStart);
		commandTableLength = commandTable.size();
		int idft = keywordEnd*16 + 1;
		defaultEnd = 4*defaultTable.size()+idft-1;
		System.out.println("IDFT: " + idft);
		System.out.println("DEF END 1: " + defaultEnd);
		defaultEnd = defaultStart + (defaultTable.byteSize()-12)/4;
		System.out.println("DEF END 2: " + defaultEnd);
		//expressionStart = defaultEnd + 3 + commandTable.maxDef()*5;
		expressionStart = defaultEnd  + 6 + commandTable.maxDef()*5;
		expressionEnd = expressionStart + (expressionTable.byteSize()-16)/4;
		totalSize = expressionEnd;
		int offset = expressionStart - prevExprStart;
		System.out.println("OFFSET " + offset);
		return offset;
	}

}
