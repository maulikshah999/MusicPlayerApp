package com.musicplayer.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Maulik Shah on 12/8/2017.
 */

public class Song implements Parcelable {

    /**
     * Used Parcelable instead of Serializable which helps to improve performance
     */

    private String song_title = "", song_img = "", song_album_name = "", song_cost = "", song_collection_name = "", song_collection_price;

    public String getSong_collection_name() {
        return song_collection_name;
    }

    public void setSong_collection_name(String song_collection_name) {
        this.song_collection_name = song_collection_name;
    }

    public String getSong_collection_price() {
        return song_collection_price;
    }

    public void setSong_collection_price(String song_collection_price) {
        this.song_collection_price = song_collection_price;
    }

    public Song() {


    }

    public String getSong_title() {
        return song_title;
    }

    public void setSong_title(String song_title) {
        this.song_title = song_title;
    }

    public String getSong_img() {
        return song_img;
    }

    public void setSong_img(String song_img) {
        this.song_img = song_img;
    }

    public String getSong_album_name() {
        return song_album_name;
    }

    public void setSong_album_name(String song_album_name) {
        this.song_album_name = song_album_name;
    }

    public String getSong_cost() {
        return song_cost;
    }

    public void setSong_cost(String song_cost) {
        this.song_cost = song_cost;
    }

    // Parcelling
    public Song(Parcel in) {

        this.song_title = in.readString();
        this.song_img = in.readString();
        this.song_album_name = in.readString();
        this.song_cost = in.readString();
        this.song_collection_name = in.readString();
        this.song_collection_price = in.readString();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.song_title);
        parcel.writeString(this.song_img);
        parcel.writeString(this.song_album_name);
        parcel.writeString(this.song_cost);
        parcel.writeString(this.song_collection_name);
        parcel.writeString(this.song_collection_price);
    }

    // Method to recreate a Question from a Parcel
    public static Creator<Song> CREATOR = new Creator<Song>() {

        @Override
        public Song createFromParcel(Parcel source) {
            return new Song(source);
        }

        @Override
        public Song[] newArray(int size) {
            return new Song[size];
        }

    };

}
