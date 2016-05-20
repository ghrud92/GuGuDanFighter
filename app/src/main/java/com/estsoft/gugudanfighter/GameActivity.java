package com.estsoft.gugudanfighter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity implements View.OnClickListener{

    private Timer timer = new Timer();

    private Button[] buttons = new Button[9];
    private TextView leftView;
    private TextView rightView;
    private TextView resultView;

    private List<Integer> numbers = new ArrayList<>();

    private int answer = 0;

    private int correctCount = 0;
    private int totalCount = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // 게임 타이머 시작
        timer.schedule(new GameTimerTask(), 1000, 1000);

        // 게임 버튼 매핑
        buttons[0] = (Button)findViewById(R.id.button1);
        buttons[1] = (Button)findViewById(R.id.button2);
        buttons[2] = (Button)findViewById(R.id.button3);
        buttons[3] = (Button)findViewById(R.id.button4);
        buttons[4] = (Button)findViewById(R.id.button5);
        buttons[5] = (Button)findViewById(R.id.button6);
        buttons[6] = (Button)findViewById(R.id.button7);
        buttons[7] = (Button)findViewById(R.id.button8);
        buttons[8] = (Button)findViewById(R.id.button9);

        // 텍스트 매핑
        leftView = (TextView)findViewById(R.id.textView7);
        rightView = (TextView)findViewById(R.id.textView9);
        resultView = (TextView)findViewById(R.id.textView6);

        // 구구단 값 입력
        for(int i=1;i<=9;i++){
            for(int j=1;j<=9;j++){
                if(!numbers.contains(i*j)){
                    numbers.add(i*j);
                }
            }
        }

        // 게임 시작
        changeQuestion();
        for(int i=0;i<9;i++){
            buttons[i].setOnClickListener(this);
        }

    }

    // 답 선택 후 문제 변경
    private void changeQuestion(){
        Random random = new Random();

        int left = random.nextInt(9) + 1;
        int right = random.nextInt(9) + 1;
        int result = left * right;

        boolean check = false;
        int[] tempNum = new int[9];

        // 보기 섞기
        Collections.shuffle(numbers);

        for(int i=0;i<9;i++){
            tempNum[i] = numbers.get(i);
            if(result == numbers.get(i)){
                answer = i;
                check = true;
            }
        }

        // 목록안에 답이 없다면
        if(!check){
            // 답 번호
            answer = random.nextInt(9);
            tempNum[answer] = result;
        }

        // UI 적용
        leftView.setText(""+left);
        rightView.setText(""+right);
        for(int i=0;i<9;i++){
            buttons[i].setText(""+tempNum[i]);
        }
    }

    // 맞으면 갯수 증가
    private void correct(boolean correct){
        // 정답 채크
        if(correct)
            correctCount++;
        totalCount++;

        // UI 적용
        resultView.setText(correctCount + "/" + totalCount);
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        switch(viewId){
            case R.id.button1: {
                correct(answer == 0);
                changeQuestion();
                break;
            }
            case R.id.button2: {
                correct(answer == 1);
                changeQuestion();
                break;
            }
            case R.id.button3: {
                correct(answer == 2);
                changeQuestion();
                break;
            }
            case R.id.button4: {
                correct(answer == 3);
                changeQuestion();
                break;
            }
            case R.id.button5: {
                correct(answer == 4);
                changeQuestion();
                break;
            }
            case R.id.button6: {
                correct(answer == 5);
                changeQuestion();
                break;
            }
            case R.id.button7: {
                correct(answer == 6);
                changeQuestion();
                break;
            }
            case R.id.button8: {
                correct(answer == 7);
                changeQuestion();
                break;
            }
            case R.id.button9: {
                correct(answer == 8);
                changeQuestion();
                break;
            }
            default:{
                changeQuestion();
                break;
            }
        }
    }

    // 게임 타이머 클래스
    private class GameTimerTask extends TimerTask{

        private Integer seconds = 30;

        @Override
        public void run() {
            if(seconds-- <= 1 ){
                timer.cancel();
                Intent intent = new Intent(GameActivity.this, ResultActivity.class);
                intent.putExtra("result", correctCount + "/" + totalCount);
                startActivity(intent);
                finish();
                return;
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    TextView textView = (TextView)findViewById(R.id.textView3);
                    textView.setText("" + seconds);
                }
            });
        }
    }
}
