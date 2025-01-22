/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package videogameproject_antxonmoço;

import javafx.scene.image.Image;

/**
 *
 * @author antxon
 */
public class RutaImagenes {
    
    private String[] background = {"file:src\\Images\\background1.png", "file:src\\Images\\background2.png", "file:src\\Images\\background3.png"};
    private String[] aliens = {"file:src\\Images\\alien1.png", "file:src\\Images\\alien2.png", "file:src\\Images\\alien3.png", "file:src\\Images\\alien4.png"};
    private String[] vidas = {"file:src\\Images\\corazonvida.png", "file:src\\Images\\corazonvidavacio.png"};
    private Image alien;
    private Image vida;
    
    public RutaImagenes(){
        int random = (int) (Math.random()*3+1);
        this.alien = new Image(aliens[random]);
    }

    public Image getBackground1() {
        return new Image(background[0]);
    }
    
    public Image getBackground2() {
        return new Image(background[1]);
    }
        
    public Image getBackground3() {
        return new Image(background[2]);
    }

    public Image getAlien() {
        int random = (int)(Math.random()*aliens.length);
        alien = new Image(aliens[random]);
        return alien;
    }
    
    public Image getVidaLlena(){
        return vida = new Image(vidas[0]);
    }
    
    public Image getVidaVacia(){
        return vida = new Image(vidas[2]);
    }

    
    
    
    
    
}
