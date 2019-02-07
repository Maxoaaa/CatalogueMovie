package id.web.skytacco.favoritemovies;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.web.skytacco.favoritemovies.Adapter.MovieAdapter;
import id.web.skytacco.favoritemovies.Entity.MovieItem;

import static id.web.skytacco.favoritemovies.Database.MovieContract.CONTENT_URI;
import static id.web.skytacco.favoritemovies.Database.MovieContract.MovieColumns.ID_MOVIE;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private final String TAG = getClass().getSimpleName();
    @BindView(R.id.rv_category)
    RecyclerView rv_category;

    MovieAdapter adapter;
    ArrayList<MovieItem> MovieLists;

    private final int MOVIE_ID = 110;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
       // adapter = new MovieAdapter(getApplicationContext());
        rv_category.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rv_category.setHasFixedSize(true);
        rv_category.setItemAnimator(new DefaultItemAnimator());

        getSupportLoaderManager().initLoader(MOVIE_ID, null, this);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        MovieLists = new ArrayList<>();
        return new CursorLoader(this, CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        MovieLists = getItem(data);
        for (MovieItem m : MovieLists) {
            ambilDataAPI(m.getId());
            adapter.notifyDataSetChanged();
        }
        //dicodingNotesAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        MovieLists = getItem(null);
        //dicodingNotesAdapter.swapCursor(null);
    }
    private ArrayList<MovieItem> getItem(Cursor cursor) {
        ArrayList<MovieItem> list = new ArrayList<>();
        cursor.moveToFirst();
        MovieItem favorite;
        if (cursor.getCount() > 0) {
            do {
                favorite = new MovieItem(cursor.getString(cursor.getColumnIndexOrThrow(ID_MOVIE)));
                list.add(favorite);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        return list;
    }

    /*private void getFavoriteMovies(String id) {
        movieService = MovieClient.getClient().create(MovieInterface.class);
        movieResultCall = movieService.getMovieById(id, API_KEY);

        movieResultCall.enqueue(new Callback<MovieResult>() {
            @Override
            public void onResponse(@NonNull Call<MovieItem> call, @NonNull Response<MovieResult> response) {
                movieResults.add(response.body());
                adapter.setMovieResult(movieResults);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(@NonNull Call<MovieResult> call, @NonNull Throwable t) {
                movieResults = null;
            }
        });
    }*/
    private void ambilDataAPI(String id) {
        //progressBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                "https://api.themoviedb.org/3/movie/"+ id +"?api_key=" + BuildConfig.TMDB_API_KEY + "&language=en-US",
                new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject responseObject = new JSONObject(response);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject mv = list.getJSONObject(i);
                        MovieItem movieItems = new MovieItem(mv);

                        movieItems.setMovie_title(mv.getString("title"));
                        movieItems.setMovie_description(mv.getString("overview"));
                        movieItems.setMovie_date(mv.getString("release_date"));
                        movieItems.setMovie_image(mv.getString("poster_path"));
                        MovieLists.add(movieItems);
                    }
                    Log.e(TAG, "onCreateeeeeeeeeeeeeeeeeeeeeee: "+MovieLists );
                    adapter = new MovieAdapter(MovieLists,getApplicationContext());
                    rv_category.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //progressBar.setVisibility(View.GONE);
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MovieLists = null;
                Toast.makeText(getApplicationContext(), "Error, No Internet Connection Access", Toast.LENGTH_SHORT).show();
                //ambilDataAPI();
                //progressBar.setVisibility(View.GONE);

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (MovieLists != null) {
            MovieLists.clear();
            //adapter.setMovieResult(MovieLists);
            rv_category.setAdapter(adapter);
        }
        getSupportLoaderManager().restartLoader(MOVIE_ID, null, this);
    }
}
