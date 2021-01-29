package com.winbee.successcentersikar.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.winbee.successcentersikar.NewModels.QuizFilter;
import com.winbee.successcentersikar.R;
import com.winbee.successcentersikar.RetrofitApiCall.OnlineTestApiClient;
import com.winbee.successcentersikar.Utils.SharedPrefManager;
import com.winbee.successcentersikar.Utils.SpinnerAdapter;
import com.winbee.successcentersikar.WebApi.ClientApi;
import com.winbee.successcentersikar.adapter.QuizTestListAdapter;
import com.winbee.successcentersikar.model.SIACDetailsDataModel;
import com.winbee.successcentersikar.model.SIACDetailsMainModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.supercharge.shimmerlayout.ShimmerLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class QuizFragment extends Fragment {

    private ShimmerLayout shimmerLayout;
    private SwipeRefreshLayout quiz_refresh;
    private RecyclerView recycle_test;
    private Toast toast_msg;
    String UserId;
    String value;
    private Spinner select_quiz;

    public QuizFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        shimmerLayout=view.findViewById(R.id.shimmerLayout);
        recycle_test=view.findViewById(R.id.recycle_test);
        quiz_refresh=view.findViewById(R.id.quiz_refresh);
        select_quiz=view.findViewById(R.id.select_quiz);
        UserId= SharedPrefManager.getInstance(getContext()).refCode().getUserId();
        quiz_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getTestList();
            }
        });
        callFilter();
    }
    private void callFilter() {
        ClientApi apiCAll = OnlineTestApiClient.getClient().create(ClientApi.class);
        Call<ArrayList<QuizFilter>> call = apiCAll.getFaculty();
        call.enqueue(new Callback<ArrayList<QuizFilter>>() {
            @Override
            public void onResponse(Call<ArrayList<QuizFilter>> call, Response<ArrayList<QuizFilter>> response) {
                int statusCode = response.code();
                if(statusCode==200){
                    if (response.body().size()!=0){
                        String[] titleArray = new String[response.body().size()];
                        for (int i = 0; i <= response.body().size() - 1; i++) {
                            Log.i("tag", "onResponse: " + response.body().get(i).getName());
                            titleArray[i] = String.valueOf(response.body().get(i).getName());
                            SpinnerAdapter adapter = new SpinnerAdapter(getContext(), titleArray);
                            select_quiz.setAdapter(adapter);
                            select_quiz.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    value = response.body().get(position).getId();
                                    getTestList();
                                    System.out.println("Value: " + value);
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> arg0) {

                                }
                            });
                            System.out.println("Suree body: " + response.body());
                        }
                    }else{
                        apiCalled();
                        doToast("No Quiz Available");
                    }

                }
                else{
                    System.out.println("Suree: response code"+response.message());
                    Toast.makeText(getContext(),"Ërror due to" + response.message(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<QuizFilter>> call, Throwable t) {
                Toast.makeText(getContext(),"Failed" + t.getMessage(),Toast.LENGTH_SHORT).show();

                System.out.println("Suree: Error "+t.getMessage());
            }
        });
    }
    private void getTestList() {
        apiCall();
        ClientApi apiClient= OnlineTestApiClient.getClient().create(ClientApi.class);
       // Call<SIACDetailsMainModel> call=apiClient.fetchSIACDetails("WB_010","1","B_000_005",UserId);
        Call<SIACDetailsMainModel> call=apiClient.fetchSIACDetails("WB_010","1",value,UserId);
        call.enqueue(new Callback<SIACDetailsMainModel>() {
            @Override
            public void onResponse(Call<SIACDetailsMainModel> call, Response<SIACDetailsMainModel> response) {
                apiCalled();
                SIACDetailsMainModel siacDetailsMainModel=response.body();
                if(siacDetailsMainModel!=null){
                    if (siacDetailsMainModel.getMessage().equalsIgnoreCase("true")){
                        List<SIACDetailsDataModel> siacDetailsDataModelList=new ArrayList<>(Arrays.asList(siacDetailsMainModel.getData()));
                        QuizTestListAdapter testListAdapter=new QuizTestListAdapter(getContext(),siacDetailsDataModelList);
                        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                        recycle_test.setLayoutManager(layoutManager);
                        recycle_test.setItemAnimator(new DefaultItemAnimator());
                        recycle_test.setAdapter(testListAdapter);
                        quiz_refresh.setRefreshing(false);
                    }
                    else
                        doToast(siacDetailsMainModel.getMessage());
                    quiz_refresh.setRefreshing(false);
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
