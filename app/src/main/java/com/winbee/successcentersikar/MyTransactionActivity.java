package com.winbee.successcentersikar;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.winbee.successcentersikar.NewModels.TxnModel;
import com.winbee.successcentersikar.RetrofitApiCall.ApiClient;
import com.winbee.successcentersikar.WebApi.ClientApi;
import com.winbee.successcentersikar.adapter.AllTxnAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyTransactionActivity extends AppCompatActivity {
    private ArrayList<TxnModel> txnModels;
    private RecyclerView txn_recycle;
    private ProgressBarUtil progressBarUtil;
    private AllTxnAdapter adapter;
    String UserID;
    private RelativeLayout today_classes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_transaction);
        progressBarUtil = new ProgressBarUtil(this);
        txn_recycle = findViewById(R.id.all_txn);
        today_classes = findViewById(R.id.today_classes);
        UserID = SharedPrefManager.getInstance(this).refCode().getUserId();
        callCourseApiService();


    }
    private void callCourseApiService() {
        progressBarUtil.showProgress();
        ClientApi apiCAll = ApiClient.getClient().create(ClientApi.class);
        Call<ArrayList<TxnModel>> call = apiCAll.getPaymentData(UserID, "WB_010");
        call.enqueue(new Callback<ArrayList<TxnModel>>() {
            @Override
            public void onResponse(Call<ArrayList<TxnModel>> call, Response<ArrayList<TxnModel>> response) {
                int statusCode = response.code();
                txnModels = new ArrayList();
                if (statusCode == 200) {
                    if (response.body().size() != 0) {
                        txnModels = response.body();
                        System.out.println("Suree body: " + response.body());
                        adapter = new AllTxnAdapter(MyTransactionActivity.this, txnModels);
                        txn_recycle.setAdapter(adapter);
                        progressBarUtil.hideProgress();
                    } else {
                        today_classes.setVisibility(View.VISIBLE);
                        progressBarUtil.hideProgress();
                    }

                } else {
                    System.out.println("Suree: response code" + response.message());
                    Toast.makeText(MyTransactionActivity.this, "Ërror due to" + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<TxnModel>> call, Throwable t) {
                Toast.makeText(MyTransactionActivity.this, "Failed" + t.getMessage(), Toast.LENGTH_SHORT).show();

                System.out.println("Suree: Error " + t.getMessage());
            }
        });
    }
}