package com.jaguarlandrover.auto.remote.vehicleentry;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.location.GpsStatus;
import android.os.Handler;
import android.os.Looper;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;


/**
 * A placeholder fragment containing a simple view.
 */
public class keyShareActivityFragment extends Fragment {
    int year_x, month_x, day_x, hour_x, min_x;
    String am_pm;
    TextView startdate, enddate, starttime, endtime;
    static final int dateDialog = 0;
    static final int timeDialog=1;
    private TextView activeDialog;
    private TextView activeTime;
    private Button shareKeyBtn;
    private Switch lock_unlock;
    private Switch enginestart;
    ViewPager userPages;
    ViewPager carPages;
    int[] users={R.drawable.bjamal,
            R.drawable.llesavre,
            R.drawable.dthiriez,
            R.drawable.arodriguez};

    int[] vehicles = {R.drawable.sciontc};

    private ShareFragmentButtonListener buttonListener;

    public keyShareActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_key_share, container, false);

        shareKeyBtn = (Button) view.findViewById(R.id.ShareBtn);
        lock_unlock = (Switch) view.findViewById(R.id.lock_unlock);
        enginestart = (Switch) view.findViewById(R.id.engine);

        shareKeyBtn.setOnClickListener(l);
        buttonListener = (ShareFragmentButtonListener) getActivity();

        return view;

    }

    public interface ShareFragmentButtonListener {
        public void onButtonCommand(View v);
    }


    private View.OnClickListener l = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.ShareBtn:
                    buttonListener.onButtonCommand(v);
                    break;
            }
        }
    };


    public void showDialog(){
        final Calendar cal = Calendar.getInstance();
        year_x = cal.get(Calendar.YEAR);
        month_x = cal.get(Calendar.MONTH);
        month_x = month_x+1;
        day_x = cal.get(Calendar.DAY_OF_MONTH);

        startdate = new TextView(getActivity());
        enddate = new TextView(getActivity());
        starttime = new TextView(getActivity());
        endtime = new TextView(getActivity());

        startdate =(TextView)getActivity().findViewById(R.id.startlblDate);
        enddate = (TextView) getActivity().findViewById(R.id.endlblDate);
        starttime = (TextView) getActivity().findViewById(R.id.starttimeLbl);
        endtime = (TextView) getActivity().findViewById(R.id.endtimeLbl);

        startdate.setText(month_x+"/"+day_x+"/"+year_x);
        enddate.setText(month_x + "/" + day_x + "/" + year_x);

        startdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeDialog = startdate;
                getActivity().showDialog(dateDialog);
            }
        });

        enddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeDialog = enddate;
                getActivity().showDialog(dateDialog);
            }
        });

        starttime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeTime = starttime;
                getActivity().showDialog(timeDialog);
            }
        });
        endtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeTime = endtime;
                getActivity().showDialog(timeDialog);
            }
        });

    }

    private void updateDisplay(TextView dateDisplay) {
        dateDisplay.setText(month_x+"/"+day_x+"/"+year_x);
    }

    private void updateTime(TextView timeDisplay){
        String newMin;
        newMin = String.valueOf(min_x);
        if (min_x <10)
        {
            newMin = "0"+String.valueOf(min_x);
        }
        timeDisplay.setText(hour_x + ":" + newMin + " " + am_pm);
    }

    public void showSelect(){

        ScrollPageAdapter userPageAdapter = new ScrollPageAdapter(getActivity(), users);
        userPages = (ViewPager) getActivity().findViewById(R.id.userscroll);
        userPages.setOffscreenPageLimit(2);
        userPages.setPageMargin(-500);
        userPages.setHorizontalFadingEdgeEnabled(true);
        userPages.setFadingEdgeLength(50);
        userPages.setAdapter(userPageAdapter);

        ScrollPageAdapter carPageAdapter = new ScrollPageAdapter(getActivity(), vehicles);
        carPages = (ViewPager) getActivity().findViewById(R.id.vehiclescroll);
        userPages.setOffscreenPageLimit(2);
        carPages.setPageMargin(-500);
        carPages.setHorizontalFadingEdgeEnabled(true);
        carPages.setFadingEdgeLength(50);
        carPages.setAdapter(carPageAdapter);

    }


    private DatePickerDialog.OnDateSetListener dpickerListener
            =new DatePickerDialog.OnDateSetListener(){
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayofMonth){
            year_x = year;
            month_x = monthOfYear+1;
            day_x = dayofMonth;
            updateDisplay(activeDialog);

        }
    };

    private TimePickerDialog.OnTimeSetListener tpickerListener = new TimePickerDialog.OnTimeSetListener(){
        @Override
        public void onTimeSet(TimePicker view,int hourOfDay, int minute ){
            am_pm = "am";
            hour_x = hourOfDay;

            if(hourOfDay > 11)
            {
                am_pm = "pm";
                if(hourOfDay == 12)
                {}
                else {
                    hour_x = hourOfDay - 12;
                }
            }
            if(hourOfDay == 0)
            {
                hour_x=hourOfDay+12;
            }

            min_x = minute;

            updateTime(activeTime);
        }
    };

    public DatePickerDialog.OnDateSetListener getdplistener(){
        return dpickerListener;
    }
    public TimePickerDialog.OnTimeSetListener gettplistener(){return tpickerListener;}

    public int getyear(){return year_x;}
    public int getmonth(){return month_x;}
    public int getday(){return day_x;}
    public int gethour(){return hour_x;}
    public int getmin(){return min_x;}
}
