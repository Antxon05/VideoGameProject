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
        
        ImageView vida1 = new ImageView(rutaImagenes.getVidaLlena());
        ImageView vida2 = new ImageView(rutaImagenes.getVidaLlena());
        ImageView vida3 = new ImageView(rutaImagenes.getVidaLlena());
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

        
        //Score
        Label scoreLabel = new Label("SCORE: " + stats.getScore());
        Font customFont = Font.loadFont(("file:src\\Images\\customFont.ttf"),17);
        scoreLabel.setTranslateX(500);
        scoreLabel.setTranslateY(20);
        scoreLabel.setFont(customFont);
        scoreLabel.setStyle("-fx-text-fill: white;");
        
        //Configuracion de los aliens

        imagenAlien = new ImageView(rutaImagenes.getAlien());
        imagenAlien.setFitWidth(100);
        imagenAlien.setFitHeight(100);
        posicionAleatoriaAlien();
        
        
        //Configuración de las animaciones para los aliens
        
        TranslateTransition translate = new TranslateTransition();
        translate.setDuration(Duration.millis(1000));
        translate.setAutoReverse(true);
        translate.setNode(imagenAlien);
        //translate.play();
        
        
        javafx.animation.Timeline timeline = new javafx.animation.Timeline(
            new javafx.animation.KeyFrame(
                Duration.seconds(3), //El alien estara visible por 3 segundos
                e -> {
                    // Si no se hace clic, se resta puntaje
                    stats.restarScore(10);
                    scoreLabel.setText("SCORE: " + stats.getScore());

                    // Verificar si el puntaje es menor a 0
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
    
}
