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

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;

public class CommandEntry {

	private String name;
	private String abbreviation;
	private int iop;
	private int level;
	private int startKeys;
	private int numKeys;
	private int typeSym;
	private int maxDef;
	private int referenceTableStart;
	private int defaultTableStart;
	private int valueLength;
	private int childClass;
	private int parentClass;
	private int defaultLength;
	private int numTypes;
	private int numDef, nSave, simLen, pos, currPrnt;
	private LengthData lengthData;
	private boolean hasDefaults;

	public CommandEntry(ByteBuffer buffer, LengthData lengthData) {
		this.lengthData = lengthData;
		read(buffer);
		hasDefaults = true;
	}

	public CommandEntry(ByteBuffer buffer, LengthData lengthData, CommandEntry previousEntry) {
		this.lengthData = lengthData;
		read(buffer);
		determineIfHasDefaults(previousEntry);
	}

	private void read(ByteBuffer buffer) {
		ByteBuffer nameBuffer = ByteBuffer.allocate(4 * 4);
		nameBuffer.order(ByteOrder.LITTLE_ENDIAN);
		for (int i = 0; i < 4; i++) {
			nameBuffer.putInt(buffer.getInt());
		}

		ByteBuffer abbreviationBuffer = ByteBuffer.allocate(4 * 2);
		abbreviationBuffer.order(ByteOrder.LITTLE_ENDIAN);
		for (int i = 0; i < 2; i++) {
			abbreviationBuffer.putInt(buffer.getInt());
		}

		try {
			name = new String(nameBuffer.array(), "ASCII").trim();
			abbreviation = new String(abbreviationBuffer.array(), "ASCII").trim();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		iop = buffer.getInt();//7
		level = buffer.getInt();//8
		startKeys = buffer.getInt();//9
		numKeys = buffer.getInt();//10
		typeSym = buffer.getInt();//11
		maxDef = buffer.getInt();//12
		numDef = buffer.getInt();//13
		referenceTableStart = buffer.getInt();//14
		defaultTableStart = buffer.getInt();//15
		valueLength = buffer.getInt();//16
		nSave = buffer.getInt();//17
		simLen = buffer.getInt();//18
		childClass = buffer.getInt();//19
		parentClass = buffer.getInt();//20
		pos = buffer.getInt();//21
		currPrnt = buffer.getInt();//22
		defaultLength = buffer.getInt();//23
		numTypes = buffer.getInt();//24
		if (numTypes == 0) {
			numTypes = 1;
		}
	}

	public void write(ByteBuffer buffer) {
		buffer.put(BinaryTools.packString(name, 16));
		buffer.put(BinaryTools.packString(abbreviation, 8));
		buffer.putInt(iop);
		buffer.putInt(level);
		buffer.putInt(startKeys);
		buffer.putInt(numKeys);
		buffer.putInt(typeSym);
		buffer.putInt(maxDef);
		buffer.putInt(numDef);
		buffer.putInt(referenceTableStart);
		buffer.putInt(defaultTableStart);
		buffer.putInt(valueLength);
		buffer.putInt(nSave);
		buffer.putInt(valueLength); //Same as i3?
		buffer.putInt(childClass);
		buffer.putInt(parentClass);
		buffer.putInt(pos);
		buffer.putInt(currPrnt);
		buffer.putInt(defaultLength);
		if (numTypes == 1) {
			buffer.putInt(0);
		} else {
			buffer.putInt(numTypes);
		}
	}

	public int byteSize() {
		return 16 + 8 + 18 * 4;
	}

	public void refreshStarts(CommandEntry prevWithDef, CommandEntry prevInRef) {
		
		if (hasDefaults()) {
			startKeys = prevWithDef.endKeys() + lengthData.keywordStart() ;
			defaultTableStart = prevWithDef.defaultTableEnd() + lengthData.defaultStart() ;
			
		}
		
		if(this.inRefTable()) {
			referenceTableStart = prevInRef.referenceTableStart() +prevInRef.maxDef();
		}
	}

	private void determineIfHasDefaults(CommandEntry previousEntry) {
		/*
		 * Deprecated commands from DOE2.1 still appear in the command table but do not
		 * have entries in the default table. These can be identified by having a
		 * defaultTableStart which is out of sequence with the previous command.
		 */

		if (numKeys() > 0) {
			if ((this.defaultTableStart() == previousEntry.defaultTableStart()
					+ previousEntry.valueLength() * previousEntry.numTypes() * 4)) {
				hasDefaults = true;
			} else {
				hasDefaults = false;
			}
		} else {
			hasDefaults = false;
		}
	}
	
	public boolean inRefTable() {
		if(typeSym == 0 || maxDef < 0) {
			return false;
		} else {
			return true;
		}
	}

	public String name() {
		return name;
	}

	public String abbreviation() {
		return abbreviation;
	}

	public boolean uniqueKeys() {
		if (numTypes < 0) {
			return true;
		} else {
			return false;
		}
	}

	public int iop() {
		return iop;
	}

	public int level() {
		return level;
	}

	public int startKeys() {
		return startKeys - lengthData.keywordStart();
	}

	public int endKeys() {
		if (uniqueKeys()) {
			return startKeys - lengthData.keywordStart() + numKeys() * numTypes();
		} else {
			return startKeys - lengthData.keywordStart() + numKeys();
		}
	}

	public int numKeys() {
		return numKeys;
	}

	public int typeSym() {
		return typeSym;
	}

	public int maxDef() {
		return maxDef;
	}

	public int referenceTableStart() {
		return referenceTableStart;
	}

	public int defaultTableStart() {
		return defaultTableStart - lengthData.defaultStart();
	}

	public int defaultTableEnd() {
		return defaultTableStart - lengthData.defaultStart() + valueLength() * numTypes() * 4;
	}

	public int valueLength() {
		return valueLength;
	}

	public int childClass() {
		return childClass;
	}

	public int parentClass() {
		return parentClass;
	}

	public int defaultLength() {
		return defaultLength;
	}

	public int numTypes() {
		return Math.abs(numTypes);
	}

	public int numUniqueTypes() {
		if (uniqueKeys()) {
			return numTypes();
		} else {
			return 1;
		}
	}

	public boolean hasDefaults() {
		return hasDefaults;
	}

//	public void setValueLength(int valueLength) {
//		this.valueLength = valueLength;
//	}
	
	public ArrayList<Integer> addKeyword(KeywordEntry keyword, KeywordTable table) {		
		ArrayList<Integer> keyInd = new ArrayList<Integer>();
		if(uniqueKeys()) {
			for(int i=0;i<numTypes();i++) {
				keyInd.add(startKeys() + numKeys*(i+1) + i);
				table.add(keyword,startKeys() + numKeys*(i+1) + i);
			}
		} else {
			keyInd.add(endKeys());
			table.add(keyword,endKeys());
		}
		numKeys++;
		valueLength += keyword.length();
		defaultLength += keyword.length();
		simLen += keyword.length();
		return keyInd;
		//System.out.println("NEW LENGTH " + valueLength);
	}
	
	public void setRefTableStart(int i) {
		this.referenceTableStart = i;
	}
	
	public void setDefTableStart(int i) {
		this.defaultTableStart = i;
	}

	public String summary() {
		StringBuilder summary = new StringBuilder();
		summary.append("SUMMARY FOR " + name() + "\n");
		summary.append("IOP: " + iop + "\n");
		summary.append("LEVEL: " + level + "\n");
		summary.append("START-KEYS: " + startKeys + "\n");
		summary.append("NUMBER-OF-KEYWORDS: " + numKeys + "\n");
		summary.append("TYPE-SYM: " + typeSym + "\n");
		summary.append("MAX-DEF: " + maxDef + "\n");
		summary.append("REFERENCE-TABLE-START: " + referenceTableStart + "\n");
		summary.append("DEFAULT-TABLE-START: " + defaultTableStart + "\n");
		summary.append("VALUE-LENGTH: " + valueLength + "\n");
		summary.append("CHILD-CLASS: " + childClass + "\n");
		summary.append("PARENT-CLASS: " + parentClass + "\n");
		summary.append("DEFAULT-LENGTH: " + defaultLength + "\n");
		summary.append("NUM-TYPES: " + numTypes + "\n");
		summary.append("NUM-DEF: " + numDef + "\n");
		summary.append("N-SAVE: " + nSave + "\n");
		summary.append("SIM-LEN: " + simLen + "\n");
		summary.append("POS: " + pos + "\n");
		return summary.toString();
	}

}
