package lexer;

/**
 * ���дʷ���Ԫ�ĸ��ࡣ
 * @author Donald
 */
public class Token {
	/**
	 * �洢�ʷ���Ԫ�����͡��ڱ�ʵ���о������Boolean,Decimal,Function,Operator�Լ�Dollar�������
	 */
	protected String type;
	/**
	 * ��ʼ���ʷ���Ԫ
	 */
	public Token() {
		type = "";
	}
	/**
	 * ��ȡ�ʷ���Ԫ������
	 * @return �ʷ���Ԫ������
	 */
	public String getType() {
		return type;
	}	
}
