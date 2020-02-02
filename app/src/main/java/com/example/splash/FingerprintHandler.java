package com.example.splash;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.annotation.TargetApi;
import android.os.CancellationSignal;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

@TargetApi(Build.VERSION_CODES.M)
public class FingerprintHandler extends FingerprintManager.AuthenticationCallback {

    private Intent intent;
    private Context context;
    private FingerPrint fingerPrint = new FingerPrint();

    public FingerprintHandler(Context context) {
        this.context = context;
    }

    private void startActivity(Intent intent) {

    }

    public void startAuth(FingerprintManager fingerprintManager, FingerprintManager.CryptoObject cryptoObject) {
        CancellationSignal cancellationSignal = new CancellationSignal();
        fingerprintManager.authenticate(cryptoObject, cancellationSignal, 0, this, null);

    }

    //if there is authentication error, inform user
    @Override
    public void onAuthenticationError(int errorcode, CharSequence errString) {

        this.update("There was an Auth Error. "+ errString, false);
    }

    @Override
    public void onAuthenticationFailed() {
        this.update("Auth Failed", false);
    }

    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
        this.update("Error: " + helpString, false);
    }

    //If authentication is successful, inform the user
    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        this.update("Cheers, Come on in!", true);

    }

    private void update(String s, boolean b) {
        TextView paraLabel = (TextView) ((Activity)context).findViewById(R.id.paraLabel);
        ImageView imageView = (ImageView) ((Activity)context).findViewById(R.id.fingerprintImage);


        paraLabel.setText(s);
        if(b == false) {
            paraLabel.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));

        } else {
            //If fingerprint is successful, expose beer image
            paraLabel.setTextColor(ContextCompat.getColor(context,R.color.colorGreen));
            imageView.setImageResource(R.drawable.beer);
            context.startActivity(new Intent(context, PubInformation.class));
            ((AppCompatActivity) context).finish();
        }
    }
}

