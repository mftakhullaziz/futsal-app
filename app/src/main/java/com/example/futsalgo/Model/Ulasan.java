package com.example.futsalgo.Model;

import java.io.Serializable;

public class Ulasan implements Serializable {

    String idUlasan, idPesanan, idPemesan, namaPemesan, nomorTelepon, ulasan, rating, timestamp;

    public Ulasan() {
    }

    public Ulasan(String idUlasan, String idPesanan, String idPemesan, String namaPemesan, String nomorTelepon, String ulasan, String rating, String timestamp) {
        this.idUlasan = idUlasan;
        this.idPesanan = idPesanan;
        this.idPemesan = idPemesan;
        this.namaPemesan = namaPemesan;
        this.nomorTelepon = nomorTelepon;
        this.ulasan = ulasan;
        this.rating = rating;
        this.timestamp = timestamp;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getIdUlasan() {
        return idUlasan;
    }

    public void setIdUlasan(String idUlasan) {
        this.idUlasan = idUlasan;
    }

    public String getIdPesanan() {
        return idPesanan;
    }

    public void setIdPesanan(String idPesanan) {
        this.idPesanan = idPesanan;
    }

    public String getIdPemesan() {
        return idPemesan;
    }

    public void setIdPemesan(String idPemesan) {
        this.idPemesan = idPemesan;
    }

    public String getNamaPemesan() {
        return namaPemesan;
    }

    public void setNamaPemesan(String namaPemesan) {
        this.namaPemesan = namaPemesan;
    }

    public String getNomorTelepon() {
        return nomorTelepon;
    }

    public void setNomorTelepon(String nomorTelepon) {
        this.nomorTelepon = nomorTelepon;
    }

    public String getUlasan() {
        return ulasan;
    }

    public void setUlasan(String ulasan) {
        this.ulasan = ulasan;
    }
}
