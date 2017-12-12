package com.example.adminjs.jiaosheng1212;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private EditText phone;
    private EditText pass;
    private Button lu;
    private Button ce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        phone = findViewById(R.id.phone);
        pass = findViewById(R.id.pass);
        lu = findViewById(R.id.login_lu);
        ce = findViewById(R.id.login_ce);

        lu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String p = phone.getText().toString().trim();
                String pa = pass.getText().toString().trim();
               Retrofit retrofit =  new Retrofit.Builder()
                       .baseUrl("http://120.27.23.105")
                       .addConverterFactory(GsonConverterFactory.create())
                       .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                       .build();
                Service service = retrofit.create(Service.class);
                service.getLogin(p,pa).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<LoginBean>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(LoginBean loginBean) {
                                String code = loginBean.getCode();
                                if (code.equals("0")){
                                    Toast.makeText(MainActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(MainActivity.this, XinxiActivity.class);
                                    startActivity(intent);
                                }else{
                                    Toast.makeText(MainActivity.this,"登录失败",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


            }
        });
        ce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ZhuceActivity.class);
                startActivity(intent);
            }
        });
    }
}
