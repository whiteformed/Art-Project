package com.example.art_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;

public class FragmentThemeList extends Fragment {
    View view;

    RelativeLayout rl_light;
    RelativeLayout rl_dark;

    ImageView iv_theme_light_selected;
    ImageView iv_theme_dark_selected;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_theme_list, container, false);

        rl_light = view.findViewById(R.id.fragment_themes_rl_light);
        rl_dark = view.findViewById(R.id.fragment_themes_rl_dark);

        iv_theme_light_selected = view.findViewById(R.id.fragment_themes_iv_theme_light_selected);
        iv_theme_dark_selected = view.findViewById(R.id.fragment_themes_iv_theme_dark_selected);

        int selected = selectedTheme();

        View.OnClickListener itemLightClickListener = v -> {
            if (selected == 0) return;

            PrefsHelper.setThemePrefs(requireActivity(), getString(R.string.pref_theme_light));
            selectedTheme();

            Intent intent = new Intent(getContext(), ActivityPersons.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        };

        View.OnClickListener itemDarkClickListener = v -> {
            if (selected == 1) return;

            PrefsHelper.setThemePrefs(requireActivity(), getString(R.string.pref_theme_dark));
            selectedTheme();

            Intent intent = new Intent(getContext(), ActivityPersons.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        };

        rl_light.setOnClickListener(itemLightClickListener);
        rl_dark.setOnClickListener(itemDarkClickListener);

        return view;
    }

    int selectedTheme() {
        if (PrefsHelper.getThemePrefs(requireActivity()).equals(getString(R.string.pref_theme_light))) {
            iv_theme_light_selected.setVisibility(View.VISIBLE);
            iv_theme_dark_selected.setVisibility(View.INVISIBLE);

            return 0;
        } else if (PrefsHelper.getThemePrefs(requireActivity()).equals(getString(R.string.pref_theme_dark))) {
            iv_theme_light_selected.setVisibility(View.INVISIBLE);
            iv_theme_dark_selected.setVisibility(View.VISIBLE);

            return 1;
        }

        return 0;
    }
}
