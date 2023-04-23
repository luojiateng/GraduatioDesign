package com.jiateng.db.impl;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.jiateng.base.BaseSQLiteHelper;
import com.jiateng.db.RecordDao;
import com.jiateng.domain.Record;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description:
 * @Title: RecordDaoImpl
 * @ProjectName: MyApp
 * @date: 2023/2/10 20:30
 * @author: 骆家腾
 */
public class RecordDaoImpl extends BaseSQLiteHelper<Record> implements RecordDao {

    private static RecordDaoImpl recordDao;
    private static String tableName = "records";

    public static RecordDaoImpl getInstance(Context context) {
        if (recordDao == null) {
            recordDao = new RecordDaoImpl(context, tableName, 1);
        }
        SQLiteDatabase writableDatabase = recordDao.getWritableDatabase();
        writableDatabase.execSQL("create table if not exists records(" +
                "id integer primary key autoincrement," +
                "name varchar(200))");
        helper = recordDao;
        helper.openRWLink();

        return recordDao;
    }

    private RecordDaoImpl(Context context, String tableName, Integer dbVersion) {
        super(context, tableName, dbVersion);
    }

    @Override
    protected void createTable(SQLiteDatabase db) {
    }


    @Override
    public List<String> queryData(String name) {
        String sql = "select id ,name from records where name like '% " + name + " %' order by id desc ";
        return query(sql, name).stream().map(Record::getName).collect(Collectors.toList());
    }

    @Override
    public boolean hasData(String name) {
        String sql = "select * from records where name = ?";
        List<Record> list = query(sql, name);
        return list == null;
    }

    @Override
    public int insertData(String name) {
        Record record = new Record();
        record.setName(name);
        if (queryOne(name) == null) {
            return (int) insert(record, tableName);
        } else {
            return 0;
        }
    }

    @Override
    public int delete(String name) {
        String whereClause = "name = ?";
        return (int) delete(whereClause, name);
    }

    @Override
    public void deleteData() {
        cleanData();
    }

    @Override
    public List<String> queryAll() {
        String sql = "select id ,name from records order by id desc";
        return query(sql).stream().map(Record::getName).collect(Collectors.toList());
    }

    private String queryOne(String name) {
        String sql = "select * from records where name = ?";
        List<String> query = query(sql, name).stream().map(Record::getName).collect(Collectors.toList());
        return query.size() == 0 ? null : query.get(0);
    }

}
