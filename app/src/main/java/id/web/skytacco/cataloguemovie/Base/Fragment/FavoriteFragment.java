package id.web.skytacco.cataloguemovie.Base.Fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.web.skytacco.cataloguemovie.Adapter.MovieRvAdapter;
import id.web.skytacco.cataloguemovie.BuildConfig;
import id.web.skytacco.cataloguemovie.Entity.MovieItem;
import id.web.skytacco.cataloguemovie.R;

import static android.content.ContentValues.TAG;
import static id.web.skytacco.cataloguemovie.Database.MovieContract.CONTENT_URI;
import static id.web.skytacco.cataloguemovie.Database.MovieContract.MovieColumns.ID_MOVIE;

public class FavoriteFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    public static final String EXTRAS = "extras";
    private static final String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=" + BuildConfig.TMDB_API_KEY + "&language=en-US";
    @BindView(R.id.rv_category)
    RecyclerView rvCategory;

    MovieRvAdapter adapter;
    ArrayList<MovieItem> movieLists;

    private final int MOVIE_ID = 110;
    public FavoriteFragment() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        ButterKnife.bind(this, view);
        showRecyclerList();

        getActivity().getSupportLoaderManager().initLoader(MOVIE_ID, null, this);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            String page = getArguments().getString(EXTRAS);
            Log.e(TAG, "onCreateView: halaman fragment " + page);
        }
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        movieLists = new ArrayList<>();
        return new CursorLoader(getContext(), CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        movieLists = getItem(data);
        for (MovieItem m : movieLists) {
            ambilDataAPI(m.getId());
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        //movieLists = getItem(null);
    }
    private void showRecyclerList() {
        rvCategory.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvCategory.setHasFixedSize(true);
        rvCategory.setItemAnimator(new DefaultItemAnimator());
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
    private void ambilDataAPI(String id) {
        //progressBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                "https://api.themoviedb.org/3/movie/"+ id +"?api_key=" + BuildConfig.TMDB_API_KEY + "&language=en-US",
                new Response.Listener<String>() {
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
                        movieLists.add(movieItems);

                    }

                    adapter = new MovieRvAdapter(movieLists, getActivity());
                    rvCategory.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //progressBar.setVisibility(View.GONE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Error, No Internet Connection Access", Toast.LENGTH_SHORT).show();
                //ambilDataAPI();
                //progressBar.setVisibility(View.GONE);

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getActivity()));
        requestQueue.add(stringRequest);
    }

}
