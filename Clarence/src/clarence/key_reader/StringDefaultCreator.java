package clarence.key_reader;

import java.util.ArrayList;

public class StringDefaultCreator implements DefaultCreator{
	
	public StringDefaultCreator() {
	}

	@Override
	public void addDefault(ArrayList<Integer> type, ArrayList<Integer> value) {
		type.add(1);
		value.add(DoubleToInt.convert(0));	
	}

}
