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

public class RealFieldProperties extends FieldValueProperties {

	private float min;
	private float max;

	public RealFieldProperties(float min, float max, int defaultType, int defaultValue,
			ExpressionTable expressionTable) {
		super(defaultType,defaultValue,expressionTable);
		this.min = min;
		this.max = max;
	}

	public double min() {
		return min;
	}

	public double max() {
		return max;
	}
	
	public float defaultValue(){
		return IntToFloat.convert(defaultValueInteger());
	}

	@Override
	public String fieldType() {
		return "REAL";
	}

	@Override
	public String write() {
		String output = "";
		output = output + "MIN " + min + System.lineSeparator();
		output = output + "MAX " + max + System.lineSeparator();
		output = output + "DEFAULT-TYPE " + defaultType() + System.lineSeparator();
		if (defaultType() == DefaultType.VALUE) {
			output = output + "DEFAULT-VALUE " + defaultValue() + System.lineSeparator();
		} else if (defaultType() == DefaultType.EXPRESSION) {
			output = output + "{" + defaultExpression() + "}" + System.lineSeparator();
		}
		return output;
	}

}
