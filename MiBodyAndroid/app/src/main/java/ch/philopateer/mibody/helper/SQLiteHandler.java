package ch.philopateer.mibody.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import ch.philopateer.mibody.object.ExerciseItem;
import ch.philopateer.mibody.object.Muscles;
import ch.philopateer.mibody.object.WorkoutExItem;
import ch.philopateer.mibody.object.WorkoutItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by NakeebMac on 10/13/16.
 */

public class SQLiteHandler extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();

    SQLiteDatabase db;

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "mibody.db";

    // Exercises table name
    public static final String EXERCISES_TABLE = "exercises";
    // Exercise Table Columns names
    public static final String EXERCISE_ID = "id";
    public static final String EXERCISE_NAME = "name";
    public static final String EXERCISE_ICON = "icon";
    public static final String EXERCISE_IMAGE = "image";
    public static final String EXERCISE_GIF = "GIF";
    public static final String EXERCISE_DESCRIPTION = "description";
    public static final String EXERCISE_CATEGORY = "category";

    private String[] EXERCISES_COLS = {EXERCISE_ID, EXERCISE_NAME, EXERCISE_ICON, EXERCISE_IMAGE, EXERCISE_GIF, EXERCISE_DESCRIPTION, EXERCISE_CATEGORY};

    // Workouts table name
    public static final String WORKOUTS_TABLE = "workouts";
    // Workouts Table Columns names
    public static final String WORKOUT_ID = "id";
    public static final String WORKOUT_NAME = "name";
    public static final String WORKOUT_REPS = "reps";
    public static final String WORKOUT_EXERCISES = "exercises";
    public static final String WORKOUT_TYPE = "type";

    private String[] WORKOUTS_COLS = {WORKOUT_ID, WORKOUT_NAME, WORKOUT_REPS, WORKOUT_EXERCISES, WORKOUT_TYPE};

    // Muscles table name
    public static final String MUSCLES_TABLE = "muscles";
    // Muscle Table Columns names
    public static final String MUSCLE_ENTRY_ID = "id";
    public static final String MUSCLE_TRICEPS = "Triceps";
    public static final String MUSCLE_QUADRICEPS = "Quadriceps";
    public static final String MUSCLE_CHEST = "Chest";
    public static final String MUSCLE_WAIST = "Waist";
    public static final String MUSCLE_CALF = "Calf";
    public static final String MUSCLE_BICEPS = "Biceps";
    public static final String MUSCLE_UPDATE = "OnCreate";

    private String[] MUSCLES_COLS = {MUSCLE_ENTRY_ID, MUSCLE_TRICEPS, MUSCLE_QUADRICEPS, MUSCLE_CHEST, MUSCLE_WAIST, MUSCLE_CALF, MUSCLE_BICEPS, MUSCLE_UPDATE};
    // Timestamp DATETIME DEFAULT CURRENT_TIMESTAMP



    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_WORKOUTS_TABLE = "CREATE TABLE " + WORKOUTS_TABLE + "( " + WORKOUT_ID + " integer primary key autoincrement, "
                + WORKOUT_NAME + " TEXT," + WORKOUT_REPS + " TEXT,"
                + WORKOUT_EXERCISES + " TEXT," + WORKOUT_TYPE + " TEXT" + ")";

        String CREATE_EXERCISES_TABLE = "CREATE TABLE " + EXERCISES_TABLE + "( " + EXERCISE_ID + " integer primary key autoincrement, "
                + EXERCISE_NAME + " TEXT," + EXERCISE_ICON + " TEXT," + EXERCISE_IMAGE + " TEXT,"
                + EXERCISE_GIF + " TEXT," + EXERCISE_DESCRIPTION + " TEXT," + EXERCISE_CATEGORY + " TEXT" + ")";

        String CREATE_MUSCLE_TABLE = "CREATE TABLE " + MUSCLES_TABLE + "( " + MUSCLE_ENTRY_ID + " integer primary key autoincrement, "
                + MUSCLE_TRICEPS + " FLOAT," + MUSCLE_QUADRICEPS + " FLOAT," + MUSCLE_CHEST + " FLOAT,"
                + MUSCLE_WAIST + " FLOAT," + MUSCLE_CALF + " FLOAT," + MUSCLE_BICEPS + " FLOAT," + MUSCLE_UPDATE + " DATETIME DEFAULT CURRENT_TIMESTAMP" + ")";

        db.execSQL(CREATE_EXERCISES_TABLE);
        db.execSQL(CREATE_WORKOUTS_TABLE);
        db.execSQL(CREATE_MUSCLE_TABLE);

        Log.d(TAG, "Database tables created");
    }

    public void open() throws SQLException {
        db = this.getWritableDatabase();
    }

    public void close(){
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + EXERCISES_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + WORKOUTS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + MUSCLES_TABLE);

        // Create tables again
        onCreate(db);
    }

    /**
     * Workouts Functions
     * */

    public void addWorkout(WorkoutItem workoutItem) {

        ContentValues values = new ContentValues();
        values.put(WORKOUT_NAME, workoutItem.workoutName);
/*
        JSONArray jsonArray = new JSONArray();
        for (int i=0; i < workoutItem.exercisesList.size(); i++) {
            jsonArray.put(workoutItem.exercisesList.get(i).getJSONObject());
        }
        Log.d ("JSONObjectSQL", jsonArray.toString());
*/
        values.put(WORKOUT_EXERCISES, workoutItem.exercisesJSON);
        values.put(WORKOUT_REPS, workoutItem.workoutReps);
        values.put(WORKOUT_TYPE, workoutItem.workoutType);

        Log.d("TAG", "New workout inserted into sqlite: ");

        // Inserting Row
        db.insert(SQLiteHandler.WORKOUTS_TABLE, null, values);

    }

    public ArrayList<WorkoutItem> getWorkouts(String args){
        ArrayList<WorkoutItem> workoutItems = new ArrayList<WorkoutItem>();

        Cursor cursor = db.query(SQLiteHandler.WORKOUTS_TABLE, WORKOUTS_COLS, args, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {

            WorkoutItem workoutItem = new WorkoutItem(cursor.getString(0), cursor.getString(1), Integer.valueOf(cursor.getString(2)), cursor.getString(3), cursor.getString(4), 0);
            workoutItems.add(workoutItem);
            cursor.moveToNext();
        }
        cursor.close();


        return workoutItems;
    }

    /**
     * Exercises Functions
     * */

    public void addExercise(ExerciseItem exerciseItem) {

        ContentValues values = new ContentValues();
        values.put(EXERCISE_ID, exerciseItem.getId());
        values.put(EXERCISE_NAME, exerciseItem.getName());
        values.put(EXERCISE_ICON, exerciseItem.getIcon());
        values.put(EXERCISE_IMAGE, exerciseItem.getImage());
        values.put(EXERCISE_GIF, exerciseItem.getGIF());
        values.put(EXERCISE_DESCRIPTION, exerciseItem.getDescription());
        values.put(EXERCISE_CATEGORY, exerciseItem.getCategory());

        Log.d("TAG", "New Exercise inserted into sqlite: ");

        // Inserting Row
        db.insert(SQLiteHandler.EXERCISES_TABLE, null, values);

    }

    public ArrayList<ExerciseItem> getExercises(String args){
        ArrayList<ExerciseItem> exerciseItemArrayList = new ArrayList<ExerciseItem>();

        Cursor cursor = db.query(SQLiteHandler.EXERCISES_TABLE, EXERCISES_COLS, args, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {

            ExerciseItem exerciseItem = new ExerciseItem(cursor.getString(0), cursor.getString(1),
                    cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6));
            exerciseItemArrayList.add(exerciseItem);
            cursor.moveToNext();
        }
        cursor.close();

        return exerciseItemArrayList;
    }

    /**
     * Muscles Functions
     * */

    public void updateMuscle(ContentValues values) {

        Log.d("TAG", "New muscle update inserted into sqlite: ");

        // Inserting Row
        db.insert(SQLiteHandler.MUSCLES_TABLE, null, values);

    }

    public ArrayList<Muscles> getMusclesUpdates(String args){
        ArrayList<Muscles> musclesArrayList = new ArrayList<Muscles>();

        Cursor cursor = db.query(SQLiteHandler.MUSCLES_TABLE, MUSCLES_COLS, args, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {

            Muscles muscles = new Muscles(cursor.getInt(0), cursor.getInt(1),
                    cursor.getInt(2), cursor.getInt(3), cursor.getInt(4), cursor.getInt(5), cursor.getInt(6), cursor.getString(7));
            musclesArrayList.add(muscles);
            cursor.moveToNext();
        }
        cursor.close();

        return musclesArrayList;
    }


    private WorkoutItem cursorToWorkout(Cursor cursor) {
        ArrayList<WorkoutExItem> workoutExItems = new ArrayList<WorkoutExItem>();
        try {
            JSONArray workoutsExArr = new JSONArray(cursor.getString(3));
            System.out.println(workoutsExArr.length());

            if(workoutsExArr.length() != 0){

                for(int i = 0; i<workoutsExArr.length(); i++){
                    // Get JSON object
                    JSONObject obj = (JSONObject) workoutsExArr.get(i);

                    String name = obj.get("name").toString();
                    String image = obj.get("image").toString();
                    String ropes = obj.get("ropes").toString();
                    int reps = obj.getInt("reps");
                    int restTime = obj.getInt("restTime");
                    int exReps = obj.getInt("exReps");

                    Log.d("checkJSON", name + " " + String.valueOf(restTime));

                    //workoutExItems.add(new WorkoutExItem(name, image, ropes, reps, restTime, exReps));
                }
            }

        }
        catch (JSONException e){
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return new WorkoutItem(cursor.getString(0), cursor.getString(1), Integer.valueOf(cursor.getString(2)), cursor.getString(3), cursor.getString(4), 0);
    }

    /**
     * Re crate database Delete all tables and create them again
     * */
    public void deleteWorkouts() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(WORKOUTS_TABLE, null, null);
        db.close();

        Log.d(TAG, "Deleted all workouts info from sqlite");
    }
}
