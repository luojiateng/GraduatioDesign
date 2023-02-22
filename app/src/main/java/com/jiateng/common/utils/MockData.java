package com.jiateng.common.utils;

import com.jiateng.bean.ShopInfo;
import com.jiateng.bean.StoreBean;

import java.util.ArrayList;

/**
 * @Description:
 * @Title: MockData
 * @ProjectName: orderFood
 * @date: 2023/2/12 19:10
 * @author: 骆家腾
 */
public class MockData {
    private static ArrayList<ShopInfo> shopInfoList;
    private static ArrayList<StoreBean.Goods> goodsList;
    private static String userId = UUIDUtil.getUUID();

    private static String[] shopIds;

    static {
        shopIds = new String[14];
        for (int i = 0; i < shopIds.length; i++) {
            shopIds[i] = UUIDUtil.getUUID();
        }
    }

    public static ArrayList<ShopInfo> getShopInfoList() {
        shopInfoList = new ArrayList<>();
        ShopInfo s1 = new ShopInfo(shopIds[0], "https://img1.baidu.com/it/u=1279564446,3597104765&fm=253&fmt=auto&app=138&f=JPEG?w=891&h=500", "户县软面", "117", "新食堂三楼1号窗口", "7:00-12:00", "18092429450", null, getGoodsList());
        ShopInfo s2 = new ShopInfo(shopIds[1], "https://img0.baidu.com/it/u=1227022720,2302127292&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=418", "过桥米线", "187", "新食堂一楼2号窗口", "7:00-12:00", "18092429451", null, getGoodsList());
        ShopInfo s3 = new ShopInfo(shopIds[2], "https://img0.baidu.com/it/u=1797479622,2843378084&fm=253&fmt=auto&app=138&f=JPEG?w=340&h=255", "水煮肉片米饭", "149", "新食堂一楼3号窗口", "7:00-12:00", "18092429452", null, getGoodsList());
        ShopInfo s4 = new ShopInfo(shopIds[3], "https://img2.baidu.com/it/u=1223881333,752344692&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=333", "香菜屋川菜", "419", "新食堂一楼4号窗口", "7:00-12:00", "18092429453", null, getGoodsList());
        ShopInfo s5 = new ShopInfo(shopIds[4], "https://img0.baidu.com/it/u=2088147889,1323818424&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=297", "人和川菜", "115", "新食堂一楼5号窗口", "7:00-12:00", "18092429454", null, getGoodsList());
        ShopInfo s6 = new ShopInfo(shopIds[5], "https://img2.baidu.com/it/u=1136232784,155018639&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=750", "黄焖鸡米饭", "225", "新食堂一楼6号窗口", "7:00-12:00", "18092429450", null, getGoodsList());
        ShopInfo s7 = new ShopInfo(shopIds[6], "https://img1.baidu.com/it/u=4098315238,1731438575&fm=253&fmt=auto&app=138&f=JPEG?w=499&h=364", "擀面皮", "49", "新食堂二楼1号窗口", "7:00-12:00", "18092429450", null, getGoodsList());
        ShopInfo s8 = new ShopInfo(shopIds[7], "https://img2.baidu.com/it/u=3141701943,2676787400&fm=253&fmt=auto&app=120&f=JPEG?w=750&h=500", "烤肉拌饭鸡排饭", "96", "新食堂二楼2号窗口", "7:00-12:00", "18092429450", null, getGoodsList());
        ShopInfo s9 = new ShopInfo(shopIds[8], "https://img0.baidu.com/it/u=3893058491,1481843989&fm=253&fmt=auto&app=138&f=PNG?w=600&h=400", "铁板炒饭", "192", "新食堂二楼3号窗口", "7:00-12:00", "18092429450", null, getGoodsList());
        ShopInfo s10 = new ShopInfo(shopIds[9], "https://img2.baidu.com/it/u=4025401126,3154955138&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=889", "潼关肉夹馍", "147", "新食堂二楼4号窗口", "7:00-12:00", "18092429450", null, getGoodsList());
        ShopInfo s11 = new ShopInfo(shopIds[10], "https://img2.baidu.com/it/u=624212996,3163756151&fm=253&fmt=auto&app=138&f=JPEG?w=667&h=500", "特一味米皮", "155", "新食堂三楼2号窗口", "7:00-12:00", "18092429450", null, getGoodsList());
        ShopInfo s12 = new ShopInfo(shopIds[11], "https://img0.baidu.com/it/u=2753548292,3197556216&fm=253&fmt=auto&app=138&f=JPEG?w=668&h=500", "泡馍烧烤", "123", "新食堂二楼5号窗口", "7:00-12:00", "18092429450", null, getGoodsList());
        ShopInfo s13 = new ShopInfo(shopIds[12], "https://img1.baidu.com/it/u=556589281,1595243929&fm=253&fmt=auto&app=138&f=JPEG?w=542&h=500", "海鲜炒饭", "373", "新食堂二楼6号窗口", "7:00-12:00", "18092429450", null, getGoodsList());
        ShopInfo s14 = new ShopInfo(shopIds[13], "https://img0.baidu.com/it/u=177208912,1576759520&fm=253&fmt=auto&app=138&f=JPEG?w=400&h=355", "兰州拉面", "76", "新食堂一楼1号窗口", "7:00-12:00", "18092429450", null, getGoodsList());
        shopInfoList.add(s1);
        shopInfoList.add(s2);
        shopInfoList.add(s3);
        shopInfoList.add(s4);
        shopInfoList.add(s5);
        shopInfoList.add(s6);
        shopInfoList.add(s7);
        shopInfoList.add(s8);
        shopInfoList.add(s9);
        shopInfoList.add(s10);
        shopInfoList.add(s12);
        shopInfoList.add(s13);
        shopInfoList.add(s14);
        shopInfoList.add(s11);
        return shopInfoList;
    }

