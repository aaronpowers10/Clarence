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

public class BinaryString {
	
	private ArrayList<Integer> bits;
	
	public BinaryString(){
		bits = new ArrayList<Integer>();
	}
	
	public BinaryString(int decimal, int size){
		bits = new ArrayList<Integer>();
		populateFromDecimal(decimal,size);
	}
	
	private BinaryString(ArrayList<Integer> bits){
		this.bits = bits;
	}
	
	private void populateFromDecimal(int decimal, int size){
		ArrayList<Integer> quotient = new ArrayList<Integer>();
		quotient.add(decimal);
		for(int i=0;i<size;i++){
			bits.add(quotient.get(quotient.size()-1)%2);
			quotient.add(quotient.get(quotient.size()-1)/2);
		}
	}
	
	public BinaryString substring(int fromIndex, int toIndex){
		return new BinaryString(new ArrayList<Integer>((bits.subList(fromIndex, toIndex))));
	}
	
	public int toDecimal(){
		int decimal = 0;
		for(int i=0;i<bits.size();i++){
			decimal += Math.pow(2, i)*bits.get(i);
		}
		return decimal;
	}
	
	public int size(){
		return bits.size();
	}
	
	public int get(int index){
		return bits.get(index);
	}
	
	public void append(BinaryString binaryString){
		for(int i=0;i<binaryString.size();i++){
			bits.add(binaryString.get(i));
		}
	}
	
	public void add(int bit){
		bits.add(bit);
	}
	
	public static void main(String[] args){
		BinaryString binaryString = new BinaryString(492830720,32).substring(20, 32);
		System.out.println(binaryString.toDecimal());
		BinaryString binaryString2 = new BinaryString(493879296,32).substring(20, 32);
		System.out.println(binaryString2.toDecimal());
		
		BinaryString binaryString3 = new BinaryString(1684234849,32).substring(0, 8);
		System.out.println(binaryString3.toDecimal());
//		BinaryString binaryString = new BinaryString(514302436,32).substring(20, 32);
//		System.out.println(binaryString.toDecimal());
//		BinaryString binaryString = new BinaryString(514302436,32).substring(20, 32);
//		System.out.println(binaryString.toDecimal());
//		BinaryString binaryString2 = new BinaryString(514302436,32).substring(10, 20);
//		System.out.println(binaryString2.toDecimal());
//		BinaryString binaryString3 = new BinaryString(514302436,32).substring(0, 10);
//		System.out.println(binaryString3.toDecimal());
//		BinaryString binaryString = new BinaryString(507510784,32).substring(20, 32);
//		System.out.println(binaryString.toDecimal());
	}
	

}
