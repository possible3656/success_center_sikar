package com.winbee.successcentersikar.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.winbee.successcentersikar.R;
import com.winbee.successcentersikar.model.SIADDSolutionDataModel;

import java.util.ArrayList;

public class TestSolutionAdapter extends RecyclerView.Adapter<TestSolutionAdapter.ViewHolder> {
    private Context context;
    private ArrayList<SIADDSolutionDataModel> list;

    public TestSolutionAdapter(Context context, ArrayList<SIADDSolutionDataModel> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_test_solution_adapter,parent, false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        //setting data toAd apter List
        holder.text_title.setText(Html.fromHtml(list.get(position).getQuestionTitle()));
        holder.text_discription.setText(Html.fromHtml(list.get(position).getAnswerDetails()));
        holder.editTextOtion1.setText(Html.fromHtml(list.get(position).getOption1()));
        holder.editTextOtion2.setText(Html.fromHtml(list.get(position).getOption2()));
        holder.editTextOtion3.setText(Html.fromHtml(list.get(position).getOption3()));
        holder.editTextOtion4.setText(Html.fromHtml(list.get(position).getOption4()));
        holder.editTextOtion1_correct.setText(Html.fromHtml(list.get(position).getOption1()));
        holder.editTextOtion2_correct.setText(Html.fromHtml(list.get(position).getOption2()));
        holder.editTextOtion3_correct.setText(Html.fromHtml(list.get(position).getOption3()));
        holder.editTextOtion4_correct.setText(Html.fromHtml(list.get(position).getOption4()));
        holder.editTextOtion1_wrong.setText(Html.fromHtml(list.get(position).getOption1()));
        holder.editTextOtion2_wrong.setText(Html.fromHtml(list.get(position).getOption2()));
        holder.editTextOtion3_wrong.setText(Html.fromHtml(list.get(position).getOption3()));
        holder.editTextOtion4_wrong.setText(Html.fromHtml(list.get(position).getOption4()));
        holder.editTextOtion1_unselected.setText(Html.fromHtml(list.get(position).getOption1()));
        holder.editTextOtion2_unselected.setText(Html.fromHtml(list.get(position).getOption2()));
        holder.editTextOtion3_unselected.setText(Html.fromHtml(list.get(position).getOption3()));
        holder.editTextOtion4_unselected.setText(Html.fromHtml(list.get(position).getOption4()));
        if (list.get(position).getIsAnswered().equals(1)){
            if (list.get(position).getUserAnswer().equals(list.get(position).getCorrectAnswer())){
                if (list.get(position).getOption1().equals(list.get(position).getCorrectAnswer())){
                    holder.editTextOtion1.setVisibility(View.GONE);
                    holder.editTextOtion1_correct.setVisibility(View.VISIBLE);
                }else if (list.get(position).getOption2().equals(list.get(position).getCorrectAnswer())){
                    holder.editTextOtion2.setVisibility(View.GONE);
                    holder.editTextOtion2_correct.setVisibility(View.VISIBLE);
                }else if (list.get(position).getOption3().equals(list.get(position).getCorrectAnswer())){
                    holder.editTextOtion3.setVisibility(View.GONE);
                    holder.editTextOtion3_correct.setVisibility(View.VISIBLE);
                }else if (list.get(position).getOption4().equals(list.get(position).getCorrectAnswer())){
                    holder.editTextOtion4.setVisibility(View.GONE);
                    holder.editTextOtion4_correct.setVisibility(View.VISIBLE);
                }

                // option is wrong
            }else if (list.get(position).getUserAnswer()!=(list.get(position).getCorrectAnswer())){
                if (list.get(position).getOption1().equals(list.get(position).getUserAnswer())){
                    if (list.get(position).getOption2().equals(list.get(position).getCorrectAnswer())){
                        holder.editTextOtion2.setVisibility(View.GONE);
                        holder.editTextOtion2_correct.setVisibility(View.VISIBLE);
                    }else if (list.get(position).getOption3().equals(list.get(position).getCorrectAnswer())){
                        holder.editTextOtion3.setVisibility(View.GONE);
                        holder.editTextOtion3_correct.setVisibility(View.VISIBLE);
                    }else if (list.get(position).getOption4().equals(list.get(position).getCorrectAnswer())){
                        holder.editTextOtion4.setVisibility(View.GONE);
                        holder.editTextOtion4_correct.setVisibility(View.VISIBLE);
                    }
                    holder.editTextOtion1.setVisibility(View.GONE);
                    holder.editTextOtion1_correct.setVisibility(View.GONE);
                    holder.editTextOtion1_wrong.setVisibility(View.VISIBLE);
                }else if (list.get(position).getOption2().equals(list.get(position).getUserAnswer())){
                    if (list.get(position).getOption1().equals(list.get(position).getCorrectAnswer())){
                        holder.editTextOtion1.setVisibility(View.GONE);
                        holder.editTextOtion1_correct.setVisibility(View.VISIBLE);
                    }else if (list.get(position).getOption3().equals(list.get(position).getCorrectAnswer())){
                        holder.editTextOtion3.setVisibility(View.GONE);
                        holder.editTextOtion3_correct.setVisibility(View.VISIBLE);
                    }else if (list.get(position).getOption4().equals(list.get(position).getCorrectAnswer())){
                        holder.editTextOtion4.setVisibility(View.GONE);
                        holder.editTextOtion4_correct.setVisibility(View.VISIBLE);
                    }
                    holder.editTextOtion2.setVisibility(View.GONE);
                    holder.editTextOtion2_correct.setVisibility(View.GONE);
                    holder.editTextOtion2_wrong.setVisibility(View.VISIBLE);
                }else if (list.get(position).getOption3().equals(list.get(position).getUserAnswer())){
                    if (list.get(position).getOption1().equals(list.get(position).getCorrectAnswer())){
                        holder.editTextOtion1.setVisibility(View.GONE);
                        holder.editTextOtion1_correct.setVisibility(View.VISIBLE);
                    }else if (list.get(position).getOption2().equals(list.get(position).getCorrectAnswer())){
                        holder.editTextOtion2.setVisibility(View.GONE);
                        holder.editTextOtion2_correct.setVisibility(View.VISIBLE);
                    }else if (list.get(position).getOption4().equals(list.get(position).getCorrectAnswer())){
                        holder.editTextOtion4.setVisibility(View.GONE);
                        holder.editTextOtion4_correct.setVisibility(View.VISIBLE);
                    }
                    holder.editTextOtion3.setVisibility(View.GONE);
                    holder.editTextOtion3_correct.setVisibility(View.GONE);
                    holder.editTextOtion3_wrong.setVisibility(View.VISIBLE);
                }else if (list.get(position).getOption4().equals(list.get(position).getUserAnswer())){
                    if (list.get(position).getOption1().equals(list.get(position).getCorrectAnswer())){
                        holder.editTextOtion1.setVisibility(View.GONE);
                        holder.editTextOtion1_correct.setVisibility(View.VISIBLE);
                    }else if (list.get(position).getOption2().equals(list.get(position).getCorrectAnswer())){
                        holder.editTextOtion2.setVisibility(View.GONE);
                        holder.editTextOtion2_correct.setVisibility(View.VISIBLE);
                    }else if (list.get(position).getOption3().equals(list.get(position).getCorrectAnswer())){
                        holder.editTextOtion3.setVisibility(View.GONE);
                        holder.editTextOtion3_correct.setVisibility(View.VISIBLE);
                    }
                    holder.editTextOtion4.setVisibility(View.GONE);
                    holder.editTextOtion4_correct.setVisibility(View.GONE);
                    holder.editTextOtion4_wrong.setVisibility(View.VISIBLE);
                }


            }
            //if user has not given answer only show the correct answer
        }else if (list.get(position).getIsAnswered().equals(0)){
            if (list.get(position).getOption1().equals(list.get(position).getCorrectAnswer())){
                holder.editTextOtion1.setVisibility(View.GONE);
                holder.editTextOtion1_unselected.setVisibility(View.VISIBLE);
            }else if (list.get(position).getOption2().equals(list.get(position).getCorrectAnswer())){
                holder.editTextOtion2.setVisibility(View.GONE);
                holder.editTextOtion2_unselected.setVisibility(View.VISIBLE);
            }else if (list.get(position).getOption3().equals(list.get(position).getCorrectAnswer())){
                holder.editTextOtion3.setVisibility(View.GONE);
                holder.editTextOtion3_unselected.setVisibility(View.VISIBLE);
            }else if (list.get(position).getOption4().equals(list.get(position).getCorrectAnswer())){
                holder.editTextOtion4.setVisibility(View.GONE);
                holder.editTextOtion4_unselected.setVisibility(View.VISIBLE);
            }
        }

        if (list.get(position).getQuestionTitle_img().endsWith("jpg")){
           holder.img_solution_title.setVisibility(View.VISIBLE);
            Picasso.get().load(list.get(position).getQuestionTitle_img()).placeholder(R.drawable.dummyimage).into(holder.img_solution_title);
        }else {
            holder.img_solution_title.setVisibility(View.GONE);
        }
        if (list.get(position).getOption1_img().endsWith("jpg")){
            holder.img_solution_option1.setVisibility(View.VISIBLE);
            Picasso.get().load(list.get(position).getOption1_img()).placeholder(R.drawable.dummyimage).into(holder.img_solution_option1);
        }else {
            holder.img_solution_option1.setVisibility(View.GONE);
        }

        if (list.get(position).getOption2_img().endsWith("jpg")){
            holder.img_solution_option2.setVisibility(View.VISIBLE);
            Picasso.get().load(list.get(position).getOption2_img()).placeholder(R.drawable.dummyimage).into(holder.img_solution_option2);
        }else {
            holder.img_solution_option2.setVisibility(View.GONE);
        }
        if (list.get(position).getOption3_img().endsWith("jpg")){
            holder.img_solution_option3.setVisibility(View.VISIBLE);
            Picasso.get().load(list.get(position).getOption3_img()).placeholder(R.drawable.dummyimage).into(holder.img_solution_option3);
        }else {
            holder.img_solution_option3.setVisibility(View.GONE);
        }
            if (list.get(position).getOption4_img().endsWith("jpg")){
                        holder.img_solution_option4.setVisibility(View.VISIBLE);
                        Picasso.get().load(list.get(position).getOption4_img()).placeholder(R.drawable.dummyimage).into(holder.img_solution_option4);
                    }else {
                        holder.img_solution_option4.setVisibility(View.GONE);
                    }
         if (list.get(position).getAnswerDetails_img().endsWith("jpg")){
                                holder.img_solution_discription.setVisibility(View.VISIBLE);
                                Picasso.get().load(list.get(position).getAnswerDetails_img()).placeholder(R.drawable.dummyimage).into(holder.img_solution_discription);
                            }else {
                                holder.img_solution_discription.setVisibility(View.GONE);
                            }


    }


