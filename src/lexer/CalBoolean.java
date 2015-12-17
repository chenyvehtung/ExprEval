package lexer;

public class CalBoolean extends Token {
	
	protected Boolean value;
	
	public CalBoolean(String temp) {
		type = "Boolean";
		if (temp == "true")
			value = true;
		else {
			value = false;
		}
	}
	
	/**
	 * ���ز��������Ӧ�Ĳ���ֵ
	 * @return
	 */
	public Boolean getValue() {
		return value;
	}

	public void setValue(Boolean newValue) {
		value = newValue;
	}
}
