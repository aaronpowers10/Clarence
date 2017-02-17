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
	private int typeIndex;
	private String parent;

	public TypeProperties(int commandIndex, int typeIndex, DOE2Tables doe2Tables) {
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
		FieldValuePropertiesFactory valueFactory = new FieldValuePropertiesFactory(commandEntry,typeIndex-1,doe2Tables);
		fieldPropertiesList = new ArrayList<FieldProperties>();
		for (int i = 0; i < commandEntry.numKeys(); i++) {
			KeywordEntry keywordEntry = doe2Tables.keywordEntry(commandEntry.startKeys() + i + typeOffset);
			String fieldName = keywordEntry.name();
			String fieldAbbreviation = keywordEntry.abbreviation();	
			fieldPropertiesList.add(new FieldProperties(fieldName,fieldAbbreviation,valueFactory.create(keywordEntry)));
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
	
	public String parent(){
		return parent;
	}

	public void addFieldProperties(FieldProperties fieldProperties) {
		fieldPropertiesList.add(fieldProperties);
	}
	
	public FieldProperties get(int index){
		return fieldPropertiesList.get(index);
	}
	
	public FieldProperties getFieldProperties(String name){
		for(int i=0;i<fieldPropertiesList.size();i++){
			if(fieldPropertiesList.get(i).name().equals(name)){
				return fieldPropertiesList.get(i);
			}
		}
		return null;
	}
	
	public String write(){
		String output = "";
		output = output + "COMMAND-DEFINITION " + name + System.lineSeparator();
		output = output + "ABBREVIATION " + abbreviation + System.lineSeparator();
		try{
			CodewordFieldProperties type = (CodewordFieldProperties)getFieldProperties("TYPE").valueProperties();
			output = output + "TYPE " + type.allowedValue(typeIndex) + System.lineSeparator();
		} catch (NullPointerException e){
			
		}
		output = output + "NUMBER-OF-KEYWORDS " + numFields() + System.lineSeparator();
		if(!parent.equals("NONE")){
			output = output + "PARENT " + parent + System.lineSeparator();
		}
		output = output + System.lineSeparator();
		for(int i=0;i<numFields();i++){
			output = output + fieldPropertiesList.get(i).write();
		}
		return output;
	}

}
