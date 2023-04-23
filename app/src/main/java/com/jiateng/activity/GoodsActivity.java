package com.jiateng.activity;

import static com.jiateng.utils.ToastUtil.ToastShow;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.flipboard.bottomsheet.BottomSheetLayout;
import com.jiateng.R;
import com.jiateng.adapter.ShoppingCartAdapter;
import com.jiateng.widget.AppTitleView;
import com.jiateng.db.impl.ShoppingCartDaoImpl;
import com.jiateng.domain.Shop;
import com.jiateng.domain.ShoppingCart;
import com.jiateng.domain.StoreBean;
import com.jiateng.domain.User;
import com.jiateng.utils.PicassoUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

public class GoodsActivity extends Activity {
    @ViewInject(R.id.shop_goods_title)
    private AppTitleView titleView;
    @ViewInject(R.id.bottomSheetLayout)
    private BottomSheetLayout bottomSheetLayout;
    private View bottomSheet;
    @ViewInject(R.id.shop_goods_car_2)
    private View carInfo;
    @ViewInject(R.id.car_list)
    private ListView carListView;
    @ViewInject(R.id.shopping_cart_price)
    private TextView shoppingCartPrice;
    @ViewInject(R.id.shop_goods_add)
    private Button addGoodsButton;
    @ViewInject(R.id.shop_goods_price)
    private TextView currentGoodsPrice;
    @ViewInject(R.id.goods_name)
    private TextView currentGoodsName;
    @ViewInject(R.id.goods_img)
    private ImageView currentGoodsImg;
    @ViewInject(R.id.goods_settlement)
    private TextView commit;

    private List<ShoppingCart> shoppingCartsData;
    private ShoppingCartDaoImpl shoppingCartImpl;
    private Integer userId;
    private Integer goodsId;
    private Integer shopId;
    private String price;
    private ShoppingCartAdapter adapter;
    private String goodsName;
    private String goodsImgUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods);
        ViewUtils.inject(this);

        Bundle bundle = getIntent().getExtras();
        ShoppingCart shoppingCartInfo = (ShoppingCart) bundle.getSerializable("shoppingCartInfo");
        StoreBean.Goods goods = (StoreBean.Goods) bundle.getSerializable("goods");
        userId = shoppingCartInfo.getUser().getUserId();
        shopId = shoppingCartInfo.getShop().getShopId();
        goodsId = shoppingCartInfo.getGoods().getGoodsId();
        price = shoppingCartInfo.getGoods().getPrice().toString();
        goodsName = shoppingCartInfo.getGoods().getGoodName();
        goodsImgUrl = shoppingCartInfo.getGoods().getGoodsImageUrl();
        shoppingCartImpl = ShoppingCartDaoImpl.getInstance();
        initCarData();
        shoppingCartPrice.setText(getShopPrice(shoppingCartInfo));
        currentGoodsName.setText(goods.getGoodName());
        currentGoodsPrice.setText(goods.getPrice().toString());
        PicassoUtil.setImage(goods.getGoodsImageUrl(), currentGoodsImg);
        adapter = new ShoppingCartAdapter(GoodsActivity.this, shoppingCartsData);

        titleView.onClickTitleListener(v -> {
            finish();
        });
        carInfo.setOnClickListener(v -> {
            showBottomSheet();
        });
        addGoodsButton.setOnClickListener(v -> {
            initCarData();
            ShoppingCart shoppingCart = new ShoppingCart(
                    new User(userId),
                    new Shop(shopId),
                    goods,
                    1);
            shoppingCartImpl.insertGoods(shoppingCart);
            initCarData();
            adapter = new ShoppingCartAdapter(GoodsActivity.this, shoppingCartsData);
            shoppingCartPrice.setText(getShopPrice(shoppingCart));
            adapter.notifyDataSetChanged();
        });
        commit.setOnClickListener(v -> {
            ArrayList<ShoppingCart> willPaidGoods = (ArrayList<ShoppingCart>) shoppingCartImpl.queryByGoodsByUserIdShopId(userId, shopId);
            if (willPaidGoods.size() == 0) {
                ToastShow("请选购商品");
            } else {
                Intent intent = new Intent(GoodsActivity.this, PaidActivity.class);
                Bundle b = new Bundle();
                b.putSerializable("willPaidGoods", willPaidGoods);
                intent.putExtras(b);
                startActivity(intent);
                finish();
            }
        });
    }


    public void initCarData() {
        shoppingCartsData = shoppingCartImpl.queryByGoodsByUserIdShopId(userId, shopId);
    }

    /**
     * 从底部滑入
     */
    private void showBottomSheet() {
        initCarData();
        bottomSheet = createBottomSheetView();
        if (bottomSheetLayout.isSheetShowing()) {
            bottomSheetLayout.dismissSheet();
        } else {
            bottomSheetLayout.showWithSheetView(bottomSheet);
        }
    }


    /**
     * 从底部弹出的子布局
     *
     * @return
     */
    private View createBottomSheetView() {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_bottom_sheet, (ViewGroup) getWindow().getDecorView(), false);
        ListView carList = view.findViewById(R.id.car_list);
        //清空购物车
        ((TextView) view.findViewById(R.id.cleanShoppingCart)).setOnClickListener(v -> {
            shoppingCartImpl.clean(userId, shopId);
            shoppingCartsData = shoppingCartImpl.queryByGoodsByUserIdShopId(userId, shopId);
            adapter.notifyDataSetChanged();
            bottomSheetLayout.dismissSheet();
            shoppingCartPrice.setText("¥0.0");
        });
        adapter = new ShoppingCartAdapter(GoodsActivity.this, shoppingCartsData);
        adapter.notifyDataSetChanged();
        carList.setAdapter(adapter);
        adapter.setOnSelectListener(new ShoppingCartAdapter.OnSelectListener() {
            @Override
            public void onSelectAdd(int position, ShoppingCart shoppingCart) {
                shoppingCartImpl.insertGoods(shoppingCart);
                adapter.notifyDataSetChanged();
                shoppingCartPrice.setText(getShopPrice(shoppingCart));
            }

            @Override
            public void onSelectReduce(int position) {
                ShoppingCart shoppingCart = shoppingCartsData.get(position);
                shoppingCartImpl.deleteGoods(shoppingCart);
                ShoppingCart hasGoods = shoppingCartImpl.queryOne(shoppingCart);

                if (hasGoods == null) {
                    shoppingCartsData.remove(position);
                }
                shoppingCartPrice.setText(getShopPrice(shoppingCart));
                adapter.notifyDataSetChanged();
                if (shoppingCartsData.size() == 0) {
                    bottomSheetLayout.dismissSheet();
                }
            }
        });
        return view;
    }

    private String getShopPrice(ShoppingCart shoppingCart) {
        double money = 0.0;
        List<ShoppingCart> shoppingCarts = shoppingCartImpl.queryByGoodsByUserIdShopId(shoppingCart.getUser().getUserId(), shoppingCart.getShop().getShopId());
        for (ShoppingCart cart : shoppingCarts) {
            money = money + cart.getGoods().getPrice().doubleValue() * cart.getGoodsCount().intValue();
        }
        return money == 0.0 ? "0" : String.format("%.1f", money);
    }

    private String getPriceString(ArrayList<ShoppingCart> shoppingCarts) {
        double money = 0.0;
        for (ShoppingCart cart : shoppingCarts) {
            money = money + cart.getGoods().getPrice().doubleValue() * cart.getGoodsCount().intValue();
        }
        return money == 0.0 ? "0" : String.format("%.1f", money);
    }
}