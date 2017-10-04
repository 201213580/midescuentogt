package micupongt.com.micupongt;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by anton on 3/10/2017.
 */

public class ConexionLocal extends SQLiteOpenHelper {
    String sqlCreate = "CREATE TABLE Usuario (usuario TEXT, password TEXT)";
    public ConexionLocal(Context contexto, String nombre,
                         SQLiteDatabase.CursorFactory factory, int version) {
        super(contexto, nombre, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Usuarios");
        db.execSQL(sqlCreate);
    }
}
