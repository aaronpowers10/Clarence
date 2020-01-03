package clarence.key_file;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

import clarence.key_reader.BinaryString;
import clarence.key_reader.BinaryTools;
import clarence.key_reader.CommandEntry;
import clarence.key_reader.CommandTable;
import clarence.key_reader.DefaultCreator;
import clarence.key_reader.DefaultTable;
import clarence.key_reader.DefaultType;
import clarence.key_reader.ExpressionEntry;
import clarence.key_reader.ExpressionTable;
import clarence.key_reader.IntToFloat;
import clarence.key_reader.KeywordEntry;
import clarence.key_reader.KeywordTable;
import clarence.key_reader.LengthData;
import clarence.key_reader.SymbolEntry;
import clarence.key_reader.SymbolTable;
import clarence.key_reader.TypeDefaultEntry;

public class KeywordFile {
	
	private byte[] record1;
	private LengthData lengthData;
	private SymbolTable symbolTable;
	private KeywordTable keywordTable;
	private CommandTable commandTable;
	private DefaultTable defaultTable;
	private ExpressionTable expressionTable;
	
	public KeywordFile(String filePath) throws IOException {
		byte[] bytes = Files.readAllBytes(Paths.get(filePath));
		
		// First Record
		record1 = new byte[10812];
		for(int i=0;i<record1.length;i++) {
			record1[i] = bytes[i];
		}
		
		int i1 = BinaryTools.bytesToInt(Arrays.copyOfRange(record1,0,4));
		int i2 = BinaryTools.bytesToInt(Arrays.copyOfRange(record1,record1.length-8,record1.length-4));
		int i3 = BinaryTools.bytesToInt(Arrays.copyOfRange(record1,record1.length-4,record1.length));
		//System.out.println("BYTE LEN ")
//		System.out.println("REC1 I1: " + i1);
//		System.out.println("REC1 I2: " + i2);
//		System.out.println("REC1 I3: " + i3);
		// Second Record
		int record2Start = 10812;
		int record2End = record2Start + 44;
		ByteBuffer record2Buffer = ByteBuffer.wrap(Arrays.copyOfRange(bytes, record2Start, record2End));
		record2Buffer.order(ByteOrder.LITTLE_ENDIAN);
		lengthData = new LengthData(record2Buffer);
		
		// Third Record
		int record3Start = record2End;  // + 8
		int record3End = record3Start + (symbolTableLength()*16 + keywordTableLength()*16+
				commandTableLength()*24)*4 + 8; 
		ByteBuffer record3Buffer = ByteBuffer.wrap(Arrays.copyOfRange(bytes, record3Start, record3End));
		record3Buffer.order(ByteOrder.LITTLE_ENDIAN);
		symbolTable = new SymbolTable(record3Buffer,symbolTableLength());
		keywordTable = new KeywordTable(record3Buffer,keywordTableLength());
		commandTable = new CommandTable(record3Buffer,commandTableLength(),lengthData);
		
		// Fourth Record
		int record4Start = record3End;// + 8;
		int record4End = record4Start + (lengthData.defaultTableLength())*4 + 8;
		ByteBuffer record4Buffer = ByteBuffer.wrap(Arrays.copyOfRange(bytes, record4Start, record4End));
		record4Buffer.order(ByteOrder.LITTLE_ENDIAN);
		defaultTable = new DefaultTable(record4Buffer,commandTable);
		
		// Fifth Record
		int record5Start = record4End;// + 8;
		int record5End = record5Start + (expressionTableLength())*4 + 16;
		ByteBuffer record5Buffer = ByteBuffer.wrap(Arrays.copyOfRange(bytes, record5Start, record5End));
		record5Buffer.order(ByteOrder.LITTLE_ENDIAN);
		expressionTable = new ExpressionTable(record5Buffer,expressionTableLength(),expressionStart());
	}
	
	public void write(String fileName) throws IOException{
		ByteBuffer buffer = ByteBuffer.allocate(byteSize());
		buffer.order(ByteOrder.LITTLE_ENDIAN);
		buffer.put(record1);
		lengthData.write(buffer);
		buffer.putInt(44);
		buffer.putInt(symbolTable.byteSize() + keywordTable.byteSize() + commandTable.byteSize()-8);
		symbolTable.write(buffer);
		keywordTable.write(buffer);
		commandTable.write(buffer);
		buffer.putInt(symbolTable.byteSize() + keywordTable.byteSize() + commandTable.byteSize()-8);
		buffer.putInt(defaultTable.byteSize()-8);
		defaultTable.write(buffer);
		buffer.putInt(defaultTable.byteSize()-8);
		buffer.putInt(expressionTable.byteSize()-12);
		expressionTable.write(buffer);
		buffer.putInt(expressionTable.byteSize()-12);
		buffer.rewind();
		FileChannel fc = new FileOutputStream(fileName).getChannel();
	    fc.write(buffer);
	    fc.close();
	    System.out.println("FINISHED SAVING FILE");
	    
	}
	
