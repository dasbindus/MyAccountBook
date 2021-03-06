package jack.bai.studio.myaccount;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.MonthDisplayHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class QueryFrag extends Fragment {

	private Button queryDateBtn, queryMonthBtn, queryAllBtn;
	private TextView q_start_monthTx, q_stop_monthTx;
	private TextView q_dateTx;

	private DatePickerDialog qr_datePickerDialog;
	private DatePickerDialog qr_DatePickerDialog_month_start;
	private DatePickerDialog qr_DatePickerDialog_month_stop;
	private String monthOfYearStr = "";
	private String dayOfMonthStr = "";

	private MyDBHelper dbHelper;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.query_frag, container, false);

		dbHelper = new MyDBHelper(getActivity(), "myAccountBook.db3", 1);

		queryDateBtn = (Button) view.findViewById(R.id.queryDateBtn);
		queryMonthBtn = (Button) view.findViewById(R.id.queryMonthBtn);
		queryAllBtn = (Button) view.findViewById(R.id.queryAllBtn);
		q_dateTx = (TextView) view.findViewById(R.id.queryDateTx);
		q_start_monthTx = (TextView) view.findViewById(R.id.start_queryMonthTx);
		q_stop_monthTx = (TextView) view.findViewById(R.id.stop_queryMonthTx);

		q_dateTx.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				qr_datePickerDialog = new DatePickerDialog(getActivity(),
						new OnDateSetListener() {

							@Override
							public void onDateSet(DatePicker view, int year,
									int monthOfYear, int dayOfMonth) {
								if (monthOfYear < 9) {
									monthOfYearStr = "0" + (monthOfYear + 1);
								} else {
									monthOfYearStr = "" + (monthOfYear + 1);
								}
								if (dayOfMonth < 10) {
									dayOfMonthStr = "0" + dayOfMonth;
								} else {
									dayOfMonthStr = "" + dayOfMonth;
								}
								q_dateTx.setText(year + "-" + monthOfYearStr
										+ "-" + dayOfMonthStr);
							}
						}, 2015, 5, 15);
				qr_datePickerDialog.show();
			}
		});

		q_start_monthTx.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				qr_DatePickerDialog_month_start = new DatePickerDialog(
						getActivity(), new OnDateSetListener() {

							@Override
							public void onDateSet(DatePicker view, int year,
									int monthOfYear, int dayOfMonth) {
								if (monthOfYear < 9) {
									monthOfYearStr = "0" + (monthOfYear + 1);
								} else {
									monthOfYearStr = "" + (monthOfYear + 1);
								}
								if (dayOfMonth < 10) {
									dayOfMonthStr = "0" + dayOfMonth;
								} else {
									dayOfMonthStr = "" + dayOfMonth;
								}
								q_start_monthTx.setText(year + "-"
										+ monthOfYearStr + "-" + dayOfMonthStr);

							}
						}, 2015, 5, 1);
				qr_DatePickerDialog_month_start.show();

			}
		});

		q_stop_monthTx.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				qr_DatePickerDialog_month_stop = new DatePickerDialog(
						getActivity(), new OnDateSetListener() {

							@Override
							public void onDateSet(DatePicker view, int year,
									int monthOfYear, int dayOfMonth) {
								if (monthOfYear < 9) {
									monthOfYearStr = "0" + (monthOfYear + 1);
								} else {
									monthOfYearStr = "" + (monthOfYear + 1);
								}
								if (dayOfMonth < 10) {
									dayOfMonthStr = "0" + dayOfMonth;
								} else {
									dayOfMonthStr = "" + dayOfMonth;
								}
								q_stop_monthTx.setText(year + "-"
										+ monthOfYearStr + "-" + dayOfMonthStr);

							}
						}, 2015, 5, 1);
				qr_DatePickerDialog_month_stop.show();

			}
		});

		// ================= 按照日期查询 =================
		queryDateBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 获取用户输入
				String date_key = q_dateTx.getText().toString();
				Log.e("查询句柄：", date_key);
				// 执行查询
				Cursor cursor = dbHelper.getReadableDatabase().rawQuery(
						"select * from account where date=?",
						new String[] { date_key });
				ArrayList<Map<String, String>> result = convertCursor2List(cursor);

				Cursor cursor2 = dbHelper.getReadableDatabase().rawQuery(
						SQLAttributes.SQL_SUM_EX_MONEY_BY_DATE,
						new String[] { date_key });
				String sumExMoney = "0";
				while (cursor2.moveToNext()) {
					sumExMoney = cursor2.getString(0);
				}
				Log.e("日期查询结果", "总支出：" + sumExMoney);

				Cursor cursor3 = dbHelper
						.getReadableDatabase()
						.rawQuery(
								"select sum(money) from account where ex_in_type=1 and date=?",
								new String[] { date_key });
				String sumInMoney = "0";
				while (cursor3.moveToNext()) {
					sumInMoney = cursor3.getString(0);
				}
				Log.e("日期查询结果", "总收入：" + sumInMoney);

				// 将查询的数据放入Bundle
				Bundle queryResult = new Bundle();
				queryResult.putSerializable("result", result);
				queryResult.putString("sumExMoney", sumExMoney);
				queryResult.putString("sumInMoney", sumInMoney);

				if (result.size() != 0) {
					// 跳转QueryResultActivity
					Intent intent = new Intent();
					intent.putExtras(queryResult);
					intent.setClass(getActivity(), QueryResultActivity.class);
					startActivity(intent);

					Log.e("日期查询结果", "result大小：" + result.size());
				} else {
					Toast.makeText(getActivity(), "查询为空！", Toast.LENGTH_SHORT)
							.show();
				}
			}
		});

		// ========================== 按照月份查询 ================================//
		queryMonthBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String startMonthKey = q_start_monthTx.getText().toString();
				String stopMonthKey = q_stop_monthTx.getText().toString();
				Log.e("查询句柄", startMonthKey + " to " + stopMonthKey);
				// 执行查询
				Cursor cursor = dbHelper.getReadableDatabase().rawQuery(
						"select * from account where date between ? and ?",
						new String[] { startMonthKey, stopMonthKey });
				ArrayList<Map<String, String>> result = convertCursor2List(cursor);

				Cursor cursor2 = dbHelper
						.getReadableDatabase()
						.rawQuery(
								"select sum(money) from account where ex_in_type=0 and date between ? and ?",
								new String[] { startMonthKey, stopMonthKey });
				String sumExMoney = "0";
				while (cursor2.moveToNext()) {
					sumExMoney = cursor2.getString(0);
				}
				Log.e("时间段查询", "总支出：" + sumExMoney);

				Cursor cursor3 = dbHelper
						.getReadableDatabase()
						.rawQuery(
								"select sum(money) from account where ex_in_type=1 and date between ? and ?",
								new String[] { startMonthKey, stopMonthKey });
				String sumInMoney = "0";
				while (cursor3.moveToNext()) {
					sumInMoney = cursor3.getString(0);
				}
				Log.e("时间段查询", "总收入：" + sumInMoney);

				// 将查询数据放入Bundle
				Bundle queryResult = new Bundle();
				queryResult.putSerializable("result", result);
				queryResult.putString("sumExMoney", sumExMoney);
				queryResult.putString("sumInMoney", sumInMoney);

				if (result.size() != 0) {
					Intent intent = new Intent();
					intent.putExtras(queryResult);
					intent.setClass(getActivity(), QueryResultActivity.class);
					startActivity(intent);

					Log.e("时间段查询", "result大小:" + result.size());
				} else {
					Toast.makeText(getActivity(), "查询为空！", Toast.LENGTH_SHORT)
							.show();
				}
			}
		});

		// ========================== 查询全部 ===================================//
		queryAllBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Cursor cursor = dbHelper.getReadableDatabase().rawQuery(
						"select * from account", null);
				ArrayList<Map<String, String>> result = convertCursor2List(cursor);

				Cursor cursor2 = dbHelper.getReadableDatabase().rawQuery(
						SQLAttributes.SQL_SUM_MONEY_ALL, null);
				String sumExAll = "0";
				while (cursor2.moveToNext()) {
					sumExAll = cursor2.getString(0);
				}

				Cursor cursor3 = dbHelper.getReadableDatabase().rawQuery(
						"select sum(money) from account where ex_in_type=1",
						null);
				String sumInAll = "0";
				while (cursor3.moveToNext()) {
					sumInAll = cursor3.getString(0);
				}

				// 将查询的数据放入Bundle
				Bundle queryResult = new Bundle();
				queryResult.putSerializable("result", result);
				queryResult.putString("sumExMoney", sumExAll);
				queryResult.putString("sumInMoney", sumInAll);

				if (result.size() != 0) {
					// 跳转QueryResultActivity
					Intent intent = new Intent();
					intent.putExtras(queryResult);
					intent.setClass(getActivity(), QueryResultActivity.class);
					startActivity(intent);

					Log.e("查询全部结果", "result大小：" + result.size());
				} else {
					Toast.makeText(getActivity(), "查询为空！", Toast.LENGTH_SHORT)
							.show();
				}
			}
		});

		return view;
	}

	/**
	 * 将Cursor转化为一个List
	 * 
	 * @param cursor
	 * @return
	 */
	protected ArrayList<Map<String, String>> convertCursor2List(Cursor cursor) {
		ArrayList<Map<String, String>> result = new ArrayList<Map<String, String>>();
		// 遍历Cursor结果
		while (cursor.moveToNext()) {
			// 将结果集中的数据存入ArrayList中
			Map<String, String> map = new HashMap<String, String>();
			map.put("_id", cursor.getString(0));
			map.put("ex_in_type", cursor.getString(1));
			map.put("type", cursor.getString(2));
			map.put("remarks", cursor.getString(3));
			map.put("money", cursor.getString(4));
			map.put("date", cursor.getString(5));
			map.put("time", cursor.getString(6));
			result.add(map);
		}
		return result;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		// 退出时关闭dbHelper中的SQLiteDatabase
		if (dbHelper != null) {
			dbHelper.close();
		}
		if (qr_datePickerDialog != null) {
			qr_datePickerDialog.dismiss();
		}
	}
}
