package jack.bai.studio.myaccount;

import java.util.List;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

//class JSInterface {
//	Context mContext;
//
//	public JSInterface(Context c) {
//		mContext = c;
//	}
//
//	/** 该方法将暴露给javascript */
//	@JavascriptInterface
//	public void showToast(String str) {
//		Toast.makeText(mContext, "Hi!" + str, Toast.LENGTH_SHORT).show();
//	}
//}

public class JSInterface {
	public static final String TAG = JSInterface.class.getSimpleName();
	private Context mContext = null;
	private Handler mHandler = null;
	private WebView mView;

	private JSONArray jsonArray = new JSONArray();
	private Random random = new Random();

	public JSInterface(Context context, Handler handler, WebView webView) {
		mContext = context;
		mHandler = handler;
		mView = webView;
	}

	public void init() {
		// 通过handler来确保init方法的执行在主线程中
		mHandler.post(new Runnable() {

			public void run() {
				// 调用网页setContactInfo方法
				mView.loadUrl("javascript：setContactInfo('" + getJsonStr()
						+ "')");
			}
		});
	}

	public int getW() {
		return px2dip(mContext.getResources().getDisplayMetrics().widthPixels);
	}

	public int getH() {
		return px2dip(mContext.getResources().getDisplayMetrics().heightPixels);
	}

	public int px2dip(float pxValue) {
		final float scale = mContext.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	public void setValue(String name, String value) {
		Toast.makeText(mContext, name + " " + value + "%", Toast.LENGTH_SHORT)
				.show();
	}

	@SuppressLint("DefaultLocale")
	public String getRandColorCode() {
		String r, g, b;
		Random random = new Random();
		r = Integer.toHexString(random.nextInt(256)).toUpperCase();
		g = Integer.toHexString(random.nextInt(256)).toUpperCase();
		b = Integer.toHexString(random.nextInt(256)).toUpperCase();

		r = r.length() == 1 ? "0" + r : r;
		g = g.length() == 1 ? "0" + g : g;
		b = b.length() == 1 ? "0" + b : b;

		return "#" + r + g + b;
	}

	public String getJsonStr() {
		try {

			for (int i = 0; i < 10; i++) {
				JSONObject object1 = new JSONObject();
				object1.put("name", "name" + i);
				object1.put("value", random.nextInt(30));
				object1.put("color", getRandColorCode());
				jsonArray.put(object1);
			}
			Log.i("", jsonArray.toString());
			return jsonArray.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 实现将list转换成json格式字符串
	 * <p>
	 * 该方法将暴露给javascript
	 * </p>
	 * 
	 * @return json格式的字符串
	 */
	@JavascriptInterface
	public String getContacts() {
		ContactService contactService = new ContactService();
		List<Contact> contacts = contactService.getContacts();
		String json = null;
		try {
			JSONArray array = new JSONArray();
			for (Contact contact : contacts) {

				JSONObject item = new JSONObject();
				item.put("name", contact.getName());
				item.put("value", contact.getValue());
				item.put("color", contact.getColor());
				array.put(item);
			}
			json = array.toString();
			Log.i(TAG, json);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json;
	}
}