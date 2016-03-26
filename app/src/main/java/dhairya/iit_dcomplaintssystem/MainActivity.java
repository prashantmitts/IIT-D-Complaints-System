package com.example.dhairya.complaintsystem;

//import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    String name;
    int specialuser = 0; // 1 for specialuser
    String userType;
    TextView headerName;
    TextView viewProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        userType = getIntent().getStringExtra("usertype");
        name = getIntent().getStringExtra("name");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(name.equals("admin"))
            specialuser = 2;
        else if(userType.equals("House Secretary")||userType.equals("Hostel Warden")||userType.equals("Dean Academics"))
            specialuser = 1;

        if(specialuser==2) {
            setTitle("Add User");
            Fragment fragment = null;
            Class fragmentClass = null;
            fragmentClass = layout.FragAddUser.class;
            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            // Insert the fragment by replacing any existing fragment
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_main, fragment).commit();
            // Set action bar title
            NavigationView navView = (NavigationView) findViewById(R.id.nav_view);
            Menu m = navView.getMenu();
            m.add("Add User");
            m.getItem(0).setIcon(R.drawable.ic_add_user_black);
            m.add("Change Password");
            m.getItem(1).setIcon(R.drawable.ic_change_password);
            m.add("Logout");
            m.getItem(2).setIcon(R.drawable.ic_logout_black);
        }
        else if (specialuser==1) {
            setTitle("All Complaints");
            Fragment fragment = null;
            Class fragmentClass = null;
            fragmentClass = layout.FragAllComplaints.class;
            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            // Insert the fragment by replacing any existing fragment
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_main, fragment).commit();
            // Set action bar title
            NavigationView navView = (NavigationView) findViewById(R.id.nav_view);
            Menu m = navView.getMenu();
            m.add("Write Complaint");
            m.getItem(0).setIcon(R.drawable.ic_write_black);
            m.add("All Complaints");
            m.getItem(1).setIcon(R.drawable.ic_all_black);
            m.add("Notifications");
            m.getItem(2).setIcon(R.drawable.ic_notifications_black);
            m.add("Add User");
            m.getItem(3).setIcon(R.drawable.ic_add_user_black);
            m.add("Change Password");
            m.getItem(4).setIcon(R.drawable.ic_change_password);
            m.add("Logout");
            m.getItem(5).setIcon(R.drawable.ic_logout_black);
        }
        else{
            setTitle("All Complaints");
            Fragment fragment = null;
            Class fragmentClass = null;
            fragmentClass = layout.FragAllComplaints.class;
            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            // Insert the fragment by replacing any existing fragment
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_main, fragment).commit();
            // Set action bar title
            NavigationView navView = (NavigationView) findViewById(R.id.nav_view);
            Menu m = navView.getMenu();
            m.add("Write Complaint");
            m.getItem(0).setIcon(R.drawable.ic_write_black);
            m.add("All Complaints");
            m.getItem(1).setIcon(R.drawable.ic_all_black);
            m.add("Notifications");
            m.getItem(2).setIcon(R.drawable.ic_notifications_black);
            m.add("Change Password");
            m.getItem(3).setIcon(R.drawable.ic_change_password);
            m.add("Logout");
            m.getItem(4).setIcon(R.drawable.ic_logout_black);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

        }
    }
    public String getMyData() {
        return userType;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        headerName = (TextView)findViewById(R.id.header_name);
        viewProfile = (TextView)findViewById(R.id.view_profile);
//        try{
        headerName.setText(name);
//        }catch(Exception e){
//            Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_LONG).show();
//        }
//        try{
        viewProfile.setText(userType);

//        }
//        catch(Exception e){
//            Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_LONG).show();
//        }
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        if (item.getTitle() == "Logout") {
            //fragmentClass = layout.FragLogout.class;
            //Toast.makeText(MainActivity.this,"Logout", Toast.LENGTH_LONG).show();
            //public void login(View v){
            //  final String usrnm = Username.getText().toString().trim();
            // final String pswd = Password.getText().toString().trim();
            // final String categ = category;
            String URL = "http://10.250.215.206:80/complaint_management/default/logout.php";

            StringRequest jsObjRequest = new StringRequest(Request.Method.POST, URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                        }
                    });
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(jsObjRequest);
        } else {
            Fragment fragment = null;
            Class fragmentClass = null;
            if (item.getTitle() == "Write Complaint") {
                fragmentClass = layout.FragWriteComplaint.class;
                //Toast.makeText(MainActivity.this, "Write Complaint", Toast.LENGTH_LONG).show();
            } else if (item.getTitle() == "All Complaints") {
                fragmentClass = layout.FragAllComplaints.class;
                //Toast.makeText(MainActivity.this, "All Complaints", Toast.LENGTH_LONG).show();
            } else if (item.getTitle() == "Notifications") {
                fragmentClass = layout.FragNotifications.class;
                //Toast.makeText(MainActivity.this,"Notifications", Toast.LENGTH_LONG).show();
            } else if (item.getTitle() == "Change Password") {
                fragmentClass = layout.FragChangePassword.class;
                //Toast.makeText(MainActivity.this,"Notifications", Toast.LENGTH_LONG).show();
            } else if (item.getTitle() == "Add User") {
                fragmentClass = layout.FragAddUser.class;
                //Toast.makeText(MainActivity.this, "Add User", Toast.LENGTH_LONG).show();
            }
            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Insert the fragment by replacing any existing fragment
            if (fragment==null)
                Toast.makeText(this,"Error",Toast.LENGTH_LONG).show();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_main, fragment).commit();

            // Set action bar title
            setTitle(item.getTitle());
        }
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }

}
