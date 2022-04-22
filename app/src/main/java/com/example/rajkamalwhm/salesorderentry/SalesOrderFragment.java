package com.example.rajkamalwhm.salesorderentry;

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
import com.example.rajkamalwhm.grnentry.Itemcode;
import com.example.rajkamalwhm.grnentry.PoNo;
import com.example.rajkamalwhm.grnentry.Response;
import com.example.rajkamalwhm.grnentry.recyclerdata.GRNAdapter;
import com.example.rajkamalwhm.grnentry.recyclerdata.GRNPojo;
import com.example.rajkamalwhm.roomdatabase.DatabaseClient;
import com.example.rajkamalwhm.roomdatabase.GRNBarcodes;
import com.example.rajkamalwhm.roomdatabase.SOBarcodes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

public class SalesOrderFragment extends Fragment {

    View view;
    Spinner pono,itemlist;
    EditText requiredqty,scancode,serialno,lotno,qty;
    List<PoNo> getpono;
    List<Itemcode> getitemcode;
    Retrofit retrofit;
    String getponostring,getitemcodestring,getsumqty;
    SpotsDialog spotsDialog;
    List itemcode;
    LinearLayout linear;
    RecyclerView recyclerView;
    long tstamp;
    Button save;

    List<String> serialnolist;
    ArrayList<GRNPojo> grnPojoArrayList;
    GRNAdapter grnAdapter;

    ExecutorService executor = Executors.newSingleThreadExecutor();
    Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_sales_order, container, false);
        pono = view.findViewById(R.id.salesno);
        itemlist = view.findViewById(R.id.itemlist);
        requiredqty = view.findViewById(R.id.requiredqty);
        scancode = view.findViewById(R.id.scancode);
        serialno = view.findViewById(R.id.serialno);
        lotno = view.findViewById(R.id.lotno);
        qty = view.findViewById(R.id.qty);
        linear = view.findViewById(R.id.linear);
        recyclerView = view.findViewById(R.id.rec);
        save = view.findViewById(R.id.save);

        serialnolist = new ArrayList<>();
        grnPojoArrayList = new ArrayList<>();
        grnAdapter=new GRNAdapter(grnPojoArrayList,getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(grnAdapter);

        DatabaseClient.getInstance(getContext()).getAppDatabase()
                .grnbarcodeDAO()
                .deleteso();

        retrofit = new Retrofit.Builder()
                .baseUrl(Global.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        spotsDialog = Global.showdailog(getContext());
        executor.execute(() -> {
            loadpono();
            handler.post(() ->  spotsDialog.dismiss());
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

        qty.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View view, int keyCode, KeyEvent keyevent) {

                if ((keyevent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {

                    getsumqty = DatabaseClient
                            .getInstance(getContext())
                            .getAppDatabase()
                            .grnbarcodeDAO().getSOSumQty(scancode.getText().toString());

                    serialnolist = DatabaseClient.getInstance(getContext()).getAppDatabase()
                            .grnbarcodeDAO()
                            .getAllSOSerialNo();

                    getsumqty= String.valueOf(Integer.parseInt(getsumqty)+Integer.parseInt(qty.getText().toString()));

                    if(serialno.getText().toString().trim().length() == 0) {
                        Global.showsnackbar(getActivity(),"Please Enter Serial No");
                    }else if(lotno.getText().toString().trim().length() == 0) {
                        Global.showsnackbar(getActivity(),"Please Enter Lot No");
                    }else if(qty.getText().toString().trim().length() == 0) {
                        Global.showsnackbar(getActivity(),"Please Enter Quantity");
                    }  else if((Integer.parseInt(qty.getText().toString())) > (Integer.parseInt(requiredqty.getText().toString())) ) {
                        Global.showsnackbar(getActivity(),"Qunatity Greater Than Required Qty");
                    } else if((Integer.parseInt(getsumqty)) > (Integer.parseInt(requiredqty.getText().toString())) ) {
                        Global.showsnackbar(getActivity(),"Qunatity Greater Than Required Qty");
                    } else if(serialnolist.contains(serialno.getText().toString())){
                        Global.showsnackbar(getActivity(),"Serial Number Already Scanned");
                    } else {

                        Calendar calendar = Calendar.getInstance();
                        tstamp = calendar.getTimeInMillis();

                        String getserialno = serialno.getText().toString();
                        String getlotno = lotno.getText().toString();
                        String getqty = qty.getText().toString();
                        String getuniquecode = String.valueOf(tstamp);

                        SOBarcodes barcode = new SOBarcodes();
                        barcode.setBarcode(getitemcodestring);
                        barcode.setSerialno(getserialno);
                        barcode.setLotno(getlotno);
                        barcode.setQty(getqty);
                        barcode.setPiece("1");
                        barcode.setUniquecode(getuniquecode);

                        DatabaseClient.getInstance(getContext()).getAppDatabase()
                                .grnbarcodeDAO()
                                .insertso(barcode);

                        grnPojoArrayList.add(new GRNPojo(scancode.getText().toString(),getserialno,getlotno,getqty,"1",getuniquecode));

                        try {
                            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                        } catch (Exception e) {
                        }

                        grnAdapter.notifyDataSetChanged();

                        scancode.setText("");
                        serialno.setText("");
                        lotno.setText("");
                        qty.setText("");

                        linear.setVisibility(View.GONE);
                        scancode.post(new Runnable() {
                            @Override
                            public void run() {
                                scancode.requestFocus();
                            }
                        });

                    }
                    return true;
                }

                return false;
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(grnPojoArrayList.size()==0){
                    Global.showsnackbar(getActivity(),"No Data To Save");
                }else {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        JSONArray scanDataJsonArray = new JSONArray(grnPojoArrayList.toString());
                        jsonObject.put("SOEntryList", scanDataJsonArray);
                        Log.e("Json: ", jsonObject.toString());
                    } catch (JSONException exception){
                        exception.printStackTrace();
                    }
                }
            }
        });

        return view;
    }

    public void loadpono(){

        MyAPICall myAPICall = retrofit.create(MyAPICall.class);
        Call<Response> call = myAPICall.getsopono();

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
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {

            }
        });

    }

    public void loaditemcode(String getponostring){

        MyAPICall myAPICall = retrofit.create(MyAPICall.class);
        Call<Response> call = myAPICall.getsoitemcode(getponostring);

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
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {

            }
        });

    }

    private void loadrequiredqty(){

        MyAPICall myAPICall = retrofit.create(MyAPICall.class);
        Call<Response> call = myAPICall.getsorequiredqty(getponostring,getitemcodestring);

        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if(response.isSuccessful()) {
                    Response checkResponse = response.body();
                    requiredqty.setText(checkResponse.getResponse());

                }else {
                    Global.showtoast(getContext(),"something went wrong");
                    //  spotsDialog.cancel();
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {

            }
        });

    }

}