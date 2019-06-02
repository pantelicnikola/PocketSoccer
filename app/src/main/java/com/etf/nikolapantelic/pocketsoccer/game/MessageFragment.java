package com.etf.nikolapantelic.pocketsoccer.game;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.etf.nikolapantelic.pocketsoccer.R;

public class MessageFragment extends Fragment {

    private TextView textViewMessage;
    private String message;

    public MessageFragment() {
        // Required empty public constructor
        int i = 90;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        textViewMessage = view.findViewById(R.id.textViewMessage);
        textViewMessage.setText(message);
        return view;
    }

    public void setText(String message) {
        textViewMessage.setText(message);
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
