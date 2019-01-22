package id.web.skytacco.cataloguemovie.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import static id.web.skytacco.cataloguemovie.Database.MovieContract.MovieColumns.ID_MOVIE;
import static id.web.skytacco.cataloguemovie.Database.MovieContract.MovieColumns.TABLE_MOVIE;

public class MovieHelper {

    private static String DATABASE_TABLE = TABLE_MOVIE;
    private Context context;

    private SQLiteDatabase mDb;

    public MovieHelper(Context context) {
        this.context = context;
    }

    public MovieHelper open() throws SQLException {
        DatabaseHelper mdatabaseHelper = new DatabaseHelper(context);
        mDb = mdatabaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mDb.close();
    }

    public Cursor queryProvider() {
        return mDb.query(
                DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                MovieContract.MovieColumns._ID + " DESC"
        );
    }

    public Cursor queryByIdProvider(String id) {
        return mDb.query(DATABASE_TABLE, null
                , ID_MOVIE + " = ?"
                , new String[]{id}
                , null
                , null
                , null
                , null);
    }

    public long insertProvider(ContentValues values) {
        return mDb.insert(DATABASE_TABLE, null, values);
    }

    public int updateProvider(String id, ContentValues values) {
        return mDb.update(DATABASE_TABLE, values, ID_MOVIE + " = ?", new String[]{id});
    }

    public int deleteProvider(String id) {
        return mDb.delete(DATABASE_TABLE, ID_MOVIE + " = ?", new String[]{id});
    }
}
