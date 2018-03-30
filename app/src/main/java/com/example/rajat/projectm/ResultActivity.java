package com.example.rajat.projectm;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class ResultActivity extends AppCompatActivity implements TextToSpeech.OnInitListener{
	private SensorManager mSensorManager;
	private ShakeEventListener mSensorListener;
	private TextToSpeech tts;
	Button button;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);

		tts=new TextToSpeech(this,this);
		button = (Button) findViewById(R.id.button);

		//get rating bar object
		RatingBar bar=(RatingBar)findViewById(R.id.ratingBar1); 
		bar.setNumStars(5);
		bar.setStepSize(1f);
		//get text view
		final TextView t=(TextView)findViewById(R.id.textResult);
		TextView t1= (TextView) findViewById(R.id.textView5);
		ImageButton imageButton= (ImageButton) findViewById(R.id.imageButton);
		//get score
		Bundle b = getIntent().getExtras();
		int score= b.getInt("score");
		//display score
		bar.setRating(score);
		switch (score)
		{
		case 1: t.setText("Try Again!");
				t1.setText("1/5");
			break;
		case 2: t.setText("Okay!");
				t1.setText("2/5");
			break;
		case 3: t.setText("Nice!");
				t1.setText("3/5");
			break;
		case 4:	t.setText("Good!");
				t1.setText("4/5");
			break;
		case 5:	t.setText("Perfect!");
				t1.setText("5/5");
			break;
		}
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		mSensorListener = new ShakeEventListener();

		mSensorListener.setOnShakeListener(new ShakeEventListener.OnShakeListener() {

			public void onShake() {
				button.performClick();
			}
		});
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				String speakQuestion = "Score is "+t.getText().toString();
				speakOut(speakQuestion);
			}
		});

		imageButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_result, menu);
		return true;
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
		if (i == TextToSpeech.SUCCESS) {
			int result = tts.setLanguage(Locale.US);
			if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
				Toast.makeText(ResultActivity.this, "Missing Data!!!", Toast.LENGTH_SHORT).show();
			}
//			else {
//			button.setEnabled(true);
//				speakOut("bullshit");
//			}
		}
	}
		private void speakOut(String textSpeech){
			//String text=editText.getText().toString();
			tts.speak(textSpeech,TextToSpeech.QUEUE_FLUSH,null,"a");
		}
}