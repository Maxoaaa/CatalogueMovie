package id.web.skytacco.cataloguemovie;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import id.web.skytacco.cataloguemovie.Adapter.MovieRvAdapter;
import id.web.skytacco.cataloguemovie.Model.MovieItem;

import static android.content.ContentValues.TAG;

public class NowPlayingFragment extends Fragment {
    public static final String EXTRAS = "extras";
    private static final String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=" + BuildConfig.TMDB_API_KEY + "&language=en-US";
    private RecyclerView rvCategory;
    private RecyclerView.Adapter adapter;
    private ArrayList<MovieItem> movieLists;

    public NowPlayingFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_now_playing, container, false);
        rvCategory = view.findViewById(R.id.rv_category);
        rvCategory.setHasFixedSize(true);
        showRecyclerList();
        movieLists = new ArrayList<>();
        ambilDataAPI();
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

    private void showRecyclerList() {
        rvCategory.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void ambilDataAPI() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url, new Response.Listener<String>() {
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

                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getActivity(), "Error" + error.toString(), Toast.LENGTH_SHORT).show();
                //ambilDataAPI();

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }


}
