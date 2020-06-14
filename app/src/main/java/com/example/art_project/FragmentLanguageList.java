package com.example.art_project;

import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;

import java.util.Locale;
import java.util.Objects;

public class FragmentLanguageList extends Fragment {
    View view;

    RelativeLayout rl_en;
    RelativeLayout rl_ru;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_language_list, container, false);

        rl_en = view.findViewById(R.id.fragment_language_list_rl_en);
        rl_ru = view.findViewById(R.id.fragment_language_list_rl_ru);

        View.OnClickListener itemEnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocaleHelper.setLocalePrefs(Objects.requireNonNull(getActivity()),"EN");
            }
        };

        View.OnClickListener itemRuClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocaleHelper.setLocalePrefs(Objects.requireNonNull(getActivity()),"RU");
            }
        };

        rl_en.setOnClickListener(itemEnClickListener);
        rl_ru.setOnClickListener(itemRuClickListener);

        return view;
    }
}
