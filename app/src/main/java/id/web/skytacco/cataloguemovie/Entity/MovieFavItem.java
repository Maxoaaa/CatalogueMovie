package id.web.skytacco.cataloguemovie.Entity;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieFavItem implements Parcelable {
    private String id;
    private String movie_title;
    private String movie_description;
    private String movie_date;
    private String movie_image;
    public MovieFavItem(String string) {
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.movie_title);
        dest.writeString(this.movie_description);
        dest.writeString(this.movie_date);
        dest.writeString(this.movie_image);
    }

    public MovieFavItem() {
    }

    public MovieFavItem(Parcel in) {
        this.id = in.readString();
        this.movie_title = in.readString();
        this.movie_description = in.readString();
        this.movie_date = in.readString();
        this.movie_image = in.readString();
    }

    public static final Parcelable.Creator<MovieFavItem> CREATOR = new Parcelable.Creator<MovieFavItem>() {
        @Override
        public MovieFavItem createFromParcel(Parcel source) {
            return new MovieFavItem(source);
        }

        @Override
        public MovieFavItem[] newArray(int size) {
            return new MovieFavItem[size];
        }
    };
}
