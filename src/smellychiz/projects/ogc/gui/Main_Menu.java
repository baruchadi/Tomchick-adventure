package smellychiz.projects.ogc.gui;

import smellychiz.projects.ogc.R;
import smellychiz.projects.ogc.StartPoint;
import smellychiz.projects.ogc.StartWorldEditor;
import smellychiz.projects.ogc.objects.world.NewLevel;
import smellychiz.projects.ogc.util.IO;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class Main_Menu extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		this.setContentView(R.layout.menu);

		StartPoint.load = false;

		Button newGame = (Button) findViewById(R.id.new_game);
		Button createWorld = (Button) findViewById(R.id.WorldEditor);
		Button quitGame = (Button) findViewById(R.id.quit_game);
		Button downloadLevels = (Button) findViewById(R.id.downloadLevels);

		newGame.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				StartPoint.load = false;
				startActivity(new Intent("android.intent.action.PICKER"));
				Toast.makeText(Main_Menu.this, "Loading Game",
						Toast.LENGTH_SHORT).show();
				finish();
			}
		});

		downloadLevels.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				IO io = new IO();
				Toast.makeText(Main_Menu.this, "Downloading...",
						Toast.LENGTH_SHORT).show();
				io.execute("lol");
				Toast.makeText(Main_Menu.this, "Done..?", Toast.LENGTH_SHORT)
						.show();
			}
		});

		createWorld.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog builder = new AlertDialog.Builder(Main_Menu.this)
						.setTitle("Create A Level")
						.setMessage("Choose Level Size")
						.setPositiveButton("Huge",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										StartWorldEditor.size = NewLevel.HUGE;
										startActivity(new Intent(
												"android.intent.action.WORLDEDIT"));
										Toast.makeText(Main_Menu.this,
												"Entering World Editor",
												Toast.LENGTH_SHORT).show();
									}
								})
						.setNegativeButton("Small",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										StartWorldEditor.size = NewLevel.SMALL;
										startActivity(new Intent(
												"android.intent.action.WORLDEDIT"));
										Toast.makeText(Main_Menu.this,
												"Entering World Editor",
												Toast.LENGTH_SHORT).show();
									}
								})
						.setNeutralButton("Medium",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										StartWorldEditor.size = NewLevel.MEDIUM;
										startActivity(new Intent(
												"android.intent.action.WORLDEDIT"));
										Toast.makeText(Main_Menu.this,
												"Entering World Editor",
												Toast.LENGTH_SHORT).show();
									}
								}).show();

			}
		});

		quitGame.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
				System.exit(0);

			}
		});

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			this.finish();
			return false;
		}

		return super.onKeyDown(keyCode, event);
	}
}
