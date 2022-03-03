package com.ftzp.programminhero.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ftzp.programminhero.R;
import com.ftzp.programminhero.model.ApiClient;
import com.ftzp.programminhero.model.ApiInterface;
import com.ftzp.programminhero.model.Question;
import com.ftzp.programminhero.model.Questions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SecondFragment extends Fragment {
    ProgressBar progressBar;
    ApiInterface apiInterface;
    TextView pointTV, questionTV, questionNumberTV;
    ImageView imageView;
    Button answer1, answer2, answer3, answer4;
    Questions questions;
    int currentpos=0, totalQuestion = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_second, container, false);

        // Network call
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        progressBar = view.findViewById(R.id.progressbar);
        pointTV = view.findViewById(R.id.point);
        questionTV = view.findViewById(R.id.question);
        questionNumberTV = view.findViewById(R.id.question_number);
        imageView = view.findViewById(R.id.question_image);
        answer1 = view.findViewById(R.id.option1);
        answer2 = view.findViewById(R.id.option2);
        answer3 = view.findViewById(R.id.option3);
        answer4 = view.findViewById(R.id.option4);

        Call<Questions> call = apiInterface.questionList();
        call.enqueue(new Callback<Questions>() {
            @Override
            public void onResponse(Call<Questions> call, Response<Questions> response) {
                if(response.isSuccessful() && response.body()!=null){
                    progressBar.setVisibility(View.GONE);
                    Log.d("TAG",response.body().toString());
                     questions = response.body();
                    Log.d("TAG", String.valueOf(questions.getQuestions().size()));
                    totalQuestion = questions.getQuestions().size();
                    setDatatoViews(currentpos, totalQuestion);

                }else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "No response", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Questions> call, Throwable t) {
                Log.d("TAG", t.toString());
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Failure", Toast.LENGTH_SHORT).show();
            }
        });

        answer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextQuestion();
            }
        });

        answer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextQuestion();
            }
        });

        answer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextQuestion();
            }
        });

        answer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextQuestion();
            }
        });


        return view;
    }

    private void setDatatoViews(int position, int totalPosition){

        questionTV.setText(questions.getQuestions().get(position).getQuestion());

        String questionNumber = String.valueOf(position+1) + "/" + String.valueOf(totalPosition);

        questionNumberTV.setText(questionNumber);

        pointTV.setText(String.valueOf(questions.getQuestions().get(position).getScore())+" Point");

        String img_src = questions.getQuestions().get(position).getQuestionImageUrl();
        if(img_src!=null){
            Picasso.get().load(img_src).into(imageView);
        }



        if(questions.getQuestions().get(position).getAnswers().getA()!=null){
            answer1.setText(questions.getQuestions().get(position).getAnswers().getA());
        }else {
            answer1.setVisibility(View.GONE);
        }
        if (questions.getQuestions().get(position).getAnswers().getB() != null){
            answer2.setText(questions.getQuestions().get(position).getAnswers().getB());
        }else {
            answer2.setVisibility(View.GONE);
        }
        if(questions.getQuestions().get(position).getAnswers().getC() != null){
            answer3.setText(questions.getQuestions().get(position).getAnswers().getC());
        }else {
            answer3.setVisibility(View.GONE);
        }
        if(questions.getQuestions().get(position).getAnswers().getD() != null){
            answer4.setText(questions.getQuestions().get(position).getAnswers().getD());
        }else {
            answer4.setVisibility(View.GONE);
        }

    }

    private void nextQuestion(){
        currentpos++;


        if (currentpos < totalQuestion){
            setDatatoViews(currentpos, totalQuestion);
        }else {
            Fragment fragment = new FirstFragment();
            FragmentManager fragmentManager = getParentFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.framelayout, fragment).commit();
        }

    }

}