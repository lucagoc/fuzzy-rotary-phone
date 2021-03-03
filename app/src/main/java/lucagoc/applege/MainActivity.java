package lucagoc.applege;


import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.app.NotificationManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity {
    private WebView myWebView;
    private String pageUrl;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    pageUrl = (String) getText(R.string.pref_default_agenda);
                    myWebView.loadUrl(pageUrl);
                    return true;
                case R.id.navigation_dashboard:
                    pageUrl = "http://0070025p.esidoc.fr/mobile/";
                    myWebView.loadUrl(pageUrl);
                    return true;
            }
            return false;
        }
    };

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        myWebView = (WebView) findViewById(R.id.webview);
        pageUrl = "http://chocolat.inforoutes.fr/chocolat_st_sauveur/mobile/index.php";  // default page
        WebView view = (WebView) this.findViewById(R.id.webview);
        view.setWebViewClient(new WebViewClient() {
                                  public boolean shouldOverrideUrlLoading(WebView view, String url) {
                                      if (Uri.parse(url).getHost().equals("play.google.com")) {
                                          // if the host is play.google.com, do not load the url to webView. Let it open with its app
                                          Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                          view.getContext().startActivity(intent);

                                          return true;
                                      }


                                      if (Uri.parse(url).getHost().equals("www.youtube.com")) {
                                          // YouTube auto open
                                          Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                          view.getContext().startActivity(intent);

                                          return true;
                                      }


                                      if (Uri.parse(url).getHost().equals("wordpress.com")) {
                                          // Appllege update
                                          Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                          view.getContext().startActivity(intent);

                                          return true;
                                      }
                                      if (Uri.parse(url).getHost().equals("appllege.wordpress.com")) {
                                          // Appllege update
                                          Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                          view.getContext().startActivity(intent);

                                          return true;
                                      }
                                      if (Uri.parse(url).getHost().equals("www.facebook.com")) {
                                          // facebook
                                          Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                          view.getContext().startActivity(intent);

                                          return true;
                                      }
                                      if (Uri.parse(url).getHost().equals("www.twitter.com")) {
                                          // twitter
                                          Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                          view.getContext().startActivity(intent);

                                          return true;
                                      }
                                      if (Uri.parse(url).getHost().equals("discordapp.com")) {
                                          // discord
                                          Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                          view.getContext().startActivity(intent);

                                          return true;
                                      }
                                      if (Uri.parse(url).getHost().equals("discord.gg")) {
                                          // discord
                                          Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                          view.getContext().startActivity(intent);

                                          return true;
                                      }
                                      return false;

                                  }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                                      myWebView.loadUrl("file:///android_asset/noConnection.html");// custom error message
                                  }
                              }
        ); // Navigateur Web + anti-ouverture de google play + youtube
        view.getSettings().setJavaScriptEnabled(true);
        view.getSettings().setSupportZoom(true);
        view.loadUrl(pageUrl);


        myWebView.setOnKeyListener(new View.OnKeyListener() { // retour en arrière
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((keyCode == KeyEvent.KEYCODE_BACK) && myWebView.canGoBack()) {
                    myWebView.goBack();
                    return true;
                }
                return false;
            }
        });


        myWebView.setDownloadListener(new DownloadListener() // pdf downloader
        {

            @Override


            public void onDownloadStart(String url, String userAgent,
                                        String contentDisposition, String mimeType,
                                        long contentLength) {
                DownloadManager.Request request = new DownloadManager.Request(
                        Uri.parse(url));

                request.setMimeType(mimeType);

                String cookies = CookieManager.getInstance().getCookie(url);

                request.addRequestHeader("cookie", cookies);

                request.addRequestHeader("User-Agent", userAgent);

                request.setDescription("Téléchargement...");

                request.setTitle(URLUtil.guessFileName(url, contentDisposition,
                        mimeType));

                request.allowScanningByMediaScanner();

                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationInExternalFilesDir(MainActivity.this,
                        Environment.DIRECTORY_DOWNLOADS, ".pdf");
                DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                dm.enqueue(request);
                Toast.makeText(getApplicationContext(), "Téléchargement en cours...",
                        Toast.LENGTH_LONG).show();
            }
        });



    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}

