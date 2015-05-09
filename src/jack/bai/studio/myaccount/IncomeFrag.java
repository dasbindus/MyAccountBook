package jack.bai.studio.myaccount;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class IncomeFrag extends Fragment {

	private Spinner mSpinner;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.income_frag, container, false);
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
				// TODO Auto-generated method stub
				Toast.makeText(getActivity(), arrayAdapter.getItem(position),
						Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				Toast.makeText(getActivity(), "«Î—°‘Ò¿‡±£°", Toast.LENGTH_SHORT)
						.show();
			}
		});
		return view;
	}
}
