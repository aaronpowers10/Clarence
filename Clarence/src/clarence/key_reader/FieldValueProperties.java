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

public abstract class FieldValueProperties {

	private DefaultType defaultType;
	private int defaultValueInteger;
	private String defaultExpression;

	public FieldValueProperties() {
		defaultType = DefaultType.NO_DEFAULT;
	}

	public void setDefaults(int defaultTypeInt, int defaultValueInteger, DOE2Tables doe2Tables) {
		this.defaultValueInteger = defaultValueInteger;
		if (defaultTypeInt == 1) {
			defaultType = DefaultType.VALUE;
			Float defaultValueFloat = IntToFloat.convert(defaultValueInteger);
			if (defaultValueFloat == -99999.0) {
				defaultType = DefaultType.REQUIRED;
			} else if (defaultValueFloat == -88888.0) {
				defaultType = DefaultType.UNUSED;
			} else if (defaultValueFloat == -77777.0) {
				defaultType = DefaultType.NO_DEFAULT;
			} else if (defaultValueFloat == -66666.0) {
				defaultType = DefaultType.UNFILLED;
			}
		} else {
			defaultType = DefaultType.EXPRESSION;
			defaultExpression = doe2Tables.expressionEntry(defaultValueInteger).code();
		}
	}

	public abstract String fieldType();

	public abstract String write();

	public DefaultType defaultType() {
		return defaultType;
	}

	protected int defaultValueInteger() {
		return defaultValueInteger;
	}

	public String defaultExpression() {
		return defaultExpression;
	}

}
