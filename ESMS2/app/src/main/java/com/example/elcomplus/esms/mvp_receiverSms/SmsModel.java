package com.example.elcomplus.esms.mvp_receiverSms;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.elcomplus.esms.databases.BankDataHelper;
import com.example.elcomplus.esms.databases.SmsDataHelper;
import com.example.elcomplus.esms.models.Bank;
import com.example.elcomplus.esms.models.Sms;
import com.example.elcomplus.esms.mvp.BankCallBack;
import com.example.elcomplus.esms.mvp.BankPresenter;

import org.json.JSONException;
import org.json.JSONObject;

public class SmsModel {
    private SmsCallBack smsCallBack;
    private SmsPresenter smsPresenter;
    BankDataHelper bankDataHelper;
    SmsDataHelper smsDataHelper;
    Context context;
    Bank bank;
    Sms sms;

    public SmsModel(Context context,SmsCallBack smsCallBack) {
        this.smsCallBack = smsCallBack;
        this.context=context;
        bankDataHelper=new BankDataHelper(context);
        smsDataHelper=new SmsDataHelper(context);
    }

    public SmsModel(SmsPresenter smsPresenter) {
        this.smsPresenter = smsPresenter;
    }

    public void mGetList(){
        smsCallBack.showAllBank(bankDataHelper.getAll());
        smsCallBack.showAllSms(smsDataHelper.getAll());
    }

    public void insertSms(Sms sms,int id_request){
        long id = smsDataHelper.addSms(sms);
        if(id<0){
            smsCallBack.onAddFailure("Add Failure");
        }
        else {
            sms.setSmsId(((int) id));
            smsCallBack.onAddSmsSuccess(sms, id_request);
        }
    }
    public void postRequest(int id_request, String url, final Sms sms){
        this.sms=sms;
        JSONObject request=new JSONObject();
        try{
            request.put("requestId",id_request);
            request.put("bankId",-2);
            request.put("bankName",sms.getBankName());
            request.put("requestType",-1);
            request.put("money",0);
            request.put("content","");
            request.put("account","");
            request.put("fullContent",sms.getContent());
            request.put("action",0);

        }catch (Exception e){
            e.printStackTrace();
        }
        JsonObjectRequest postJson=new JsonObjectRequest(Request.Method.POST, url, request, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    int result = response.getInt("result");
                    String resultDesc = response.getString("resultDesc");
                    String requestId = response.getString("requestId");
                    smsCallBack.onSuccessRequest(requestId+resultDesc);
                    sms.setStatus(1);
                    int e=sms.getSmsId();
                    editSms(sms);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            ;
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                smsCallBack.onFaileRequest("Error!");
            }
        });
        Volley.newRequestQueue(context.getApplicationContext()).add(postJson);
    }
    public void editSms(Sms sms){
        smsDataHelper.editSms(sms);
    }
}
