package cr.tec.desarrollomovil.lectuticas;

import java.util.ArrayList;

/**
 * Created by Daniel on 12/06/2016.
 */
public class Pregunta {

    private int idPregunta;
    private String pregunta;
    private ArrayList<Respuesta> respuestas = new ArrayList<Respuesta>();

    public Pregunta(int idPregunta,String pregunta){
        this.idPregunta = idPregunta;
        this.pregunta = pregunta;
    }

    public int getIdPregunta() { return idPregunta;   }

    public void setIdPregunta(int idPregunta) {
        this.idPregunta = idPregunta;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public void addRespuesta(Respuesta respuesta){  respuestas.add(respuesta);  }

    public Respuesta getRespuesta(int index){ return respuestas.get(index); }

}
