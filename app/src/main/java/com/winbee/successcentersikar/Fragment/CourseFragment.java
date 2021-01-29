package com.winbee.successcentersikar.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.winbee.successcentersikar.activity.LoginActivity;
import com.winbee.successcentersikar.NewModels.CourseContent;
import com.winbee.successcentersikar.NewModels.CourseContentArray;
import com.winbee.successcentersikar.NewModels.LogOut;
import com.winbee.successcentersikar.Utils.ProgressBarUtil;
import com.winbee.successcentersikar.R;
import com.winbee.successcentersikar.RetrofitApiCall.ApiClient;
import com.winbee.successcentersikar.Utils.SharedPrefManager;
import com.winbee.successcentersikar.WebApi.ClientApi;
import com.winbee.successcentersikar.adapter.AllPurchaseCourseFragmentAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CourseFragment extends Fragment {
    String UserId,android_id,UserMobile,UserPassword;
    private ArrayList<CourseContentArray> list1;
    RecyclerView all_study_material;
    private ProgressBarUtil progressBarUtil;
    private AllPurchaseCourseFragmentAdapter allPurchaseCourseAdapter;


    public CourseFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_course, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        progressBarUtil = new ProgressBarUtil(getContext());
        all_study_material = view.findViewById(R.id.all_study_material);
        UserId= SharedPrefManager.getInstance(getContext()).refCode().getUserId();
        android_id = Settings.Secure.getString(getContext().getContentResolver(),Settings.Secure.ANDROID_ID);
        UserMobile=SharedPrefManager.getInstance(getContext()).refCode().getUsername();
        UserPassword=SharedPrefManager.getInstance(getContext()).refCode().getPassword();
        callApiService();
    }
    private void callApiService() {
        progressBarUtil.showProgress();
        ClientApi apiCAll = ApiClient.getClient().create(ClientApi.class);
        Call<CourseContent> call = apiCAll.getBranchId(1,"WB_010","WB_010",UserId,android_id);
        call.enqueue(new Callback<CourseContent>() {
            @Override
            public void onResponse(Call<CourseContent> call, Response<CourseContent> response) {
                CourseContent courseContent=response.body();
                int statusCode = response.code();
                list1 = new ArrayList();
                if(statusCode==200){
                    if (response.body().getError()== false) {
                        System.out.println("Suree body: " + response.body());
                        list1 = new ArrayList<>(Arrays.asList(Objects.requireNonNull(courseContent).getData()));
                        allPurchaseCourseAdapter = new AllPurchaseCourseFragmentAdapter(getContext(), list1);
                        all_study_material.setAdapter(allPurchaseCourseAdapter);
                        progressBarUtil.hideProgress();
                    }else{
                        android.app.AlertDialog.Builder alertDialogBuilder =
                                new android.app.AlertDialog.Builder(getContext());
                        alertDialogBuilder.setTitle("Alert");
                        alertDialogBuilder
                                .setMessage(response.body().getError_Message())
                                .setCancelable(false)
                                .setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        forceLogout();
                                    }
                                });

                        android.app.AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    }
                }
                else{
                    progressBarUtil.hideProgress();
                    System.out.println("Suree: response code"+response.message());
                    Toast.makeText(getContext(),"NetWork Issue,Please Check Network Connection" ,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CourseContent> call, Throwable t) {
                Toast.makeText(getContext(),"Failed" + t.getMessage(),Toast.LENGTH_SHORT).show();
                progressBarUtil.hideProgress();
                System.out.println("Suree: Error "+t.getMessage());
            }
        });
    }


    private void logout() {

        progressBarUtil.showProgress();
        ClientApi mService = ApiClient.getClient().create(ClientApi.class);
        Call<LogOut> call = mService.refCodeLogout(3, UserMobile, UserPassword, "SCS001",android_id);
        call.enqueue(new Callback<LogOut>() {
            @Override
            public void onResponse(Call<LogOut> call, Response<LogOut> response) {
                int statusCode = response.code();
                if (statusCode == 200 && response.body().getLoginStatus()!=false) {
                    if (response.body().getError()==false){
                        progressBarUtil.hideProgress();
                        SharedPrefManager.getInstance(getContext()).logout();
                        startActivity(new Intent(getContext(), LoginActivity.class));
                    }else{
                        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(
                                getContext());
                        alertDialogBuilder.setTitle("Alert");
                        alertDialogBuilder
                                .setMessage(response.body().getError_Message())
                                .setCancelable(false)
                                .setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        forceLogout();
                                    }
                                });

                        android.app.AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();

                    }


                } else {
                    progressBarUtil.hideProgress();
                    Toast.makeText(getContext(), response.body().getMessageFailure(), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<LogOut> call, Throwable t) {
                Toast.makeText(getContext(), "Failed" + t.getMessage(), Toast.LENGTH_LONG).show();
                System.out.println(t.getLocalizedMessage());
            }
        });
    }
    private void forceLogout() {
        SharedPrefManager.getInstance(getContext()).logout();
        startActivity(new Intent(getContext(), LoginActivity.class));
        //Objects.requireNonNull(this).finish();
    }
}