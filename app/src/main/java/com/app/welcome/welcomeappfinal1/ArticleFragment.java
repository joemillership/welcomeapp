package com.app.welcome.welcomeappfinal1;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by 10089683 on 10/05/2016.
 */
public class ArticleFragment extends Fragment {
    View view;
    private String title;
    private String date;
    private String section;
    private String url;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.article_layout, container, false);

        title = getArguments().getString("title");
        date = getArguments().getString("date");
        section = getArguments().getString("section");
        url = getArguments().getString("url");

        TextView tvTitle = (TextView)view.findViewById(R.id.title);
        tvTitle.setText(title);

        TextView tvDate = (TextView)view.findViewById(R.id.date);
        tvDate.setText(date);

        TextView tvSection = (TextView)view.findViewById(R.id.section);
        tvSection.setText(section);


        Button loadarticle = (Button)view.findViewById(R.id.webBtn);
        loadarticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        return view;
    }
}
