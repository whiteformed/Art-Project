package com.example.art_project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;

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
                LocaleHelper.setLocalePrefs(Objects.requireNonNull(getActivity()), getString(R.string.pref_locale_en));
            }
        };

        View.OnClickListener itemRuClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocaleHelper.setLocalePrefs(Objects.requireNonNull(getActivity()), getString(R.string.pref_locale_ru));
            }
        };

        rl_en.setOnClickListener(itemEnClickListener);
        rl_ru.setOnClickListener(itemRuClickListener);

        return view;
    }
}
