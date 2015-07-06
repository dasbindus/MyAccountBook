package jack.bai.studio.myaccount;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

public class GuidePage extends Activity {

	private static final String TAG = "GuidePage";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.guidepage);

		Thread guide = new Thread() {
			@Override
			public void run() {
				super.run();
				try {
					int wait = 0;
					while (wait < 2000) {
						sleep(100);
						wait += 100;
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					Intent intent = new Intent();
					intent.setClass(GuidePage.this, MainActivity.class);
					startActivity(intent);
					finish();
				}
			}

		};
		guide.start();
	}

}
