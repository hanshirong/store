package com.example.store;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class login extends AppCompatActivity implements View.OnClickListener {
    private EditText et_username;
    private EditText et_password;
    private Button btn_login;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        Toast.makeText(getApplicationContext(), "Button is clicked", Toast.LENGTH_LONG).show();
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        btn_login = (Button) findViewById(R.id.btn_login);
        tv = (TextView) findViewById(R.id.tv);
        btn_login.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                String username = et_username.getText().toString();
                String password = et_password.getText().toString();
                //判断输入表单是否为空
                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                    Toast.makeText(this, "输入不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                //登录请求
                OkHttpClient client = new OkHttpClient();
                Gson gson = new Gson();
                FormBody formBody = new FormBody.Builder()
                        .add("username", username)
                        .add("password", password)
                        .build();
                Request request = new Request.Builder()
                        .post(formBody)
                        .url("http://120.26.199.166:8088/api/v1/auth/signin")
                        .build();
                Call call = client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(login.this, "请求失败", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                    @Override
                    public void onResponse(Call call,  Response response) throws IOException {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try{
                                    String result=response.body().string();
                                    Gson gson = new Gson();
                                    ResultData loginData = gson.fromJson(result, ResultData.class);
                                    String token=loginData.getToken();
                                    MyApplication.setToken(token);
                                    tv.setText("全局变量token "+MyApplication.getToken());
                                    Toast.makeText(login.this,"请求成功",Toast.LENGTH_LONG).show();
                                }catch (IOException e){
                                    e.printStackTrace();
                                    Toast.makeText(login.this,"请求捕捉到了错误",Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                        }
             });
                break;
        }

    }
    public class ResultData{
        private String token;
        private  int  resultCode;
        private Object data;
        public String getToken(){
            return token;
        }
        public void setToken(String token){
            this.token=token;
        }
        public int getResultcode(){
            return resultCode;
        }
        public void setResultCode(int resultCode){
            this.resultCode=resultCode;
        }
        public Object getData(){
            return data;
        }
        public void setData(Object data){
            this.data=data;
        }


    }



}



