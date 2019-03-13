package id.web.skytacco.cataloguemovie.Base.Activity;

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
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.web.skytacco.cataloguemovie.Database.MovieContract;
import id.web.skytacco.cataloguemovie.Entity.MovieItem;
import id.web.skytacco.cataloguemovie.R;

public class DetailActivity extends AppCompatActivity {
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
    @BindView(R.id.btnFavorite)
    Button btnFavorite;
    @BindView(R.id.imgposter)
    ImageView imgPoster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        //get intent
        final String title = getIntent().getStringExtra(EXTRA_TITLE);
        String overview = getIntent().getStringExtra(EXTRA_OVERVIEW);
        final String image = getIntent().getStringExtra(EXTRA_IMAGE);
        String datee = getIntent().getStringExtra(EXTRA_DATE);

        //Format Date Release
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", new Locale("in", "ID"));
        try {
            Date date = dateFormat.parse(datee);
            //SimpleDateFormat newDateFormat = new SimpleDateFormat("EEEE, MMM dd, yyyy", new Locale("in","ID"));
            SimpleDateFormat newDateFormat = new SimpleDateFormat("yyyy", new Locale("in", "ID"));
            String dateee = newDateFormat.format(date);
            txtDate.setText(dateee);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //pass value using set function
        txtTitle.setText(title);
        txtOverview.setText(overview);
        Picasso.get().load("http://image.tmdb.org/t/p/original/" + image)
                .placeholder(R.mipmap.ic_launcher2_round)
                .error(R.mipmap.ic_launcher2_round)
                .into(imgPoster);

        final MovieItem itemList = getIntent().getParcelableExtra("DETAILL");
        if (isFavorite(itemList.getId().toString())) {
            if (btnFavorite != null) {
                btnFavorite.setText(getResources().getString(R.string.favorite));
            }
        }

        if (btnFavorite != null) {
            btnFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isFavorite(itemList.getId().toString())) {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(MovieContract.MovieColumns.ID_MOVIE, itemList.getId());
                        contentValues.put(MovieContract.MovieColumns.TITLE_MOVIE, title);
                        getContentResolver().insert(MovieContract.CONTENT_URI, contentValues);
                        btnFavorite.setText(getResources().getString(R.string.favorite));
                        Toast.makeText(getApplicationContext(), "This movie has been add to your favorite" , Toast.LENGTH_SHORT).show();
                    } else {
                        Uri uri = MovieContract.CONTENT_URI;
                        uri = uri.buildUpon().appendPath(itemList.getId()).build();
                        getContentResolver().delete(uri, null, null);
                        btnFavorite.setText(getResources().getString(R.string.favorite2));
                        //Log.v("MovieDetail", "" + uri);
                        //Log.v("MovieDetail", uri.toString());
                        Toast.makeText(getApplicationContext(), "This movie has been remove from your favorite", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    private boolean isFavorite(String id) {
        String selection = " movie_id = ?";
        String[] selectionArgs = {id};
        String[] projection = {MovieContract.MovieColumns.ID_MOVIE};
        Uri uri = MovieContract.CONTENT_URI;
        uri = uri.buildUpon().appendPath(id).build();

        Cursor cursor = null;
        cursor = getContentResolver().query(uri, projection, selection, selectionArgs, null, null);

        assert cursor != null;
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }
}
