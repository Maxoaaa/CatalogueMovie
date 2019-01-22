package id.web.skytacco.cataloguemovie.Database;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class MovieContract {
    // Authority yang digunakan
    public static final String AUTHORITY = "id.web.skytacco.cataloguemovie";
    private static final String SCHEME = "content";

    private MovieContract(){}

    public static final class MovieColumns implements BaseColumns {
        public static String TABLE_MOVIE = "movie";
        public static String ID_MOVIE = "movie_id";
        static String TITLE_MOVIE = "movie_title";
    }
    public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(MovieColumns.TABLE_MOVIE)
            .build();
    //Digunakan untuk mempermudah akses data didalam cursor dengan parameter nama column
    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    public static long getColumnLong(Cursor cursor, String columnName) {
        return cursor.getLong(cursor.getColumnIndex(columnName));
    }
}
