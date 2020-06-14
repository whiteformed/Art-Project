package com.example.art_project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;

import java.util.Objects;

public class FragmentSettingList extends Fragment {
    View view;

    RelativeLayout rl_lang;
    RelativeLayout rl_info;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_setting_list, container, false);

        rl_lang = view.findViewById(R.id.fragment_setting_list_rl_lang);
        rl_info = view.findViewById(R.id.fragment_setting_list_rl_info);

        View.OnClickListener itemLanguageClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ActivitySettings) Objects.requireNonNull(getActivity())).setFragment(1);
            }
        };

        View.OnClickListener itemInfoClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ActivitySettings) Objects.requireNonNull(getActivity())).setFragment(2);
            }
        };

        rl_lang.setOnClickListener(itemLanguageClickListener);
        rl_info.setOnClickListener(itemInfoClickListener);

        return view;
    }
}
