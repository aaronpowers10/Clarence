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

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class KeyReader {
	
	
	public static DOE2DataProperties read(String filePath)throws IOException{
		byte[] bytes = Files.readAllBytes(Paths.get(filePath));
		
		int record1Start = 10808 + 4;
		int record1End = record1Start + 44;
		ByteBuffer record1Buffer = ByteBuffer.wrap(Arrays.copyOfRange(bytes, record1Start, record1End));
		record1Buffer.order(ByteOrder.LITTLE_ENDIAN);
		LengthData lengthData = new LengthData(record1Buffer);
		
		int record2Start = record1End + 8;
		int record2End = record2Start + (lengthData.symbolTableLength()*16 + (lengthData.keywordTableLength())*16+
				lengthData.commandTableLength()*24)*4; 
		ByteBuffer record2Buffer = ByteBuffer.wrap(Arrays.copyOfRange(bytes, record2Start, record2End));
		record2Buffer.order(ByteOrder.LITTLE_ENDIAN);
		
		SymbolTable symbolTable = new SymbolTable(record2Buffer,lengthData.symbolTableLength());
		KeywordTable keywordTable = new KeywordTable(record2Buffer,lengthData.keywordTableLength());
		CommandTable commandTable = new CommandTable(record2Buffer,lengthData.commandTableLength(),lengthData.keywordStart());
		
		int record3Start = record2End + 8;
		int record3End = record3Start + (lengthData.defaultTableLength())*4;
		ByteBuffer record3Buffer = ByteBuffer.wrap(Arrays.copyOfRange(bytes, record3Start, record3End));
		record3Buffer.order(ByteOrder.LITTLE_ENDIAN);
		DefaultTable defaultTable = new DefaultTable(record3Buffer,commandTable);
			
		int record4Start = record3End + 8;
		int record4End = record4Start + (lengthData.expressionTableLength())*4;
		ByteBuffer record4Buffer = ByteBuffer.wrap(Arrays.copyOfRange(bytes, record4Start, record4End));
		record4Buffer.order(ByteOrder.LITTLE_ENDIAN);
		ExpressionTable expressionTable = new ExpressionTable(record4Buffer,lengthData.expressionTableLength());
		
		return new DOE2DataProperties(symbolTable,keywordTable,commandTable,defaultTable,expressionTable);

	}


}
