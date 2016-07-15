package com.augmentis.ayp.aypquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {
    Button trueButton;
    Button falseButton;

    Button nextButton;
    Button previousButton;
    TextView questionText;

    Question[] questions = new Question[]{
            new Question(R.string.question_1_nine,true),
            new Question(R.string.question_2_rawin, true),
            new Question(R.string.question_3_math, true),
            new Question(R.string.question_4_mar, false)
    };
    int currentIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        trueButton = (Button) findViewById(R.id.true_button);
        falseButton = (Button) findViewById(R.id.false_button);
        nextButton = (Button) findViewById(R.id.next_button);
        previousButton = (Button) findViewById(R.id.previous_button);
        questionText = (TextView) findViewById(R.id.text_question);

        currentIndex = 0;
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
              if (currentIndex < questions.length -1 ){  // 0 1 2
                  currentIndex++;
              }else{
                  currentIndex=0;
              }
//                if (currentIndex>=questions.length){
//                    currentIndex=0;
//                }
//                currentIndex1 =++currentIndex1%questions.length;
//                updateQuestion();
                questionText.setText(questions[currentIndex].getQuestionId());
            }
        });
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentIndex <= questions.length -1 && currentIndex != 0 ){  // 0 1 2
                    currentIndex--;
                }else{
                    currentIndex=questions.length-1;
                }
//                currentIndex = --currentIndex%questions.length;
//                if(currentIndex == 0 ) currentIndex +=4;
                questionText.setText(questions[currentIndex].getQuestionId());
            }
        });
    }
//    public void updateQuestion()
//    {
//        questionText.setText(questions[currentIndex1].getQuestionId());
//    }
    public void checkAnswer(boolean answer){
        boolean correctAnswer =  questions[currentIndex].getAnswer();
//        boolean correctAnswer2 = questions[currentIndex2].getAnswer();

        int result = (answer == correctAnswer)? R.string.correct_text : R.string.incorrect_text ;
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
//        int result2 = (answer == correctAnswer2)? R.string.correct_text : R.string.incorrect_text ;
//        Toast.makeText(QuizActivity.this,result,Toast.LENGTH_SHORT).show();
    }

}
