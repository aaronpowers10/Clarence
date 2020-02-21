package clarence.key_reader;

import java.util.ArrayList;

public class KeywordEntryFactory {
	
	public static KeywordEntry createInteger(String name, String abbreviation, int length,int min, int max, int valPos) {
		return new KeywordEntry(name, abbreviation,6,length,min,max,valPos);
	}
	
	public static KeywordEntry createReal(String name, String abbreviation, int length,double min, double max, int valPos) {
		return new KeywordEntry(name, abbreviation,1,length,DoubleToInt.convert(min),DoubleToInt.convert(max),valPos);
	}
	
	public static KeywordEntry createReal(String name, String abbreviation, int length,double min, double max, int valPos,int units) {
		return new KeywordEntry(name, abbreviation,1,length,DoubleToInt.convert(min),DoubleToInt.convert(max),valPos,units);
	}
	
	public static KeywordEntry createCodeword(String name, String abbreviation, int length,int symInd, int valPos) {
		return new KeywordEntry(name, abbreviation,2,length,symInd,0,valPos);
	}
	
	public static KeywordEntry createString(String name, String abbreviation, int length, int valPos) {
		return new KeywordEntry(name, abbreviation,4,length,length,0,valPos);
	}
	
	public static KeywordEntry createObject(String name, String abbreviation, int length, int allowedCommand,int valPos) {
		return new KeywordEntry(name, abbreviation,3,length,allowedCommand,0,valPos);
	}
	
	public static KeywordEntry createObject(String name, String abbreviation, int length, int allowedCommand,int valPos, ArrayList<Integer> allowedTypes) {
		int i1 = 0;
		int i2 = 0;
		int i3 = 0;
		
		if(allowedTypes.size() > 0) {
			i1 = allowedTypes.get(0);
		}
		
		if(allowedTypes.size() > 1) {
			i2 = allowedTypes.get(1);
		}
		
		if(allowedTypes.size() > 2) {
			i3 = allowedTypes.get(2);
		}
		
		
		BinaryString s1 = new BinaryString(i1,12);
		BinaryString s2 = new BinaryString(i2,10);
		BinaryString s3 = new BinaryString(i3,10);
		
		BinaryString s4 = new BinaryString();
		s4.append(s3);
		s4.append(s2);
		s4.append(s1);
		
		int typeCode = s4.toDecimal();
		if(allowedTypes.size() == 0) {
			typeCode = 0;
		}
		KeywordEntry keyEntry = new KeywordEntry(name, abbreviation,3,length,allowedCommand,0,valPos);
		keyEntry.setTypeCode(typeCode);
		
		return keyEntry;
	}

}
