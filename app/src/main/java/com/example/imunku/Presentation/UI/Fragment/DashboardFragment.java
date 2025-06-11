package com.example.imunku.Presentation.UI.Fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.imunku.R;

public class DashboardFragment extends Fragment {

    public DashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        // Set up the header
        TextView userName = view.findViewById(R.id.user_name);
        userName.setText("Halo, Ibu Ayu!"); // Ganti dengan nama pengguna dinamis

        // Infografis
        TextView infographics = view.findViewById(R.id.infographics);
        infographics.setText("Infografis tentang\npentingnya imunisasi");

        return view;
    }
}