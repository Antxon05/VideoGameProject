/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author antxon
 */
public class Stats {
    
    private int score = 0;
    private int lifes = 3;
    private int contadorAciertos = 0;
    private int level = 1;

    public int getScore() {
        return score;
    }

    public void setScore() {
        this.score += 10;
    }
    
    public void restarScore(){
        this.score -= 30;
    }
    
    public void resetScore(){
        this.score = 0;
    }

    public int getLifes() {
        return lifes;
    }

    public void setLifes(int lifes) {
        this.lifes = lifes;
    }
    
    public void reiniciarVidas(){
        this.lifes = 0;
    }
    
    
    public void perderVida(){
        this.lifes--;
    }
    
    public void incrementarAciertos(){
        this.contadorAciertos++;
    }

    public int getContadorAciertos() {
        return contadorAciertos;
    }
    
    
    public void incrementarLevel(){
        this.level++;
    }
    
    
    public int getLevel() {
        return level;
    }
    
    
}
