package clarence.key_expression;

import java.nio.ByteBuffer;

public class BinaryExpression {
	int i1;
	int numNodes;
	private SyntaxTree exprTree;
	
	public void read(ByteBuffer buffer) {
		exprTree = new SyntaxTree();
		i1 = buffer.getInt();
		numNodes = buffer.getInt();
		
		for(int i=0;i<numNodes;i++) {
			ExpressionNode node = null;
			byte b = buffer.get();
			if(b == (byte)1) {
				node = new FloatNode();
			} else if(b == (byte)2) {
				node = new StringNode();
			} else if(b == (byte)4) {
				node = new ReservedWordNode();
			} else if(b == (byte)7) {
				node = new FunctionNode();
			} else if(b == (byte)8) {
				node = new ThenNode();
			} else if(b == (byte)9) {
				node = new ElseNode();
			} else if(b == (byte)10) {
				node = new EndIfNode();
			} else if(b == (byte)11) {
				node = new SwitchNode();
			} else if(b == (byte)12) {
				node = new DefaultNode();
			} else if(b == (byte)13) {
				node = new CaseNode();
			} else if(b == (byte)14) {
				node = new EndSwitchNode();
			} else {
				System.out.println("UNKNOWN BYTE CODE " + b);
			}
			node.read(buffer,exprTree);
			//System.out.println("NODE " + (i+1) + " of " + numNodes + ": " + node.printString());
			exprTree.addNode(node);
		}
		
		while(buffer.hasRemaining()) {
			System.out.println("HAS REMAINING");
			System.out.println(buffer.get());
		}
		
		if(numNodes != exprTree.size()) {
			System.out.println("NODE SIZES NOT EQUAL " + numNodes + "  " + exprTree.size());
		}
		
		//root = new ExpressionTerm();
		//root.proc(exprData);
		//exprData.rewind();
		//StringBuilder sb = new StringBuilder();
		//root.traverse(sb);
		
	}
	
	public void write(ByteBuffer buffer) {
		buffer.putInt(i1);
		buffer.putInt(numNodes);
		exprTree.write(buffer);
	}
	
	public int byteSize() {
		return 4*2 + exprTree.byteSize();
	}
	
	public int numNodes() {
		return numNodes;
	}
	
	public int numStrings() {
		return exprTree.numStrings();
	}
	
	public int numStringChars() {
		return exprTree.numStringChars();
	}
	
	public String printNodeSize() {
		String nodeSize = "PRINTING NODE SIZES" + System.lineSeparator();
		
		nodeSize = nodeSize + exprTree.printNodeSizes() ;
		return nodeSize;
	}
	
	public String summary() {
		StringBuilder summary = new StringBuilder();
		summary.append("I1: " + i1 + System.lineSeparator());
		summary.append("NUM-NODES: " + numNodes+ System.lineSeparator());
		summary.append(exprTree.summary());
		return summary.toString();
	}
	
	public void keywordAdde(int keyInd) {
		exprTree.keywordAdded(keyInd);
	}

}
