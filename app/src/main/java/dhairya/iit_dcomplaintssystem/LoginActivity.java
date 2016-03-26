package com.example.dhairya.complaintsystem;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends Activity {
    String category;
    private EditText Username;
    private EditText Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Typeface tf1 = Typeface.createFromAsset(getAssets(), "fonts/Aller_Bd.ttf");
        TextView tv1 = (TextView) findViewById(R.id.titleText1);
        tv1.setTypeface(tf1);
        tv1 = (TextView) findViewById(R.id.titleText2);
        tv1.setTypeface(tf1);

        Username = (EditText) findViewById(R.id.username);
        Password = (EditText) findViewById(R.id.password);

        NoDefaultSpinner spinner = (NoDefaultSpinner) findViewById(R.id.category);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spinner_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position) != null)
                    category(parent.getItemAtPosition(position));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    public void category(Object c) {
        category = c.toString();
    }
    public void login(View v){
        final String usrnm = Username.getText().toString().trim();
        final String pswd = Password.getText().toString().trim();
        final String categ = category;
        String URL = "http://10.250.215.206:80/complaint_management/default/login.php";

        StringRequest jsObjRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject response1 = null;
                        try {
                             response1 = new JSONObject(response);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        try {
                            if (response1.getInt("success") == 1) {
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("usertype",response1.getString("usertype"));
                                intent.putExtra("name",response1.getString("name"));
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            } else
                                Toast.makeText(LoginActivity.this, response1.getString("message"), Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                if(usrnm!=null)
                    params.put("username", usrnm);
                if(pswd!=null)
                    params.put("password", pswd);
                if(categ!=null)
                    params.put("logintype", categ);
                return params;
            }
        };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsObjRequest);
    }
    }