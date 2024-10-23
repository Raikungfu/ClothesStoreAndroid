package com.prmproject.clothesstoremobileandroid.ui.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.prmproject.clothesstoremobileandroid.Data.model.Address;
import com.prmproject.clothesstoremobileandroid.Data.sqllite.AddressDatabaseHelper;
import com.prmproject.clothesstoremobileandroid.R;

import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressViewHolder> {
    private List<Address> addressList;
    private AddressDatabaseHelper dbHelper;

    public AddressAdapter(List<Address> addressList, AddressDatabaseHelper dbHelper) {
        this.addressList = addressList;
        this.dbHelper = dbHelper;
    }

    @Override
    public AddressViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_address_item, parent, false);
        return new AddressViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AddressViewHolder holder, int position) {
        Address address = addressList.get(position);
        holder.fullName.setText(address.getFullName());
        holder.fullAddress.setText(address.getAddress() + ", " + address.getCity() + ", " + address.getState() + ", " + address.getCountry());

        // Handle delete button click
        holder.deleteAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Delete address from the database
                dbHelper.deleteAddress(address.getId());

                // Remove the item from the list and notify the adapter
                addressList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, addressList.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return addressList.size();
    }

    class AddressViewHolder extends RecyclerView.ViewHolder {
        TextView fullName, fullAddress;
        Button deleteAddressBtn;

        public AddressViewHolder(View itemView) {
            super(itemView);
            fullName = itemView.findViewById(R.id.fullName);
            fullAddress = itemView.findViewById(R.id.fullAddress);
            deleteAddressBtn = itemView.findViewById(R.id.deleteAddress_Btn);
        }
    }
}
