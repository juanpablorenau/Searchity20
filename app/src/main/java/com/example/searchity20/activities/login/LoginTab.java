package com.example.searchity20.activities.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.searchity20.R;

public class LoginTab extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle){
        ViewGroup vg = (ViewGroup) inflater.inflate(R.layout.login_tab, viewGroup, false);
        return vg;
    }
}
