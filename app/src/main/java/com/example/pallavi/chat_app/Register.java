package com.example.pallavi.chat_app;

import android.os.Bundle;
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

public class Register extends AppCompatActivity {
Button bt3;EditText Username;
    EditText Email;
    EditText Password;
    EditText Cpassword;
    String data,s1,e,u,p,cp;
    JSONObject jo;
    int flag=0;int c1,c2,ind1,ind2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        bt3=(Button)findViewById(R.id.btn_signup1);
       // Email = (EditText) findViewById(R.id.email);
        register();

    }
  /*  public void setInputTypeToEmail(View view){

        Email.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
    }*/
    public void register(){
        Toast.makeText(Register.this,"entered Register",Toast.LENGTH_LONG).show();
       bt3.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(Register.this, "clicked signup", Toast.LENGTH_LONG).show();
                        Username = (EditText) findViewById(R.id.username);
                        Email = (EditText) findViewById(R.id.email);
                        Password = (EditText) findViewById(R.id.input_password);
                        Cpassword = (EditText) findViewById(R.id.input_cpassword1);
                        u = Username.getText().toString();
                        e = Email.getText().toString();
                        p = Password.getText().toString();
                        cp = Cpassword.getText().toString();
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
                        if((c1==0)&&(c2==1)){
                          Email.setError("@ is missing");
                        }
                      else  if((c2==0)&&(c1==1))
                        {
                            Email.setError(". is missing");

                        }
                      else  if((c2==0)&&(c1==0))
                        {
                            Email.setError("@ and . are missing");
                        }
                        else if((c1>1)||(c2>1))
                        {
                            Email.setError("multiple @ or . are not not allowed");
                        }



                        if ((c1 == 1)&&(c2 == 1) && (ind2 > ind1))*/
                        boolean b= Pattern.matches("[a-zA-Z0-9+._%-+]{1,256}" +
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
                      {
                            c1=0;c2=0;
                        Toast.makeText(Register.this, "Usename is" + u, Toast.LENGTH_LONG).show();
                        Thread t = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                data = "{\"email\":\"" + e + "\",\"password\":\"" + p + "\",\"cpassword\":\"" + cp + "\",\"username\":\"" + u + "\"}";
                                Log.v("THE REQUESTED DATA IS ", data);
                                OkHttpClient client = new OkHttpClient();
                                Request request = new Request.Builder()
                                        .url("http://www.palzone.ml/service_pallavi/register1.php")
                                        .post(RequestBody.create(okhttp3.MediaType.parse("application/json;charset=utf-8"), data))
                                        .build();
                                client.newCall(request).enqueue(new Callback() {
                                    @Override
                                    public void onFailure(Call call, final IOException e) {
                                        Register.this.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Log.v("ON FAILURE ", "CLIENT REQUEST NOT SENT ");
                                                Toast.makeText(Register.this, "client request not sent", Toast.LENGTH_LONG).show();
                                            }
                                        });

                                    }

                                    @Override
                                    public void onResponse(Call call, final Response response) throws IOException {
                                        s1 = response.body().string();
                                        final String s2;
                                        s2 = s1.substring(s1.indexOf('[') + 1, s1.length() - 1);
                                        try {
                                            jo = new JSONObject(s2);
                                        } catch (JSONException e1) {
                                            e1.printStackTrace();
                                        }

                                        Log.v("THe response is:", s1);
                                        Register.this.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                try {
                                                    int statusResponse = Integer.parseInt(String.valueOf(jo.getInt("response_data")));
                                                    if (statusResponse == 1)
                                                        Toast.makeText(Register.this, "registration successful", Toast.LENGTH_LONG).show();
                                                    else if (statusResponse == 2)
                                                        Toast.makeText(Register.this, "password not matched", Toast.LENGTH_SHORT).show();
                                                    else if (statusResponse == 3)
                                                        Toast.makeText(Register.this, "Email already exists", Toast.LENGTH_SHORT).show();
                                                    else if (statusResponse == 4)
                                                        Toast.makeText(Register.this, "missing some field", Toast.LENGTH_SHORT).show();
                                                   // Toast.makeText(Register.this,"Login in order to enter the app",Toast.LENGTH_LONG).show();
                                                   // Intent intent=new Intent("com.example.pallavi.chat_app.MainActivity");
                                                    //startActivity(intent);


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
                    }

        );
    }

}
