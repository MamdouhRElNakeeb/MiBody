package ch.philopateer.mibody.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import ch.philopateer.mibody.R;
import ch.philopateer.mibody.fragments.ScheduleFragment;
import ch.philopateer.mibody.object.MaterialCalendar;

/**
 * Created by mamdouhelnakeeb on 3/24/17.
 */

public class MaterialCalendarAdapter extends BaseAdapter{

    // Variables
    private Context mContext;
    private static ViewHolder mHolder;
    int mWeekDayNames = 7;
    int mGridViewIndexOffset = 1;

    MaterialCalendar materialCalendar;

    private static class ViewHolder {
        ImageView mSelectedDayImageView;
        TextView mTextView;
        ImageView mSavedEventImageView;
    }

    // Constructor
    public MaterialCalendarAdapter(Context context, MaterialCalendar materialCalendar) {
        mContext = context;
        this.materialCalendar = materialCalendar;
    }

    @Override
    public int getCount() {
        if (materialCalendar.mFirstDay != -1 && materialCalendar.mNumDaysInMonth != -1) {
            Log.d("GRID_COUNT", String.valueOf(mWeekDayNames + materialCalendar.mFirstDay + materialCalendar.mNumDaysInMonth));
            return mWeekDayNames + materialCalendar.mFirstDay + materialCalendar.mNumDaysInMonth;
        }

        return mWeekDayNames;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.schedule_calendar_day, parent, false);

            mHolder = new ViewHolder();

            if (convertView != null) {
                mHolder.mSelectedDayImageView = (ImageView) convertView.findViewById(R.id.material_calendar_selected_day);
                mHolder.mTextView = (TextView) convertView.findViewById(R.id.material_calendar_day);
                mHolder.mSavedEventImageView = (ImageView) convertView.findViewById(R.id.saved_event_imageView);
                convertView.setTag(mHolder);
            }
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }

        if (mHolder.mTextView != null) {
            setCalendarDay(position);
        }

        if (mHolder.mSelectedDayImageView != null) {
            GridView gridView = (GridView) parent;
            Log.d("ITEM_CHECKED_POSITION", String.valueOf(gridView.isItemChecked(position)));
            if (gridView.isItemChecked(position)) {
                Animation feedBackAnimation = AnimationUtils.loadAnimation(mContext, R.anim.selected_day_feedback);
                mHolder.mSelectedDayImageView.setVisibility(View.VISIBLE);
                mHolder.mTextView.setTextColor(ContextCompat.getColor(mContext, R.color.MiBodyWhite));
                mHolder.mSavedEventImageView.setColorFilter(ContextCompat.getColor(mContext, R.color.MiBodyWhite));

                if (feedBackAnimation != null) {
                    mHolder.mSelectedDayImageView.startAnimation(feedBackAnimation);
                }
            } else {
                mHolder.mSelectedDayImageView.setVisibility(View.INVISIBLE);
                mHolder.mTextView.setTextColor(ContextCompat.getColor(mContext, R.color.black));
                mHolder.mSavedEventImageView.setColorFilter(ContextCompat.getColor(mContext, R.color.MiBodyOrange));
            }
        }




        if (mHolder.mSavedEventImageView != null) {
            setSavedEvent(position);
        }

        return convertView;
    }

    private void setCalendarDay(int position) {
        if (position <= mWeekDayNames - mGridViewIndexOffset + materialCalendar.mFirstDay) {
            mHolder.mTextView.setTextColor(mContext.getResources().getColor(R.color.calendar_day_text_color));
            Log.d("NO_CLICK_POSITION", String.valueOf(position));
        } else {
            //mHolder.mTextView.setTextColor(mContext.getResources().getColor(R.color.calendar_number_text_color));
        }

        switch (position) {
            case 0:
                mHolder.mTextView.setText(mContext.getResources().getString(R.string.sunday));
                mHolder.mTextView.setTypeface(Typeface.DEFAULT);
                break;

            case 1:
                mHolder.mTextView.setText(mContext.getResources().getString(R.string.monday));
                mHolder.mTextView.setTypeface(Typeface.DEFAULT);
                break;

            case 2:
                mHolder.mTextView.setText(mContext.getResources().getString(R.string.tuesday));
                mHolder.mTextView.setTypeface(Typeface.DEFAULT);
                break;

            case 3:
                mHolder.mTextView.setText(mContext.getResources().getString(R.string.wednesday));
                mHolder.mTextView.setTypeface(Typeface.DEFAULT);
                break;

            case 4:
                mHolder.mTextView.setText(mContext.getResources().getString(R.string.thursday));
                mHolder.mTextView.setTypeface(Typeface.DEFAULT);
                break;

            case 5:
                mHolder.mTextView.setText(mContext.getResources().getString(R.string.friday));
                mHolder.mTextView.setTypeface(Typeface.DEFAULT);
                break;

            case 6:
                mHolder.mTextView.setText(mContext.getResources().getString(R.string.saturday));
                mHolder.mTextView.setTypeface(Typeface.DEFAULT);
                break;

            default:
                Log.d("CURRENT_POSITION", String.valueOf(position));
                if (position < mWeekDayNames + materialCalendar.mFirstDay) {
                    Log.d("BLANK_POSITION", "This is a blank day");
                    mHolder.mTextView.setText("");
                    mHolder.mTextView.setTypeface(Typeface.DEFAULT);
                } else {
                    mHolder.mTextView.setText(String.valueOf(position - (mWeekDayNames - mGridViewIndexOffset) -
                            materialCalendar.mFirstDay));
                    //mHolder.mTextView.setTypeface(Typeface.DEFAULT_BOLD);

                    if (materialCalendar.mCurrentDay != -1) {
                        int startingPosition = mWeekDayNames - mGridViewIndexOffset + materialCalendar.mFirstDay;
                        int currentDayPosition = startingPosition + materialCalendar.mCurrentDay;

                        if (position == currentDayPosition) {
                            //mHolder.mTextView.setTextColor(mContext.getResources().getColor(R.color.calendar_number_text_color));
                            mHolder.mTextView.setTextColor(ContextCompat.getColor(mContext, R.color.MiBodyWhite));
                        } else {
                            mHolder.mTextView.setTextColor(mContext.getResources().getColor(
                                    R.color.calendar_number_text_color));
                        }
                    } else {
                        mHolder.mTextView.setTextColor(mContext.getResources().getColor(
                                R.color.calendar_number_text_color));
                    }
                }
                break;
        }
    }

    private void setSavedEvent(int position) {
        // Reset saved position indicator
        mHolder.mSavedEventImageView.setVisibility(View.INVISIBLE);

        if (materialCalendar.mFirstDay != -1 && ScheduleFragment.mSavedEventDays != null &&
                ScheduleFragment.mSavedEventDays.size() > 0) {

            int startingPosition = mWeekDayNames - mGridViewIndexOffset + materialCalendar.mFirstDay;
            Log.d("SAVED_EVENT_STARTINGPOS", String.valueOf(startingPosition));
            if (position > startingPosition) {
                for (int i = 0; i < ScheduleFragment.mSavedEventDays.size(); i++) {
                    int savedEventPosition = startingPosition + ScheduleFragment.mSavedEventDays.get(i);

                    Log.d("POSITION", String.valueOf(position));
                    Log.d("SAVED_POSITION", String.valueOf(savedEventPosition));
                    if (position == savedEventPosition) {
                        mHolder.mSavedEventImageView.setVisibility(View.VISIBLE);
                    }
                }
            } else {
                mHolder.mSavedEventImageView.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();

    }
}