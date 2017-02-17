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

public class ListFieldProperties extends KeywordValueProperties {
	
	private ArrayList<KeywordValueProperties> elements;
	
	public ListFieldProperties(){
		super();
		elements = new ArrayList<KeywordValueProperties>();
	}
	
	public void add(KeywordValueProperties element){
		elements.add(element);
	}
	
	@Override
	public String fieldType() {
		return "LIST";
	}
	
	public int maxSize(){
		return elements.size();
	}

	@Override
	public String write() {
		String output = "";
		output = output + "ELEMENT-TYPE " + elements.get(0).fieldType() + System.lineSeparator();
		for(int i=0;i<maxSize();i++){
			output = output + "ELEMENT " + (i+1) + System.lineSeparator();
			output = output + elements.get(i).write();
		}
		return output;
	}

}
