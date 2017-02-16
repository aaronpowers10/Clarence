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

public class ObjectFieldProperties extends FieldValueProperties{
	
	private CommandEntry commandEntry;
	private ArrayList<String> allowedTypes;
	
	public ObjectFieldProperties(CommandEntry commandEntry, ArrayList<String> allowedTypes){
		super();
		this.commandEntry = commandEntry;
		this.allowedTypes = allowedTypes;
	}
	
	public String type(){
		return commandEntry.name();
	}

	@Override
	public String fieldType() {
		return "OBJECT";
	}

	@Override
	public String write() {
		String output = "";
		output = output + "ALLOWED-COMMAND " + commandEntry.name() + System.lineSeparator();
		if(allowedTypes.size()>0){
			output = output + "ALLOWED-TYPES ";
			for(int i=0;i<allowedTypes.size();i++){
				output = output + allowedTypes.get(i);
				if(i < allowedTypes.size() - 1){
					output = output + ",";
				}
			}
			output = output + System.lineSeparator();
		}
		output = output + "DEFAULT-TYPE " + defaultType() + System.lineSeparator();
		return output;
	}

}
