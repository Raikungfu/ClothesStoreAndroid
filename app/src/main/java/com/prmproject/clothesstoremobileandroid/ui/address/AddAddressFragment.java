package com.prmproject.clothesstoremobileandroid.ui.address;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.prmproject.clothesstoremobileandroid.Data.sqllite.AddressDatabaseHelper;
import com.prmproject.clothesstoremobileandroid.R;

public class AddAddressFragment extends Fragment {
    private EditText txtFullName, txtAddress, txtCity, txtState, txtPhone, txtCountry;
    private Button saveAddressButton;
    private AddressDatabaseHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_address, container, false);

        txtFullName = view.findViewById(R.id.txt_fullname);
        txtAddress = view.findViewById(R.id.txt_address);
        txtCity = view.findViewById(R.id.txt_city);
        txtState = view.findViewById(R.id.txt_state);
        txtPhone = view.findViewById(R.id.txt_phone);
        txtCountry = view.findViewById(R.id.txt_country);
        saveAddressButton = view.findViewById(R.id.save_address);

        dbHelper = new AddressDatabaseHelper(getActivity());

        saveAddressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName = txtFullName.getText().toString();
                String address = txtAddress.getText().toString();
                String city = txtCity.getText().toString();
                String state = txtState.getText().toString();
                String phone = txtPhone.getText().toString();
                String country = txtCountry.getText().toString();

                if (!fullName.isEmpty() && !address.isEmpty() && !city.isEmpty() && !state.isEmpty() && !phone.isEmpty() && !country.isEmpty()) {
                    boolean isInserted = dbHelper.addAddress(fullName, address, city, state, phone, country);
                    if (isInserted) {
                        Toast.makeText(getContext(), "Address Saved", Toast.LENGTH_SHORT).show();
                        getFragmentManager().popBackStack();
                    } else {
                        Toast.makeText(getContext(), "Error Saving Address", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "All fields are required", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
}
