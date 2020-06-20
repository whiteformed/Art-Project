package com.example.art_project;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;

import java.util.Objects;

public class FragmentLanguageList extends Fragment {
    View view;

    RelativeLayout rl_en;
    RelativeLayout rl_ru;

    ImageView iv_language_en_selected;
    ImageView iv_language_ru_selected;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_language_list, container, false);

        rl_en = view.findViewById(R.id.fragment_language_list_rl_en);
        rl_ru = view.findViewById(R.id.fragment_language_list_rl_ru);

        iv_language_en_selected = view.findViewById(R.id.fragment_language_list_iv_language_en_selected);
        iv_language_ru_selected = view.findViewById(R.id.fragment_language_list_iv_language_ru_selected);

        selectLanguage();

        View.OnClickListener itemEnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocaleHelper.setLocalePrefs(Objects.requireNonNull(getActivity()), getString(R.string.pref_locale_en));
                selectLanguage();
            }
        };

        View.OnClickListener itemRuClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocaleHelper.setLocalePrefs(Objects.requireNonNull(getActivity()), getString(R.string.pref_locale_ru));
                selectLanguage();
            }
        };

        rl_en.setOnClickListener(itemEnClickListener);
        rl_ru.setOnClickListener(itemRuClickListener);

        return view;
    }

    void selectLanguage() {
        if (LocaleHelper.getLocalePrefs(Objects.requireNonNull(getActivity())).equals(getString(R.string.pref_locale_en))) {
            iv_language_en_selected.setVisibility(View.VISIBLE);
            iv_language_ru_selected.setVisibility(View.INVISIBLE);
        }
        else if (LocaleHelper.getLocalePrefs(Objects.requireNonNull(getActivity())).equals(getString(R.string.pref_locale_ru))) {
            iv_language_en_selected.setVisibility(View.INVISIBLE);
            iv_language_ru_selected.setVisibility(View.VISIBLE);
        }
    }
}
