/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package videogameproject_antxonmoço;

/**
 *
 * @author antxon
 */
public class Stats {
    
    private int score = 0;
    private int lifes = 3;
    private int contadorAciertos = 0;
    private int level = 0;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score += score;
    }
    
    public void restarScore(int score){
        this.score -= score;
    }
    
    public void resetScore(){
        this.score = 0;
    }

    public int getLifes() {
        return lifes;
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
    
    
}
