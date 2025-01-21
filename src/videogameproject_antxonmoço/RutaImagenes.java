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
    
    private Image background;
    private String[] aliens = {"file:src\\Images\\alien1.png", "file:src\\Images\\alien2.png", "file:src\\Images\\alien3.png", "file:src\\Images\\alien4.png"};
    private Image alien;
    
    public RutaImagenes(){
        int random = (int) (Math.random()*3+1);
        this.alien = new Image(aliens[random]);
        this.background = new Image("file:src\\Images\\background1.png");
    }

    public Image getBackground() {
        return background;
    }

    public Image getAlien() {
        int random = (int)(Math.random()*aliens.length);
        alien = new Image(aliens[random]);
        return alien;
    }

    
    
    
    
    
}
