package lexer;

/**
 * ʮ�������Ĵʷ���Ԫ���̳���Token�ࡣ
 * @author Donald
 * @see Token
 */
public class CalDecimal extends Token {
	/**
	 * ʮ����������ֵ
	 */
	protected Double value;
	/**
	 * ��ʼ��ʮ�������ʷ���Ԫ����������������ΪDecimal��������ѧ������ת��Ϊdouble���͡�
	 * @param num  ���汻��Ϊ��ʮ���������ַ�����
	 */
	public CalDecimal(String num) {
		type = "Decimal";
		String lowerNum = num.toLowerCase();
		int hasE = lowerNum.indexOf('e');
		if (hasE != -1) {
			double fraction = Double.parseDouble(lowerNum.substring(0, hasE));
			double exponent;
			switch (lowerNum.charAt(hasE+1)) {
			case '-':
				exponent = Double.parseDouble(lowerNum.substring(hasE+2, lowerNum.length()));
				value = fraction / Math.pow(10.0, exponent);
				break;
			case '+':
				exponent = Double.parseDouble(lowerNum.substring(hasE+2, lowerNum.length()));
				value = fraction * Math.pow(10.0, exponent);
				break;
			default:
				exponent = Double.parseDouble(lowerNum.substring(hasE+1, lowerNum.length()));
				value = fraction * Math.pow(10.0, exponent);
				break;
			}
		}
		else {
			value = Double.parseDouble(lowerNum);
		}
		
	}	
	/**
	 * ��ȡʮ���Ƶ���ֵ
	 * @return �ôʷ���Ԫ�Ķ�ʮ������ֵ
	 */
	public Double getValue() {
		return value;
	}
	/**
	 * ���ôʷ���Ԫ��ʮ��������ֵ
	 * @param newValue double������ֵ��
	 */
	public void setValue(Double newValue) {
		value = newValue;
	}
}


