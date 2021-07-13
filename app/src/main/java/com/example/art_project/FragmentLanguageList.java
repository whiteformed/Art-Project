package com.example.art_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;

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

        View.OnClickListener itemEnClickListener = v -> {
            LocaleHelper.setLocalePrefs(requireActivity(), getString(R.string.pref_locale_en));
            selectLanguage();

            Intent intent = new Intent(getContext(), ActivityPersons.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        };

        View.OnClickListener itemRuClickListener = v -> {
            LocaleHelper.setLocalePrefs(requireActivity(), getString(R.string.pref_locale_ru));
            selectLanguage();

            Intent intent = new Intent(getContext(), ActivityPersons.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        };

        rl_en.setOnClickListener(itemEnClickListener);
        rl_ru.setOnClickListener(itemRuClickListener);

        return view;
    }

    void selectLanguage() {
        if (LocaleHelper.getLocalePrefs(requireActivity()).equals(getString(R.string.pref_locale_en))) {
            iv_language_en_selected.setVisibility(View.VISIBLE);
            iv_language_ru_selected.setVisibility(View.INVISIBLE);
        } else if (LocaleHelper.getLocalePrefs(requireActivity()).equals(getString(R.string.pref_locale_ru))) {
            iv_language_en_selected.setVisibility(View.INVISIBLE);
            iv_language_ru_selected.setVisibility(View.VISIBLE);
        }
    }
}
