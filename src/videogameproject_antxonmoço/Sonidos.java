/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package videogameproject_antxonmoço;

import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 *
 * @author Antxon
 */
public class Sonidos {
    
    
    private Media derrota = new Media(new File("src/Sounds/derrota.mp3").toURI().toString());
    private Media disparo = new Media(new File("src/Sounds/disparo.mp3").toURI().toString());
    private Media sonidoFondo = new Media(new File("src/Sounds/sonidofondo.mp3").toURI().toString());
    private Media sonidoVictoria = new Media(new File("src/Sounds/sonidovictoria.mp3").toURI().toString());

    
    public MediaPlayer getDerrota() {
        return new MediaPlayer(derrota);
    }

    public MediaPlayer getDisparo() {
        return new MediaPlayer(disparo);
    }

    public MediaPlayer getSonidoFondo() {
        return new MediaPlayer(sonidoFondo);
    }

    public MediaPlayer getSonidoVictoria() {
        return new MediaPlayer(sonidoVictoria);
    }
    
    
    
}
