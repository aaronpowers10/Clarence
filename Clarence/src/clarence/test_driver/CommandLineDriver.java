package clarence.test_driver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import clarence.key_file.KeywordFile;
import clarence.key_reader.CommandEntry;
import clarence.key_reader.DefaultType;
import clarence.key_reader.IntToFloat;
import clarence.key_reader.KeywordEntry;
import clarence.key_reader.KeywordType;
import clarence.key_reader.RealDefaultCreator;

public class CommandLineDriver {

	private KeywordFile file;

	public static void main(String[] args) throws IOException {
		CommandLineDriver driver = new CommandLineDriver();
		//compare();
	}

	private static void compare() throws IOException {
		System.out.println("**********");
		byte[] bytes1 = Files.readAllBytes(Paths.get("C:\\doe22\\EXE48y\\BDLKEY.BIN"));
		byte[] bytes2 = Files.readAllBytes(Paths.get("C:\\doe22\\EXE48y\\BDLKEY_OUT.BIN"));
		//System.out.println("SIZES: " + bytes1.length + " " + bytes2.length);
		int notEq = 511504;
		int j = 0;
		for (int i = 0; i < bytes1.length; i++) {
			//if(i > notEq-40 && i < notEq + 100)
			//	System.out.println("I:" + i + " Byte1:" + bytes1[i] + " Byte2:" + bytes2[i]);
			if (bytes1[i] != bytes2[i]) {
				if (j == 0) {
					System.out.println("************************************************************************");
					System.out.println("NOT EQUAL AT: " + i);
					System.out.println("************************************************************************");
					
					j = 1;
				}
			}
		}
	}

	public CommandLineDriver() throws IOException {
		System.out.println("LOADING KEYWORD FILE");
		
		//file = new KeywordFile("C:\\doe22_versions\\D22NT44c3\\doe22\\exent\\BDLKEY.BIN");
		//file = new KeywordFile("C:\\doe22_versions\\D22NT45k\\doe22\\exent\\BDLKEY.BIN");
		//file = new KeywordFile("C:\\doe22_versions\\D22NT46a\\doe22\\exent\\BDLKEY.BIN");
		//file = new KeywordFile("C:\\doe22_versions\\D22NT47d\\doe22\\exent\\BDLKEY.BIN");
		//file = new KeywordFile("C:\\doe22_versions\\D22s48zr52n\\doe22\\EXE48z\\BDLKEY.BIN");
		//file = new KeywordFile("C:\\doe22\\EXE48y\\BDLKEY.BIN");
		//file = new KeywordFile("C:\\doe22\\EXE48y\\BDLKEY_OUT.BIN");
		file = new KeywordFile("C:\\Users\\cpoweraa\\Documents\\eQUEST 3-65-7175 Data\\DOE-2\\Arch\\BDLKEY.BIN");
		
		//file = new KeywordFile("C:\\doe22_versions\\D23s50e\\doe23\\EXE50e\\BDLKEY.BIN");
		System.out.println("KEYWORD FILE READ COMPLETE");
		System.out.println();
		//file.printRecord1();

		options1();
	}
	
	private void options1() {
		System.out.println("SELECT AN OPTION");
		System.out.println("1. PRINT LENGTH DATA SUMMARY");
		System.out.println("2. PRINT SYMBOL TABLE SUMMARY");
		System.out.println("3. PRINT KEYWORD TABLE SUMMARY");
		System.out.println("4. PRINT COMMAND TABLE SUMMARY");
		System.out.println("5. PRINT DEFAULT TABLE SUMMARY");
		System.out.println("6. PRINT EXPRESSION TABLE SUMMARY");
		System.out.println("7. PRINT EXPRESSION ENTRY RAW SUMMARY BY INDEX");
		System.out.println("8. PRINT EXPRESSION ENTRY RAW SUMMARY BY MEMORY POINTER");
		System.out.println("9. PRINT EXPRESSION ENTRY PROCESSED SUMMARY BY INDEX");
		System.out.println("10. PRINT EXPRESSION ENTRY PROCESSED SUMMARY BY MEMORY POINTER");
		System.out.println("11. SELECT COMMAND");
		System.out.println("12. SAVE FILE");
		Scanner in = new Scanner(System.in);
		int option = in.nextInt();

		if (option == 1) {
			this.printLengthDataSummary();
			options1();
		} else if (option == 2) {
			this.printSymbolTableSummary();
			options1();
		} else if (option == 3) {
			this.printKeywordTableSummary();
			options1();
		}else if (option == 4) {
			this.printCommandTableSummary();
			options1();
		}else if (option == 5) {
			this.printDefaultTableSummary();
			options1();
		}else if (option == 6) {
			this.printExpressionTableSummary();
			options1();
		}else if (option == 7) {
			this.printExpressionEntrySummary();
			options1();
		}else if (option == 8) {
			this.printExpressionEntrySummaryByPointer();
			options1();
		}else if (option == 9) {
			this.printExpressionEntrySummaryProcessed();
			options1();
		}else if (option == 10) {
			this.printExpressionEntrySummaryProcessedByPointer();
			options1();
		}else if (option == 11) {
			viewEditCommand();
		}else if (option == 12) {
			file.refresh();
			saveFile();
			options1();
		}
		in.close();
	}

