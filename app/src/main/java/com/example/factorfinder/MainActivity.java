package com.example.factorfinder;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.text.Editable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    static String random_option1;
    static String random_option2;
    static String random_option3;

    public static final String STREAK = "streakKey";

    static String divisor;

    public int counter = 10;

    static int streak = 0;

    static int int_winning_streak;

    int win1  ;

    static String show_correct = "The correct answer is : ";

    CountDownTimer timer1 = null;

    boolean check = false;

    SharedPreferences sh;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RadioGroup opt = (RadioGroup) findViewById(R.id.options);
        TextView longest_streak = (TextView) findViewById(R.id.longest_winning_streak) ;


        opt.setVisibility(View.INVISIBLE);

        sh = getSharedPreferences("longest_streak" , Context.MODE_PRIVATE) ;

        int win2 = sh.getInt(STREAK , 0) ;
        String s  = "LONGEST STREAK: " + win2 ;
        longest_streak.setText(s);



    }

    public void onClick(View view) {

        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);

        EditText num1 = findViewById(R.id.edit_text);
        TextView tv = findViewById(R.id.tvtextview);
        final TextView countdown = findViewById(R.id.countdown_timer);
        final TextView correct_answer_display1 = (TextView) findViewById(R.id.correct_answer_display1);
        final TextView correct_answer_display2 = (TextView) findViewById(R.id.correct_answer_display2);
        final TextView ans = (TextView) findViewById(R.id.answer);


        Button submit = findViewById(R.id.submit_button);
        final Button reset = findViewById(R.id.reset);
        final RadioGroup options = findViewById(R.id.options);
        final RadioButton option1 = findViewById(R.id.option1);
        final RadioButton option2 = findViewById(R.id.option2);
        final RadioButton option3 = findViewById(R.id.option3);

   reset.setClickable(false);


        Editable number = num1.getText();


        final String str = num1.getText().toString();

        if (str.equals("")){
            Toast.makeText(this, "Enter a no. greater than 4", Toast.LENGTH_SHORT).show();
            return;
        }


        int num = Integer.parseInt(str, 10);

        if (num < 4) {
            Toast.makeText(this, "Enter a no. greater than 4", Toast.LENGTH_SHORT).show();
            return;
        }

        submit.setClickable(false);

        tv.setText(number);
        options.setVisibility(View.VISIBLE) ;


        option1.setClickable(true);
        option2.setClickable(true);
        option3.setClickable(true);

        ArrayList<Integer> divisors = new ArrayList<Integer>();
        ArrayList<Integer> nondivisors = new ArrayList<Integer>();


        for (int i = 2; i <= num; i++) {
            if (num % i == 0)
                divisors.add(i);
            else
                nondivisors.add(i);
        }

        MainActivity obj1 = new MainActivity();
        MainActivity obj = new MainActivity();
        MainActivity obj2 = new MainActivity();

        int div;
        div = obj1.getRandomElement_divisors(divisors);
        int nondiv1;
        nondiv1 = obj.getRandomElement_nondivisors(nondivisors);
        int nondiv2;
        nondiv2 = obj2.getRandomElement_nondivisors(nondivisors);

        for (int i = 0; i < 1; ) {
            if (nondiv1 == nondiv2) {
                nondiv1 = obj.getRandomElement_nondivisors(nondivisors);
                nondiv2 = obj2.getRandomElement_nondivisors(nondivisors);
            } else {
                break;
            }
        }


        divisor = Integer.toString(div, 10);
        String non_divisor1 = Integer.toString(nondiv1, 10);
        String non_divisor2 = Integer.toString(nondiv2, 10);


        ArrayList<String> random_options = new ArrayList<>();
        random_options.add(divisor);
        random_options.add(non_divisor1);
        random_options.add(non_divisor2);

        MainActivity rand_obj = new MainActivity();
        random_option1 = rand_obj.getRandomElement_random_options(random_options);


        random_options.remove(random_option1);

        MainActivity rand_opt = new MainActivity();
        random_option2 = rand_opt.getRandomElement_random_options(random_options);

        random_options.remove(random_option2);

        random_option3 = random_options.get(0);

        option1.setText(random_option1);
        option2.setText(random_option2);
        option3.setText(random_option3);

        final String timeup = "Time's up";

        final boolean checked1 = option1.isChecked();
        final boolean checked2 = option2.isChecked();
        final boolean checked3 = option3.isChecked();


        timer1 = new CountDownTimer(10000, 1000) {
            int counter = 10;

            public void onTick(long millisUntilFinished) {
                countdown.setText(String.valueOf(counter));
                counter--;

                if (check == true) {
                    timer1.cancel();
                }
            }

            public void onFinish() {
                countdown.setText(String.valueOf(counter));
                ans.setText(timeup);
                correct_answer_display1.setText(show_correct);
                correct_answer_display2.setText(divisor);
                streak = 0;
                reset.setClickable(true);
                option1.setClickable(false);
                option2.setClickable(false);
                option3.setClickable(false);
            }
        }.start();
    }

    public int getRandomElement_divisors(List<Integer> divisors) {
        Random rand = new Random();
        return divisors.get(rand.nextInt(divisors.size()));
    }

    public int getRandomElement_nondivisors(List<Integer> nondivisors) {
        Random rand_nondiv1 = new Random();
        return nondivisors.get(rand_nondiv1.nextInt(nondivisors.size()));
    }

    public String getRandomElement_random_options(List<String> random_options) {
        Random rand_randopt = new Random();
        return random_options.get(rand_randopt.nextInt(random_options.size()));
    }


    public void onRadioButtonClicked(View view) {
        TextView ans = (TextView) findViewById(R.id.answer);
        TextView correct_answer_display1 = (TextView) findViewById(R.id.correct_answer_display1);
        TextView correct_answer_display2 = (TextView) findViewById(R.id.correct_answer_display2);
        TextView streak_value = (TextView) findViewById(R.id.streak_value);
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        final View parent = findViewById(R.id.parent);
        Button reset = findViewById(R.id.reset);
        check = true;


        long[] pattern = {0, 100, 1000};

        reset.setClickable(true);



        String correct = "YOUR ANSWER IS CORRECT :)";
        String wrong = "your answer is wrong :(";

        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.option1:
                if (checked)
                    if (random_option1.equals(divisor)) {
                        ans.setText(correct);
                        streak = streak + 1;
                        parent.setBackgroundColor(Color.parseColor("#006600"));
                        break;
                    } else {
                        ans.setText(wrong);
                        correct_answer_display1.setText(show_correct);
                        correct_answer_display2.setText(divisor);
                        streak = 0;
                        v.vibrate(pattern, -1);
                        parent.setBackgroundColor(Color.parseColor("#660000"));
                        break;
                    }
            case R.id.option2:
                if (checked)
                    if (random_option2.equals(divisor)) {
                        ans.setText(correct);
                        streak = streak + 1;
                        parent.setBackgroundColor(Color.parseColor("#006600"));
                        break;
                    } else {
                        ans.setText(wrong);
                        correct_answer_display1.setText(show_correct);
                        correct_answer_display2.setText(divisor);
                        streak = 0;
                        v.vibrate(pattern, -1);
                        parent.setBackgroundColor(Color.parseColor("#660000"));
                        break;
                    }

            case R.id.option3:
                if (checked)
                    if (random_option3.equals(divisor)) {
                        ans.setText(correct);
                        streak = streak + 1;
                        parent.setBackgroundColor(Color.parseColor("#006600"));
                        break;
                    } else {
                        ans.setText(wrong);
                        correct_answer_display1.setText(show_correct);
                        correct_answer_display2.setText(divisor);
                        streak = 0;
                        v.vibrate(pattern, -1);
                        parent.setBackgroundColor(Color.parseColor("#660000"));
                        break;
                    }


        }
        String string_streak = Integer.toString(streak, 10);
        streak_value.setText(string_streak);
        streak = Integer.parseInt(string_streak, 10);
        MainActivity object = new MainActivity();

        new CountDownTimer(500, 10000) {
            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {
                parent.setBackgroundColor(Color.parseColor("#ffffff"));

            }
        }.start();

        RadioButton option1 = findViewById(R.id.option1);
        RadioButton option2 = findViewById(R.id.option2);
        RadioButton option3 = findViewById(R.id.option3);

        option1.setClickable(false);
        option2.setClickable(false);
        option3.setClickable(false);



        TextView longest_streak = (TextView) findViewById(R.id.longest_winning_streak) ;


        win1 = sh.getInt(STREAK , 0) ;
        String s  = "LONGEST STREAK: " + win1 ;
        longest_streak.setText(s);

        if (streak >= win1) {


            SharedPreferences.Editor editor = sh.edit();
            editor.putInt(STREAK, streak);
            editor.commit();

            int win2 = sh.getInt(STREAK, 0);
            String winner = Integer.toString(win2, 10);
            String wining_streak = ("LONGEST STREAK: " + winner);
            longest_streak.setText(wining_streak);
        }




    }


    public void onResetButtonClicked(View b) {
        RadioGroup optionsoptions = findViewById(R.id.options);
        TextView tvtv = findViewById(R.id.tvtextview);
        EditText num1num1 = findViewById(R.id.edit_text);
        TextView ans = findViewById(R.id.answer);
        RadioButton option1option1 = findViewById(R.id.option1);
        RadioButton option2option2 = findViewById(R.id.option2);
        RadioButton option3option3 = findViewById(R.id.option3);
        TextView correct_answer_display1_1 = (TextView) findViewById(R.id.correct_answer_display1);
        TextView correct_answer_display2_2 = (TextView) findViewById(R.id.correct_answer_display2);
        TextView countdown = findViewById(R.id.countdown_timer);
        View parent_view = findViewById(R.id.parent);
        optionsoptions.clearCheck();
        tvtv.setText("");
        option1option1.setText("");
        option2option2.setText("");
        option3option3.setText("");
        num1num1.getText().clear();
        ans.setText("");
        correct_answer_display1_1.setText("");
        correct_answer_display2_2.setText("");
        countdown.setText("");
        optionsoptions.setVisibility(View.INVISIBLE);

        Button submit = findViewById(R.id.submit_button);
        submit.setClickable(true);


        check = false;


    }


}




