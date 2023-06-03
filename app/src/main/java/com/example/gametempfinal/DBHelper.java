package com.example.gametempfinal;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.File;

public class DBHelper  extends SQLiteOpenHelper {

    // good 100 %

    private Context context;
    public static String userID = "";
    private static final String DATABASE_NAME = "GameTown.db";
    private static final String TABLE_NAME = "my_games";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "game_name";
    private static final String COLUMN_TYPE = "game_type";
    private static final String COLUMN_PRICE = "game_price";  // or int
    private static final String COLUMN_USER = "username";
    private static final String COLUMN_STATUS = "status";

    //***********************************************************************************
    // cons
    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context = context;
    }
    public void setUSERID(String s)
    {
        userID=s;
    }


    //***********************************************************************************
    // on create
    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("create Table usersdb(username TEXT primary key, password TEXT ,email TEXT ,phone NUMBER)");
        //Deema

        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_TYPE + " TEXT, " +
                COLUMN_PRICE + " INTEGER," +
                COLUMN_USER + " TEXT, " +
                COLUMN_STATUS + " TEXT);";
        MyDB.execSQL(query);
        //reem
        MyDB.execSQL("create Table rentedgames(id TEXT primary key, name TEXT ,type TEXT ,price TEXT,time TEXT ,date TEXT,userid TEXT)");
    }



    //***********************************************************************************

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("drop Table if exists usersdb");

        //deema
        MyDB.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(MyDB);
        //reem
        MyDB.execSQL("drop Table if exists rentedgames");

    }

    //***********************************************************************************

    //reem

    public Boolean insertData(String username, String password ,String email , String phone){
        userID =username;
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        contentValues.put("email", email);
        contentValues.put("phone", phone);
        long result = MyDB.insert("usersdb", null, contentValues);
        if(result==-1) return false;
        else
            return true;
    }

    public Boolean checkusername(String username) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from usersdb where username = ?", new String[]{username});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }
    public Boolean checkemail(String email) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from usersdb where email = ?", new String[]{email});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }
    public Boolean checkphone(String phone) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from usersdb where phone = ?", new String[]{phone});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public Boolean checkusernamepassword(String username, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from usersdb where username = ? and password = ?", new String[] {username,password});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }


    //***********************************************************************************

    //deema
    void addGame(String name, String type, int price){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_TYPE, type);
        cv.put(COLUMN_PRICE, price);
        cv.put(COLUMN_USER, userID);
        cv.put(COLUMN_STATUS, "yes");

        long result = db.insert(TABLE_NAME, null, cv);
        if(result==-1){
            // nothing happens. no one is added.
        }else {
            Toast.makeText(context, "Added Game successfully", Toast.LENGTH_SHORT).show();
          //  Toast.makeText(context, "USER:"+ userID, Toast.LENGTH_SHORT).show();

        } }

    //***********************************************************************************
    void addRentedGame(String id ,String name ,String type , String price,String time , String date){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("id", id);
        cv.put("name", name);
        cv.put("type", type);
        cv.put("price", price);
        cv.put("time", time);
        cv.put("date", date);
        cv.put("userid", userID);

        ContentValues cd = new ContentValues();
        cd.put(COLUMN_STATUS,"no");

        long result = db.insert("rentedgames", null, cv);
        if(result==-1){
            // nothing happens. no one is added.
        }else {
            Toast.makeText(context, "Add Successfully!", Toast.LENGTH_SHORT).show();
           // Toast.makeText(context, "USER:"+ userID, Toast.LENGTH_SHORT).show();
            long resultUpd = db.update(TABLE_NAME , cd,"_id=?",new String[] {id});
            if(resultUpd!=-1)
            {
                Toast.makeText(context, "Rented Successfully", Toast.LENGTH_SHORT).show();
            }

        }

    }

    //for store data
    Cursor readAllData(){

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        if(db != null){
            cursor = db.rawQuery("Select * FROM "+TABLE_NAME+" WHERE username=? ", new String[] {userID});
            //Toast.makeText(context, "USER:"+ userID, Toast.LENGTH_SHORT).show();
        }
        return cursor;
    } //good
    //***********************************************************************************
    //retreving all games except
    Cursor readAllviewData(){

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery("Select * FROM "+TABLE_NAME+" WHERE username!=? and status=? ", new String[] {userID,"yes"});
           // Toast.makeText(context, "USER:"+ userID, Toast.LENGTH_SHORT).show();
        }
        return cursor;
    } //good
    //***********************************************************************************
    Cursor readAllRentedGames(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery("Select * FROM "+"rentedgames"+" WHERE userid=?  ", new String[] {userID});
           // Toast.makeText(context, "USER:"+ userID, Toast.LENGTH_SHORT).show();
        }
        return cursor;
    }


    void deleteOneRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});

        if(result == -1){
            // nothing happens. no one is deleted.
        }else{
            deleteRented(row_id);
            Toast.makeText(context, "Successfully Deleted", Toast.LENGTH_SHORT).show();
        }

    }

    void deleteRented(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete("rentedgames", "id=?", new String[]{id});
        ContentValues cd = new ContentValues();
        cd.put(COLUMN_STATUS,"yes");
        long resultUpd = db.update(TABLE_NAME , cd,"_id=?",new String[] {id});
        if (result ==-1 && resultUpd == -1){
            Toast.makeText(context, "Error.", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Returned Successfully", Toast.LENGTH_SHORT).show();

        }
    }
}


