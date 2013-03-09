package notetoself.data;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import notetoself.models.NoteEntry;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
//////////////////////////////////////////////////////////////////////////////////////////////
public class NoteEntryRepository extends SQLiteOpenHelper 
{
	public static final int DATABASE_VERSION = 2;
    public static final String TABLE_NAME = "notetoself";
    public static final String COLUMN_NOTE_ID = "_id";
    public static final String COLUMN_NOTE_BODY = "NoteBody";
    public static final String COLUMN_DATE_CREATED = "DateCreated";
    private static final String TABLE_CREATE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_NOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NOTE_BODY + " TEXT, " +
                COLUMN_DATE_CREATED + " TEXT);";
    
	//----------------------------------------------------------------------------------------		
	public NoteEntryRepository(Context context){
		super(context, TABLE_NAME, null, DATABASE_VERSION);
	}
	//----------------------------------------------------------------------------------------	
	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		Log.w(NoteEntryRepository.class.getName(), "DROPPING DATABASE");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
	    onCreate(db);
	}
	//----------------------------------------------------------------------------------------		
	@Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }
	//----------------------------------------------------------------------------------------		
	public ArrayList<NoteEntry> GetNoteEntries(){
		ArrayList<NoteEntry> noteEntries = new ArrayList<NoteEntry>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
		SQLiteDatabase db = this.getReadableDatabase();
		
		Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
		if(cursor.moveToFirst()){
			do{
				try{
					//Get cursor data
					int id = cursor.getInt(cursor.getColumnIndex(COLUMN_NOTE_ID));
					String dateString = cursor.getString(cursor.getColumnIndex(COLUMN_DATE_CREATED));
					String noteBody = cursor.getString(cursor.getColumnIndex(COLUMN_NOTE_BODY));
					
					//Create note entry
					NoteEntry entry = new NoteEntry();
					entry.Id = id;
					entry.DateCreated = dateFormat.parse(dateString);
					entry.NoteBody = noteBody;
					
					//Add entry to list
					noteEntries.add(entry);
				}catch(Exception ex){
					//Eat Exception
				}
			}while(cursor.moveToNext());
		}
		cursor.close();
		
		return noteEntries;
	}
	//----------------------------------------------------------------------------------------		
	public void AddNoteEntry(NoteEntry noteEntry){
		//Get writable DB
		SQLiteDatabase db = this.getWritableDatabase();
		
		//Set values to insert
		ContentValues contentValues = new ContentValues();
		contentValues.put(COLUMN_DATE_CREATED, noteEntry.DateCreated.toString());
		contentValues.put(COLUMN_NOTE_BODY, noteEntry.NoteBody);
		
		//Execute insert
		long rowId = db.insert(TABLE_NAME, null, contentValues);	
	}
	//----------------------------------------------------------------------------------------		
	public void UpdateNoteEntry(NoteEntry noteEntry){
		//Get writable DB
		SQLiteDatabase db = this.getWritableDatabase();
		
		//Set values to update
		ContentValues contentValues = new ContentValues();
		contentValues.put(COLUMN_DATE_CREATED, noteEntry.DateCreated.toString());
		contentValues.put(COLUMN_NOTE_BODY, noteEntry.NoteBody);
		String whereClause = COLUMN_NOTE_ID+" = "+noteEntry.Id;
		
		//Execute update
		long rowId = db.update(TABLE_NAME, contentValues, whereClause, null);	
	}
	//----------------------------------------------------------------------------------------
	public int DeleteNoteEntry(int noteEntryId){
		//Get writable DB
		SQLiteDatabase db = this.getWritableDatabase();
		
		//Set delete condition
		String whereClause = COLUMN_NOTE_ID+" = "+noteEntryId;
		
		//Execute delete
		int rowsEffected = db.delete(TABLE_NAME, whereClause, null);	
		
		return rowsEffected;
	}
	//----------------------------------------------------------------------------------------
}
//////////////////////////////////////////////////////////////////////////////////////////////