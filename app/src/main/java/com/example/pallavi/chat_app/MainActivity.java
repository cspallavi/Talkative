package com.example.pallavi.chat_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
Button bt1,bt2;
    EditText Email,Password;int c1=0;int c2=0;int ind1,ind2;int userid;
    String e,p;String data;String s1;JSONObject jo;int response_data;int wait=0;SharedPreferences sp;int sessionid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sp = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        sessionid = sp.getInt("sessionid", -1);
        if (sessionid == -1) {
            bt2 = (Button) findViewById(R.id.btn_login);
            bt1 = (Button) findViewById(R.id.btn_signup);


            onClickButtonListener();
            Log.v("hello pallavi", "hello");
            login();
        } else
        {
            Intent intent=new Intent("com.example.pallavi.chat_app.Chat");
            startActivity(intent);
        }
            }
            public void onClickButtonListener(){

                bt1.setOnClickListener(
                        new View.OnClickListener(){
                       public void  onClick(View v){
                           Intent intent=new Intent("com.example.pallavi.chat_app.Register");
                           startActivity(intent);

                }}
                );
            }
            public void login() {
                Toast.makeText(MainActivity.this, "entered login page", Toast.LENGTH_LONG).show();
                if (wait == 0) {
                    bt2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            wait = 1;
                            Toast.makeText(MainActivity.this, "login pressed"+wait, Toast.LENGTH_LONG).show();


                            Email = (EditText) findViewById(R.id.email2);
                            Password = (EditText) findViewById(R.id.input_password2);
                            e = Email.getText().toString();
                            p = Password.getText().toString();
                          /*  for (int i = 0; i < e.length(); i++) {
                                if (e.charAt(i) == '@') {
                                    c1++;
                                    ind1 = i;

                                }
                                if (e.charAt(i) == '.') {
                                    c2++;
                                    ind2 = i;

                                }


                            }
                            if ((c1 == 0) && (c2 == 1)) {
                                Email.setError("@ is missing");
                            } else if ((c2 == 0) && (c1 == 1)) {
                                Email.setError(". is missing");

                            } else if ((c2 == 0) && (c1 == 0)) {
                                Email.setError("@ and . are missing");
                            } else if ((c1 >=2) || (c2 >=2)) {
                                Email.setError("multiple @ or . are not  allowed");
                            }

                            //Toast.makeText(MainActivity.this,"c1="+c1+" c2="+c2,Toast.LENGTH_LONG).show();
                            if ((c1 == 1) && (c2 == 1) && (ind2 > ind1)) */
                          /*  Pattern p1= Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$");
                            Matcher m= p1.matcher(e);
                            boolean b=m.matches();*/
                          boolean b=Pattern.matches("[a-zA-Z0-9+._%-+]{1,256}" +
                                  "@" +
                                  "[a-zA-Z0-9][a-zA-Z0-9-]{0,64}" +
                                  "(" +
                                  "." +
                                  "[a-zA-Z0-9][a-zA-Z0-9-]{0,25}" +
                                  ")+",e);
                           // if(b==true)
                              //  Toast.makeText(MainActivity.this, "matches", Toast.LENGTH_LONG).show();

                           if(b==false)
                            {
                                Email.setError("email entered does not follow the correct format");
                            }
                            else
                          {  // Toast.makeText(MainActivity.this, "does not match", Toast.LENGTH_LONG).show();

                                Toast.makeText(MainActivity.this, "password is" + p, Toast.LENGTH_LONG).show();
                                Thread t = new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Log.v("PALLAVI", "ENTERED NEW THREAD");
                                        data = "{\"email\":\"" + e + "\",\"password\":\"" + p + "\"}";
                                        Log.v("THE REQUESTED DATA IS:", data);
                                        wait = 1;
                                        OkHttpClient client = new OkHttpClient();
                                        Request request = new Request.Builder()
                                                .url("http://www.palzone.ml/service_pallavi/login.php")

                                                .post(RequestBody.create(okhttp3.MediaType.parse("application/json;charset=utf-8"), data))
                                                .build();
                                        client.newCall(request).enqueue(new Callback() {
                                            @Override
                                            public void onFailure(Call call, IOException e) {
                                                MainActivity.this.runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        Toast.makeText(MainActivity.this, "client request not sent", Toast.LENGTH_LONG).show();
                                                        wait = 0;
                                                    }
                                                });
                                                Log.v("ON FAILURE", "NEW CALLBACK FAILURE");
                                            }

                                            @Override
                                            public void onResponse(Call call, final Response response) throws IOException {
                                                Log.v("PALLAVI", "ENTERED ON RESPONSE");
                                            /*    MainActivity.this.runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        Email.setText(null);
                                                        Password.setText(null);
                                                    }
                                                });*/
                                                try {
                                                    s1 = response.body().string();
                                                } catch (Exception e2) {
                                                    e2.printStackTrace();
                                                    wait = 0;
                                                }
                                                Log.v("THE JSON OBJ IS CREATED", s1);

                                                final String s2 = s1.substring(s1.indexOf('[') + 1, s1.length() - 1);
                                                //s1.substring(s1.indexOf('[')+1, s1.length() - 1);

                                                try {
                                                    jo = new JSONObject(s2);

                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }

                                                MainActivity.this.runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {

                                                        try {
                                                            SharedPreferences sp= PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                                                            SharedPreferences.Editor ed=sp.edit();
                                                            userid=Integer.parseInt(String.valueOf(jo.getInt("userid")));
                                                            ed.putInt("sessionid",userid);
                                                            ed.commit();
// SharedPreferences sp= PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                                                            //sessionid=sp.getInt("sessionid",-1);
                                                            response_data = Integer.parseInt(String.valueOf(jo.getInt("response_data")));
                                                            if (response_data == 5) {
                                                                Toast.makeText(MainActivity.this, "You have logged in successfully..Please wait till the page is redirected to home page", Toast.LENGTH_LONG).show();
                                                                Intent intent=new Intent("com.example.pallavi.chat_app.Chat");
                                                                startActivity(intent);

                                                            } else if (response_data == 6) {
                                                                Toast.makeText(MainActivity.this, "Password does not match", Toast.LENGTH_LONG).show();
                                                            } else if (response_data == 7) {
                                                                Toast.makeText(MainActivity.this, "Email does not match", Toast.LENGTH_LONG).show();
                                                            } else if (response_data == 8) {
                                                                Toast.makeText(MainActivity.this, "Missing fields", Toast.LENGTH_LONG).show();
                                                            }
                                                            wait = 0;
                                                        } catch (JSONException e1) {
                                                            e1.printStackTrace();
                                                            wait = 0;

                                                        }

                                                    }
                                                });
                                            }
                                        });

                                    }
                                });
                                t.start();


                            }
                        }

                    });

               /* Toast.makeText(MainActivity.this,"entered Register",Toast.LENGTH_LONG).show();
                bt2.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainActivity.this,"clicked signup",Toast.LENGTH_LONG).show();

                                Email = (EditText) findViewById(R.id.email2);
                                Password = (EditText) findViewById(R.id.input_password2);

                                e=Email.getText().toString();
                                p=Password.getText().toString();
                                //Toast.makeText(MainActivity.this,"Usename is"+u,Toast.LENGTH_LONG).show();
                                Thread t = new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        data = "{\"email\":\"" + e + "\",\"password\":\"" + p + "\"}";
                                        Log.v("THE REQUESTED DATA IS ", data);
                                        OkHttpClient client = new OkHttpClient();
                                        Request request = new Request.Builder()
                                                .url("http://www.palzone.ml/service_pallavi/login.php")
                                                .post(RequestBody.create(okhttp3.MediaType.parse("application/json;charset=utf-8"), data))
                                                .build();
                                        client.newCall(request).enqueue(new Callback() {
                                            @Override
                                            public void onFailure(Call call, final IOException e) {
                                                MainActivity.this.runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        Log.v("ON FAILURE ","CLIENT REQUEST NOT SENT ");
                                                        Toast.makeText(MainActivity.this, "client request not sent", Toast.LENGTH_LONG).show();
                                                    }
                                                });

                                            }

                                            @Override
                                            public void onResponse(Call call, final Response response) throws IOException {
                                                s1 = response.body().string();
                                                final String s2;
                                                s2 = s1.substring(s1.indexOf('[')+1, s1.length() - 1);
                                                try {
                                                    jo = new JSONObject(s2);
                                                } catch (JSONException e1) {
                                                    e1.printStackTrace();
                                                }

                                                Log.v("THe response is:", s1);
                                                MainActivity.this.runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        try {
                                                            int statusResponse = Integer.parseInt(String.valueOf(jo.getInt("response_data")));
                                                            if (statusResponse == 5)
                                                                Toast.makeText(MainActivity.this, "login successful", Toast.LENGTH_LONG).show();
                                                            else if (statusResponse == 6)
                                                                Toast.makeText(MainActivity.this, "password not matched", Toast.LENGTH_SHORT).show();
                                                            else if (statusResponse == 7)
                                                                Toast.makeText(MainActivity.this, "Email does not exists", Toast.LENGTH_LONG).show();
                                                            else if (statusResponse == 8)
                                                                Toast.makeText(MainActivity.this, "missing some field", Toast.LENGTH_LONG).show();

                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                });

                                            }
                                        });


                                    }
                                });
                                t.start();
                            }
                        }
                );*/

                }

            }
}
