package com.example.rajkamalwhm.grnentry;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rajkamalwhm.Global;
import com.example.rajkamalwhm.MyAPICall;
import com.example.rajkamalwhm.R;
import com.example.rajkamalwhm.grnentry.recyclerdata.GRNAdapter;
import com.example.rajkamalwhm.grnentry.recyclerdata.GRNPojo;
import com.example.rajkamalwhm.roomdatabase.DatabaseClient;
import com.example.rajkamalwhm.roomdatabase.GRNBarcodes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GRNEntryFragment extends Fragment {

    EditText invoiceno,invoicedate,pckslipno,pckslipdate,grnremark;
    TextView showinvoicedate,showpckslipdate;
    Button proceed;

    View view;

    String getinvoiceno,getinvoicedate,getpckslipno,getpckslipdate,getgrnremark;
    private int mYear,mMonth,mDay;

    public GRNEntryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_g_r_n_entry, container, false);

        invoiceno = view.findViewById(R.id.invoiceno);
        invoicedate = view.findViewById(R.id.invoicedate);
        pckslipno = view.findViewById(R.id.pckslipno);
        pckslipdate = view.findViewById(R.id.pckslipdate);
        grnremark = view.findViewById(R.id.grnremark);
        proceed = view.findViewById(R.id.proceed);
        showinvoicedate = view.findViewById(R.id.showinvoicedate);
        showpckslipdate = view.findViewById(R.id.showpckslipdate);

        invoicedate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                invoicedate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                showinvoicedate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                                pckslipno.requestFocus();
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });

        pckslipdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                pckslipdate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                showpckslipdate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                                grnremark.requestFocus();
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(invoiceno.getText().toString().trim().length() == 0) {
                    Global.showsnackbar(getActivity(),"Please Enter Invoice No");
                }else if(invoicedate.getText().toString().trim().length() == 0) {
                    Global.showsnackbar(getActivity(),"Please Select Invoice Date");
                }else if(pckslipno.getText().toString().trim().length() == 0) {
                    Global.showsnackbar(getActivity(),"Please Enter Picking Slip No");
                }else if(pckslipdate.getText().toString().trim().length() == 0) {
                    Global.showsnackbar(getActivity(),"Please Enter Picking Slip Date");
                }else if(grnremark.getText().toString().trim().length() == 0) {
                    Global.showsnackbar(getActivity(),"Please Enter GRN Remark");
                }else {

                    getinvoiceno = invoiceno.getText().toString();
                    getinvoicedate = showinvoicedate.getText().toString();
                    getpckslipno = pckslipno.getText().toString();
                    getpckslipdate = showpckslipdate.getText().toString();
                    getgrnremark = grnremark.getText().toString();

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("d1",getinvoiceno);
                    bundle.putSerializable("d2",getinvoicedate);
                    bundle.putSerializable("d3",getpckslipno);
                    bundle.putSerializable("d4",getpckslipdate);
                    bundle.putSerializable("d5",getgrnremark);
                    Navigation.findNavController(view).navigate(R.id.action_GRNEntryFragment_to_GRNEntrySaveFragment, bundle);
                }

            }
        });

        return view;
    }
}