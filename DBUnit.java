package com.example.administrator.myapplication;

/**
 * Created by Administrator on 2014/10/28.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

public class DBUnit {
    private Context mContext = null;

    private SQLiteDatabase mSQLiteDatabase = null;//���ڲ������ݿ�Ķ���
    private DBHelper dh = null;//���ڴ������ݿ�Ķ���
    Cursor namecursor=null;
    private String dbName = "DB_of_CUBE.db";
    private int dbVersion = 1;

    public DBUnit(Context context){
        mContext = context;
    }

    /**
     * �����ݿ�
     */
    public void open(){

        try{
            dh = new DBHelper(mContext, dbName, null, dbVersion);
            if(dh == null){

                return ;
            }
            mSQLiteDatabase = dh.getWritableDatabase();
            //dh.onOpen(mSQLiteDatabase);
            Log.i("log", "DB is opened");
        }catch(SQLiteException se){
            se.printStackTrace();
            Log.i("log", "open DB failed");
        }
    }

    /**
     * �ر����ݿ�
     */
    public void close(){

        mSQLiteDatabase.close();
        dh.close();
        Log.i("log", "DB is closed");
    }

    //��ȡ�б�
    public Cursor selectAllofporject(){
        Cursor cursor = null;
        try{
            String sql = "select * from project";
            cursor = mSQLiteDatabase.rawQuery(sql, null);
        }catch(Exception ex){
            ex.printStackTrace();
            cursor = null;
        }
        return cursor;
    }

    public Cursor selectAllofmeeting(){
        Cursor cursor = null;
        try{
            String sql = "select * from meeting";
            cursor = mSQLiteDatabase.rawQuery(sql, null);
        }catch(Exception ex){
            ex.printStackTrace();
            cursor = null;
        }
        return cursor;
    }

    public Cursor selectAlloftag(){
        Cursor cursor = null;
        try{
            String sql = "select * from tag";
            cursor = mSQLiteDatabase.rawQuery(sql, null);
        }catch(Exception ex){
            ex.printStackTrace();
            cursor = null;
        }
        return cursor;
    }

    public Cursor selectById(int id){

        //String result[] = {};
        Cursor cursor = null;
        try{
            String sql = "select * from travels where _id='" + id +"'";
            cursor = mSQLiteDatabase.rawQuery(sql, null);
        }catch(Exception ex){
            ex.printStackTrace();
            cursor = null;
        }

        return cursor;
    }
    public Cursor selectPathByName(String name){
        Cursor cursor=null;
        try{
            String sql = "select path from icons where filename='"+ name +"'";
            cursor=mSQLiteDatabase.rawQuery(sql, null);
            Log.i("log", sql);
        }catch(Exception ex){
            ex.printStackTrace();
            Log.i("log", "select failed");
        }
        return cursor;
    }
    //��������
    public long insert(String title, String content){
        Log.i("log", "ready to insert");
        long datetime = System.currentTimeMillis();
        Log.i("log", "time------>"+datetime);
        long l = -1;
        try{
            ContentValues cv = new ContentValues();
            cv.put("title", title);
            cv.put("content", content);
            cv.put("time", datetime);
            Log.i("log", "data----->"+title+content+datetime);
            l = mSQLiteDatabase.insert("travels", null, cv);
            Log.i("log", cv.toString());
            Log.i("log", datetime+""+l);
        }catch(Exception ex){
            ex.printStackTrace();
            l = -1;
        }
        return l;

    }
    public long inserticonpath(String filename,String iconpath){
        long l=-1;
        try{
            Log.i("log", "ready to insert icon");
            ContentValues cv = new ContentValues();
            cv.put("filename",filename);
            cv.put("path", iconpath);
            l = mSQLiteDatabase.insert("icons", null, cv);
            Log.i("log", "insert iconname success");
        }catch(Exception ex){
            ex.printStackTrace();
            l = -1;
        }
        return l;
    }
    //ɾ������
    public int delete(long id){
        int affect = 0;
        try{
            Log.i("log","try to delete the data in databases");
            affect = mSQLiteDatabase.delete("travels", "_id=?", new String[]{id+""});
            Log.i("log", "delete success");
        }catch(Exception ex){
            ex.printStackTrace();
            affect = -1;
            Log.i("log", "delete failed");
        }

        return affect;
    }

    //�޸�����
    public int update(int id, String title, String content){
        int affect = 0;
        try{
            ContentValues cv = new ContentValues();

            cv.put("title", title);
            cv.put("content", content);
            affect = mSQLiteDatabase.update("travels", cv, "_id=?", new String[]{id+""});
        }catch(Exception ex){
            ex.printStackTrace();
            affect = -1;
        }
        return affect;
    }
}
