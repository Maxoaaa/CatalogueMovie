package id.web.skytacco.cataloguemovie.Entity;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieFavItem implements Parcelable {
    private String id;
    private String title;
    private String description;
    private String date;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeString(this.date);
    }

    public MovieFavItem() {
    }

    private MovieFavItem(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.description = in.readString();
        this.date = in.readString();
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
