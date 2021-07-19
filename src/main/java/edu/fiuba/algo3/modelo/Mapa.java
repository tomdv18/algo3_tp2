package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.modelo.excepciones.PaisSinEjercitosSuficientesException;
import edu.fiuba.algo3.modelo.excepciones.PaisesConMismoDuenoException;
import edu.fiuba.algo3.modelo.excepciones.PaisesNoContinuosException;

import java.util.*;

public class Mapa {
    private Hashtable<String, Pais> paises;
    private Hashtable<String, Continente> continentes;

    public Mapa(){
        this.paises = new Hashtable<String, Pais>();
        this.continentes = new Hashtable<String, Continente>();
        this.crearContinentes();
        this.crearPaises();
    }

    //Transformar este metodo a una lectura de archivos (un archivo de paises y otro de fronteras)
    public void crearPaises() {

        String[] nombresPaises = {"Madagascar", "Etiopia", "Egipto", "Sahara", "Sudafrica", "Zaire", "Arabia", "Turquia"};
        Pais paisAux;

        for(int i = 0; i < (nombresPaises.length); i++){
            paises.put(nombresPaises[i], new Pais(nombresPaises[i]));

        }

        ArrayList<Pais> fronteraAux = new ArrayList<>();
        paisAux = paises.get("Egipto");

        continentes.get("África").agregarPais(paisAux);

        fronteraAux.add( paises.get("Etiopia"));
        fronteraAux.add( paises.get("Madagascar"));
        fronteraAux.add( paises.get("Sahara"));
        paisAux.agregarFrontera(fronteraAux);

        paisAux = paises.get("Etiopia");

        continentes.get("África").agregarPais(paisAux);

        fronteraAux = new ArrayList<>();

        fronteraAux.add( paises.get("Egipto"));
        fronteraAux.add( paises.get("Zaire"));
        fronteraAux.add( paises.get("Sahara"));
        fronteraAux.add( paises.get("Sudafrica"));

        paisAux.agregarFrontera(fronteraAux);

        paisAux = paises.get("Madagascar");

        continentes.get("África").agregarPais(paisAux);

        fronteraAux = new ArrayList<>();
        fronteraAux.add( paises.get("Egipto"));
        fronteraAux.add( paises.get("Zaire"));

        paisAux.agregarFrontera(fronteraAux);

        paisAux = paises.get("Sahara");

        continentes.get("África").agregarPais(paisAux);

        fronteraAux = new ArrayList<>();
        fronteraAux.add( paises.get("Egipto"));
        fronteraAux.add( paises.get("Zaire"));
        fronteraAux.add( paises.get("Etiopia"));

        paisAux.agregarFrontera(fronteraAux);

        paisAux = (paises.get("Sudafrica"));

        continentes.get("África").agregarPais(paisAux);

        fronteraAux = new ArrayList<>();
        fronteraAux.add( paises.get("Zaire"));
        fronteraAux.add( paises.get("Etiopia"));

        paisAux.agregarFrontera(fronteraAux);

        paisAux = paises.get("Zaire");

        continentes.get("África").agregarPais(paisAux);

        fronteraAux = new ArrayList<>();

        fronteraAux.add( paises.get("Madagascar"));
        fronteraAux.add( paises.get("Etiopia"));
        fronteraAux.add( paises.get("Sahara"));
        fronteraAux.add(paises.get("Sudafrica"));

        paisAux.agregarFrontera(fronteraAux);

        paisAux = paises.get("Turquia");

        continentes.get("Asia").agregarPais(paisAux);

        fronteraAux = new ArrayList<>();

        fronteraAux.add( paises.get("Arabia"));

        paisAux.agregarFrontera(fronteraAux);

        paisAux = paises.get("Arabia");

        continentes.get("Asia").agregarPais(paisAux);

        fronteraAux = new ArrayList<>();

        fronteraAux.add( paises.get("Turquia"));
        paisAux.agregarFrontera(fronteraAux);


    }


    public void crearContinentes(){
        ArrayList<Integer> bonusFichas = new ArrayList<>();
        bonusFichas.add(5);
        bonusFichas.add(3);
        bonusFichas.add(7);
        bonusFichas.add(3);
        bonusFichas.add(5);
        bonusFichas.add(2);
        String[] nombresContinentes = {"América del Norte", "América del Sur", "Asia", "África", "Europa", "Oceanía"};
        for(int i = 0; i < (nombresContinentes.length); i++){
            continentes.put(nombresContinentes[i], new Continente(nombresContinentes[i],bonusFichas.get(i)));

        }

    }