	public int symbolTableLength() {
		return lengthData.symbolTableLength();
	}
	
	public int keywordTableLength() {
		return lengthData.keywordTableLength();
	}
	
	public int commandTableLength() {
		return lengthData.commandTableLength();
	}
	
	public int expressionTableLength() {
		return lengthData.expressionTableLength();
	}
	
	public int expressionStart() {
		return lengthData.expressionStart();
	}
	
	public int numCommands() {
		return commandTable.size();
	}
	
	public ArrayList<String> commandNames() {
		ArrayList<String> names = new ArrayList<String>();
		for(int i=0;i< commandTableLength();i++) {
			names.add(commandTable.get(i).name());
		}
		return names;
	}
	
	public CommandEntry getCommand(int index) {
		return commandTable.get(index);
	}
	
	public KeywordEntry getKeyword(int commandIndex, int keywordIndex){
		CommandEntry command = getCommand(commandIndex);
		return keywordTable.get(command.startKeys() + keywordIndex);
	}
	
	public KeywordEntry getKeyword(int commandIndex, int keywordIndex, int typeOffset){
		CommandEntry command = getCommand(commandIndex);
		return keywordTable.get(command.startKeys() + keywordIndex + typeOffset);
	}
	
	public ArrayList<String> keywordNames(int commandIndex){
		ArrayList<String> names = new ArrayList<String>();
		CommandEntry command = getCommand(commandIndex);
		for(int i=0;i<command.numKeys();i++) {
			names.add(getKeyword(commandIndex,i).name());
		}
		return names;
	}
	
	public void setDefaultValue(int commandIndex,int keywordIndex, double value) {
		CommandEntry command = commandTable.get(commandIndex);
		int valPos = getKeyword(commandIndex,keywordIndex).valPos();
		//int valPos = keywordTable.get(keywordIndex).valPos();
		TypeDefaultEntry defaultEntry = defaultTable.get(command.name()).defaultEntry(0);
		defaultEntry.setType(1,valPos-1);
		defaultEntry.setValue(value,valPos-1);
	}
	
	public int defaultTypeInt(int commandIndex,int keywordIndex) {
		CommandEntry command = commandTable.get(commandIndex);
		int valPos = getKeyword(commandIndex,keywordIndex).valPos();
		//int valPos = keywordTable.get(keywordIndex).valPos();
		TypeDefaultEntry defaultEntry = defaultTable.get(command.name()).defaultEntry(0);
		return defaultEntry.type(valPos-1);
	}
	
	public int defaultValueInt(int commandIndex,int keywordIndex) {
		CommandEntry command = commandTable.get(commandIndex);
		int valPos = getKeyword(commandIndex,keywordIndex).valPos();
		//int valPos = keywordTable.get(keywordIndex).valPos();
		TypeDefaultEntry defaultEntry = defaultTable.get(command.name()).defaultEntry(0);
		return defaultEntry.value(valPos-1);
	}
	
	public DefaultType defaultType(int commandIndex, int keywordIndex) {
		int defaultTypeInt = defaultTypeInt(commandIndex,keywordIndex);
		int defaultValueInteger = defaultValueInt(commandIndex,keywordIndex);
		if (defaultTypeInt == 1) {
			Float defaultValueFloat = IntToFloat.convert(defaultValueInteger);
			if (defaultValueFloat == -99999.0) {
				return DefaultType.REQUIRED;
			} else if (defaultValueFloat == -88888.0) {
				return DefaultType.UNUSED;
			} else if (defaultValueFloat == -77777.0) {
				return DefaultType.NO_DEFAULT;
			} else if (defaultValueFloat == -66666.0) {
				return DefaultType.UNFILLED;
			} else {
				return DefaultType.VALUE;
			}
		} else {
			return DefaultType.EXPRESSION;
			
		}
	}
	
	public String defaultExpression(int commandIndex, int keywordIndex) {
		return expressionTable.getByPointer(defaultValueInt(commandIndex,keywordIndex)).code();
	}
	
	public ArrayList<String> allowedValues(int commandIndex, int keywordIndex){
		ArrayList<String> values = new ArrayList<String>();
		ArrayList<SymbolEntry> symbols = symbolTable.getListOfType(getKeyword(commandIndex,keywordIndex).min());
		for(SymbolEntry symbol:symbols) {
			values.add(symbol.symbol());
		}
		return values;
	}
	
	public String symbol(int index) {
		return symbolTable.get(index).symbol();
	}
	
	public int numSymbols() {
		return symbolTable.size();
	}
	
	public String allowedCommand(int commandIndex, int keywordIndex) {
		return commandTable.getOfType(getKeyword(commandIndex,keywordIndex).min()).name();
	}
	
