package layout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
import com.example.dhairya.complaintsystem.recycler_threads;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Vector;


public class FragNotifications extends Fragment {
    View view;
    ArrayList<recycler_threads> threadarrays = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.frag_notifications_layout,container,false);

        final RecyclerView rvCourses = (RecyclerView)view.findViewById(R.id.viewThreads);
        String URL = "http://10.250.215.206:80/complaint_management/notifications/getnotifications.php";


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
                                JSONArray jsonArray = response1.getJSONArray("allnotifications");
                                if(jsonArray.length()==0) {

                                    TextView t = (TextView)view.findViewById(R.id.textView);
                                    t.setText("No Notifications!");
                                }
                                else{
                                    TextView t = (TextView)view.findViewById(R.id.textView);
                                    t.setVisibility(View.GONE);
                                    Vector<String> descriptions = new Vector<>();
                                    Vector<String> names = new Vector<>();
                                    Vector<String> times = new Vector<>();
                                    Vector<Integer> ids = new Vector<>();
                                    String temp;
                                    int x;
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        try{
                                            JSONObject obj = jsonArray.getJSONObject(i);
                                            temp = obj.getString("createdby");
                                            names.add(temp);
                                            temp = obj.getString("createdat");
                                            times.add(temp);
                                            temp = names.get(i)+" posted a comment on a complaint posted by you.";
                                            descriptions.add(temp);
                                            x = obj.getInt("complaint_id");
                                            ids.add(x);
                                        }catch (JSONException exe){exe.printStackTrace();}

                                    }
                                    threadarrays = recycler_threads.createThreads(descriptions, times, ids);
                                    NotificationsAdapter adapter = new NotificationsAdapter(threadarrays,getActivity());
                                    rvCourses.setAdapter(adapter);
                                    rvCourses.setLayoutManager(new LinearLayoutManager(getActivity()));
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

        };


        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsObjRequest);


        return view;


    }


    public class NotificationsAdapter extends RecyclerView.Adapter<MyViewHolder>
    {

        private ArrayList<recycler_threads> threadArray;
        private LayoutInflater inflater;

        public NotificationsAdapter(ArrayList<recycler_threads> allthreads, Context context)
        {
            threadArray = allthreads;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View gradeView = inflater.inflate(R.layout.item_notification, parent, false);
            MyViewHolder viewHolder = new MyViewHolder(gradeView);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            final recycler_threads thread = threadArray.get(position);
            TextView textView2 = holder.DescriptionTextView;
            TextView textView3 = holder.CreatedAtTextView;
            LinearLayout lView = holder.LayoutView;


            textView2.setText(thread.description);
            textView3.setText("Created at: " + thread.time);

            final int tId = thread.id;
            textView2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Toast.makeText(Notifications.this,thread.id+thread.description+thread.time,Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getActivity(), Complaint.class);
                    intent.putExtra("complaintId",tId);
                    startActivity(intent);
                }
            });
            textView3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(Notifications.this,thread.id+thread.description+thread.time,Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getActivity(), Complaint.class);
                    intent.putExtra("complaintId", tId);
                    startActivity(intent);
                }
            });

        }

        @Override
        public int getItemCount() {
            return threadArray.size();
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView DescriptionTextView;
        public TextView CreatedAtTextView;
        public LinearLayout LayoutView;

        public MyViewHolder(View itemview)
        {
            super(itemview);
            DescriptionTextView = (TextView)itemview.findViewById(R.id.Description);
            CreatedAtTextView = (TextView)itemview.findViewById(R.id.time);
            LayoutView = (LinearLayout)itemview.findViewById(R.id.layoutThread);


        }
    }

}
