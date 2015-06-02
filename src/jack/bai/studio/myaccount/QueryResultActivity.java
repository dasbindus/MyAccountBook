package jack.bai.studio.myaccount;

import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class QueryResultActivity extends Activity {

	private ListView resultList;
	private TextView sumTx;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_query_result);

		setTitle("查询结果");
		resultList = (ListView) findViewById(R.id.resultList);
		sumTx = (TextView) findViewById(R.id.sumTx);

		Intent intent = getIntent();
		Bundle resultData = intent.getExtras();
		@SuppressWarnings("unchecked")
		List<Map<String, String>> listData = (List<Map<String, String>>) resultData
				.get("result");
		String sumMoney = resultData.getString("sum");
		sumTx.setText(sumMoney);

		// 添加Adapter
		SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), listData,
				R.layout.list_item, new String[] { "_id", "ex_in_type", "type",
						"remarks", "date", "time", "money" }, new int[] {
						R.id.qr_id, R.id.qr_ex_in_type, R.id.qr_type,
						R.id.qr_remarks, R.id.qr_date, R.id.qr_time,
						R.id.qr_money });
		resultList.setAdapter(adapter);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

}