    @Override
    public int getItemCount() {
        //We are Checking Here list should not be null if it  will null than we are setting here size = 0
        //else size you are getting my point
        return list==null ? 0 : list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView text_title,editTextOtion1,editTextOtion2,editTextOtion3,editTextOtion4,
                editTextOtion1_correct,editTextOtion2_correct,editTextOtion3_correct,editTextOtion4_correct,
                editTextOtion1_wrong,editTextOtion2_wrong,editTextOtion3_wrong,editTextOtion4_wrong,
                editTextOtion1_unselected,editTextOtion2_unselected,editTextOtion3_unselected,editTextOtion4_unselected,text_discription;
        private RelativeLayout branch_live;
        private ImageView img_solution_title,img_solution_option1,img_solution_option2,img_solution_option3,img_solution_option4,img_solution_discription;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text_title = itemView.findViewById(R.id.text_title);
            editTextOtion1 = itemView.findViewById(R.id.editTextOtion1);
            editTextOtion2 = itemView.findViewById(R.id.editTextOtion2);
            editTextOtion3 = itemView.findViewById(R.id.editTextOtion3);
            editTextOtion4 = itemView.findViewById(R.id.editTextOtion4);
            editTextOtion1_correct = itemView.findViewById(R.id.editTextOtion1_correct);
            editTextOtion2_correct = itemView.findViewById(R.id.editTextOtion2_correct);
            editTextOtion3_correct = itemView.findViewById(R.id.editTextOtion3_correct);
            editTextOtion4_correct = itemView.findViewById(R.id.editTextOtion4_correct);
            editTextOtion1_wrong = itemView.findViewById(R.id.editTextOtion1_wrong);
            editTextOtion2_wrong = itemView.findViewById(R.id.editTextOtion2_wrong);
            editTextOtion3_wrong = itemView.findViewById(R.id.editTextOtion3_wrong);
            editTextOtion4_wrong = itemView.findViewById(R.id.editTextOtion4_wrong);
            editTextOtion1_unselected = itemView.findViewById(R.id.editTextOtion1_unselected);
            editTextOtion2_unselected = itemView.findViewById(R.id.editTextOtion2_unselected);
            editTextOtion3_unselected = itemView.findViewById(R.id.editTextOtion3_unselected);
            editTextOtion4_unselected = itemView.findViewById(R.id.editTextOtion4_unselected);
            text_discription = itemView.findViewById(R.id.text_discription);
            img_solution_title = itemView.findViewById(R.id.img_solution_title);
            img_solution_option1 = itemView.findViewById(R.id.img_solution_option1);
            img_solution_option2 = itemView.findViewById(R.id.img_solution_option2);
            img_solution_option3 = itemView.findViewById(R.id.img_solution_option3);
            img_solution_option4 = itemView.findViewById(R.id.img_solution_option4);
            img_solution_discription = itemView.findViewById(R.id.img_solution_discription);
        }
    }
}

