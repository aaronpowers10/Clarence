package clarence.key_reader;

import java.util.ArrayList;

public class ObjectDefaultCreator implements DefaultCreator{
	
	public ObjectDefaultCreator() {
	}

	@Override
	public void addDefault(ArrayList<Integer> type, ArrayList<Integer> value) {
		type.add(1);
		value.add(-946345856);	
	}

}
