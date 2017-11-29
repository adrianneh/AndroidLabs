package android.labs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Adrianne on 2017-11-03.
 */

public class ChatDatabaseHelper extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "Messages.db";
    private static String TABLE_NAME = "MESSAGES";
    private static int VERSION_NUM =7;
    public static final String KEY_ID = " _id";
    public static final String KEY_MESSAGE = "message";

    public ChatDatabaseHelper(Context ctx){

        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_MESSAGE + " VARCHAR2(30)"
                + ");";
        db.execSQL(query);
        Log.i("ChatDatabaseHelper", "Calling onCreate");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
            Log.i("ChatDatabaseHelper", "Calling onUpgrade, oldVersion = " + oldVer + "newVersion=" + newVer);

    }


}
