package id.web.skytacco.cataloguemovie;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;


import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MovieAsyncTaskLoader extends AsyncTaskLoader<ArrayList<MovieItem>> {
    private ArrayList<MovieItem> miawData;
    private boolean mHasResult = false;
    private String mMovieTitle;

    public MovieAsyncTaskLoader(final Context context, String MovieTitle) {
        super(context);
        onContentChanged();
        this.mMovieTitle = MovieTitle;
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
    //private void onReleaseResources(ArrayList<MovieItem> miawData) {
        // untuk simple List<> tidak dilakukan apa-apa.
        // seperti Cursor, harus ditutup disini
    //}
    private static final String API_KEY = "23dd87e3bee02912925aa93845f265de";
    @Override
    public ArrayList<MovieItem> loadInBackground() {
        SyncHttpClient client = new SyncHttpClient();

        final ArrayList<MovieItem> Banyak_Items = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/search/movie?api_key=" +
                API_KEY+"&language=en-US&query="+mMovieTitle;

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

                    for (int i = 0 ; i < list.length() ; i++){
                        JSONObject mv = list.getJSONObject(i);
                        MovieItem movieItems = new MovieItem(mv);
                        Banyak_Items.add(movieItems);
                    }
                }catch (Exception e){
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
