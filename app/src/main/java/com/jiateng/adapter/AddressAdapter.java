package com.jiateng.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jiateng.R;
import com.jiateng.domain.AddressInfo;

import java.util.ArrayList;

/**
 * @Description:
 * @Title: AddressAdapter
 * @ProjectName: orderFood
 * @date: 2023/4/14 19:55
 * @author: 骆家腾
 */
public class AddressAdapter extends RecyclerView.Adapter {
    private ArrayList<AddressInfo> addressInfos;
    private Context context;


    public AddressAdapter(Context context, ArrayList<AddressInfo> addressInfos) {
        this.context = context;
        this.addressInfos = addressInfos;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_address, null);
        return new AddressAdapter.AddressHolder(context, view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        AddressHolder addressHolder = (AddressHolder) holder;
        addressHolder.setData(addressInfos.get(position), position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return addressInfos.size();
    }


    private class AddressHolder extends RecyclerView.ViewHolder {
        private View view;
        private TextView name;
        private TextView phone;
        private TextView addressContext;
        private RelativeLayout relativeLayout;
        private ImageView edit;

        public AddressHolder(Context context, @NonNull View itemView) {
            super(itemView);
            this.view = itemView;
            name = itemView.findViewById(R.id.addrees_user_name);
            phone = itemView.findViewById(R.id.addrees_phone);
            addressContext = itemView.findViewById(R.id.address_info_context);
            edit = itemView.findViewById(R.id.edit_address);
            relativeLayout = itemView.findViewById(R.id.address_are);
        }

        public void setData(AddressInfo addressInfo, int position) {
            name.setTag(position);
            name.setText(addressInfo.getUserName());
            phone.setTag(position);
            phone.setText(addressInfo.getUserPhone());
            addressContext.setTag(position);
            String userAddress = addressInfo.getSchoolName() + addressInfo.getBuild() + addressInfo.getSpecificAddress();
            addressContext.setText(userAddress);

            edit.setOnClickListener(v -> {
                if (addressOnClickListener != null) {
                    addressOnClickListener.editListener(view, position);
                }
            });
            relativeLayout.setOnClickListener(v -> {
                if (addressOnClickListener != null) {
                    addressOnClickListener.itemClickListener(view, position);
                }
            });
        }
    }

    private AddressAdapter.AddressOnClickListener addressOnClickListener;

    public void setMyOnClickListener(AddressAdapter.AddressOnClickListener addressOnClickListener) {
        this.addressOnClickListener = addressOnClickListener;
    }

    public interface AddressOnClickListener {
        void itemClickListener(View view, int position);

        void editListener(View view, int position);
    }
}
