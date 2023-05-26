//package com.example.calculmentalapp.db.database;
//
//import android.content.Context;
//
//public class ScoreBaseHelper extends DataBaseHelper{
//    public ScoreBaseHelper(Context context, String dataBaseName, int dataBaseVersion) {
//        super(context, dataBaseName, dataBaseVersion);
//    }
//
//    @Override
//    protected String getCreationSql() {
//        return "CREATE TABLE IF NOT EXISTS " + ScoreDao.tableName + " (" +
//                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
//                ScoreDao.premierElement + " INTEGER NOT NULL," +
//                ScoreDao.deuxiemeElement + " INTEGER NOT NULL," +
//                ScoreDao.symbol + " VARCHAR(5) not null," +
//                ScoreDao.resultat + " INTEGER NOT NULL" +
//                ")";
//    }
//
//    @Override
//    protected String getDeleteSql() {
//        return "DROP TABLE IF EXISTS " + ScoreDao.tableName;
//    }
//}
