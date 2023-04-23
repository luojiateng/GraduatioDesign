package com.jiateng.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.jiateng.R;

import java.util.List;

public class SeachRecordAdapter extends SearchRecycleAdapter<String> {
    public SeachRecordAdapter(List<String> datas, Context mContext) {
        super(datas, mContext);
    }

    @Override
    protected void bindData(BaseViewHolder holder, final int position) {

        TextView textView = (TextView) holder.getView(R.id.tv_record);
        textView.setText(datas.get(position));

        holder.getView(R.id.tv_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != mRvItemOnclickListener) {
                    mRvItemOnclickListener.removeItemOnclick(position);
                }
            }
        });
        holder.getView(R.id.tv_record).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mRvItemOnclickListener) {
                    mRvItemOnclickListener.intoHistoryPage(position);
                }
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_search;
    }

}