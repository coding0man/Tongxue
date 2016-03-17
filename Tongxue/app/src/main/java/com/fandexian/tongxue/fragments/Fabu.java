package com.fandexian.tongxue.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fandexian.tongxue.R;

/**
 * Created by dexian.fan on 2016/3/17.
 */
public class Fabu extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fabu,container,false);
    }
}
