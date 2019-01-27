package id.web.skytacco.favoritemovies;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.web.skytacco.favoritemovies.Database.MovieContract;
import id.web.skytacco.favoritemovies.Entity.MovieItem;

public class DetailActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    public static String EXTRA_TITLE = "extra_title";
    public static String EXTRA_OVERVIEW = "extra_overview";
    public static String EXTRA_DATE = "extra_date_release";
    public static String EXTRA_IMAGE = "extra_image_movie";

    @BindView(R.id.txtMovieTitle)
    TextView txtTitle;
    @BindView(R.id.TxtOverviewContent)
    TextView txtOverview;
    @BindView(R.id.txtDate)
    TextView txtDate;
    @BindView(R.id.imgposter)
    ImageView imgPoster;
    @BindView(R.id.btnFavorite)
    Button btnFavorite;
    MovieItem list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        //get intent
        String title = getIntent().getStringExtra(EXTRA_TITLE);
        String overview = getIntent().getStringExtra(EXTRA_OVERVIEW);
        final String image = getIntent().getStringExtra(EXTRA_IMAGE);
        String datee = getIntent().getStringExtra(EXTRA_DATE);

        TextView txtTitle = findViewById(R.id.txtMovieTitle);
        TextView txtOverview = findViewById(R.id.TxtOverviewContent);
        TextView txtDate = findViewById(R.id.txtDate);
        final ImageView imgPoster = findViewById(R.id.imgposter);

        //Format Date Release
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", new Locale("in", "ID"));
        Log.e(TAG, "onCreateoooooooooooooooooo: "+datee );
        //Date date = dateFormat.parse(datee);

        //SimpleDateFormat newDateFormat = new SimpleDateFormat("EEEE, MMM dd, yyyy", new Locale("in","ID"));
        SimpleDateFormat newDateFormat = new SimpleDateFormat("yyyy", new Locale("in", "ID"));
        // String dateee = newDateFormat.format(date);
        // txtDate.setText(dateee);

        //pass value using set function
        txtTitle.setText(title);
        txtOverview.setText(overview);
        Picasso.get().load("http://image.tmdb.org/t/p/original/" + image)
                .placeholder(R.mipmap.placeholder_round)
                .error(R.mipmap.placeholder_round)
                .into(imgPoster);

        list = getIntent().getParcelableExtra("movie_detail");
        btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isFavorite(list.getId().toString())) {
                    ContentValues mContentValues = new ContentValues();
                    mContentValues.put(MovieContract.MovieColumns.ID_MOVIE, list.getId().toString());
                    mContentValues.put(MovieContract.MovieColumns.TITLE_MOVIE, list.getMovie_title());
                    getContentResolver().insert(MovieContract.CONTENT_URI, mContentValues);

                    btnFavorite.setText(getResources().getString(R.string.favorite2));
                    Snackbar.make(v, "This movie has been add to your favorite", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                } else {
                    Uri uri = MovieContract.CONTENT_URI;
                    uri = uri.buildUpon().appendPath(list.getId().toString()).build();
                    getContentResolver().delete(uri, null, null);

                    btnFavorite.setText(getResources().getString(R.string.favorite2));
                    Snackbar.make(v, "This movie has been remove from your favorite", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
            }
        });
    }

    private boolean isFavorite(String id) {
        String selection = " movie_id = ?";
        String[] selectionArgs = {id};
        String[] projection = {MovieContract.MovieColumns.ID_MOVIE};
        Uri uri = MovieContract.CONTENT_URI;
        uri = uri.buildUpon().appendPath(id).build();

        Cursor cursor = null;
        cursor = getContentResolver().query(uri, projection, selection, selectionArgs, null, null);

        boolean exists = false;
        if (cursor != null) {
            exists = (cursor.getCount() > 0);
            cursor.close();
        }
        return exists;
    }
}
