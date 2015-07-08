package jack.bai.studio.myaccount;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class ChartActivity extends Activity {

	private WebView webView;

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
		webView.addJavascriptInterface(new JSInterface(this), "Android");
		// 加载assets目录下的文件
		String url = "file:///android_asset/test.html";
		webView.loadUrl(url);
		// webView.loadUrl("http://www.baidu.com"); // for test
	}

}
