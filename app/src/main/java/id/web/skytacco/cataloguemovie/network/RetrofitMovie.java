package id.web.skytacco.cataloguemovie.network;

import id.web.skytacco.cataloguemovie.BuildConfig;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitMovie {

    private static Retrofit mretrofit = null;
    public static Retrofit getClient() {
        if (mretrofit == null) {
            mretrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.TMDB_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return mretrofit;
    }
}
