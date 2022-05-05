package com.example.rajkamalwhm;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import dmax.dialog.SpotsDialog;

public class Global {

    public static String BASE_URL="http://192.168.0.175/RajkamalWHM/WebService1.asmx/";

    public static void showtoast(Context context,String message){

        Toast.makeText(context, message, Toast.LENGTH_LONG).show();

    }

    public static void showsnackbar(Activity activity, String message){

        Snackbar snackbar = Snackbar.make(activity.findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG);
        snackbar.show();

    }

    public static SpotsDialog showdailog(Context context){

        SpotsDialog progressDialog;
        progressDialog = new SpotsDialog(context, R.style.Custom);
        progressDialog.show();
        return progressDialog;

    }

}