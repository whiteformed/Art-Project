package com.example.art_project;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class FragmentInformation extends Fragment {
    View view;
    TextView tv_github;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_info, container, false);

        tv_github = view.findViewById(R.id.fragment_info_tv_github);

        View.OnClickListener onLinkClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/whiteformed/Debt-Manager"));
                startActivity(intent);
            }
        };

        tv_github.setOnClickListener(onLinkClickListener);

        return view;
    }
}
