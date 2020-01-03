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

public class DefaultTable {

	private ArrayList<DefaultEntry> defaultEntries;
	private int i1, i2;

	public DefaultTable(ByteBuffer buffer, CommandTable commandTable) {
		i1 = buffer.getInt();
		i2 = buffer.getInt();
		
//		System.out.println("DEF TBL I1: " + i1);
//		System.out.println("DEF TBL I2: " + i2);
		
		defaultEntries = new ArrayList<DefaultEntry>();
		for (int i = 0; i < commandTable.size(); i++) {
			if (commandTable.get(i).hasDefaults()) {
				defaultEntries.add(new DefaultEntry(buffer, commandTable.get(i)));
			}
		}
	}

	public void write(ByteBuffer buffer) {
		//buffer.putInt(i1);
		//buffer.putInt(i2);
		for (DefaultEntry entry : defaultEntries) {
			entry.write(buffer);
		}
	}

	public int byteSize() {
		int byteSize = 8;
		for (DefaultEntry entry : defaultEntries) {
			byteSize += entry.byteSize();
		}
		return byteSize;
	}

//	public DefaultEntry get(int index) {
//		return defaultEntries.get(index);
//	}
//
//	public TypeDefaultEntry get(int index, int type) {
//		return defaultEntries.get(index).defaultEntry(type);
//	}

	public DefaultEntry get(String commandName) {
		for (int i = 0; i < defaultEntries.size(); i++) {
			if (defaultEntries.get(i).commandName().equals(commandName)) {
				return defaultEntries.get(i);
			}
		}
		return null;
	}

	public TypeDefaultEntry get(String commandName, int type) {
		return get(commandName).defaultEntry(type);
	}
	
	public int size() {
		int size = 0;
		for(DefaultEntry entry: defaultEntries) {
			size += entry.size();
		}
		return size;
	}

	public String summary() {
		StringBuilder summary = new StringBuilder();
		summary.append("DEFAULT TABLE SUMMARY" + "\n");
		summary.append("NUMBER OF ENTRIES: " + size() + "\n");
		summary.append("SIZE ON DISK: " + byteSize() + "\n");
		summary.append("DEFAULT ENTRIES: " + "\n");
		int tableIndex = 1;
		for (int i = 0; i < defaultEntries.size(); i++) {
			DefaultEntry defaultEntry = defaultEntries.get(i);
			String commandName = defaultEntry.commandName();
			summary.append("DEFAULTS FOR: " + commandName + " WITH " + defaultEntry.size() + " TYPES"
					+ "\n");

			for (int j = 0; j < defaultEntries.get(i).numEntries(); j++) {
				summary.append("DEFAULTS FOR COMMAND " + commandName + " TYPE " + (j + 1) + "\n");
				TypeDefaultEntry entry = defaultEntries.get(i).defaultEntry(j);
				for (int k = 0; k < entry.size(); k++) {
					int typeInt = entry.type(k);
					int val = entry.value(k);
					summary.append(tableIndex + ": ");
					if (typeInt == 2) {
						summary.append("EXPRESSION INDEX: " + val + "\n");
					} else {
						if (val == -947768064) {
							summary.append("VALUE: UNFILLED" + "\n");
						} else if (val == -946345856) {
							summary.append("VALUE: NO-DEFAULT" + "\n");
						} else if (val == -944923648) {
							summary.append("VALUE: UNUSED" + "\n");
						} else if (val == -943501440) {
							summary.append("VALUE: REQUIRED" + "\n");
						} else {
							if (Math.abs(val) < 1000000000) {
								summary.append("VALUE: " + val + "\n");
							} else {
								float valF = IntToFloat.convert(val);
								summary.append("VALUE: " + valF + "\n");
							}
						}
					}
					tableIndex++;
				}

			}
		}
		return summary.toString();
	}
	
	public void add(DefaultCreator creator, String commandName) {
		this.get(commandName).add(creator);
	}
	
	public void offsetExpressions(int offset) {
		for(DefaultEntry entry: defaultEntries) {
			entry.offsetExpressions(offset);
		}
	}

}
