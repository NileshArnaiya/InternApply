package com.example.nilofer.emailwithattachments;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.shashank.sony.fancydialoglib.Animation;
import com.shashank.sony.fancydialoglib.FancyAlertDialog;
import com.shashank.sony.fancydialoglib.FancyAlertDialogListener;
import com.shashank.sony.fancydialoglib.Icon;

public class Successful extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_successful);

        new FancyAlertDialog.Builder(this)
                .setTitle("We've Received Your application")
                .setBackgroundColor(Color.parseColor("#303F9F"))  //Don't pass R.color.colorvalue
                .setMessage("If we like your application we will send you an email")
                .setPositiveBtnBackground(Color.parseColor("#FF4081"))  //Don't pass R.color.colorvalue
                .setPositiveBtnText("Go Back")
                .setNegativeBtnText("Rate Our App")
                .setAnimation(Animation.POP)
                .isCancellable(true)
                .setIcon(R.drawable.checked, Icon.Visible)
                .OnPositiveClicked(new FancyAlertDialogListener() {
                    @Override
                    public void OnClick() {
                        //Toast.makeText(getApplicationContext(),"Rate",Toast.LENGTH_SHORT).show();
                        //go somewhere next screen or home screen
                    }
                })
                .OnNegativeClicked(new FancyAlertDialogListener() {
                    @Override
                    public void OnClick() {
                        //Toast.makeText(getApplicationContext(),"Cancel",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.shortfundly"));
                        startActivity(intent);

                    }
                })
                .build();
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
