package jack.bai.studio.myaccount;

//import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {

	private static final String TAG = MainActivity.class.getSimpleName();

	// ----------Fragment�л�----------------//
	private FragmentTransaction fragmentTransaction;
	private FragmentManager mFragmentManager;

	// ----------TAB��ͷ��---------//
	private TextView mExpend, mExpendEn, mIncome, mIncomeEn, mQuery, mQueryEn,
			title;

	// ----------����TAB��Ӧ��Fragment-------//
	private ExpenditureFrag mExpenditureFrag = new ExpenditureFrag();
	private IncomeFrag mIncomeFrag = new IncomeFrag();
	private QueryFrag mQueryFrag = new QueryFrag();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		mFragmentManager = getSupportFragmentManager();

		mExpend = (TextView) findViewById(R.id.expenditureTx);
		mIncome = (TextView) findViewById(R.id.incomeTx);
		mQuery = (TextView) findViewById(R.id.queryTx);

		mExpendEn = (TextView) findViewById(R.id.expenditureTxEn);
		mIncomeEn = (TextView) findViewById(R.id.incomeTxEn);
		mQueryEn = (TextView) findViewById(R.id.queryTxEn);

		title = (TextView) findViewById(R.id.actionbar_title);

		View frameView = findViewById(R.id.my_frame);
		onExpendClick(frameView);
	}

	// /**
	// * ��ʼ��ActionBar
	// */
	// private void setUpActionBar() {
	// final ActionBar actionbar = getActionBar();
	// actionbar.setHomeButtonEnabled(false);
	// actionbar.setDisplayShowTitleEnabled(true);
	// actionbar.setDisplayShowHomeEnabled(true);
	// }

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

			title.setText("֧��");
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

			title.setText("����");
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

			title.setText("��ѯ");
		}
	}

	/**
	 * ��������ͼ��
	 * 
	 * @param v
	 */
	public void callChart(View v) {
		Intent intent = new Intent();
		intent.setClass(MainActivity.this, ChartActivity.class);
		startActivity(intent);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			AlertDialog.Builder builder = new AlertDialog.Builder(
					MainActivity.this);
			builder.setTitle("�˳�").setIcon(R.drawable.ic_quit)
					.setMessage("���Ҫ�˳����˱���ô��")
					.setPositiveButton("�����˳�", new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							finish();
						}
					}).setNegativeButton("ȡ���˳�", new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							Toast.makeText(MainActivity.this, "ȡ���˳�",
									Toast.LENGTH_SHORT).show();
						}
					}).create().show();
			break;
		default:
			break;
		}
		return super.onKeyDown(keyCode, event);
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
