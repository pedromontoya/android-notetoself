package notetoself.models;

import java.io.Serializable;
import java.util.Date;

//////////////////////////////////////////////////////////////////////////////////////////////
public class NoteEntry implements Serializable {

	private static final long serialVersionUID = -1532501338441631619L;

	public NoteEntry(){
		this.DateCreated = new Date();
	}
	
	public int Id;
	
	public Date DateCreated;
	
	public String NoteBody;
	
	public String GetNoteBodyPreview(){
		String notePreview = this.NoteBody;
	
		if(notePreview != null && notePreview != ""){
			//By default, only show 30 characters of a note body in the list view
			int noteLength = notePreview.length();
			int maxLength = noteLength > 30 ? 30: noteLength;
			
			notePreview = notePreview.substring(0, maxLength -1);
			if(noteLength > 30){
				notePreview += "...";
			}
		}
		return notePreview;
	}

}
//////////////////////////////////////////////////////////////////////////////////////////////