package id.web.skytacco.cataloguemovie.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import id.web.skytacco.cataloguemovie.MovieItem;
import id.web.skytacco.cataloguemovie.R;

public class MovieAdapter extends BaseAdapter {
    private ArrayList<MovieItem> miawData = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context context;

    public MovieAdapter(Context context) {
        this.context = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setData(ArrayList<MovieItem> items) {
        miawData = items;
        notifyDataSetChanged();
    }

    public void addItem(final MovieItem item) {
        miawData.add(item);
        notifyDataSetChanged();
    }

    public void clearData() {
        miawData.clear();
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public int getCount() {
        //jika data nya null maka 0 , dikembaliin ke banyaknya data
        if (miawData == null) return 0;
        return miawData.size();
    }

    @Override
    public MovieItem getItem(int position) {
        return miawData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.list_movie, null);
            holder.txtTitle = convertView.findViewById(R.id.txtTitle);
            holder.txtDescription = convertView.findViewById(R.id.txtDescription);
            holder.txtDate = convertView.findViewById(R.id.txtDate);
            holder.imgPoster = convertView.findViewById(R.id.MoviePict);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.txtTitle.setText(miawData.get(position).getMovie_title());
        //proses menangkap Deskripsi pada movie
        String overview = miawData.get(position).getMovie_description();
        String deskripsi_final;
        if (TextUtils.isEmpty(overview)) {
            deskripsi_final = "No Have a Description";
        } else {
            deskripsi_final = overview;
        }
        holder.txtDescription.setText(deskripsi_final);
        //holder.txtDescription.setText(miawData.get(position).getDescription());

        //proses untuk menangkap  Date
        String ambilDate = miawData.get(position).getMovie_date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", new Locale("in", "ID"));
        try {
            Date date = dateFormat.parse(ambilDate);

            SimpleDateFormat newDateFormat = new SimpleDateFormat("yyyy", new Locale("in", "ID"));
            String date_of_release = newDateFormat.format(date);
            holder.txtDate.setText(date_of_release);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        //holder.txtDate.setText(miawData.get(position).getMovie_date());
        //holder.txtDate.setText(ambilDate);

        //proses untuk memunculkan gambar dgn ukuran w154
        Picasso.get().load("http://image.tmdb.org/t/p/w154/" +
                miawData.get(position).getMovie_image())
                .placeholder(context.getResources().getDrawable(R.mipmap.ic_launcher2_round))
                .error(context.getResources().getDrawable(R.mipmap.ic_launcher2_round))
                .into(holder.imgPoster);

        return convertView;
    }

    private static class ViewHolder {
        TextView txtTitle;
        TextView txtDescription;
        TextView txtDate;
        ImageView imgPoster;
    }
}
