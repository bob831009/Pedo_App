package name.bagi.levente.pedometer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by BenChen on 2015/8/22.
 */
public class DbHelper extends SQLiteOpenHelper{

    public final static int DB_VERSION = 1;
    public final static String DATABASE_NAME = "MyDatabases.db";
    public final static String TABLE_NAME = "slowList";
    // 資料庫物件，固定的欄位變數
    private static SQLiteDatabase database;
    public final static String SPEED = "speed";
    public final static String SONG_NAME = "song";
    //public final static String SONG_POSITION = "song_position" ;


    //constructor
    public DbHelper(Context context){
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        final String SQL = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (id integer primary key autoincrement, "
                + SONG_NAME +" text,"+ SPEED+" text)";
        db.execSQL(SQL);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }


    public boolean create(String names, String speeds ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues item = new ContentValues();
        item.put(SONG_NAME, names);
        item.put(SPEED, speeds);
        //item.put(SONG_POSITION , position) ;
        try {
            Log.v("my debug", "YYYYYYYYYYYYYYYY");
            db.insertOrThrow(TABLE_NAME, null, item);
        }
        catch (SQLiteException e){
            Log.v("my debug", "GGGGGGGGGGGGG");
            return false;
        }
        return true;
    }

    public Cursor get(String spd){

        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = new String[]{"ID", SONG_NAME, SPEED};
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + SPEED + " = ? "   , new String[]{spd} );
        //Cursor res = db.rawQuery(TABLE_NAME, columns, null, null, null, null, null);
        //Log.e("!!!!!!!!!!!!!!res found", "" + res.getCount());
        // String[] ans = new String[res.getCount()];
//        ArrayList<String> ans = new ArrayList<String>();
//        int rows_num = res.getCount();//取得資料表列數
//        if(rows_num != 0) {
//            res.moveToFirst();   //將指標移至第一筆資料
//            for(int i=0; i<rows_num; i++) {
//                if(res.getString(1).equals(spd)) {
//                    ans.add(res.getString(0));
//                    res.moveToNext();//將指標移至下一筆資料
//                }
//            }
//        }
//        res.close();
        return res;
    }



}