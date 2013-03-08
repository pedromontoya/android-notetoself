package com.notetoself;

import com.notetoself.core.NoteEntryRepository;
import com.notetoself.models.NoteEntry;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

//////////////////////////////////////////////////////////////////////////////////////////////
public class NoteDetailEdit extends Activity implements OnClickListener
{
	private NoteEntryRepository _noteRepository;
	private NoteEntry _noteEntry;
	
	//----------------------------------------------------------------------------------------			
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_note_detail_edit);

		_noteRepository = new NoteEntryRepository(this);
		_noteEntry = (NoteEntry)getIntent().getSerializableExtra("NoteEntry");
		if(_noteEntry != null){
			//Initialize note body txt
			EditText noteBody = (EditText)findViewById(R.id.noteDetails);
			noteBody.setText(_noteEntry.NoteBody);
		}
		
		//Attach EventListner
		Button cancel = (Button)findViewById(R.id.cancelEdit);
		cancel.setOnClickListener(this);
		Button save = (Button)findViewById(R.id.saveNote);
		save.setOnClickListener(this);
	}
	//----------------------------------------------------------------------------------------		
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.note_detail_edit, menu);
		return true;
	}
	//----------------------------------------------------------------------------------------		
	 @Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int viewId = v.getId();
		
		switch(viewId){
			case R.id.cancelEdit:{
				setResult(RESULT_CANCELED, new Intent());
				finish();
				break;
			}
			case R.id.saveNote:{
				EditText editTxt = (EditText)findViewById(R.id.noteDetails);
				if(_noteEntry == null){
					NoteEntry noteEntry = new NoteEntry();
					noteEntry.NoteBody = editTxt.getText().toString();
					_noteRepository.AddNoteEntry(noteEntry);
				}else{
					_noteEntry.NoteBody = editTxt.getText().toString();
					_noteRepository.UpdateNoteEntry(_noteEntry);
				}
				setResult(RESULT_OK, new Intent());
				finish();
			}
		}
		
	}
	//----------------------------------------------------------------------------------------		
}
//////////////////////////////////////////////////////////////////////////////////////////////
