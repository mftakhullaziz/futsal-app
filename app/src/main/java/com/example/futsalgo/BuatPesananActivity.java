package com.example.futsalgo;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.futsalgo.MenuTempatFutsal.DetailTempatFutsalActivity;
import com.example.futsalgo.Model.Pemesan;
import com.example.futsalgo.Model.Pesanan;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class BuatPesananActivity extends AppCompatActivity {

    private TextView ENama, ENoPhone;
    private TextView ENamaLP, ELP;
    private TextView Tfustsal, lp1,lp2,lp3;
    private String nama, email, password, alamat, telepon, IDPengguna;
    private String emailPengguna;
    private Button btnUpdate, btnCekJadwal, btnPembayaran;
    private FirebaseAuth auth;
    private ProgressDialog mProgress;
    private DatabaseReference dbRef;
    private String invoice;

    private boolean jadwal = false;
    private boolean jam = false;
    private boolean tgl = false;

    private String idPesanan;

    private String idUlpload;

    private TextView namaTempatFutsal;
    private TextView namaLapangan;

    //DatePicker
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private  TextView tanggalPesan;

    //Spinner
    private Spinner spinnerMulai;
    private Spinner spinnerSelesai;
    private Integer[] jamMulai = {6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23};
    private Integer[] jamSelesai = {7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24};

    private Calendar calander;
    private SimpleDateFormat simpledateformat;

    private TextView namaPemesan;
    private TextView nomorPemesan;

    private TextView namaLPA;
    private TextView hargaLPA;
    private TextView tempatLPA;

    private TextView nomorKO;
    private int totalPembayaran;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buat_pesanan);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextAppearance(this, R.style.montserat);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Create Booking");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mProgress = new ProgressDialog(this);

        ENama = (TextView) findViewById(R.id.tvNamaPemesan);
        ENoPhone = (TextView) findViewById(R.id.tvNomorTelepon);
        Tfustsal = (TextView) findViewById(R.id.tvTempatFutsal);

        lp1 = (TextView) findViewById(R.id.tvNamaLapanganUser);
        lp2 = (TextView) findViewById(R.id.tvNamaLapanganUser);
        lp3 = (TextView) findViewById(R.id.tvNamaLapanganUser);

        tanggalPesan = findViewById(R.id.tvTanggalPesan);

        namaPemesan = findViewById(R.id.tvNamaPemesan);
        nomorPemesan = findViewById(R.id.tvNomorTelepon);

        tempatLPA = findViewById(R.id.tvTempatFutsal);
        namaLPA = findViewById(R.id.tvNamaLapanganUser);
        hargaLPA = findViewById(R.id.tvHargaLapanganUser);

 /*       tempatLPA2 = findViewById(R.id.tvTempatFutsal);
        namaLPA2 = findViewById(R.id.tvNamaLapanganUser);
        hargaLPA2 = findViewById(R.id.tvHargaLapanganUser);

        namaLPA3 = findViewById(R.id.tvNamaLapanganUser);
        hargaLPA3 = findViewById(R.id.tvHargaLapanganUser);*/

       /* nomorKO = findViewById(R.id.cekNomorL);*/


        btnCekJadwal = findViewById(R.id.btnCek);
        btnPembayaran = findViewById(R.id.btnPembayaran);


        spinnerMulai = findViewById(R.id.jamMulai);
        spinnerSelesai = findViewById(R.id.jamSelesai);



        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference("pengguna");
        dbRef.keepSynced(true);
        final FirebaseUser user = firebaseAuth.getCurrentUser();
        assert user != null;
        IDPengguna = user.getUid();
        emailPengguna = user.getEmail();


        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        getDataPemesan();
        buatInvoice();
        setSpinner();
        getDataLapangan();


        tanggalPesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });

        btnCekJadwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (cekTanggal()) {
                    cekJam();
                }

                if (tgl == true && jam == true) {
                    cekJadwal();
                }
            }
        });

        btnPembayaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (tgl == true && jam == true && jadwal == true) {

                    buatPesanan();
                    String namaInvoice = getIntent().getStringExtra("invoice");
                    String namaTempatF = getIntent().getStringExtra("imgName");
                    String namaLapanganF = getIntent().getStringExtra("lapangan_1");
                    String namaBank = getIntent().getStringExtra("nama_bank");
                    String noBanko = getIntent().getStringExtra("nomor_rekening");
                    String namap = getIntent().getStringExtra("nama_pemilik");
                    String harg = getIntent().getStringExtra("harga_lapangan_1");
                    String tglpes = getIntent().getStringExtra("tanggalPesan");
                    String jampes1 = getIntent().getStringExtra("jamMulai");
                    String jampes2 = getIntent().getStringExtra("jamSelesai");
                    Intent intent = new Intent(BuatPesananActivity.this, DetailPesananActivity.class);
                    intent.putExtra("imgName", namaTempatF);
                    intent.putExtra("lapangan_1", namaLapanganF);
                    intent.putExtra("nama_bank", namaBank);
                    intent.putExtra("nomor_rekening", noBanko);
                    intent.putExtra("nama_pemilik", namap);
                    intent.putExtra("harga_lapangan_1", harg);

                    startActivity(intent);


                }else {
                    Toast.makeText(BuatPesananActivity.this, "Periksa Kembali Pesanan Anda",
                            Toast.LENGTH_LONG).show();
                }




            }
        });

    }

    private void buatPesanan() {
        /*String idPetugas = getIntent().getStringExtra("idPetugas");
        String idLapangan = getIntent().getStringExtra("idLapangan");
        String emailTempatFutsal = getIntent().getStringExtra("email");*/

        String a = getIntent().getStringExtra("nama_bank");
        String b = getIntent().getStringExtra("nomor_rekening");
        String c = getIntent().getStringExtra("nama_pemilik");

        String pemesan = namaPemesan.getText().toString();
        String noTelpon = nomorPemesan.getText().toString();

        /*String tempatFutsal = namaTempatFutsal.getText().toString();
        String lapangan = namaLapangan.getText().toString();*/

        String tglPesan = tanggalPesan.getText().toString();
        String jamMulai = spinnerMulai.getSelectedItem().toString();
        String jamSelesai = spinnerSelesai.getSelectedItem().toString();

        String AddressFuts = tempatLPA.getText().toString();
        String NameFuts = namaLPA.getText().toString();
        String Money = hargaLPA.getText().toString();

        String statusPesanan = "Belum Bayar";

        idPesanan = dbRef.push().getKey();

        calander = Calendar.getInstance();
        simpledateformat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String timestamp = simpledateformat.format(calander.getTime());

        dbRef = FirebaseDatabase.getInstance().getReference().child("pesanan").child(idPesanan);
        dbRef.child("idPesanan").setValue(idPesanan);
        dbRef.child("idPengguna").setValue(IDPengguna);
        dbRef.child("nameTempatFuts").setValue(AddressFuts);
        dbRef.child("nameLapanganFuts").setValue(NameFuts);
        dbRef.child("hargaLapanganFuts").setValue(Money);
        dbRef.child("statusPesanan").setValue(statusPesanan);
        dbRef.child("namaPemesan").setValue(pemesan);

        dbRef.child("emailPemesan").setValue(emailPengguna);
        dbRef.child("noTelepon").setValue(noTelpon);

        /*dbRef.child("namaTempatFutsal").setValue(tempatFutsal);
        dbRef.child("namaLapangan").setValue(lapangan);*/

        dbRef.child("tanggalPesan").setValue(tglPesan);
        dbRef.child("jamMulai").setValue(jamMulai);
        dbRef.child("jamSelesai").setValue(jamSelesai);

        dbRef.child("totalPembayaran").setValue(totalPembayaran());
        dbRef.child("invoice").setValue(invoice);
        dbRef.child("timestamp").setValue(timestamp);

        dbRef.child("transfer_ke_bank").setValue(a);
        dbRef.child("transfer_ke_no_rekening").setValue(b);
        dbRef.child("transfer_ke_nama_pemilik").setValue(c);

        /*dbRef.child("emailTempatFutsal").setValue(emailTempatFutsal);*/
    }

    private int totalPembayaran() {

        /*Integer hargaSewa1 = getIntent().getIntExtra("harga_lapangan_1", 0);*/
        String hargaT = getIntent().getExtras().getString("harga_lapangan_1");
        int hargaSewa = Integer.parseInt(String.valueOf(hargaT));

        int jamMulai = Integer.parseInt(String.valueOf(spinnerMulai.getSelectedItem()));
        int jamSelesai = Integer.parseInt(String.valueOf(spinnerSelesai.getSelectedItem()));
        int durasi = jamSelesai - jamMulai;
        int total = durasi * hargaSewa;

        totalPembayaran = total;

        return totalPembayaran;
    }


    private boolean cekJadwal() {
        mProgress.setMessage("Mengecek Jadwal Tersedia");
   /*     final String tempatFutsal = namaTempatFutsal.getText().toString();
        final String lapangan = namaLapangan.getText().toString();*/
        final String tglPesan = tanggalPesan.getText().toString();
        final String jamMulai = spinnerMulai.getSelectedItem().toString();
        final String jamSelesai = spinnerSelesai.getSelectedItem().toString();
      /*  (data.child("namaTempatFutsal").getValue().equals(tempatFutsal)) &&
                            (data.child("namaLapangan").getValue().equals(lapangan)) &&
*/
        mProgress.show();
      /*  btnCekJadwal.setText("Jadwal Tersedia");
        mProgress.dismiss();*/
        dbRef = FirebaseDatabase.getInstance().getReference();
        dbRef.child("pesanan").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    if ((data.child("tanggalPesan").getValue().equals(tglPesan)) &&
                            (data.child("jamMulai").getValue().equals(jamMulai)) &&
                            (data.child("jamSelesai").getValue().equals(jamSelesai))) {
                        btnCekJadwal.setText("Sudah Ada yang Memesan");
                        mProgress.dismiss();
                        jadwal = false;
                    } else {
                        btnCekJadwal.setText("Jadwal Tersedia");
                        mProgress.dismiss();
                        jadwal = true;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return jadwal;
    }

    private boolean cekTanggal() {
        String tanggal = tanggalPesan.getText().toString();

        if (TextUtils.isEmpty(tanggal)) {
            btnCekJadwal.setText("Silahkan pilih tanggal pesan");
            tgl = false;
        } else {
            tgl = true;
        }
        return tgl;
    }

    private boolean cekJam() {
        Integer jamMulai = (Integer) spinnerMulai.getSelectedItem();
        Integer jamSelesai = (Integer) spinnerSelesai.getSelectedItem();

        if (jamSelesai <= jamMulai) {
            btnCekJadwal.setText("Periksa kembali jam pesan");
            jam = false;
        } else {
            jam = true;
        }
        return jam;
    }

    public void getDataPemesan() {
        dbRef.child(IDPengguna).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Pemesan dataPemesan = dataSnapshot.getValue(Pemesan.class);
                if (dataPemesan != null) {
                    ENama.setText(dataPemesan.getNama());
                    ENoPhone.setText(dataPemesan.getTelepon());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getDataLapangan() {

        String namaS = getIntent().getExtras().getString("imgName");
        tempatLPA.setText(namaS);

        String namaT = getIntent().getExtras().getString("lapangan_1");
        namaLPA.setText(namaT);

        String hargaT = getIntent().getExtras().getString("harga_lapangan_1");
        hargaLPA.setText(hargaT);

       /* String hargaTNo = getIntent().getExtras().getString("nomor_rekening");
        nomorKO.setText("no. "+hargaTNo);*/

        /*String namaS2 = getIntent().getExtras().getString("imgName");
        tempatLPA2.setText(namaS2);

        String namaT2 = getIntent().getExtras().getString("lapangan_2");
        namaLPA2.setText(namaT2);

        String hargaT2 = getIntent().getExtras().getString("harga_lapangan_2");
        hargaLPA2.setText(hargaT2);*/

      /*  String namaT2 = getIntent().getExtras().getString("lapangan_2");
        namaLPA2.setText(namaT2);

        String hargaT2 = getIntent().getExtras().getString("harga_lapangan_2");
        hargaLPA2.setText(hargaT2);

        String namaT3 = getIntent().getExtras().getString("lapangan_3");
        namaLPA3.setText(namaT3);

        String hargaT3 = getIntent().getExtras().getString("harga_lapangan_3");
        hargaLPA3.setText(hargaT3);*/


       /* dbRef = FirebaseDatabase.getInstance().getReference();
        idUlpload = dbRef.child("uploads").getKey();


        dbRef.child(idUlpload).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Upload dataLapangan = dataSnapshot.getValue(Upload.class);
                if (dataLapangan != null) {
                    tempatLPA.setText(dataLapangan.getImgName());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/
    }

    /*public void getDataLapangan2() {

        String namaS2 = getIntent().getExtras().getString("imgName");
        tempatLPA.setText(namaS2);

        String namaT2 = getIntent().getExtras().getString("lapangan_2");
        namaLPA2.setText(namaT2);

        String hargaT2 = getIntent().getExtras().getString("harga_lapangan_2");
        hargaLPA2.setText(hargaT2);
    }*/



    private void showDateDialog() {
        //Calendar untuk mendapatkan tanggal sekarang
        Calendar newCalendar = Calendar.getInstance();

        //Initiate DatePicker dialog
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                //Method ini dipanggil saat kita selesai memilih tanggal di DatePicker
                //Set Calendar untuk menampung tanggal yang dipilih
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                tanggalPesan.setText(dateFormatter.format(newDate.getTime()));
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH),
                newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void setSpinner() {
        // inisialiasi Array Adapter dengan memasukkan string array di atas
        final ArrayAdapter<Integer> adapterMulai = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, jamMulai);

        // inisialiasi Array Adapter dengan memasukkan string array di atas
        final ArrayAdapter<Integer> adapterSelesai = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, jamSelesai);

        // mengeset Array Adapter tersebut ke Spinner
        spinnerMulai.setAdapter(adapterMulai);
        spinnerSelesai.setAdapter(adapterSelesai);

        spinnerMulai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerSelesai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void buatInvoice() {
        String Date;
        calander = Calendar.getInstance();
        simpledateformat = new SimpleDateFormat("ddMMyyyyHHmm");
        Date = simpledateformat.format(calander.getTime());
        invoice = ("LPGFUTSAL" + Date);
    }




            @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
