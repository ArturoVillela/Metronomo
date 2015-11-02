package metronomo.charlieapps.com.metronomo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.webkit.WebView;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        loadWebPage();
        init_timer(6);
    }

    private void loadWebPage(){
        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.loadUrl("file:///android_asset/indexlogo.html");
    }

    //despues de xSegundos llamara al metodo removeLoadingScreen
    private void init_timer(int xSegundos){
        //iniciamos timer.. por que solo pondremos esta pantalla por unos segundos..
        ScheduledFuture scheduledFuture = scheduledExecutorService.schedule(new Callable() {
            @Override
            public Object call() throws Exception {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(MainActivity.this,ActivityConfigMetrometro.class));
                    }
                });
                return null;
            }
        },xSegundos,TimeUnit.SECONDS);
    }
    //remueve el webview del activity despues de xSegundos, en este caso 6.
    private void removeLoadingScreen(){

    }


    //region Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    //endregion

}
