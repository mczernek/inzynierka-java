package com.example.mandown;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;




public class SaveToFileDialogFragment extends DialogFragment implements DialogInterface.OnClickListener, TextWatcher{
	private Activity activity;
	private AlertDialog dialog;
	private AlertDialog.Builder builder;
	private EditText editText;
	private AccelerometerRecorderManager recorder;
	
	String filename = null;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState){
		
		activity = getActivity();
		
		editText = new EditText(activity);
		editText.addTextChangedListener(this);
		
		editText.setEnabled(true);
		
		builder = new AlertDialog.Builder(activity);
		builder.setTitle("Siema capie");
		builder.setView(editText);
		builder.setPositiveButton("Zapisz", this);
		builder.setNegativeButton("Olej", this);
		dialog = builder.create();
		dialog.setOnShowListener(new DialogInterface.OnShowListener() {
			
			@Override
			public void onShow(DialogInterface d) {
				if(null == filename || filename.equals("")){
					editText.setHint("Nazwa pliku");
					dialog.getButton(DialogInterface.BUTTON_POSITIVE).setEnabled(false);
				}else{
					editText.setText(filename);
					dialog.getButton(DialogInterface.BUTTON_POSITIVE).setEnabled(true);
				}			
			}
		});
		return dialog;
	}

	@Override
	public void onClick(DialogInterface arg0, int arg1) {
		switch(arg1){
		case DialogInterface.BUTTON_POSITIVE:
			filename = editText.getText().toString();
			recorder.saveResults(activity, filename);
			arg0.dismiss();
			break;
		case DialogInterface.BUTTON_NEGATIVE:
			arg0.cancel();
			break;
		}
	}
	
	public void show(FragmentManager fm, String tag, AccelerometerRecorderManager recorder){
		super.show(fm, tag);
		recorder.stopRecording();
		this.recorder = recorder;
	}

	@Override
	public void afterTextChanged(Editable arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
			int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
		if(arg0.length()>0){
			dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
		}else{
			dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
		}
		
	}
	
}
