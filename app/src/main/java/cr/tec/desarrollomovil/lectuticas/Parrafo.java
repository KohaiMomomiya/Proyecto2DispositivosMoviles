package cr.tec.desarrollomovil.lectuticas;

/**
 * Created by Daniel on 09/06/2016.
 */
class Parrafo {

  private int idParafo;
  private String texto;
  private int numero;

  Parrafo(int idParafo, String texto, int numero) {
    this.idParafo = idParafo;
    this.texto = texto;
    this.numero = numero;
  }

  public int getIdParafo() {
    return idParafo;
  }

  public void setIdParafo(int idParafo) {
    this.idParafo = idParafo;
  }

  String getTexto() {
    return texto;
  }

  public void setTexto(String texto) {
    this.texto = texto;
  }

  public int getNumero() {
    return numero;
  }

  public void setNumero(int numero) {
    this.numero = numero;
  }
}
