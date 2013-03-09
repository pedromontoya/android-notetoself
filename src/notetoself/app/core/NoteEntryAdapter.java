package notetoself.app.core;
import java.util.ArrayList;

import notetoself.models.NoteEntry;

import android.content.Context;
import com.notetoself.R;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

//////////////////////////////////////////////////////////////////////////////////////////////
public class NoteEntryAdapter extends ArrayAdapter<NoteEntry> 
{
	private final Context _context;
	private final ArrayList<NoteEntry> _values;
	private final int _resource;
	
	//----------------------------------------------------------------------------------------	
	public NoteEntryAdapter(Context context, int resource, int textViewResourceId, ArrayList<NoteEntry> objects){
		super(context, resource, textViewResourceId, objects);
		_context = context;
		_values = objects;
		_resource = resource;
	}
	//----------------------------------------------------------------------------------------		
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LinearLayout layout = (LinearLayout)super.getView(position, convertView, parent);
		TextView textView = (TextView)layout.findViewById(R.id.label);
		textView.setText(_values.get(position).GetNoteBodyPreview());
		
		return layout;
	}
	//----------------------------------------------------------------------------------------	
}
//////////////////////////////////////////////////////////////////////////////////////////////