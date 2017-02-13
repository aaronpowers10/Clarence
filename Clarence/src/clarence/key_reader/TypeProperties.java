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

public class TypeProperties {
	private String name;
	private String abbreviation;
	private ArrayList<FieldProperties> fieldPropertiesList;

	public TypeProperties(CommandEntry commandEntry, KeywordTable keywordTable, int type, SymbolTable symbolTable,
			CommandTable commandTable, DefaultTable defaultTable, ExpressionTable expressionTable) {
		this.name = commandEntry.name();
		this.abbreviation = commandEntry.abbreviation();
		fieldPropertiesList = new ArrayList<FieldProperties>();
		FieldPropertiesFactory factory = new FieldPropertiesFactory(symbolTable, commandTable);
		for (int i = 0; i < commandEntry.numKeys(); i++) {
			fieldPropertiesList.add(factory.create(keywordTable.get(commandEntry.startKeys() + i)));
		}
	}

	public String name() {
		return name;
	}

	public String abbreviation() {
		return abbreviation;
	}

	public int numFields() {
		return fieldPropertiesList.size();
	}

	public void addFieldProperties(FieldProperties fieldProperties) {
		fieldPropertiesList.add(fieldProperties);
	}

}
