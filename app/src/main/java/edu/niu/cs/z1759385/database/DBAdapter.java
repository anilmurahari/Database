package edu.niu.cs.z1759385.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by anilm on 4/21/2016.
 */
public class DBAdapter {
    private static final String TAG = "DBAdapter";

    private static final String KEY_ROWID = "_id",
                                KEY_NAME  = "name",
                                KEY_STUDENTNUM = "studentnum";
    private static final String[] ALL_KEY = new String[]{KEY_ROWID,KEY_NAME,KEY_STUDENTNUM};

    public static final int COL_ROWID = 0,
                                COL_NAME = 1,
                                COL_STUDENTNUM = 2;
    private static final String DATABASE_NAME = "MyDB",
                                DATABASE_TABLE = "mainTable";

    private static final int DATABASE_VERSION = 1;

    private static String CREATE_SQL = "create table " + DATABASE_TABLE
                                        + "(" + KEY_ROWID + "integer primary key autoincrement, "
                                        + KEY_NAME + " text not null, "
                                           + KEY_STUDENTNUM + "integer not null " + ");";
    private DBHelper myDBHelper;
    private SQLiteDatabase db;

    public DBAdapter(Context context)
    {
        myDBHelper = new DBHelper(context);
    }
    public DBAdapter open()
    {
        db = myDBHelper.getWritableDatabase();
        return this;
    }
    public void close()
    {
        myDBHelper.close();
    }
    public long insertRow(String name,int studNum)
    {
        ContentValues rowValues = new ContentValues();
        rowValues.put(KEY_NAME,name);
        rowValues.put(KEY_STUDENTNUM, studNum);

        return db.insert(DATABASE_TABLE,null,rowValues);
    }

    public boolean deleteRow(long rowID)
    {
     String where = KEY_ROWID + "=" + rowID;
     return db.delete(DATABASE_TABLE,where,null) != 0;
    }

    public void deleteAll()
    {
        Cursor cursor = getAllRows();
        long rowId = cursor.getColumnIndexOrThrow(KEY_ROWID);

        boolean result = cursor.moveToFirst();
        while(result)
        {
            deleteRow(cursor.getInt((int)rowId));
            result = cursor.moveToNext();
        }
    }
    public Cursor getAllRows()
    {
        String where = null;
        Cursor cursor = db.query(true, DATABASE_TABLE, ALL_KEY, where, null, null, null, null, null);
        if(cursor != null)
        {
            cursor.moveToFirst();
        }
        return cursor;
    }
    public Cursor getARow(long rowID)
    {
        String where = KEY_ROWID + "=" +rowID;
        Cursor cursor = db.query(true,DATABASE_TABLE,ALL_KEY,where,null,null,null,null,null);
        if(cursor != null)
        {
            cursor.moveToFirst();
        }
        return cursor;
    }
  //Inner class
    private static class DBHelper extends SQLiteOpenHelper
    {


        public DBHelper(Context context) {
            super(context,DATABASE_NAME,null,DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_SQL);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG,"Upgrading application's daataabase from "+ oldVersion + "to " + newVersion
               + " , which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            onCreate(db);
        }
    }





















}
