package clarence.key_reader;

import java.util.ArrayList;

public class CodewordDefaultCreator implements DefaultCreator{
	
	private int index;
	
	public CodewordDefaultCreator(int index) {
		this.index = index;
	}

	@Override
	public void addDefault(ArrayList<Integer> type, ArrayList<Integer> value) {
		type.add(1);
		value.add(index);
	}

}
