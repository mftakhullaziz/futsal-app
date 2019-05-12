package com.example.futsalgo;

public class Upload {
    private String imgName;
    private String imgUrl;
    private String idAdministrator;
    private String deskripsi_lapangan;
    private String nomor_tlp_lapangan;
    private String fasilitas_tersedia;
    private String alamat_lapangan;
    private String nama_bank;
    private String nomor_rekening;
    private String nama_pemilik;

    private String lapangan_1;
    private String lapangan_2;
    private String lapangan_3;

    private String harga_lapangan_1;
    private String harga_lapangan_2;
    private String harga_lapangan_3;

    public Upload() {
    }

    public Upload(String imgName, String imgUrl, String idAdministrator, String deskripsi_lapangan,
                  String nomor_tlp_lapangan, String fasilitas_tersedia, String alamat_lapangan,
                  String lapangan_1, String lapangan_2, String lapangan_3, String harga_lapangan_1,
                  String harga_lapangan_2, String harga_lapangan_3, String nama_bank, String nomor_rekening,
                  String nama_pemilik) {
        if(imgName.trim().equals(""))
        {
            imgName="No name";
        }
        this.imgName = imgName;
        this.imgUrl = imgUrl;
        this.idAdministrator = idAdministrator;
        this.deskripsi_lapangan = deskripsi_lapangan;
        this.nomor_tlp_lapangan = nomor_tlp_lapangan;
        this.fasilitas_tersedia = fasilitas_tersedia;
        this.alamat_lapangan = alamat_lapangan;
        this.nama_pemilik = nama_pemilik;


        this.lapangan_1 = lapangan_1;
        this.lapangan_2 = lapangan_2;
        this.lapangan_3 = lapangan_3;

        this.harga_lapangan_1 = harga_lapangan_1;
        this.harga_lapangan_2 = harga_lapangan_2;
        this.harga_lapangan_3 = harga_lapangan_3;

        this.nama_bank = nama_bank;
        this.nomor_rekening = nomor_rekening;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getIdAdministrator() {
        return idAdministrator;
    }

    public void setIdAdministrator(String idAdministrator) {
        this.idAdministrator = idAdministrator;
    }

    public String getDeskripsi_lapangan() {
        return deskripsi_lapangan;
    }

    public void setDeskripsi_lapangan(String deskripsi_lapangan) {
        this.deskripsi_lapangan = deskripsi_lapangan;
    }

    public String getNomor_tlp_lapangan() {
        return nomor_tlp_lapangan;
    }

    public void setNomor_tlp_lapangan(String nomor_tlp_lapangan) {
        this.nomor_tlp_lapangan = nomor_tlp_lapangan;
    }

    public String getFasilitas_tersedia() {
        return fasilitas_tersedia;
    }

    public void setFasilitas_tersedia(String fasilitas_tersedia) {
        this.fasilitas_tersedia = fasilitas_tersedia;
    }

    public String getAlamat_lapangan() {
        return alamat_lapangan;
    }

    public void setAlamat_lapangan(String alamat_lapangan) {
        this.alamat_lapangan = alamat_lapangan;
    }

    public String getLapangan_1() {
        return lapangan_1;
    }

    public void setLapangan_1(String lapangan_1) {
        this.lapangan_1 = lapangan_1;
    }

    public String getLapangan_2() {
        return lapangan_2;
    }

    public void setLapangan_2(String lapangan_2) {
        this.lapangan_2 = lapangan_2;
    }

    public String getLapangan_3() {
        return lapangan_3;
    }

    public void setLapangan_3(String lapangan_3) {
        this.lapangan_3 = lapangan_3;
    }

    public String getHarga_lapangan_1() {
        return harga_lapangan_1;
    }

    public void setHarga_lapangan_1(String harga_lapangan_1) {
        this.harga_lapangan_1 = harga_lapangan_1;
    }

    public String getHarga_lapangan_2() {
        return harga_lapangan_2;
    }

    public void setHarga_lapangan_2(String harga_lapangan_2) {
        this.harga_lapangan_2 = harga_lapangan_2;
    }

    public String getHarga_lapangan_3() {
        return harga_lapangan_3;
    }

    public void setHarga_lapangan_3(String harga_lapangan_3) {
        this.harga_lapangan_3 = harga_lapangan_3;
    }

    public String getNama_bank() {
        return nama_bank;
    }

    public void setNama_bank(String nama_bank) {
        this.nama_bank = nama_bank;
    }

    public String getNomor_rekening() {
        return nomor_rekening;
    }

    public void setNomor_rekening(String nomor_rekening) {
        this.nomor_rekening = nomor_rekening;
    }

    public String getNama_pemilik() {
        return nama_pemilik;
    }

    public void setNama_pemilik(String nama_pemilik) {
        this.nama_pemilik = nama_pemilik;
    }
}
