package hitcube.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {

    private final static String DATABASE_NAME = "HITCUBEDB_01";//
    private final static int DATABASE_VERSION = 2;

    //构造函数，创建数据库
    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //建表
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + "Project"
                + "(_id INTEGER PRIMARY KEY,"
                + " project VARCHAR	NOT NULL,"
                + " detail VARCHAR)";
        db.execSQL(sql);
        String sql2 = "CREATE TABLE " +"Meeting"
                + "(_id INTEGER PRIMARY KEY,"
                + " meeting VARCHAR	NOT NULL,"
                + " detail VARCHAR,"
                + " linktoproject INTEGER)";
        db.execSQL(sql2);
        String sql3 = "CREATE TABLE " +"Note"
                + "(_id INTEGER PRIMARY KEY,"
                + " note VARCHAR	NOT NULL,"
                + " detail VARCHAR,"
                + " linktomeeting INTEGER)";
        db.execSQL(sql3);

    }


    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + "Project";
        db.execSQL(sql);
        sql = "DROP TABLE IF EXISTS " + "Meeting";
        db.execSQL(sql);
        sql = "DROP TABLE IF EXISTS " + "Note";
        db.execSQL(sql);
        onCreate(db);
    }

    //获取游标
    public Cursor select(String TABLE_NAME) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        return cursor;
    }

    //插入一条记录
    public long insert(String TABLE_NAME,String name,String detail) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("project", name);
        cv.put("detail", detail);
        long row = db.insert(TABLE_NAME, null, cv);
        return row;
    }

    public long insertmeeting(String TABLE_NAME,String name,String detail,int project_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("meeting", name);
        cv.put("detail", detail);
        cv.put("linktoproject",project_id);
        long row = db.insert(TABLE_NAME, null, cv);
        return row;
    }

    public long insertnote(String TABLE_NAME,String name,String detail,int meeting_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("note", name);
        cv.put("detail", detail);
        cv.put("linktomeeting",meeting_id);
        long row = db.insert(TABLE_NAME, null, cv);
        return row;
    }
    //根据条件查询
    public Cursor query(String TABLE_NAME,String[] args) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE project LIKE ?", args);
        return cursor;
    }

    public Cursor queryMeeting(String TABLE_NAME,String[] args) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE meeting LIKE ? and linktoproject=?", args);
        return cursor;
    }


    /*public Cursor queryNote(String TABLE_NAME,String[] args) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE note LIKE ?", args);
        return cursor;
    }*/

    public Cursor select_linktoproject(String TABLE_NAME,int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE linktoproject=?", new String[]{id+""});
        return cursor;
    }

    public Cursor select_linktomeeting(String TABLE_NAME,int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE linktomeeting=?", new String[]{id+""});
        return cursor;
    }
    //删除记录
    public void delete(String TABLE_NAME,int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String where ="_id = ?";
        String[] whereValue = { id+"" };
        db.delete(TABLE_NAME, where, whereValue);
    }

    //更新记录
    public void update(String TABLE_NAME,int id,String name,String detail) {
        SQLiteDatabase db = this.getWritableDatabase();
        String where = "_id = ?";
        String[] whereValue = { id+"" };
        ContentValues cv = new ContentValues();
        cv.put("project", name);
        cv.put("detail", detail);
        db.update(TABLE_NAME, cv, where, whereValue);
    }
    public void updatemeeting(String TABLE_NAME,int id, String name,String detail) {
        SQLiteDatabase db = this.getWritableDatabase();
        String where = "_id = ?";
        String[] whereValue = { id+"" };
        ContentValues cv = new ContentValues();
        cv.put("meeting", name);
        cv.put("detail", detail);
        db.update(TABLE_NAME, cv, where, whereValue);
    }
    public void updatenote(String TABLE_NAME,int id, String name,String detail) {
        SQLiteDatabase db = this.getWritableDatabase();
        String where = "_id = ?";
        String[] whereValue = { id+"" };
        ContentValues cv = new ContentValues();
        cv.put("note", name);
        cv.put("detail", detail);
        db.update(TABLE_NAME, cv, where, whereValue);
    }
}
