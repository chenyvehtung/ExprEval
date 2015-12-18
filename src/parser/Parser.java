package parser;

import java.util.Arrays;
import java.util.Stack;
import exceptions.FunctionCallException;
import exceptions.LexicalException;
import exceptions.MissingLeftParenthesisException;
import exceptions.MissingOperandException;
import exceptions.MissingRightParenthesisException;
import exceptions.SyntacticException;
import exceptions.TrinaryOperationException;
import exceptions.TypeMismatchedException;
import lexer.CalBoolean;
import lexer.CalDecimal;
import lexer.CalFunction;
import lexer.CalOperator;
import lexer.Dollar;
import lexer.Lexer;
import lexer.Token;

public class Parser {

	/**
	 * ����ȷ��label����Ӧ���±꣬�Դ���ƥ����ű�
	 */
	protected final String[] LABEL_ARRAY = 
		{"(", ")", "func", "-", "^", "md", "pm", "cmp", "!", "&", "|", "?", ":", ",", "$"};
	/**
	 * ���ڴ洢���ŵĶ�ջ��
	 */
	protected Stack<Token> operators = new Stack<Token>();	
	/**
	 * ���ڴ洢�������Ķ�ջ������ʮ�������Ͳ�����
	 */
	protected Stack<Token> operands = new Stack<Token>();
	/**
	 * �洢��ǰ����Ĵʷ���Ԫ��
	 */
	protected Token curToken = new Token();
	/*
	 * �洢��������ջջ��Ԫ��
	 */
	protected Token topToken = new Token();
	
	/**
	 * ��ʼ��������ջ.
	 */
	public Parser() {
		operators.clear();
		operands.clear();
		Dollar dollar = new Dollar();
		operators.push(dollar);				
	}
	
	/**
	 * ��ȡ��Ӧ�ʷ���Ԫ��label
	 * @param temp
	 * @return
	 */
	private String getLabel(Token temp) {
		String tempType = temp.getType();
		if (tempType.equals("Function")) {
			return ((CalFunction)temp).getLabel();
		}
		if (tempType.equals("Operator")) {
			return ((CalOperator)temp).getLable();
		}
		return ((Dollar)temp).getLabel();
	}
	
	/**
	 * ��ȡ�ʷ���Ԫlabel��LABEL_ARRAY�ж�Ӧ���±�
	 * @param label
	 * @return
	 */
	private int getIndex(String label) {
		return Arrays.asList(LABEL_ARRAY).indexOf(label);
	}
	
	/**
	 * ִ�к���������
	 * @param cnt
	 * @param func
	 * @throws FunctionCallException 
	 */
	private void doFunction(int cnt, String func) throws FunctionCallException {
		Token tempOperand = operands.pop();
		if (tempOperand.getType().intern() != "Decimal") {
			throw new FunctionCallException();
		}
		Double ansValue = 0.0;
		
		if (cnt > 0) {
			//do max and min
			ansValue = ((CalDecimal)tempOperand).getValue();
			Double topValue = 0.0;
			for (int i = 0; i < cnt; i++) {
				tempOperand = operands.pop();
				topValue = ((CalDecimal)tempOperand).getValue();
				if (func.equals("max") && topValue > ansValue) {
					ansValue = topValue; 
				}
				if (func.equals("min") && topValue < ansValue) {
					ansValue = topValue;
				}
			}
		}
		else {
			//do sin and cos
			if(func.equals("sin")) {
				ansValue = Math.sin( ((CalDecimal)tempOperand).getValue() );
			}
			if (func.equals("cos")) {
				ansValue = Math.cos( ((CalDecimal)tempOperand).getValue() );
			}
		}
		operands.push(new CalDecimal(Double.toString(ansValue)));
	}
	
	/**
	 * �����Ŵʷ���Ԫѹ��operators��ջ�С�
	 * @param oper
	 */
	private void shift(Token oper) {
		operators.push(oper);
	}
	
