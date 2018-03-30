package com.example.rajat.projectm;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

public class QuizActivity extends AppCompatActivity implements TextToSpeech.OnInitListener{


	private SensorManager mSensorManager;
	private ShakeEventListener mSensorListener;
	private TextToSpeech tts;
	List<Question> quesList;
	int score = 0;
	int qid = 0;
	Question currentQ;
	TextView txtQuestion;
	RadioButton rda, rdb, rdc;
	Button butNext;
	RadioGroup radioGroup;
	int i=0;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quiz);

		final Window window = getWindow();
		window.setStatusBarColor(Color.parseColor("#00796B"));
		window.setNavigationBarColor(Color.parseColor("#198679"));

		tts=new TextToSpeech(this,this);
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		mSensorListener = new ShakeEventListener();

		mSensorListener.setOnShakeListener(new ShakeEventListener.OnShakeListener() {

			public void onShake() {
				butNext.performClick();
			}
		});

		AlertDialog.Builder builder=new AlertDialog.Builder(QuizActivity.this);
		builder.setTitle("Instructions").setIcon(R.drawable.ic_info).setMessage(" 1. Volume Up - Toggle Options (A)/(B)\n 2. Volume Down - Select Option (C) \n 3. Shake for next Question ");
		builder.setNeutralButton("Okay", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				Toast.makeText(QuizActivity.this, "All the best!", Toast.LENGTH_SHORT).show();
			}
		});
		builder.show();
		final DbHelper db = new DbHelper(this);
		quesList = db.getAllQuestions();
		currentQ = quesList.get(qid);
		txtQuestion = (TextView) findViewById(R.id.textView1);
		rda = (RadioButton) findViewById(R.id.radio0);
		rdb = (RadioButton) findViewById(R.id.radio1);
		rdc = (RadioButton) findViewById(R.id.radio2);
		butNext = (Button) findViewById(R.id.button1);
		radioGroup= (RadioGroup) findViewById(R.id.radioGroup1);
		setQuestionView();
		buttonNext();

	}

	private void handleShakeEvent(int count) {
		buttonNext();
	}

	private void buttonNext() {
		butNext.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				RadioGroup grp = (RadioGroup) findViewById(R.id.radioGroup1);
				RadioButton answer = (RadioButton) findViewById(grp.getCheckedRadioButtonId());
				Log.d("yourans", currentQ.getANSWER() + " " + answer.getText());
				if (currentQ.getANSWER().equals(answer.getText())) {
					score++;
					Log.d("score", "Your score" + score);
				}

				if (qid < 5) {
					currentQ = quesList.get(qid);
					setQuestionView();
				} else {
					Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
					Bundle b = new Bundle();
					b.putInt("score", score); //Your score
					intent.putExtras(b); //Put your score to your next Intent
					startActivity(intent);
					finish();
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_quiz, menu);

		return true;
	}

	private void setQuestionView() {
		txtQuestion.setText(currentQ.getQUESTION());
		rda.setText(currentQ.getOPTA());
		rdb.setText(currentQ.getOPTB());
		rdc.setText(currentQ.getOPTC());
		qid++;
		String speakQuestion = txtQuestion.getText().toString();
		speakOut(speakQuestion);
		rda.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				String textSpeech=rda.getText().toString();
				speakOut(textSpeech);
			}
		});
		rdb.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				String textSpeech=rdb.getText().toString();
				speakOut(textSpeech);
			}
		});
		rdc.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				String textSpeech=rdc.getText().toString();
				speakOut(textSpeech);
			}
		});
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		int action = event.getAction();
		int keyCode = event.getKeyCode();
		//int i;
		switch (keyCode) {
			case KeyEvent.KEYCODE_VOLUME_UP:
				if (action == KeyEvent.ACTION_DOWN) {
					if(i==0){
						rda.setChecked(true);
						String textSpeech=rda.getText().toString();
						speakOut(textSpeech);
						//rdb.setChecked(false);
						//	rdc.setChecked(false);

					i+=2;
					}
					else if(i==2){
						//rda.setChecked(false);
						rdb.setChecked(true);
						String textSpeech=rdb.getText().toString();
						speakOut(textSpeech);
						//		rdc.setChecked(false);
						i-=2;
					}
					//Toast.makeText(getApplicationContext(), "Volume Up", Toast.LENGTH_SHORT).show();
				}
				return true;
			case KeyEvent.KEYCODE_VOLUME_DOWN:
				if (action == KeyEvent.ACTION_DOWN) {
					//Toast.makeText(getApplicationContext(), "Volume Down", Toast.LENGTH_SHORT).show();
					//rda.setChecked(false);
					//rdb.setChecked(false);
					rdc.setChecked(true);
					String textSpeech=rdc.getText().toString();
					speakOut(textSpeech);
				}
				return true;
			default:
				return super.dispatchKeyEvent(event);
		}
	}

	@Override
	protected void onResume() {

		super.onResume();
		mSensorManager.registerListener(mSensorListener,
				mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_UI);
	}

	@Override
	protected void onPause() {
		mSensorManager.unregisterListener(mSensorListener);
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		if(tts!=null){
			tts.stop();
			tts.shutdown();
		}
		super.onDestroy();
	}

	@Override
	public void onInit(int i) {
		if(i==TextToSpeech.SUCCESS){
			int result=tts.setLanguage(Locale.US);
			if(result==TextToSpeech.LANG_MISSING_DATA || result==TextToSpeech.LANG_NOT_SUPPORTED){
				Toast.makeText(QuizActivity.this,"Missing Data!!!",Toast.LENGTH_SHORT);
			}
			//else {
				//buttonSpeak.setEnabled(true);
			//	speakOut();
			//}
		}
	}
	private void speakOut(String textSpeech){
		//String text=editText.getText().toString();
		tts.speak(textSpeech,TextToSpeech.QUEUE_FLUSH,null,"a");
	}

	@Override
	public void onBackPressed() {
		//super.onBackPressed();
		Toast.makeText(QuizActivity.this,"Please complete quiz first.",Toast.LENGTH_LONG).show();
		String textSpeech="Please Complete Quiz First";
		speakOut(textSpeech);

	}
}
