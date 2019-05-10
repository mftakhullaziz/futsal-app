package com.example.futsalgo.Model;

public class AlbumFoto {

    public String imageName;

    public String imageURL;

    public AlbumFoto() {

    }

    public AlbumFoto(String name, String url) {

        this.imageName = name;
        this.imageURL = url;
    }

    public String getImageName() {
        return imageName;
    }

    public String getImageURL() {
        return imageURL;
    }
}
