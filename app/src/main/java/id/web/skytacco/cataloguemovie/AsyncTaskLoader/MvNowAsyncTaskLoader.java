package id.web.skytacco.cataloguemovie.AsyncTaskLoader;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.AsyncTaskLoader;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import id.web.skytacco.cataloguemovie.BuildConfig;
import id.web.skytacco.cataloguemovie.Entity.MovieItem;

public class MvNowAsyncTaskLoader extends AsyncTaskLoader<ArrayList<MovieItem>> {
    private static final String API_KEY = BuildConfig.TMDB_API_KEY;
    private String urli;
    private ArrayList<MovieItem> miawData;
    private boolean mHasResult = false;

    public MvNowAsyncTaskLoader(@NonNull Context context, String url) {
        super(context);
        onContentChanged();
        this.urli = url;
    }

    //data nya mulai diloading/diproses
    @Override
    protected void onStartLoading() {
        if (takeContentChanged())
            forceLoad();
        else if (mHasResult)
            deliverResult(miawData);
    }

    public void deliverResult(ArrayList<MovieItem> data) {
        miawData = data;
        mHasResult = true;
        super.deliverResult(data);
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        if (mHasResult) {
            //onReleaseResources(miawData);
            miawData = null;
            mHasResult = false;
        }
    }

    @Override
    public ArrayList<MovieItem> loadInBackground() {
        SyncHttpClient client = new SyncHttpClient();

        final ArrayList<MovieItem> Banyak_Items = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/movie/" + urli + "?api_key=" + API_KEY + "&language=en-US";

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                //menggunakan synchronous krn thread menggunakan asynchronous
                //method loadInBackground mengembalikan nilai balikan
                setUseSynchronousMode(true);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject mv = list.getJSONObject(i);
                        MovieItem movieItems = new MovieItem(mv);
                        movieItems.setMovie_title(mv.getString("title"));
                        movieItems.setMovie_description(mv.getString("overview"));
                        movieItems.setMovie_date(mv.getString("release_date"));
                        movieItems.setMovie_image(mv.getString("poster_path"));
                        Banyak_Items.add(movieItems);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                //ketika respon gagal tidak melakukan apa-apa
            }
        });
        return Banyak_Items;
    }
}
