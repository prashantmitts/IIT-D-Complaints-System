package layout;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dhairya.complaintsystem.MainActivity;
import com.example.dhairya.complaintsystem.NoDefaultSpinner;
import com.example.dhairya.complaintsystem.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FragWriteComplaint extends android.support.v4.app.Fragment {
    String level = null;
    String title = null;
    String desc = null;
    String type = null;
    String tags = null;

    View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.frag_write_complaint_layout,container,false);
        final NoDefaultSpinner spinner1 = (NoDefaultSpinner)view.findViewById(R.id.level);
        final NoDefaultSpinner spinner2 = (NoDefaultSpinner)view.findViewById(R.id.type);
        //String username = getArguments().getString("usertype");
        String username = ((MainActivity) getActivity()).getMyData();
        int x;
        //x = R.array.level;
        if (username.equals("Normal Student") || username.equals("Mess Secretary") || username.equals("Maintenance Secretary") || username.equals("Sports Secretary") || username.equals("House Secretary") || username.equals("Cultural Secretary")
                || username.equals("BRCA General Secretary") || username.equals("BSA General Secretary") || username.equals("Hostel Warden")) {
            x = R.array.level;
        }
        else
            x = R.array.levelwithouthostel;
        final ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getActivity().getBaseContext(), x, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position) != null)
                    level = parent.getItemAtPosition(position).toString();
                if (level != null) {
                    if (level.equals("Individual")) {
                        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getActivity().getBaseContext(), R.array.IndividualTypes, android.R.layout.simple_spinner_item);
                        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner2.setAdapter(adapter2);
                        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (parent.getItemAtPosition(position) != null)
                                    type = level + " " + parent.getItemAtPosition(position).toString();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    } else if (level.equals("Hostel")) {
                        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getActivity().getBaseContext(), R.array.HostelTypes, android.R.layout.simple_spinner_item);
                        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner2.setAdapter(adapter2);
                        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (parent.getItemAtPosition(position) != null)
                                    type = level + " " + parent.getItemAtPosition(position).toString();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    } else {
                        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getActivity().getBaseContext(), R.array.InstituteTypes, android.R.layout.simple_spinner_item);
                        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner2.setAdapter(adapter2);
                        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (parent.getItemAtPosition(position) != null)
                                    type = level + " " + parent.getItemAtPosition(position).toString();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
//        @Override
//        public void onBackPressed() {
//            FragmentManager fm = getFragmentManager();
//            FragmentTransaction ft = fm.beginTransaction();
//            Fragment fragment = null;
//            Class fragmentClass = null;
//            fragmentClass = layout.FragAllComplaints.class;
//            try {
//                fragment = (Fragment) fragmentClass.newInstance();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            ft.replace(R.id.content_main,fragment);
//            ft.commit();
//        }

        final EditText editText = (EditText)view.findViewById(R.id.tagsEdit);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString();
                if(text.charAt(text.length()-1)==' '){
                    editText.setText(text+"#");
                }
            }
        });

        Button button = (Button)view.findViewById(R.id.writeComplaint);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et = (EditText) view.findViewById(R.id.tit);
                title = et.getText().toString().trim();

                et = (EditText) view.findViewById(R.id.des);
                desc = et.getText().toString().trim();

                et = (EditText) view.findViewById(R.id.tagsEdit);
                tags = et.getText().toString().trim();
                if(tags==null)
                    tags = "";
                else if(tags.charAt(tags.length()-1)=='#')
                    tags = tags.substring(0,tags.length()-2);

                final String ti = title;
                final String ty = type;
                final String d = desc;
                final String l = level;
                final String t = tags;

                String URL = "http://10.250.215.206:80/complaint_management/complaint/submitcomplaint.php";

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
                                        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                                        TextView title = new TextView(getContext());
                                        // You Can Customise your Title here
                                        title.setText("Message");
                                        title.setBackgroundColor(Color.rgb(255, 153, 51));
                                        title.setPadding(10, 15, 15, 10);
                                        title.setGravity(Gravity.CENTER);
                                        title.setTextColor(Color.WHITE);
                                        title.setTextSize(22);
                                        alert.setCustomTitle(title);
                                        //alertDialog.setMessage(message);
                                        alert.setMessage("Complaint filed successfully!");
//                                        TextView messageText = (TextView)alert.findViewById(android.R.id.message);
//                                        messageText.setGravity(Gravity.CENTER);
                                        alert.setNegativeButton("Ok, Thanks!", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                // TODO Auto-generated method stub
                                                //Toast.makeText(getContext(),"OK",Toast.LENGTH_LONG).show();
                                                FragmentManager fm = getFragmentManager();
                                                FragmentTransaction ft = fm.beginTransaction();
                                                Fragment fragment = null;
                                                Class fragmentClass = null;
                                                fragmentClass = layout.FragAllComplaints.class;
                                                try {
                                                    fragment = (Fragment) fragmentClass.newInstance();
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                                ft.replace(R.id.content_main, fragment);
                                                ft.commit();
                                            }
                                        });
                                        alert.setPositiveButton("Write another!", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                // TODO Auto-generated method stub
                                                //Toast.makeText(getContext(),"OK",Toast.LENGTH_LONG).show();
                                                FragmentManager fm = getFragmentManager();
                                                FragmentTransaction ft = fm.beginTransaction();
                                                Fragment fragment = null;
                                                Class fragmentClass = null;
                                                fragmentClass = layout.FragWriteComplaint.class;
                                                try {
                                                    fragment = (Fragment) fragmentClass.newInstance();
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                                ft.replace(R.id.content_main, fragment);
                                                ft.commit();
                                            }
                                        });
                                        alert.show();
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
                        if (ti != null)
                            params.put("title", ti);
                        if (ty != null)
                            params.put("type", ty);
                        if (l != null)
                            params.put("level", l);
                        if (d != null)
                            params.put("description", d);
                        params.put("tags",t);

                        return params;
                    }
                };


                RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                requestQueue.add(jsObjRequest);


            }
        });

        return view;

    }
}