    //Esta funcion no aplica aleatoriedad.
    public void repartirPaises(ArrayList<Jugador> jugadores){
        int cantidadPaises = paises.size();
        int cantidadJugadores = jugadores.size();
        Pais paisAux;
        String[] keys = paises.keySet().toArray(new String[0]);
        int i = 0;
        int j = 0;
        int jugActual;
        while(i < cantidadPaises ){
            jugActual = j % cantidadJugadores;
            paisAux = paises.get(keys[i]);
            paisAux.asignarEjercito(jugadores.get(jugActual).getEjercito());
            paisAux.agregarEjercito(1);
            i++;
            j++;
        }
    }


    public boolean todosLosPaisesOcupados(){
        boolean estanOcupados = true;
        int i = 0;
        String[] keys = paises.keySet().toArray(new String[0]);
        while(estanOcupados && i < paises.size()){
            estanOcupados = estanOcupados && (paises.get(keys[i]).estaOcupado());
            i++;
        }
        return estanOcupados;
    }

    //Tal vez esta funcion deberia tener una excepcion por si no existe ese país
    public Pais obtenerPais(String paisABuscar) {
        Pais paisEncontrado = paises.get(paisABuscar);
        return paisEncontrado;
    }

    public void atacar(String paisAtaque, String paisDefensa, int cantEjercitos) throws PaisesNoContinuosException, PaisesConMismoDuenoException, PaisSinEjercitosSuficientesException {

        Pais paisAtacante = obtenerPais(paisAtaque);
        Pais paisDefensor = obtenerPais(paisDefensa);

        if(!sonContiguos(paisAtacante, paisDefensor))
            throw new PaisesNoContinuosException();
        if(!paisAtacante.tienenEjercitosDiferentes(paisDefensor))
            throw new PaisesConMismoDuenoException();
        if(!paisAtacante.esAptoParaAtacar())
            throw new PaisSinEjercitosSuficientesException();

        //En caso de ningun error, se realiza el ataque
        Combate combate = new Combate(paisAtacante, paisDefensor, cantEjercitos);
        combate.generarCombate();
    }

    public boolean sonContiguos(Pais paisUno, Pais paisDos) {
        return paisUno.estaEnFrontera(paisDos);
    }

    public Ejercito ejercitoEnPais(String nombrePais){
        Pais paisBuscado = paises.get(nombrePais);

        return paisBuscado.getEjercito();
    }

    public int numeroEjercitosEn(String nombrePais){
        Pais paisBuscado = paises.get(nombrePais);

        return paisBuscado.obtenerCantidadEjercitos();
    }

    public Integer paisesConEjercito(Ejercito ejercito){
        int contadorEjercito = 0;
        Ejercito ejercitoAux;
        int i = 0;
        String[] keys = paises.keySet().toArray(new String[0]);

        while (i < this.paises.size()){
            ejercitoAux = ejercitoEnPais(keys[i]);
            if(ejercitoAux == ejercito){
                contadorEjercito++;
            }
            i++;
        }

        return contadorEjercito;
    }
    public void moverEjercitos(String paisOrigen, String paisDestino, Integer cantidadAMover){
        Pais origen = obtenerPais(paisOrigen);
        Pais destino = obtenerPais(paisDestino);

        if (!sonContiguos(origen,destino)) return; //LANZAR ERROR PAISES NO SON ADYACENTES
        if (origen.tienenEjercitosDiferentes(destino)) return; //LANZAR ERROR PAISES NO SON DEL MISMO DUEÑO
        if (!origen.sePuedeMoverEstaCantidadDeEjercitos(cantidadAMover)) return; // LANZAR ERROR DE QUE NO ALCANZAN LAS TROPAS

        origen.eliminarEjercitos(cantidadAMover);
        destino.agregarEjercito(cantidadAMover);

    }
    public Object[] todosLosPaises(){
        return paises.values().toArray();
    }


    public boolean paisLePertenece(String pais, Jugador jugador) {
        Pais paisAux = paises.get(pais);
        return jugador.getEjercito() == paisAux.getEjercito();

    }


	public void agregarEjercitos(String paisIngresado, int cantidad) {
		Pais pais = paises.get(paisIngresado);
		pais.agregarEjercito(cantidad);
	}


    public boolean jugadorControlaContinente(String  continente, Jugador jugador) {
        return continentes.get(continente).jugadorControlaContinente(jugador.getEjercito());
    }


    public int fichasPorContinentesControlados(Ejercito ejercito) {
        int fichas = 0;

        Set<String> keys = continentes.keySet();

        Iterator<String> itr = keys.iterator();

        while(itr.hasNext()){
            String contienteNombre = itr.next();
            Continente continente = continentes.get(contienteNombre);

            if (continente.jugadorControlaContinente(ejercito)){
                fichas += continente.getBonusConquista();
            }

        }

        return fichas;
    }
}
