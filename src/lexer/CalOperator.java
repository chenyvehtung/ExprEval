package lexer;

public class CalOperator extends Token {
	protected String lexeme;
	protected String label;
	
	public CalOperator(String Oper) {
		type = "Operator";
		lexeme = Oper;
		if(Oper=="+" || Oper == "-") {
			label = "pm";
		}
		else if(Oper == "*" || Oper == "/") {
			label = "md";
		}
		else if(Oper == "minus") {
			label = "-";
		}
		else if (Oper == "=" || Oper == "<" || Oper == ">" || Oper == "<="
				|| Oper == ">=" || Oper == "<>") {
			label = "cmp";
		}
		//���� ^ , & | ? : !
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
		if (label == "-" || label == "!") {
			num = 1;
		}
		if (label == "pm" || label == "md" || label == "^" || label == "&" || label == "|" ||
				label == "cmp") {
			num = 2;
		}
		if (label == "?" || label == ":") {
			num = 3;
		}
		return num;			
	}
}
