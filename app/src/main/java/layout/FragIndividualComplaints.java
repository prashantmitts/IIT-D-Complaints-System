package layout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.dhairya.complaintsystem.Complaint;
import com.example.dhairya.complaintsystem.R;
import com.example.dhairya.complaintsystem.recycler_complaints;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dhairya on 29-03-2016.
 */
public class FragIndividualComplaints extends Fragment {
    View view;
    ArrayList<recycler_complaints> comp;
    int dateorder = 0; //-1 for desc,1 for asc,0 for neither
    int voteselected = 0;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.frag_individual_complaints,container,false);
        EditText et = (EditText)view.findViewById(R.id.search);
        et.setOnKeyListener(new View.OnKeyListener()
        {
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    switch (keyCode)
                    {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            search();
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });
        final RecyclerView rvComplaint = (RecyclerView)view.findViewById(R.id.viewIndividualComplaints);
        String URL = "http://10.250.215.206:80/complaint_management/complaint/allcomplaints.php";


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
                                JSONArray jsonArray = response1.getJSONArray("allcomplaints");
                                if(jsonArray.length()==0) {
                                    EditText et = (EditText)view.findViewById(R.id.search);
                                    et.setVisibility(View.GONE);
                                    Button b = (Button)view.findViewById(R.id.dateSort);
                                    b.setVisibility(View.GONE);
                                    b = (Button)view.findViewById(R.id.votesSort);
                                    b.setVisibility(View.GONE);
                                    rvComplaint.setVisibility(View.GONE);
                                    TextView t = (TextView)view.findViewById(R.id.sortByText);
                                    t.setText("No Complaints Available For This Level!");
                                }
                                else{ArrayList<String> titles = new ArrayList<>();
                                ArrayList<String> types = new ArrayList<>();
                                ArrayList<String> createdAts = new ArrayList<>();
                                ArrayList<String> createdBys = new ArrayList<>();
                                ArrayList<Integer> ids = new ArrayList<>();
                                String temp;
                                int x;
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    try{
                                        JSONObject obj = jsonArray.getJSONObject(i);
                                        temp = obj.getString("title");
                                        titles.add(temp);
                                        temp = obj.getString("type");
                                        types.add(temp);
                                        temp = obj.getString("createdat");
                                        createdAts.add(temp);
                                        temp = obj.getString("createdby");
                                        createdBys.add(temp);
                                        x = obj.getInt("complaint_id");
                                        ids.add(x);
                                    }catch (JSONException exe){exe.printStackTrace();}

                                }
                                comp = recycler_complaints.createComplaints(titles, types, createdAts, createdBys, ids);
                                IndividualComplaintsAdapter adapter = new IndividualComplaintsAdapter(comp,getContext());
                                    rvComplaint.setLayoutManager(new LinearLayoutManager(getContext()));
                                    rvComplaint.setAdapter(adapter);
                            } }else
                                Toast.makeText(getActivity(), "Error", Toast.LENGTH_LONG).show();
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
                params.put("level","Individual");
                return params;
            }
        };


        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsObjRequest);

        final Button date = (Button) view.findViewById(R.id.dateSort);
        final Button vote = (Button) view.findViewById(R.id.votesSort);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vote.setBackgroundResource(R.drawable.sort_button);
                voteselected = 0;
                if(dateorder==-1) {
                    date.setBackgroundResource(R.drawable.datesort_asc);
                    dateorder = 1;
                    final RecyclerView rvComplaint = (RecyclerView)view.findViewById(R.id.viewIndividualComplaints);
                    String URL = "http://10.250.215.206:80/complaint_management/complaint/sort.php";


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
                                            JSONArray jsonArray = response1.getJSONArray("allcomplaints");
                                            if(jsonArray.length()==0) {
                                                EditText et = (EditText)view.findViewById(R.id.search);
                                                et.setVisibility(View.GONE);
                                                Button b = (Button)view.findViewById(R.id.dateSort);
                                                b.setVisibility(View.GONE);
                                                b = (Button)view.findViewById(R.id.votesSort);
                                                b.setVisibility(View.GONE);
                                                rvComplaint.setVisibility(View.GONE);
                                                TextView t = (TextView)view.findViewById(R.id.sortByText);
                                                t.setText("No Complaints Available For This Level!");
                                            }
                                            else{ArrayList<String> titles = new ArrayList<>();
                                            ArrayList<String> types = new ArrayList<>();
                                            ArrayList<String> createdAts = new ArrayList<>();
                                            ArrayList<String> createdBys = new ArrayList<>();
                                            ArrayList<Integer> ids = new ArrayList<>();
                                            String temp;
                                            int x;
                                            for (int i = 0; i < jsonArray.length(); i++) {
                                                try{
                                                    JSONObject obj = jsonArray.getJSONObject(i);
                                                    temp = obj.getString("title");
                                                    titles.add(temp);
                                                    temp = obj.getString("type");
                                                    types.add(temp);
                                                    temp = obj.getString("createdat");
                                                    createdAts.add(temp);
                                                    temp = obj.getString("createdby");
                                                    createdBys.add(temp);
                                                    x = obj.getInt("complaint_id");
                                                    ids.add(x);
                                                }catch (JSONException exe){exe.printStackTrace();}

                                            }
                                            comp = recycler_complaints.createComplaints(titles, types, createdAts, createdBys, ids);
                                            IndividualComplaintsAdapter adapter = new IndividualComplaintsAdapter(comp,getContext());
                                                rvComplaint.setLayoutManager(new LinearLayoutManager(getContext()));
                                                rvComplaint.setAdapter(adapter);
                                        } }else
                                            Toast.makeText(getActivity(), "Error", Toast.LENGTH_LONG).show();
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
                            params.put("level","Individual");
                            params.put("sorttype","dateasc");
                            return params;
                        }
                    };


                    RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                    requestQueue.add(jsObjRequest);
                }
                else {
                    date.setBackgroundResource(R.drawable.datesort_desc);
                    dateorder = -1;
                    final RecyclerView rvComplaint = (RecyclerView)view.findViewById(R.id.viewIndividualComplaints);
                    String URL = "http://10.250.215.206:80/complaint_management/complaint/sort.php";


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
                                            JSONArray jsonArray = response1.getJSONArray("allcomplaints");
                                            if(jsonArray.length()==0) {
                                                EditText et = (EditText)view.findViewById(R.id.search);
                                                et.setVisibility(View.GONE);
                                                Button b = (Button)view.findViewById(R.id.dateSort);
                                                b.setVisibility(View.GONE);
                                                b = (Button)view.findViewById(R.id.votesSort);
                                                b.setVisibility(View.GONE);
                                                rvComplaint.setVisibility(View.GONE);
                                                TextView t = (TextView)view.findViewById(R.id.sortByText);
                                                t.setText("No Complaints Available For This Level!");
                                            }
                                            else{ArrayList<String> titles = new ArrayList<>();
                                            ArrayList<String> types = new ArrayList<>();
                                            ArrayList<String> createdAts = new ArrayList<>();
                                            ArrayList<String> createdBys = new ArrayList<>();
                                            ArrayList<Integer> ids = new ArrayList<>();
                                            String temp;
                                            int x;
                                            for (int i = 0; i < jsonArray.length(); i++) {
                                                try{
                                                    JSONObject obj = jsonArray.getJSONObject(i);
                                                    temp = obj.getString("title");
                                                    titles.add(temp);
                                                    temp = obj.getString("type");
                                                    types.add(temp);
                                                    temp = obj.getString("createdat");
                                                    createdAts.add(temp);
                                                    temp = obj.getString("createdby");
                                                    createdBys.add(temp);
                                                    x = obj.getInt("complaint_id");
                                                    ids.add(x);
                                                }catch (JSONException exe){exe.printStackTrace();}

                                            }
                                            comp = recycler_complaints.createComplaints(titles, types, createdAts, createdBys, ids);
                                            IndividualComplaintsAdapter adapter = new IndividualComplaintsAdapter(comp,getContext());
                                                rvComplaint.setLayoutManager(new LinearLayoutManager(getContext()));
                                                rvComplaint.setAdapter(adapter);
                                        }} else
                                            Toast.makeText(getActivity(), "Error", Toast.LENGTH_LONG).show();
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
                            params.put("level","Individual");
                            params.put("sorttype","datedesc");
                            return params;
                        }
                    };


                    RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                    requestQueue.add(jsObjRequest);
                }

            }
        });
        vote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(voteselected==0){

                voteselected = 1;
                date.setBackgroundResource(R.drawable.datesort_none);
                dateorder = 0;
                vote.setBackgroundResource(R.drawable.votes_selected);
                final RecyclerView rvComplaint = (RecyclerView)view.findViewById(R.id.viewIndividualComplaints);
                String URL = "http://10.250.215.206:80/complaint_management/complaint/sort.php";


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
                                        JSONArray jsonArray = response1.getJSONArray("allcomplaints");
                                        if(jsonArray.length()==0) {
                                            EditText et = (EditText)view.findViewById(R.id.search);
                                            et.setVisibility(View.GONE);
                                            Button b = (Button)view.findViewById(R.id.dateSort);
                                            b.setVisibility(View.GONE);
                                            b = (Button)view.findViewById(R.id.votesSort);
                                            b.setVisibility(View.GONE);
                                            rvComplaint.setVisibility(View.GONE);
                                            TextView t = (TextView)view.findViewById(R.id.sortByText);
                                            t.setText("No Complaints Available For This Level!");
                                        }
                                        else{ArrayList<String> titles = new ArrayList<>();
                                        ArrayList<String> types = new ArrayList<>();
                                        ArrayList<String> createdAts = new ArrayList<>();
                                        ArrayList<String> createdBys = new ArrayList<>();
                                        ArrayList<Integer> ids = new ArrayList<>();
                                        String temp;
                                        int x;
                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            try{
                                                JSONObject obj = jsonArray.getJSONObject(i);
                                                temp = obj.getString("title");
                                                titles.add(temp);
                                                temp = obj.getString("type");
                                                types.add(temp);
                                                temp = obj.getString("createdat");
                                                createdAts.add(temp);
                                                temp = obj.getString("createdby");
                                                createdBys.add(temp);
                                                x = obj.getInt("complaint_id");
                                                ids.add(x);
                                            }catch (JSONException exe){exe.printStackTrace();}

                                        }
                                        comp = recycler_complaints.createComplaints(titles, types, createdAts, createdBys, ids);
                                        IndividualComplaintsAdapter adapter = new IndividualComplaintsAdapter(comp,getContext());
                                            rvComplaint.setLayoutManager(new LinearLayoutManager(getContext()));
                                            rvComplaint.setAdapter(adapter);
                                    } }else
                                        Toast.makeText(getActivity(), "Error", Toast.LENGTH_LONG).show();
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
                        params.put("level","Individual");
                        params.put("sorttype","votes");
                        return params;
                    }
                };


                RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                requestQueue.add(jsObjRequest);
            }}
        });
        return view;


    }

    public void search(){
        final String text = ((EditText)view.findViewById(R.id.search)).getText().toString().trim();


        final RecyclerView rvComplaint = (RecyclerView)view.findViewById(R.id.viewIndividualComplaints);
        String URL = "http://10.250.215.206:80/complaint_management/complaint/search.php";


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
                                JSONArray jsonArray = response1.getJSONArray("allcomplaints");
                                if(jsonArray.length()==0) {
                                    EditText et = (EditText)view.findViewById(R.id.search);
                                    et.setVisibility(View.GONE);
                                    Button b = (Button)view.findViewById(R.id.dateSort);
                                    b.setVisibility(View.GONE);
                                    b = (Button)view.findViewById(R.id.votesSort);
                                    b.setVisibility(View.GONE);
                                    rvComplaint.setVisibility(View.GONE);
                                    TextView t = (TextView)view.findViewById(R.id.sortByText);
                                    t.setText("No Complaints Available For This Level!");
                                }
                                else{ArrayList<String> titles = new ArrayList<>();
                                    ArrayList<String> types = new ArrayList<>();
                                    ArrayList<String> createdAts = new ArrayList<>();
                                    ArrayList<String> createdBys = new ArrayList<>();
                                    ArrayList<Integer> ids = new ArrayList<>();
                                    String temp;
                                    int x;
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        try{
                                            JSONObject obj = jsonArray.getJSONObject(i);
                                            temp = obj.getString("title");
                                            titles.add(temp);
                                            temp = obj.getString("type");
                                            types.add(temp);
                                            temp = obj.getString("createdat");
                                            createdAts.add(temp);
                                            temp = obj.getString("createdby");
                                            createdBys.add(temp);
                                            x = obj.getInt("complaint_id");
                                            ids.add(x);
                                        }catch (JSONException exe){exe.printStackTrace();}

                                    }
                                    comp = recycler_complaints.createComplaints(titles, types, createdAts, createdBys, ids);
                                    IndividualComplaintsAdapter adapter = new IndividualComplaintsAdapter(comp,getContext());
                                    rvComplaint.setLayoutManager(new LinearLayoutManager(getContext()));
                                    rvComplaint.setAdapter(adapter);
                                } }else
                                Toast.makeText(getActivity(), "Error", Toast.LENGTH_LONG).show();
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
                params.put("level","Individual");
                params.put("input",text);
                return params;
            }
        };


        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsObjRequest);
    }

    class IndividualComplaintsAdapter extends RecyclerView.Adapter<ViewHolderIndi>
    {
        private ArrayList<recycler_complaints> complaintsArray;
        private LayoutInflater inflater;

        public IndividualComplaintsAdapter(ArrayList<recycler_complaints> allcomplaints, Context context)
        {
            complaintsArray = allcomplaints;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public ViewHolderIndi onCreateViewHolder(ViewGroup parent, int viewType) {
            View complaintView = inflater.inflate(R.layout.item_complaint, parent, false);
            ViewHolderIndi viewHolder = new ViewHolderIndi(complaintView);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolderIndi holder, int position) {
            final recycler_complaints complaint = complaintsArray.get(position);
            TextView textView1 = holder.TitleTextView;
            TextView textView2 = holder.TypeTextView;
            TextView textView3 = holder.CreatedAtTextView;
            TextView textView4 = holder.CreatedByTextView;
            textView1.setText(complaint.title);
            textView2.setText("Type: "+complaint.type);
            textView3.setText(complaint.createdAt);
            textView4.setText(complaint.createdBy);

            textView1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), Complaint.class);
                    intent.putExtra("complaintId", complaint.id);
                    startActivity(intent);
                }
            });
            textView2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(),Complaint.class);
                    intent.putExtra("complaintId",complaint.id);
                    startActivity(intent);
                }
            });
            textView3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(),Complaint.class);
                    intent.putExtra("complaintId",complaint.id);
                    startActivity(intent);
                }
            });
        textView4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(),Complaint.class);
                    intent.putExtra("complaintId",complaint.id);
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return complaintsArray.size();
        }
    }

    class ViewHolderIndi extends RecyclerView.ViewHolder
    {
        public TextView TitleTextView;
        public TextView TypeTextView;
        public TextView CreatedAtTextView;
        public TextView CreatedByTextView;
        public ViewHolderIndi(View itemview)
        {
            super(itemview);
            TitleTextView = (TextView)itemview.findViewById(R.id.complaintTitle);
            TypeTextView = (TextView)itemview.findViewById(R.id.complaintType);
            CreatedAtTextView = (TextView)itemview.findViewById(R.id.createdAt);
            CreatedByTextView = (TextView)itemview.findViewById(R.id.createdby);
        }
    }

}
