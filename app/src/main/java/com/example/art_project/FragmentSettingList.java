package com.example.art_project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;

public class FragmentSettingList extends Fragment {
    View view;

    RelativeLayout rl_lang;
    RelativeLayout rl_info;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_setting_list, container, false);

        rl_lang = view.findViewById(R.id.fragment_setting_list_rl_lang);
        rl_info = view.findViewById(R.id.fragment_setting_list_rl_info);

        View.OnClickListener itemLanguageClickListener = v -> ((ActivitySettings) requireActivity()).setFragment(1);

        View.OnClickListener itemInfoClickListener = v -> ((ActivitySettings) requireActivity()).setFragment(2);

        rl_lang.setOnClickListener(itemLanguageClickListener);
        rl_info.setOnClickListener(itemInfoClickListener);

        return view;
    }
}
