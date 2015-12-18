package lexer;

public class CalOperator extends Token {
	protected String lexeme;
	protected String label;
	
	public CalOperator(String Oper) {
		type = "Operator";
		lexeme = Oper;
		/*
		 * The string.equal function checks the actual contents of the string, 
		 * the == operator checks whether the references to the objects are equal
		 */
		if(Oper.equals("+") || Oper.equals("-") ) {
			label = "pm";
		}
		else if(Oper.equals("*") || Oper.equals("/")) {
			label = "md";
		}
		else if(Oper.equals("minus")) {
			label = "-";
		}
		else if (Oper.equals("=") || Oper.equals("<") || Oper.equals(">") ||
				Oper.equals("<=") || Oper.equals(">=") || Oper.equals("<>")) {
			label = "cmp";
		}
		//���� ^ , & | ? : ! ( )
		else {
			label = Oper;
		}
	}
	
	/**
	 * ��ȡ������
	 * @return
	 */
	public String getLexeme() {
		return lexeme;
	}
	
	/**
	 * ��ȡ�������ڷ��ű��еı��
	 * @return
	 */
	public String getLable() {
		return label;
	}
	
	/**
	 * ��ȡ�������Ǽ�Ŀ�����
	 * @return
	 */
	public int getNum() {
		int num = 0;
				
		if (label.equals("-") || label.equals("!")) {
			num = 1;
		}
		if (label.equals("pm") || label.equals("md") || label.equals("^") ||
				label.equals("&") || label.equals("|") || label.equals("cmp")) {
			num = 2;
		}
		if (label.equals("?") || label.equals(":")) {
			num = 3;
		}
		
		return num;			
	}
}
