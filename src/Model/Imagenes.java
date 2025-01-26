/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import javafx.scene.image.Image;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 *
 * @author antxon
 */
public class Imagenes {
    
    private Image backgroundPrincipal = new Image("file:src\\Images\\backgroundPantallaPrincipal.jpeg");
    private String[] background = {"file:src\\Images\\background1.png", "file:src\\Images\\background2.png", "file:src\\Images\\background3.png"};
    private String[] aliens = {"file:src\\Images\\alien1.png", "file:src\\Images\\alien2.png", "file:src\\Images\\alien3.png", "file:src\\Images\\alien4.png"};
    private String[] vidas = {"file:src\\Images\\corazonvida.png", "file:src\\Images\\corazonvidavacio.png"};
    private Image alien;
    private Image vida;
    private Image trofeo = new Image("file:src\\Images\\trofeo.gif");
    private Image confetti = new Image("file:src\\Images\\confetti.gif");
    private Image explosion = new Image("file:src\\Images\\explosion.gif");
    private Image lluvia = new Image("file:src\\Images\\lluvia.gif");
    private Image boton = new Image("file:src\\Images\\fondoBoton.png");
    private Image fondoInfo = new Image("file:src\\Images\\howtoplay.png");
    
    public Imagenes(){
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

    public Image getTrofeo() {
        return trofeo;
    }

    public Image getConfetti() {
        return confetti;
    }

    public Image getExplosion() {
        return explosion;
    }

    public Image getLluvia() {
        return lluvia;
    }

    public Image getBackgroundPrincipal() {
        return backgroundPrincipal;
    }

    public Image getBoton() {
        return boton;
    }

    public Image getFondoInfo() {
        return fondoInfo;
    }
    
    
    
}