	private void saveFile() {
		try {
			file.write("C:\\doe22\\EXE48y\\BDLKEY_OUT.BIN");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void printLengthDataSummary() {
		System.out.println(file.lengthDataInfo());
		System.out.println("TOTAL SIZE ON DISK " + file.byteSize() + " BYTES");
	}
	
	private void printSymbolTableSummary() {
		System.out.println(file.symbolTableSummary());
	}
	
	private void printKeywordTableSummary() {
		System.out.println(file.keywordTableSummary());
	}
	
	private void printCommandTableSummary() {
		System.out.println(file.commandTableSummary());
	}
	
	private void printDefaultTableSummary() {
		System.out.println(file.defaultTableSummary());
	}
	
	private void printExpressionTableSummary() {
		System.out.println(file.expressionTableSummary());
	}
	
	private void printExpressionEntrySummary() {
		System.out.println("SELECT EXPRESSION NUMBER");
		Scanner in = new Scanner(System.in);
		int option = in.nextInt();
		System.out.println(file.expressionSummary(option -1));
	}
	
	private void printExpressionEntrySummaryByPointer() {
		System.out.println("SELECT EXPRESSION MEMORY POINTER");
		Scanner in = new Scanner(System.in);
		int option = in.nextInt();
		System.out.println(file.expressionSummaryByPointer(option));
	}
	
	private void printExpressionEntrySummaryProcessed() {
		System.out.println("SELECT EXPRESSION NUMBER");
		Scanner in = new Scanner(System.in);
		int option = in.nextInt();
		System.out.println(file.expressionSummaryProcessed(option -1));
	}
	
	private void printExpressionEntrySummaryProcessedByPointer() {
		System.out.println("SELECT EXPRESSION MEMORY POINTER");
		Scanner in = new Scanner(System.in);
		int option = in.nextInt();
		System.out.println(file.expressionSummaryProcessedByPointer(option));
	}

	private void viewEditCommand() {
		System.out.println("SELECT COMMAND NUMBER");
		Scanner in = new Scanner(System.in);
		int option = in.nextInt();
		loadCommand(option - 1);
	}

	private void loadCommand(int commandIndex) {
		CommandEntry command = file.getCommand(commandIndex);
		System.out.println("SELECT OPTION FOR COMMAND " + command.name());
		System.out.println("1. PRINT COMMAND SUMMARY");
		System.out.println("2. LIST KEYWORDS");
		System.out.println("3. VIEW KEYWORD FORMATTED");
		System.out.println("4. VIEW KEYWORD RAW");
		System.out.println("5. ADD KEYWORD");
		System.out.println("6. EDIT KEYWORD");
		System.out.println("7. BACK");
		Scanner in = new Scanner(System.in);
		int option = in.nextInt();
		System.out.println();
		if (option == 1) {
			printCommandSummary(commandIndex);
			loadCommand(commandIndex);
		} else if (option == 2) {
			printKeywordNames(commandIndex);
			loadCommand(commandIndex);
		} else if (option == 3) {
			viewKeyword(commandIndex);
			loadCommand(commandIndex);
		} else if (option == 4) {
			viewKeywordRaw(commandIndex);
			loadCommand(commandIndex);
		}else if (option == 5) {
			addKeyword(commandIndex);
			loadCommand(commandIndex);
		}else if (option == 6) {
			editKeyword(commandIndex);
			loadCommand(commandIndex);
		}else if (option == 7) {
			options1();
		}else {
			options1();
		}
		in.close();

	}
	
	private void printCommandSummary(int commandIndex) {
		System.out.println(file.commandSummary(commandIndex));
	}

	private void printKeywordNames(int commandIndex) {
		System.out.println("KEYWORD NAMES");
		ArrayList<String> names = file.keywordNames(commandIndex);
		for (int i = 0; i < names.size(); i++) {
			System.out.println((i + 1) + ": " + names.get(i));
		}
		System.out.println();
	}
	
	private void viewKeywordRaw(int commandIndex) {
		System.out.println("SELECT KEYWORD NUMBER");
		Scanner in = new Scanner(System.in);
		int option = in.nextInt();
		loadKeywordRaw(commandIndex, option - 1);
		System.out.println();
	}
	
	private void loadKeywordRaw(int commandIndex, int keywordIndex) {
		System.out.println(file.getKeyword(commandIndex,keywordIndex).getRawInfo());
		int defaultValueInteger = file.defaultValueInt(commandIndex, keywordIndex);
		int defaultTypeInteger = file.defaultTypeInt(commandIndex,keywordIndex);
		System.out.println("DEFAULT TYPE: "  + defaultTypeInteger);
		System.out.println("DEFAULT VALUE INT: " + defaultValueInteger);
		System.out.println("DEFAULT VALUE FLOAT: " + IntToFloat.convert(defaultValueInteger));

	}

	private void viewKeyword(int commandIndex) {
		System.out.println("SELECT KEYWORD NUMBER");
		Scanner in = new Scanner(System.in);
		int option = in.nextInt();
		loadKeyword(commandIndex, option - 1);
		System.out.println();
	}

	private void loadKeyword(int commandIndex, int keywordIndex) {
		KeywordEntry keyword = file.getKeyword(commandIndex, keywordIndex);
		System.out.println("DETAILS FOR KEYWORD " + keyword.name());
		System.out.println("NUMBER OF ELEMENTS: " + keyword.length());
		KeywordType type = keyword.keywordType();
		if (type == KeywordType.INTEGER) {
			showIntegerKeyword(commandIndex, keywordIndex);
		} else if (type == KeywordType.REAL) {
			showRealKeyword(commandIndex, keywordIndex);
		} else if (type == KeywordType.STRING) {
			showStringKeyword(commandIndex, keywordIndex);
		} else if (type == KeywordType.OBJECT) {
			showObjectKeyword(commandIndex, keywordIndex);
		} else if (type == KeywordType.CODEWORD) {
			showCodewordKeyword(commandIndex, keywordIndex);
		}
	}

	private void showIntegerKeyword(int commandIndex, int keywordIndex) {
		KeywordEntry keyword = file.getKeyword(commandIndex, keywordIndex);
		System.out.println("TYPE: INTEGER");
		System.out.println("MIN: " + keyword.minAsInt());
		System.out.println("MAX: " + keyword.maxAsInt());

		DefaultType defaultType = file.defaultType(commandIndex, keywordIndex);
		int defaultValueInteger = file.defaultValueInt(commandIndex, keywordIndex);
		System.out.println("DEFAULT TYPE: " + defaultType);
		if (defaultType == DefaultType.VALUE) {
			System.out.println("DEFAULT VALUE: " + (int) IntToFloat.convert(defaultValueInteger));
		} else if (defaultType == DefaultType.EXPRESSION) {
			System.out.println("DEFAULT EXPRESSION: " + file.defaultExpression(commandIndex, keywordIndex));
		}
	}

	private void showRealKeyword(int commandIndex, int keywordIndex) {
		KeywordEntry keyword = file.getKeyword(commandIndex, keywordIndex);
		System.out.println("TYPE: REAL");
		System.out.println("MIN: " + keyword.minAsFloat());
		System.out.println("MAX: " + keyword.maxAsFloat());

		DefaultType defaultType = file.defaultType(commandIndex, keywordIndex);
		int defaultValueInteger = file.defaultValueInt(commandIndex, keywordIndex);
		System.out.println("DEFAULT TYPE: " + defaultType);
		if (defaultType == DefaultType.VALUE) {
			System.out.println("DEFAULT VALUE: " + IntToFloat.convert(defaultValueInteger));
		} else if (defaultType == DefaultType.EXPRESSION) {
			System.out.println("DEFAULT EXPRESSION: " + file.defaultExpression(commandIndex, keywordIndex));
		}
	}

	private void showStringKeyword(int commandIndex, int keywordIndex) {
		System.out.println("TYPE: STRING");

		DefaultType defaultType = file.defaultType(commandIndex, keywordIndex);
		int defaultValueInteger = file.defaultValueInt(commandIndex, keywordIndex);
		System.out.println("DEFAULT TYPE: " + defaultType);
		if (defaultType == DefaultType.VALUE) {
			System.out.println("DEFAULT VALUE: " + defaultValueInteger);
		} else if (defaultType == DefaultType.EXPRESSION) {
			System.out.println("DEFAULT EXPRESSION: " + file.defaultExpression(commandIndex, keywordIndex));
		}

	}

	private void showObjectKeyword(int commandIndex, int keywordIndex) {
		int objectType = file.getKeyword(commandIndex, keywordIndex).min();
		CommandEntry commandEntry = file.getCommandOfType(objectType);
		if (commandEntry == null) {
			showCodewordKeyword(commandIndex, keywordIndex);
		} else {

			System.out.println("TYPE: OBJECT");
			System.out.println("ALLOWED COMMAND: " + file.allowedCommand(commandIndex, keywordIndex));
			System.out.println("ALLOWED TYPES: ");
			ArrayList<String> allowedTypes = file.allowedTypes(commandIndex, keywordIndex);
			for (int i = 0; i < allowedTypes.size(); i++) {
				System.out.println((i + 1) + ": " + allowedTypes.get(i));
			}

			DefaultType defaultType = file.defaultType(commandIndex, keywordIndex);
			int defaultValueInteger = file.defaultValueInt(commandIndex, keywordIndex);
			System.out.println("DEFAULT TYPE: " + defaultType);
			if (defaultType == DefaultType.VALUE) {
				System.out.println("DEFAULT VALUE: " + defaultValueInteger);
			} else if (defaultType == DefaultType.EXPRESSION) {
				System.out.println("DEFAULT EXPRESSION: " + file.defaultExpression(commandIndex, keywordIndex));
			}
		}

	}

	private void showCodewordKeyword(int commandIndex, int keywordIndex) {
		System.out.println("TYPE: CODEWORD");

		ArrayList<String> allowedVals = file.allowedValues(commandIndex, keywordIndex);
		System.out.println("NUMBER OF CHOICES: " + allowedVals.size());
		System.out.println("CHOICES:");
		for (int i = 0; i < allowedVals.size(); i++) {
			System.out.println((i + 1) + ": " + allowedVals.get(i));
		}

		DefaultType defaultType = file.defaultType(commandIndex, keywordIndex);
		int defaultValueInteger = file.defaultValueInt(commandIndex, keywordIndex);
		System.out.println("DEFAULT TYPE: " + defaultType);
		if (defaultType == DefaultType.VALUE) {
			System.out.println("DEFAULT VALUE: " + file.symbol(defaultValueInteger));
		} else if (defaultType == DefaultType.EXPRESSION) {
			System.out.println("DEFAULT EXPRESSION: " + file.defaultExpression(commandIndex, keywordIndex));
		}
	}
	
	private void addKeyword(int commandIndex) {
		System.out.println("SELECT KEYWORD TYPE");
		System.out.println("1: INTEGER");
		System.out.println("2: REAL");
		System.out.println("3: CODEWORD");
		Scanner in = new Scanner(System.in);
		int option = in.nextInt();
		
		
	}
	
	private void addRealKeyword(int commandIndex) {
		CommandEntry command = file.getCommand(commandIndex);
		System.out.println("CREATING NEW REAL TYPE KEYWORD FOR " +command.name());
		System.out.println("KEYWORD NAME:");
		Scanner in = new Scanner(System.in);
		String name = in.nextLine().trim();
		System.out.println("KEYWORD ABBREVIATION:");
		String abbreviation = in.nextLine().trim();
		System.out.println("DEFAULT VALUE:");
		double defVal = in.nextDouble();
		System.out.println("MIN:");
		double min = in.nextDouble();
		System.out.println("MAX:");
		double max = in.nextDouble();
		KeywordEntry lastKey = file.getKeyword(commandIndex, command.endKeys() - command.startKeys() -1);
		int valPos = lastKey.valPos() + lastKey.length();
		System.out.println("LAST KEY " + lastKey.name());
		System.out.println("VAL POS " + valPos);
		System.out.println("START KEYS " + command.startKeys());
		System.out.println("END KEYS " + command.endKeys());
		//KeywordEntry keyword = new KeywordEntry(name,abbreviation,min,max,valPos);
		RealDefaultCreator defaultCreator = new RealDefaultCreator(defVal);
		//file.addKeyword(command,keyword,defaultCreator);
	}
	
	public void editKeyword(int commandIndex) {

		System.out.println("SELECT KEYWORD NUMBER TO EDIT");
		Scanner in = new Scanner(System.in);
		int keywordIndex = in.nextInt()-1;
		
		KeywordEntry keyword = file.getKeyword(commandIndex,keywordIndex);
		System.out.println("EDITING KEYWORD " + keyword.name());
		
		DefaultType defaultType = file.defaultType(commandIndex, keywordIndex);
		int defaultValueInteger = file.defaultValueInt(commandIndex, keywordIndex);
		System.out.println("CURRENT DEFAULT TYPE: " + defaultType);
		if (defaultType == DefaultType.VALUE) {
			System.out.println("CURRENT DEFAULT VALUE: " + IntToFloat.convert(defaultValueInteger));
		} else if (defaultType == DefaultType.EXPRESSION) {
			System.out.println("CURRENT DEFAULT EXPRESSION: " + file.defaultExpression(commandIndex, keywordIndex));
		}
		
		System.out.println("ENTER NEW DEFAULT: ");
		double defVal = in.nextDouble();
		file.setDefaultValue(commandIndex, keywordIndex, defVal);
		System.out.println("NEW DEFAULT SET TO: ");
		System.out.println(file.defaultTypeInt(commandIndex,keywordIndex));
		
	}

}
