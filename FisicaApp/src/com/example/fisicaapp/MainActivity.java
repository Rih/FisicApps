package com.example.fisicaapp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {
	Drawable drawable;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		this.getResources().getDrawable(R.drawable.ic_launcher);
		return true;
	}
	  public void lanzar(View view) {
	        Intent i = new Intent(this, Actvector.class );
	        startActivity(i);
	  } 

}
