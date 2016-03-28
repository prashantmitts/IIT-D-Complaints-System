package layout;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dhairya.complaintsystem.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FragChangePassword extends android.support.v4.app.Fragment {

    String old = null;
    String newp = null;
    String confirmp = null;
    View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_change_password, container,false);

        Button button = (Button) view.findViewById(R.id.changeButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = (EditText) view.findViewById(R.id.oldpasswordEdit);
                old = editText.getText().toString().trim();
                editText = (EditText) view.findViewById(R.id.newpasswordEdit);
                newp = editText.getText().toString().trim();
                editText = (EditText) view.findViewById(R.id.confirmpasswordEdit);
                confirmp = editText.getText().toString().trim();
                if(old!=null) {
                    if(old.equals(""))
                        old = null;
                }
                if (newp!=null) {
                    if(newp.equals(""))
                        newp = null;
                }
                if(confirmp!=null) {
                    if(confirmp.equals(""))
                        newp = null;
                }

                final String o = old;
                final String n = newp;
                final String c = confirmp;

                if ((old!=null) && (newp!=null) && (confirmp!=null) && !(confirmp.equals(newp))) {
                    Toast.makeText(getContext(), "New Password and Confirm Password donot match!", Toast.LENGTH_LONG).show();
                }else{


                    String URL = "http://10.250.215.206:80/complaint_management/default/changepassword.php";


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
                                        Toast.makeText(getActivity(), response1.getString("message"), Toast.LENGTH_LONG).show();
                                    } else
                                        Toast.makeText(getActivity(), response1.getString("message"), Toast.LENGTH_LONG).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("Content-Type", "application/x-www-form-urlencoded");
                        if(o!=null)
                            params.put("oldpassword", o);
                        if(n!=null)
                            params.put("newpassword", n);
                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                requestQueue.add(jsObjRequest);
                }

            }
        });
        return view;//inflater.inflate(R.layout.frag_add_user_layout,null);
    }
}
