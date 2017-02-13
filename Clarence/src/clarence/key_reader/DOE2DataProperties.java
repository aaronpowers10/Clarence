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

public class DOE2DataProperties {

	private ArrayList<TypeProperties> typePropertiesList;

	public DOE2DataProperties(SymbolTable symbolTable, KeywordTable keywordTable, CommandTable commandTable, DefaultTable defaultTable, ExpressionTable expressionTable) {
		typePropertiesList = new ArrayList<TypeProperties>();
		for (int commandIndex = 0; commandIndex < commandTable.size(); commandIndex++) {
			for (int typeIndex = 0; typeIndex < commandTable.get(commandIndex).numTypes(); typeIndex++) {
				TypeProperties typeProperties = new TypeProperties(commandTable.get(commandIndex), keywordTable, typeIndex, symbolTable,
						commandTable,defaultTable,expressionTable);
				typePropertiesList.add(typeProperties);
			}
		}
	}

}
