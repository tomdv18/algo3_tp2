package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.modelo.excepciones.PaisSinEjercitosSuficientesException;
import edu.fiuba.algo3.modelo.excepciones.PaisesConMismoDuenoException;
import edu.fiuba.algo3.modelo.excepciones.PaisesNoContinuosException;

import java.util.ArrayList;

public class Teg {
    private int cantidadJugadores;
    private Mapa mapa;
    private ArrayList<Jugador> jugadores;
    private Turno turnoActual;
    private int numeroJugadorActual;

    public Teg(ArrayList<String> nombresJugadores) {
        this.cantidadJugadores = nombresJugadores.size();
        this.mapa = new Mapa();
        this.jugadores = new ArrayList<Jugador>();
        this.numeroJugadorActual = 0;

        String[] colores = {"rojo", "azul", "verde", "amarillo", "rosa", "negro"};

        for (int i = 0; i < this.cantidadJugadores; i++) {
            this.jugadores.add(new Jugador(nombresJugadores.get(i), new Ejercito(colores[i])));
        }

        mapa.repartirPaises(jugadores);

        turnoActual = new TurnoAtaque(jugadores.get(numeroJugadorActual),mapa);
    }

    public boolean todosLosPaisesOcupados(){
        return this.mapa.todosLosPaisesOcupados();
    }

    public void atacar(String paisAtaque, String paisDefensa, int cantEjercitos){
        try {
            mapa.atacar(paisAtaque, paisDefensa, cantEjercitos);
        } catch (PaisesNoContinuosException | PaisesConMismoDuenoException | PaisSinEjercitosSuficientesException e) {
            e.printStackTrace();
        }
    }

    public void avanzarEtapa(){
        turnoActual = turnoActual.avanzarEtapa();

        if(turnoActual == null){
            numeroJugadorActual++;
            numeroJugadorActual %= jugadores.size();
            turnoActual = new TurnoAtaque(jugadores.get(numeroJugadorActual),mapa);
        }
    }



}