	public ArrayList<String> allowedTypes(int commandIndex, int keywordIndex) {
		ArrayList<String> allowedTypes = new ArrayList<String>();
		int typeCode = getKeyword(commandIndex,keywordIndex).typeCode();
		int type1Index = new BinaryString(typeCode, 32).substring(20, 32).toDecimal();
		if (type1Index != 0) {
			allowedTypes.add(symbolTable.get(type1Index).symbol());
		}
		int type2Index = new BinaryString(typeCode, 32).substring(10, 20).toDecimal();
		if (type2Index != 0) {
			allowedTypes.add(symbolTable.get(type2Index).symbol());
		}
		int type3Index = new BinaryString(typeCode, 32).substring(0, 10).toDecimal();
		if (type3Index != 0) {
			allowedTypes.add(symbolTable.get(type3Index).symbol());
		}
		return allowedTypes;
	}
	
	public CommandEntry getCommandOfType(int type) {
		return commandTable.getOfType(type);
	}
	
	public int byteSize() {
		// Record 1 and 2
		int size = 10812 + 44;
		
		// Record 3
		size += symbolTable.byteSize();
		size += commandTable.byteSize();
		size += keywordTable.byteSize();
		
		// Record 4
		size += defaultTable.byteSize();
		
		// Record 5
		size += expressionTable.byteSize();
		return size;
	}
	
	public String lengthDataInfo() {
		return lengthData.getInfo();
	}
	
	public String symbolTableSummary() {
		return symbolTable.summaryString();
	}
	
	public String keywordTableSummary() {
		return keywordTable.summary();
	}
	
	public String commandTableSummary() {
		return commandTable.summary();
	}
	
	public String defaultTableSummary() {
		return defaultTable.summary();
	}
	
	public String commandSummary(int commandIndex) {
		return getCommand(commandIndex).summary();
	}
	
	public String expressionTableSummary() {
		return expressionTable.summary();
	}
	
	public void refresh() {
		int offset = lengthData.update(symbolTable, keywordTable, commandTable, defaultTable, expressionTable);
		defaultTable.offsetExpressions(offset);
		expressionTable.offsetExpressions(offset);
		CommandEntry previousCommand = null;
		CommandEntry prevCommandInRefTable = null;
		for(int i=0;i<commandTable.size();i++) {
			CommandEntry command = commandTable.get(i);
			if(i>0) {
				command.refreshStarts(previousCommand,prevCommandInRefTable);
			} else {
				command.setDefTableStart(lengthData.defaultStart());
				command.setRefTableStart(lengthData.defaultEnd()/5+2);
			}
			
			if(command.hasDefaults()) {
				previousCommand = command;
			}
			
			if(command.inRefTable()) {
				prevCommandInRefTable = command;
			}
		}
		
	}
	
	public void addKeyword(CommandEntry command,KeywordEntry keyword, DefaultCreator creator) {
		ArrayList<Integer> keyInd = command.addKeyword(keyword,keywordTable);
		defaultTable.add(creator,command.name());
		refresh();
		//int keyIndex = command.endKeys();
		for(int i=0;i<keyInd.size();i++) {
			expressionTable.keywordAdded(keyInd.get(i));
		}
		
	}
	
	public int indexOf(int commandIndex, int keywordIndex) {
		return getCommand(commandIndex).startKeys()-lengthData.keywordStart() + keywordIndex;
	}
	
	public void printRecord1() {
		System.out.println("RECORD 1");
		for(int i=0;i<record1.length;i++) {
			System.out.println((i+1) + ": " + record1[i]);
		}
	}
	
	public String expressionSummary(int index) {
		return expressionTable.summary(index);
	}
	
	public String expressionSummaryByPointer(int pointer) {
		return expressionTable.summaryByPointer(pointer);
	}
	
	public String expressionSummaryProcessed(int index) {
		return expressionTable.processedSummary(index);
	}
	
	public String expressionSummaryProcessedByPointer(int pointer) {
		return expressionTable.processedSummaryByPointer(pointer);
	}
	
	public void offsetExpressions(int offset) {
		defaultTable.offsetExpressions(offset);
	}
	
	public void keywordAdded(int keyInd) {
		expressionTable.keywordAdded(keyInd);
	}
	
	public ExpressionEntry getExpressionByIndex(int index) {
		return expressionTable.getByIndex(index);
	}
	
	public ExpressionEntry getExpressionByPointer(int pointer) {
		return expressionTable.getByPointer(pointer);
	}
	
	public int getUniqueSymbolType() {
		return symbolTable.getUniqueType();
	}
	
	public void addSymbols(ArrayList<String> symbols, int type) {
		symbolTable.addCodewordSymbols(symbols, type);
	}
	
	public int indexOfCommand(String name) {
		return commandTable.indexOf(name);
	}


}
