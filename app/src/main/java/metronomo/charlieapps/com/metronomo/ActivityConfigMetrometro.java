package metronomo.charlieapps.com.metronomo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.RadioGroup;

import metronomo.charlieapps.com.beans.ConfigMetronomo;

public class ActivityConfigMetrometro extends Activity {

    ImageButton[] imgBtnArray = new ImageButton[4];
    NumberPicker npMinutos;
    NumberPicker npSegundos;
    NumberPicker npVolumen;

    int iSegundos = 6;
    int iMinutos = 0;
    int iVolumen = 1;

    int iInstrumento = 0;//0guitarra,1 bateria, 2 piano, por default cheked la primera
    int iVelocidad = 1;//rapido,normal,lento//por default es normal
    int iNota=2;//0redonda, 1blanca, 2negra, 3corchea. por default checked la nota negra.

    Button btnContinuar;
    MediaPlayer mp;
    Context context;

    private float currentVolumen = 1f;
    private float volumenTemp = 0.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getActionBar().hide();
        setContentView(R.layout.activity_config_metrometro);
        context = this;
        btnContinuar = (Button) findViewById(R.id.btn_continuar);

        AudioManager audioManager =  (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        audioManager.setMode(AudioManager.MODE_IN_CALL);
        audioManager.setSpeakerphoneOn(true);

        getValuesOfNumberPickers();
        getTipoDeInstrumento();
        getTipoDeVelocidad();
    }

    @Override
    protected void onResume() {
        super.onResume();
        btnContinuar.setEnabled(true);
    }

    public void btnContinuarClicked(View view){
        Button b = (Button) findViewById(R.id.btn_continuar);
        btnContinuar.setEnabled(false);
        ConfigMetronomo bean = new ConfigMetronomo();
        bean.setInstrumento(this.iInstrumento);
        bean.setNota(this.iNota);
        bean.setSegundosDeDuracion((this.iSegundos) + (this.iMinutos * 60));
        bean.setVolumen(this.iVolumen);
        bean.setVelocidad(this.iVelocidad);

        Intent i = new Intent(ActivityConfigMetrometro.this,Metronomo.class);
        i.putExtra("objeto", bean);
        startActivity(i);
    }

    private void getTipoDeVelocidad(){
        RadioGroup rg = (RadioGroup) findViewById(R.id.rgVelocidad);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_vel_rapida:
                        iVelocidad = 0; msg("ivel 0");
                        break;
                    case R.id.rb_vel_normal:msg("ivel 1");
                        iVelocidad = 1;
                        break;
                    case R.id.rb_vel_lento: msg("ivel 2");
                        iVelocidad = 2;
                        break;
                }
            }
        });
    }

    private void getTipoDeInstrumento(){
        RadioGroup rg = (RadioGroup) findViewById(R.id.rgInstrumento);


        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_bajoguitarra:
                        iInstrumento = 0;
                        mp = MediaPlayer.create(context, R.raw.cuerda_electrica);
                        break;
                    case R.id.rb_bateria:
                        iInstrumento = 1;
                        mp = MediaPlayer.create(context, R.raw.bateria);
                        break;
                    case R.id.rb_piano:
                        iInstrumento = 2;
                        mp = MediaPlayer.create(context, R.raw.piano);
                        break;
                    case R.id.rb_beep1:
                        iInstrumento = 3;
                        mp = MediaPlayer.create(context, R.raw.beep1);
                        break;
                    case R.id.rb_beep2:
                        iInstrumento = 4;
                        mp = MediaPlayer.create(context, R.raw.beep2);
                        break;
                    case R.id.rb_beep3:
                        iInstrumento = 5;
                        mp = MediaPlayer.create(context, R.raw.beep3);
                        break;
                    case R.id.rb_tap:
                        iInstrumento = 6;
                        mp = MediaPlayer.create(context, R.raw.tap);
                        break;
                }
                mp.setVolume(currentVolumen, currentVolumen);
                mp.start();
            }
        });
    }

    private void getValuesOfNumberPickers(){
        npMinutos = (NumberPicker) findViewById(R.id.np_minutos);
        npMinutos.setMinValue(0);npMinutos.setMaxValue(5);
        npMinutos.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                iMinutos = newVal;
            }
        });
        npSegundos = (NumberPicker) findViewById(R.id.np_segundos);
        npSegundos.setMinValue(6);npSegundos.setMaxValue(59);
        npSegundos.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                iSegundos = newVal;
            }
        });
        npVolumen = (NumberPicker) findViewById(R.id.np_volumen);
        npVolumen.setMinValue(1);npVolumen.setMaxValue(10);
        npVolumen.setValue(10);
        npVolumen.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                iVolumen = newVal;
                volumenTemp = iVolumen;
                currentVolumen = volumenTemp/10f;

                //msg("ivolumen:"+iVolumen+",   currentVolumen:"+currentVolumen);
            }
        });
    }

    public void imgButtonClicked(View view){
//        for (int n=0;n<imgBtnArray.length;n++){
//            imgBtnArray[n].setAlpha(0.4f);
//        }
//
//        switch (view.getId()){
//            case R.id.ib_redonda: imgBtnArray[0].setAlpha(1.0f);iNota=0; break;
//            case R.id.ib_blanca: imgBtnArray[1].setAlpha(1.0f);iNota=1; break;
//            case R.id.ib_negra: imgBtnArray[2].setAlpha(1.0f);iNota=2; break;
//            case R.id.ib_corchea: imgBtnArray[3].setAlpha(1.0f);iNota=3; break;
//        }
    }

    private void msg(String cad){
        Log.d("charlie",cad);
    }

    //region Menu
    //sobreescribimos el metodo onCreateOptionsMenu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    //Ahora sobreescribimos el metodo onOptionsItemSelected
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                startActivity(new Intent(ActivityConfigMetrometro.this,ConfiguracionActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    //endregion
}
