package jack.bai.studio.myaccount;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDBHelper extends SQLiteOpenHelper {

	private static final String TAG = MyDBHelper.class.getSimpleName();
	final String CREATE_EXTEND_TABLE_SQL = "create table expend_book(_id integer primary key autoincrement, "
			+ "ex_type TEXT, "
			+ "ex_remarks TEXT, "
			+ "ex_money TEXT, "
			+ "ex_date TEXT, " + "ex_time TEXT)";

	/**
	 * 
	 * @param context
	 * @param name
	 * @param version
	 */
	public MyDBHelper(Context context, String name, int version) {
		super(context, name, null, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// 第一次使用数据库时自动创建表
		db.execSQL(CREATE_EXTEND_TABLE_SQL);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// 版本更新时调用
		Log.e(TAG, "------------- onUpgrate Called ----------------"
				+ oldVersion + "--->" + newVersion);
	}

}
