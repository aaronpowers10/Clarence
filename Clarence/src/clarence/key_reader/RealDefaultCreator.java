package clarence.key_reader;

import java.util.ArrayList;

public class RealDefaultCreator implements DefaultCreator{
	
	private double value;
	
	public RealDefaultCreator(double value) {
		this.value = value;
	}

	@Override
	public void addDefault(ArrayList<Integer> type, ArrayList<Integer> value) {
		type.add(1);
		value.add(DoubleToInt.convert(this.value));	
	}

}
