package jack.bai.studio.myaccount;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends FragmentActivity {

	private ViewPager mPager;
	private MyPagerAdapter mPagerAdapter;
	
	private ImageView mExpendPic, mIncomePic, mQueryPic;
	private TextView t1, t2, t3;
	private int currentIndex = 0;

	private ExpenditureFrag mExpenditureFrag = new ExpenditureFrag();
	private IncomeFrag mIncomeFrag = new IncomeFrag();
	private QueryFrag mQueryFrag = new QueryFrag();

	private static final int TAB_INDEX_COUNT = 3;
	private static final int TAB_INDEX_ONE = 0;
	private static final int TAB_INDEX_TWO = 1;
	private static final int TAB_INDEX_THREE = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * ≥ı ºªØActionBar
	 */
	private void setUpActionBar() {
		final ActionBar actionbar = getActionBar();
		actionbar.setHomeButtonEnabled(false);
		actionbar.setDisplayShowTitleEnabled(false);
		actionbar.setDisplayShowHomeEnabled(true);
	}

	/**
	 * ≥ı ºªØÕ∑±Í
	 */
	private void InitTextView() {
		t1 = (TextView) findViewById(R.id.expenditureTx);
		t2 = (TextView) findViewById(R.id.incomeTx);
		t3 = (TextView) findViewById(R.id.queryTx);

		t1.setOnClickListener(new MyOnClickListener(0));
		t2.setOnClickListener(new MyOnClickListener(1));
		t3.setOnClickListener(new MyOnClickListener(2));
	}

	/**
	 * ≥ı ºªØViewPager
	 */
	private void InitViewPager() {
		mPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
		mPager = (ViewPager) findViewById(R.id.vPager);
		mPager.setAdapter(mPagerAdapter);
		mPager.setCurrentItem(0);
		mPager.setOnPageChangeListener(new MyOnPageChangeListener());
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

	/**
	 * Õ∑±Íµ„ª˜º‡Ã˝
	 */
	public class MyOnClickListener implements View.OnClickListener {
		private int index = 0;

		public MyOnClickListener(int i) {
			index = i;
		}

		@Override
		public void onClick(View v) {
			mPager.setCurrentItem(index);
		}
	};
	
	/**
	 * “≥ø®«–ªªº‡Ã˝
	 */
	public class MyOnPageChangeListener implements OnPageChangeListener {

//		int one = offset * 2 + bmpW;// “≥ø®1 -> “≥ø®2 ∆´“∆¡ø
//		int two = one * 2;// “≥ø®1 -> “≥ø®3 ∆´“∆¡ø
//		int three = one * 3;//“≥ø®1 -> “≥ø®4∆´“∆¡ø

		@Override
		public void onPageSelected(int arg0) {
//			Animation animation = new TranslateAnimation(one * currIndex, one
//					* arg0, 0, 0);
//			currIndex = arg0;
//			animation.setFillAfter(true);// True:Õº∆¨Õ£‘⁄∂Øª≠Ω· ¯Œª÷√
//			animation.setDuration(300);
//			cursor.startAnimation(animation);
			System.out.println(arg0);
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	}

	/**
	 * ViewPager  ≈‰∆˜
	 * 
	 * @author JackBai
	 * 
	 */
	public class MyPagerAdapter extends FragmentPagerAdapter {

		public MyPagerAdapter(FragmentManager fragmentManager) {
			super(fragmentManager);
		}

		@Override
		public Fragment getItem(int position) {
			switch (position) {
			case TAB_INDEX_ONE:
				return mExpenditureFrag;
			case TAB_INDEX_TWO:
				return mIncomeFrag;
			case TAB_INDEX_THREE:
				return mQueryFrag;
			}
			throw new IllegalStateException("No fragment at position "
					+ position);
		}

		@Override
		public int getCount() {
			return TAB_INDEX_COUNT;
		}
	}
}
