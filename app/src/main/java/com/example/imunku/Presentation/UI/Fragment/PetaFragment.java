package com.example.imunku.Presentation.UI.Fragment;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.imunku.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PetaFragment extends Fragment {

    private GoogleMap mMap;
    private MapView mapView;
    private FusedLocationProviderClient fusedLocationClient;

    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    enableMyLocation();
                }
            });

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_peta, container, false);

        requestPermissions();

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());

        mapView = view.findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(googleMap -> {
            mMap = googleMap;
            mMap.getUiSettings().setZoomControlsEnabled(true);
            mMap.getUiSettings().setIndoorLevelPickerEnabled(true);
            mMap.getUiSettings().setCompassEnabled(true);
            mMap.getUiSettings().setMapToolbarEnabled(true);
            getMyLocation();
        });

        return view;
    }

    private void requestPermissions() {
        requestPermissions(new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        }, 1);
    }

    private void getMyLocation() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            enableMyLocation();
        } else {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
        }
    }

    @SuppressLint("MissingPermission")
    private void enableMyLocation() {
        if (mMap != null && ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            tampilkanPuskesmasTerdekat();
        }
    }

    private static class Puskesmas {
        String nama;
        String alamat;
        double lat;
        double lng;
        double jarak;

        Puskesmas(String nama, String alamat, double lat, double lng) {
            this.nama = nama;
            this.alamat = alamat;
            this.lat = lat;
            this.lng = lng;
        }
    }

    private List<Puskesmas> getDaftarPuskesmas() {
        List<Puskesmas> list = new ArrayList<>();
        list.add(new Puskesmas("Puskesmas Alalak Selatan", "Jl. HKSN Komp. Dasamaya 2", -3.30000, 114.59000));
        list.add(new Puskesmas("Puskesmas Alalak Tengah", "Jl. HKSN Komp. AMD Permai", -3.279683, 114.575872));
        list.add(new Puskesmas("Puskesmas Kayu Tangi", "Jl. Kayu Tangi", -3.485556, 114.999417));
        // Tambahkan puskesmas lainnya sesuai kebutuhan
        return list;
    }

    private double hitungJarak(double lat1, double lon1, double lat2, double lon2) {
        double earthRadius = 6371; // km
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return earthRadius * c;
    }

    @SuppressLint("MissingPermission")
    private void tampilkanPuskesmasTerdekat() {
        fusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
            if (location != null) {
                double userLat = location.getLatitude();
                double userLng = location.getLongitude();

                List<Puskesmas> puskesmasList = getDaftarPuskesmas();
                for (Puskesmas p : puskesmasList) {
                    p.jarak = hitungJarak(userLat, userLng, p.lat, p.lng);
                }

                Collections.sort(puskesmasList, Comparator.comparingDouble(p -> p.jarak));

                List<Puskesmas> terdekat = puskesmasList.subList(0, Math.min(5, puskesmasList.size()));
                for (Puskesmas p : terdekat) {
                    mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(p.lat, p.lng))
                            .title(p.nama)
                            .snippet("Jarak: " + String.format("%.2f", p.jarak) + " km"));
                }
            }
        });
    }

    // Lifecycle untuk MapView
    @Override
    public void onResume() {
        super.onResume();
        if (mapView != null) mapView.onResume();
    }

    @Override
    public void onPause() {
        if (mapView != null) mapView.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        if (mapView != null) mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (mapView != null) mapView.onLowMemory();
    }
}