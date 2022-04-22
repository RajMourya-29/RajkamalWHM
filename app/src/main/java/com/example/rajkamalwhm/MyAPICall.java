package com.example.rajkamalwhm;

import com.example.rajkamalwhm.grnentry.Response;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface MyAPICall {

    @GET("GetGRNPONO")
    Call<Response> getgrnpono();

    @GET("GetGRNItemcode")
    Call<Response> getgrnitemcode(@Query("orderno") String orderno);

    @FormUrlEncoded
    @POST("GetGRNRequiredQty")
    Call<Response> getgrnrequiredqty(@Field("orderno") String orderno,
                              @Field("item_code") String item_code);

    @FormUrlEncoded
    @POST("GetLastGrnoDetails")
    Call<Response> getlastgrndetails(@Field("orderno") String orderno,
                                     @Field("item_code") String item_code);

    @GET("GetAllGrno")
    Call<Response> getallgrnno();

    @FormUrlEncoded
    @POST("SaveGRN")
    Call<Response> savegrndata(@Field("orderno") String orderno,
                               @Field("item_code") String item_code,
                               @Field("invoiceno") String invoiceno,
                               @Field("invoicedate") String invoicedate,
                               @Field("pckslipno") String pckslipno,
                               @Field("pckslipdate") String pckslipdate,
                               @Field("grnremark") String grnremark);

    @FormUrlEncoded
    @POST("SaveGRNDetails")
    Call<Response> savegrndetails(@Field("grn_id") String grn_id,
                              @Field("po_no") String po_no,
                              @Field("itempartno") String itempartno,
                              @Field("itemqty") String itemqty,
                              @Field("recvqty") String recvqty,
                              @Field("item_pcs") String item_pcs,
                              @Field("item_srno") String item_srno,
                              @Field("lotno") String lotno,
                              @Field("grn_no") String grn_no,
                              @Field("idescription") String idescription,
                              @Field("item_name") String item_name,
                              @Field("item_desc") String item_desc);

    @GET("GetSOPONO")
    Call<Response> getsopono();

    @GET("GetSOItemcode")
    Call<Response> getsoitemcode(@Query("orderno") String orderno);

    @FormUrlEncoded
    @POST("GetSORequiredQty")
    Call<Response> getsorequiredqty(@Field("orderno") String orderno,
                                     @Field("item_code") String item_code);

}
