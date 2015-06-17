package com.eboodnero.crossman;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.Format;
import java.util.concurrent.TimeUnit;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link hiit_timer.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link hiit_timer#newInstance} factory method to
 * create an instance of this fragment.
 */
public class hiit_timer extends Fragment {

    View FragmentView;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment hiit_timer.
     */
    // TODO: Rename and change types and number of parameters
    public static hiit_timer newInstance(String param1, String param2) {
        hiit_timer fragment = new hiit_timer();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public hiit_timer() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentView = inflater.inflate(R.layout.fragment_hiit_timer, container, false);

        Button startButton = (Button) FragmentView.findViewById(R.id.startButton);

        Button upHourIncrement = (Button) FragmentView.findViewById(R.id.up_hour_increment);
        Button downHourIncrement = (Button) FragmentView.findViewById(R.id.down_hour_increment);
        EditText hourView = (EditText) FragmentView.findViewById(R.id.hour_text_view);


        Button upSecondIncrement = (Button) FragmentView.findViewById(R.id.up_second_increment);
        Button downSecondIncrement = (Button) FragmentView.findViewById(R.id.down_second_increment);
        EditText secondView = (EditText) FragmentView.findViewById(R.id.second_text_view);

        Button upMinuteIncrement = (Button) FragmentView.findViewById(R.id.up_minute_increment);
        Button downMinuteIncrement = (Button) FragmentView.findViewById(R.id.down_minute_increment);
        EditText minuteView = (EditText) FragmentView.findViewById(R.id.minute_text_view);

        setStartButtonListener(startButton);
        setHourListeners(upHourIncrement, hourView, downHourIncrement);
        setSecondListeners(upSecondIncrement, secondView, downSecondIncrement);
        setMinuteListeners(upMinuteIncrement, minuteView, downMinuteIncrement);


        return FragmentView;
    }

    @Override
    public void onResume() {
        super.onResume();
        EditText hourView = (EditText) FragmentView.findViewById(R.id.hour_text_view);

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    public void startTimer() {

        final int hours, minutes, seconds;

        final EditText hoursView = (EditText) getView().findViewById(R.id.hour_text_view);
        final EditText minutesView = (EditText) getView().findViewById(R.id.minute_text_view);
        final EditText secondsView = (EditText) getView().findViewById(R.id.second_text_view);

        hours = Integer.valueOf(hoursView.getText().toString());
        minutes = Integer.valueOf(minutesView.getText().toString());
        seconds = Integer.valueOf(secondsView.getText().toString());

        InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);

        new CountDownTimer(hours * 3600000 + minutes * 60000 + seconds * 1000, 1000) { // adjust the milli seconds here
            public void onTick(long millisUntilFinished) {

                hoursView.setText("" + TimeUnit.MILLISECONDS.toHours(millisUntilFinished));
                minutesView.setText("" + (TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                        TimeUnit.MILLISECONDS.toHours(millisUntilFinished))));
                secondsView.setText("" +
                                (TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)))
                );

            }

            public void onFinish() {
                hoursView.setText("0");
                minutesView.setText("0");
                secondsView.setText("0");
                final ToneGenerator tg = new ToneGenerator(AudioManager.STREAM_NOTIFICATION, 500);
                tg.startTone(ToneGenerator.TONE_PROP_BEEP);
            }
        }.start();
    }

    private void setStartButtonListener(Button startButton){
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimer();
            }
        });
    }

    private void setHourListeners(Button upHourIncrement, final EditText hourView, Button downHourIncrement){
        upHourIncrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hour = Integer.valueOf(hourView.getText().toString()) + 1;
                hourView.setText("" +  hour);
            }
        });
        downHourIncrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hour = Integer.valueOf(hourView.getText().toString()) - 1;
                if(hour + 1 > 0) hourView.setText("" + hour);
            }
        });

        hourView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(hourView.getText().toString().equals("0")) hourView.setText("");
                return false;
            }
        });
    }
    private void setSecondListeners(Button upSecondIncrement, final EditText secondView, Button downSecondIncrement){
        upSecondIncrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int second = Integer.valueOf(secondView.getText().toString()) + 1;
                secondView.setText("" +  second);
            }
        });
        downSecondIncrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int second = Integer.valueOf(secondView.getText().toString()) - 1;
                if (second + 1 > 0) secondView.setText("" + second);
            }
        });
        secondView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(secondView.getText().equals("0")){
                    secondView.setText("");
                }
                return false;
            }
        });
    }
    private void setMinuteListeners(Button upMinuteIncrement, final EditText minuteView, Button downMinuteIncrement){
        upMinuteIncrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int minute = Integer.valueOf(minuteView.getText().toString()) + 1;
                minuteView.setText("" +  minute);
            }
        });
        downMinuteIncrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int minute = Integer.valueOf(minuteView.getText().toString()) - 1;
                if(minute + 1 > 0) minuteView.setText("" + minute);
            }
        });
        minuteView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(minuteView.getText().toString().equals("0")) minuteView.setText("");
                return false;
            }
        });
    }
}
