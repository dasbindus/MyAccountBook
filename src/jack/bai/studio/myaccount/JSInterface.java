package jack.bai.studio.myaccount;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

class JSInterface {
	Context mContext;

	public JSInterface(Context c) {
		mContext = c;
	}

	/** 该方法将暴露给js */
	@JavascriptInterface
	public void showToast(String str) {
		Toast.makeText(mContext, "Hi!" + str, Toast.LENGTH_SHORT).show();
	}
}
