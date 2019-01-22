package id.web.skytacco.cataloguemovie;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;

import id.web.skytacco.cataloguemovie.Adapter.MovieAdapter;
import id.web.skytacco.cataloguemovie.AsyncTaskLoader.MovieAsyncTaskLoader;
import id.web.skytacco.cataloguemovie.Entity.MovieItem;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<MovieItem>> {
    static final String EXTRAS_MOVIE = "EXTRAS_MOVIE";
    ListView listView;
    EditText txtJudul;
    ImageView imgPoster;
    Button btnFind;
    ProgressBar pgsBar;
    MovieAdapter adapter;
    View.OnClickListener mvListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String movieTitle = txtJudul.getText().toString();
            if (TextUtils.isEmpty(movieTitle)) {
                return;
            }

            Bundle mbundle = new Bundle();
            mbundle.putString(EXTRAS_MOVIE, movieTitle);
            getSupportLoaderManager().restartLoader(0, mbundle, MainActivity.this);
        }
    };
    AdapterView.OnItemClickListener lvListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
            MovieItem item = (MovieItem) parent.getItemAtPosition(position);
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);

            intent.putExtra(DetailActivity.EXTRA_TITLE, item.getMovie_title());
            intent.putExtra(DetailActivity.EXTRA_OVERVIEW, item.getMovie_description());
            intent.putExtra(DetailActivity.EXTRA_DATE, item.getMovie_date());
            intent.putExtra(DetailActivity.EXTRA_IMAGE, item.getMovie_image());

            startActivity(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pgsBar = findViewById(R.id.pgsBar);
        pgsBar.setVisibility(View.GONE);
        adapter = new MovieAdapter(this);
        adapter.notifyDataSetChanged();
        listView = findViewById(R.id.lvMovie);
        listView.setAdapter(adapter);

        txtJudul = findViewById(R.id.txtJudul);
        imgPoster = findViewById(R.id.MoviePict);
        btnFind = findViewById(R.id.btnFind);
        btnFind.setOnClickListener(mvListener);

        String movieTitle = txtJudul.getText().toString();

        listView.setOnItemClickListener(lvListener);
        Bundle mbundle = new Bundle();
        Bundle beva = getIntent().getExtras();
        String abe = beva.getString(EXTRAS_MOVIE);
        if (movieTitle != null && !EXTRAS_MOVIE.equals("EXTRAS_MOVIE")) {
            mbundle.putString(EXTRAS_MOVIE, movieTitle);
        } else {
            mbundle.putString(EXTRAS_MOVIE, abe);
        }
        //inisialisasi dari loader masuk onCreate
        getSupportLoaderManager().initLoader(0, mbundle, this);

    }

    //run MovieAsyncTaskLoader
    @NonNull
    @Override
    public Loader<ArrayList<MovieItem>> onCreateLoader(int i, Bundle args) {
        pgsBar.setVisibility(View.VISIBLE);
        String movies = "";
        if (args != null) {
            movies = args.getString(EXTRAS_MOVIE);
        }
        return new MovieAsyncTaskLoader(this, movies);
    }

    //dipanggil saat proses load sudah selesai
    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<MovieItem>> loader, ArrayList<MovieItem> mdata) {
        pgsBar.setVisibility(View.GONE);
        adapter.setData(mdata);
    }

    //dipanggil saat loader direset
    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<MovieItem>> loader) {
        pgsBar.setVisibility(View.GONE);
        adapter.setData(null);
    }
}
