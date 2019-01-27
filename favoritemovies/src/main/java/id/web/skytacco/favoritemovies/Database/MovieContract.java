package id.web.skytacco.favoritemovies.Database;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class MovieContract {
    public static final String AUTHORITY = "id.web.skytacco.cataloguemovie";
    private static final String SCHEME = "content";

    public static final class MovieColumns implements BaseColumns {
        public static final String TABLE_MOVIE = "movie";
        public static String ID_MOVIE = "movie_id";
        public static String TITLE_MOVIE = "title";
        public static String DATE_MOVIE = "title";
        public static String DESC_MOVIE = "title";
    }
    public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(MovieColumns.TABLE_MOVIE)
            .build();

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
