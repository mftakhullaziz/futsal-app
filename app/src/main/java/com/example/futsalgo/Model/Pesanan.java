package com.example.futsalgo.Model;

public class Pesanan {

    private  String emailPemesan, idPesanan, invoice, jamMulai,
               jamSelesai, namaPemesan, nameLapanganFuts, nameTempatFuts,
              noTelepon, statusPesanan, tanggalPesan, timestamp, hargaLapanganFuts;

    private String transfer_ke_bank;
    private String transfer_ke_nama_pemilik;
    private String transfer_ke_no_rekening;

    private  double  totalPembayaran;

    public Pesanan() {
    }

    public Pesanan( String emailPemesan, String idPesanan, String invoice,
                    String jamMulai, String jamSelesai, String nameLapanganFuts,
                    String nameTempatFuts, String noTelepon, String statusPesanan,
                    String tanggalPesan, String timestamp, String hargaLapanganFuts,
                    double  totalPembayaran, String transfer_ke_bank, String transfer_ke_nama_pemilik,
                    String transfer_ke_no_rekening) {

        this.emailPemesan = emailPemesan;
        this.idPesanan = idPesanan;
        this.invoice = invoice;
        this.jamMulai = jamMulai;
        this.jamSelesai = jamSelesai;
        this.nameLapanganFuts = nameLapanganFuts;
        this.nameTempatFuts = nameTempatFuts;
        this.noTelepon = noTelepon;
        this.statusPesanan = statusPesanan;
        this.tanggalPesan = tanggalPesan;
        this.timestamp = timestamp;
        this.hargaLapanganFuts = hargaLapanganFuts;
        this.totalPembayaran = totalPembayaran;
        this.transfer_ke_bank = transfer_ke_bank;
        this.transfer_ke_nama_pemilik = transfer_ke_nama_pemilik;
        this.transfer_ke_no_rekening = transfer_ke_no_rekening;
    }

    public String getEmailPemesan() {
        return emailPemesan;
    }

    public void setEmailPemesan(String emailPemesan) {
        this.emailPemesan = emailPemesan;
    }

    public String getIdPesanan() {
        return idPesanan;
    }

    public void setIdPesanan(String idPesanan) {
        this.idPesanan = idPesanan;
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public String getJamMulai() {
        return jamMulai;
    }

    public void setJamMulai(String jamMulai) {
        this.jamMulai = jamMulai;
    }

    public String getJamSelesai() {
        return jamSelesai;
    }

    public void setJamSelesai(String jamSelesai) {
        this.jamSelesai = jamSelesai;
    }

    public String getNamaPemesan() {
        return namaPemesan;
    }

    public void setNamaPemesan(String namaPemesan) {
        this.namaPemesan = namaPemesan;
    }

    public String getNameLapanganFuts() {
        return nameLapanganFuts;
    }

    public void setNameLapanganFuts(String nameLapanganFuts) {
        this.nameLapanganFuts = nameLapanganFuts;
    }

    public String getNameTempatFuts() {
        return nameTempatFuts;
    }

    public void setNameTempatFuts(String nameTempatFuts) {
        this.nameTempatFuts = nameTempatFuts;
    }

    public String getNoTelepon() {
        return noTelepon;
    }

    public void setNoTelepon(String noTelepon) {
        this.noTelepon = noTelepon;
    }

    public String getStatusPesanan() {
        return statusPesanan;
    }

    public void setStatusPesanan(String statusPesanan) {
        this.statusPesanan = statusPesanan;
    }

    public String getTanggalPesan() {
        return tanggalPesan;
    }

    public void setTanggalPesan(String tanggalPesan) {
        this.tanggalPesan = tanggalPesan;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getHargaLapanganFuts() {
        return hargaLapanganFuts;
    }

    public void setHargaLapanganFuts(String hargaLapanganFuts) {
        this.hargaLapanganFuts = hargaLapanganFuts;
    }

    public double getTotalPembayaran() {
        return totalPembayaran;
    }

    public void setTotalPembayaran(double totalPembayaran) {
        this.totalPembayaran = totalPembayaran;
    }

    public String getTransfer_ke_bank() {
        return transfer_ke_bank;
    }

    public void setTransfer_ke_bank(String transfer_ke_bank) {
        this.transfer_ke_bank = transfer_ke_bank;
    }

    public String getTransfer_ke_nama_pemilik() {
        return transfer_ke_nama_pemilik;
    }

    public void setTransfer_ke_nama_pemilik(String transfer_ke_nama_pemilik) {
        this.transfer_ke_nama_pemilik = transfer_ke_nama_pemilik;
    }

    public String getTransfer_ke_no_rekening() {
        return transfer_ke_no_rekening;
    }

    public void setTransfer_ke_no_rekening(String transfer_ke_no_rekening) {
        this.transfer_ke_no_rekening = transfer_ke_no_rekening;
    }
}
