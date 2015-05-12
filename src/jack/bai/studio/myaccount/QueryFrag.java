package jack.bai.studio.myaccount;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

public class QueryFrag extends Fragment {

	private Button queryBtn;
	private EditText q_dateTx;

	private MyDBHelper dbHelper;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.query_frag, container, false);

		dbHelper = new MyDBHelper(getActivity(), "myAccountBook.db3", 1);

		queryBtn = (Button) view.findViewById(R.id.queryBtn);
		q_dateTx = (EditText) view.findViewById(R.id.queryDateTx);

		queryBtn.setOnClickListener(new OnClickListener() {

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
				if (result.size() != 0) {
					Log.e("查询结果：", result.get(0).get("ex_money"));
				} else {
					Log.e("查询为空", "查询为空");
				}

			}
		});

		return view;
	}

	protected ArrayList<Map<String, String>> convertCursor2List(Cursor cursor) {
		ArrayList<Map<String, String>> result = new ArrayList<Map<String, String>>();
		// 遍历Cursor结果
		while (cursor.moveToNext()) {
			// 将结果集中的数据存入ArrayList中
			Map<String, String> map = new HashMap<String, String>();
			map.put("ex_money", cursor.getString(3));
			result.add(map);
		}
		return result;
	}
}
