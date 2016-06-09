package cr.tec.desarrollomovil.lectuticas;

import java.util.ArrayList;

/**
 * Created by Daniel on 09/06/2016.
 */
public class Cuento {

    private int idCuento;
    private String nombre;
    private ArrayList<Parrafo> parrafos = null;

    public Cuento(String nombre, int idCuento) {
        this.nombre = nombre;
        this.idCuento = idCuento;
    }

    public void addParrafo(Parrafo parrafo){
        parrafos.add(parrafo);
    }

    public Parrafo getParrafo(int index){
        return parrafos.get(index);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIdCuento() {
        return idCuento;
    }

    public void setIdCuento(int idCuento) {
        this.idCuento = idCuento;
    }
}
