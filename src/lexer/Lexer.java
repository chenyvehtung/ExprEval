package lexer;

import exceptions.*;

public class Lexer {

	/*
	 * ��ǵ�ǰָ��λ��
	 */
	protected int index;
	/*
	 * �洢��ȡ������ԭʼ�ַ�����
	 */
	protected String inputString;
	/*
	 * ��ȡ�޸Ĺ���������ַ������޸�ָ���Ǳ�ΪСд��ȥ���ո�
	 */
	protected String fixString;
	/*
	 * �洢�������ࡣ
	 */
	protected final String opers = "+-*/^?:><=&|!";
	
	public Lexer(String expression) {
		inputString = expression;
		fixString = inputString.toLowerCase().replace(" ", "");
		index = 0;
	}
	
	public Token getNextToken() throws LexicalException {
		/*
		 * ��¼�޸Ĺ�����ַ����ĳ��ȡ�
		 */
		int strLen = fixString.length();	
		/*
		 * �ж�ȫ�ǿ��ַ���
		 */
		if (strLen == 0) {
			throw new exceptions.LexicalException();//ȫ�ǿ��ַ�
		}
		/*
		 * ��¼��ǰλ�õ��ַ���
		 */
		Character curChar = fixString.charAt(index);
				
		//�ж�Decimal
		if (Character.isDigit(curChar)) {
			Boolean dotFlag = false;
			Boolean eFlag = false;
			int startInt = index;
			int endInt = strLen;
			for (int j = index + 1; j < strLen; j++) {
				char peek = fixString.charAt(j);
				if (Character.isDigit(peek)) 
					continue;
				else if (peek == '.') {
					if (eFlag) 
						throw new exceptions.LexicalException(); //С���������ָ������
					else if (dotFlag)
						throw new exceptions.LexicalException(); //С�����������
					else if (j + 1 < strLen) {
						if (!Character.isDigit(fixString.charAt(j+1)))
							throw new exceptions.LexicalException(); //С�����ӵĲ�������
					}
					else if (j + 1 == strLen) 
						throw new exceptions.LexicalException(); //С���㲻�������һλ
					else {
						dotFlag = true;
					}					
				}
				else if (peek == 'e') {
					if (eFlag)
						throw new exceptions.LexicalException(); //e���ֶ��
					else if (j + 1 < strLen) { 
						if (!(Character.isDigit(fixString.charAt(j+1)) || 
								fixString.charAt(j+1) == '+' || fixString.charAt(j+1) == '-' ))
							throw new exceptions.LexicalException(); //e����ӵĲ������ֻ�+����-��
					}
					else if (j+1 == strLen) 
						throw new exceptions.LexicalException(); //e���������һλ
					else {
						eFlag = true;
					}
				}
				else if (peek == '-' || peek == '+') {
					if ( !(fixString.charAt(j-1) == 'e') ) {
						index = j;
						endInt = j;
						break;
					}
					if (j+1 == strLen)
						throw new exceptions.LexicalException(); //e�����'+'��-���������Ҫ������
				}
				else {
					index = j;
					endInt = j;
					break;
				}
			}
			return new CalDecimal(fixString.substring(startInt, endInt));
		}
		
		//�жϲ���ʽ��
		if (curChar == 't' || curChar == 'f') {
			String boolStrLow = "";
			if (curChar == 't') {
				boolStrLow = fixString.substring(index, index+4);
				if (boolStrLow == "true") {
					index += 4;
					return new CalBoolean(boolStrLow);
				}
				else {
					throw new exceptions.LexicalException(); //��ͷ��t,������true
				}
			}
			else {
				boolStrLow = fixString.substring(index, index+5);
				if (boolStrLow == "false") {
					index += 5;
					return new CalBoolean(boolStrLow);
				}
				else
					throw new exceptions.LexicalException(); //��ͷ��f��������false
			}
		}
		
		//�жϺ���
		if (curChar == 's' || curChar == 'c' || curChar == 'm') {
			String funcLow = fixString.substring(index, index+3);
			if (funcLow == "sin" || funcLow == "cos" || funcLow == "max" || funcLow == "min") {
				index += 3;
				return new CalFunction(funcLow);
			}
			else {
				throw new exceptions.LexicalException();//����������
			}
		}
		
		//�жϷ���
		if (opers.indexOf(curChar) != -1) {
			if (curChar == '>') {
				if (index < strLen - 1) {
					if (fixString.charAt(index+1) == '=') {
						index += 2;
						return new CalOperator(">=");
					}
				}
			}
			if (curChar == '<') {
				if (index < strLen -1) {
					if (fixString.charAt(index+1) == '=') {
						index += 2;
						return new CalOperator("<=");
					}
					if (fixString.charAt(index+1) == '>') {
						index += 2;
						return new CalOperator("<>");
					}
				}
			}
			if (curChar == '-') {
				if (index - 1 >= 0) {
					//ֻ�е�-ǰΪ����������ʱ�����Ŵ�����ţ�������Ǹ���
					if (fixString.charAt(index - 1 ) == ')' || 
							Character.isDigit(fixString.charAt(index -1))) {
						index += 1;
						return new CalOperator("-");
					}
				}
				index += 1;
				return new CalOperator("minus");
			}
			
			index += 1;
			return new CalOperator(curChar.toString());
		}
		
		//��������������ͣ����׳��쳣
		if (index < strLen) {
			throw new exceptions.LexicalException(); //���������Ƿ��ַ���
		}
		
		//�����ս��
		return new Dollar();
	}
	
	public String getFixString() {
		return fixString;
	}
	
}
