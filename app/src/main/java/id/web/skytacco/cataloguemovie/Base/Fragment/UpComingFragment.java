package id.web.skytacco.cataloguemovie.Base.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.web.skytacco.cataloguemovie.Adapter.MovieRvAdapter;
import id.web.skytacco.cataloguemovie.AsyncTaskLoader.MvNowAsyncTaskLoader;
import id.web.skytacco.cataloguemovie.Entity.MovieItem;
import id.web.skytacco.cataloguemovie.R;

public class UpComingFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<MovieItem>> {
    public static final String EXTRAS = "extras";
    private static final String url = "upcoming";
    @BindView(R.id.rv_category2)
    RecyclerView rvCategory;
    private ArrayList<MovieItem> movieLists;
    private Activity activity;

    public UpComingFragment() {
    }

    /*    @Override
        public void onAttach(Context context) {
            super.onAttach(context);
            activity = getActivity();
        }*/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getActivity().getSupportLoaderManager().restartLoader(0, null, this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_up_coming, container, false);
        ButterKnife.bind(this, view);
        rvCategory.setHasFixedSize(true);
        rvCategory.setLayoutManager(new LinearLayoutManager(getContext()));

        getActivity().getSupportLoaderManager().initLoader(0, null, this);
        return view;
    }

    @NonNull
    @Override
    public Loader<ArrayList<MovieItem>> onCreateLoader(int i, Bundle args) {
        return new MvNowAsyncTaskLoader(getContext(), url);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<MovieItem>> loader, ArrayList<MovieItem> mdata) {
        //pgsBar.setVisibility(View.GONE);
        rvCategory.setAdapter(new MovieRvAdapter(mdata, getContext()));
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<MovieItem>> loader) {
        // pgsBar.setVisibility(View.GONE);
        rvCategory.setAdapter(null);
    }
}
