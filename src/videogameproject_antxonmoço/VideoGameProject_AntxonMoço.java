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
    
    private int Score = 50;
    private int contadorAciertos = 0;
    private ImageView imagenAlien = new ImageView();
    private RutaImagenes rutaImagenes = new RutaImagenes();
    
    @Override
    public void start(Stage primaryStage) {
        Group root = new Group(); 
        Scene scene = new Scene(root, 600, 500);
        
        //Configuración del fondo de pantalla, se va adaptando segun agrandemos el scene
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(-0.3);
        
        ImageView imageView = new ImageView(rutaImagenes.getBackground());
        imageView.fitWidthProperty().bind(scene.widthProperty());
        imageView.fitHeightProperty().bind(scene.heightProperty());
        imageView.setEffect(colorAdjust);
        imageView.setPreserveRatio(false);
        
        //Configuración de la puntuación
        
        Label scoreLabel = new Label("SCORE: " + Score);
        Font customFont = Font.loadFont(("file:src\\Images\\customFont.ttf"),24);
        scoreLabel.setTranslateX(20);
        scoreLabel.setTranslateY(20);
        scoreLabel.setFont(customFont);
        scoreLabel.setStyle("-fx-text-fill: white;");
        
        //Configuracion de los aliens

        imagenAlien = new ImageView(rutaImagenes.getAlien());
        imagenAlien.setFitWidth(100);
        imagenAlien.setFitHeight(100);
        imagenAlien.setX(300);
        imagenAlien.setY(300);
        
        
        //Configuración de las animaciones para los aliens
        
        
        
        TranslateTransition translate = new TranslateTransition();
        int valorAleatorioX = (int) (Math.random()*600+1);
        int valorAleatorioY = (int) (Math.random()*300+1);
        translate.setByX(valorAleatorioX);
        translate.setByY(valorAleatorioY);
        translate.setDuration(Duration.millis(3000));
        translate.setAutoReverse(true);
        translate.setNode(imagenAlien);
        translate.play();
        
        //Evento del click
        
        imagenAlien.setOnMouseClicked(event -> {
            contadorAciertos++;
            Score += 10;
            scoreLabel.setText("SCORE: " + Score);
            
//            if(contadorAciertos == 5){
//                playS
//            }

            imagenAlien.setImage(rutaImagenes.getAlien());
        });
        
        scene.setOnMouseClicked(event -> {
        
            Score -= 30;
            scoreLabel.setText("SCORE: " + Score);
            imagenAlien.setImage(rutaImagenes.getAlien());
            
        });
        
        
        
        
        root.getChildren().add(imageView);
        root.getChildren().add(imagenAlien);
        root.getChildren().add(scoreLabel);
        
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
    
}
