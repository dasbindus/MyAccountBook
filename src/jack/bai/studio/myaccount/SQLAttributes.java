package jack.bai.studio.myaccount;

import java.util.HashMap;

public class SQLAttributes {
	private static HashMap<String, String> sqlMap = new HashMap<String, String>();

	// TODO 设置更完善的SQL语句
	public static String SQL_CREATE_TABLE_EXPEND = "";
	public static String SQL_CREATE_TABLE_INCOME = "";
	/** 查询所有记录 */
	public static String SQL_QUERY_ALL = "select * from account";
	/** 按指定日期查询查询 */
	public static String SQL_QUERY_BY_DATE = "select * from account where date=?";
	/** 查询所有总支出 */
	public static String SQL_SUM_MONEY_ALL = "select sum(money) from account where ex_in_type=0";
	/** 按指定日期查询总支出 */
	public static String SQL_SUM_EX_MONEY_BY_DATE = "select sum(money) from account where ex_in_type=0 and date=?";
	/** 按指定时间段查询总支出 */
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
