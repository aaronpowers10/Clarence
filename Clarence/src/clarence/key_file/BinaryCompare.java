package clarence.key_file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class BinaryCompare {
	
	
	public static void main(String[] args) throws IOException{
		byte[] b1 = Files.readAllBytes(Paths.get("C:\\Users\\cpoweraa\\Documents\\eQUEST 3-65-7175 Data\\DOE23\\BDLKEY.BIN"));
		byte[] b2 = Files.readAllBytes(Paths.get("C:\\eQuest MOD_001\\eQUEST 3-65-7175_001 Data\\DOE23\\BDLKEY.BIN"));
		for(int i=0;i<b1.length;i++) {
			if(b1[i]!=b2[i]) {
				System.out.println("NOT EQUAL AT " + i + " B1:" + b1[i] + " B2:" + b2[i]);
			}
		}
		System.out.println("ITS OVER!!");
	}

}
