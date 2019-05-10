package com.example.futsalgo.Model;

import java.io.Serializable;

public class Lapangan implements Serializable {
    String idLapangan, idAdministrator, namaLapangan, kategori;
    double hargaSewa;

    public Lapangan() {
    }

    public Lapangan(String idLapangan, String idAdministrator, String namaLapangan, String kategori, double hargaSewa) {
        this.idLapangan = idLapangan;
        this.idAdministrator = idAdministrator;
        this.namaLapangan = namaLapangan;
        this.kategori = kategori;
        this.hargaSewa = hargaSewa;
    }

    public String getIdLapangan() {
        return idLapangan;
    }

    public void setIdLapangan(String idLapangan) {
        this.idLapangan = idLapangan;
    }

    public String getIdAdministrator() {
        return idAdministrator;
    }

    public void setIdAdministrator(String idAdministrator) {
        this.idAdministrator = idAdministrator;
    }

    public String getNamaLapangan() {
        return namaLapangan;
    }

    public void setNamaLapangan(String namaLapangan) {
        this.namaLapangan = namaLapangan;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public double getHargaSewa() {
        return hargaSewa;
    }

    public void setHargaSewa(double hargaSewa) {
        this.hargaSewa = hargaSewa;
    }
}
