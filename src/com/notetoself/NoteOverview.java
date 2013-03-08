package com.notetoself;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.net.MailTo;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import com.notetoself.core.NoteEntryAdapter;
import com.notetoself.core.NoteEntryRepository;
import com.notetoself.models.NoteEntry;

//////////////////////////////////////////////////////////////////////////////////////////////
public class NoteOverview extends ListActivity
{
	private ArrayList<NoteEntry> _userNotes;
	private ArrayAdapter<NoteEntry> _adapter;
	private NoteEntryRepository _noteRepository;
	private View _selectedItem;

	//----------------------------------------------------------------------------------------	
	public NoteOverview(){
		_noteRepository = new NoteEntryRepository(this);
	}
	//----------------------------------------------------------------------------------------	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_note_overview);
		
		//Initialize list adapter
		SetListAdapter();
		
		//Register event listeners
		Button addBtn = (Button)findViewById(R.id.addNote);
		addBtn.setOnClickListener(this.AddNoteClickListner);
		ListView listView = this.getListView();
		registerForContextMenu(listView);
	}
	//----------------------------------------------------------------------------------------
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == 1){
			if(resultCode == RESULT_OK){
				SetListAdapter();
			}
		}
	}
	//----------------------------------------------------------------------------------------	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		
		//Get selected list item
		NoteEntry entry = _userNotes.get(position);
		Intent intent = new Intent(this, NoteDetailEdit.class);
		intent.putExtra("NoteEntry", entry);
		startActivityForResult(intent,1);
	}
	//----------------------------------------------------------------------------------------	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.note_overview, menu);
		return true;
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		if(v.getId() == android.R.id.list){
			AdapterContextMenuInfo info = (AdapterContextMenuInfo)menuInfo;
			
			NoteEntry entry = _userNotes.get(info.position);
			menu.add(Menu.NONE, entry.Id, Menu.NONE, "Delete");
		}
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		int itemId = item.getItemId();
		int rowsEffected = _noteRepository.DeleteNoteEntry(itemId);
		
		//If item was deleted, refresh list view
		if(rowsEffected > 0){
			SetListAdapter();
		}
		
		//return false to allow normal menu processing to proceed
		return false;
	}
	//----------------------------------------------------------------------------------------	
	private OnClickListener AddNoteClickListner = new OnClickListener() {
		@Override
		public void onClick(View view) {
			
			Intent intent = new Intent(NoteOverview.this, NoteDetailEdit.class);
			startActivityForResult(intent, 1);
		}
	};
	//----------------------------------------------------------------------------------------	
	private void SetListAdapter(){
		_userNotes = _noteRepository.GetNoteEntries();
		_adapter = new NoteEntryAdapter(
				this,
				R.layout.layout_note_list_item,
				R.id.label,
				_userNotes);	
		
		//Set the adapter
		setListAdapter(_adapter);
	}
	
//	private void SetItemBackground(View v, boolean isSelected){
//		if(isSelected){
//			_selectedItem = v;
//			v.setBackgroundColor(getResources().getColor(R.color.LightGrey));
//		}else{
//			_selectedItem = null;
//			v.setBackgroundColor(getResources().getColor(R.color.Gray));
//		}
//	}
	//----------------------------------------------------------------------------------------
}
//////////////////////////////////////////////////////////////////////////////////////////////
