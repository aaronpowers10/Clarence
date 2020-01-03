package clarence.key_expression;

import java.nio.ByteBuffer;

public interface ExpressionNode {
	
	public String printString();
	public void read(ByteBuffer buffer,SyntaxTree tree);
	public void write(ByteBuffer buffer);
	public int byteSize();
}
