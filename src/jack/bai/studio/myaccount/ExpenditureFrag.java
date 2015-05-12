package jack.bai.studio.myaccount;

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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.Toast;

public class ExpenditureFrag extends Fragment {

	/** ���Ա�ʶ */
	private final static String TAG = ExpenditureFrag.class.getSimpleName();

	private Spinner mSpinner;
	private RadioGroup mRadioGroup;
	private EditText ex_remarksTx, ex_moneyTx, ex_dateTx;

	/** ���ݿ�Helper���� */
	private MyDBHelper dbHelper;
	/** �ύ��ť */
	private Button submitBtn = null;

	/** ��д�����ݿ������ */
	private String[] ex_data = new String[5];
	/** RadioButtonѡ�е����� */
	private String rbData = "����";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.expenditure_frag,
				container, false);

		// ����MyDBHelper����ָ�����ݿ�İ汾Ϊ1
		// �˴�ʹ�����·�����ɣ����ݿ��ļ��Զ������ڳ���������ļ��е�databasesĿ¼��
		dbHelper = new MyDBHelper(getActivity(), "myAccountBook.db3", 1);

		ex_remarksTx = (EditText) view.findViewById(R.id.remarksTx);
		ex_moneyTx = (EditText) view.findViewById(R.id.moneyTx);
		ex_dateTx = (EditText) view.findViewById(R.id.dateTx);

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
				ex_data[0] = arrayAdapter.getItem(position);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
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
				Log.e(TAG, "ѡ�е�RadioButton�ǣ�" + rb.getText());
				rbData = rb.getText().toString();
			}
		});

		// ----------------------Submit Button----------------------//
		submitBtn = (Button) view.findViewById(R.id.submitBtn);
		submitBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// ��ȡ�û����������
				ex_data[1] = ex_remarksTx.getText().toString();
				ex_data[2] = ex_moneyTx.getText().toString();
				ex_data[3] = ex_dateTx.getText().toString();
				ex_data[4] = rbData;

				Log.e(TAG, "д�������Ϊ: " + ex_data[0] + ',' + ex_data[1] + ','
						+ ex_data[2] + ',' + ex_data[3] + ',' + ex_data[4]);
				// ������Ŀ��¼
				insertData(dbHelper.getReadableDatabase(), ex_data);
				// �������
				clearInput();
				// ��ʾ��ʾ��Ϣ
				Toast.makeText(getActivity(), "���֧����¼�ɹ���", Toast.LENGTH_SHORT)
						.show();
			}
		});
		return view;
	}

	private void clearInput() {
		ex_remarksTx.setText("");
		ex_moneyTx.setText("");
		ex_dateTx.setText("");
	}

	/**
	 * ��������
	 * 
	 * @param db
	 * @param data
	 */
	private void insertData(SQLiteDatabase db, String[] data) {
		// ִ�в������
		db.execSQL("insert into expend_book values(null, ?, ?, ?, ?, ?)", data);
	}
}
