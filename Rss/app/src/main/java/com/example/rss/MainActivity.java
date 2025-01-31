package com.example.rss;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView rssListView;
    private ArrayList<String> rssItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rssListView = findViewById(R.id.rssListView);
        rssItems = new ArrayList<>();

        // Replace with your RSS feed URL
        String rssUrl = "https://codingconnect.net/feed";

        new FetchRssFeed().execute(rssUrl);
    }

    private class FetchRssFeed extends AsyncTask<String, Void, ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(String... urls) {
            ArrayList<String> items = new ArrayList<>();
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                InputStream inputStream = connection.getInputStream();
                SAXParserFactory factory = SAXParserFactory.newInstance();
                SAXParser saxParser = factory.newSAXParser();
                RSSHandler handler = new RSSHandler(items);
                XMLReader xmlReader = saxParser.getXMLReader();
                xmlReader.setContentHandler(handler);
                xmlReader.parse(new InputSource(inputStream));

                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return items;
        }

        @Override
        protected void onPostExecute(ArrayList<String> items) {
            super.onPostExecute(items);
            rssItems.clear();
            rssItems.addAll(items);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, rssItems);
            rssListView.setAdapter(adapter);
        }
    }
}