    public static ArrayList<StoreBean.Goods> getGoodsList() {
        goodsList = new ArrayList<>();
        StoreBean.Goods g1 = new StoreBean.Goods(UUIDUtil.getUUID(), "https://img2.baidu.com/it/u=1826713460,3667247546&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=390", "牛肉拉面", "面食", 17.8, 100);
        StoreBean.Goods g2 = new StoreBean.Goods(UUIDUtil.getUUID(), "https://img2.baidu.com/it/u=1048876618,4105876664&fm=253&fmt=auto&app=138&f=JPEG?w=886&h=500", "牛肉炒拉面", "面食", 19.8, 100);
        StoreBean.Goods g3 = new StoreBean.Goods(UUIDUtil.getUUID(), "https://img1.baidu.com/it/u=3215661205,3626821523&fm=253&fmt=auto&app=138&f=JPEG?w=667&h=500", "牛肉干饺", "面食", 21.8, 100);
        StoreBean.Goods g4 = new StoreBean.Goods(UUIDUtil.getUUID(), "https://img2.baidu.com/it/u=4035318550,4215867129&fm=253&fmt=auto&app=138&f=JPEG?w=600&h=353", "鸡蛋炒拉面", "面食", 18.8, 100);
        StoreBean.Goods g5 = new StoreBean.Goods(UUIDUtil.getUUID(), "https://img0.baidu.com/it/u=1611470889,370478518&fm=253&fmt=auto&app=138&f=JPEG?w=667&h=500", "油泼面", "面食", 18.8, 100);
        StoreBean.Goods g6 = new StoreBean.Goods(UUIDUtil.getUUID(), "https://img0.baidu.com/it/u=2648827580,2037327779&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=666", "鸡蛋拉面", "面食", 16.0, 100);
        StoreBean.Goods g7 = new StoreBean.Goods(UUIDUtil.getUUID(), "https://img0.baidu.com/it/u=3735925207,1750522930&fm=253&fmt=auto&app=138&f=JPEG?w=660&h=495", "葱油拌面", "面食", 18.8, 100);
        StoreBean.Goods g8 = new StoreBean.Goods(UUIDUtil.getUUID(), "https://img0.baidu.com/it/u=1425075852,1126807826&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=667", "新疆拌面", "面食", 22.8, 100);
        StoreBean.Goods g9 = new StoreBean.Goods(UUIDUtil.getUUID(), "https://img1.baidu.com/it/u=979551574,4218693927&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=333", "牛肉烩面", "面食", 18.8, 100);
        StoreBean.Goods g10 = new StoreBean.Goods(UUIDUtil.getUUID(), "https://img0.baidu.com/it/u=397900147,1388378649&fm=253&fmt=auto&app=138&f=JPEG?w=667&h=500", "葱爆肉盖饭", "盖浇饭(面)", 19.8, 100);
        StoreBean.Goods g11 = new StoreBean.Goods(UUIDUtil.getUUID(), "https://img2.baidu.com/it/u=2883734066,3070082682&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500", "青椒炒牛肉盖饭", "盖浇饭(面)", 18.8, 100);
        StoreBean.Goods g12 = new StoreBean.Goods(UUIDUtil.getUUID(), "https://img2.baidu.com/it/u=2883734066,3070082682&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500", "土豆丝盖饭", "盖浇饭(面)", 16.8, 100);
        StoreBean.Goods g13 = new StoreBean.Goods(UUIDUtil.getUUID(), "https://img0.baidu.com/it/u=3092465916,2648848643&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500", "牛肉烧牛肉盖饭", "盖浇饭(面)", 20.8, 100);
        StoreBean.Goods g14 = new StoreBean.Goods(UUIDUtil.getUUID(), "https://img1.baidu.com/it/u=1814072173,3673832201&fm=253&fmt=auto&app=138&f=JPEG?w=640&h=384", "青椒炒蛋", "西北特色菜", 18.8, 100);
        StoreBean.Goods g15 = new StoreBean.Goods(UUIDUtil.getUUID(), "https://img1.baidu.com/it/u=540552827,715806715&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500", "青椒土豆丝", "西北特色菜", 22.0, 100);
        StoreBean.Goods g16 = new StoreBean.Goods(UUIDUtil.getUUID(), "https://img1.baidu.com/it/u=540552827,715806715&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500", "酸辣白菜", "西北特色菜", 18.0, 100);
        StoreBean.Goods g17 = new StoreBean.Goods(UUIDUtil.getUUID(), "https://img0.baidu.com/it/u=3039685960,669193345&fm=253&fmt=auto&app=120&f=JPEG?w=660&h=493", "番茄炒蛋", "西北特色菜", 18.0, 100);
        StoreBean.Goods g18 = new StoreBean.Goods(UUIDUtil.getUUID(), "https://img0.baidu.com/it/u=556964291,594054334&fm=253&fmt=auto&app=138&f=JPEG?w=890&h=500", "红烧鸡块", "西北特色菜", 45.0, 100);
        StoreBean.Goods g19 = new StoreBean.Goods(UUIDUtil.getUUID(), "https://img1.baidu.com/it/u=2135400829,1448574902&fm=253&fmt=auto&app=138&f=JPEG?w=667&h=500", "孜然牛肉", "西北特色菜", 53.0, 100);
        StoreBean.Goods g20 = new StoreBean.Goods(UUIDUtil.getUUID(), "https://img0.baidu.com/it/u=2825733849,2494339808&fm=253&fmt=auto&app=138&f=JPEG?w=640&h=427", "新疆大盘鸡", "西北特色菜", 89.0, 100);
        StoreBean.Goods g21 = new StoreBean.Goods(UUIDUtil.getUUID(), "https://img2.baidu.com/it/u=3744750923,2856551476&fm=253&fmt=auto&app=138&f=JPEG?w=855&h=500", "凉拌土豆丝", "凉菜", 15.0, 100);
        StoreBean.Goods g22 = new StoreBean.Goods(UUIDUtil.getUUID(), "https://img2.baidu.com/it/u=2542377015,3754015865&fm=253&fmt=auto&app=138&f=JPEG?w=650&h=400", "凉拌牛肉", "凉菜", 54.0, 100);
        StoreBean.Goods g23 = new StoreBean.Goods(UUIDUtil.getUUID(), "https://img0.baidu.com/it/u=2214438882,247126097&fm=253&fmt=auto&app=138&f=JPEG?w=577&h=438", "牛肉粉丝汤", "汤类", 23.0, 100);
        StoreBean.Goods g24 = new StoreBean.Goods(UUIDUtil.getUUID(), "https://img2.baidu.com/it/u=2518731073,3315121438&fm=253&fmt=auto&app=138&f=JPEG?w=751&h=500", "西红柿鸡蛋汤", "汤类", 15.0, 100);
        StoreBean.Goods g25 = new StoreBean.Goods(UUIDUtil.getUUID(), "https://img0.baidu.com/it/u=516213803,3174116539&fm=253&fmt=auto&app=138&f=JPEG?w=640&h=426", "白米饭", "米饭", 1.0, 1000);
        goodsList.add(g1);
        goodsList.add(g2);
        goodsList.add(g3);
        goodsList.add(g4);
        goodsList.add(g5);
        goodsList.add(g6);
        goodsList.add(g7);
        goodsList.add(g8);
        goodsList.add(g9);
        goodsList.add(g10);
        goodsList.add(g11);
        goodsList.add(g12);
        goodsList.add(g13);
        goodsList.add(g14);
        goodsList.add(g15);
        goodsList.add(g16);
        goodsList.add(g17);
        goodsList.add(g18);
        goodsList.add(g19);
        goodsList.add(g20);
        goodsList.add(g21);
        goodsList.add(g22);
        goodsList.add(g23);
        goodsList.add(g24);
        goodsList.add(g25);

        return goodsList;
    }

    public static String getUserId() {
        return userId;
    }
}
