package com.jiateng.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jiateng.R;
import com.jiateng.domain.Order;

import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.SneakyThrows;

public class OrderDialogFragment extends Dialog {

    private LinearLayout layout1;
    private LinearLayout layout2;
    private LinearLayout layout3;
    private LinearLayout line_over;
    private TextView create;
    private TextView paid;
    private TextView shopGet;
    private TextView over;
    private Button commit;
    private ImageView cancel;
    private Order order;
    private DialogListener dialogListener;

    public OrderDialogFragment setDialogListener(DialogListener dialogListener) {
        this.dialogListener = dialogListener;
        return this;
    }

    public OrderDialogFragment setOrder(Order order) {
        this.order = order;
        return this;
    }

    @SneakyThrows
    @SuppressLint("SimpleDateFormat")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_fragment_order_status);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setCancelable(false);
        //自定义Dialog宽度
        WindowManager m = getWindow().getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = getWindow().getAttributes();
        Point size = new Point();
        d.getSize(size);
        p.width = (int) ((size.x) * 0.8);        //设置为屏幕的0.7倍宽度
        getWindow().setAttributes(p);
        layout1 = findViewById(R.id.blue_1);
        layout2 = findViewById(R.id.blue_2);
        layout3 = findViewById(R.id.blue_3);
        create = findViewById(R.id.create_time);
        paid = findViewById(R.id.paid_time);
        shopGet = findViewById(R.id.shop_init);
        over = findViewById(R.id.order_over);
        commit = findViewById(R.id.submit_order);
        cancel = findViewById(R.id.close_dialog);
        line_over = findViewById(R.id.line_over);

        if (order.getStartTimeOfOrder() != null) {
            String startTimeOfOrder = order.getStartTimeOfOrder();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日-HH:mm:ss");
            Date parse = simpleDateFormat.parse(startTimeOfOrder);
            String simpleTime = new SimpleDateFormat("MM-dd HH:mm").format(parse);
            create.setText(simpleTime);
            paid.setText(simpleTime);
            shopGet.setText(simpleTime);
        }
        if ("订单创建".equals(order.getStatus())) {
            layout3.setVisibility(View.GONE);
            line_over.setVisibility(View.GONE);
        } else {
            layout3.setVisibility(View.VISIBLE);
            line_over.setVisibility(View.VISIBLE);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日-HH:mm:ss");
            Date parse = simpleDateFormat.parse(order.getEndTimeOfOrder());
            String simpleTime = new SimpleDateFormat("MM-dd HH:mm").format(parse);
            over.setText(simpleTime);
            commit.setVisibility(View.GONE);
        }
        commit.setOnClickListener(v -> {
            if (dialogListener != null) {
                dialogListener.submitButton();
            }
        });
        cancel.setOnClickListener(v -> {
            if (dialogListener != null) {
                dialogListener.cancelButton();
            }
        });

    }

    public OrderDialogFragment(@NonNull Context context) {
        super(context);
    }

    public OrderDialogFragment(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected OrderDialogFragment(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public interface DialogListener {
        void submitButton();

        void cancelButton();
    }
}
