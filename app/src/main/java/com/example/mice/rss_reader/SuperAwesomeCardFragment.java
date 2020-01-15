package com.example.mice.rss_reader;

/**
 * Created by delaroystudios on 7/8/2016.
 */

import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.mice.rss_reader.Adapter.CustomAdapter;
import com.example.mice.rss_reader.modal.Docbao;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.paperdb.Paper;

public class SuperAwesomeCardFragment extends Fragment {

    private static final String ARG_POSITION = "position";

    private int position;
    ListView listView;
    ArrayList<String> listurl = new ArrayList();

    ImageView btnmenu, btnoldnews, btnrefresh;
    CustomAdapter customAdapter;
    ArrayList<Docbao> arrayListDocbao;
    ArrayList<Docbao> arrayListfilterDocbao = new ArrayList<>();
    EditText searchView;
    ImageView imgtt;
    TextView txttitle;
    private int mode = 1;
    Database database;
    int a = 0;


    public static SuperAwesomeCardFragment newInstance(int position) {
        SuperAwesomeCardFragment f = new SuperAwesomeCardFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        Log.d("position", "newInstance: "+position);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        position = getArguments().getInt(ARG_POSITION);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_main, container, false);
        Paper.init(getContext());
        Mapped(view);
        addurl();
        getData();

        VNExpressCategory.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Log.d("dsdsd", "onQueryTextChange: " + s);
                customAdapter.notifyDataSetChanged();
                customAdapter.filter(s);
                return false;
            }
        });
        return view;
    }



//    @Override
//    public void onResume() {
//        getData();
//        Log.d("dsdsdsdsd", "onResume: "+position);
//        listurl.get(position);
//        new readRSSVnExpress().execute();
//        super.onResume();
//    }

    private void getData() {
//        database = new Database(getContext(), "news.sqlite", null, 1);
//        database.QueryData("CREATE TABLE IF NOT EXISTS TinTuc(Id INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                "Title VARCHAR," +
//                "Link VARCHAR," +
//                "Image VARCHAR," +
//                "PubDate VARCHAR," +
//                "Description VARCHAR)");
        arrayListDocbao = new ArrayList<Docbao>();
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final String lienket = listurl.get(position);
                Log.d("dsdsdsdsd", "onResume: "+position);
                Log.d("MAnh", "run: " + lienket);
                new readRSSVnExpress().execute(lienket);

                VNExpressCategory.imgrefresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getContext(), "dsds", Toast.LENGTH_SHORT).show();

                        new readRSSVnExpress().execute(lienket);
                    }
                });
            }
        });

    }

    class readRSSVnExpress extends AsyncTask<String, Integer, String> {//<đường link,quá trình thực hiện,kết quả trả về>

        @Override
        //hàm xử lý

        public String doInBackground(String... strings) {
            return docNoiDung_Tu_URL(strings[0]);

        }

        @Override
        //hàm trả kết quả
        public void onPostExecute(String s) {
            XMLDOMParser parser = new XMLDOMParser();
            //chứa toàn bộ nội dung của rss để đọc
            try {


            Document document = parser.getDocument(s);
            NodeList nodeListItem = document.getElementsByTagName("item");
            NodeList nodeListDescription = document.getElementsByTagName("description");
            String hinhanh = "";
            String title = "";
            String link = "";
            String date = "";
            String description = "";


            for (int i = 0; i < nodeListItem.getLength(); i++) {
                String cdata = nodeListDescription.item(i + 1).getTextContent();//lấy giá trị trong thẻ CDATA

                String segments[] = cdata.split("</br>");

                Pattern p;
                //lấy link hình ảnh trong CDATA
                if (cdata.contains("data-original")) {
                    p = Pattern.compile("<img[^>]+data-original\\s*=\\s*['\"]([^'\"]+)['\"][^>]*");
                } else {
                    p = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*");
                }

                //kiểm tra nếu hình ảnh trùng với group item thì nhóm lại
                Matcher matcher = p.matcher(cdata);
                if (matcher.find()) {
                    hinhanh = matcher.group(1);
                }

                Element element = (Element) nodeListItem.item(i);

                title = parser.getValue(element, "title");//lấy nội dung trong thẻ title
                link = parser.getValue(element, "link");//lấy link trong thẻ link
                date = parser.getValue(element, "pubDate");//lấy nội dung trong thẻ pubDate*/

                description = segments[segments.length - 1];//lấy phần tóm tắt ở trong CDATA bên trong thẻ Description

                arrayListDocbao.add(new Docbao(title.replace("\"", "'"), link, hinhanh, date, description.replace("\"", "'")));
            }
            for (Docbao person : arrayListDocbao) {

                if (person.image != "") {

                    arrayListfilterDocbao.add(person);
                    Paper.book().write("news", arrayListfilterDocbao);
                }

            }
//            ArrayList<Docbao> locolist = new ArrayList<>();
//            locolist = Paper.book().read("news");
            customAdapter = new CustomAdapter(getActivity(), android.R.layout.simple_list_item_1, arrayListfilterDocbao);
            listView.setAdapter(customAdapter);

            super.onPostExecute(s);
            }catch(Exception e)    {
                Log.d("dsdsdsd", "onPostExecute: ");
                ArrayList<Docbao> locolist = new ArrayList<>();
                locolist = Paper.book().read("news");
                customAdapter = new CustomAdapter(getActivity(), android.R.layout.simple_list_item_1, locolist);
                e.printStackTrace();
            }

        }


    }

    private void Mapped(View v) {
        listView = v.findViewById(R.id.listview);

        btnoldnews = v.findViewById(R.id.btnOldNews);
        btnrefresh = v.findViewById(R.id.btnRefresh);
        searchView = v.findViewById(R.id.searchView);
        imgtt = v.findViewById(R.id.imgtt);
        txttitle = v.findViewById(R.id.txttitlett);

    }

    private void addurl() {
        listurl.add("https://vnexpress.net/rss/giai-tri.rss");
        listurl.add("https://vnexpress.net/rss/du-lich.rss");
        listurl.add("https://vnexpress.net/rss/the-thao.rss");
        listurl.add("https://vnexpress.net/rss/the-gioi.rss");
        listurl.add("https://vnexpress.net/rss/phap-luat.rss");
        listurl.add("https://vnexpress.net/rss/giao-duc.rss");
        listurl.add("https://vnexpress.net/rss/oto-xe-may.rss");
        listurl.add("https://vnexpress.net/rss/khoa-hoc.rss");
    }

    public String docNoiDung_Tu_URL(String theUrl) {
        StringBuilder content = new StringBuilder();
        try {
            // create a url object
            URL url = new URL(theUrl);

            // create a urlconnection object
            URLConnection urlConnection = url.openConnection();

            // wrap the urlconnection in a bufferedreader
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String line;

            // read from the urlconnection via the bufferedreader
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mode = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
//        getActivity().rel
    }
}