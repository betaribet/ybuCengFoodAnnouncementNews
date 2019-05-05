package com.example.ybucengtabs;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;


public class Tab1Fragment extends Fragment {

    TextView textView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab1_fragment,container,false);

        textView = (TextView) view.findViewById(R.id.textTab1);
        //Toast.makeText(getActivity(), "Getting Foods...",Toast.LENGTH_SHORT).show();

        new doit().execute();
        return view;
    }
    public class doit extends AsyncTask<Void,Void,Void>{
        ArrayList<String>  list = new ArrayList<>();

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                URL link = new URL("https://aybu.edu.tr/sks/");
                Document doc = Jsoup.parse(link,3000);
                Element table = doc.select("table").first();

                Iterator<Element> itterator = table.select("td").iterator();

                itterator.next();
                while(itterator.hasNext()){
                    list.add(itterator.next().text());
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            StringBuilder food= new StringBuilder();
            for(int i=0;i<list.size();i++){
                food.append("\n").append(list.get(i));
            }
            textView.setText(food.toString());
        }
        }
    }



