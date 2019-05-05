package com.example.ybucengtabs;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;


public class Tab3Fragment extends Fragment {
    private ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab3_fragment,container,false);
        listView = (ListView) view.findViewById(R.id.list);
        //Toast.makeText(getActivity(), "Getting news...",Toast.LENGTH_SHORT).show();
        new Tab3Fragment.doit().execute();
        return view ;
    }

    public class doit extends AsyncTask<Void,Void,Void> {
        ArrayList<String> list = new ArrayList<>();
        ArrayList<String> links = new ArrayList<>();

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Document doc = Jsoup.connect("https://aybu.edu.tr/muhendislik/bilgisayar/").get();
                Element news = doc.select("div.cnContent").first();

                for (Element div : news.select("div.cncItem")) {
                    list.add(div.text());
                    System.out.println("Value 1: " + div.select("a").attr("href"));
                    links.add(div.select("a").attr("href"));
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            ArrayAdapter adapter = new ArrayAdapter<>(Objects.requireNonNull(getActivity()),android.R.layout.simple_list_item_1,list);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position,
                                        long id) {
                    String[] link = new String[10000];
                    for(int i=0 ; i<links.size() ; i++){
                        link[i]="https://aybu.edu.tr/muhendislik/bilgisayar/"+links.get(i);
                    }
                    Uri uri = Uri.parse(link[position]);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);

                }
            });
        }
    }
}

