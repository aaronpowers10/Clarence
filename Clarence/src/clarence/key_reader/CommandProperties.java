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

public class CommandProperties {
	private String name;
	private String abbreviation;
	private ArrayList<KeywordProperties> keywordPropertiesList;
	private int typeIndex;
	private String parent;

	public CommandProperties(int commandIndex, int typeIndex, DOE2Tables doe2Tables) {
		CommandEntry commandEntry = doe2Tables.commandEntry(commandIndex);
		this.name = commandEntry.name();
		this.abbreviation = commandEntry.abbreviation();
		this.typeIndex = typeIndex;
		if(commandEntry.parentClass() == 0 || commandEntry.level() == 0){
			parent = "NONE";
		} else {
			parent = doe2Tables.getCommandParent(commandEntry.parentClass()).name();
		}
		int typeOffset = 0;
		if(commandEntry.uniqueKeys()){
			typeOffset = (typeIndex-1)*commandEntry.numKeys();
		} 
		KeywordValuePropertiesFactory valueFactory = new KeywordValuePropertiesFactory(commandEntry,typeIndex-1,doe2Tables);
		keywordPropertiesList = new ArrayList<KeywordProperties>();
		for (int i = 0; i < commandEntry.numKeys(); i++) {
			KeywordEntry keywordEntry = doe2Tables.keywordEntry(commandEntry.startKeys() + i + typeOffset);
			String fieldName = keywordEntry.name();
			String fieldAbbreviation = keywordEntry.abbreviation();	
			keywordPropertiesList.add(new KeywordProperties(fieldName,fieldAbbreviation,valueFactory.create(keywordEntry)));
		}
	}

	public String name() {
		return name;
	}

	public String abbreviation() {
		return abbreviation;
	}

	public int numKeywords() {
		return keywordPropertiesList.size();
	}
	
	public String parent(){
		return parent;
	}

	public void addKeywordProperties(KeywordProperties keywordProperties) {
		keywordPropertiesList.add(keywordProperties);
	}
	
	public KeywordProperties get(int index){
		return keywordPropertiesList.get(index);
	}
	
	public KeywordProperties getKeywordProperties(String name){
		for(int i=0;i<keywordPropertiesList.size();i++){
			if(keywordPropertiesList.get(i).name().equals(name)){
				return keywordPropertiesList.get(i);
			}
		}
		return null;
	}
	
	public String write(){
		String output = "";
		output = output + "COMMAND-DEFINITION " + name + System.lineSeparator();
		output = output + "ABBREVIATION " + abbreviation + System.lineSeparator();
		try{
			CodewordFieldProperties type = (CodewordFieldProperties)getKeywordProperties("TYPE").valueProperties();
			output = output + "TYPE " + type.allowedValue(typeIndex) + System.lineSeparator();
		} catch (NullPointerException e){
			
		}
		output = output + "NUMBER-OF-KEYWORDS " + numKeywords() + System.lineSeparator();
		if(!parent.equals("NONE")){
			output = output + "PARENT " + parent + System.lineSeparator();
		}
		output = output + System.lineSeparator();
		for(int i=0;i<numKeywords();i++){
			output = output + keywordPropertiesList.get(i).write();
		}
		return output;
	}

}
