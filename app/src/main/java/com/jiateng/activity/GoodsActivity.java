package com.jiateng.activity;

import android.app.Activity;
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
import com.jiateng.bean.ShoppingCart;
import com.jiateng.bean.StoreBean;
import com.jiateng.common.utils.PicassoUtil;
import com.jiateng.common.widget.AppTitleView;
import com.jiateng.db.impl.ShoppingCartImpl;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

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

    private List<ShoppingCart> shoppingCartsData;
    private ShoppingCartImpl shoppingCartDao;
    private String userId;
    private String goodsId;
    private String shopId;
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
        ShoppingCart goodsInfo = (ShoppingCart) bundle.getSerializable("shoppingCartInfo");
        StoreBean.Goods goods = (StoreBean.Goods) bundle.getSerializable("goods");
        userId = goodsInfo.getUserId();
        shopId = goodsInfo.getShopId();
        goodsId = goodsInfo.getGoodsId();
        price = goodsInfo.getGoodsPrice().toString();
        goodsName = goodsInfo.getGoodsName();
        goodsImgUrl = goodsInfo.getGoodsImgUrl();
        shoppingCartDao = ShoppingCartImpl.getInstance(GoodsActivity.this);
        initCarData();
        shoppingCartPrice.setText(getShopPrice(goodsInfo));
        currentGoodsName.setText(goods.getName());
        currentGoodsPrice.setText(goods.getPrice().toString());
        PicassoUtil.setImage(goods.getGoodsImgUrl(), currentGoodsImg);
        adapter = new ShoppingCartAdapter(GoodsActivity.this, shoppingCartsData);

        titleView.onClickTitleListener(v -> {
            finish();
        });
        carInfo.setOnClickListener(v -> {
            showBottomSheet();
        });
        addGoodsButton.setOnClickListener(v -> {
            initCarData();
            ShoppingCart shoppingCart = new ShoppingCart(null, userId, shopId, goodsId, goodsName, Double.parseDouble(price), goodsImgUrl, 1);
            shoppingCartDao.insertGoods(shoppingCart);
            shoppingCartPrice.setText(getShopPrice(shoppingCart));
        });
    }


    public void initCarData() {
        shoppingCartsData = shoppingCartDao.queryByGoodsByUserIdShopId(userId, shopId);
    }

    /**
     * 从底部滑入
     */
    private void showBottomSheet() {
        bottomSheet = createBottomSheetView();
        if (bottomSheetLayout.isSheetShowing()) {
            bottomSheetLayout.dismissSheet();
        } else {
            initCarData();
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
            shoppingCartDao.clean(userId, shopId);
            shoppingCartsData = shoppingCartDao.queryByGoodsByUserIdShopId(userId, shopId);
            adapter.notifyDataSetChanged();
            bottomSheetLayout.dismissSheet();
            shoppingCartPrice.setText("¥0.0");
        });
        adapter.notifyDataSetChanged();
        adapter.setOnSelectListener(new ShoppingCartAdapter.OnSelectListener() {
            @Override
            public void onSelectAdd(int position, ShoppingCart shoppingCart) {
                shoppingCartDao.insertGoods(shoppingCart);
                adapter.notifyDataSetChanged();

                shoppingCartPrice.setText(getShopPrice(shoppingCart));
            }

            @Override
            public void onSelectReduce(int position) {
                ShoppingCart shoppingCart = shoppingCartsData.get(position);
                shoppingCartDao.deleteGoods(shoppingCart);
                ShoppingCart hasGoods = shoppingCartDao.queryOne(shoppingCart);
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
        carList.setAdapter(adapter);

        return view;
    }

    private String getShopPrice(ShoppingCart shoppingCart) {
        double money = 0.0;
        List<ShoppingCart> shoppingCarts = shoppingCartDao.queryByGoodsByUserIdShopId(shoppingCart.getUserId(), shoppingCart.getShopId());
        for (ShoppingCart cart : shoppingCarts) {
            money = money + cart.getGoodsPrice().doubleValue() * cart.getGoodsCount().intValue();
        }
        return String.format("%.1f", money);
    }
}