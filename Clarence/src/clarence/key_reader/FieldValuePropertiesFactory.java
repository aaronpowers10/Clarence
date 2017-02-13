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

	public FieldValuePropertiesFactory(SymbolTable symbolTable, CommandTable commandTable) {
		this.symbolTable = symbolTable;
		this.commandTable = commandTable;
	}

	public FieldValueProperties create(KeywordEntry keywordEntry) {

		if (keywordEntry.length() > 1) {
			ListFieldProperties valueProperties = new ListFieldProperties();
			for(int i=0;i<keywordEntry.length();i++){
				valueProperties.add(this.createElement(keywordEntry, i));
			}
			return valueProperties;
		} else {

			if (keywordEntry.type() == 1) {
				double min = IntToDouble.convert(keywordEntry.min());
				double max = IntToDouble.convert(keywordEntry.max());
				return new RealFieldProperties(min, max);
			} else if (keywordEntry.type() == 2) {
				int codewordType = keywordEntry.min();
				ArrayList<SymbolEntry> allowedValues = symbolTable.getListOfType(codewordType);
				return new CodewordFieldProperties(allowedValues);
			} else if (keywordEntry.type() == 3) {
				int objectType = keywordEntry.type();
				CommandEntry commandEntry = commandTable.getOfType(objectType);
				return new ObjectFieldProperties(commandEntry);
			} else if (keywordEntry.type() == 4) {
				return new StringFieldProperties();
			} else if (keywordEntry.type() == 5) {
				double min = IntToDouble.convert(keywordEntry.min());
				double max = IntToDouble.convert(keywordEntry.max());
				return new RealFieldProperties(min, max);
			} else if (keywordEntry.type() == 6) {
				double min = IntToDouble.convert(keywordEntry.min());
				double max = IntToDouble.convert(keywordEntry.max());
				return new IntegerFieldProperties(min, max);
			} else if (keywordEntry.type() == 10) {
				int objectType = keywordEntry.type();
				CommandEntry commandEntry = commandTable.getOfType(objectType);
				return new ObjectFieldProperties(commandEntry);
			} else if (keywordEntry.type() == 13) {
				int objectType = keywordEntry.type();
				CommandEntry commandEntry = commandTable.getOfType(objectType);
				return new ObjectFieldProperties(commandEntry);
			} else if (keywordEntry.type() == 16) {
				double min = IntToDouble.convert(keywordEntry.min());
				double max = IntToDouble.convert(keywordEntry.max());
				return new IntegerFieldProperties(min, max);
			} else if (keywordEntry.type() == 20) {
				int objectType = keywordEntry.type();
				CommandEntry commandEntry = commandTable.getOfType(objectType);
				return new ObjectFieldProperties(commandEntry);
			}
		}

		return null;
	}
	
	private FieldValueProperties createElement(KeywordEntry keywordEntry, int index) {
			if (keywordEntry.type() == 1) {
				double min = IntToDouble.convert(keywordEntry.min());
				double max = IntToDouble.convert(keywordEntry.max());
				return new RealFieldProperties(min, max);
			} else if (keywordEntry.type() == 2) {
				int codewordType = keywordEntry.min();
				ArrayList<SymbolEntry> allowedValues = symbolTable.getListOfType(codewordType);
				return new CodewordFieldProperties(allowedValues);
			} else if (keywordEntry.type() == 3) {
				int objectType = keywordEntry.type();
				CommandEntry commandEntry = commandTable.getOfType(objectType);
				return new ObjectFieldProperties(commandEntry);
			} else if (keywordEntry.type() == 4) {
				return new StringFieldProperties();
			} else if (keywordEntry.type() == 5) {
				double min = IntToDouble.convert(keywordEntry.min());
				double max = IntToDouble.convert(keywordEntry.max());
				return new RealFieldProperties(min, max);
			} else if (keywordEntry.type() == 6) {
				double min = IntToDouble.convert(keywordEntry.min());
				double max = IntToDouble.convert(keywordEntry.max());
				return new IntegerFieldProperties(min, max);
			} else if (keywordEntry.type() == 10) {
				int objectType = keywordEntry.type();
				CommandEntry commandEntry = commandTable.getOfType(objectType);
				return new ObjectFieldProperties(commandEntry);
			} else if (keywordEntry.type() == 13) {
				int objectType = keywordEntry.type();
				CommandEntry commandEntry = commandTable.getOfType(objectType);
				return new ObjectFieldProperties(commandEntry);
			} else if (keywordEntry.type() == 16) {
				double min = IntToDouble.convert(keywordEntry.min());
				double max = IntToDouble.convert(keywordEntry.max());
				return new IntegerFieldProperties(min, max);
			} else if (keywordEntry.type() == 20) {
				int objectType = keywordEntry.type();
				CommandEntry commandEntry = commandTable.getOfType(objectType);
				return new ObjectFieldProperties(commandEntry);
			}

		return null;
	}

}
