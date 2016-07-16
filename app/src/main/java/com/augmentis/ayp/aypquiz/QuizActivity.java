package com.augmentis.ayp.aypquiz;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {
    private static final int REQUEST_CHEATED = 7628;
    private Button trueButton;
    private Button falseButton;

    private Button nextButton;
    private Button previousButton;
    private Button cheatButton;
    private TextView questionText;

    Question[] questions = new Question[]{
            new Question(R.string.question_1_nine,true),
            new Question(R.string.question_2_rawin, true),
            new Question(R.string.question_3_math, false),
            new Question(R.string.question_4_mar, false),
            new Question(R.string.question_5_who,true),
            new Question(R.string.question_6_city,false)


    };
    int currentIndex;
    private static final String TAG = "AYPQUIZ";
    private static final String INDEX= "INDEX";
    private static final String CHEAT = "CHEAT";
    private boolean isCheater;
    private boolean testt;


    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.d(TAG,"State is saving");
        savedInstanceState.putInt(INDEX, currentIndex);
        savedInstanceState.putBoolean(CHEAT,isCheater);


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent dataIntent) {
        if(resultCode != Activity.RESULT_OK){
            return;
        }
        if(requestCode==REQUEST_CHEATED){
            if(dataIntent == null){
                return;
            }
//            isCheater = dataIntent.getExtras().getBoolean("CHEATED");
            isCheater = CheatActivity.wasCeated(dataIntent);
            questions[currentIndex].setCheated(isCheater);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        trueButton = (Button) findViewById(R.id.true_button);
        falseButton = (Button) findViewById(R.id.false_button);
        nextButton = (Button) findViewById(R.id.next_button);
        previousButton = (Button) findViewById(R.id.previous_button);
        cheatButton =(Button) findViewById(R.id.cheat_button);
        questionText = (TextView) findViewById(R.id.text_question);

        if(savedInstanceState!= null){
           currentIndex = savedInstanceState.getInt(INDEX,0);
            isCheater = savedInstanceState.getBoolean(CHEAT);
        }else{
            resetCheater();
            currentIndex = 0;
        }

        updateQuestion();

        questionText.setText(questions[currentIndex].getQuestionId());
        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(true);
            }
        });
        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(false);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//              if (currentIndex < questions.length -1 ){  // 0 1 2
//                  currentIndex++;
//              }else{
//                  currentIndex=0;
//              }
//                if (currentIndex>=questions.length){
//                    currentIndex=0;
//                }

                currentIndex = ++currentIndex%questions.length;
                resetCheater();
                updateQuestion();
            }
        });
        cheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
//                Intent intent = new Intent(QuizActivity.this, CheatActivity.class);
//                intent.putExtra("NAME",questions[currentIndex].getAnswer());
                Intent intent = CheatActivity.createIntent(QuizActivity.this,getCurrentAnswer());
                startActivityForResult(intent, REQUEST_CHEATED);
            }
        });



        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (currentIndex <= questions.length -1 && currentIndex != 0 ){  // 0 1 2
//                    currentIndex--;
//                }else{
//                    currentIndex=questions.length-1;
//                }
                 currentIndex = --currentIndex%questions.length;
                 if(currentIndex < 0 ) currentIndex +=6;
                resetCheater();
                updateQuestion();

            }
        });
    }
//    private void savecheater(){
//
//    }
    private void resetCheater(){
        isCheater = false;
    }
    public void updateQuestion()
    {
        questionText.setText(questions[currentIndex].getQuestionId());
    }
    public void checkAnswer(boolean answer){
        boolean correctAnswer =  questions[currentIndex].getAnswer();
        int result;
//        int result = (answer == correctAnswer)? R.string.correct_text : R.string.incorrect_text ;
        if(questions[currentIndex].isCheated()){
            result = R.string.cheater_text;
        }else{
            if (answer== correctAnswer){
                result = R.string.correct_text;
            } else{
                result = R.string.incorrect_text;
            }
        }
//        *if(answer== correctAnswer) {
//            result = R.string.correct_text;
//            Toast.makeText(QuizActivity.this, R.string.correct_text,
//                    Toast.LENGTH_SHORT).show();
//        }else{
//            result = R.string.incorrect_text;
//            Toast.makeText(QuizActivity.this, R.string.incorrect_text,
//                    Toast.LENGTH_SHORT).show();
//        }*/
        Toast.makeText(QuizActivity.this,result,Toast.LENGTH_SHORT).show();
    }

    public boolean getCurrentAnswer() {
//        return currentAnswer;
    return  questions[currentIndex].getAnswer();
    }
}
