package clarence.key_reader;

public class KeywordEntryFactory {
	
	public static KeywordEntry createInteger(String name, String abbreviation, int length,int min, int max, int valPos) {
		return new KeywordEntry(name, abbreviation,6,length,min,max,valPos);
	}
	
	public static KeywordEntry createReal(String name, String abbreviation, int length,double min, double max, int valPos) {
		return new KeywordEntry(name, abbreviation,1,length,DoubleToInt.convert(min),DoubleToInt.convert(max),valPos);
	}
	
	public static KeywordEntry createCodeword(String name, String abbreviation, int length,int symInd, int valPos) {
		return new KeywordEntry(name, abbreviation,2,length,symInd,0,valPos);
	}
	
	public static KeywordEntry createString(String name, String abbreviation, int length, int valPos) {
		return new KeywordEntry(name, abbreviation,4,length,0,0,valPos);
	}
	
	public static KeywordEntry createObject(String name, String abbreviation, int length, int allowedCommand,int valPos) {
		return new KeywordEntry(name, abbreviation,3,length,allowedCommand,0,valPos);
	}

}
