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
	
	public void write(ByteBuffer buffer) {
		for(int i=0;i<commands.size();i++) {
			commands.get(i).write(buffer);
		}
	}

	public int size() {
		return commands.size();
	}
	
	public CommandEntry get(int index){
		return commands.get(index);
	}
	
	public void add(CommandEntry command) {
		commands.add(command);
	}
	
	public CommandEntry getOfType(int type){
		for(int i=0;i<commands.size();i++){
			if(commands.get(i).typeSym() == type){
				return commands.get(i);
			}
		}
		return null;
	}
	
	public CommandEntry getParent(int parentIndex){
		for(int i=0;i<commands.size();i++){
			if(commands.get(i).childClass() == parentIndex){
				return commands.get(i);
			}
		}
		return null;
	}
	
	public int byteSize() {
		int byteSize = 0;
		for(CommandEntry command: commands) {
			byteSize += command.byteSize();
		}
		return byteSize;
	}
	
	public String summary() {
		StringBuilder summary = new StringBuilder();
		summary.append("COMMAND TABLE SUMMARY" + System.lineSeparator());
		summary.append("NUMBER OF ENTRIES: " + size()  + System.lineSeparator());
		summary.append("SIZE ON DISK: " + byteSize() + " BYTES"+ System.lineSeparator());
		summary.append("COMMANDS:"+ System.lineSeparator());
		for(int i=0;i<size();i++) {
			summary.append((i+1) + ": " + commands.get(i).name() + System.lineSeparator());
		}
		return summary.toString();
	}
	
	public int maxDef() {
		int maxDef = 0;
		for(CommandEntry command: commands) {
			if(command.maxDef() >0 && command.typeSym() > 0) {
				maxDef += command.maxDef();
			}
		}
		return maxDef;
	}
	
	public int indexOf(String name) {
		for(int i=0;i<commands.size();i++) {
			if(commands.get(i).name().toLowerCase().equals(name.toLowerCase())) {
				//return commands.get(i).typeSym();
				return i;
			}
		}
		return -1;
	}
	
	public int typeSymOf(String name) {
		for(int i=0;i<commands.size();i++) {
			if(commands.get(i).name().toLowerCase().equals(name.toLowerCase())) {
				return commands.get(i).typeSym();
			}
		}
		return -1;
	}

}
