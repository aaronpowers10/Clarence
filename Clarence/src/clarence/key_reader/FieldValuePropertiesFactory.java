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

import java.util.ArrayList;

public class FieldValuePropertiesFactory {

	private SymbolTable symbolTable;
	private CommandTable commandTable;
	private DefaultTable defaultTable;
	private ExpressionTable expressionTable;
	private int commandIndex;
	private int typeIndex;

	public FieldValuePropertiesFactory(SymbolTable symbolTable, CommandTable commandTable, DefaultTable defaultTable, ExpressionTable expressionTable, int commandIndex,int typeIndex) {
		this.symbolTable = symbolTable;
		this.commandTable = commandTable;
		this.defaultTable = defaultTable;
		this.expressionTable = expressionTable;
		this.commandIndex = commandIndex;
		this.typeIndex = typeIndex;
	}

	public FieldValueProperties create(KeywordEntry keywordEntry) {

		if (keywordEntry.length() > 1) {
			ListFieldProperties valueProperties = new ListFieldProperties(1,1,expressionTable);
			for (int i = 0; i < keywordEntry.length(); i++) {
				int defaultType = defaultTable.get(commandIndex).defaultEntry(typeIndex).type(keywordEntry.valPos() + i - 1);
				int defaultValue = defaultTable.get(commandIndex).defaultEntry(typeIndex).value(keywordEntry.valPos() + i - 1);
				valueProperties.add(this.createElement(keywordEntry, defaultType,defaultValue));
			}
			return valueProperties;
		} else {
			int defaultType = defaultTable.get(commandIndex).defaultEntry(typeIndex).type(keywordEntry.valPos() - 1);
			int defaultValue = defaultTable.get(commandIndex).defaultEntry(typeIndex).value(keywordEntry.valPos() - 1);
			return createElement(keywordEntry,defaultType,defaultValue);
		}
	}

	private FieldValueProperties createElement(KeywordEntry keywordEntry, int defaultType, int defaultValue) {
		if (keywordEntry.type() == 1) {
			return createRealFieldProperties(keywordEntry, defaultType,defaultValue);
		} else if (keywordEntry.type() == 2) {
			return createCodewordFieldProperties(keywordEntry, defaultType,defaultValue);
		} else if (keywordEntry.type() == 3) {
			return createObjectFieldProperties(keywordEntry, defaultType,defaultValue);
		} else if (keywordEntry.type() == 4) {
			return createStringFieldProperties(keywordEntry,defaultType,defaultValue);
		} else if (keywordEntry.type() == 5) {
			return createRealFieldProperties(keywordEntry, defaultType,defaultValue);
		} else if (keywordEntry.type() == 6) {
			return createIntegerFieldProperties(keywordEntry, defaultType,defaultValue);
		} else if (keywordEntry.type() == 10) {
			return createObjectFieldProperties(keywordEntry, defaultType,defaultValue);
		} else if (keywordEntry.type() == 13) {
			return createObjectFieldProperties(keywordEntry,defaultType,defaultValue);
		} else if (keywordEntry.type() == 16) {
			return createIntegerFieldProperties(keywordEntry, defaultType,defaultValue);
		} else if (keywordEntry.type() == 20) {
			return createObjectFieldProperties(keywordEntry, defaultType,defaultValue);
		}

		return null;
	}

	private FieldValueProperties createIntegerFieldProperties(KeywordEntry keywordEntry, int defaultType, int defaultValue) {
		float min = IntToFloat.convert(keywordEntry.min());
		float max = IntToFloat.convert(keywordEntry.max());
		return new IntegerFieldProperties(min, max,defaultType,defaultValue,expressionTable);
	}

	private FieldValueProperties createRealFieldProperties(KeywordEntry keywordEntry, int defaultType, int defaultValue) {
		float min = IntToFloat.convert(keywordEntry.min());
		float max = IntToFloat.convert(keywordEntry.max());
		return new RealFieldProperties(min, max,defaultType,defaultValue,expressionTable);
	}

	private FieldValueProperties createCodewordFieldProperties(KeywordEntry keywordEntry, int defaultType, int defaultValue) {
		int codewordType = keywordEntry.min();
		ArrayList<SymbolEntry> allowedValues = symbolTable.getListOfType(codewordType);
		return new CodewordFieldProperties(allowedValues,defaultType,defaultValue,expressionTable,symbolTable);
	}

	private FieldValueProperties createObjectFieldProperties(KeywordEntry keywordEntry, int defaultType, int defaultValue) {
		int objectType = keywordEntry.min();
		CommandEntry commandEntry = commandTable.getOfType(objectType);
		if (commandEntry == null) {
			return createCodewordFieldProperties(keywordEntry,defaultType,defaultValue);
		}
		ArrayList<String> allowedTypes = new ArrayList<String>();
		int typeCode = keywordEntry.typeCode();
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
		return new ObjectFieldProperties(commandEntry, allowedTypes,defaultType,defaultValue,expressionTable);
	}
	
	private FieldValueProperties createStringFieldProperties(KeywordEntry keywordEntry, int defaultType, int defaultValue) {
		return new StringFieldProperties(defaultType,defaultValue,expressionTable);
	}

}
