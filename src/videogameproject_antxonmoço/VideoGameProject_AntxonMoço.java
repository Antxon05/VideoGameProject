/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package videogameproject_antxonmoço;

import java.awt.event.MouseEvent;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author antxon
 */
public class VideoGameProject_AntxonMoço extends Application {
    
    
    private ImageView imagenAlien = new ImageView();
    private RutaImagenes rutaImagenes = new RutaImagenes();
    private Stats stats = new Stats();
    private ImageView vida1 = new ImageView(rutaImagenes.getVidaLlena());
    private ImageView vida2 = new ImageView(rutaImagenes.getVidaLlena());
    private ImageView vida3 = new ImageView(rutaImagenes.getVidaLlena());
    private boolean activarnivel2;
    private boolean activarnivel3;
    
    
    @Override
    public void start(Stage primaryStage) {
        Group root = new Group(); 
        Scene scene = new Scene(root, 700, 600);
        
        //Configuración del fondo de pantalla, se va adaptando segun agrandemos el scene
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(-0.3);
        
        ImageView imageView = new ImageView(rutaImagenes.getBackground1());
        imageView.fitWidthProperty().bind(scene.widthProperty());
        imageView.fitHeightProperty().bind(scene.heightProperty());
        imageView.setEffect(colorAdjust);
        imageView.setPreserveRatio(false);
        
        //Configuración de la vida inicial, puntuación y niveles
        
        vida1.setFitWidth(50);
        vida1.setFitHeight(50);
        vida1.setTranslateX(10);
        vida1.setTranslateY(10);
        
        vida2.setFitWidth(50);
        vida2.setFitHeight(50);
        vida2.setTranslateX(60);
        vida2.setTranslateY(10);
        
        vida3.setFitWidth(50);
        vida3.setFitHeight(50);
        vida3.setTranslateX(110);
        vida3.setTranslateY(10);

        
        //Configuración del Score
        Label scoreLabel = new Label("SCORE: " + stats.getScore());
        Font customFont = Font.loadFont(("file:src\\Images\\customFont.ttf"),17);
        scoreLabel.setTranslateX(500);
        scoreLabel.setTranslateY(20);
        scoreLabel.setFont(customFont);
        scoreLabel.setStyle("-fx-text-fill: white;");
        
        //Connfiguración del Level
        Label nivelLabel = new Label("LEVEL: " + stats.getLevel());
        nivelLabel.setTranslateX(270);
        nivelLabel.setTranslateY(20);
        nivelLabel.setFont(customFont);
        nivelLabel.setStyle("-fx-text-fill: white;");
        
        //Configuracion de los aliens

        imagenAlien = new ImageView(rutaImagenes.getAlien());
        imagenAlien.setFitWidth(100);
        imagenAlien.setFitHeight(100);
        posicionAleatoriaAlien();
        
        
        //Animación del contador de 3 segundos
        
        javafx.animation.Timeline timeline = new javafx.animation.Timeline(
            new javafx.animation.KeyFrame(
                Duration.seconds(3), //El alien se muestra durante 3 segundos
                e -> {
                    // Si no se hace clic, se resta el puntuaje
                    stats.restarScore(30);
                    scoreLabel.setText("SCORE: " + stats.getScore());

                    // Verificar si el puntuaje es menor a 0
                    if (stats.getScore() < 0) {
                        stats.perderVida();
                        stats.resetScore();
                        scoreLabel.setText("SCORE: " + stats.getScore());
                        actualizarVidas(vida1, vida2, vida3);
                    }

                    // Generar una nueva posición del alien
                    posicionAleatoriaAlien();
                }
            )
        );
        timeline.setCycleCount(javafx.animation.Timeline.INDEFINITE);
        timeline.play();
        
        //Evento del click en caso de que le des al alien
        
        imagenAlien.setOnMouseClicked(event -> {
            stats.incrementarAciertos();
            stats.setScore(10);
            scoreLabel.setText("SCORE: " + stats.getScore());
            
            //Configuración del nivel 2
            if(stats.getScore() >= 50 && !activarnivel2){
                activarnivel2 = true;
                imageView.setImage(rutaImagenes.getBackground2());
                stats.incrementarLevel();
                nivelLabel.setText("LEVEL: " + stats.getLevel());
                
                configurarMovimientoNivel2();
            }
            
            posicionAleatoriaAlien();
            timeline.playFromStart(); // Reinicia el temporizador cuando le das click
            event.consume();
        });
        
        //Evento del click, en caso de que falles
        
        scene.setOnMouseClicked(event -> {
            stats.restarScore(30);
            scoreLabel.setText("SCORE: " + stats.getScore());
            
            if (stats.getScore() < 0) {
                stats.perderVida();
                stats.resetScore();
                scoreLabel.setText("SCORE: " + stats.getScore());
                actualizarVidas(vida1, vida2, vida3);
            }
            
            imagenAlien.setImage(rutaImagenes.getAlien());
        });
        
        
        root.getChildren().add(imageView);
        root.getChildren().add(imagenAlien);
        root.getChildren().add(scoreLabel);
        root.getChildren().add(vida1);
        root.getChildren().add(vida2);
        root.getChildren().add(vida3);
        root.getChildren().add(nivelLabel);
        
        primaryStage.setTitle("KILL THE ALIEN");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    
    //Metodo para generar el alien en una posicion aleatoria
    private void posicionAleatoriaAlien() {
        double maxX = 600 - imagenAlien.getFitWidth();
        double maxY = 500 - imagenAlien.getFitHeight();
        double x = Math.random() * maxX;
        double y = Math.random() * maxY;
        imagenAlien.setX(x);
        imagenAlien.setY(y);
    }
    
    
    private void actualizarVidas(ImageView vida1, ImageView vida2, ImageView vida3){
        int vidas = stats.getLifes();
        vida1.setVisible(vidas >= 1);
        vida2.setVisible(vidas >= 2);
        vida3.setVisible(vidas >= 3);
        
        if(vidas == 0){
            System.out.println("Game Over");
            System.exit(0);
        }
        
    }
    
    //Metodo para configurar el movimiento del nivel 2
    private void configurarMovimientoNivel2() {
        double maxX = 600 - imagenAlien.getFitWidth();
        double maxY = 500 - imagenAlien.getFitHeight();
        double x = Math.random() * maxX;
        double y = Math.random() * maxY;
        
        TranslateTransition movimientoAlien = new TranslateTransition(Duration.seconds(5), imagenAlien);
        movimientoAlien.setFromX(imagenAlien.getX());
        movimientoAlien.setFromY(imagenAlien.getY());
        movimientoAlien.setToX(x);
        movimientoAlien.setToY(y);
        movimientoAlien.setCycleCount(1);
        movimientoAlien.setOnFinished(e -> {
            //Cuando acaba el movimiento, cambia de posicion y pierdes puntos
            stats.restarScore(30); 
            if (stats.getScore() < 0) {
                stats.perderVida();
                stats.resetScore();
                actualizarVidas(vida1, vida2, vida3);
            }
            posicionAleatoriaAlien();
            configurarMovimientoNivel2();
        });
        movimientoAlien.play();
    }
    
}
