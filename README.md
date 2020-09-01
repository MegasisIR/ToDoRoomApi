
<pre dir="rtl">تصاویر</pre>

<div style="margin:0 auto;padding:15px;display:inline-block" dir="rtl">
 <img src="https://github.com/MegasisIR/ToDoRoomApi/blob/master/screenshots/screenshot-2020-08-28_13.30.00.163.png" alt="drawing" width="300px" height="600px" style="max-width:100%;float: right;">
 
  <img src="https://github.com/MegasisIR/ToDoRoomApi/blob/master/screenshots/screenshot-2020-08-28_13.30.18.816.png" alt="drawing" width="300px" height="600px" style="max-width:100%;float: right;">
  
 <img src="https://github.com/MegasisIR/ToDoRoomApi/blob/master/screenshots/screenshot-2020-08-28_13.31.46.965.png" style="float:right" alt="drawing" width="300px" height="600px" margin="10px"/>
</div>
<pre dir="rtl">
 حجم نوشته ما  وقتی  مستقیم با پایگاه داده در ارتباط هستیم
</pre>
<pre>

public class SQLiteHelper extends SQLiteOpenHelper {
    private String TABLE_TASKS = "tbl_tasks";
    private static final String TAG = "SQLiteHelper";

    public SQLiteHelper(@Nullable Context context) {
        super(context, "db_app", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(" CREATE TABLE " + TABLE_TASKS + " (id INTEGER PRIMARY KEY AUTOINCREMENT , title TEXT, completed BOOLEAN) ;");
        } catch (Exception error) {
            Log.e(TAG, "onCreate: ", error);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long add(Task task) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", task.getTitle());
        contentValues.put("completed", task.isCompleted());
        long result = sqLiteDatabase.insert(TABLE_TASKS, null, contentValues);
        sqLiteDatabase.close();
        return result;
    }

    public int update(Task task) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", task.getTitle());
        contentValues.put("completed", task.isCompleted());
        int result = sqLiteDatabase.update(TABLE_TASKS, contentValues, " id = ? ", new String[]{String.valueOf(task.getId())});
        sqLiteDatabase.close();
        return result;
    }

    public int delete(Task task) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        int result = sqLiteDatabase.delete(TABLE_TASKS, " id = ? ", new String[]{String.valueOf(task.getId())});
        sqLiteDatabase.close();
        return result;
    }

    public List<Task> getAll() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(" SELECT * FROM " + TABLE_TASKS, null);
        List<Task> tasks = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Task task = new Task();
                task.setId(cursor.getLong(0));
                task.setTitle(cursor.getString(1));
                task.setCompleted(cursor.getInt(2) == 1);
                tasks.add(task);
            } while (cursor.moveToNext());
            sqLiteDatabase.close();
        }
        return tasks;
    }

    public void deleteAll() {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.execSQL("DELETE FROM "+TABLE_TASKS);
        sqLiteDatabase.close();
    }


    public List<Task> search(String query) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(" SELECT * FROM " + TABLE_TASKS + " WHERE title LIKE '%" + query + "%' ;", null);
        List<Task> tasks = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Task task = new Task();
                task.setId(cursor.getLong(0));
                task.setTitle(cursor.getString(1));
                task.setCompleted(cursor.getInt(2) == 1);
                tasks.add(task);
            }while (cursor.moveToNext());
        }

        return tasks;
    }
}
</pre>

<pre dir="rtl">
 حجم نوشته ما  وقتی به کمک کتابخانه Room با پایگاه داده در ارتباط هستیم
</pre>

<pre>

@Dao
public interface TaskDao {

    @Insert
    long add(Task task);

    @Update
    int update(Task task);

    @Delete
    int delete(Task task);

    @Query(" SELECT * FROM tbl_task")
    List<Task> getAll();

    @Query(" DELETE FROM tbl_task")
    void deleteAll();

    @Query(" SELECT * FROM tbl_task WHERE title LIKE '%' || :query || '%' ")
    List<Task> search(String query);


}

</pre>
