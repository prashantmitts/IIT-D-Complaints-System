package layout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dhairya.complaintsystem.NoDefaultSpinner;
import com.example.dhairya.complaintsystem.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FragAddUser extends android.support.v4.app.Fragment {

    String name = null;
    String username = null;
    String entryno = null;
    String hostel = null;
    String logintype = null;
    String usertype = null;
    View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.frag_add_user_layout, container,false);
        final NoDefaultSpinner spinner = (NoDefaultSpinner) view.findViewById(R.id.category);
        final NoDefaultSpinner spinner1 = (NoDefaultSpinner) view.findViewById(R.id.usertype);
        final NoDefaultSpinner spinner2 = (NoDefaultSpinner) view.findViewById(R.id.hostel);
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(), R.array.spinner_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position) != null)
                    logintype = parent.getItemAtPosition(position).toString();
                    if(logintype!=(null)){
                        if(logintype.equals("Admin")) {
                            ArrayAdapter<CharSequence> adapter_temp1 = ArrayAdapter.createFromResource(getActivity().getBaseContext(),R.array.emptyArray,android.R.layout.simple_spinner_item);
                            adapter_temp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner2.setAdapter(adapter_temp1);
                            ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getActivity().getBaseContext(), R.array.admin, android.R.layout.simple_spinner_item);
                            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner1.setAdapter(adapter1);
                            spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    if (parent.getItemAtPosition(position) != null)

                                    if(usertype!=(null)) {
                                        ArrayAdapter<CharSequence> adapter_temp1 = ArrayAdapter.createFromResource(getActivity().getBaseContext(),R.array.emptyArray,android.R.layout.simple_spinner_item);
                                        adapter_temp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                        spinner2.setAdapter(adapter_temp1);
                                        if (!(usertype.equals("Dean Academics") || usertype.equals("Other Faculty") || usertype.equals("Electrician") || usertype.equals("Plumber") || usertype.equals("Security"))) {
                                            ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getActivity(), R.array.hostel, android.R.layout.simple_spinner_item);
                                            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                            spinner2.setAdapter(adapter2);
                                            spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                @Override
                                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                    if (parent.getItemAtPosition(position) != null)
                                                        hostel = parent.getItemAtPosition(position).toString();


                                                }

                                                @Override
                                                public void onNothingSelected(AdapterView<?> parent) {
                                                }
                                            });
                                        }
                                    }
                                    usertype = "Normal Student";


                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {
                                }
                            });
                        }
                        else if(logintype.equals("User")) {
                            ArrayAdapter<CharSequence> adapter_temp2 = ArrayAdapter.createFromResource(getActivity().getBaseContext(),R.array.emptyArray,android.R.layout.simple_spinner_item);
                            adapter_temp2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner2.setAdapter(adapter_temp2);
                            ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getActivity().getBaseContext(), R.array.user, android.R.layout.simple_spinner_item);
                            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner1.setAdapter(adapter1);
                            spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    if (parent.getItemAtPosition(position) != null) {
                                        usertype = parent.getItemAtPosition(position).toString();
                                        ArrayAdapter<CharSequence> adapter_temp3 = ArrayAdapter.createFromResource(getActivity().getBaseContext(),R.array.emptyArray,android.R.layout.simple_spinner_item);
                                        adapter_temp3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                        spinner2.setAdapter(adapter_temp3);
                                    }
                                        if(usertype!=(null)) {
                                            if (!(usertype.equals("Dean Academics") || usertype.equals("Other Faculty") || usertype.equals("Electrician") || usertype.equals("Plumber") || usertype.equals("Security"))) {
                                                ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getActivity(), R.array.hostel, android.R.layout.simple_spinner_item);
                                                adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                spinner2.setAdapter(adapter2);
                                                spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                    @Override
                                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                        if (parent.getItemAtPosition(position) != null)
                                                            hostel = parent.getItemAtPosition(position).toString();

                                                    }

                                                    @Override
                                                    public void onNothingSelected(AdapterView<?> parent) {
                                                    }
                                                });
                                            }
                                        }
                                        if(usertype.equals("Other Student"))
                                            usertype = "Normal Student";
                                        else if(usertype.equals("Other Faculty"))
                                            usertype = "Normal Faculty";
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {
                                }
                            });

                        }}
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Button button = (Button) view.findViewById(R.id.submitButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = (EditText) view.findViewById(R.id.usernameEdit);
                username = editText.getText().toString().trim();
                if(username!=null) {
                    if(username.equals(""))
                        username = null;
                }

                editText = (EditText) view.findViewById(R.id.nameEdit);
                name = editText.getText().toString().trim();
                if (name!=null) {
                    if(name.equals(""))
                        name = null;
                }


                editText = (EditText) view.findViewById(R.id.entryNumberEdit);
                entryno = editText.getText().toString().trim();
                if(entryno!=null) {
                    if(entryno.equals(""))
                        entryno = null;
                }

                final String n = name;
                final String u = username;
                final String p = username;
                final String e = entryno;
                final String h = hostel;
                final String ut = usertype;
                final String l = logintype;

                String URL = "http://10.250.215.206:80/complaint_management/default/adduser.php";

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
                                        TextView tv = (TextView)view.findViewById(R.id.name);
                                        tv.setText(response1.getString("message").toUpperCase()+"!");
                                        tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                        RelativeLayout.LayoutParams layoutparams = (RelativeLayout.LayoutParams)tv.getLayoutParams();
                                        layoutparams.addRule(RelativeLayout.CENTER_HORIZONTAL);
                                        tv.setLayoutParams(layoutparams);
                                        EditText et = (EditText)view.findViewById(R.id.nameEdit);
                                        et.setVisibility(View.GONE);
                                        tv = (TextView)view.findViewById(R.id.entryNumber);
                                        tv.setVisibility(View.GONE);
                                        et = (EditText)view.findViewById(R.id.entryNumberEdit);
                                        et.setVisibility(View.GONE);
                                        tv = (TextView)view.findViewById(R.id.username);
                                        tv.setVisibility(View.GONE);
                                        et = (EditText)view.findViewById(R.id.usernameEdit);
                                        et.setVisibility(View.GONE);
                                        NoDefaultSpinner spinner = (NoDefaultSpinner)view.findViewById(R.id.category);
                                        spinner.setVisibility(View.GONE);
                                        spinner = (NoDefaultSpinner)view.findViewById(R.id.usertype);
                                        spinner.setVisibility(View.GONE);
                                        spinner = (NoDefaultSpinner)view.findViewById(R.id.hostel);
                                        spinner.setVisibility(View.GONE);
                                        tv = (TextView)view.findViewById(R.id.asterisk);
                                        tv.setVisibility(View.GONE);
                                        Button button = (Button)view.findViewById(R.id.submitButton);
                                        button.setVisibility(View.GONE);
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
                        if(u!=null)
                            params.put("username", u);
                        if(p!=null)
                            params.put("password", p);
                        if(l!=null)
                            params.put("logintype", l);
                        if(ut!=null)
                            params.put("usertype",ut);
                        if(h!=null)
                            params.put("hostel",h);
                        if(n!=null)
                            params.put("name",n);
                        if(e!=null)
                            params.put("entryno",e);
                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                requestQueue.add(jsObjRequest);
            }
        });
                return view;//inflater.inflate(R.layout.frag_add_user_layout,null);
        }

}
