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
	private EditText q_monthTx;
	private TextView q_dateTx;

	private DatePickerDialog qr_datePickerDialog;

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
		q_monthTx = (EditText) view.findViewById(R.id.queryMonthTx);

		q_dateTx.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				qr_datePickerDialog = new DatePickerDialog(getActivity(),
						new OnDateSetListener() {

							@Override
							public void onDateSet(DatePicker view, int year,
									int monthOfYear, int dayOfMonth) {
								q_dateTx.setText(year + "-" + (monthOfYear + 1)
										+ "-" + dayOfMonth);
							}
						}, 2015, 10, 17);
				qr_datePickerDialog.show();
			}
		});

		queryDateBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 获取用户输入
				String date_key = q_dateTx.getText().toString();
				Log.e("查询句柄：", date_key);
				// 执行查询
				Cursor cursor = dbHelper.getReadableDatabase().rawQuery(
						"select * from expend_book where ex_date=?",
						new String[] { date_key });
				ArrayList<Map<String, String>> result = convertCursor2List(cursor);

				// 查询求和的cursor
				Cursor cursor2 = dbHelper.getReadableDatabase().rawQuery(
						SQLAttributes.SQL_SUM_MONEY_BY_DATE,
						new String[] { date_key });
				String sumMoneyByDate = "0";
				while (cursor2.moveToNext()) {
					sumMoneyByDate = cursor2.getString(0);
				}
				Log.e("test", "求和为：" + sumMoneyByDate);

				// 将查询的数据放入Bundle
				Bundle queryResult = new Bundle();
				queryResult.putSerializable("result", result);
				queryResult.putString("sum", sumMoneyByDate);

				if (result.size() != 0) {
					// 跳转QueryResultActivity
					Intent intent = new Intent();
					intent.putExtras(queryResult);
					intent.setClass(getActivity(), QueryResultActivity.class);
					startActivity(intent);

					Log.e("查询结果：", "result大小：" + result.size());
				} else {
					Toast.makeText(getActivity(), "查询为空！", Toast.LENGTH_SHORT)
							.show();
				}
			}
		});

		queryMonthBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

			}
		});

		queryAllBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Cursor cursor = dbHelper.getReadableDatabase().rawQuery(
						"select * from expend_book", null);
				ArrayList<Map<String, String>> result = convertCursor2List(cursor);

				Cursor cursor2 = dbHelper.getReadableDatabase().rawQuery(
						SQLAttributes.SQL_SUM_MONEY_ALL, null);
				String sumAll = "0";
				while (cursor2.moveToNext()) {
					sumAll = cursor2.getString(0);
				}

				// 将查询的数据放入Bundle
				Bundle queryResult = new Bundle();
				queryResult.putSerializable("result", result);
				queryResult.putString("sum", sumAll);

				if (result.size() != 0) {
					// 跳转QueryResultActivity
					Intent intent = new Intent();
					intent.putExtras(queryResult);
					intent.setClass(getActivity(), QueryResultActivity.class);
					startActivity(intent);

					Log.e("查询全部结果：", "result大小：" + result.size());
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
			map.put("ex_type", cursor.getString(1));
			map.put("ex_remarks", cursor.getString(2));
			map.put("ex_money", cursor.getString(3));
			map.put("ex_date", cursor.getString(4));
			map.put("ex_time", cursor.getString(5));
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
