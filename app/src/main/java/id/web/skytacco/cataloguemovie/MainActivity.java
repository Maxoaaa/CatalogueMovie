package id.web.skytacco.cataloguemovie;

import android.content.Intent;
import android.os.Bundle;
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

import java.util.ArrayList;

import id.web.skytacco.cataloguemovie.Adapter.MovieAdapter;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<MovieItem>>  {
    ListView listView;
    EditText txtJudul;
    ImageView imgPoster;
    Button btnFind;
    MovieAdapter adapter;
    static final String EXTRAS_MOVIE = "EXTRAS_MOVIE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        mbundle.putString(EXTRAS_MOVIE, movieTitle);
        //inisialisasi dari loader masuk onCreate
        getSupportLoaderManager().initLoader(0, mbundle, this);

    }

//run MovieAsyncTaskLoader
    @Override
    public Loader<ArrayList<MovieItem>> onCreateLoader(int i, Bundle args) {
        String movies = "";
        if (args != null) {
            movies = args.getString(EXTRAS_MOVIE);
        }
        return new MovieAsyncTaskLoader(this, movies);
    }
//dipanggil saat proses load sudah selesai
    @Override
    public void onLoadFinished(Loader<ArrayList<MovieItem>> loader, ArrayList<MovieItem> mdata) {
        adapter.setData(mdata);
    }
//dipanggil saat loader direset
    @Override
    public void onLoaderReset(Loader<ArrayList<MovieItem>> loader) {
        adapter.setData(null);
    }
    View.OnClickListener mvListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String movieTitle = txtJudul.getText().toString();
            if(TextUtils.isEmpty(movieTitle)){ return; }

            Bundle mbundle = new Bundle();
            mbundle.putString(EXTRAS_MOVIE, movieTitle);
            getSupportLoaderManager().restartLoader(0, mbundle, MainActivity.this);
        }
    };
    AdapterView.OnItemClickListener lvListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
            MovieItem item = (MovieItem)parent.getItemAtPosition(position);
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);

            intent.putExtra(DetailActivity.EXTRA_TITLE, item.getMovie_title());
            intent.putExtra(DetailActivity.EXTRA_OVERVIEW, item.getMovie_description());
            intent.putExtra(DetailActivity.EXTRA_DATE, item.getMovie_date());
            intent.putExtra(DetailActivity.EXTRA_IMAGE, item.getMovie_image());

            startActivity(intent);
        }
    };
}
