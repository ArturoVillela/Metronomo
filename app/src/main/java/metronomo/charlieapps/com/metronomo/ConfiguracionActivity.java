package metronomo.charlieapps.com.metronomo;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class ConfiguracionActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //this.getActionBar().hide();
        super.onCreate(savedInstanceState);
        //getActionBar().hide();
        setContentView(R.layout.activity_configuracion);
        //will actually be... acerca de activity
    }

    //sobreescribimos el metodo onCreateOptionsMenu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
       // MenuInflater inflater = getMenuInflater();
        //inflater.inflate(R.menu.menu_main, menu);
        return true;
    }


}
