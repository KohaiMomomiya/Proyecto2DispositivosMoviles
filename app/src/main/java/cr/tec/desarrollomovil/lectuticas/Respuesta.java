package cr.tec.desarrollomovil.lectuticas;

/**
 * Created by Daniel on 12/06/2016.
 */
class Respuesta {

  private int idRespuesta;
  private String respuesta;
  private boolean esCorrecta;

  Respuesta(int idRespuesta, String respuesta, boolean esCorrecta) {
    this.idRespuesta = idRespuesta;
    this.respuesta = respuesta;
    this.esCorrecta = esCorrecta;
  }

  String getRespuesta() {
    return respuesta;
  }

  public void setRespuesta(String respuesta) {
    this.respuesta = respuesta;
  }

  public int getIdRespuesta() {
    return idRespuesta;
  }

  public void setIdRespuesta(int idRespuesta) {
    this.idRespuesta = idRespuesta;
  }

  boolean isEsCorrecta() {
    return esCorrecta;
  }

  public void setEsCorrecta(boolean esCorrecta) {
    this.esCorrecta = esCorrecta;
  }
}
