package jack.bai.studio.myaccount;
import java.util.HashMap;

public class SQLAttributes {
	private static HashMap<String, String> sqlMap = new HashMap<String, String>();

	// TODO 设置更完善的SQL语句
	public static String SQL_CREATE_TABLE_EXPEND = "";
	public static String SQL_CREATE_TABLE_INCOME = "";
	public static String SQL_QUERY_ALL = "select * from expend_book";
	public static String SQL_QUERY_BY_DATE = "select * from expend_book where ex_date=?";
	public static String SQL_SUM_MONEY_ALL = "select sum(ex_money) from expend_book";
	public static String SQL_SUM_MONEY_BY_DATE = "select sum(ex_money) from expend_book where ex_date=?";
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
