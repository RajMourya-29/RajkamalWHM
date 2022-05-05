package com.example.rajkamalwhm.grnentry;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.rajkamalwhm.Global;
import com.example.rajkamalwhm.MyAPICall;
import com.example.rajkamalwhm.R;
import com.example.rajkamalwhm.grnentry.recyclerdata.GRNAdapter;
import com.example.rajkamalwhm.grnentry.recyclerdata.GRNPojo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GRNEntrySaveFragment extends Fragment {

    View view;
    Spinner pono,itemlist;
    EditText requiredqty,scancode,serialno,lotno,qty,desc;
    List<PoNo> getpono;
    List<Itemcode> getitemcode;
    Retrofit retrofit;
    String getponostring,getitemcodestring,getsrno,getlotno,getdesc,getreqqty;
    SpotsDialog spotsDialog;
    List itemcode;

    LinearLayout linear;
    long tstamp;
    Button save;

    List<GrNo> grno;
    List serialnolist;

    List<LastGrnoDetail> lastgrnolist;

    String getinvoiceno,getinvoicedate,getpckslipno,getpckslipdate,getgrnremark;
    ExecutorService executor = Executors.newSingleThreadExecutor();
    Handler handler = new Handler(Looper.getMainLooper());

    String getgrnid,getgrnno,getitemname,getitemdesc;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_g_r_n_entry_save, container, false);

        pono = view.findViewById(R.id.pono);
        itemlist = view.findViewById(R.id.itemlist);
        requiredqty = view.findViewById(R.id.requiredqty);
        scancode = view.findViewById(R.id.scancode);
        serialno = view.findViewById(R.id.serialno);
        lotno = view.findViewById(R.id.lotno);
        qty = view.findViewById(R.id.qty);
        linear = view.findViewById(R.id.linear);
        save = view.findViewById(R.id.save);
        desc = view.findViewById(R.id.desc);

        Bundle bundle = getArguments();
        getinvoiceno = String.valueOf(bundle.getSerializable("d1"));
        getinvoicedate = String.valueOf(bundle.getSerializable("d2"));
        getpckslipno = String.valueOf(bundle.getSerializable("d3"));
        getpckslipdate = String.valueOf(bundle.getSerializable("d4"));
        getgrnremark = String.valueOf(bundle.getSerializable("d5"));

        lastgrnolist = new ArrayList<>();
        grno = new ArrayList<>();

        retrofit = new Retrofit.Builder()
                .baseUrl(Global.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        spotsDialog = Global.showdailog(getContext());
        executor.execute(() -> {
            loadpono();
            loadallgrno();
          //  handler.post(() ->  spotsDialog.dismiss());
        });


        scancode.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View view, int keyCode, KeyEvent keyevent) {

                if ((keyevent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {

                    if(scancode.getText().toString().trim().length() == 0) {
                        Global.showsnackbar(getActivity(),"Please Scan A Barcode");
                    }else {
                        if(itemcode.contains(scancode.getText().toString())){
                            getitemcodestring = scancode.getText().toString();
                            loadrequiredqty();
                            linear.setVisibility(View.VISIBLE);
                            serialno.post(new Runnable() {
                                @Override
                                public void run() {
                                    serialno.requestFocus();
                                }
                            });
                            // serialno.requestFocus();
                        }else {
                            linear.setVisibility(View.GONE);
                            Global.showsnackbar(getActivity(),"No Data Found");
                        }
                    }
                    return true;
                }

                return false;
            }
        });

        desc.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View view, int keyCode, KeyEvent keyevent) {

                if ((keyevent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {

                    if(serialno.getText().toString().trim().length() == 0) {
                        Global.showsnackbar(getActivity(),"Please Enter Serial No");
                    }else if(lotno.getText().toString().trim().length() == 0) {
                        Global.showsnackbar(getActivity(),"Please Enter Lot No");
                    }else if(qty.getText().toString().trim().length() == 0) {
                        Global.showsnackbar(getActivity(),"Please Enter Quantity");
                    }else if(desc.getText().toString().trim().length() == 0) {
                        Global.showsnackbar(getActivity(),"Please Enter Description");
                    }  else if((Integer.parseInt(qty.getText().toString())) > (Integer.parseInt(requiredqty.getText().toString())) ) {
                        Global.showsnackbar(getActivity(),"Qunatity Greater Than Required Qty");
                    }  else if(serialnolist.contains(serialno.getText().toString())){
                        Global.showsnackbar(getActivity(),"Serial Number Already Scanned");
                    } else if((Integer.parseInt(qty.getText().toString())) == 0){
                        Global.showsnackbar(getActivity(),"Quantity Cannot Be 0");
                    } else {
//                        Calendar calendar = Calendar.getInstance();
//                        tstamp = calendar.getTimeInMillis();

                    }
                    return true;
                }

                return false;
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    spotsDialog = Global.showdailog(getContext());
                    executor.execute(() -> {
                        savegrndata();
//                        handler.post(() ->
//                                spotsDialog.dismiss()
//                        );
                    });
            }
        });

        return view;
    }

    public void loadpono(){

        MyAPICall myAPICall = retrofit.create(MyAPICall.class);
        Call<Response> call = myAPICall.getgrnpono();

        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {

                Response jsonResponse = response.body();

                if(jsonResponse.getResponse().equals("true")) {

                    getpono=jsonResponse.getPoNo();

                    List orderno = new ArrayList<>();
                    for (int i=0; i<getpono.size(); i++) {
                        orderno.add(getpono.get(i).getOrderNo());
                    }

                    ArrayAdapter aa = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,orderno);
                    aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    pono.setAdapter(aa);

                    pono.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                            getponostring = pono.getSelectedItem().toString();
                            loaditemcode(getponostring);

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                }else {
                    Global.showtoast(getContext(),"No Data");
                    spotsDialog.cancel();
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {

            }
        });

    }

    public void loaditemcode(String getponostring){

        MyAPICall myAPICall = retrofit.create(MyAPICall.class);
        Call<Response> call = myAPICall.getgrnitemcode(getponostring);

        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {

                Response jsonResponse = response.body();

                if(jsonResponse.getResponse().equals("true")) {

                    getitemcode=jsonResponse.getItemcode();

                    itemcode = new ArrayList<>();
                    itemcode.add("Select Item");
                    for (int i=0; i<getitemcode.size(); i++) {
                        itemcode.add(getitemcode.get(i).getItemCode());
                    }

                    ArrayAdapter aa = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,itemcode);
                    aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    itemlist.setAdapter(aa);

                    itemlist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                            getitemcodestring = itemlist.getSelectedItem().toString();
                            scancode.setText(getitemcodestring);

                            if(scancode.getText().toString().trim().length() == 0) {
                                scancode.requestFocus();
                            }if(getitemcodestring.equals("Select Item")) {
                                linear.setVisibility(View.GONE);
                                scancode.setText("");
                                scancode.requestFocus();
                            }else {
                                linear.setVisibility(View.VISIBLE);
                                serialno.requestFocus();
                            }

                            loadrequiredqty();

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                }else {
                    Global.showtoast(getContext(),"No Data");
                    spotsDialog.cancel();
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {

            }
        });

    }

    private void loadrequiredqty(){

        MyAPICall myAPICall = retrofit.create(MyAPICall.class);
        Call<Response> call = myAPICall.getgrnrequiredqty(getponostring,getitemcodestring);

        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if(response.isSuccessful()) {
                    Response checkResponse = response.body();
                    requiredqty.setText(checkResponse.getResponse());
                    spotsDialog.cancel();
                }else {
                    Global.showtoast(getContext(),"something went wrong");
                      spotsDialog.cancel();
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {

            }
        });

    }

    public void loadallgrno(){

        MyAPICall myAPICall = retrofit.create(MyAPICall.class);
        Call<Response> call = myAPICall.getallgrnno();

       call.enqueue(new Callback<Response>() {
           @Override
           public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
               Response jsonResponse = response.body();

               if (jsonResponse.getResponse().equals("true")) {
                   grno = jsonResponse.getgrno();
                   serialnolist = new ArrayList<>();
                   for (int i=0; i<grno.size(); i++) {
                       serialnolist.add(grno.get(i).getGrno());
                   }
                   Log.e("serial list ", String.valueOf(serialnolist.size()));
               } else {
                   Global.showtoast(getContext(), "No Data");
               }
           }

           @Override
           public void onFailure(Call<Response> call, Throwable t) {

           }
       });

    }

    public void loadlastgrno(){

        MyAPICall myAPICall = retrofit.create(MyAPICall.class);
        Call<Response> call = myAPICall.getlastgrndetails(getponostring,getitemcodestring);

        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                Response jsonResponse = response.body();

                if (jsonResponse.getResponse().equals("true")) {
                    lastgrnolist = jsonResponse.getLastGrnoDetails();

                    getgrnid=lastgrnolist.get(0).getGrnId();
                    getgrnno=lastgrnolist.get(0).getGrnNo();
                    getitemname=lastgrnolist.get(0).getItemName();
                    getitemdesc=lastgrnolist.get(0).getItemDesc();

                    for (int i=0;i<Integer.parseInt(qty.getText().toString());i++){
                        savegrndetails();
                    }

                } else {
                    Global.showtoast(getContext(), "No Data");
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {

            }
        });

    }

    private void savegrndata() {

   //     spotsDialog = Global.showdailog(getContext());

        MyAPICall myAPICall = retrofit.create(MyAPICall.class);
        Call<Response> call = myAPICall.savegrndata(getponostring,getitemcodestring,getinvoiceno,getinvoicedate,
                getpckslipno,getpckslipdate,getgrnremark);

        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if(response.isSuccessful()) {
                    Response checkResponse = response.body();

                    if (checkResponse.getResponse().equals("data saved suceessfully")) {
                        loadlastgrno();
//                        lastgrnolist = checkResponse.getLastGrnoDetails();
//
//                        getgrnid=lastgrnolist.get(0).getGrnId();
//                        getgrnno=lastgrnolist.get(0).getGrnNo();
//                        getitemname=lastgrnolist.get(0).getItemName();
//                        getitemdesc=lastgrnolist.get(0).getItemDesc();
//
//                        for (int i=0;i<Integer.parseInt(qty.getText().toString());i++){
//                            savegrndetails();
//                        }


//                        Global.showtoast(getContext(),"data saved suceessfully");
                    }
                }else {
                    Global.showtoast(getContext(),"something went wrong");
                    spotsDialog.cancel();
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {

            }
        });

    }

    private void savegrndetails() {

   //     spotsDialog = Global.showdailog(getContext());

        getreqqty = requiredqty.getText().toString();
        getsrno = serialno.getText().toString();
        getlotno = lotno.getText().toString();
        getdesc = desc.getText().toString();

        MyAPICall myAPICall = retrofit.create(MyAPICall.class);
        Call<Response> call = myAPICall.savegrndetails(getgrnid,getponostring,getitemcodestring,"1",getreqqty,"1",
                getsrno,getlotno,getgrnno,getdesc,getitemname,getitemdesc);

        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if(response.isSuccessful()) {
                    Response checkResponse = response.body();

                    if (checkResponse.getResponse().equals("data saved suceessfully")) {
                        spotsDialog.cancel();
                        Global.showtoast(getContext(),"data saved suceessfully");

                        scancode.setText("");
                        serialno.setText("");
                        lotno.setText("");
                        qty.setText("");
                        desc.setText("");

                        linear.setVisibility(View.GONE);
                        loadpono();

                        scancode.post(new Runnable() {
                            @Override
                            public void run() {
                                scancode.requestFocus();
                            }
                        });

                    }
                }else {
                    Global.showtoast(getContext(),"something went wrong");
                    spotsDialog.cancel();
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {

            }
        });

    }

}