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

	private DOE2Tables doe2Tables;
	private CommandEntry commandEntry;
	private int typeIndex;

	public FieldValuePropertiesFactory(CommandEntry commandEntry, int typeIndex,DOE2Tables doe2Tables) {
		this.doe2Tables = doe2Tables;
		this.commandEntry = commandEntry;
		this.typeIndex = typeIndex;
	}

	public FieldValueProperties create(KeywordEntry keywordEntry) {

		if (keywordEntry.length() > 1) {
			ListFieldProperties valueProperties = new ListFieldProperties();
			for (int i = 0; i < keywordEntry.length(); i++) {
				FieldValueProperties element = createElement(keywordEntry);
				if(commandEntry.hasDefaults()){
					int defaultType = doe2Tables.defaultEntry(commandEntry.name(),typeIndex).type(keywordEntry.valPos() + i - 1);
					int defaultValue = doe2Tables.defaultEntry(commandEntry.name(),typeIndex).value(keywordEntry.valPos() + i - 1);
					element.setDefaults(defaultType, defaultValue, doe2Tables);
				}
				
				valueProperties.add(element);
			}
			return valueProperties;
		} else {
			FieldValueProperties valueProperties = createElement(keywordEntry);
			if(commandEntry.hasDefaults()){
				int defaultType = doe2Tables.defaultEntry(commandEntry.name(),typeIndex).type(keywordEntry.valPos() - 1);
				int defaultValue = doe2Tables.defaultEntry(commandEntry.name(),typeIndex).value(keywordEntry.valPos() - 1);
				valueProperties.setDefaults(defaultType, defaultValue, doe2Tables);
			}
			return valueProperties;
		}
	}
	

	private FieldValueProperties createElement(KeywordEntry keywordEntry) {
		if (keywordEntry.type() == 1) {
			return createRealFieldProperties(keywordEntry);
		} else if (keywordEntry.type() == 2) {
			return createCodewordFieldProperties(keywordEntry);
		} else if (keywordEntry.type() == 3) {
			return createObjectFieldProperties(keywordEntry);
		} else if (keywordEntry.type() == 4) {
			return createStringFieldProperties(keywordEntry);
		} else if (keywordEntry.type() == 5) {
			return createRealFieldProperties(keywordEntry);
		} else if (keywordEntry.type() == 6) {
			return createIntegerFieldProperties(keywordEntry);
		} else if (keywordEntry.type() == 10) {
			return createObjectFieldProperties(keywordEntry);
		} else if (keywordEntry.type() == 13) {
			return createObjectFieldProperties(keywordEntry);
		} else if (keywordEntry.type() == 16) {
			return createIntegerFieldProperties(keywordEntry);
		} else if (keywordEntry.type() == 20) {
			return createObjectFieldProperties(keywordEntry);
		}

		return null;
	}

	private FieldValueProperties createIntegerFieldProperties(KeywordEntry keywordEntry) {
		float min = IntToFloat.convert(keywordEntry.min());
		float max = IntToFloat.convert(keywordEntry.max());
		return new IntegerFieldProperties(min, max);
	}

	private FieldValueProperties createRealFieldProperties(KeywordEntry keywordEntry) {
		float min = IntToFloat.convert(keywordEntry.min());
		float max = IntToFloat.convert(keywordEntry.max());
		return new RealFieldProperties(min, max);
	}

	private FieldValueProperties createCodewordFieldProperties(KeywordEntry keywordEntry) {
		int codewordType = keywordEntry.min();
		ArrayList<SymbolEntry> allowedValues = doe2Tables.getSymbolListOfType(codewordType);
		return new CodewordFieldProperties(allowedValues,doe2Tables);
	}

	private FieldValueProperties createObjectFieldProperties(KeywordEntry keywordEntry) {
		int objectType = keywordEntry.min();
		CommandEntry commandEntry = doe2Tables.getCommandOfType(objectType);
		if (commandEntry == null) {
			return createCodewordFieldProperties(keywordEntry);
		}
		ArrayList<String> allowedTypes = new ArrayList<String>();
		int typeCode = keywordEntry.typeCode();
		int type1Index = new BinaryString(typeCode, 32).substring(20, 32).toDecimal();
		if (type1Index != 0) {
			allowedTypes.add(doe2Tables.symbolEntry(type1Index).symbol());
		}
		int type2Index = new BinaryString(typeCode, 32).substring(10, 20).toDecimal();
		if (type2Index != 0) {
			allowedTypes.add(doe2Tables.symbolEntry(type2Index).symbol());
		}
		int type3Index = new BinaryString(typeCode, 32).substring(0, 10).toDecimal();
		if (type3Index != 0) {
			allowedTypes.add(doe2Tables.symbolEntry(type3Index).symbol());
		}
		return new ObjectFieldProperties(commandEntry, allowedTypes);
	}
	
	private FieldValueProperties createStringFieldProperties(KeywordEntry keywordEntry) {
		return new StringFieldProperties();
	}

}
