package com.example.imunku.Presentation.UI;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.imunku.R;

public class Jadwal_Imunisasi extends AppCompatActivity {

    EditText etNama, etJK, etUmur, etJenis, etTanggal;
    TextView tvJadwal1, tvJadwal2;
    Button btnSimpan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwal_imunisasi);

        etNama = findViewById(R.id.etNama);
        etJK = findViewById(R.id.etJK);
        etUmur = findViewById(R.id.etUmur);
        etJenis = findViewById(R.id.etJenis);
        etTanggal = findViewById(R.id.etTanggal);
        btnSimpan = findViewById(R.id.btnSimpan);
        tvJadwal1 = findViewById(R.id.tvJadwal1);
        tvJadwal2 = findViewById(R.id.tvJadwal2);

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nama = etNama.getText().toString();
                String jk = etJK.getText().toString();
                String umur = etUmur.getText().toString();
                String jenis = etJenis.getText().toString();
                String tanggal = etTanggal.getText().toString();

                String hasil1 = "Nama: " + nama + "\nJK: " + jk + "\nUmur: " + umur;
                String hasil2 = "Imunisasi: " + jenis + "\nTanggal: " + tanggal;

                tvJadwal1.setText(hasil1);
                tvJadwal2.setText(hasil2);
            }
        });
    }
}
