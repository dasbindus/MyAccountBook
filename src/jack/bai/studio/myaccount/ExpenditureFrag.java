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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ExpenditureFrag extends Fragment {

	/** ���Ա�ʶ */
	private final static String TAG = ExpenditureFrag.class.getSimpleName();

	private Spinner mSpinner;
	private RadioGroup mRadioGroup;
	private EditText ex_remarksTx, ex_moneyTx;
	private TextView ex_dateTx;
	private DatePickerDialog datePickerDialog;
	private boolean isUpdate = false;

	/** ���ݿ�Helper���� */
	private MyDBHelper dbHelper;
	/** �ύ��ť */
	private Button submitBtn = null;

	// ��д�����ݿ������ //
	private int ex_in_type = 0; // 0 ����֧��
	private String ex_type;
	private String ex_remark;
	private float ex_money = 0;
	private String ex_date;
	private String ex_time;

	/** RadioButtonѡ�е����� */
	private String rbData = "����";
	private String monthOfYearStr = "";
	private String dayOfMonthStr = "";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.expenditure_frag,
				container, false);

		// ����MyDBHelper����ָ�����ݿ�İ汾Ϊ1
		// �˴�ʹ�����·�����ɣ����ݿ��ļ��Զ������ڳ���������ļ��е�databasesĿ¼��
		dbHelper = new MyDBHelper(getActivity(), "myAccountBook.db3", 1);

		ex_remarksTx = (EditText) view.findViewById(R.id.exRemarksTx);
		ex_moneyTx = (EditText) view.findViewById(R.id.exMoneyTx);
		ex_dateTx = (TextView) view.findViewById(R.id.exDateTx);

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
								if (dayOfMonth < 10) {
									dayOfMonthStr = "0" + dayOfMonth;
								} else {
									dayOfMonthStr = "" + dayOfMonth;
								}
								ex_dateTx.setText(year + "-" + monthOfYearStr
										+ "-" + dayOfMonthStr);
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
				// ��ѡ�е���Ŀд��ex_data�еȴ�д�����ݿ�
				ex_type = arrayAdapter.getItem(position);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				Toast.makeText(getActivity(), "��ѡ�����", Toast.LENGTH_SHORT)
						.show();
			}
		});

		// ----------------------RadioGroup----------------------//
		mRadioGroup = (RadioGroup) view.findViewById(R.id.radioGroup1);
		mRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup parent, int position) {
				// ��ȡѡ�����ID
				int radioButtonId = parent.getCheckedRadioButtonId();
				// ����ID��ȡRadioButton��ʵ��
				RadioButton rb = (RadioButton) view.findViewById(radioButtonId);
				// ��ȡѡ���������
				// Log.e(TAG, "ѡ�е�RadioButton�ǣ�" + rb.getText());
				rbData = rb.getText().toString();
			}
		});

		// ----------------------Submit Button----------------------//
		submitBtn = (Button) view.findViewById(R.id.submitBtn);
		submitBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// ��ȡ�û����������
				ex_remark = ex_remarksTx.getText().toString();
				if (!("".equals(ex_moneyTx.getText().toString()))) {
					ex_money = Float
							.parseFloat(ex_moneyTx.getText().toString());
				}
				ex_date = ex_dateTx.getText().toString();
				ex_time = rbData;

				// �������
				isUpdate = checkInput(ex_remark, ex_moneyTx.getText()
						.toString(), ex_date);

				if (isUpdate) {
					Log.e(TAG, "д�������Ϊ: " + ex_in_type + ',' + ex_type + ','
							+ ex_remark + ',' + ex_money + ',' + ex_date + ','
							+ ex_time);
					ContentValues values = new ContentValues();
					values.put("ex_in_type", ex_in_type);
					values.put("type", ex_type);
					values.put("remarks", ex_remark);
					values.put("money", ex_money);
					values.put("date", ex_date);
					values.put("time", ex_time);
					// ������Ŀ��¼
					insertData(dbHelper.getReadableDatabase(), values);
					// �������
					clearInput();
					// ��ʾ��ʾ��Ϣ
					Toast.makeText(getActivity(), "���֧����¼�ɹ���",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
		return view;
	}

	/**
	 * ��������
	 */
	private void clearInput() {
		ex_remarksTx.setText("");
		ex_moneyTx.setText("");
		ex_dateTx.setText("");
	}

	/**
	 * ��������Ƿ���Ϲ淶
	 * 
	 * @param remarks
	 * @param money
	 * @param date
	 * @return
	 */
	private boolean checkInput(String remarks, String money, String date) {
		if ("".equals(remarks) || "".equals(money) || "".equals(date)) {
			Toast.makeText(getActivity(), "���벻��Ϊ�գ�", Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}

	/**
	 * ��������
	 * 
	 * @param db
	 * @param data
	 */
	private void insertData(SQLiteDatabase db, ContentValues values) {
		// ִ�в������
		db.insert("account", null, values);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		// �˳�ʱ�ر�dbHelper�е�SQLiteDatabase
		if (dbHelper != null) {
			dbHelper.close();
		}
		if (datePickerDialog != null) {
			datePickerDialog.dismiss();
		}
	}
}
