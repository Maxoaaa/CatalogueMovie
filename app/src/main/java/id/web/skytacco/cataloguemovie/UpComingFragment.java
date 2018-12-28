package id.web.skytacco.cataloguemovie;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import id.web.skytacco.cataloguemovie.Adapter.MovieRvAdapter;
import id.web.skytacco.cataloguemovie.AsyncTaskLoader.MvComingAsyncTaskLoader;

import static android.content.ContentValues.TAG;

public class UpComingFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<MovieItem>>{
    public static final String EXTRAS = "extras";
    private RecyclerView rvCategory;
    private RecyclerView.Adapter adapter;
    private ArrayList<MovieItem> movieLists;
    private static final String API_KEY = BuildConfig.TMDB_API_KEY;

/*    View.OnClickListener mvListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            *//*String movieTitle = txtJudul.getText().toString();
            if (TextUtils.isEmpty(movieTitle)) {
                return;
            }*//*

            Bundle mbundle = new Bundle();
            //mbundle.putString(EXTRAS_MOVIE, movieTitle);
            getSupportLoaderManager().restartLoader(0, mbundle, MainActivity.this);
        }
    };
    AdapterView.OnItemClickListener lvListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
            MovieItem item = (MovieItem) parent.getItemAtPosition(position);
            Intent intent = new Intent(getActivity().getApplicationContext(), DetailActivity.class);

            intent.putExtra(DetailActivity.EXTRA_TITLE, item.getMovie_title());
            intent.putExtra(DetailActivity.EXTRA_OVERVIEW, item.getMovie_description());
            intent.putExtra(DetailActivity.EXTRA_DATE, item.getMovie_date());
            intent.putExtra(DetailActivity.EXTRA_IMAGE, item.getMovie_image());

            startActivity(intent);
        }
    };*/
    public UpComingFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getActivity().getSupportLoaderManager().initLoader(0, null, this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_up_coming, container, false);
        rvCategory = view.findViewById(R.id.rv_category);
        rvCategory.setHasFixedSize(true);
        showRecyclerList();
        //movieLists = new ArrayList<>();

        getActivity().getSupportLoaderManager().initLoader(0, null, this);
        return view ;
    }
    private void showRecyclerList(){
        rvCategory.setLayoutManager(new LinearLayoutManager(getActivity()));
        //adapter = new MovieRvAdapter(movieLists, getActivity());

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
    public Loader<ArrayList<MovieItem>> onCreateLoader(int i, @Nullable Bundle args) {
        /*pgsBar.setVisibility(View.VISIBLE);
        String movies = "";
        if (args != null) {
            movies = args.getString(EXTRAS_MOVIE);
        }*/
        return new MvComingAsyncTaskLoader(getActivity().getApplicationContext());
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<MovieItem>> loader, ArrayList<MovieItem> mdata) {
        //pgsBar.setVisibility(View.GONE);
        //adapter.setData(mdata);
        rvCategory.setAdapter(new MovieRvAdapter(mdata, getActivity()));
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<MovieItem>> loader) {
       // pgsBar.setVisibility(View.GONE);
        //adapter.setData(null);
        //rvCategory.setAdapter(null);
    }
}
