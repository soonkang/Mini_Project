package com.sp.mini_assignment.QRScanner;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;
import com.sp.mini_assignment.R;

public class QRScannerFragment extends Fragment {

    private TextView scanButton, generateQRButton;
    private ImageView QRCode;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_q_r_scanner, container, false);

        // Initialize views
        scanButton = rootView.findViewById(R.id.scanQRbtn);
        QRCode = rootView.findViewById(R.id.qr_scanner_img);
        generateQRButton = rootView.findViewById(R.id.generateQRbtn);

        // Set click listener for the scan button
        scanButton.setOnClickListener(v -> scanQRCode());

        generateQRButton.setOnClickListener(v -> generateQR());

        return rootView;
    }

    private void scanQRCode() {
        // Start the QR code scanning process
        qrCodeLauncher.launch(new ScanOptions());
    }

    // Register the activity result launcher for QR code scanning
    private final ActivityResultLauncher<ScanOptions> qrCodeLauncher = registerForActivityResult(
            new ScanContract(),
            result -> {
                if (result.getContents() == null) {
                    // QR code scanning was cancelled
                    Toast.makeText(getContext(), "Invalid QR Code", Toast.LENGTH_SHORT).show();
                } else {
                    // QR code scanning was successful, extract information from the QR code
                    String qrCodeContents = result.getContents();
                    if ("Access Allowed".equals(qrCodeContents)) {
                        Toast.makeText(getContext(), "Access Allowed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );

    private void generateQR() {
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            // Encode the content (e.g., car park ID) into a QR code
            BitMatrix bitMatrix = multiFormatWriter.encode("Harold Harry, P2204637", BarcodeFormat.QR_CODE, 350, 350);

            // Create a new Matrix with a transparent background color
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            int[] pixels = new int[width * height];
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    pixels[y * width + x] = bitMatrix.get(x, y) ? 0xFF000000 : 0x00000000; // Black or transparent
                }
            }

            // Create a Bitmap with the transparent background
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);

            // Create a transparent drawable from the Bitmap
            Drawable transparentDrawable = new BitmapDrawable(getResources(), bitmap);

            // Set the transparent drawable as the background of the ImageView
            QRCode.setBackground(transparentDrawable);
        } catch (WriterException e) {
            // Handle encoding errors
            e.printStackTrace();
        }
    }

    // Other methods for handling camera permissions, etc.
}
