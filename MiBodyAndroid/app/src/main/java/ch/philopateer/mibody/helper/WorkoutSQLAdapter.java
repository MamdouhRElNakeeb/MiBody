package ch.philopateer.mibody.helper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import ch.philopateer.mibody.R;
import ch.philopateer.mibody.object.WorkoutExItem;

import java.util.List;

/**
 * Created by nakeebimac on 10/19/16.
 */

public class WorkoutSQLAdapter extends RecyclerView.Adapter<AddWorkoutRecyclerViewHolder> {

    private List<WorkoutExItem> arrayList;
    private Context context;
    private int ExercisesNo;

    public WorkoutSQLAdapter(Context context, List<WorkoutExItem> arrayList) {
        // TODO Auto-generated constructor stub

        this.context = context;
        this.arrayList = arrayList;
        this.ExercisesNo = arrayList.size();

    }

    @Override
    public AddWorkoutRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // This method will inflate the custom layout and return as viewholder
        LayoutInflater mInflater = LayoutInflater.from(parent.getContext());

        ViewGroup mainGroup = (ViewGroup) mInflater.inflate(
                R.layout.exercises_set_grid_item, parent, false);
        AddWorkoutRecyclerViewHolder listHolder = new AddWorkoutRecyclerViewHolder(mainGroup);
        return listHolder;
    }

    @Override
    public void onBindViewHolder(AddWorkoutRecyclerViewHolder holder, int position) {
        final WorkoutExItem model = arrayList.get(position);

        final AddWorkoutRecyclerViewHolder mainHolder = (AddWorkoutRecyclerViewHolder) holder;// holder

        //mainHolder.ExerciseImg.setImageResource(AppConfig.Images[Integer.valueOf(model.name)]);
        mainHolder.Reps.setText(String.valueOf(model.reps));
        mainHolder.Reps.setEnabled(false);
        mainHolder.RestTime.setText(String.valueOf(model.restTime));
        mainHolder.RestTime.setEnabled(false);
        mainHolder.RGB.setText(model.rope1);
        mainHolder.setRepeat.setText(String.valueOf(model.exReps));
        mainHolder.setRepeat.setEnabled(false);

    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);
    }
}
