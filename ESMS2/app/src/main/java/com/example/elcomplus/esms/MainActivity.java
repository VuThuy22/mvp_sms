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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.elcomplus.esms.adapter.BankAdapter;
import com.example.elcomplus.esms.models.Bank;
import com.example.elcomplus.esms.models.Sms;
import com.example.elcomplus.esms.mvp.BankPresenter;
import com.example.elcomplus.esms.mvp.BankView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements BankView{


    @BindView(R.id.edt_bankPhone)
    EditText edtBankPhone;
    @BindView(R.id.edt_bankName)
    EditText edtBankName;
    @BindView(R.id.btn_add)
    Button btnAdd;
    @BindView(R.id.rc_bank)
    RecyclerView rcBank;
    private BankPresenter bankPresenter;
    LinearLayoutManager manager;
    BankAdapter adapter;
    List<Bank> bankList;
    BroadcastReceiver receiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        bankList=new ArrayList<>();
        manager=new LinearLayoutManager(MainActivity.this);
        adapter=new BankAdapter(bankList);
        rcBank.setLayoutManager(manager);
        rcBank.setAdapter(adapter);
        bankPresenter=new BankPresenter(MainActivity.this, this);
        bankPresenter.getList();
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bankPhone=edtBankPhone.getText().toString();
                String bankName=edtBankName.getText().toString();
                bankPresenter.insert(new Bank(bankName,bankPhone));
                edtBankName.setText("");
                edtBankPhone.setText("");
                Intent intent=new Intent(MainActivity.this, AddActivity.class);
                setResult(1,intent);
                finish();
            }
        });
        adapter.setOnItemClick(new BankAdapter.OnItemClick() {
            @Override
            public void onClick(int postion) {
                DialogDelete(postion);
            }
        });


    }


    @Override
    public void displayBank(List<Bank> list) {
        this.bankList.clear();
        this.bankList.addAll(list);
        adapter.notifyDataSetChanged();
    }


    @Override
    public void displaySms(List<Sms> list) {

    }

    @Override
    public void showDialog(String title,String message) {
        final AlertDialog.Builder builder;
        builder=new AlertDialog.Builder(MainActivity.this);
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

    public void DialogDelete(final int id){
        final AlertDialog.Builder builder;
        builder=new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Chú ý!").setMessage("Bạn có chắc chắn muốn xóa bản ghi?").setCancelable(true);
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                bankPresenter.delete(id);
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }



}
