package jack.bai.studio.myaccount;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class ChartActivity extends Activity {

	private WebView webView;
	// private static Context mContext;
	private static Handler mHandler = new Handler();

	@SuppressLint({ "JavascriptInterface", "SetJavaScriptEnabled" })
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.charts);

		webView = (WebView) findViewById(R.id.webview);

		webView.setHorizontalScrollBarEnabled(true);
		webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_INSET);

		WebSettings settings = webView.getSettings();

		// 设置字符集编码
		settings.setDefaultTextEncodingName("UTF-8");

		// 开启javascript支持
		settings.setJavaScriptEnabled(true);
		settings.setSupportZoom(true);
		settings.setBuiltInZoomControls(true);
		webView.addJavascriptInterface(
				new JSInterface(this, mHandler, webView), JSInterface.TAG);
		// 加载assets目录下的文件
		// String url = "file:///android_asset/histogram.html";
		String url = "file:///android_asset/pie_chart.html";
		webView.loadUrl(url);
	}

	@Override
	protected void onDestroy() {
		webView = null;
		super.onDestroy();
	}
}
