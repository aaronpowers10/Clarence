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

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class DOE2Tables {
	
	private LengthData lengthData;
	private CommandTable commandTable;
	private KeywordTable keywordTable;
	private SymbolTable symbolTable;
	private DefaultTable defaultTable;
	private ExpressionTable expressionTable;
	
	public DOE2Tables(String filePath) throws IOException{
		read(filePath);
	}
	
	private void read(String filePath) throws IOException{
		byte[] bytes = Files.readAllBytes(Paths.get(filePath));
		
		int record1Start = 10808 + 4;
		int record1End = record1Start + 44;
		ByteBuffer record1Buffer = ByteBuffer.wrap(Arrays.copyOfRange(bytes, record1Start, record1End));
		record1Buffer.order(ByteOrder.LITTLE_ENDIAN);
		lengthData = new LengthData(record1Buffer);
		
		int record2Start = record1End + 8;
		int record2End = record2Start + (lengthData.symbolTableLength()*16 + (lengthData.keywordTableLength())*16+
				lengthData.commandTableLength()*24)*4; 
		ByteBuffer record2Buffer = ByteBuffer.wrap(Arrays.copyOfRange(bytes, record2Start, record2End));
		record2Buffer.order(ByteOrder.LITTLE_ENDIAN);
		
		symbolTable = new SymbolTable(record2Buffer,symbolTableLength());
		keywordTable = new KeywordTable(record2Buffer,keywordTableLength());
		commandTable = new CommandTable(record2Buffer,commandTableLength(),lengthData);
		
		int record3Start = record2End + 8;
		int record3End = record3Start + (lengthData.defaultTableLength())*4;
		ByteBuffer record3Buffer = ByteBuffer.wrap(Arrays.copyOfRange(bytes, record3Start, record3End));
		record3Buffer.order(ByteOrder.LITTLE_ENDIAN);
		defaultTable = new DefaultTable(record3Buffer,commandTable);
			
		int record4Start = record3End + 8;
		int record4End = record4Start + (lengthData.expressionTableLength())*4;
		ByteBuffer record4Buffer = ByteBuffer.wrap(Arrays.copyOfRange(bytes, record4Start, record4End));
		record4Buffer.order(ByteOrder.LITTLE_ENDIAN);
		expressionTable = new ExpressionTable(record4Buffer,lengthData.expressionTableLength(),lengthData.expressionStart());
	}
	
	public CommandEntry commandEntry(int index){
		return commandTable.get(index);
	}
	
	public int numCommands(){
		return commandTable.size();
	}
	
	public CommandEntry getCommandOfType(int type){
		return commandTable.getOfType(type);
	}
	
	public CommandEntry getCommandParent(int parentIndex){
		return commandTable.getParent(parentIndex);
	}
	
	public KeywordEntry keywordEntry(int index){
		return keywordTable.get(index);
	}
	
	public int numKeywords(){
		return keywordTable.size();
	}
	
	public SymbolEntry symbolEntry(int index){
		return symbolTable.get(index);
	}
	
	public int numSymbols(){
		return symbolTable.size();
	}
	
	public ArrayList<SymbolEntry> getSymbolListOfType(int type){
		return symbolTable.getListOfType(type);
	}
	
//	public DefaultEntry defaultEntry(int index){
//		return defaultTable.get(index);
//	}
	
	public DefaultEntry defaultEntry(String commandName){
		return defaultTable.get(commandName);
	}
	
	public TypeDefaultEntry defaultEntry(String commandName, int type){
		return defaultTable.get(commandName,type);
	}
	
	public ExpressionEntry expressionEntry(int index){
		return expressionTable.getByPointer(index);
	}
	
	public int units(){
		return lengthData.units();
	}
	
	public int maxSymbols(){
		return lengthData.maxSymbols();
	}
	
	public int totalSize(){
		return lengthData.totalSize();
	}
	
	public int keywordTableLength(){
		return lengthData.keywordTableLength();
	}
	
	public int defaultTableLength(){
		return lengthData.defaultTableLength();
	}
	
	public int expressionTableLength(){
		return lengthData.expressionTableLength();
	}
	
	public int expressionStart(){
		return lengthData.expressionStart();
	}
	
	public int symbolTableLength(){
		return lengthData.symbolTableLength();
	}
	
	public int commandTableLength(){
		return lengthData.commandTableLength();
	}
	
//	public int keywordStart(){
//		return lengthData.keywordStart();
//	}
	
	public int defaultStart(){
		return lengthData.defaultStart();
	}

}
