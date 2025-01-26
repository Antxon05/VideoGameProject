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
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author Antxon
 */
public class PantallaInicial extends Application{

    private Imagenes rutaImagenes = new Imagenes();
    private Sonidos sounds = new Sonidos();
    
    private Font inicioFont = Font.loadFont(("file:src\\Images\\Orbitron-ExtraBold.ttf"),20);
    
    private MediaPlayer s_fondo;
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        Group root = new Group();
        Scene startScene = new Scene(root, 900, 800);
        
        //Configuración del background
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(-0.5);
        
        ImageView backgroundStart = new ImageView(rutaImagenes.getBackgroundPrincipal());
        backgroundStart.fitWidthProperty().bind(startScene.widthProperty());
        backgroundStart.fitHeightProperty().bind(startScene.heightProperty());
        backgroundStart.setEffect(colorAdjust);
        backgroundStart.setPreserveRatio(false);
        
        //Configuración del sonido de fondo
        this.s_fondo = sounds.getSonidoFondo();
        this.s_fondo.play();
        this.s_fondo.setVolume(0.10);
        
        
        //Configruación de los elementos usados, text, buttons, etc...
        Text l_title = new Text("ALIEN SHOOTER");
        l_title.setFont(Font.font(inicioFont.getFamily(),FontWeight.BOLD,FontPosture.REGULAR,60));
        l_title.setFill(Color.WHITE);
        l_title.setX(170);
        l_title.setY(200);
        
        
        Button b_start = new Button("START GAME");
        b_start.setFont(Font.font(inicioFont.getFamily(), FontWeight.BOLD, FontPosture.REGULAR, 20));
        b_start.setStyle("-fx-background-color: white;");
        b_start.setTranslateX(330);
        b_start.setTranslateY(400);
        b_start.setPrefWidth(250);
        b_start.setPrefHeight(50);
        
        Button b_exit = new Button("EXIT");
        b_exit.setFont(Font.font(inicioFont.getFamily(), FontWeight.BOLD, FontPosture.REGULAR, 20));
        b_exit.setStyle("-fx-background-color: white;");
        b_exit.setTranslateX(330);
        b_exit.setTranslateY(500);
        b_exit.setPrefWidth(250);
        b_exit.setPrefHeight(50);
        
        Button b_info = new Button("HOW TO PLAY");
        b_info.setFont(Font.font(inicioFont.getFamily(), FontWeight.BOLD, FontPosture.REGULAR, 20));
        b_info.setStyle("-fx-background-color: white;");
        b_info.setTranslateX(330);
        b_info.setTranslateY(600);
        b_info.setPrefWidth(250);
        b_info.setPrefHeight(50);

        //Animación para el parpadeo de los botones
        FadeTransition fadeParpadeo = new FadeTransition(Duration.millis(300));
        fadeParpadeo.setFromValue(1.0);
        fadeParpadeo.setToValue(0.5);
        fadeParpadeo.setCycleCount(FadeTransition.INDEFINITE);
        fadeParpadeo.setAutoReverse(true);
        
        
        b_start.setOnMouseEntered(event -> {
        fadeParpadeo.setNode(b_start);
        fadeParpadeo.play();
        });
        
        b_start.setOnMouseExited(event -> {
            fadeParpadeo.stop();
            b_start.setOpacity(1.0);
        });
        
        b_exit.setOnMouseEntered(event -> {
            fadeParpadeo.setNode(b_exit);
            fadeParpadeo.play();
        });
        
        b_exit.setOnMouseExited(event -> {
            fadeParpadeo.stop();
            b_exit.setOpacity(1.0);
        });
        
        
        b_info.setOnMouseEntered(event -> {
            fadeParpadeo.setNode(b_info);
            fadeParpadeo.play();
        });
        
        b_info.setOnMouseExited(event -> {
            fadeParpadeo.stop();
            b_info.setOpacity(1.0);
        });
        
        
        //Evento para cuando le demos al boton de start
        b_start.setOnAction((event) -> {
        
            PantallaPrincipal pPrincipal = new PantallaPrincipal();
            
            try{
                this.s_fondo.stop();
                pPrincipal.start(primaryStage);
            }catch(Exception e){
                e.printStackTrace();
            }
        });
        
        //Evento para cuando le demos al boton de exit
        b_exit.setOnAction((event) -> {
            Platform.exit();
            System.exit(0);
        });
        
        //Evento para cuando le demos al boton de how to play
        b_info.setOnAction((event) -> {
            PantallaInfo pInfo = new PantallaInfo();
            
            try{
              this.s_fondo.stop();
              pInfo.start(primaryStage);
            }catch(Exception e){
                e.printStackTrace();
            }
        
        });
        
        
        //Agregamos los elementos al root
        root.getChildren().add(backgroundStart);
        root.getChildren().add(l_title);
        root.getChildren().add(b_start);
        root.getChildren().add(b_exit);
        root.getChildren().add(b_info);
        
        primaryStage.setScene(startScene);
        primaryStage.show();
        
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
