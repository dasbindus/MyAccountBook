package jack.bai.studio.myaccount;

import android.app.ActionBar;
import android.graphics.Color;
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

	// ----------Fragment�л�----------------//
	private FragmentTransaction fragmentTransaction;
	private FragmentManager mFragmentManager;

	// ----------TAB��ͷ��---------//
	private TextView mExpend, mExpendEn, mIncome, mIncomeEn, mQuery, mQueryEn;
	private int currentIndex = 0;

	// ----------����TAB��Ӧ��Fragment-------//
	private ExpenditureFrag mExpenditureFrag = new ExpenditureFrag();
	private IncomeFrag mIncomeFrag = new IncomeFrag();
	private QueryFrag mQueryFrag = new QueryFrag();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		setUpActionBar();

		mFragmentManager = getSupportFragmentManager();

		mExpend = (TextView) findViewById(R.id.expenditureTx);
		mIncome = (TextView) findViewById(R.id.incomeTx);
		mQuery = (TextView) findViewById(R.id.queryTx);

		mExpendEn = (TextView) findViewById(R.id.expenditureTxEn);
		mIncomeEn = (TextView) findViewById(R.id.incomeTxEn);
		mQueryEn = (TextView) findViewById(R.id.queryTxEn);

		View frameView = findViewById(R.id.my_frame);
		onExpendClick(frameView);
	}

	/**
	 * ��ʼ��ActionBar
	 */
	private void setUpActionBar() {
		final ActionBar actionbar = getActionBar();
		actionbar.setHomeButtonEnabled(false);
		actionbar.setDisplayShowTitleEnabled(false);
		actionbar.setDisplayShowHomeEnabled(true);
	}

	/**
	 * ��һ��TAB�ĵ����¼�
	 * 
	 * @param v
	 */
	public void onExpendClick(View v) {
		Log.d(TAG, "---->������1��TAB");

		if (!mExpenditureFrag.isAdded()) {
			fragmentTransaction = mFragmentManager.beginTransaction();
			fragmentTransaction.replace(R.id.my_frame, mExpenditureFrag);
			fragmentTransaction.commit();

			mExpend.setTextColor(Color.GREEN);
			mIncome.setTextColor(Color.GRAY);
			mQuery.setTextColor(Color.GRAY);

			mExpendEn.setTextColor(Color.GREEN);
			mIncomeEn.setTextColor(Color.GRAY);
			mQueryEn.setTextColor(Color.GRAY);
		}

	}

	/**
	 * �ڶ���TAB�ĵ����¼�
	 * 
	 * @param v
	 */
	public void onIncomeClick(View v) {
		Log.d(TAG, "---->������2��TAB");

		if (!mIncomeFrag.isAdded()) {
			fragmentTransaction = mFragmentManager.beginTransaction();
			fragmentTransaction.replace(R.id.my_frame, mIncomeFrag);
			fragmentTransaction.commit();

			mExpend.setTextColor(Color.GRAY);
			mIncome.setTextColor(Color.GREEN);
			mQuery.setTextColor(Color.GRAY);

			mExpendEn.setTextColor(Color.GRAY);
			mIncomeEn.setTextColor(Color.GREEN);
			mQueryEn.setTextColor(Color.GRAY);
		}

	}

	/**
	 * ������TAB�ĵ����¼�
	 * 
	 * @param v
	 */
	public void onQueryClick(View v) {
		Log.d(TAG, "---->������3��TAB");

		if (!mQueryFrag.isAdded()) {
			fragmentTransaction = mFragmentManager.beginTransaction();
			fragmentTransaction.replace(R.id.my_frame, mQueryFrag);
			fragmentTransaction.commit();

			mExpend.setTextColor(Color.GRAY);
			mIncome.setTextColor(Color.GRAY);
			mQuery.setTextColor(Color.GREEN);

			mExpendEn.setTextColor(Color.GRAY);
			mIncomeEn.setTextColor(Color.GRAY);
			mQueryEn.setTextColor(Color.GREEN);
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
