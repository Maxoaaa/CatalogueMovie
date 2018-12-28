package id.web.skytacco.cataloguemovie.Adapter;

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

import id.web.skytacco.cataloguemovie.DetailActivity;
import id.web.skytacco.cataloguemovie.Listener.ItemClickSupport;
import id.web.skytacco.cataloguemovie.MovieItem;
import id.web.skytacco.cataloguemovie.R;

public class MovieRvAdapter extends RecyclerView.Adapter<MovieRvAdapter.ViewHolder> {
    private ArrayList<MovieItem> movieLists;
    private Context context;

    public MovieRvAdapter(ArrayList<MovieItem> movieLists, Context context) {
        this.movieLists = movieLists;
        this.context = context;
    }
    @NonNull
    @Override
    public MovieRvAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieRvAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
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
                .load("http://image.tmdb.org/t/p/w154/"+itemList.getMovie_image())
                .placeholder(R.mipmap.ic_launcher2_round)
                .into(holder.poster);

        holder.btnFavorite.setOnClickListener(new ItemClickSupport(position, new ItemClickSupport.OnItemClickCallback()
        {
            @Override
            public void onItemClicked(View view, int position)
            {
                Toast.makeText(context, "Favorite Success: ", Toast.LENGTH_SHORT).show();
            }
        }));

        holder.btnShare.setOnClickListener(new ItemClickSupport(position, new ItemClickSupport.OnItemClickCallback()
        {
            @Override
            public void onItemClicked(View view, int position)
            {
                Toast.makeText(context, "Share:" +itemList.getMovie_title(), Toast.LENGTH_SHORT).show();
            }
        }));

        holder.cv_selected.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
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
        //init view objects use butterknife lib
        TextView title, description, date;
        ImageView poster;
        Button btnFavorite,btnShare;
        ConstraintLayout cv_selected;

        ViewHolder(View view) {
            super(view);
            title = itemView.findViewById(R.id.txtTitle);
            description = itemView.findViewById(R.id.txtDescription);
            date = itemView.findViewById(R.id.txtDate);
            poster = itemView.findViewById(R.id.img_poster);
            btnFavorite = itemView.findViewById(R.id.btnFavorit);
            btnShare = itemView.findViewById(R.id.btnShare);
            cv_selected = itemView.findViewById(R.id.cv_selected);
        }

    }
}
