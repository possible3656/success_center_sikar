package com.winbee.successcentersikar;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.navigation.NavigationView;
import com.winbee.successcentersikar.RetrofitApiCall.ApiClient;
import com.winbee.successcentersikar.Utils.SpinnerAdapter;
import com.winbee.successcentersikar.WebApi.ClientApi;
import com.winbee.successcentersikar.adapter.AllLiveClassAdapter;
import com.winbee.successcentersikar.model.FacultyName;
import com.winbee.successcentersikar.model.LiveClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class YouTubeVideoList extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private ArrayList<LiveClass> liveList;
    private List<FacultyName> List;
    private RecyclerView video_list_recycler;
    private ProgressBarUtil progressBarUtil;
    private SwipeRefreshLayout live_refresh;
    private AllLiveClassAdapter adapter;
    private ImageView img_share,WebsiteHome;
    private RelativeLayout today_classes;
    LinearLayout layout_user,layout_test_series,layout_home,layout_doubt,layout_notification;
    private Spinner select_option,spinner_faculty;
    long item,item_value;
     String value;
    Context context=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_you_tube_video_list);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        progressBarUtil   =  new ProgressBarUtil(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        video_list_recycler = findViewById(R.id.all_liveClasses);
        live_refresh = findViewById(R.id.live_refresh);
        live_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                callLiveApiService();
            }
        });
        today_classes = findViewById(R.id.today_classes);
        select_option=findViewById(R.id.select_option);
        spinner_faculty=findViewById(R.id.spinner_faculty);


        String[] titleArray = getResources ( ).getStringArray ( R.array.filter);
        SpinnerAdapter adapter = new SpinnerAdapter( YouTubeVideoList.this , titleArray);
       // adapter.setDropDownViewResource(R.layout.spinner_item);
        spinner_faculty.setAdapter ( adapter );
        spinner_faculty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 item = spinner_faculty.getItemIdAtPosition(position);
                callFilter();
                System.out.println("Suree: "+item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });





        WebsiteHome = findViewById(R.id.WebsiteHome);
        WebsiteHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.openDrawer(GravityCompat.START);
                //return true;
            }
        });
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View hView =  navigationView.getHeaderView(0);
        TextView nav_user = (TextView)hView.findViewById(R.id.txt_user);
        TextView nav_user_nuber = (TextView)hView.findViewById(R.id.txt_user_number);
        nav_user.setText(SharedPrefManager.getInstance(this).refCode().getName());
        nav_user_nuber.setText(SharedPrefManager.getInstance(this).refCode().getUsername());
        layout_user=findViewById(R.id.layout_user);
        layout_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profile = new Intent(YouTubeVideoList.this,DashboardCourseActivity.class);
                startActivity(profile);
            }
        });
        layout_test_series=findViewById(R.id.layout_test_series);
        layout_test_series.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent test = new Intent(YouTubeVideoList.this,SubjectActivity.class);
                startActivity(test);
                // Toast.makeText(MainActivity.this, "Coming Soon", Toast.LENGTH_SHORT).show();
            }
        });
        layout_home=findViewById(R.id.layout_home);
        layout_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profile = new Intent(YouTubeVideoList.this,MainActivity.class);
                startActivity(profile);
            }
        });
        layout_doubt=findViewById(R.id.layout_doubt);
        layout_doubt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profile = new Intent(YouTubeVideoList.this,DiscussionActivity.class);
                startActivity(profile);
            }
        });
        layout_notification=findViewById(R.id.layout_notification);
        layout_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profile = new Intent(YouTubeVideoList.this,NotificationActivity.class);
                startActivity(profile);
            }
        });
        img_share = findViewById(R.id.img_share);
        img_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Success Center Sikar");
                    String shareMessage= "\nSuccess Center Sikar download the application.\n ";
                    shareMessage = shareMessage + "\nhttps://play.google.com/store/apps/details?id="+getPackageName() ;
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch(Exception e) {
                    //e.toString();
                    //+ BuildConfig.APPLICATION_ID +"\n\n"
                }
            }
        });

        callLiveApiService();
        //callFilter();
    }
    private void callFilter() {
        progressBarUtil.showProgress();
        ClientApi apiCAll = ApiClient.getClient().create(ClientApi.class);
        Call<FacultyName> call = apiCAll.getFaculty(item);
        call.enqueue(new Callback<FacultyName>() {
            @Override
            public void onResponse(Call<FacultyName> call, Response<FacultyName> response) {

                int statusCode = response.code();
                if(statusCode==200){
                    String[] titleArray = new String[response.body().getCategoryData().size()];
                    for (int i=0;i<=response.body().getCategoryData().size()-1;i++) {
                        Log.i("tag", "onResponse: "+response.body().getCategoryData().get(i).getName());
                        titleArray[i]=String.valueOf( response.body().getCategoryData().get(i).getName());
                        SpinnerAdapter adapter = new SpinnerAdapter( YouTubeVideoList.this , titleArray);
                         select_option.setAdapter ( adapter );
                         progressBarUtil.hideProgress();

                        select_option.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                value = select_option.getSelectedItem().toString();
                                //item_value = select_option.getItemIdAtPosition(position);
                                callLiveApiService();
                                System.out.println("Value: "+value);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> arg0) {

                            }
                        });

                        System.out.println("Suree body: " + response.body());
                    }
                }
                else{
                    System.out.println("Suree: response code"+response.message());
                    Toast.makeText(getApplicationContext(),"Ërror due to" + response.message(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FacultyName> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Failed" + t.getMessage(),Toast.LENGTH_SHORT).show();

                System.out.println("Suree: Error "+t.getMessage());
            }
        });
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent home=new Intent(YouTubeVideoList.this,MainActivity.class);
            startActivity(home);
        } else if (id == R.id.nav_profile) {
            Intent profile = new Intent(YouTubeVideoList.this,DashboardCourseActivity.class);
            startActivity(profile);

        } else if (id == R.id.nav_course) {
            Intent course = new Intent(YouTubeVideoList.this,MyPurchasedCourseActivity.class);
            startActivity(course);
        } else if (id == R.id.nav_test_series) {
            Intent test = new Intent(YouTubeVideoList.this,AllPurchasedTestActivity.class);
            startActivity(test);
        } else if (id == R.id.nav_txn) {
           Intent txn = new Intent(YouTubeVideoList.this,MyTransactionActivity.class);
            startActivity(txn);
        } else if (id == R.id.nav_live_class) {
            Intent live = new Intent(YouTubeVideoList.this,YouTubeVideoList.class);
            startActivity(live);
        } else if (id == R.id.nav_pdf) {
            Intent pdf = new Intent(YouTubeVideoList.this,AllPurchasedPdfActivity.class);
            startActivity(pdf);
        } else if (id == R.id.nav_about) {
            Intent intent = new Intent(YouTubeVideoList.this,AboutUsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_rate) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" +getPackageName())));
        } else if (id == R.id.nav_contact) {
            Intent intent = new Intent(YouTubeVideoList.this,ContactUsActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_share) {
            try {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Success Center Sikar");
                String shareMessage= "\nSuccess Center Sikar download the application.\n ";
                shareMessage = shareMessage + "\nhttps://play.google.com/store/apps/details?id="+getPackageName() ;
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                startActivity(Intent.createChooser(shareIntent, "choose one"));
            } catch(Exception e) { }

        } else if (id == R.id.nav_logout) {
            logout();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void callLiveApiService() {
        progressBarUtil.showProgress();
        ClientApi apiCAll = ApiClient.getClient().create(ClientApi.class);
        Call<ArrayList<LiveClass>> call = apiCAll.getLive();
        Log.i("tag", "callLiveApiService: "+ item+""+value);
        call.enqueue(new Callback<ArrayList<LiveClass>>() {
            @Override
            public void onResponse(Call<ArrayList<LiveClass>> call, Response<ArrayList<LiveClass>> response) {
                int statusCode = response.code();
                liveList = new ArrayList();
                if(statusCode==200){
                    if(response.body().size()!=0){
                        today_classes.setVisibility(View.GONE);
                    System.out.println("Suree body: "+response.body());
                    liveList = response.body();
                    adapter = new AllLiveClassAdapter(YouTubeVideoList.this,liveList);
                    video_list_recycler.setAdapter(adapter);
                        live_refresh.setRefreshing(false);
                    progressBarUtil.hideProgress();
                    }
                    else{
                        today_classes.setVisibility(View.VISIBLE);
                        live_refresh.setRefreshing(false);
                        progressBarUtil.hideProgress();
                    }

                }
                else{
                    System.out.println("Suree: response code"+response.message());
                    Toast.makeText(getApplicationContext(),"Ërror due to" + response.message(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<LiveClass>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Failed" + t.getMessage(),Toast.LENGTH_SHORT).show();

                System.out.println("Suree: Error "+t.getMessage());
            }
        });
    }

    private void logout() {
        SharedPrefManager.getInstance(this).logout();
        startActivity(new Intent(this, LoginActivity.class));
        Objects.requireNonNull(this).finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.searchbar, menu);
        MenuItem searchViewItem = menu.findItem(R.id.search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchViewItem);
        searchView.setQueryHint("Search Profiles");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchUserByName(query);
                searchView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchUserByName(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void searchUserByName(String s) {
        ArrayList<LiveClass> filteredList = new ArrayList<>();
        try{
            for(int i=0;i<liveList.size();i++) {
                String faculty = "",subject="";

                if(liveList.get (i).getTeacher()!=null){
                    faculty= liveList.get (i).getTeacher();
                }

                if(faculty.toLowerCase().contains(s.toLowerCase())) {
                    filteredList.add(liveList.get(i));
                }
                if(liveList.get (i).getSubject()!=null){
                    subject= liveList.get (i).getSubject();
                }

                if(subject.toLowerCase().contains(s.toLowerCase())) {
                    filteredList.add(liveList.get(i));
                }
            }

            adapter = new AllLiveClassAdapter (YouTubeVideoList.this, filteredList);
            video_list_recycler.setAdapter (adapter);
            adapter.notifyDataSetChanged();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
