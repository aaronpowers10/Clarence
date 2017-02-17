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

	private ArrayList<CommandProperties> commandPropertiesList;

	public DOE2DataProperties(DOE2Tables doe2Tables) {
		commandPropertiesList = new ArrayList<CommandProperties>();
		for (int commandIndex = 0; commandIndex < doe2Tables.numCommands(); commandIndex++) {
			for (int typeIndex = 1; typeIndex < doe2Tables.commandEntry(commandIndex).numTypes() + 1; typeIndex++) {
				CommandProperties typeProperties = new CommandProperties(commandIndex,typeIndex,doe2Tables);
				commandPropertiesList.add(typeProperties);
			}
		}
	}
	
	public int size(){
		return commandPropertiesList.size();
	}
	
	public CommandProperties get(int index){
		return commandPropertiesList.get(index);
	}
	
	public CommandProperties getCommandProperties(String name){
		for(int i=0;i<commandPropertiesList.size();i++){
			if(commandPropertiesList.get(i).name().equals(name)){
				return commandPropertiesList.get(i);
			}
		}
		return null;
	}
	
	public KeywordProperties getKeywordProperties(String command, String keyword){
		return getCommandProperties(command).getKeywordProperties(keyword);
	}

}
