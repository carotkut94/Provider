package com.death.provider;

import android.content.res.Resources;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

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
    Document document = null;
    AdapterProducts mAdapter;
    ArrayList<ItemModel> itemModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.query);
        search = (Button) findViewById(R.id.getResults);
        recyclerView = (RecyclerView) findViewById(R.id.container);
        itemModels = new ArrayList<>();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        mAdapter = new AdapterProducts(getApplicationContext(), itemModels);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] stores = SelectorAttributes.getArrayStoresWithQuery(editText.getText().toString());
                viewScrapper(stores);
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    public void viewScrapper(String[] urls) {
        itemModels.clear();
        for (String url : urls) {
            try {
                document = Jsoup.connect(url).followRedirects(true).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (document.select(SelectorAttributes.PAYTM_ROOT).size()==0 && SelectorAttributes.getStoreName(url).equals("PAYTM")) {
                Log.e("ERROR","IN IF");
                WebView webView = (WebView) findViewById(R.id.webview);
                webView.loadUrl(url);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                        return super.shouldOverrideUrlLoading(view, request);
                    }

                    @Override
                    public void onPageCommitVisible(WebView view, String url) {
                        super.onPageCommitVisible(view, url);
                        count++;
                        if (count % 2 == 0) {
                            try {
                                document = Jsoup.connect(view.getUrl()).followRedirects(true).get();
                                fillDataInContainer("PAYTM", document, SelectorAttributes.selectorArray("PAYTM"));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            } else if (SelectorAttributes.getStoreName(url).equals("PAYTM") && document.select(SelectorAttributes.PAYTM_ROOT).size()>0) {
                Log.e("ERROR","IN ELSE IF");
                fillDataInContainer("PAYTM", document, SelectorAttributes.selectorArray("PAYTM"));
            } else {
                Log.e("ERROR","IN ELSE");
                fillDataInContainer("AMAZON", document, SelectorAttributes.selectorArray("AMAZON"));
            }
        }
    }

//    public static int getVerticalCardViewOffset(Context context) {
//        Resources res = context.getResources();
//        int elevation = res.getDimensionPixelSize(R.dimen.cardview_default_elevation);
//        int radius = res.getDimensionPixelSize(R.dimen.cardview_default_radius);
//        return (int) (elevation * 1.5 + (1 - Math.cos(45)) * radius);
//    }
//
//    public static int getHorizontalCardViewOffset(Context context) {
//        Resources res = context.getResources();
//        int elevation = res.getDimensionPixelSize(R.dimen.cardview_default_elevation);
//        int radius = res.getDimensionPixelSize(R.dimen.cardview_default_radius);
//        return (int) (elevation + (1 - Math.cos(45)) * radius);
//    }

    public void fillDataInContainer(String storeName, Document doc, String[] css) {
        if (storeName.equals("PAYTM")) {
            for (Element element : doc.select(SelectorAttributes.PAYTM_ROOT)) {
                Log.e("ELEMENT PAYTM", element.toString());
                ItemModel model = new ItemModel();
                model.setpName(element.select(css[0]).text());
                model.setpPrice(element.select(css[2]).text().trim());
                model.setpLink(element.select(css[3]).attr("abs:href"));
                model.setpImageLink(element.select(css[1]).attr("abs:src"));
                model.setpStoreName(storeName);
                itemModels.add(model);
            }
            mAdapter.notifyDataSetChanged();
        } else {
            for (Element element : doc.select(SelectorAttributes.AMAZON_ROOT)) {
                Log.e("ELEMENT AMAZON", element.toString());
                ItemModel model = new ItemModel();
                model.setpName(element.select(css[0]).text());
                model.setpPrice(element.select(css[2]).text());
                model.setpLink(element.select(css[3]).attr("abs:href"));
                model.setpImageLink(element.select(css[1]).attr("abs:src"));
                model.setpStoreName(storeName);
                itemModels.add(model);
            }
            mAdapter.notifyDataSetChanged();
        }
    }


    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
