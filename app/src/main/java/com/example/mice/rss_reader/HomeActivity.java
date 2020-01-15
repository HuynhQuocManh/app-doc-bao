package com.example.mice.rss_reader;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import android.view.View;
import android.widget.AdapterView;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

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
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HomeActivity extends AppCompatActivity {

    ListView listView;
    ImageView btnmenu,btnoldnews, btnrefresh;
    CustomAdapter customAdapter;
    ArrayList<Docbao> arrayListDocbao;
    EditText searchView;


    Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listview);

        btnoldnews=findViewById(R.id.btnOldNews);
        btnrefresh=findViewById(R.id.btnRefresh);



        //khởi tạo database news
        database =new Database(this, "news.sqlite",null,1);

        //tạo bảng TinTuc
        database.QueryData("CREATE TABLE IF NOT EXISTS TinTuc(Id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "Title VARCHAR," +
                "Link VARCHAR," +
                "Image VARCHAR," +
                "PubDate VARCHAR," +
                "Description VARCHAR)");

        btnoldnews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayListDocbao.clear();
                Cursor dataNew = database.GetData("SELECT * FROM TinTuc");
                while (dataNew.moveToNext())
                {
                    String tieude = dataNew.getString(1);
                    String link = dataNew.getString(2);
                    String image = dataNew.getString(3);
                    String pubdate = dataNew.getString(4);
                    String description = dataNew.getString(5);
                    arrayListDocbao.add(new Docbao(tieude,link,image,pubdate,description));
                }
                customAdapter = new CustomAdapter(HomeActivity.this,android.R.layout.simple_list_item_1,arrayListDocbao);
                listView.setAdapter(customAdapter);
            }
        });

        btnrefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=getIntent();
                String newsType = intent.getStringExtra("newsType");
                switch (newsType) {
                    case "VNExpress":{
                        String lienket = intent.getStringExtra("linkDanhMucVnExpress");
                        new readRSSVnExpress().execute(lienket);
                    }break;
                    case "TuoiTre":{
                        String lienket = intent.getStringExtra("linkDanhMucTuoiTre");
                        new readRSSTuoiTre().execute(lienket);
                    }break;
                    case "VietNamNet":{
                        String lienket = intent.getStringExtra("linkDanhMucVietnamnet");
                        new readRSSVietnamnet().execute(lienket);
                    }break;
                }
            }
        });

        //search trong listview
        searchView.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                String text = searchView.getText().toString().toLowerCase(Locale.getDefault());
                customAdapter.filter(text);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
                // TODO Auto-generated method stub
            }
        });

        btnmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(HomeActivity.this,PickNews.class);
                startActivity(intent);
            }
        });

        arrayListDocbao = new ArrayList<Docbao>();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Intent intent=getIntent();
                    String newsType = intent.getStringExtra("newsType");
                    switch (newsType) {
                        case "VNExpress":{
                            String lienket = intent.getStringExtra("linkDanhMucVnExpress");
                            new readRSSVnExpress().execute(lienket);
                        }break;
                        case "TuoiTre":{
                            String lienket = intent.getStringExtra("linkDanhMucTuoiTre");
                            new readRSSTuoiTre().execute(lienket);
                        }break;
                        case "VietNamNet":{
                            String lienket = intent.getStringExtra("linkDanhMucVietnamnet");
                            new readRSSVietnamnet().execute(lienket);
                        }break;
                    }

                }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(HomeActivity.this,Main2Activity.class);
                intent.putExtra("link",arrayListDocbao.get(position).link);
                int Count = 0;
                Cursor data = database.GetData("SELECT Title FROM TinTuc WHERE Title = \"" + arrayListDocbao.get(position).title + "\"");
                while (data.moveToNext()) {
                    String data_title = data.getString(0);
                    if (data_title != null) {
                        Count++;
                    }
                }
                if (Count == 0) {
                    database.QueryData("INSERT INTO TinTuc VALUES(null,\""
                            + arrayListDocbao.get(position).title + "\",'"
                            + arrayListDocbao.get(position).link + "','"
                            + arrayListDocbao.get(position).image + "','"
                            + arrayListDocbao.get(position).pubDate + "',\""
                            + arrayListDocbao.get(position).description + "\")");
                }
                startActivity(intent);
            }
        });
    }

    //Hàm để đọc RSS
    public class readRSSVnExpress extends AsyncTask<String,Integer,String>{//<đường link,quá trình thực hiện,kết quả trả về>

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
            Document document = parser.getDocument(s);
            NodeList nodeListItem = document.getElementsByTagName("item");
            NodeList nodeListDescription = document.getElementsByTagName("description");
            String hinhanh="";
            String title="";
            String link="";
            String date="";
            String description="";
            for(int i=0;i<nodeListItem.getLength();i++)
            {
                String cdata = nodeListDescription.item(i+1).getTextContent();//lấy giá trị trong thẻ CDATA

                String segments[] =cdata.split("</br>");

                Pattern p;
                //lấy link hình ảnh trong CDATA
                if(cdata.contains("data-original")){
                    p = Pattern.compile("<img[^>]+data-original\\s*=\\s*['\"]([^'\"]+)['\"][^>]*");
                }else{
                    p = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*");
                }

                //kiểm tra nếu hình ảnh trùng với group item thì nhóm lại
                Matcher matcher = p.matcher(cdata);
                if(matcher.find())
                {
                    hinhanh=matcher.group(1);
                }

                Element element = (Element) nodeListItem.item(i);

                title = parser.getValue(element,"title");//lấy nội dung trong thẻ title
                link = parser.getValue(element,"link");//lấy link trong thẻ link
                date = parser.getValue(element,"pubDate");//lấy nội dung trong thẻ pubDate*/

                description = segments[segments.length-1];//lấy phần tóm tắt ở trong CDATA bên trong thẻ Description

                arrayListDocbao.add(new Docbao(title.replace("\"", "'"),link,hinhanh,date,description.replace("\"", "'")));
            }
            customAdapter = new CustomAdapter(HomeActivity.this,android.R.layout.simple_list_item_1,arrayListDocbao);
            listView.setAdapter(customAdapter);
            super.onPostExecute(s);
        }
    }

    public class readRSSTuoiTre extends AsyncTask<String,Integer,String>{//<đường link,quá trình thực hiện,kết quả trả về>

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
            Document document = parser.getDocument(s);
            NodeList nodeListItem = document.getElementsByTagName("item");
            NodeList nodeListTitle = document.getElementsByTagName("title");
            NodeList nodeListLink = document.getElementsByTagName("link");
            NodeList nodeListpubDate = document.getElementsByTagName("pubDate");
            NodeList nodeListDescription = document.getElementsByTagName("description");
            String hinhanh="";
            String title="";
            String link="";
            String date="";
            String description="";
            for(int i=0;i<nodeListItem.getLength();i++)
            {
                String cdataTitle = nodeListTitle.item(i+1).getTextContent();
                String cdataLink = nodeListLink.item(i+1).getTextContent();
                String cdatapubDate = nodeListpubDate.item(i+1).getTextContent();
                String cdataDescription = nodeListDescription.item(i).getTextContent();//lấy giá trị trong thẻ CDATA

                String segments[] =cdataDescription.split("</a>");

                Pattern p;
                //lấy link hình ảnh trong CDATA
                if(cdataDescription.contains("data-original")){
                    p = Pattern.compile("<img[^>]+data-original\\s*=\\s*['\"]([^'\"]+)['\"][^>]*");
                }else{
                    p = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*");
                }

                //kiểm tra nếu hình ảnh trùng với group item thì nhóm lại
                Matcher matcher = p.matcher(cdataDescription);
                if(matcher.find())
                {
                    hinhanh=matcher.group(1);
                }

                Element element = (Element) nodeListItem.item(i);

                title = cdataTitle;
                link = cdataLink;
                date = cdatapubDate;

                description = segments[segments.length-1];//lấy phần tóm tắt ở trong CDATA bên trong thẻ Description

                arrayListDocbao.add(new Docbao(title.replace("\"", "'"),link,hinhanh,date,description.replace("\"", "'")));
            }
            customAdapter = new CustomAdapter(HomeActivity.this,android.R.layout.simple_list_item_1,arrayListDocbao);
            listView.setAdapter(customAdapter);
            super.onPostExecute(s);
        }
    }

    public class readRSSVietnamnet extends AsyncTask<String,Integer,String>{//<đường link,quá trình thực hiện,kết quả trả về>

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
            Document document = parser.getDocument(s);
            NodeList nodeListItem = document.getElementsByTagName("item");
            NodeList nodeListDescription = document.getElementsByTagName("description");
            String hinhanh="";
            String title="";
            String link="";
            String date="";
            String description="";
            for(int i=0;i<nodeListItem.getLength();i++)
            {
                String cdata = nodeListDescription.item(i+1).getTextContent();//lấy giá trị trong thẻ CDATA

                String segments[] = cdata.split("</p>");

                Pattern p;
                //lấy link hình ảnh trong CDATA
                if(cdata.contains("data-original")){
                    p = Pattern.compile("<img[^>]+data-original\\s*=\\s*['\"]([^'\"]+)['\"][^>]*");
                }else{
                    p = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*");
                }

                //kiểm tra nếu hình ảnh trùng với group item thì nhóm lại
                Matcher matcher = p.matcher(cdata);
                if(matcher.find())
                {
                    hinhanh=matcher.group(1);
                }

                Element element = (Element) nodeListItem.item(i);

                title = parser.getValue(element,"title");//lấy nội dung trong thẻ title
                link = parser.getValue(element,"link");//lấy link trong thẻ link
                date = parser.getValue(element,"pubDate");//lấy nội dung trong thẻ pubDate

                description = segments[0].replace("<p>", "");//lấy phần tóm tắt ở trong CDATA bên trong thẻ Description

                arrayListDocbao.add(new Docbao(title.replace("\"", "'"),link,hinhanh,date,description.replace("\"", "'")));
            }
            customAdapter = new CustomAdapter(HomeActivity.this,android.R.layout.simple_list_item_1,arrayListDocbao);
            listView.setAdapter(customAdapter);
            super.onPostExecute(s);
        }
    }

    //Hàm đọc nội dung từ URL
    public String docNoiDung_Tu_URL(String theUrl){
        StringBuilder content = new StringBuilder();
        try    {
            // create a url object
            URL url = new URL(theUrl);

            // create a urlconnection object
            URLConnection urlConnection = url.openConnection();

            // wrap the urlconnection in a bufferedreader
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String line;

            // read from the urlconnection via the bufferedreader
            while ((line = bufferedReader.readLine()) != null){
                content.append(line + "\n");
            }
            bufferedReader.close();
        }
        catch(Exception e)    {
            e.printStackTrace();
        }
        return content.toString();
    }
}
