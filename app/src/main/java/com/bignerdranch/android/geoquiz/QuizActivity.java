package com.bignerdranch.android.geoquiz;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {
    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";

    private int count;
    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private TextView mQuestionTextView;
    private Button mPrevButton;

    private Question[] mQuestionBank = new Question[]{
            new Question(R.string.question_australia, true),
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true),
    };

    private int mCurrentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        if(savedInstanceState != null){
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX,0);
        }

        Log.d(TAG,"+++onCreate");

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        mQuestionTextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
            }
        });

        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);

            }
        });

        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

        mNextButton = (Button) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTrueButton.setEnabled(true);
                mFalseButton.setEnabled(true);
                //mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                mCurrentIndex = (mCurrentIndex + 1);
                if(mCurrentIndex > mQuestionBank.length-1){
                    mCurrentIndex = mQuestionBank.length-1;
                    return;
                }
                updateQuestion();
            }
        });

        mPrevButton = (Button) findViewById(R.id.prev_button);
        mPrevButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex - 1);
                if(mCurrentIndex < 0){
                    mCurrentIndex = 0;
                }

                updateQuestion();
            }
        });

        updateQuestion();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "+++ON START ++");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "+++ON RESUME +");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "+++ON PAUSE -");
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG,"onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);

    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "+++ON STOP --");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "+++ON DESTROY -");
    }



    private void updateQuestion() {
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue) {

        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();

        int messageResId = 0;

        if (userPressedTrue == answerIsTrue) {
            messageResId = R.string.correct_toast;
            count++;
        } else {
            messageResId = R.string.incorrect_toast;
        }

        mTrueButton.setEnabled(false);
        mFalseButton.setEnabled(false);
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();

        double results = Math.floor((double)count/mQuestionBank.length * 100);

        if(mCurrentIndex == mQuestionBank.length-1){

            Toast.makeText(this,"Your score: " + results,Toast.LENGTH_LONG).show();
        }



    }
}
