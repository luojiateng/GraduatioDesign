package com.jiateng.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.jiateng.R;
import com.jiateng.adapter.OrderGoodsInfoAdapter;
import com.jiateng.domain.Order;
import com.jiateng.fragment.OrderDialogFragment;
import com.jiateng.retrofit.api.OrderApi;
import com.jiateng.retrofit.domain.ResponseResult;
import com.jiateng.retrofit.domain.ResultUtil;
import com.jiateng.retrofit.domain.RetrofitManager;
import com.jiateng.utils.ToastUtil;
import com.jiateng.utils.UUIDUtil;
import com.jiateng.widget.AppTitleView;
import com.jiateng.widget.OrderInfoView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderInfoActivity extends Activity {

    @ViewInject(R.id.callBossPhone)
    private Button callBoss;
    @ViewInject(R.id.title_orderInfo)
    private AppTitleView titleOrderInfo;
    @ViewInject(R.id.order_status_checkout)
    private Button checkoutOrderStatus;
    @ViewInject(R.id.order_item_info)
    private TextView shopInfo;
    @ViewInject(R.id.order_info_list)
    private ListView recyclerView;
    private OrderGoodsInfoAdapter adapter;
    private Order order;
    @ViewInject(R.id.order_total_orderview)
    private OrderInfoView totalMoney;
    @ViewInject(R.id.order_begin_time)
    private OrderInfoView tv_createTime;
    @ViewInject(R.id.order_take_address)
    private OrderInfoView tv_takeAddress;
    @ViewInject(R.id.order_remark)
    private OrderInfoView tv_orderRemark;
    @ViewInject(R.id.order_userInfo)
    private OrderInfoView tv_orderUserInfo;
    @ViewInject(R.id.order_id)
    private OrderInfoView tv_orderId;
    @ViewInject(R.id.post_order)
    private TextView postingOrderImageTv;
    @ViewInject(R.id.finish_order)
    private TextView finishOrderImageTv;
    @ViewInject(R.id.order_status_checkout)
    private Button statusCheck;
    @ViewInject(R.id.getOrderStatus)
    private TextView getOrderStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_info);
        ViewUtils.inject(this);
        Bundle bundle = getIntent().getExtras();
        order = (Order) bundle.getSerializable("order");
        initViewFunction(order);
        adapter = new OrderGoodsInfoAdapter(OrderInfoActivity.this, order.getShoppingCartList());
        recyclerView.setAdapter(adapter);
        totalMoney.setTv_back("￥" + String.format("%.1f", order.getMoney()));
        tv_createTime.setTv_back(order.getStartTimeOfOrder());
        if ("post".equals(order.getType())) {
            tv_takeAddress.setTv_back(order.getAddressInfo().getAddressInfo());
        } else {
            tv_takeAddress.setTv_front("自提地点");
            tv_takeAddress.setTv_back(order.getShop().getAddress().getAddressInfo());
        }
        tv_orderRemark.setTv_back(order.getRemark());
        tv_orderUserInfo.setTv_back(order.getUser().getNickName() + " " + order.getUser().getPhoneNumber());
        tv_orderId.setTv_back(UUIDUtil.getUUID_8() + order.getOrderId());
        statusCheck.setOnClickListener(v -> {
            String currentText = statusCheck.getText().toString();
            if ("取消订单".equals(currentText)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);//this为上下文，如果在本类里显示，通常使用this
                builder.setTitle("提示");
                builder.setMessage("确定取消订单？");
                builder.setPositiveButton("确定", (dialog, which) -> {
                    RetrofitManager.getInstance().getApiService("/order", OrderApi.class).deleteOrder(order.getOrderId()).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (ResultUtil.getResultMsg(response)) {
                                ToastUtil.ToastShow("订单已经取消");
                                finish();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                        }
                    });
                });
                builder.setNeutralButton("取消", null);
                builder.show(); //调用show()方法来展示对话框
            } else {
                Intent intent = new Intent(OrderInfoActivity.this, EvaluateActivity.class);
                Bundle evaluateBundle = new Bundle();
                evaluateBundle.putString("userId", order.getUser().getUserId() + "");
                evaluateBundle.putString("shopId", order.getShop().getShopId() + "");
                evaluateBundle.putString("orderId", order.getOrderId() + "");
                intent.putExtras(evaluateBundle);
                startActivity(intent);
            }
        });
        getOrderStatus.setOnClickListener(v -> {
            OrderDialogFragment dialogFragment = new OrderDialogFragment(OrderInfoActivity.this);
            dialogFragment.setOrder(order).setDialogListener(new OrderDialogFragment.DialogListener() {
                @Override
                public void submitButton() {
                    RetrofitManager.getInstance().getApiService("/order", OrderApi.class).finishOrder(order.getOrderId()).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (ResultUtil.getResultMsg(response)) {
                                ToastUtil.ToastShow("已收货");
                                dialogFragment.dismiss();
                                RetrofitManager.getInstance().getApiService("/order", OrderApi.class).getCurrentOrderInfo(order.getOrderId()).enqueue(new Callback<ResponseResult<Order>>() {
                                    @Override
                                    public void onResponse(Call<ResponseResult<Order>> call, Response<ResponseResult<Order>> response) {
                                        order = ResultUtil.getResult(response);
                                        System.out.println("======" + order);
                                        initViewFunction(order);
                                    }

                                    @Override
                                    public void onFailure(Call<ResponseResult<Order>> call, Throwable t) {

                                    }
                                });
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                        }
                    });
                    dialogFragment.dismiss();
                }

                @Override
                public void cancelButton() {
                    dialogFragment.dismiss();
                }
            }).show();
        });
    }

    public void initViewFunction(Order order) {
        callBoss.setOnClickListener(v -> {
            String phoneNumber = order.getShop().getShopPhone();
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_DIAL);
            Uri uri = Uri.parse("tel:" + phoneNumber);
            intent.setData(uri);
            startActivity(intent);
        });

        titleOrderInfo.onClickTitleListener(v -> {
            finish();
        });
        checkoutOrderStatus.setOnClickListener(v -> {

        });
        shopInfo.setOnClickListener(v -> {
            Intent intent = new Intent(OrderInfoActivity.this, ShopActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("shop", order.getShop());
            intent.putExtras(bundle);
            startActivity(intent);
        });
        RetrofitManager.getInstance().getApiService("/order", OrderApi.class).getOrderStatus(order.getOrderId()).enqueue(new Callback<ResponseResult<String>>() {
            @Override
            public void onResponse(Call<ResponseResult<String>> call, Response<ResponseResult<String>> response) {
                String status = ResultUtil.getResult(response);
                if ("订单创建".equals(status) && "pickup".equals(order.getType())) {
                    postingOrderImageTv.setText("等待自提");
                    statusCheck.setText("取消订单");
                    Drawable img = getResources().getDrawable(R.drawable.ic_pickup);
                    img.setBounds(0, 0, img.getIntrinsicWidth(), img.getIntrinsicHeight()); // 设置图片大小
                    postingOrderImageTv.setCompoundDrawables(null, img, null, null);
                } else if ("订单创建".equals(status) && "post".equals(order.getType())) {
                    postingOrderImageTv.setText("配送中");
                    statusCheck.setText("取消订单");
                } else if ("订单完成".equals(status) && "post".equals(order.getType())) {
                    postingOrderImageTv.setText("已送达");
                    Drawable img = getResources().getDrawable(R.drawable.ic_finish_1);
                    img.setBounds(0, 0, img.getIntrinsicWidth(), img.getIntrinsicHeight()); // 设置图片大小
                    finishOrderImageTv.setCompoundDrawables(null, img, null, null);
                    statusCheck.setText("点评");
                } else if ("订单完成".equals(status) && "pickup".equals(order.getType())) {
                    postingOrderImageTv.setText("已取货");
                    Drawable img = getResources().getDrawable(R.drawable.ic_finish_1);
                    img.setBounds(0, 0, img.getIntrinsicWidth(), img.getIntrinsicHeight()); // 设置图片大小
                    finishOrderImageTv.setCompoundDrawables(null, img, null, null);
                    statusCheck.setText("点评");
                } else {
                }
            }

            @Override
            public void onFailure(Call<ResponseResult<String>> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        statusCheck.setOnClickListener(v -> {
            String currentText = statusCheck.getText().toString();
            if ("取消订单".equals(currentText)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);//this为上下文，如果在本类里显示，通常使用this
                builder.setTitle("提示");
                builder.setMessage("确定取消订单？");
                builder.setPositiveButton("确定", (dialog, which) -> {
                    RetrofitManager.getInstance().getApiService("/order", OrderApi.class).deleteOrder(order.getOrderId()).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (ResultUtil.getResultMsg(response)) {
                                ToastUtil.ToastShow("订单已经取消");
                                finish();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                        }
                    });
                });
                builder.setNeutralButton("取消", null);
                builder.show(); //调用show()方法来展示对话框
            } else {
                Intent intent = new Intent(OrderInfoActivity.this, EvaluateActivity.class);
                Bundle evaluateBundle = new Bundle();
                evaluateBundle.putString("userId", order.getUser().getUserId() + "");
                evaluateBundle.putString("shopId", order.getShop().getShopId() + "");
                evaluateBundle.putString("orderId", order.getOrderId() + "");
                intent.putExtras(evaluateBundle);
                startActivity(intent);
            }
        });
    }
}