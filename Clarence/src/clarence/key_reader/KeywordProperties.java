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

public class KeywordProperties {
	
	private String name;
	private String abbreviation;
	private KeywordValueProperties valueProperties;
	
	public KeywordProperties(String name, String abbreviation,KeywordValueProperties valueProperties){
		this.name = name;
		this.abbreviation = abbreviation;
		this.valueProperties = valueProperties;
	}
	
	public String name(){
		return name;
	}
	
	public String abbreviation(){
		return abbreviation;
	}
	
	public KeywordValueProperties valueProperties(){
		return valueProperties;
	}
	
	public String type(){
		return valueProperties.fieldType();
	}
	
	public String write(){
		String output = "";
		output = output + "KEYWORD-DEFINITION " + name + System.lineSeparator();
		output = output + "KEYWORD-TYPE " + type() + System.lineSeparator();
		output = output + valueProperties.write();
		output = output + System.lineSeparator();
		return output;
	}

}
