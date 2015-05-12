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
				// ��ȡ�û�����
				String date_key = q_dateTx.getText().toString();
				Log.e("��ѯ�����", date_key);
				// ִ�в�ѯ
				Cursor cursor = dbHelper.getReadableDatabase().rawQuery(
						"select * from expend_book where ex_date=?",
						new String[] { date_key });
				ArrayList<Map<String, String>> result = convertCursor2List(cursor);
				if (result.size() != 0) {
					Log.e("��ѯ�����", result.get(0).get("ex_money"));
				} else {
					Log.e("��ѯΪ��", "��ѯΪ��");
				}

			}
		});

		return view;
	}

	protected ArrayList<Map<String, String>> convertCursor2List(Cursor cursor) {
		ArrayList<Map<String, String>> result = new ArrayList<Map<String, String>>();
		// ����Cursor���
		while (cursor.moveToNext()) {
			// ��������е����ݴ���ArrayList��
			Map<String, String> map = new HashMap<String, String>();
			map.put("ex_money", cursor.getString(3));
			result.add(map);
		}
		return result;
	}
}
