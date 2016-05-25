package com.app.welcome.welcomeappfinal1;

import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by 10089683 on 03/05/2016.
 */
public class NewsFragment extends Fragment {

    ListView lvNewsList;
    ArrayList<String> newsHeadlines = new ArrayList<String>();

    private JSONObject resultObject;
    private String resultString;
    private String title;
    OnNewsSetListener onNewsSetListener;
    private String date;
    private String section;
    private String url;

    GetNews getNews = new GetNews();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_layout, container, false);

        lvNewsList = (ListView)view.findViewById(R.id.newsList);

        lvNewsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    title = resultObject.getJSONObject("response").getJSONArray("results").getJSONObject(position).getString("webTitle");
                    date = resultObject.getJSONObject("response").getJSONArray("results").getJSONObject(position).getString("webPublicationDate");
                    section = resultObject.getJSONObject("response").getJSONArray("results").getJSONObject(position).getString("sectionName");
                    url = resultObject.getJSONObject("response").getJSONArray("results").getJSONObject(position).getString("webUrl");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                onNewsSetListener.showNewsDetails(title, date, section, url);
            }
        });
        getNews.execute();

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private class GetNews extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {

            String url2 = "http://content.guardianapis.com/search?api-key=79ddaeae-3123-4596-a11a-c7abdb3f870a";

            HttpClient httpClient = null;
            try {

                httpClient = new DefaultHttpClient();
                HttpResponse data = httpClient.execute(new HttpGet(url2));
                HttpEntity entity = data.getEntity();
                resultString = EntityUtils.toString(entity, "UTF8");

                if (resultString != null) {
                    resultObject = new JSONObject(resultString);

                    for(int i = 0; i < resultObject.getJSONObject("response").getJSONArray("results").length(); i++) {
                        title = resultObject.getJSONObject("response").getJSONArray("results").getJSONObject(i).getString("webTitle");
                        newsHeadlines.add(title);
                        System.out.println(resultObject.toString());
                        System.out.print("++++++++++++++++++++++" + title);
                    }




                }

            } catch (Exception e) {
                System.out.println("***************" + e.toString());

            } finally {
                httpClient.getConnectionManager().shutdown();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result){
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.activity_list_cell, R.id.txtListCell, newsHeadlines);
            lvNewsList.setAdapter(arrayAdapter);
            ((ArrayAdapter) lvNewsList.getAdapter()).notifyDataSetChanged();
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        onNewsSetListener = (OnNewsSetListener)activity;
    }
}
