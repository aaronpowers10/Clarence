package clarence.key_expression;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class FunctionNode implements ExpressionNode {

	private int retType;
	private int code;
	private int numArgs;
	private ArrayList<ExpressionNode> args;
	private ArrayList<Byte> remBytes;
	private boolean lastNotInt;

	public FunctionNode() {
		args = new ArrayList<ExpressionNode>();
		remBytes = new ArrayList<Byte>();
		lastNotInt = false;
	}

	public Funcs function() {
		if (code == 2) {
			return Funcs.POW;
		} else if (code == 3) {
			return Funcs.ABS;
		} else if (code == 6) {
			return Funcs.MIN;
		} else if (code == 7) {
			return Funcs.MAX;
		} else if (code == 9) {
			return Funcs.INT;
		} else if (code == 17) {
			return Funcs.SQRT;
		} else if (code == 20) {
			return Funcs.GLOBAL;
		} else if (code == 21) {
			return Funcs.LOCAL;
		} else if (code == 23) {
			return Funcs.PARENT;
		} else if (code == 24) {
			return Funcs.LOCALREF;
		} else if (code == 27) {
			return Funcs.SYMINDEX;
		} else if (code == 28) {
			return Funcs.SIT;
		} else if (code == 29) {
			return Funcs.SYMVALUE;
		} else if (code == 30) {
			return Funcs.CALCAZ;
		} else if (code == 31) {
			return Funcs.RESVALUE;
		} else if (code == 32) {
			return Funcs.POLYAREA;
		} else if (code == 34) {
			return Funcs.NC;
		} else if (code == 35) {
			return Funcs.CALCW;
		} else if (code == 36) {
			return Funcs.PARENTPV;
		} else if (code == 38) {
			return Funcs.MIRRORPOLYGON;
		} else if (code == 39) {
			return Funcs.PARENT2;
		} else if (code == 41) {
			return Funcs.PARENT3;
		} else if (code == 42) {
			return Funcs.MULT;
		} else if (code == 43) {
			return Funcs.ADD;
		} else if (code == 44) {
			return Funcs.CALCDEGN;
		} else if (code == 45) {
			return Funcs.SUB;
		} else if (code == 47) {
			return Funcs.DIV;
		} else if (code == 48) {
			return Funcs.LOCALSTATUS;
		} else if (code == 266) {
			return Funcs.OR;
		} else if (code == 267) {
			return Funcs.AND;
		} else if (code == 268) {
			return Funcs.EQ;
		} else if (code == 269) {
			return Funcs.NE;
		} else if (code == 270) {
			return Funcs.LT;
		} else if (code == 271) {
			return Funcs.GT;
		} else if (code == 272) {
			return Funcs.LE;
		} else if (code == 273) {
			return Funcs.GE;
		} else if (code == 274) {
			return Funcs.EXPONENTIAL;
		} else if (code == 277) {
			return Funcs.UNMINUS;
		} else {
			return Funcs.UNKNOWN;
		}
	}

	public int numArgs() {
		return numArgs;
	}

	public int retType() {
		return retType;
	}

	@Override
	public String printString() {
		if (function() == Funcs.UNKNOWN) {
			return "Unknown Func: " + code;
		} else {
			if (retType == 3) {
				return function().toString() + " NUM-ARGS:" + numArgs;
			} else {
				return function().toString();
			}
		}
	}

	@Override
	public void read(ByteBuffer buffer, SyntaxTree tree) {

		retType = buffer.getInt();
		code = buffer.getInt();
		try {
			numArgs = buffer.getInt();
		} catch (BufferUnderflowException e) {
			numArgs = 1;
			lastNotInt = true;
			while (buffer.hasRemaining()) {
				remBytes.add(buffer.get());
			}
		}
		
		if(function() == Funcs.GLOBAL) {
			numArgs = 2;
		}

		if (retType == 3) {
			for (int i = 0; i < numArgs; i++) {
				args.add(tree.peek(-i - 1));
			}
		}

//		if (function() == Funcs.LOCALREF) {
//			System.out.println("LR " + numArgs);
//			for (int i = 0; i < numArgs; i++) {
//				if (args.get(i) instanceof FloatNode) {
//					System.out.println("ARG " + (i + 1) + ": " + ((FloatNode) args.get(i)).value());
//				} else {
//					System.out.println("ARG " + (i + 1) + ((StringNode) args.get(i)).string());
//				}
//			}
//		}

	}

	public void setArgs(ArrayList<ExpressionNode> args) {
		this.args = args;
	}

	@Override
	public void write(ByteBuffer buffer) {
		buffer.put(((byte) 7));
		buffer.putInt(retType);
		buffer.putInt(code);
		if (lastNotInt) {
			for (Byte b : remBytes) {
				buffer.put(b);
			}
		} else {
			buffer.putInt(numArgs);
		}

	}

	@Override
	public int byteSize() {
		if (lastNotInt) {
			return 1 + 4 * 2 + remBytes.size();
		} else {
			return 1 + 4 * 3;
		}
	}

	public void keywordAdded(int keyInd) {
		if (function() == Funcs.LOCAL) {
			if (args.get(0) instanceof FloatNode) {
				FloatNode node = (FloatNode) args.get(0);
				if (node.value() > keyInd) {
					node.setValue(node.value() + 1);
				}
			}
		} else if (function() == Funcs.PARENT) {
			if (args.get(0) instanceof FloatNode) {
				FloatNode node = (FloatNode) args.get(0);
				if (node.value() > keyInd) {
					node.setValue(node.value() + 1);
				}
			}
		} else if (function() == Funcs.PARENT2) {
			if (args.get(0) instanceof FloatNode) {
				FloatNode node = (FloatNode) args.get(0);
				if (node.value() > keyInd) {
					node.setValue(node.value() + 1);
				}
			}

		} else if (function() == Funcs.PARENT3) {
			if (args.get(0) instanceof FloatNode) {
				FloatNode node = (FloatNode) args.get(0);
				if (node.value() > keyInd) {
					node.setValue(node.value() + 1);
				}
			}
		}
		if (function() == Funcs.GLOBAL) {
			//if (numArgs > 1) {
				if (args.get(1) instanceof FloatNode) {
					FloatNode node = (FloatNode) args.get(1);
					if (node.value() > keyInd) {
						node.setValue(node.value() + 1);
					}
				}
//			} else {
//				if (args.get(0) instanceof FloatNode) {
//					FloatNode node = (FloatNode) args.get(0);
//					if (node.value() > keyInd) {
//						node.setValue(node.value() + 1);
//					}
//				}
//			}
		} else if (function() == Funcs.LOCALREF) {
			if(numArgs == 1) {
				if (args.get(0) instanceof FloatNode) {
					FloatNode node = (FloatNode) args.get(0);
					if (node.value() > keyInd) {
						node.setValue(node.value() + 1);
					}
				}
			} else if (numArgs == 2) {
				if (args.get(0) instanceof FloatNode) {
					FloatNode node = (FloatNode) args.get(0);
					if (node.value() > keyInd) {
						node.setValue(node.value() + 1);
					}
				}
				
				if (args.get(1) instanceof FloatNode) {
					FloatNode node = (FloatNode) args.get(1);
					if (node.value() > keyInd) {
						node.setValue(node.value() + 1);
					}
				}
			} else {
				if (args.get(0) instanceof FloatNode) {
					FloatNode node = (FloatNode) args.get(0);
					if (node.value() > keyInd) {
						node.setValue(node.value() + 1);
					}
				}
				
				if (args.get(2) instanceof FloatNode) {
					FloatNode node = (FloatNode) args.get(2);
					if (node.value() > keyInd) {
						node.setValue(node.value() + 1);
					}
				}
			}
		}

	}

}
