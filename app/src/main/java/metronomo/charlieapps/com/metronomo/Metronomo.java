package metronomo.charlieapps.com.metronomo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import metronomo.charlieapps.com.beans.ConfigMetronomo;

public class Metronomo extends Activity {

    MediaPlayer mp;
    ConfigMetronomo bean;
    SeekBar seekBar;

    int cont =4;
    ImageView iv;

    private Timer myTimer;

    private boolean isCanceableByUser = false;

    private float volumen = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.getActionBar().hide();
        setContentView(R.layout.activity_metronomo);
        iv = (ImageView) findViewById(R.id.iv_fondo_contador);

        AudioManager audioManager =  (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        audioManager.setMode(AudioManager.MODE_IN_CALL);
        audioManager.setSpeakerphoneOn(true);


        Intent i = getIntent();
        bean = (ConfigMetronomo) i.getSerializableExtra("objeto");
        seekBar = (SeekBar) findViewById(R.id.sk_duracion);
        seekBar.setEnabled(false);
        comienzaAnimacionDelay();
    }

    private void comienzaAnimacionDelay(){
        new CountDownTimer(4000, 1000) {

            public void onTick(long millisUntilFinished) {
                cont--;
                ((TextView) findViewById(R.id.tv_contdelay)).setText(""+cont);
            }

            public void onFinish() {
                findViewById(R.id.ll_contenedor_texto).setVisibility(View.GONE);
                startPlaying();
            }
        }.start();
    }

    private void startPlaying(){
        ((TextView) findViewById(R.id.tv_contdelay)).setText("0");
        msg("instrumento:" + bean.getInstrumento() + ", nota:" + bean.getNota() + ", vel:" + bean.getVelocidad() + ", seg:" + bean.getSegundosDeDuracion());
        int velocidadBase = 0;
        int milisegundos = 0;
        switch (bean.getVelocidad()){
            case 0: milisegundos = 1000; break;
            case 1: milisegundos = 1500; break;
            case 2: milisegundos = 2000; break;
        }
        cont = 0;//nos indicara cuantas veces se repetira esta madre..
        final int cantDeRepeticiones = (bean.getSegundosDeDuracion()*1000)/milisegundos;
        msg("se repetira :" + cantDeRepeticiones + " veces");

        iv = (ImageView) findViewById(R.id.iv_fondo_contador);
        iv.setImageResource(R.drawable.fondo_contador_con_bocina);



        switch (bean.getInstrumento()){
            case 0: mp = MediaPlayer.create(this, R.raw.cuerda_electrica);msg("se eligio cuerda"); break;
            case 1: mp = MediaPlayer.create(this, R.raw.bateria); break;
            case 2: mp = MediaPlayer.create(this, R.raw.piano); break;
            case 3: mp = MediaPlayer.create(this, R.raw.beep1); break;
            case 4: mp = MediaPlayer.create(this, R.raw.beep2); break;
            case 5: mp = MediaPlayer.create(this, R.raw.beep3); break;
            case 6: mp = MediaPlayer.create(this, R.raw.tap);msg("tap elegido"); break;
        }
        // mp.start();
        msg("bean.getvolumen = "+bean.getVolumen());
        float vol = ((float) bean.getVolumen())/10.0f;
        mp.setVolume(vol,vol);
        msg("se eligio volumen de:"+vol);

        myTimer = new Timer();
        msg("cant de rep:"+cantDeRepeticiones);
        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (cont++>=cantDeRepeticiones || isCanceableByUser) {
                    myTimer.cancel();
                    isCanceableByUser = false;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            terminarPantalla();
                        }
                    });
                }else{
                    mp.start();
                    final int porcentajeTranscurrido = (cont*100)/cantDeRepeticiones;
                    msg("porcentaje: " + porcentajeTranscurrido);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            seekBar.setProgress(porcentajeTranscurrido);
                        }
                    });

                }
            }

        },0, milisegundos);

    }

    @Override
    protected void onStop() {
        super.onStop();
        isCanceableByUser = true;
    }

    public void btnCancelClicked(View view){
        isCanceableByUser=true;
        iv.setImageResource(R.drawable.fondo_contador);
        terminarPantalla();
    }

    private  void terminarPantalla(){
        this.finish();
    }

    private void msg(String cad){
        Log.d("charlie",cad);
    }

}
