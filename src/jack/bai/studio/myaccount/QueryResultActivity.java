package jack.bai.studio.myaccount;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class QueryResultActivity extends Activity {

	private ListView resultList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_query_result);

		resultList = (ListView) findViewById(R.id.resultList);
		// TODO ÎªListÌí¼ÓAdapter
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

}
