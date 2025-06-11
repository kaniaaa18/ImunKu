package com.example.imunku.Presentation.UI.Fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.imunku.R;
import com.example.imunku.Presentation.UI.Activity.PanduanActivity;

public class ProfileFragment extends Fragment {

    private ImageView userIcon;
    private EditText nameInput, emailInput, phoneInput, passwordInput;
    private Button guideButton, logoutButton, saveButton;
    private static final int PICK_IMAGE = 1; // Kode permintaan untuk mengambil gambar

    public ProfileFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        userIcon = view.findViewById(R.id.user_icon);
        nameInput = view.findViewById(R.id.name_input);
        emailInput = view.findViewById(R.id.email_input);
        phoneInput = view.findViewById(R.id.phone_input);
        passwordInput = view.findViewById(R.id.password_input);
        guideButton = view.findViewById(R.id.panduan_aplikasi);
        logoutButton = view.findViewById(R.id.logout_button);
        saveButton = view.findViewById(R.id.save_button);

        // Menambahkan listener untuk mengubah foto profil
        userIcon.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, PICK_IMAGE);
        });

        // Menambahkan listener untuk tombol panduan aplikasi
        guideButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), PanduanActivity.class);
            startActivity(intent);
        });

        // Menambahkan listener untuk tombol logout
        logoutButton.setOnClickListener(v -> showLogoutConfirmationDialog());

        // Menambahkan listener untuk tombol simpan
        saveButton.setOnClickListener(v -> saveProfile());

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            userIcon.setImageURI(selectedImage); // Set gambar yang dipilih ke ImageView
        }
    }

    private void showLogoutConfirmationDialog() {
        new AlertDialog.Builder(getActivity())
                .setTitle("Log Out")
                .setMessage("Apakah Anda yakin untuk log out?")
                .setPositiveButton("Ya", (dialog, which) -> {
                    // Implementasikan fungsionalitas logout
                    Toast.makeText(getActivity(), "Anda telah log out", Toast.LENGTH_SHORT).show();
                    // Redirect ke activity login jika perlu
                })
                .setNegativeButton("Tidak", null)
                .create()
                .show();
    }

    private void saveProfile() {
        String name = nameInput.getText().toString();
        String email = emailInput.getText().toString();
        String phone = phoneInput.getText().toString();
        String password = passwordInput.getText().toString();

        // Implementasikan fungsionalitas menyimpan data profil
        Toast.makeText(getActivity(), "Profil disimpan", Toast.LENGTH_SHORT).show();
    }
}