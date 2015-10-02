package jack.bai.studio.myaccount;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class IncomeFrag extends Fragment {

	/** 调试标识 */
	private final static String TAG = IncomeFrag.class.getSimpleName();

	private Spinner mSpinner;
	private EditText in_remarkTx, in_moneyTx;
	private TextView in_dateTx;
	private DatePickerDialog datePickerDialog;
	private boolean isUpdate = false;

	/** 数据库Helper工具 */
	private MyDBHelper dbHelper;
	/** 提交按钮 */
	private Button submitBtn2 = null;

	// 待写入数据库的数据 //
	private int ex_in_type = 1; // 0 代表收入
	private String in_type;
	private String in_remark;
	private float in_money;
	private String in_date;
	private String in_time = "略";

	private String monthOfYearStr = "";
	private String dayOfMonthStr = "";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.income_frag, container, false);

		dbHelper = new MyDBHelper(getActivity(), "myAccountBook.db3", 1);

		in_remarkTx = (EditText) view.findViewById(R.id.incomeRemarksTx);
		in_moneyTx = (EditText) view.findViewById(R.id.incomeMoneyTx);
		in_dateTx = (TextView) view.findViewById(R.id.incomeDateTx);

		// ----------------------DatePickerDialog-------------------------//
		in_dateTx.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				datePickerDialog = new DatePickerDialog(getActivity(),
						new OnDateSetListener() {

							@Override
							public void onDateSet(DatePicker view, int year,
									int monthOfYear, int dayOfMonth) {
								if (monthOfYear < 9) {
									monthOfYearStr = "0" + (monthOfYear + 1);
								} else {
									monthOfYearStr = "" + (monthOfYear + 1);
								}
								if (dayOfMonth < 10) {
									dayOfMonthStr = "0" + dayOfMonth;
								} else {
									dayOfMonthStr = "" + dayOfMonth;
								}
								in_dateTx.setText(year + "-" + monthOfYearStr
										+ "-" + dayOfMonthStr);
							}
						}, 2015, 5, 15);
				datePickerDialog.show();
			}
		});

		// ---------------------------Spinner----------------------//
		mSpinner = (Spinner) view.findViewById(R.id.spinner2);
		String[] mSpinnerItems = getResources().getStringArray(
				R.array.spinnername_income);
		final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
				getActivity(), android.R.layout.simple_spinner_item,
				mSpinnerItems);
		arrayAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mSpinner.setAdapter(arrayAdapter);
		mSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View v,
					int position, long id) {
				in_type = arrayAdapter.getItem(position);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				Toast.makeText(getActivity(), "请选择类别！", Toast.LENGTH_SHORT)
						.show();
			}
		});

		// -------------------submit button-----------------------//
		submitBtn2 = (Button) view.findViewById(R.id.incomeSubmitBtn);
		submitBtn2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// 获取用户输入的数据
				in_remark = in_remarkTx.getText().toString();
				if (!("".equals(in_moneyTx.getText().toString()))) {
					in_money = Float
							.parseFloat(in_moneyTx.getText().toString());
				}
				in_date = in_dateTx.getText().toString();

				isUpdate = checkInput(in_remark, in_moneyTx.getText()
						.toString(), in_date);
				if (isUpdate) {
					Log.e(TAG, "写入的数据为: " + ex_in_type + ',' + in_type + ','
							+ in_remark + ',' + in_money + ',' + in_date);
					ContentValues values = new ContentValues();
					values.put("ex_in_type", ex_in_type);
					values.put("type", in_type);
					values.put("remarks", in_remark);
					values.put("money", in_money);
					values.put("date", in_date);
					values.put("time", in_time);
					// 插入账目记录
					insertData(dbHelper.getReadableDatabase(), values);
					// 清空输入
					clearInput();
					// 显示提示信息
					Toast.makeText(getActivity(), "添加收入记录成功！",
							Toast.LENGTH_SHORT).show();
				}
			}
		});

		return view;
	}

	/**
	 * 清空输入框
	 */
	private void clearInput() {
		in_remarkTx.setText("");
		in_moneyTx.setText("");
		in_dateTx.setText("");
	}

	/**
	 * 
	 * @param remarks
	 * @param money
	 * @param date
	 * @return
	 */
	private boolean checkInput(String remarks, String money, String date) {
		if ("".equals(money) || "".equals(date)) {
			Toast.makeText(getActivity(), "输入不能为空！", Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}

	/**
	 * 插入数据
	 * 
	 * @param db
	 * @param data
	 */
	private void insertData(SQLiteDatabase db, ContentValues values) {
		// 执行插入语句
		db.insert("account", null, values);
	}

}
