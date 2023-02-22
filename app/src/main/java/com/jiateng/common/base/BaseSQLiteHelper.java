package com.jiateng.common.base;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import lombok.SneakyThrows;

public abstract class BaseSQLiteHelper<T> extends SQLiteOpenHelper {
    private Class<T> clazz = null;
    private static String DB_NAME = "orderFood.db";
    private static String TABLE_NAME = null;
    private static Integer DB_VERSION = 0;
    private SQLiteDatabase rdb = null;
    private SQLiteDatabase wdb = null;
    protected static BaseSQLiteHelper helper;


    {
        Type genericSuperclass = this.getClass().getGenericSuperclass(); //带泛型的父类
        ParameterizedType paramerizedType = (ParameterizedType) genericSuperclass;//转化为带参数的泛型
        Type[] actualTypeArguments = paramerizedType.getActualTypeArguments(); //获取父类的泛型参数
        clazz = (Class<T>) actualTypeArguments[0];//取出泛型的第一个参数
    }

    public BaseSQLiteHelper(Context context, String tableName, Integer dbVersion) {
        super(context, DB_NAME, null, dbVersion);
        this.TABLE_NAME = tableName;
        this.DB_VERSION = dbVersion;

    }

    public void openRWLink() {
        if (rdb == null || !rdb.isOpen()) {
            rdb = helper.getReadableDatabase();
        }
        if (wdb == null || !wdb.isOpen()) {
            wdb = helper.getWritableDatabase();
        }
    }

    public void close() {
        if (rdb != null && rdb.isOpen()) {
            rdb.close();
            rdb = null;
        }
        if (wdb != null && wdb.isOpen()) {
            wdb.close();
            wdb = null;
        }
    }

    /**
     * 建表部分
     *
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        createTable(db);
    }

    protected abstract void createTable(SQLiteDatabase db);

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //TODO
    }

    /**
     * 插入
     *
     * @param t
     * @return
     */
    @SneakyThrows
    protected int insert(T t) {
        ContentValues values = new ContentValues();
        Class<T> tClass = (Class<T>) t.getClass();
        Field[] fields = tClass.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            Object obj = field.get(t);
            if (obj != null) {
                values.put(field.getName(), obj.toString());
            }
        }
        return (int) wdb.insert(TABLE_NAME, null, values);
    }

    /**
     * @param whereClause
     * @param args
     * @return
     */
    protected int delete(String whereClause, Object... args) {
        String[] params = null;
        if (args != null) {
            params = Arrays.stream(args).map(o -> o + "").collect(Collectors.toList()).toArray(new String[args.length]);
        }
        return wdb.delete(TABLE_NAME, whereClause, params);
    }

    /**
     * 更新操作
     *
     * @param t
     * @param whereClause
     * @param args
     * @return
     */
    @SneakyThrows
    protected int update(T t, String whereClause, Object... args) {
        String[] params = null;
        if (args != null) {
            params = Arrays.stream(args).map(o -> o + "").collect(Collectors.toList()).toArray(new String[args.length]);
        }
        ContentValues values = new ContentValues();
        Class<T> tClass = (Class<T>) t.getClass();
        Field[] fields = tClass.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            Object obj = field.get(t);
            if (obj != null) {
                values.put(field.getName(), obj.toString());
            }
        }
        return wdb.update(TABLE_NAME, values, whereClause, params);
    }

    /**
     * 利用反射完成查询操作
     *
     * @param sql  完整的SQL，使用？占位
     * @param args ？按照占位符对应的字段填写参数
     * @return 返回查询出的列表
     */
    @SneakyThrows
    protected List<T> query(String sql, Object... args) {
        String[] params = null;
        if (args != null) {
            params = Arrays.stream(args).map(o -> o + "").collect(Collectors.toList()).toArray(new String[args.length]);
        }
        Cursor cursor = rdb.rawQuery(sql, params);
        List<T> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            T t = clazz.newInstance();
            for (int i = 0; i < cursor.getColumnCount(); i++) {
                String columnName = cursor.getColumnName(i);
                Field field = clazz.getDeclaredField(columnName);
                field.setAccessible(true);
                String type = field.getType().getName();
                Object obj = null;
                if ("java.lang.String".equals(type)) {
                    obj = cursor.getString(i);
                } else if ("java.lang.Double".equals(type)) {
                    obj = cursor.getDouble(i);
                } else if ("java.lang.Integer".equals(type)) {
                    obj = cursor.getInt(i);
                }
                //TODO 可以扩展其他类型
                field.set(t, obj);
            }
            list.add(t);
        }
        return list;
    }


    /**
     * 清空数据
     */
    protected void cleanData() {

        wdb.execSQL("delete from " + TABLE_NAME);
    }

}