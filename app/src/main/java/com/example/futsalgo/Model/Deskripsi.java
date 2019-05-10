package com.example.futsalgo.Model;

public class Deskripsi {

    private String deskripsi, idAdministrator, noTelepon;

    public Deskripsi() {
    }

    public Deskripsi(String deskripsi, String idAdministrator, String noTelepon) {
        this.deskripsi = deskripsi;
        this.idAdministrator = idAdministrator;
        this.noTelepon = noTelepon;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getIdAdministrator() {
        return idAdministrator;
    }

    public void setIdAdministrator(String idAdministrator) {
        this.idAdministrator = idAdministrator;
    }

    public String getNoTelepon() {
        return noTelepon;
    }

    public void setNoTelepon(String noTelepon) {
        this.noTelepon = noTelepon;
    }
}
