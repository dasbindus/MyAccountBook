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

		setTitle("查询结果");
		resultList = (ListView) findViewById(R.id.resultList);
		sumTx = (TextView) findViewById(R.id.sumTx);
		sumExMoneyTx = (TextView) findViewById(R.id.sumExpendTx);
		sumInMoneyTx = (TextView) findViewById(R.id.sumIncomeTx);

		Intent intent = getIntent();
		Bundle resultData = intent.getExtras();
		@SuppressWarnings("unchecked")
		List<Map<String, String>> listData = (List<Map<String, String>>) resultData
				.get("result");
		String sumExMoney = resultData.getString("sumExMoney");
		String sumInMoney = resultData.getString("sumInMoney");
		if (sumExMoney != null && sumInMoney != null) {// 判断不为空，防止空指针异常
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

		// 添加Adapter
		SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), listData,
				R.layout.list_item, new String[] { "_id", "ex_in_type", "type",
						"remarks", "date", "time", "money" }, new int[] {
						R.id.qr_id, R.id.qr_ex_in_type, R.id.qr_type,
						R.id.qr_remarks, R.id.qr_date, R.id.qr_time,
						R.id.qr_money });
		resultList.setAdapter(adapter);

		// 长按事件
		resultList.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				final int pos = position;

				AlertDialog.Builder builder = new AlertDialog.Builder(
						QueryResultActivity.this);
				builder.setTitle("删除").setIcon(R.drawable.ic_quit)
						.setMessage("更新/删除")
						.setPositiveButton("更新", new OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								Toast.makeText(QueryResultActivity.this, "更新",
										Toast.LENGTH_SHORT).show();
							}
						}).setNegativeButton("删除", new OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								Toast.makeText(QueryResultActivity.this, "删除",
										Toast.LENGTH_SHORT).show();
								String args[] = { String.valueOf(pos + 1) };
								Log.d("test", "_id : " + args[0]);
								SQLiteDatabase db = dbHelper
										.getReadableDatabase();
								int result = db
										.delete("account", "_id=?", args);
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