	/**
	 * ��Ŀ����Ĺ�Լ��
	 */
	private void unaryReduce() {
		CalOperator tempOperator = (CalOperator)operators.pop();  //��ȡ���Ŷ�ջջ��Ԫ�ز��Ƴ�
		Token tempOperand = operands.pop();
		if (tempOperator.getLable().equals("-")) {
			Double tempValue = ((CalDecimal)tempOperand).getValue();
			tempValue = 0 - tempValue;
			((CalDecimal)tempOperand).setValue(tempValue);
		}
		if (tempOperator.getLable().equals("!")) {
			Boolean tempValue = ((CalBoolean)tempOperand).getValue();
			tempValue = !tempValue;
			((CalBoolean)tempOperand).setValue(tempValue);			
		}
		operands.push(tempOperand);
	}
	
	/**
	 * ˫Ŀ�����Լ��
	 * @throws TypeMismatchedException 
	 */
	private void binaryReduce() throws TypeMismatchedException {
		CalOperator tempOperator = (CalOperator)operators.pop(); //��ȡջ�����Ų��Ƴ�
		Token operandB = operands.pop();
		Token operandA = operands.pop();
		// ִ�� + - * / ^
		if (tempOperator.getLable().equals("pm") || tempOperator.getLable().equals("md") || tempOperator.getLable().equals("^")) {
			if (operandA.getType().equals("Decimal") && operandB.getType().equals("Decimal")) {
				Double valueA = ((CalDecimal)operandA).getValue();
				Double valueB = ((CalDecimal)operandB).getValue();
				Double valueC = 0.0;
				switch (tempOperator.getLexeme().charAt(0)) {
				case '+':
					valueC = valueA + valueB; break;
				case '-':
					valueC = valueA - valueB; break;
				case '*':
					valueC = valueA * valueB; break;			
				case '/':
					valueC = valueA / valueB; break;
				case '^':
					valueC = Math.pow(valueA, valueB); break;
				default:
					break;
				}
				operands.push(new CalDecimal(Double.toString(valueC)));
			}
			else {
				throw new TypeMismatchedException();
			}
		}
		//ִ�� & |
		if (tempOperator.getLable().equals("&") || tempOperator.getLable().equals("|")) {
			if (operandA.getType().equals("Boolean") && operandB.getType().equals("Boolean")) {
				Boolean boolA = ((CalBoolean)operandA).getValue();
				Boolean boolB = ((CalBoolean)operandB).getValue();
				Boolean boolC = false;
				switch (tempOperator.getLexeme().charAt(0)) {
				case '&':
					boolC = boolA & boolB; break;
				case '|':	
					boolC = boolA | boolB; break;
				default:
					break;
				}
				operands.push(new CalBoolean(Boolean.toString(boolC)));
			}
			else {
				throw new TypeMismatchedException();
			}
		}
		//ִ�й�ϵ���㣬> < = >= <= <>
		if (tempOperator.getLable().equals("cmp")) {
			if (operandA.getType().equals("Decimal") && operandB.getType().equals("Decimal")) {
				Double valueA = ((CalDecimal)operandA).getValue();
				Double valueB = ((CalDecimal)operandB).getValue();
				Boolean boolC = false;
				String operLexeme = tempOperator.getLexeme();
				if ( (operLexeme.equals(">") && valueA > valueB) || (operLexeme.equals("<") && valueA < valueB) || 
					 (operLexeme.equals("=") && valueA ==valueB) || (operLexeme.equals(">=")&& valueA >= valueB) ||
					 (operLexeme.equals("<=")&& valueA <=valueB) || (operLexeme.equals("<>")&& valueA != valueB)) {
					boolC = true;
				}
				operands.push(new CalBoolean(Boolean.toString(boolC)));
			}
			else {
				throw new TypeMismatchedException();
			}
		}		
	}
	
	/**
	 * ��Ŀ�����Լ��
	 * @throws TrinaryOperationException
	 * @throws TypeMismatchedException
	 */
	private void trinaryReduce() throws TrinaryOperationException, TypeMismatchedException {
		CalOperator operatorB = (CalOperator)operators.pop();
		CalOperator operatorA = (CalOperator)operators.pop();
		Token operandC = operands.pop();
		Token operandB = operands.pop();
		Token operandA = operands.pop();
		if (operatorA.getLexeme().equals("?") && operatorB.getLexeme().equals(":")) {
			if (operandA.getType().equals("Boolean") && operandB.getType().equals("Decimal") && operandC.getType().equals("Decimal")) {
				Double valueD = 0.0;
				if ( ((CalBoolean)operandA).getValue() ) {
					valueD = ((CalDecimal)operandB).getValue(); 
				}
				else {
					valueD = ((CalDecimal)operandC).getValue();
				}
				operands.push(new CalDecimal(Double.toString(valueD)));
			}
			else {
				throw new TypeMismatchedException();
			}
		}
		else {
			throw new TrinaryOperationException();
		}
	}
	
