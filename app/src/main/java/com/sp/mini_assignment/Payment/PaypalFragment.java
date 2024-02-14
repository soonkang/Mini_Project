package com.sp.mini_assignment.Payment;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.sp.mini_assignment.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;

public class PaypalFragment extends Fragment {

    EditText editAmount;
    Button btnPayment;
    String clientID = "Adr3qNBEMGlRyK7E53e6uProSaqZETqFkMSQksPa6WvE3uL2DGUDB1Q2JXfKz1U4Ds0ITuoZR6VCQUdI";
    int PAYPAL_REQUEST_CODE = 123;
    public static PayPalConfiguration configuration;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment__paypal, container, false);

        btnPayment = view.findViewById(R.id.btnPayment);
        configuration = new PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
                .clientId(clientID);

        // Create notification channel
        createNotificationChannel();

        btnPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPayment();

                // Display notification
                NotificationCompat.Builder mbuilder = new NotificationCompat.Builder(requireContext(), "payment_channel")
                        .setSmallIcon(R.drawable.baseline_notifications_active_24)
                        .setContentTitle("Notification")
                        .setContentText("Payment Processing");

                NotificationManager notificationManager = (NotificationManager)
                        requireActivity().getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(0, mbuilder.build());


            }
        });

        return view;
    }

    private void getPayment() {
        String amounts = "10";
        PayPalPayment payment = new PayPalPayment(new BigDecimal(amounts), "SGD", "Total Amount",
                PayPalPayment.PAYMENT_INTENT_SALE);

        Intent intent = new Intent(requireContext(), PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, configuration);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
        startActivityForResult(intent, PAYPAL_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PAYPAL_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                PaymentConfirmation paymentConfirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (paymentConfirmation != null) {
                    try {
                        String paymentDetails = paymentConfirmation.toJSONObject().toString();
                        JSONObject object = new JSONObject(paymentDetails);
                        // Handle the payment details as needed
                    } catch (JSONException e) {
                        Toast.makeText(requireContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(requireContext(), "Payment canceled", Toast.LENGTH_SHORT).show();
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                Toast.makeText(requireContext(), "Invalid Payment", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent(requireContext(), PayPalService.class);
        requireContext().stopService(intent);
    }

    // Create a notification channel
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Payment Channel";
            String description = "Channel for payment notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("payment_channel", name, importance);
            channel.setDescription(description);

            // Register the channel with the system; you can't change the importance or other notification behaviors after this
            NotificationManager notificationManager = requireActivity().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}