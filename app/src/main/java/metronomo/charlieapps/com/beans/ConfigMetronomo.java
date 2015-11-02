package metronomo.charlieapps.com.beans;

import java.io.Serializable;

/**
 * Created by charlito on 10/28/2015.
 */
public class ConfigMetronomo implements Serializable {

    private int instrumento;
    private int nota;
    private int segundosDeDuracion;
    private int volumen;
    private int velocidad;

    public int getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }

    public int getVolumen() {
        return this.volumen;
    }

    public void setVolumen(int volumen) {
        this.volumen = volumen;
    }

    public int getInstrumento() {
        return instrumento;
    }

    public void setInstrumento(int instrumento) {
        this.instrumento = instrumento;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    public int getSegundosDeDuracion() {
        return segundosDeDuracion;
    }

    public void setSegundosDeDuracion(int segundosDeDuracion) {
        this.segundosDeDuracion = segundosDeDuracion;
    }
}
