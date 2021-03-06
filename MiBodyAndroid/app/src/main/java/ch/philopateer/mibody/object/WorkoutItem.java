package ch.philopateer.mibody.object;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.firebase.database.Exclude;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by nakeebimac on 10/18/16.
 */

public class WorkoutItem implements Parcelable{

    public String workoutID;
    public String workoutName;
    public String exercisesJSON;
    public ArrayList<WorkoutExItem> exercisesList;
    public int workoutReps;
    public String workoutType;
    public int wTime = 0;

    public WorkoutItem(String workoutName, ArrayList<WorkoutExItem> exercisesList, int workoutReps){
        this.workoutName = workoutName;
        this.exercisesList = exercisesList;
        this.workoutReps = workoutReps;
    }

    public WorkoutItem(){

    }

    public WorkoutItem(String workoutID, String workoutName, String exercisesJSON){
        this.workoutID = workoutID;
        this.workoutName = workoutName;
        this.exercisesJSON = exercisesJSON;

        JSONtoArray();
    }

    // [START post_to_map]
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("workoutID", workoutID);
        result.put("workoutName", workoutName);
        result.put("exercisesJSON", exercisesJSON);
        result.put("workoutReps", workoutReps);
        result.put("wTime", wTime);

        return result;
    }

    public WorkoutItem(String workoutID, String workoutName, int workoutReps, String exercisesJSON, String workoutType, int wTime){
        this.workoutID = workoutID;
        this.workoutName = workoutName;
        this.workoutReps = workoutReps;
        this.exercisesJSON = exercisesJSON;
        this.workoutType = workoutType;
        this.wTime = wTime;

        JSONtoArray();

    }

    public void JSONtoArray(){

        exercisesList = new ArrayList<WorkoutExItem>();

        try {
            JSONArray resultsArr = new JSONArray(exercisesJSON);
            System.out.println(resultsArr.length());
            // If no of array elements is not zero
            if(resultsArr.length() != 0){

                Log.d("resultsArray", resultsArr.toString());
                // Loop through each array element, get JSON object
                for (int i = 0; i < resultsArr.length(); i++) {
                    // Get JSON object
                    JSONObject obj = (JSONObject) resultsArr.get(i);

                    // DB QueryValues Object to insert into Movies ArrayList
                    String name = obj.get("name").toString();
                    String image = obj.get("image").toString();
                    String rope1 = obj.get("rope1").toString();
                    String rope2 = obj.get("rope2").toString();
                    String rope3 = obj.get("rope3").toString();
                    int reps = Integer.parseInt(obj.get("reps").toString());
                    int actualReps = Integer.parseInt(obj.get("actualReps").toString());
                    int restTime = Integer.parseInt(obj.get("restTime").toString());
                    int exReps = Integer.parseInt(obj.get("exReps").toString());
                    int exTime = Integer.parseInt(obj.get("exTime").toString());

                    int actualExTime = 0;
                    if (obj.has("actualExTime")){
                        actualExTime = Integer.parseInt(obj.get("actualExTime").toString());
                    }
                    boolean repsTimeBool = Boolean.parseBoolean(obj.get("repsTimeBool").toString());

                    exercisesList.add(new WorkoutExItem(name, image, rope1, rope2, rope3, reps, actualReps, restTime, exReps, exTime, actualExTime, repsTimeBool));

                }

            }
        }
        catch (JSONException e){
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    protected WorkoutItem(Parcel in) {
        workoutID = in.readString();
        workoutName = in.readString();
        workoutReps = in.readInt();
        exercisesJSON = in.readString();
        workoutType = in.readString();
        wTime = in.readInt();
    }

    public static final Creator<WorkoutItem> CREATOR = new Creator<WorkoutItem>() {
        @Override
        public WorkoutItem createFromParcel(Parcel in) {
            return new WorkoutItem(in);
        }

        @Override
        public WorkoutItem[] newArray(int size) {
            return new WorkoutItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(workoutID);
        parcel.writeString(workoutName);
        parcel.writeInt(workoutReps);
        parcel.writeString(exercisesJSON);
        parcel.writeString(workoutType);
        parcel.writeInt(wTime);
    }
}