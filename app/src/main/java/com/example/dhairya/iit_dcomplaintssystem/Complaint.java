package com.example.dhairya.complaintsystem;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class Complaint extends AppCompatActivity {
    String title,level,type,desc,tags,createdat,resolved,createdby,vote;
    String myusername,myusertype;
    int upv,downv;
    int id;
    ArrayList<recycler_comments> comm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);
        id = getIntent().getIntExtra("complaintId",-1);
        final TextView tv_title = (TextView)findViewById(R.id.cTitle);
        final TextView tv_type = (TextView)findViewById(R.id.cType);
        final TextView tv_desc = (TextView)findViewById(R.id.cDescription);
        final TextView tv_createdat = (TextView)findViewById(R.id.cCreatedAt);
        final TextView tv_resolved = (TextView)findViewById(R.id.resolvedText);
        final TextView tv_upv = (TextView)findViewById(R.id.upvotes);
        final TextView tv_downv = (TextView)findViewById(R.id.downvotes);
        final TextView tv_creator = (TextView)findViewById(R.id.cCreator);
        final ImageButton upv_im = (ImageButton)findViewById(R.id.upVoteImage);
        final ImageButton downv_im = (ImageButton)findViewById(R.id.downVoteImage);
        final CheckBox resolve_box = (CheckBox)findViewById(R.id.resolvedBox);
        String URL = "http://10.250.215.206:80/complaint_management/complaint/getcomplaint.php";

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
                                title = response1.getString("title");
                                level = response1.getString("level");
                                type = response1.getString("type");
                                desc = response1.getString("description");
                                tags = response1.getString("tags");
                                createdat = response1.getString("createdat");
                                resolved = response1.getString("resolved");
                                createdby = response1.getString("createdby");
                                upv = response1.getInt("upvotes");
                                downv = response1.getInt("downvotes");
                                vote = response1.getString("vote");
                                myusername = response1.getString("myusername");
                                myusertype = response1.getString("myusertype");
                                JSONArray commentArray = response1.getJSONArray("allcomments");
                                Vector<String> comments = new Vector<>();
                                Vector<String> commentedat = new Vector<>();
                                Vector<String> commentedby = new Vector<>();
                                String temp;
                                int x;
                                for (int i = 0; i < commentArray.length(); i++) {
                                    try{
                                        JSONObject obj = commentArray.getJSONObject(i);
                                        temp = obj.getString("comment");
                                        comments.add(temp);
                                        temp = obj.getString("commentaddedat");
                                        commentedat.add(temp);
                                        temp = obj.getString("commentby");
                                        commentedby.add(temp);
                                    }catch (JSONException exe){exe.printStackTrace();}

                                }
                                comm = recycler_comments.createComments(commentedby, comments, commentedat);
                                CommentsAdapter adapter = new CommentsAdapter(comm);
                                RecyclerView rvGrades = (RecyclerView)findViewById(R.id.viewComments);
                                rvGrades.setAdapter(adapter);
                                rvGrades.setLayoutManager(new LinearLayoutManager(Complaint.this));
                                tv_title.setText(title);
                                tv_type.setText(type);
                                tv_desc.setText(desc);

                                if(!tags.equals("")){

                                }
                                tv_createdat.setText(createdat);
                                if((level.equals("Hostel")&&myusertype.equals("Hostel Warden"))||!(level.equals("Hostel"))&&myusername.equals(createdby)) {
                                    tv_resolved.setVisibility(View.GONE);
                                    if(resolved.equals("Resolved"))
                                        resolve_box.setChecked(true);
                                    else
                                        resolve_box.setChecked(false);
                                    RelativeLayout.LayoutParams p;
                                    p = (RelativeLayout.LayoutParams)upv_im.getLayoutParams();
                                    p.addRule(RelativeLayout.BELOW, R.id.resolvedBox);
                                    upv_im.setLayoutParams(p);
                                    p = (RelativeLayout.LayoutParams)downv_im.getLayoutParams();
                                    p.addRule(RelativeLayout.BELOW, R.id.resolvedBox);
                                    downv_im.setLayoutParams(p);
                                    p = (RelativeLayout.LayoutParams)tv_upv.getLayoutParams();
                                    p.addRule(RelativeLayout.BELOW, R.id.resolvedBox);
                                    tv_upv.setLayoutParams(p);
                                    p = (RelativeLayout.LayoutParams)tv_downv.getLayoutParams();
                                    p.addRule(RelativeLayout.BELOW, R.id.resolvedBox);
                                    tv_downv.setLayoutParams(p);
                                    p = (RelativeLayout.LayoutParams)tv_createdat.getLayoutParams();
                                    p.addRule(RelativeLayout.BELOW, R.id.resolvedBox);
                                    tv_createdat.setLayoutParams(p);
                                    resolve_box.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if(resolved.equals("Resolved"))
                                                resolved = "Pending";
                                            else resolved = "Resolved";
                                            String URL = "http://10.250.215.206:80/complaint_management/editcomplaint/addresolve.php";

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

                                                                } else
                                                                    Toast.makeText(Complaint.this, response1.getString("message"), Toast.LENGTH_LONG).show();
                                                            } catch (JSONException e) {
                                                                e.printStackTrace();
                                                            }
                                                        }
                                                    },
                                                    new Response.ErrorListener() {
                                                        @Override
                                                        public void onErrorResponse(VolleyError error) {
                                                            Toast.makeText(Complaint.this, error.toString(), Toast.LENGTH_LONG).show();
                                                        }
                                                    }) {
                                                @Override
                                                protected Map<String, String> getParams() {
                                                    Map<String, String> params = new HashMap<String, String>();
                                                    params.put("Content-Type", "application/x-www-form-urlencoded");
                                                    params.put("complaint_id", Integer.toString(id));
                                                    return params;
                                                }
                                            };


                                            RequestQueue requestQueue = Volley.newRequestQueue(Complaint.this);
                                            requestQueue.add(jsObjRequest);

                                        }
                                    });
                                }
                                else {
                                    resolve_box.setVisibility(View.GONE);
                                    tv_resolved.setText(resolved);
                                    if(resolved.equals("Resolved"))
                                        tv_resolved.setTextColor(Color.GREEN);
                                    else
                                        tv_resolved.setTextColor(Color.RED);
                                }
                                tv_upv.setText(Integer.toString(upv));
                                tv_downv.setText(Integer.toString(downv));
                                tv_creator.setText(createdby);
                                int imu = R.drawable.thumbsup_grey;
                                int imd = R.drawable.thumbsdown_grey;
                                if(vote.equals("up"))
                                    imu = R.drawable.thumbsup_blue;
                                else if(vote.equals("down"))
                                    imd = R.drawable.thumbsdown_blue;
                                upv_im.setImageResource(imu);
                                downv_im.setImageResource(imd);
                                upv_im.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (vote.equals("up")) {
                                            upv = upv-1;
                                            tv_upv.setText(Integer.toString(upv));
                                            upv_im.setImageResource(R.drawable.thumbsup_grey);
                                            vote = "none";
                                        } else if (vote.equals("down")) {

                                        } else {
                                            upv = upv+1;
                                            tv_upv.setText(Integer.toString(upv));
                                            upv_im.setImageResource(R.drawable.thumbsup_blue);
                                            vote = "up";
                                        }
                                        if (!vote.equals("down")) {
                                            String URL = "http://10.250.215.206:80/complaint_management/editcomplaint/addvote.php";

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
                                                                }else{
                                                                Toast.makeText(Complaint.this, response1.getString("message"), Toast.LENGTH_LONG).show();
                                                            } }catch (JSONException e) {
                                                                e.printStackTrace();
                                                            }
                                                        }
                                                    },
                                                    new Response.ErrorListener() {
                                                        @Override
                                                        public void onErrorResponse(VolleyError error) {
                                                            Toast.makeText(Complaint.this, error.toString(), Toast.LENGTH_LONG).show();
                                                        }
                                                    }) {
                                                @Override
                                                protected Map<String, String> getParams() {
                                                    Map<String, String> params = new HashMap<String, String>();
                                                    params.put("Content-Type", "application/x-www-form-urlencoded");
                                                    params.put("complaint_id", Integer.toString(id));
                                                    params.put("vote","up");
                                                    return params;
                                                }
                                            };


                                            RequestQueue requestQueue = Volley.newRequestQueue(Complaint.this);
                                            requestQueue.add(jsObjRequest);
                                        }

                                    }
                                });
                                downv_im.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if(vote.equals("down")) {
                                            downv = downv -1;
                                            tv_downv.setText(Integer.toString(downv));
                                            downv_im.setImageResource(R.drawable.thumbsdown_grey);
                                            vote = "none";
                                        }
                                        else if(vote.equals("up")){

                                        }
                                        else {
                                            downv = downv +1;
                                            tv_downv.setText(Integer.toString(downv));
                                            downv_im.setImageResource(R.drawable.thumbsdown_blue);
                                            vote = "down";
                                        }
                                        if(!vote.equals("up")){
                                            String URL = "http://10.250.215.206:80/complaint_management/editcomplaint/addvote.php";

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
                                                                }else{
                                                                Toast.makeText(Complaint.this, response1.getString("message"), Toast.LENGTH_LONG).show();
                                                            } }catch (JSONException e) {
                                                                e.printStackTrace();
                                                            }
                                                        }
                                                    },
                                                    new Response.ErrorListener() {
                                                        @Override
                                                        public void onErrorResponse(VolleyError error) {
                                                            Toast.makeText(Complaint.this, error.toString(), Toast.LENGTH_LONG).show();
                                                        }
                                                    }) {
                                                @Override
                                                protected Map<String, String> getParams() {
                                                    Map<String, String> params = new HashMap<String, String>();
                                                    params.put("Content-Type", "application/x-www-form-urlencoded");
                                                    params.put("complaint_id", Integer.toString(id));
                                                    params.put("vote","down");
                                                    return params;
                                                }
                                            };


                                            RequestQueue requestQueue = Volley.newRequestQueue(Complaint.this);
                                            requestQueue.add(jsObjRequest);
                                        }

                                    }
                                });

                            } else
                                Toast.makeText(Complaint.this, response1.getString("message"), Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Complaint.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                params.put("complaint_id", Integer.toString(id));
                return params;
            }
        };


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsObjRequest);


    }
    public void addComment(View v){
        String str;
        EditText edittext = (EditText) findViewById(R.id.comment1);
        str = edittext.getText().toString();
        if(str!=null){
            if(str.equals(""))
                str = null;
        }
        final String comment = str;
        String URL = "http://10.250.215.206:80/complaint_management/editcomplaint/addcomment.php";

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
                                finish();
                                startActivity(getIntent());
                            } else
                                Toast.makeText(Complaint.this, response1.getString("message"), Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Complaint.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                if(comment!=null)
                    params.put("comment", comment);
                params.put("complaint_id", Integer.toString(id));
                return params;
            }
        };


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsObjRequest);

    }
}
