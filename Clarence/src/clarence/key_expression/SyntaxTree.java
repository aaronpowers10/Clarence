package clarence.key_expression;

import java.nio.ByteBuffer;
import java.util.ArrayList;

public class SyntaxTree {
	
	private ArrayList<ExpressionNode> nodes;
	private int pointer;
	
	public SyntaxTree() {
		nodes = new ArrayList<ExpressionNode>();
		pointer = 0;
	}
	
	public void rewind() {
		pointer = 0;
	}
	
	public void addNode(ExpressionNode node) {
		nodes.add(node);
		pointer++;
	}
	
	public int size() {
		return nodes.size();
	}
	
	public boolean hasNext() {
		if(pointer < size()) {
			return true;
		} else {
			return false;
		}
	}
	
	public ExpressionNode next() {
		ExpressionNode node = nodes.get(pointer);
		pointer++;
		return node;
	}
	
	public ExpressionNode peek(int i) {
		return nodes.get(pointer + i);
	}
	
	public void write(ByteBuffer buffer) {
		for(ExpressionNode node: nodes) {
			node.write(buffer);
		}
	}
	
	public int byteSize() {
		int byteSize = 0;
		for(ExpressionNode node: nodes) {
			byteSize += node.byteSize();
		}
		return byteSize;
	}
	
	public int numStrings() {
		int numStrings = 0;
		for(ExpressionNode node: nodes) {
			if(node instanceof StringNode) {
				numStrings++;
			}
		}
		return numStrings;
	}
	
	public int numStringChars() {
		int numStringChars = 0;
		for(ExpressionNode node: nodes) {
			if(node instanceof StringNode) {
				numStringChars += ((StringNode)node).numChars();
			}
		}
		return numStringChars;
	}
	
	public String printNodeSizes() {
		StringBuilder nodeSizes = new StringBuilder();
		for(ExpressionNode node: nodes) {
			nodeSizes.append(node.getClass() + "  " + node.byteSize() + System.lineSeparator());
		}
		return nodeSizes.toString();
	}
	
	public String summary() {
		StringBuilder summary = new StringBuilder();
		for(ExpressionNode node: nodes) {
			summary.append(node.printString() + System.lineSeparator());
		}
		return summary.toString();
	
	}
	
	public void keywordAdded(int keyInd) {
		for(ExpressionNode node: nodes) {
			if(node instanceof FunctionNode) {
				((FunctionNode)node).keywordAdded(keyInd);
			}
		}
	}

}
