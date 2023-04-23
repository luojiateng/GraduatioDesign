package com.jiateng.db;

import java.util.List;

/**
 * @Description:
 * @Title: RecordDaoInter
 * @ProjectName: MyApp
 * @date: 2023/2/10 20:29
 * @author: 骆家腾
 */
public interface RecordDao {
    List<String> queryData(String name);

    boolean hasData(String name);

    int insertData(String name);

    int delete(String name);

    void deleteData();

    List<String> queryAll();



}
