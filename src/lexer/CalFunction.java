package lexer;
/**
 * �����Ĵʷ���Ԫ���̳���Token�ࡣ
 * @author Donald
 * @see Token
 */
public class CalFunction extends Token{
	/**
	 * �洢�ʷ���Ԫ�ĺ�����
	 */
	protected String lexeme;
	/**
	 * �洢�ʷ���Ԫ��������ȹ�ϵ���е�label
	 */
	protected String label;
	/**
	 * ��ʼ�������ʷ���Ԫ����������������ΪFunction�������亯�����Լ�������labelΪfunc
	 * @param func  ���汻��Ϊ�Ǻ��������ַ���
	 */
	public CalFunction(String func) {
		type = "Function";
		lexeme = func.toLowerCase();
		label = "func";
	}
	/**
	 * ��ȡ������
	 * @return ������
	 */
	public String getLexeme() {
		return lexeme;
	}
	/**
	 * ��ȡ�ʷ���Ԫ��������ȼ����еı��
	 * @return �ʷ���Ԫ��������ȼ����еı��
	 */
	public String getLabel() {
		return label;
	}
}
