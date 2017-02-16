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
import java.util.ArrayList;

public class CodewordFieldProperties extends FieldValueProperties {
	
	private ArrayList<SymbolEntry> allowedValues;
	private String defaultValue;
	
	public CodewordFieldProperties(ArrayList<SymbolEntry> allowedValues,int defaultType, int defaultValueInt,
			ExpressionTable expressionTable, SymbolTable symbolTable){
		super(defaultType,defaultValueInt,expressionTable);
		this.allowedValues = allowedValues;
		if(defaultType() == DefaultType.VALUE){
			//defaultValue = symbolTable.get(defaultValueInteger()).symbol();
		}
	}
	
	public int numAllowedValues(){
		return allowedValues.size();
	}
	
	public String allowedValue(int index){
		for(int i=0;i<allowedValues.size();i++){
			if(allowedValues.get(i).value() == index){
				return allowedValues.get(i).symbol();
			}
		}
		return "NULL";
	}

	@Override
	public String fieldType() {
		return "CODEWORD";
	}

	@Override
	public String write() {
		String output = "";
		output = output + "NUMBER-OF-ALLOWED-VALUES " + numAllowedValues() + System.lineSeparator();
		output = output + "ALLOWED-VALUES ";
		for(int i=0;i<allowedValues.size();i++){
			output = output + allowedValues.get(i).symbol();
			if(i<allowedValues.size()-1){
				output = output + ", ";
			}
		}
		output = output + System.lineSeparator();
		output = output + "DEFAULT-TYPE " + defaultType() + System.lineSeparator();
		if(defaultType() == DefaultType.VALUE){
			output = output + "DEFAULT-VALUE " + defaultValue + System.lineSeparator();
		}
		return output;
	}



}
