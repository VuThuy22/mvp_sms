package com.example.elcomplus.esms;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.elcomplus.esms.adapter.BankAdapterMain;
import com.example.elcomplus.esms.adapter.SmsAdapter;
import com.example.elcomplus.esms.adapter.SmsAdapterMain;
import com.example.elcomplus.esms.databases.BankDataHelper;
import com.example.elcomplus.esms.databases.SmsDataHelper;
import com.example.elcomplus.esms.models.Bank;
import com.example.elcomplus.esms.models.Sms;
import com.example.elcomplus.esms.mvp.BankPresenter;
import com.example.elcomplus.esms.mvp.BankView;
import com.example.elcomplus.esms.mvp_receiverSms.SmsPresenter;
import com.example.elcomplus.esms.mvp_receiverSms.SmsView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddActivity extends AppCompatActivity implements SmsView {

    @BindView(R.id.txt_bankName)
    TextView txtBankName;
    @BindView(R.id.txt_time)
    TextView txtTime;
    @BindView(R.id.txt_total)
    TextView txtTotal;
    @BindView(R.id.image_add)
    ImageView imageAdd;
    @BindView(R.id.rc_bank)
    RecyclerView rcBank;
    @BindView(R.id.rc_sms)
    RecyclerView rcSms;
    List<Bank> bankList;
    List<Sms> smsList;
    BankAdapterMain adapterBankMain;
    SmsAdapterMain smsAdapterMain;
    LinearLayoutManager managerBank, managerSms;
    BankDataHelper bankDataHelper;
    SmsDataHelper smsDataHelper;
    int MY_PERMISSIONS_REQUEST_SMS_RECEIVE = 1;
    private final int REQ_CODE_SMS = 0;
    BroadcastReceiver receiver;
    private SmsPresenter smsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        ButterKnife.bind(this);
        smsPresenter=new SmsPresenter(AddActivity.this, this);
        bankList=new ArrayList<>();
        smsList=new ArrayList<>();
        adapterBankMain=new BankAdapterMain(bankList);
        managerBank=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        rcBank.setLayoutManager(managerBank);
        rcBank.setAdapter(adapterBankMain);
        smsAdapterMain=new SmsAdapterMain(smsList);
        managerSms=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rcSms.setLayoutManager(managerSms);
        rcSms.setAdapter(smsAdapterMain);
        smsPresenter.getList();

        imageAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AddActivity.this,MainActivity.class);
                startActivityForResult(intent,1);
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED) {
            } else {
                requestPermissions(new String[]{Manifest.permission.READ_SMS}, REQ_CODE_SMS);
            }
            if (checkSelfPermission(Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED) {

            } else {
                requestPermissions(
                        new String[]{Manifest.permission.RECEIVE_SMS},
                        MY_PERMISSIONS_REQUEST_SMS_RECEIVE);
            }

        }
        registerReceiver();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){
            if(resultCode==1){
                bankList.clear();
                bankList.addAll(bankDataHelper.getAll());
                adapterBankMain.notifyDataSetChanged();
            }
        }
    }

    private void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("receiverSms");
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                DialogShowSms();
            }
        };
        registerReceiver(receiver, intentFilter);
    }


    public void DialogShowSms(){
        final AlertDialog.Builder builder;
        builder=new AlertDialog.Builder(AddActivity.this);
        builder.setTitle("Thông báo").setMessage("Requets thành công").setCancelable(true);
        builder.setPositiveButton("Oke", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
              smsPresenter.getList();
            }
        });

        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }

    @Override
    public void displayBank(List<Bank> list) {
        this.bankList.clear();
        this.bankList.addAll(list);
        adapterBankMain.notifyDataSetChanged();
    }

    @Override
    public void displaySms(List<Sms> list) {
        this.smsList.clear();
        this.smsList.addAll(list);
        smsAdapterMain.notifyDataSetChanged();
    }

    @Override
    public void showDialog(String title, String message) {
        final AlertDialog.Builder builder;
        builder=new AlertDialog.Builder(AddActivity.this);
        builder.setTitle(title).setMessage(message).setCancelable(true);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }

    @Override
    public void onSuccessRequest(List<Sms> list) {
        this.smsList.clear();
        this.smsList.addAll(list);
        smsAdapterMain.notifyDataSetChanged();
    }

    @Override
    public void onFaileRequest(String title, String mesage) {

    }
}
