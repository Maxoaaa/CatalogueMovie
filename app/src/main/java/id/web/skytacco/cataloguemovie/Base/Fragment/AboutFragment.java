package id.web.skytacco.cataloguemovie.Base.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import id.web.skytacco.cataloguemovie.R;

import static android.content.ContentValues.TAG;

public class AboutFragment extends Fragment {
    public static final String EXTRAS = "extras";

    public AboutFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_about, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            String page = getArguments().getString(EXTRAS);

            Log.e(TAG, "onCreateView: halaman fragment " + page);
        }
    }
}
