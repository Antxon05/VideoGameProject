/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import Model.Imagenes;
import Model.Sonidos;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author Antxon
 */
public class PantallaWin extends Application{

    private Imagenes rutaImagenes = new Imagenes();
    private Font winFont = Font.loadFont(("file:src\\Images\\customFont.ttf"),50);
    
    
    private Sonidos sonidos = new Sonidos();
    private MediaPlayer mediaWin;
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        Group root = new Group();
        Scene winScene = new Scene(root, 900, 800);
        
        //Configuración del background
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(-0.5);
        
        ImageView backgroundWin = new ImageView(rutaImagenes.getBackground3());
        backgroundWin.fitWidthProperty().bind(winScene.widthProperty());
        backgroundWin.fitHeightProperty().bind(winScene.heightProperty());
        backgroundWin.setEffect(colorAdjust);
        backgroundWin.setPreserveRatio(false);
        
        //Añadimos algunos elementos del scene
        ImageView gifTrofeo = new ImageView(rutaImagenes.getTrofeo());
        gifTrofeo.setFitWidth(225);
        gifTrofeo.setFitHeight(220);
        gifTrofeo.setX(330);
        gifTrofeo.setY(100);
        
        ImageView gifConfetti = new ImageView(rutaImagenes.getConfetti());
        gifConfetti.setFitWidth(900);
        gifConfetti.setFitHeight(800);


        //Configuración del sonido
        this.mediaWin = sonidos.getSonidoVictoria();
        this.mediaWin.play();
        this.mediaWin.setVolume(0.4);
        
        //Elementos como labels, botones, etc...
        Label enhorabuenaLabel = new Label("YOU WIN!");
        enhorabuenaLabel.setTranslateX(250);
        enhorabuenaLabel.setTranslateY(360);
        enhorabuenaLabel.setFont(Font.font(winFont.getFamily(), FontWeight.BOLD, 50));
        enhorabuenaLabel.setStyle("-fx-text-fill: yellow;");
        
        Label l_playAgain =new Label("PLAY AGAIN");
        l_playAgain.setTranslateX(330);
        l_playAgain.setTranslateY(500);
        l_playAgain.setFont(Font.font(winFont.getFamily(), FontWeight.BOLD, 25));
        l_playAgain.setStyle("-fx-text-fill: white;");
        
        Button b_yes = new Button("YES");
        b_yes.setPrefWidth(100);
        b_yes.setPrefHeight(40);
        b_yes.setTranslateX(300);
        b_yes.setTranslateY(550);
        b_yes.setStyle("-fx-background-color: transparent; -fx-text-fill: #FFD700;");
        b_yes.setFont(Font.font(winFont.getFamily(), FontWeight.BOLD, 30));
        
        Button b_no = new Button("NO");
        b_no.setPrefWidth(100);
        b_no.setPrefHeight(40);
        b_no.setTranslateX(500);
        b_no.setTranslateY(550);
        b_no.setStyle("-fx-background-color: transparent; -fx-text-fill: #FF4500");
        b_no.setFont(Font.font(winFont.getFamily(), FontWeight.BOLD, 30));
        
        //Acimación de YouWin
        FadeTransition fade = new FadeTransition();
        fade.setDuration(Duration.millis(500));
        fade.setFromValue(1.0);
        fade.setToValue(0);
        fade.setCycleCount(FadeTransition.INDEFINITE);
        fade.setAutoReverse(true);
        fade.setNode(enhorabuenaLabel);
        fade.play();
        
        //Animación para el yes y el no
        FadeTransition fadeParpadeo = new FadeTransition(Duration.millis(300));
        fadeParpadeo.setFromValue(1.0);
        fadeParpadeo.setToValue(0.2);
        fadeParpadeo.setCycleCount(FadeTransition.INDEFINITE);
        fadeParpadeo.setAutoReverse(true);
        
        b_yes.setOnMouseEntered(event -> {
        fadeParpadeo.setNode(b_yes);
        fadeParpadeo.play();
        });
        
        b_yes.setOnMouseExited(event -> {
            fadeParpadeo.stop();
            b_yes.setOpacity(1.0);
        });
        
        b_no.setOnMouseEntered(event -> {
            fadeParpadeo.setNode(b_no);
            fadeParpadeo.play();
        });
        
        b_no.setOnMouseExited(event -> {
            fadeParpadeo.stop();
            b_no.setOpacity(1.0);
        });
        
        
        //Configuración en caso de que le demos al si
        b_yes.setOnAction((event) -> {
            
            PantallaPrincipal gameProject = new PantallaPrincipal();
            try{
                this.mediaWin.stop();
                gameProject.start(primaryStage);
            }catch(Exception e){
                e.printStackTrace();
            }
            
        });
        
        
        //Configuración cuando le demos al no
        b_no.setOnAction((event) -> {
            Platform.exit();
            System.exit(0);
        });
        
        
        root.getChildren().add(backgroundWin);
        root.getChildren().add(gifTrofeo);
        root.getChildren().add(enhorabuenaLabel);
        root.getChildren().add(gifConfetti);
        root.getChildren().add(b_yes);
        root.getChildren().add(b_no);
        root.getChildren().add(l_playAgain);
        
        primaryStage.setScene(winScene);
        primaryStage.show();
    }
    
    
    
}
