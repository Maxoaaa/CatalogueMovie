package id.web.skytacco.favoritemovies.Listener;

import android.view.View;

public class ItemClickSupport implements View.OnClickListener {
    private int position;
    private OnItemClickCallback onItemClickCallback;

    public ItemClickSupport(int position, OnItemClickCallback onItemClickCallback) {
        this.position = position;
        this.onItemClickCallback = onItemClickCallback;
    }


    public void onClick(View view) {
        onItemClickCallback.onItemClicked(view, position);
    }

    public interface OnItemClickCallback {
        void onItemClicked(View view, int position);
    }
}
