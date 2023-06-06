package com.example.calculmentalapp.db.database;

import android.content.Context;

public class ScoreBaseHelper extends DataBaseHelper {
    public ScoreBaseHelper(Context context, String dataBaseName, int dataBaseVersion) {
        super(context, dataBaseName, dataBaseVersion);
    }

    @Override
    protected String getCreationSql() {
        return "CREATE TABLE IF NOT EXISTS " + ScoreDao.tableName + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                ScoreDao.userName + " TEXT," +
                ScoreDao.score + " INTEGER" +
                ")";
    }

    @Override
    protected String getDeleteSql() {
        return "DROP TABLE IF EXISTS " + ScoreDao.tableName;
    }
}
