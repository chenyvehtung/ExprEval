package lexer;

/**
 * �������͵Ĵʷ���Ԫ���̳���Token�ࡣ
 * @author Donald
 * @see Token
 */
public class CalBoolean extends Token {
	/**
	 * �洢���������Ӧ�Ĳ���ֵ��
	 */
	protected Boolean value;
	/**
	 * ��ʼ����������
	 * ������������ΪBoolean����������value��
	 * @param temp  ���汻��Ϊ�ǲ���ֵ���ַ�����
	 */
	public CalBoolean(String temp) {
		type = "Boolean";
		temp = temp.toLowerCase();
		if (temp.equals("true") )
			value = true;
		else {
			value = false;
		}
	}
	/**
	 * ���ز��������Ӧ�Ĳ���ֵ��
	 * @return �ö���Ĳ���ֵ��
	 */
	public Boolean getValue() {
		return value;
	}
	/**
	 * ���ö���Ĳ���ֵ��
	 * @param newValue  ��������value�Ĳ���ֵ��
	 */
	public void setValue(Boolean newValue) {
		value = newValue;
	}
}
