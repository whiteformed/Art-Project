package com.example.art_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;

public class FragmentLanguageList extends Fragment {
    View view;

    RelativeLayout rl_en;
    RelativeLayout rl_ru;

    RadioButton rb_language_en_selected;
    RadioButton rb_language_ru_selected;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_language_list, container, false);

        rl_en = view.findViewById(R.id.fragment_language_list_rl_en);
        rl_ru = view.findViewById(R.id.fragment_language_list_rl_ru);

        rb_language_en_selected = view.findViewById(R.id.fragment_language_list_rb_language_en_selected);
        rb_language_ru_selected = view.findViewById(R.id.fragment_language_list_rb_language_ru_selected);

        int selected = getSelectedLanguage();

        View.OnClickListener itemEnClickListener = v -> {
            if (selected == 0) return;

            PrefsHelper.setLocalePrefs(requireActivity(), getString(R.string.pref_locale_en));
            getSelectedLanguage();

            Intent intent = new Intent(getContext(), ActivityPersons.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        };

        View.OnClickListener itemRuClickListener = v -> {
            if (selected == 1) return;

            PrefsHelper.setLocalePrefs(requireActivity(), getString(R.string.pref_locale_ru));
            getSelectedLanguage();

            Intent intent = new Intent(getContext(), ActivityPersons.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        };

        rl_en.setOnClickListener(itemEnClickListener);
        rl_ru.setOnClickListener(itemRuClickListener);

        rb_language_en_selected.setOnClickListener(itemEnClickListener);
        rb_language_ru_selected.setOnClickListener(itemRuClickListener);

        return view;
    }

    int getSelectedLanguage() {
        if (PrefsHelper.getLocalePrefs(requireActivity()).equals(getString(R.string.pref_locale_en))) {
            rb_language_en_selected.setChecked(true);
            rb_language_ru_selected.setChecked(false);

            return 0;
        } else if (PrefsHelper.getLocalePrefs(requireActivity()).equals(getString(R.string.pref_locale_ru))) {
            rb_language_en_selected.setChecked(false);
            rb_language_ru_selected.setChecked(true);

            return 1;
        }

        return 0;
    }
}
