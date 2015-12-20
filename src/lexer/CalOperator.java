package lexer;

/**
 * �������Ĵʷ���Ԫ���̳���Token�ࡣ
 * @author Donald
 * @see Token
 */
public class CalOperator extends Token {
	/**
	 * �洢��������
	 */
	protected String lexeme;
	/**
	 * �洢��������������ȹ�ϵ��OPP�еı��
	 */
	protected String label;
	/**
	 * ��ʼ���������ʷ���Ԫ����������������ΪOperator,���ò��������Լ����ò�������OPP�еı�ǡ�
	 * @param Oper  ���汻��Ϊ�ǲ��������ַ�����
	 */
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
	 * @return ������
	 */
	public String getLexeme() {
		return lexeme;
	}	
	/**
	 * ��ȡ�������ڷ��ű��еı��
	 * @return �������ڷ��ű��еı��
	 */
	public String getLable() {
		return label;
	}
	/**
	 * ��ȡ�������Ǽ�Ŀ�����
	 * @return ��ȡ�������Ǽ�Ŀ�����
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
