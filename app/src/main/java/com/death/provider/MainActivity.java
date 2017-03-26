package com.death.provider;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.death.provider.utils.SelectorAttributes;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    static int count = 0;
    RecyclerView recyclerView;
    Button search;
    EditText editText;
    Document doc = null;
    AdapterProducts mAdapter;
    ArrayList<PaytmModel> paytmModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.query);
        search = (Button) findViewById(R.id.getResults);
        recyclerView = (RecyclerView) findViewById(R.id.container);
        paytmModels = new ArrayList<>();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        mAdapter = new AdapterProducts(getApplicationContext(), paytmModels);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paytmModels.clear();
                String urlForStore = SelectorAttributes.BASE_FLIPKART + editText.getText().toString();
                Log.e("URL", urlForStore);
                viewScrapper(urlForStore);
//                Toast.makeText(MainActivity.this, "INTO ELSE", Toast.LENGTH_LONG).show();
//                WebView webView = (WebView) findViewById(R.id.webview);
//                webView.loadUrl(urlForStore);
//                webView.getSettings().setJavaScriptEnabled(true);
//                webView.setWebViewClient(new WebViewClient() {
//                    @Override
//                    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//                        return super.shouldOverrideUrlLoading(view, request);
//                    }
//
//                    @Override
//                    public void onPageCommitVisible(WebView view, String url) {
//                        super.onPageCommitVisible(view, url);
//                        count++;
//                        if (count % 2 == 0) {
//                            Log.e("URL", url);
//                            Log.e("CURL", view.getUrl());
//                            viewScrapper(view.getUrl());
//                        }
//                    }
//                });

                mAdapter.notifyDataSetChanged();
            }
        });
    }

    public void viewScrapper(String url) {
        paytmModels.clear();
//
//        Log.e("URL", redirectedUrl);
//        Document doc = null;
//        try {
//            doc = Jsoup.connect(redirectedUrl).followRedirects(true).get();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        assert doc != null;
//        for (Element element : doc.select("._2i1r")) {
//            PaytmModel model = new PaytmModel();
//            model.setpName(element.select("._2apC").text());
//            model.setpPrice(element.select("._1kMS").text());
//            model.setpLink(element.select("a._8vVO").attr("abs:href"));
//            model.setpImageLink(element.select("img").attr("abs:src"));
//            paytmModels.add(model);
//        }

        Log.e("URL", url);
        Document doc = null;
        try {
            doc = Jsoup.connect(url).followRedirects(true).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert doc != null;
        for (Element element : doc.select("._2SxMvQ")) {
//            PaytmModel model = new PaytmModel();
//            model.setpName(element.select("._2apC").text());
//            model.setpPrice(element.select("._1kMS").text());
//            model.setpLink(element.select("a._8vVO").attr("abs:href"));
//            model.setpImageLink(element.select("img").attr("abs:src"));
//            paytmModels.add(model);
//            Log.e("ELEMENTS",element.select("h2.a-size-base.a-color-null.s-inline.scx-truncate.s-access-title.a-text-normal").text());
//            Log.e("IMAGE LINKS",element.select("img.s-access-image.cfMarker").attr("abs:src"));
//            Log.e("PRICE",element.select("span.a-size-base.a-color-price.a-text-bold").text());
//            Log.e("ITEM LINK",element.select("a.a-link-normal").attr("abs:href"));

            Log.e("ELEMENT", element.toString());
        }
    }
}
