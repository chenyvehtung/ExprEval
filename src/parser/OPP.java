package parser;

public class OPP {

		/**
		 * ȱ�������ţ�-7
		 */
		public static final int ERRLEFTPAR = -7;
		/**
		 * �﷨���� -6
		 */
		public static final int ERRSYN = -6;
		/**
		 * ȱ�ٲ������� -5
		 */
		public static final int ERROPERAND = -5;
		/**
		 * ���ʹ��� -4
		 */
		public static final int ERRTYPE = -4;
		/**
		 * �����﷨���� -3
		 */
		public static final int ERRFUNCSYN = -3;
		/**
		 * ȱ�������ţ� -2
		 */
		public static final int ERRRIGHTPAR = -2;
		/**
		 * ��Ԫ�����쳣�� -1
		 */
		public static final int ERRTRINA = -1;
		/**
		 * ����, 0
		 */
		public static final int ACCEPT = 0; 
		/**
		 * ��Ŀ���㣬 1������-�ͣ�
		 */
		public static final int RDUNAOPER = 1;
		/**
		 * ˫Ŀ�����Լ�� 2������+�� -�� *�� /, ^, &, |�Լ���ϵ���㡣
		 */
		public static final int RDBINAOPER = 2;
		/**
		 * ��Ŀ�����Լ�� 3������?:
		 */
		public static final int RDTRINAOPER = 3; 
		/**
		 * ���������Լ�� 4������������⼰���㣬
		 */
		public static final int RDMATCH = 4;
		/**
		 * ��������� 5��
		 */
		public static final int SHIFT = 5;
		
	/**
	 * �����Լ���������д�������ջ�����ţ��д������Ƕ�����ţ������ֺ�����ö���������Ӧ��
	 * funcָ���Ǻ���������cos, sin, max, min
	 * mdָ����multiple��divide�����˳�����
	 * pmָ����plus��minus�����Ӽ�����
	 * -ָ���Ǹ���
	 * cmpָ�Ĺ�ϵ���㣬��>,<�ȵ�
	 * ���⣬���ڣ����������ȼ���ߣ���������������й�Լ����ѹջ������ջ�������еģ�����ֻ��Ϊ�˺����������㣬û��ʵ�����塣
	 */
	public static final int table[][] = {
		/*ջ��*/ /*(  ) func - ^  md pm cmp ! &  |  ?  :  ,  $    �����ַ�*/
		/*(*/    {5, 4, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5,-1, 5,-2},
		/*)*/    {4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4},
		/*func*/ {5,-3,-3,-3,-3,-3,-3,-3,-3,-3,-3,-3,-3,-3,-3},
		/*-*/    {5, 4, 5, 5, 1, 1, 1, 1,-6,-4,-4,-4, 1, 1, 1},
		/*^*/    {5, 4, 5, 5, 5, 2, 2, 2,-6,-4,-4,-4, 2, 2, 2},
		/*md*/   {5, 4, 5, 5, 5, 2, 2, 2,-6,-4,-4,-4, 2, 2, 2},
		/*pm*/   {5, 4, 5, 5, 5, 5, 2, 2,-6,-4,-4,-4, 2, 2, 2},
		/*cmp*/  {5, 4, 5, 5, 5, 5, 5,-4,-6, 2, 2, 2,-1,-3, 2},
		/*!*/    {5, 4,-4,-4,-4,-4,-4, 5, 5, 1, 1, 1,-1,-3, 1},
		/*&*/    {5, 4,-4,-4,-4,-4,-4, 5, 5, 2, 2, 2,-1,-3, 2},
		/*|*/    {5, 4,-4,-4,-4,-4,-4, 5, 5, 5, 2, 2,-1,-3, 2},
		/*?*/    {5,-1, 5, 5, 5, 5, 5,-1,-1,-1,-1, 5, 5,-1,-1},
		/*:*/    {5, 4, 5, 5, 5, 5, 5,-1,-1,-1,-1, 5,-1,-1, 3},
		/*,*/    {5, 4, 5, 5, 5, 5, 5,-3,-3,-3,-3, 5,-1, 5,-3},
		/*$*/    {5,-7, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5,-1,-3, 0}
	};
}