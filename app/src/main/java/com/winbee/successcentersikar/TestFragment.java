package com.winbee.successcentersikar;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.winbee.successcentersikar.RetrofitApiCall.OnlineTestApiClient;
import com.winbee.successcentersikar.Utils.OnlineTestData;
import com.winbee.successcentersikar.WebApi.ClientApi;
import com.winbee.successcentersikar.adapter.DemoTestListAdapter;
import com.winbee.successcentersikar.adapter.FragmentTestListAdapter;
import com.winbee.successcentersikar.model.SIACDetailsDataModel;
import com.winbee.successcentersikar.model.SIACDetailsMainModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.supercharge.shimmerlayout.ShimmerLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestFragment extends Fragment {

    private ShimmerLayout shimmerLayout;
    private RecyclerView recycle_test;
    private Toast toast_msg;
    private TextView txt_no_subject;
    String UserId;
    public TestFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_test, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        shimmerLayout=view.findViewById(R.id.shimmerLayout);
        recycle_test=view.findViewById(R.id.recycle_test);
        txt_no_subject=view.findViewById(R.id.txt_no_subject);
        UserId=SharedPrefManager.getInstance(getContext()).refCode().getUserId();
        if (LocalData.TestBuckedId.equals("")){
            txt_no_subject.setVisibility(View.VISIBLE);
        }else{
            txt_no_subject.setVisibility(View.GONE);
            getTestList();
        }

    }
    private void getTestList() {
        apiCall();
        ClientApi apiClient= OnlineTestApiClient.getClient().create(ClientApi.class);
        Call<SIACDetailsMainModel> call=apiClient.fetchSIACDetails(OnlineTestData.org_code,OnlineTestData.auth_code,LocalData.TestBuckedId,UserId);
        call.enqueue(new Callback<SIACDetailsMainModel>() {
            @Override
            public void onResponse(Call<SIACDetailsMainModel> call, Response<SIACDetailsMainModel> response) {
                apiCalled();
                SIACDetailsMainModel siacDetailsMainModel=response.body();
                if(siacDetailsMainModel!=null){
                    if (siacDetailsMainModel.getMessage().equalsIgnoreCase("true")){
                        List<SIACDetailsDataModel> siacDetailsDataModelList=new ArrayList<>(Arrays.asList(siacDetailsMainModel.getData()));
                        FragmentTestListAdapter testListAdapter=new FragmentTestListAdapter(getContext(),siacDetailsDataModelList);
                        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                        recycle_test.setLayoutManager(layoutManager);
                        recycle_test.setItemAnimator(new DefaultItemAnimator());
                        recycle_test.setAdapter(testListAdapter);
                    }
                    else
                        doToast(siacDetailsMainModel.getMessage());
                }
                else
                    doToast("data null");
            }
            @Override
            public void onFailure(Call<SIACDetailsMainModel> call, Throwable t) {
                doToast(getString(R.string.went_wrong));
                System.out.println("call fail "+t);
                apiCalled();
            }
        });
    }
    private void apiCall() {
        shimmerLayout.setVisibility(View.VISIBLE);
        shimmerLayout.startShimmerAnimation();
    }
    private void apiCalled() {
        shimmerLayout.setVisibility(View.GONE);
        shimmerLayout.stopShimmerAnimation();
    }
    private void doToast(String msg){
        if(toast_msg !=null){
            toast_msg.cancel();
        }
        toast_msg = Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT);
        toast_msg.show();
    }
}