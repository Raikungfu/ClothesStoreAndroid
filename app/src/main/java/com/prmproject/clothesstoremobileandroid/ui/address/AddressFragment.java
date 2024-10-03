//package com.prmproject.clothesstoremobileandroid.ui.address;
//
//import android.widget.Button;
//import android.os.Bundle;
//import androidx.fragment.app.Fragment;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import androidx.navigation.NavController;
//import androidx.navigation.Navigation;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//import com.prmproject.clothesstoremobileandroid.Data.model.Address;
//import com.prmproject.clothesstoremobileandroid.Data.sqllite.AddressDatabaseHelper;
//import com.prmproject.clothesstoremobileandroid.R;
//import com.prmproject.clothesstoremobileandroid.ui.Adapter.AddressAdapter;
//
//import java.util.List;
//
//
//public class AddressFragment extends Fragment {
//    private RecyclerView recyclerView;
//    private AddressAdapter adapter;
//    private List<Address> addressList;
//    private AddressDatabaseHelper dbHelper;
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_address, container, false);
//
//        dbHelper = new AddressDatabaseHelper(getActivity());
//        recyclerView = view.findViewById(R.id.recyclerViewAddress);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//
//        // Fetch all addresses from SQLite database
//        addressList = dbHelper.getAllAddresses();
//
//        // Initialize and set the adapter
//        adapter = new AddressAdapter(addressList);
//        recyclerView.setAdapter(adapter);
//
//        // Handle the add button click to navigate to the AddAddressFragment
//        Button addAddressBtn = view.findViewById(R.id.addAddress_Btn);
//        addAddressBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Use NavController to navigate to AddAddressFragment
//                NavController navController = Navigation.findNavController(v);
//                navController.navigate(R.id.action_navigation_myorder_to_navigation_newAddress);
//            }
//        });
//
//        return view;
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        // Refresh address list when returning to this fragment
//        addressList.clear();
//        addressList.addAll(dbHelper.getAllAddresses());
//        adapter.notifyDataSetChanged();
//    }
//}

package com.prmproject.clothesstoremobileandroid.ui.address;

import android.widget.Button;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.prmproject.clothesstoremobileandroid.Data.model.Address;
import com.prmproject.clothesstoremobileandroid.Data.sqllite.AddressDatabaseHelper;
import com.prmproject.clothesstoremobileandroid.R;
import com.prmproject.clothesstoremobileandroid.ui.Adapter.AddressAdapter;

import java.util.List;

public class AddressFragment extends Fragment {
    private RecyclerView recyclerView;
    private AddressAdapter adapter;
    private List<Address> addressList;
    private AddressDatabaseHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_address, container, false);

        dbHelper = new AddressDatabaseHelper(getActivity());
        recyclerView = view.findViewById(R.id.recyclerViewAddress);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        addressList = dbHelper.getAllAddresses();

        adapter = new AddressAdapter(addressList, dbHelper);
        recyclerView.setAdapter(adapter);

        Button addAddressBtn = view.findViewById(R.id.addAddress_Btn);
        addAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_navigation_myorder_to_navigation_newAddress);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        addressList.clear();
        addressList.addAll(dbHelper.getAllAddresses());
        adapter.notifyDataSetChanged();
    }
}
