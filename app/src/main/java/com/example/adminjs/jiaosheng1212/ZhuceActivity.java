package com.example.adminjs.jiaosheng1212;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Adminjs on 2017/12/12.
 */

public class ZhuceActivity extends AppCompatActivity{

    private EditText uphone;
    private EditText upass;
    private TextView fan;
    private Button uzhu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhuce);
        uphone = findViewById(R.id.uphone);
        upass = findViewById(R.id.upass);
        fan = findViewById(R.id.fan);
        uzhu = findViewById(R.id.zhu);

        fan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ZhuceActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        uzhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String p = uphone.getText().toString().trim();
                String pa=upass.getText().toString().trim();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://120.27.23.105")
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .build();
                Service service = retrofit.create(Service.class);
                service.getRegist(p,pa).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<RegistBean>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(RegistBean registBean) {
                                String code = registBean.getCode();
                                if (code.equals("0")){
                                    Toast.makeText(ZhuceActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                                    finish();
                                }else{
                                    Toast.makeText(ZhuceActivity.this,"注册失败",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}
