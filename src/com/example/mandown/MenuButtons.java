package com.example.mandown;

import android.view.Menu;
import android.view.MenuItem;

public class MenuButtons {
	public MenuItem startButton;
	public MenuItem stopButton;
	public MenuItem saveButton;

	public MenuButtons(Menu menu) {
		startButton = menu.findItem(R.id.play_button);
		stopButton = menu.findItem(R.id.stop_button);
		saveButton = menu.findItem(R.id.save_button);
	}

	public void setInitialState() {
		startButton.setVisible(true);
		stopButton.setVisible(false);
		saveButton.setVisible(true);
		saveButton.setEnabled(false);
	}

	public void setRecordingState() {
		startButton.setVisible(false);
		stopButton.setVisible(true);
		saveButton.setVisible(true);
		saveButton.setEnabled(false);
	}

	public void setRecordedState() {
		startButton.setVisible(true);
		stopButton.setVisible(false);
		saveButton.setVisible(true);
		saveButton.setEnabled(true);
	}

}