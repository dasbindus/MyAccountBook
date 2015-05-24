package jack.bai.studio.myaccount;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ExpenditureFrag extends Fragment {

	/** 调试标识 */
	private final static String TAG = ExpenditureFrag.class.getSimpleName();

	private Spinner mSpinner;
	private RadioGroup mRadioGroup;
	private EditText ex_remarksTx, ex_moneyTx;
	TextView ex_dateTx;
	private DatePickerDialog datePickerDialog;
	private boolean isUpdate = false;

	/** 数据库Helper工具 */
	private MyDBHelper dbHelper;
	/** 提交按钮 */
	private Button submitBtn = null;

	/** 待写入数据库的数据 */
	private String[] ex_data = new String[5];
	/** RadioButton选中的数据 */
	private String rbData = "早上";
	private String monthOfYearStr = "";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.expenditure_frag,
				container, false);

		// 创建MyDBHelper对象，指定数据库的版本为1
		// 此处使用相对路径即可：数据库文件自动保存在程序的数据文件夹的databases目录下
		dbHelper = new MyDBHelper(getActivity(), "myAccountBook.db3", 1);

		ex_remarksTx = (EditText) view.findViewById(R.id.remarksTx);
		ex_moneyTx = (EditText) view.findViewById(R.id.moneyTx);
		ex_dateTx = (TextView) view.findViewById(R.id.dateTx);

		// ----DatePickerDialog---
		ex_dateTx.setOnClickListener(new OnClickListener() {

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
								ex_dateTx.setText(year + "-" + monthOfYearStr
										+ "-" + dayOfMonth);
							}
						}, 2015, 5, 15);
				datePickerDialog.show();
			}
		});

		// ----------------------Spinner----------------------//
		mSpinner = (Spinner) view.findViewById(R.id.spinner1);
		String[] mSpinnerItems = getResources().getStringArray(
				R.array.spinnername_expend);
		final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
				getActivity(), android.R.layout.simple_spinner_item,
				mSpinnerItems);
		arrayAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mSpinner.setAdapter(arrayAdapter);
		mSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View v,
					int position, long id) {
				// 将选中的项目写入ex_data中等待写入数据库
				ex_data[0] = arrayAdapter.getItem(position);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				Toast.makeText(getActivity(), "请选择类别！", Toast.LENGTH_SHORT)
						.show();
			}
		});

		// ----------------------RadioGroup----------------------//
		mRadioGroup = (RadioGroup) view.findViewById(R.id.radioGroup1);
		mRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup parent, int position) {
				// 获取选中项的ID
				int radioButtonId = parent.getCheckedRadioButtonId();
				// 根据ID获取RadioButton的实例
				RadioButton rb = (RadioButton) view.findViewById(radioButtonId);
				// 获取选中项的内容
				// Log.e(TAG, "选中的RadioButton是：" + rb.getText());
				rbData = rb.getText().toString();
			}
		});

		// ----------------------Submit Button----------------------//
		submitBtn = (Button) view.findViewById(R.id.submitBtn);
		submitBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// 获取用户输入的数据
				ex_data[1] = ex_remarksTx.getText().toString();
				ex_data[2] = ex_moneyTx.getText().toString();
				ex_data[3] = ex_dateTx.getText().toString();
				ex_data[4] = rbData;

				// 检查输入
				isUpdate = checkInput(ex_data[1], ex_data[2], ex_data[3]);

				if (isUpdate) {
					Log.e(TAG, "写入的数据为: " + ex_data[0] + ',' + ex_data[1] + ','
							+ ex_data[2] + ',' + ex_data[3] + ',' + ex_data[4]);
					// 插入账目记录
					insertData(dbHelper.getReadableDatabase(), ex_data);
					// 清空输入
					clearInput();
					// 显示提示信息
					Toast.makeText(getActivity(), "添加支出记录成功！",
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
		ex_remarksTx.setText("");
		ex_moneyTx.setText("");
		ex_dateTx.setText("");
	}

	/**
	 * 检查输入是否符合规范
	 * 
	 * @param remarks
	 * @param money
	 * @param date
	 * @return
	 */
	private boolean checkInput(String remarks, String money, String date) {
		if ("".equals(remarks) || "".equals(money) || "".equals(date)) {
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
	private void insertData(SQLiteDatabase db, String[] data) {
		// 执行插入语句
		db.execSQL("insert into expend_book values(null, ?, ?, ?, ?, ?)", data);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		// 退出时关闭dbHelper中的SQLiteDatabase
		if (dbHelper != null) {
			dbHelper.close();
		}
		if (datePickerDialog != null) {
			datePickerDialog.dismiss();
		}
	}
}
