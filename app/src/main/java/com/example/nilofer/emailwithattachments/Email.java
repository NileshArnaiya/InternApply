package com.example.nilofer.emailwithattachments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URISyntaxException;

public class Email extends AppCompatActivity {

    private static final int FILE_SELECT_CODE = 0;
    private final String TAG = "LOL";
    Button button;
    Uri uri;
    Mail m ,mm;
    String path, name, email, college, phone, skills;
    EditText phone_text, institute_text, skills_text;
    TextView name_text, email_text;


    @SuppressLint("NewApi")

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);
        phone_text = findViewById(R.id.phoneNumber);
        institute_text = findViewById(R.id.institute);
        skills_text = findViewById(R.id.skillsText);
        name_text = findViewById(R.id.user_name);
        email_text = findViewById(R.id.email_address);

        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Log.d(TAG, path);

                getUserData();
                if (phone =="" || college=="" || skills=="") {
                    Toast.makeText(Email.this, "Please enter all details first", Toast.LENGTH_SHORT).show();

                }
                else {
                    if (isValidMail(email) && isValidMobile(phone)) {


                        int permissionCheck = ContextCompat.checkSelfPermission(Email.this,
                                Manifest.permission.READ_EXTERNAL_STORAGE);
                        if (permissionCheck != 0) {
                            ActivityCompat.requestPermissions(Email.this,
                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                    1);
                            Toast.makeText(Email.this, "Email sent", Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(Email.this, Successful.class);
//                            startActivity(intent);

                        } else {
                            new Connection().execute("");
                            Intent intent = new Intent(Email.this, Successful.class);
                            startActivity(intent);
                        }
                        if (ContextCompat.checkSelfPermission(Email.this,
                                Manifest.permission.READ_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED) {

                            // Should we show an explanation?
                            if (ActivityCompat.shouldShowRequestPermissionRationale(Email.this,
                                    Manifest.permission.READ_EXTERNAL_STORAGE)) {

                                // Show an explanation to the user *asynchronously* -- don't block
                                // this thread waiting for the user's response! After the user
                                // sees the explanation, try again to request the permission.

                            } else {

                                // No explanation needed, we can request the permission.

                                ActivityCompat.requestPermissions(Email.this,
                                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                        1);


                                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                                // app-defined int constant. The callback method gets the
                                // result of the request.
                            }
                        }
                    } else {
                        if (!isValidMail(email)) {
                            Toast.makeText(Email.this, "Pleease enter valid mail address", Toast.LENGTH_SHORT).show();

                        }
                        if (!isValidMobile(phone)) {
                            Toast.makeText(Email.this, "Pleease enter valid phone number", Toast.LENGTH_SHORT).show();

                        }
                    }
                }
            }


        });
    }

    private boolean isValidMail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidMobile(String phone) {
        return android.util.Patterns.PHONE.matcher(phone).matches();
    }

    private void getUserData() {

        name = name_text.getText().toString();
        email = email_text.getText().toString();
        college = institute_text.getText().toString();
        phone = phone_text.getText().toString();
        skills = skills_text.getText().toString();


    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    new Connection().execute("");
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {
                    Toast.makeText(this, "fuck off", Toast.LENGTH_SHORT).show();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(
                    Intent.createChooser(intent, "Select a File to Upload"),
                    FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(this, "Please install a File Manager.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void upload_cv(View view) {
        showFileChooser();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case FILE_SELECT_CODE:
                if (resultCode == RESULT_OK) {
                    // Get the Uri of the selected file
                    uri = data.getData();
                    //Log.d(TAG, "File Uri: " + uri.toString());
                    // Get the path
                    path = null;
                    try {
                        path = FileUtils.getPath(this, uri);
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(this, "Your File is selected", Toast.LENGTH_SHORT).show();
                    //Log.d(TAG, "File Path: " + path);
                    // Get the file instance
                    // File file = new File(path);
                    // Initiate the upload
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @SuppressLint("StaticFieldLeak")
    private class Connection extends AsyncTask {


        @Override
        protected Object doInBackground(Object[] objects) {
            //insert your gmail id and password
            m = new Mail("test@gmail.com", "pass");
            //insert where to send emails to
            String[] toArr = {"hr@shortfundly.com", "selvam@shortfundly.com"};
            m.setTo(toArr);
            m.setFrom("hr@shortfundly.com");
            m.setSubject("Internship Application");
            //body of the e-mail
            m.setBody(name + "\n Phone: "+phone +"\n "+ email +"\n "+ college + "\n "+skills);
           //new email respoond

            try {
                String filepath = uri.toString();
                m.addAttachment(path);
                Log.d(TAG, filepath);

                if (m.send()) {
                    Log.d(TAG,"sending response email");
                    //add email and password
                    mm = new Mail("test@gmail.com", "pass");
                    //insert where to send emails to
                    String[] respondArray = {email};
                    mm.setTo(respondArray);
                    mm.setFrom("hr@shortfundly.com");
                    mm.setSubject("We've received your application");
                    //body of the e-mail
                    mm.setBody("We've received your application! \n Hi "+name+",Thank you for applying to the ShortFundly Internship program. Your application " +
                            "was successfully received!");
                    if (mm.send()){
                        Log.d(TAG,"sent bro");
                    }
                } else {
                    Toast.makeText(Email.this, "Email was not sent.", Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                //Toast.makeText(MailApp.this, "There was a problem sending the email.", Toast.LENGTH_LONG).show();
                Log.e("MailApp", "Could not send email", e);
            }
            return null;
        }
    }
}
