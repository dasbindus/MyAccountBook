package jack.bai.studio.myaccount;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.Toast;

public class ExpenditureFrag extends Fragment {

	private final static String TAG = ExpenditureFrag.class.getSimpleName();

	private Spinner mSpinner;
	private RadioGroup mRadioGroup;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.expenditure_frag,
				container, false);

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
				// TODO Auto-generated method stub
				Toast.makeText(getActivity(), arrayAdapter.getItem(position),
						Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				Toast.makeText(getActivity(), "请选择类别！", Toast.LENGTH_SHORT)
						.show();
			}
		});

		mRadioGroup = (RadioGroup) view.findViewById(R.id.radioGroup1);
		mRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup parent, int position) {
				// 获取选中项的ID
				int radioButtonId = parent.getCheckedRadioButtonId();
				// 根据ID获取RadioButton的实例
				RadioButton rb = (RadioButton) view.findViewById(radioButtonId);
				// 获取选中项的内容
				Log.i(TAG, "选中的RadioButton是：" + rb.getText());
			}
		});
		return view;
	}
}
