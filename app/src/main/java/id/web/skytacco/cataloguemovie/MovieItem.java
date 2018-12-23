package id.web.skytacco.cataloguemovie;

import org.json.JSONObject;

public class MovieItem {
    private String movie_title;
    private String movie_description;
    private String movie_date;
    private String movie_image;

    MovieItem(JSONObject object) {
        try {

            String title = object.getString("title");
            String description = object.getString("overview");
            String datei = object.getString("release_date");
            String image = object.getString("poster_path");

            this.movie_title = title;
            this.movie_description = description;
            this.movie_date = datei;
            this.movie_image = image;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getMovie_title() {
        return movie_title;
    }

    public void setMovie_title(String movie_title) {
        this.movie_title = movie_title;
    }

    public String getMovie_description() {
        return movie_description;
    }

    public void setMovie_description(String movie_description) {
        this.movie_description = movie_description;
    }

    public String getMovie_date() {
        return movie_date;
    }

    public void setMovie_date(String movie_date) {
        this.movie_date = movie_date;
    }

    public String getMovie_image() {
        return movie_image;
    }

    public void setMovie_image(String movie_image) {
        this.movie_image = movie_image;
    }
}