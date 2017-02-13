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

public class CodewordFieldProperties implements FieldValueProperties {

	private ArrayList<SymbolEntry> allowedValues;
	
	public CodewordFieldProperties(ArrayList<SymbolEntry> allowedValues){
		this.allowedValues = allowedValues;
	}
	
	public int numAllowedValues(){
		return allowedValues.size();
	}
	
	public String allowedValue(int index){
		return allowedValues.get(index).symbol();
	}

}
