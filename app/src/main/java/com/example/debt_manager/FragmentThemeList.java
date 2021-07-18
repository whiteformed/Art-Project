package com.example.debt_manager;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;

public class FragmentThemeList extends Fragment {
    View view;

    RelativeLayout rl_light;
    RelativeLayout rl_dark;

    RadioButton rb_theme_light_selected;
    RadioButton rb_theme_dark_selected;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_theme_list, container, false);

        rl_light = view.findViewById(R.id.fragment_themes_rl_light);
        rl_dark = view.findViewById(R.id.fragment_theme_list_rl_dark);

        rb_theme_light_selected = view.findViewById(R.id.fragment_theme_list_rb_theme_light_selected);
        rb_theme_dark_selected = view.findViewById(R.id.fragment_theme_list_rb_theme_dark_selected);

        int selected = getSelectedTheme();

        View.OnClickListener itemLightClickListener = v -> {
            if (selected == 0) return;

            PrefsHelper.setThemePrefs(requireActivity(), getString(R.string.pref_theme_light));
            getSelectedTheme();

            Intent intent = new Intent(getContext(), ActivityPersons.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        };

        View.OnClickListener itemDarkClickListener = v -> {
            if (selected == 1) return;

            PrefsHelper.setThemePrefs(requireActivity(), getString(R.string.pref_theme_dark));
            getSelectedTheme();

            Intent intent = new Intent(getContext(), ActivityPersons.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        };

        rl_light.setOnClickListener(itemLightClickListener);
        rl_dark.setOnClickListener(itemDarkClickListener);

        rb_theme_light_selected.setOnClickListener(itemLightClickListener);
        rb_theme_dark_selected.setOnClickListener(itemDarkClickListener);

        return view;
    }

    int getSelectedTheme() {
        if (PrefsHelper.getThemePrefs(requireActivity()).equals(getString(R.string.pref_theme_light))) {
            rb_theme_light_selected.setChecked(true);
            rb_theme_dark_selected.setChecked(false);

            return 0;
        } else if (PrefsHelper.getThemePrefs(requireActivity()).equals(getString(R.string.pref_theme_dark))) {
            rb_theme_light_selected.setChecked(false);
            rb_theme_dark_selected.setChecked(true);

            return 1;
        }

        return 0;
    }
}
