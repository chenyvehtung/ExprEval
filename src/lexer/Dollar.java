package lexer;

/**
 * �ս���ʷ���Ԫ���̳���Token�ࡣ
 * @author Donald
 * @see Token
 */
public class Dollar extends Token {
	/**
	 * �洢�ս��
	 */
	protected String lexeme;
	/**
	 * �洢�ս����������ȹ�ϵ��OPP�еı�ǡ�
	 */
	protected String label;
	/**
	 * ��ʼ���ս������������������ΪDollar���������ʾΪ$,�Լ���������OPP���еı��Ϊ$��
	 */
	public Dollar() {
		type = "Dollar";
		lexeme = "$";
		label = "$";
	}
	/**
	 * ��ȡ�ս����ʾ
	 * @return �ս����ʾ
	 */
	public String getLexeme() {
		return lexeme;
	}
	/**
	 * ��ȡ�ս����OPP���еı�ǡ�
	 * @return �ս����OPP���еı��
	 */
	public String getLabel() {
		return label;
	}
	
}
