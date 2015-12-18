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
	protected final String opers = "+-*/^?:><=&|!(),";
	
	public Lexer(String expression) {
		inputString = expression;
		fixString = inputString.toLowerCase().replace(" ", "");
		index = 0;
	}
	
	public Token getNextToken() throws LexicalException, ExpressionException {
		/*
		 * ��¼�޸Ĺ�����ַ����ĳ��ȡ�
		 */
		int strLen = fixString.length();	
		/**
		 * �ж�ȫ�ǿ��ַ���
		 */
		if (strLen == 0) {
			throw new EmptyExpressionException();//ȫ�ǿ��ַ�
		}
		//�Ѿ�������ȫ���ַ�
		if (index >= strLen) {
			return new Dollar();
		}
		
		/**
		 * ��¼��ǰλ�õ��ַ���
		 */
		Character curChar = fixString.charAt(index);
				
		//�ж�Decimal
		if (Character.isDigit(curChar)) {
			Boolean dotFlag = false;
			Boolean eFlag = false;
			int startInt = index;
			//�˴�ʹ��j��Ԥ����һλ������жϷ����ǺϷ��ַ�����ô�ͽ������Decimal�У�Ҳ���ǽ�index����1��
			for (int j = startInt + 1; j < strLen; j++) {
				char peek = fixString.charAt(j);
				if (Character.isDigit(peek)) {
					index++; //�Ϸ��ַ�������index
					continue;
				}
				else if (peek == '.') {
					if (eFlag || dotFlag) 
						throw new IllegalDecimalException(); //С���������ָ������,����С�����������
					else if (j + 1 >= strLen) 
						throw new IllegalDecimalException(); //С���������һλ
					else if (!Character.isDigit(fixString.charAt(j+1)))
						throw new IllegalDecimalException(); //С�����ӵĲ�������
					else {
						index++; //�Ϸ��ַ�������index
						dotFlag = true;
					}					
				}
				else if (peek == 'e') {
					if (eFlag)
						throw new IllegalDecimalException(); //e���ֶ��
					else if (j+1 >= strLen) 
						throw new IllegalDecimalException(); //e�����һλ
					else if (!(Character.isDigit(fixString.charAt(j+1)) || 
							fixString.charAt(j+1) == '+' || fixString.charAt(j+1) == '-' ))
						throw new IllegalDecimalException(); //e����ӵĲ������ֻ�+����-��
					else {
						index++; //�Ϸ��ַ�������index
						eFlag = true;
					}
				}
				else if (peek == '-' || peek == '+') {
					if ( !(fixString.charAt(j-1) == 'e') ) { //+����������e���棬��ô����Ϊ�Ƿ��ţ�Decimal�������
						break;
					}
					else if (j+1 == strLen)
						throw new IllegalDecimalException(); //e�����'+'��-���������Ҫ������
					else 
						index++; //�Ϸ��ַ�������index
				}
				//���ǺϷ����ַ�������decimal��
				else {
					break;
				}
			}
			//��forѭ����������Ҫô�����������Ϸ��ַ���Ҫô�����ַ��������ˡ���index����ָ�����һ���Ϸ������룬���Խ�index����1ʹ����ָ����һ���ַ����Ա��ȡ�¸�Token
			index++; 
			return new CalDecimal(fixString.substring(startInt, index));
		}
		
		//�жϲ���ʽ��
		else if (curChar == 't' || curChar == 'f') {
			String boolStrLow = "";
			if (curChar == 't') {
				boolStrLow = fixString.substring(index, index+4);
				if (boolStrLow.equals("true")) {
					index += 4;
					return new CalBoolean(boolStrLow);
				}
				else {
					throw new IllegalIdentifierException(); //��ͷ��t,������true
				}
			}
			else {
				boolStrLow = fixString.substring(index, index+5);
				if (boolStrLow.equals("false")) {
					index += 5;
					return new CalBoolean(boolStrLow);
				}
				else
					throw new IllegalIdentifierException(); //��ͷ��f��������false
			}
		}
		
		//�жϺ���
		else if (curChar == 's' || curChar == 'c' || curChar == 'm') {
			String funcLow = fixString.substring(index, index+3);
			if (funcLow.equals("sin") || funcLow.equals("cos") || funcLow.equals("max") || funcLow.equals("min")) {
				index += 3;
				return new CalFunction(funcLow);
			}
			else {
				throw new IllegalIdentifierException();//����������
			}
		}
		
		//�жϷ���
		else if (opers.indexOf(curChar) != -1) {
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
			
			//��ӣ����ڲ������Ͳ���������ͬһ����ջ�У���Щ�﷨����ֻ���ڴʷ��������׳�������
			if (curChar == ')') {
				if (index - 1 >= 0) {
					if (fixString.charAt(index - 1 ) == '(')
						throw new MissingOperandException();
				}
			}
			if (curChar == ':') {
				if (index - 1 >= 0) {
					if (fixString.charAt(index - 1 ) == '?')
						throw new MissingOperandException();
				}
			}
 			
			index += 1;
			return new CalOperator(curChar.toString());
		}
		
		//��������������ͣ����׳��쳣
		else {
			if (curChar == '.') {
				if (index + 1 < fixString.length()) {
					if (Character.isDigit(fixString.charAt(index+1)))
						throw new IllegalDecimalException();
					else 
						throw new LexicalException();
				}
				else 
					throw new LexicalException();
			}
			else if (Character.isAlphabetic(curChar)) {
				throw new IllegalIdentifierException(); //��ĸ��ͷ�����Ǻ���
			}
			else 
				throw new IllegalSymbolException(); //���������Ƿ��ַ���
		}
		
	}
	
	public String getFixString() {
		return fixString;
	}
	
}
