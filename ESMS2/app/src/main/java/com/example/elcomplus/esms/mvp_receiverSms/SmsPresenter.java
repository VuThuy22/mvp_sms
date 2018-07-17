package com.example.elcomplus.esms.mvp_receiverSms;

import android.content.Context;

import com.example.elcomplus.esms.models.Bank;
import com.example.elcomplus.esms.models.Sms;
import com.example.elcomplus.esms.mvp.BankModel;
import com.example.elcomplus.esms.mvp.BankView;

import java.util.List;

public class SmsPresenter implements SmsCallBack{

    private SmsView smsView;
    private SmsModel smsModel;
    String URL = "http://101.99.23.175:5566";

    public SmsPresenter(Context context, SmsView smsView) {
        this.smsView = smsView;
        this.smsModel=new SmsModel(context,this);
    }

    public void insertSms(Sms sms, int id_requets){
        smsModel.insertSms(sms,id_requets);
    }
    public void getList(){
        smsModel.mGetList();
    }
    public void sendRequets(int Id_requets,String url,Sms sms){
        smsModel.postRequest(Id_requets,url,sms);
    }

    @Override
    public void onAddSmsSuccess(Sms sms, int id_request) {
        getList();
        String url = URL + "/api/vietlott/admin/change_balance";
        sendRequets(id_request,url,sms);
    }

    @Override
    public void onAddFailure(String message) {

    }

    @Override
    public void onSuccessRequest(String message) {
        smsView.showDialog("Succes",message);
    }

    @Override
    public void onFaileRequest(String message) {
        smsView.showDialog("Faile",message);
    }

    @Override
    public void showAllBank(List<Bank> list) {
        smsView.displayBank(list);
    }

    @Override
    public void showAllSms(List<Sms> list) {
        smsView.displaySms(list);
    }
}