	/**
	 * ���������Լ����������Լ��
	 * @throws TypeMismatchedException
	 * @throws SyntacticException
	 * @see doFunction
	 */
	private void matchReduce() throws TypeMismatchedException, SyntacticException {	
		Token tempOperator = operators.peek();
		int cntComma = 0;
		Boolean matchCompleted = false;
		while (!matchCompleted) {
			if (tempOperator.getType().equals("Dollar")) {
				throw new MissingLeftParenthesisException();
			}
			else {
				if (((CalOperator)tempOperator).getLexeme().equals("(")) {
					operators.pop(); //�Ƴ���
					matchCompleted = true;
					break;
				}
				//�����������Ҫ�Ĳ���������
				int tempNum = ((CalOperator)tempOperator).getNum();
				
				if (cntComma == 0 && tempNum == 1) {
					unaryReduce();
				}
				else if (cntComma == 0 && tempNum == 2) {
					binaryReduce();
				}
				else if (cntComma == 0 && tempNum == 3) {
					trinaryReduce();
				}
				else if (((CalOperator)tempOperator).getLexeme().equals(",")) { //�ò�������,
					operators.pop();
					cntComma++;
				}
				else {
					throw new SyntacticException();
				}			
			}
			tempOperator = operators.peek();
		} //end of while loop
		
		tempOperator = operators.peek();
		//�����function�Ļ�������ִ��function����������������㡣
		if (tempOperator.getType().equals("Function")) {
			doFunction(cntComma, ((CalFunction)tempOperator).getLexeme());
		}		
		
	}
	
	/**
	 * ִ���﷨���������嶯����
	 * @param expression
	 * @return
	 * @throws LexicalException
	 * @throws TypeMismatchedException
	 * @throws SyntacticException
	 */
	public Double parsing(String expression) throws LexicalException, TypeMismatchedException, 
	SyntacticException {
		
		Lexer lexer = new Lexer(expression);
		curToken = lexer.getNextToken();
		Boolean completed = false;
		int action = 0;
		int lableStackIndex;
		int lableReadIndex;
		
		
		while(!completed) {
			topToken = operators.peek();
			if (curToken.getType().equals("Boolean") || curToken.getType().equals("Decimal")) {
				operands.push(curToken);
				curToken = lexer.getNextToken();
				continue;
			}
			else {
				lableReadIndex = getIndex(getLabel(curToken));
				lableStackIndex = getIndex(getLabel(topToken));
				action = OPP.table[lableStackIndex][lableReadIndex];
				switch (action) {
				case OPP.ACCEPT:
					completed = true;
					break;
				case OPP.SHIFT:
					shift(curToken);
					curToken = lexer.getNextToken();
					break;
				case OPP.RDUNAOPER:
					unaryReduce();
					break;
				case OPP.RDBINAOPER:
					binaryReduce();
					break;
				case OPP.RDTRINAOPER:
					trinaryReduce();
					break;
				case OPP.RDMATCH:
					matchReduce();
					break;
				case OPP.ERRLEFTPAR:
					throw new MissingLeftParenthesisException();
				case OPP.ERRSYN:
					throw new SyntacticException();
				case OPP.ERROPERAND:
					throw new MissingOperandException();
				case OPP.ERRTYPE:
					throw new TypeMismatchedException();
				case OPP.ERRFUNCSYN:
					throw new FunctionCallException();
				case OPP.ERRRIGHTPAR:
					throw new MissingRightParenthesisException();
				case OPP.ERRTRINA:
					throw new TypeMismatchedException();
				default:
					break;
				}
			}
		}
		
		if (completed) {
			//return the ans;
			if (operands.size() == 1 && operands.peek().getType().equals("Decimal")) {
				return ((CalDecimal)operands.peek()).getValue();
			}
			else {
				throw new SyntacticException();
			}
		}
		else {
			throw new SyntacticException();
		}
		
	}
	
	
}
