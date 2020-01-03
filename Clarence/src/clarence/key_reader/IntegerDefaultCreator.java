package clarence.key_reader;

import java.util.ArrayList;

public class IntegerDefaultCreator implements DefaultCreator{
	
	private int value;
	
	public IntegerDefaultCreator(int value) {
		this.value = value;
	}

	@Override
	public void addDefault(ArrayList<Integer> type, ArrayList<Integer> value) {
		type.add(1);
		value.add(this.value);	
	}

}
