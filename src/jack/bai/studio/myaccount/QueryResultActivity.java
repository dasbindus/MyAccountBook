package jack.bai.studio.myaccount;

import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class QueryResultActivity extends Activity {

	private ListView resultList;
	private TextView sumTx, sumExMoneyTx, sumInMoneyTx;

	private MyDBHelper dbHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_query_result);

		dbHelper = new MyDBHelper(QueryResultActivity.this,
				"myAccountBook.db3", 1);

		setTitle("��ѯ���");
		resultList = (ListView) findViewById(R.id.resultList);
		sumTx = (TextView) findViewById(R.id.sumTx);
		sumExMoneyTx = (TextView) findViewById(R.id.sumExpendTx);
		sumInMoneyTx = (TextView) findViewById(R.id.sumIncomeTx);

		Intent intent = getIntent();
		Bundle resultData = intent.getExtras();
		@SuppressWarnings("unchecked")
		final
		List<Map<String, String>> listData = (List<Map<String, String>>) resultData
				.get("result");
		String sumExMoney = resultData.getString("sumExMoney");
		String sumInMoney = resultData.getString("sumInMoney");
		if (sumExMoney != null && sumInMoney != null) {// �жϲ�Ϊ�գ���ֹ��ָ���쳣
			sumTx.setText(""
					+ (Float.parseFloat(sumInMoney) - Float
							.parseFloat(sumExMoney)));
		} else if (sumExMoney != null && sumInMoney == null) {
			sumTx.setText("" + (0 - Float.parseFloat(sumExMoney)));
		} else if (sumExMoney == null && sumInMoney != null) {
			sumTx.setText("" + (Float.parseFloat(sumInMoney) - 0));
		} else {
			sumTx.setText(0);
		}
		sumExMoneyTx.setText(sumExMoney);
		sumInMoneyTx.setText(sumInMoney);

		// ���Adapter
		final SimpleAdapter adapter = new SimpleAdapter(getBaseContext(),
				listData, R.layout.list_item, new String[] { "_id",
						"ex_in_type", "type", "remarks", "date", "time",
						"money" }, new int[] { R.id.qr_id, R.id.qr_ex_in_type,
						R.id.qr_type, R.id.qr_remarks, R.id.qr_date,
						R.id.qr_time, R.id.qr_money });
		resultList.setAdapter(adapter);

		// �����¼�
		resultList.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				final int pos = position;
				TextView idView = (TextView) view.findViewById(R.id.qr_id);
				String _id = idView.getText().toString();
				final String args[] = { _id };
				Log.d("test", "_id => " + args[0]);

				AlertDialog.Builder builder = new AlertDialog.Builder(
						QueryResultActivity.this);
				builder.setTitle("ɾ��").setIcon(R.drawable.ic_quit)
						.setMessage("����/ɾ��")
						.setPositiveButton("����", new OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								Toast.makeText(QueryResultActivity.this, "����",
										Toast.LENGTH_SHORT).show();
							}
						}).setNegativeButton("ɾ��", new OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								SQLiteDatabase db = dbHelper
										.getReadableDatabase();
								int resultcode = db.delete("account", "_id=?",
										args);
								Log.d("test", "resultcode => " + resultcode);
								if (1 == resultcode) {
									Toast.makeText(QueryResultActivity.this,
											"ɾ���ɹ���", Toast.LENGTH_SHORT).show();
									listData.remove(pos);
									adapter.notifyDataSetChanged();
								}

							}
						}).create().show();

				return true;
			}
		});
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

}
