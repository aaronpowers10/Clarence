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

public class CommandTable {
	
	private ArrayList<CommandEntry> commands;

	public CommandTable(ByteBuffer buffer, int size, LengthData lengthData) {
		commands = new ArrayList<CommandEntry>();
		read(buffer, size,lengthData);
	}

	private void read(ByteBuffer buffer, int size, LengthData lengthData) {
		CommandEntry lastCommandWithDefaults = null;
		for (int i = 0; i < size; i++) {
			
			if(i==0){
				commands.add(new CommandEntry(buffer,lengthData));
			} else {
				commands.add(new CommandEntry(buffer,lengthData,lastCommandWithDefaults));
			}
			if(commands.get(i).hasDefaults()){
				lastCommandWithDefaults = commands.get(i);
			}
		}
	}

	public int size() {
		return commands.size();
	}
	
	public CommandEntry get(int index){
		return commands.get(index);
	}
	
	public CommandEntry getOfType(int type){
		for(int i=0;i<commands.size();i++){
			if(commands.get(i).typeSym() == type){
				return commands.get(i);
			}
		}
		return null;
	}

}
