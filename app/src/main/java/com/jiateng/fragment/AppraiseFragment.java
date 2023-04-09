package com.jiateng.fragment;

import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.jiateng.R;
import com.jiateng.adapter.AppraiseAdapter;
import com.jiateng.common.base.BaseFragment;
import com.jiateng.domain.Address;
import com.jiateng.domain.Appraise;
import com.jiateng.domain.Order;
import com.jiateng.domain.Shop;
import com.jiateng.domain.ShoppingCart;
import com.jiateng.domain.User;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * @Description:
 * @Title: AppraiseFragment
 * @ProjectName: orderFood
 * @date: 2023/2/7 16:53
 * @author: 骆家腾
 */
public class AppraiseFragment extends BaseFragment implements RadioGroup.OnCheckedChangeListener {
    private ArrayList<Appraise> allAppraise;
    private ArrayList<Appraise> goodAppraise;
    private ArrayList<Appraise> badAppraise;

    @ViewInject(R.id.appraise_listView)
    private ListView appraiseListView;
    @ViewInject(R.id.appraise_choose)
    private RadioGroup choose;
    @ViewInject(R.id.appraise_all)
    private RadioButton all;

    private AppraiseAdapter adapter;
    @ViewInject(R.id.shop_attitude)
    private TextView attitude;
    @ViewInject(R.id.shop_goods_quality)
    private TextView quality;
    @ViewInject(R.id.shop_post_speed)
    private TextView speed;

    @Override
    protected View initView() {
        View view = View.inflate(context, R.layout.fragment_appraise, null);
        ViewUtils.inject(this, view);
        appraiseDataInit();
        adapter = new AppraiseAdapter(context, allAppraise);
        appraiseListView.setAdapter(adapter);
        appraiseListView.setFriction(ViewConfiguration.getScrollFriction() * 4);
        choose.setOnCheckedChangeListener(this);
        all.setChecked(true);
        return view;
    }

    public void appraiseDataInit() {
        allAppraise = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            Appraise appraise = new Appraise();
            appraise.setAppraiseId(i);
            appraise.setServeScore(4.3);
            appraise.setOrder(new Order(1, new User(), new Shop(), new Address(), new ShoppingCart(), "", 1.0, "", ""));
            appraise.setTime("2023-02-" + i + "日");
            appraise.setContext("此顾客没有评价！");
            appraise.setType((i % 2 == 0) ? 1 : 0);
            allAppraise.add(appraise);
        }
        goodAppraise = (ArrayList<Appraise>) allAppraise.stream().filter(a -> a.getType() == 1).collect(Collectors.toList());
        badAppraise = (ArrayList<Appraise>) allAppraise.stream().filter(a -> a.getType() == 0).collect(Collectors.toList());
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.appraise_all:
                adapter = new AppraiseAdapter(context, allAppraise);
                appraiseListView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                break;
            case R.id.appraise_good:
                adapter = new AppraiseAdapter(context, goodAppraise);
                appraiseListView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                break;
            case R.id.appraise_bad:
                adapter = new AppraiseAdapter(context, badAppraise);
                appraiseListView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }
}
