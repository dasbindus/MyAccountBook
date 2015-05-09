package jack.bai.studio.myaccount;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends FragmentActivity {

	private static final String TAG = MainActivity.class.getSimpleName();

	private FragmentTransaction fragmentTransaction;
	private FragmentManager mFragmentManager;

	private ImageView mExpendPic, mIncomePic, mQueryPic;
	private TextView t1, t2, t3;
	private int currentIndex = 0;

	private ExpenditureFrag mExpenditureFrag = new ExpenditureFrag();
	private IncomeFrag mIncomeFrag = new IncomeFrag();
	private QueryFrag mQueryFrag = new QueryFrag();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		setUpActionBar();

		mFragmentManager = getSupportFragmentManager();

		mExpendPic = (ImageView) findViewById(R.id.expenditurePic);
		mIncomePic = (ImageView) findViewById(R.id.incomePic);
		mQueryPic = (ImageView) findViewById(R.id.queryPic);

		View frameView = findViewById(R.id.my_frame);
		onExpendClick(frameView);
	}

	/**
	 * 初始化ActionBar
	 */
	private void setUpActionBar() {
		final ActionBar actionbar = getActionBar();
		actionbar.setHomeButtonEnabled(false);
		actionbar.setDisplayShowTitleEnabled(false);
		actionbar.setDisplayShowHomeEnabled(true);
	}

	/**
	 * 第一个TAB的单击事件
	 * 
	 * @param v
	 */
	public void onExpendClick(View v) {
		Log.d(TAG, "---->单击第1个TAB");

		if (!mExpenditureFrag.isAdded()) {
			fragmentTransaction = mFragmentManager.beginTransaction();
			fragmentTransaction.replace(R.id.my_frame, mExpenditureFrag);
			fragmentTransaction.commit();

			mExpendPic.setImageDrawable(getResources().getDrawable(
					R.drawable.ic_launcher));
			mIncomePic.setImageDrawable(getResources().getDrawable(
					R.drawable.ic_launcher));
			mQueryPic.setImageDrawable(getResources().getDrawable(
					R.drawable.ic_launcher));
		}

	}

	/**
	 * 第二个TAB的单击事件
	 * 
	 * @param v
	 */
	public void onIncomeClick(View v) {
		Log.d(TAG, "---->单击第2个TAB");

		if (!mIncomeFrag.isAdded()) {
			fragmentTransaction = mFragmentManager.beginTransaction();
			fragmentTransaction.replace(R.id.my_frame, mIncomeFrag);
			fragmentTransaction.commit();

			mExpendPic.setImageDrawable(getResources().getDrawable(
					R.drawable.ic_launcher));
			mIncomePic.setImageDrawable(getResources().getDrawable(
					R.drawable.ic_launcher));
			mQueryPic.setImageDrawable(getResources().getDrawable(
					R.drawable.ic_launcher));
		}

	}

	/**
	 * 第三个TAB的单击事件
	 * 
	 * @param v
	 */
	public void onQueryClick(View v) {
		Log.d(TAG, "---->单击第3个TAB");

		if (!mQueryFrag.isAdded()) {
			fragmentTransaction = mFragmentManager.beginTransaction();
			fragmentTransaction.replace(R.id.my_frame, mQueryFrag);
			fragmentTransaction.commit();

			mExpendPic.setImageDrawable(getResources().getDrawable(
					R.drawable.ic_launcher));
			mIncomePic.setImageDrawable(getResources().getDrawable(
					R.drawable.ic_launcher));
			mQueryPic.setImageDrawable(getResources().getDrawable(
					R.drawable.ic_launcher));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
