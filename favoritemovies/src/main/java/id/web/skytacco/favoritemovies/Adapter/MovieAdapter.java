package id.web.skytacco.favoritemovies.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.web.skytacco.favoritemovies.DetailActivity;
import id.web.skytacco.favoritemovies.Entity.MovieItem;
import id.web.skytacco.favoritemovies.Listener.ItemClickSupport;
import id.web.skytacco.favoritemovies.R;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private ArrayList<MovieItem> movieLists;
    private Context context;

    public MovieAdapter(ArrayList<MovieItem> movieLists, Context context) {
        this.movieLists = movieLists;
        this.context = context;
    }

    public void setData(ArrayList<MovieItem> items) {
        movieLists = items;
        notifyDataSetChanged();
    }

    public void addItem(final MovieItem item) {
        movieLists.add(item);
        notifyDataSetChanged();
    }

    public void clearData() {
        movieLists.clear();
    }

    public void setMovieResult(ArrayList<MovieItem> movieResult) {
        this.movieLists = movieResult;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final MovieItem itemList = movieLists.get(position);
        holder.title.setText(itemList.getMovie_title());
        holder.description.setText(itemList.getMovie_description());
        String ambilDate = itemList.getMovie_date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", new Locale("in", "ID"));
        try {
            Date date = dateFormat.parse(ambilDate);

            SimpleDateFormat newDateFormat = new SimpleDateFormat("dd-MM-yyyy", new Locale("in", "ID"));
            String date_of_release = newDateFormat.format(date);
            holder.date.setText(date_of_release);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Picasso.get()
                .load("http://image.tmdb.org/t/p/w154/" + itemList.getMovie_image())
                .placeholder(R.mipmap.placeholder_round)
                .into(holder.poster);

        holder.btnFavorite.setOnClickListener(new ItemClickSupport(position, new ItemClickSupport.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                Toast.makeText(context, "Favorite Success: ", Toast.LENGTH_SHORT).show();
            }
        }));

        holder.btnShare.setOnClickListener(new ItemClickSupport(position, new ItemClickSupport.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                //Toast.makeText(context, "Share:" + itemList.getMovie_title(), Toast.LENGTH_SHORT).show();
                //itemView.getContext().startActivity(si);
                Intent si = new Intent(android.content.Intent.ACTION_SEND);
                si.setType("text/plain");
                si.putExtra(android.content.Intent.EXTRA_SUBJECT, "Handoyo Oficial");
                si.putExtra(android.content.Intent.EXTRA_TEXT, "Dapatkan Informasi tentang Film " + itemList.getMovie_title() + " dan Lainnya. Kunjungi https://skytacco.web.id/ \n atau hubungi email REALTH99@GMAIL.COM");
                context.startActivity(Intent.createChooser(si, "Share via"));
            }
        }));

        holder.cv_selected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MovieItem itemFilmList = movieLists.get(position);
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra(DetailActivity.EXTRA_TITLE, itemFilmList.getMovie_title());
                intent.putExtra(DetailActivity.EXTRA_OVERVIEW, itemFilmList.getMovie_description());
                intent.putExtra(DetailActivity.EXTRA_DATE, itemFilmList.getMovie_date());
                intent.putExtra(DetailActivity.EXTRA_IMAGE, itemFilmList.getMovie_image());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieLists.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtTitle)
        TextView title;
        @BindView(R.id.txtDescription)
        TextView description;
        @BindView(R.id.txtDate)
        TextView date;
        @BindView(R.id.img_poster)
        ImageView poster;
        @BindView(R.id.btnFavorit)
        Button btnFavorite;
        @BindView(R.id.btnShare)
        Button btnShare;
        @BindView(R.id.cv_selected)
        ConstraintLayout cv_selected;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
