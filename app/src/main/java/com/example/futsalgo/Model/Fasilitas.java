package com.example.futsalgo.Model;

import java.io.Serializable;

public class Fasilitas implements Serializable {

    String idFasilitas,idAdmin, fasilitas;

    public Fasilitas() {
    }

    public Fasilitas(String idFasilitas, String idAdmin, String fasilitas) {
        this.idFasilitas = idFasilitas;
        this.idAdmin = idAdmin;
        this.fasilitas = fasilitas;
    }

    public String getIdFasilitas() {
        return idFasilitas;
    }

    public void setIdFasilitas(String idFasilitas) {
        this.idFasilitas = idFasilitas;
    }

    public String getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(String idAdmin) {
        this.idAdmin = idAdmin;
    }

    public String getFasilitas() {
        return fasilitas;
    }

    public void setFasilitas(String fasilitas) {
        this.fasilitas = fasilitas;
    }
}
