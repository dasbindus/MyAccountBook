package jack.bai.studio.myaccount;

import java.util.HashMap;

public class SQLAttributes {
	private static HashMap<String, String> sqlMap = new HashMap<String, String>();

	// TODO ���ø����Ƶ�SQL���
	public static String SQL_CREATE_TABLE_EXPEND = "";
	public static String SQL_CREATE_TABLE_INCOME = "";
	/** ��ѯ���м�¼ */
	public static String SQL_QUERY_ALL = "select * from account";
	/** ��ָ�����ڲ�ѯ��ѯ */
	public static String SQL_QUERY_BY_DATE = "select * from account where date=?";
	/** ��ѯ������֧�� */
	public static String SQL_SUM_MONEY_ALL = "select sum(money) from account where ex_in_type=0";
	/** ��ָ�����ڲ�ѯ��֧�� */
	public static String SQL_SUM_EX_MONEY_BY_DATE = "select sum(money) from account where ex_in_type=0 and date=?";
	/** ��ָ��ʱ��β�ѯ��֧�� */
	public static String SQL_SUM_MONEY_BY_DATE_RANGE = "";

	static {
		sqlMap.put("query_by_date", SQL_QUERY_BY_DATE);
		sqlMap.put("sql_query_all", SQL_QUERY_ALL);
	}

	public static String lookUp(String name) {
		String sql = sqlMap.get(name);
		return sql == null ? "" : sql;
	}
}
