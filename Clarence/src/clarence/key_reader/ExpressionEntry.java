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
import java.util.Arrays;

import clarence.key_expression.BinaryExpression;

public class ExpressionEntry {

	private int index;
	private String code;
	private int numChars;
	private int entryLength;
	private int i1, i2, i3, i4;
	private ArrayList<Byte> i5;
	private int hackInt;
	private BinaryExpression expression;

	public ExpressionEntry(ByteBuffer buffer, int index) {
		this.index = index;
		code = "";
		i5 = new ArrayList<Byte>();
		read(buffer);
	}

	private void read(ByteBuffer buffer) {
		// DOE2 Version 43-47 have two extra ints after i4.  First one holds numChars.
		i1 = buffer.getInt(); // Mostly 0, 1, 2, or 3.  Sometimes large int.
		hackInt = buffer.getInt();
		entryLength = (int) Math.ceil(hackInt / 4.0);
		i2 = buffer.getInt(); // Never 0.  Always small int.
		i3 = buffer.getInt(); // Always 0?
		numChars = buffer.getInt();
		i4 = buffer.getInt(); // Never 0.  Always small int.  Same as first int in i5, sometimes

		ByteBuffer codeBuffer = ByteBuffer.allocate(numChars);
		codeBuffer.order(ByteOrder.LITTLE_ENDIAN);
		for (int i = 0; i < numChars; i++) {
			codeBuffer.put(buffer.get());
		}

		try {
			code = new String(Arrays.copyOfRange(codeBuffer.array(), 0, numChars), "ASCII");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		int remainingBytes = entryLength*4 - code.length() - 6*4;
		//int rem = remainingBytes %4;
		//if(rem>0) {
		//	System.out.println("REMAINDER " + rem);
		//}

		//for (int i = numInts + 6; i < entryLength; i++) {
		for(int i=0;i<remainingBytes;i++) {
			i5.add(buffer.get());
		}
		
		ByteBuffer b = ByteBuffer.allocate(i5.size());
		b.order(ByteOrder.LITTLE_ENDIAN);
		for(Byte bi: i5) {
			b.put(bi);
		}
		b.rewind();
		expression = new BinaryExpression();
//		System.out.println("READING NEW EXPRESSION*********************************************************");
//		System.out.println(code);
		expression.read(b);
		
		//System.out.println("EXP I1: " + i1);
		//System.out.println("EXP I2: " + i2);
		//System.out.println("EXP I4: " + i4);

	}

	public void write(ByteBuffer buffer) {
		buffer.putInt(i1);
		buffer.putInt(hackInt);
		buffer.putInt(i2);
		buffer.putInt(i3);
		buffer.putInt(numChars);
		//buffer.putInt(code.length());
		buffer.putInt(i4);
		// int numInts = (int)Math.ceil(numChars / 4.0);
		// buffer.put(BinaryTools.packString(code,numInts*4));
		buffer.put(code.getBytes());
//		if(i5.size() != expression.byteSize()) {
//			System.out.println("NOT EQUAL*************************************************************************************");
//			System.out.println("I5 SIZE:" + i5.size() + "  Expr Size:" + expression.byteSize() + "  Num Nodes:" + expression.numNodes() + 
//					"  NUM STR:" + expression.numStrings() + "  NUM STR CHARS:" + expression.numStringChars());
//			System.out.println(expression.printNodeSize());
//			System.out.println(code);
//		}
		expression.write(buffer);;
//		for (Byte i : i5) {
//			buffer.put(i);
//			//buffer.put((byte)1);
//		}
	}

	public int byteSize() {
		return 6 * 4 + code.getBytes().length + i5.size();
	}

	public int entryLength() {
		return entryLength;
	}

	public int index() {
		return index;
	}

	public String code() {
		return code;
	}
	
	public String summary() {
		StringBuilder summary = new StringBuilder();
		summary.append("EXPRESSION ENTRY SUMMARY" + "\n");
		summary.append("I1: " + i1 + "\n");
		summary.append("HACK: " + hackInt + "\n");
		summary.append("ENTRY LENGTH: " + entryLength + "\n");
		summary.append("I2: " + i2 + "\n");
		summary.append("I3: " + i3 + "\n");
		summary.append("NUM CHARS: " + numChars + "\n");
		summary.append("I4: " + i4 + "\n");
		summary.append("CODE:" + "\n");
		summary.append(code + "\n");
		summary.append("REMAINING BYTES: " + i5.size() + "\n");
		for(int i=0;i<i5.size();i++) {
			summary.append("I5-" + (i+1) + ": " + i5.get(i) + "\n");			
		}
		return summary.toString();
	}
	
	public String summaryProcessed() {
		StringBuilder summary = new StringBuilder();
		summary.append("EXPRESSION ENTRY SUMMARY" + "\n");
		summary.append("I1: " + i1 + "\n");
		summary.append("HACK: " + hackInt + "\n");
		summary.append("ENTRY LENGTH: " + entryLength + "\n");
		summary.append("I2: " + i2 + "\n");
		summary.append("I3: " + i3 + "\n");
		summary.append("NUM CHARS: " + numChars + "\n");
		summary.append("I4: " + i4 + "\n");
		summary.append("CODE:" + "\n");
		summary.append(code + "\n");
		summary.append("REMAINING BYTES: " + i5.size() + "\n");
		summary.append("BINARY EXPRESSION PROCESSED:" + "\n");
		summary.append(expression.summary());
		return summary.toString();
	}
	
	public void offsetIndex(int offset) {
		index += offset;
	}
	
	public void keywordAdded(int keyInd) {
		expression.keywordAdde(keyInd);
	}

}
