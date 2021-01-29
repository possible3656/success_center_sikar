package com.winbee.successcentersikar.Fragment;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.winbee.successcentersikar.Utils.ProgressBarUtil;
import com.winbee.successcentersikar.R;
import com.winbee.successcentersikar.RetrofitApiCall.ApiClient;
import com.winbee.successcentersikar.Utils.SharedPrefManager;
import com.winbee.successcentersikar.WebApi.ClientApi;
import com.winbee.successcentersikar.adapter.QuizAdapter;
import com.winbee.successcentersikar.model.AskDoubtQuestion;
import com.winbee.successcentersikar.model.NewDoubtQuestion;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class DiscussionFragment extends Fragment {

    EditText editTextQuestionTitle,editTextQuestionDescription;
    private ProgressBarUtil progressBarUtil;
    Button submit;
    private ImageView question_image,img_share;
    private Button btn_asked;
    private QuizAdapter adapter;
    private ArrayList<AskDoubtQuestion> list;
    private RecyclerView askedQuestion;
    private  static final int IMG_REQUEST=777;
    private Bitmap bitmap;



    public DiscussionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_discussion, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        editTextQuestionTitle=view.findViewById(R.id.editTextQuestionTitleFragment);
        editTextQuestionDescription=view.findViewById(R.id.editTextQuestionDescriptionFragment);
        progressBarUtil   =  new ProgressBarUtil(getContext());
        img_share=view.findViewById(R.id.img_share);
        btn_asked=view.findViewById(R.id.btn_asked);
        askedQuestion = view.findViewById(R.id.gec_asked_question_recycle);
        submit=view.findViewById(R.id.buttonSubmit);
        question_image=view.findViewById(R.id.question_image);
        question_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo
                selectImage();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userValidation();
            }
        });
        callAskedQuestionApiService();
    }
    private void userValidation() {
        final String title = editTextQuestionTitle.getText().toString();
        final String description = editTextQuestionDescription.getText().toString();
        final String userid = SharedPrefManager.getInstance(getContext()).refCode().getUserId();

        if (TextUtils.isEmpty(title)) {
            editTextQuestionTitle.setError("Please enter title");
            editTextQuestionTitle.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(description)) {
            editTextQuestionDescription.setError("Please enter description");
            editTextQuestionDescription.requestFocus();
            return;
        }

        NewDoubtQuestion newDoubtQuestion = new NewDoubtQuestion();
        newDoubtQuestion.setTitle(title);
        newDoubtQuestion.setQuestion(description);
        newDoubtQuestion.setUserId(userid);




        callNewAskedQuestionApiService(newDoubtQuestion);

    }

    private void callNewAskedQuestionApiService(NewDoubtQuestion newDoubtQuestion){
        progressBarUtil.showProgress();
        // final UrlNewQuestion urlNewQuestion = new UrlNewQuestion(title,description,documentid,userid);
        ClientApi apiCall = ApiClient.getClient().create(ClientApi.class);
        Call<NewDoubtQuestion> call =apiCall.getQuizQuestionText(newDoubtQuestion.getTitle(),newDoubtQuestion.getQuestion(),newDoubtQuestion.getUserId(),"1");
        Log.d("TAG", "callNewAskedQuestionApiService: "+newDoubtQuestion.getTitle()+""+newDoubtQuestion.getQuestion()+""+newDoubtQuestion.getUserId());
        call.enqueue(new Callback<NewDoubtQuestion>() {
            @Override
            public void onResponse(Call<NewDoubtQuestion> call, Response<NewDoubtQuestion> response) {
                int statusCode = response.code();
                if(statusCode==200 && response.body()!=null){
                    progressBarUtil.hideProgress();
                    //submitAlertDialog();
                    DiscussionFragment discussionFragment = new DiscussionFragment();
                    if (getActivity()!=null){
                        FragmentTransaction ft = getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.containerQuiz,discussionFragment,"QuizFragment");
                        ft.commit();}else{
                        Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();}
                    editTextQuestionTitle.getText().clear();
                    editTextQuestionDescription.getText().clear();
//                    startActivity(new Intent(NewDoubtActivity.this, NewDoubtActivity.class));
//
//                    finish();
                }
                else{
                    System.out.println("Sur: response code"+response.message());
                    Toast.makeText(getContext(),"Ërror due to" + response.message(),Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<NewDoubtQuestion> call, Throwable t) {
                System.out.println("Suree: "+t.getMessage());
                progressBarUtil.hideProgress();
                Toast.makeText(getContext(),"Failed"+t.getMessage() ,Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void submitAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        //Set title
        builder.setTitle(getResources().getString(R.string.app_name));
        builder.setMessage("Your Doubt Has Been Submitted");
        builder.setCancelable(false);

        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.cancel();

            }
        });

        builder.show();
    }

    private void callAskedQuestionApiService(){
        progressBarUtil.showProgress();
        ClientApi apiCall = ApiClient.getClient().create(ClientApi.class);
        Call<ArrayList<AskDoubtQuestion>> call =apiCall.getQuizQuestion();
        call.enqueue(new Callback<ArrayList<AskDoubtQuestion>>() {
            @Override
            public void onResponse(Call<ArrayList<AskDoubtQuestion>> call, Response<ArrayList<AskDoubtQuestion>> response) {

                int statusCode = response.code();
                list = new ArrayList();
                if(statusCode==200){
                    System.out.println("Suree body: "+response.body());
                    list = response.body();
                    adapter = new QuizAdapter(getActivity(),list);
                    askedQuestion.setAdapter(adapter);
                    progressBarUtil.hideProgress();
                }
                else{
                    System.out.println("Suree: response code"+response.message());
                    Toast.makeText(getContext(),"Ërror due to" + response.message(),Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ArrayList<AskDoubtQuestion>> call, Throwable t) {
                System.out.println("Suree: "+t.getMessage());
                progressBarUtil.hideProgress();
                Toast.makeText(getContext(),"Failed"+t.getMessage() ,Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void selectImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMG_REQUEST);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//
        if (requestCode == IMG_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            ContentResolver cr = getActivity().getContentResolver();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(cr, selectedImage);
                final Dialog dialog = new Dialog(getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.select_image_alert);
                TextView txt_cancel=dialog.findViewById(R.id.txt_cancel);
                TextView txt_re_select=dialog.findViewById(R.id.txt_re_select);
                TextView txt_upload=dialog.findViewById(R.id.txt_upload);
                ImageView selected_image= dialog.findViewById(R.id.selected_image);
                selected_image.setImageBitmap(bitmap);
                txt_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                txt_re_select.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        selectImage();
                        dialog.dismiss();
                    }
                });
                txt_upload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //apicall
                        ImageQuestionApi();
                        dialog.dismiss();
                    }
                });

                dialog.show();
                dialog.setCancelable(false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    private String imageToString()
    {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        // In case you want to compress your image, here it's at 40%
        bitmap.compress(Bitmap.CompressFormat.JPEG, 40, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();



        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }
    private void ImageQuestionApi() {
        final String title = editTextQuestionTitle.getText().toString();
        final String description = imageToString();
        final String userid = SharedPrefManager.getInstance(getContext()).refCode().getUserId();

        if (TextUtils.isEmpty(title)) {
            editTextQuestionTitle.setError("Please enter title");
            editTextQuestionTitle.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(description)) {
            editTextQuestionDescription.setError("Please enter description");
            editTextQuestionDescription.requestFocus();
            return;
        }

        NewDoubtQuestion newDoubtQuestion = new NewDoubtQuestion();
        newDoubtQuestion.setTitle(title);
        newDoubtQuestion.setQuestion(description);
        newDoubtQuestion.setUserId(userid);




        callNewAskedQuestionImageApiService(newDoubtQuestion);

    }

    private void callNewAskedQuestionImageApiService(NewDoubtQuestion newDoubtQuestion){
        progressBarUtil.showProgress();
        // final UrlNewQuestion urlNewQuestion = new UrlNewQuestion(title,description,documentid,userid);
        ClientApi apiCall = ApiClient.getClient().create(ClientApi.class);
        Call<NewDoubtQuestion> call =apiCall.getQuizQuestionImage(newDoubtQuestion.getTitle(),newDoubtQuestion.getQuestion(),newDoubtQuestion.getUserId(),"2");
        Log.d("TAG", "callNewAskedQuestionApiService: "+newDoubtQuestion.getTitle()+""+newDoubtQuestion.getQuestion()+""+newDoubtQuestion.getUserId());
        call.enqueue(new Callback<NewDoubtQuestion>() {
            @Override
            public void onResponse(Call<NewDoubtQuestion> call, Response<NewDoubtQuestion> response) {
                int statusCode = response.code();
                if(statusCode==200 && response.body()!=null){
                    progressBarUtil.hideProgress();
                    //  submitAlertDialog();
                    AskFragment mcqFragment = new AskFragment();
                    if (getActivity()!=null){
                        FragmentTransaction ft = getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.containerQuiz,mcqFragment,"QuizFragment");
                        ft.commit();}else{
                        Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();}
                    editTextQuestionTitle.getText().clear();
                    editTextQuestionDescription.getText().clear();
//                    startActivity(new Intent(NewDoubtActivity.this, NewDoubtActivity.class));
//
//                    finish();
                }
                else{
                    System.out.println("Sur: response code"+response.message());
                    Toast.makeText(getContext(),"Ërror due to" + response.message(),Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<NewDoubtQuestion> call, Throwable t) {
                System.out.println("Suree: "+t.getMessage());
                progressBarUtil.hideProgress();
                Toast.makeText(getContext(),"Failed"+t.getMessage() ,Toast.LENGTH_SHORT).show();

            }
        });
    }
}
