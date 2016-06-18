package cr.tec.desarrollomovil.lectuticas;

import java.util.ArrayList;

/**
 * Created by Daniel on 12/06/2016.
 */
class Pregunta {

  private int idPregunta;
  private String pregunta;
  private ArrayList<Respuesta> respuestas = new ArrayList<Respuesta>();

  Pregunta(int idPregunta, String pregunta) {
    this.idPregunta = idPregunta;
    this.pregunta = pregunta;
  }

  public int getIdPregunta() {
    return idPregunta;
  }

  public void setIdPregunta(int idPregunta) {
    this.idPregunta = idPregunta;
  }

  String getPregunta() {
    return pregunta;
  }

  public void setPregunta(String pregunta) {
    this.pregunta = pregunta;
  }

  void addRespuesta(Respuesta respuesta) {
    respuestas.add(respuesta);
  }

  Respuesta getRespuesta(int index) {
    return respuestas.get(index);
  }

}
