package com.example.rajkamalwhm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class LoginPage extends AppCompatActivity {

    String getuserid,getpassword;
    EditText userid,password;
    ProgressBar progressBar;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        getSupportActionBar().hide();

        userid = findViewById(R.id.userid);
        password = findViewById(R.id.password);
        progressBar = findViewById(R.id.progressbar);
        login = findViewById(R.id.login);

        login.setOnClickListener(view -> {

//            if(userid.getText().toString().trim().equals("")){
//                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Please Enter UserId", Snackbar.LENGTH_LONG);
//                snackbar.show();
//            }else if(password.getText().toString().trim().equals("")){
//                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Please Enter Password", Snackbar.LENGTH_LONG);
//                snackbar.show();
//            }else {
//                login.setVisibility(View.INVISIBLE);
//               // checklogin();
               startActivity(new Intent(LoginPage.this,MainActivity.class));
         //   }
        });
    }

}