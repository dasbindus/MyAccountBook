package jack.bai.studio.myaccount;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class QueryFrag extends Fragment {

	private Button queryBtn, queryAllBtn;
	private EditText q_dateTx;
	private TextView q_resultTx;

	private MyDBHelper dbHelper;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.query_frag, container, false);

		dbHelper = new MyDBHelper(getActivity(), "myAccountBook.db3", 1);

		queryBtn = (Button) view.findViewById(R.id.queryBtn);
		queryAllBtn = (Button) view.findViewById(R.id.queryAllBtn);
		q_dateTx = (EditText) view.findViewById(R.id.queryDateTx);
		q_resultTx = (TextView) view.findViewById(R.id.queryResultTx);

		queryBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// ��ȡ�û�����
				String date_key = q_dateTx.getText().toString();
				Log.e("��ѯ�����", date_key);
				// ִ�в�ѯ
				Cursor cursor = dbHelper.getReadableDatabase().rawQuery(
						"select * from expend_book where ex_date=?",
						new String[] { date_key });
				ArrayList<Map<String, String>> result = convertCursor2List(cursor);

				// ��ѯ��͵�cursor
				Cursor cursor2 = dbHelper.getReadableDatabase().rawQuery(
						SQLAttributes.SQL_SUM_MONEY_BY_DATE,
						new String[] { date_key });
				String sumMoneyByDate = "0";
				while (cursor2.moveToNext()) {
					sumMoneyByDate = cursor2.getString(0);
				}
				Log.e("test", "���Ϊ��" + sumMoneyByDate);

				// ����ѯ�����ݷ���Bundle
				Bundle queryResult = new Bundle();
				queryResult.putSerializable("result", result);
				queryResult.putString("sum", sumMoneyByDate);

				if (result.size() != 0) {
					// ��תQueryResultActivity
					Intent intent = new Intent();
					intent.putExtras(queryResult);
					intent.setClass(getActivity(), QueryResultActivity.class);
					startActivity(intent);

					Log.e("��ѯ�����", "result��С��" + result.size());
				} else {
					Toast.makeText(getActivity(), "��ѯΪ�գ�", Toast.LENGTH_SHORT)
							.show();
				}
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

				// ����ѯ�����ݷ���Bundle
				Bundle queryResult = new Bundle();
				queryResult.putSerializable("result", result);
				queryResult.putString("sum", sumAll);

				if (result.size() != 0) {
					// ��תQueryResultActivity
					Intent intent = new Intent();
					intent.putExtras(queryResult);
					intent.setClass(getActivity(), QueryResultActivity.class);
					startActivity(intent);

					Log.e("��ѯȫ�������", "result��С��" + result.size());
				} else {
					Toast.makeText(getActivity(), "��ѯΪ�գ�", Toast.LENGTH_SHORT)
							.show();
				}
			}
		});

		return view;
	}

	/**
	 * ��Cursorת��Ϊһ��List
	 * 
	 * @param cursor
	 * @return
	 */
	protected ArrayList<Map<String, String>> convertCursor2List(Cursor cursor) {
		ArrayList<Map<String, String>> result = new ArrayList<Map<String, String>>();
		// ����Cursor���
		while (cursor.moveToNext()) {
			// ��������е����ݴ���ArrayList��
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
		// �˳�ʱ�ر�dbHelper�е�SQLiteDatabase
		if (dbHelper != null) {
			dbHelper.close();
		}
	}

}
