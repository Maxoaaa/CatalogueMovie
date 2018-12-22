package id.web.skytacco.cataloguemovie;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DetailActivity extends AppCompatActivity {
    public static String EXTRA_TITLE = "extra_title";
    public static String EXTRA_OVERVIEW = "extra_overview";
    public static String EXTRA_DATE = "extra_date_release";
    public static String EXTRA_IMAGE = "extra_image_movie";

    private TextView txtTitle, txtOverview, txtDate;
    private ImageView imgPoster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //get intent
        String title = getIntent().getStringExtra(EXTRA_TITLE);
        String overview = getIntent().getStringExtra(EXTRA_OVERVIEW);
        String image = getIntent().getStringExtra(EXTRA_IMAGE);
        String datee = getIntent().getStringExtra(EXTRA_DATE);


        txtTitle = findViewById(R.id.txtMovieTitle);
        txtOverview = findViewById(R.id.TxtOverviewContent);
        txtDate = findViewById(R.id.txtDate);
        imgPoster = findViewById(R.id.imgposter);

        //Format Date Release
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, dd-MMMMM-yyyy",new Locale("in", "ID"));
        try {
            Date date = dateFormat.parse(datee);

            //SimpleDateFormat newDateFormat = new SimpleDateFormat("EEEE, MMM dd, yyyy", new Locale("in","ID"));
            SimpleDateFormat newDateFormat = new SimpleDateFormat("yyyy", new Locale("in","ID"));
            String dateee = newDateFormat.format(date);
            txtDate.setText(dateee);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        //pass value using set function
        txtTitle.setText(title);
        txtOverview.setText(overview);
        Picasso.get().load("http://image.tmdb.org/t/p/original/" + image).into(imgPoster);
    }
}
