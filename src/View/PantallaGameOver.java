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
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author Antxon
 */
public class PantallaGameOver extends Application{

    private Imagenes rutaImagenes = new Imagenes();
    private Sonidos sonidos = new Sonidos();
    
    private Font loseFont = Font.loadFont(("file:src\\Images\\customFont.ttf"),50);
    
    private MediaPlayer mediaDerrota;
    
    @Override
    public void start(Stage primaryStage) throws Exception {
                //Mostrara la pantalla de derrota
        Group root = new Group();
        Scene looseScene = new Scene(root, 900, 800);
        
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(-0.5);
        
        ImageView backgroundWin = new ImageView(rutaImagenes.getBackground3());
        backgroundWin.fitWidthProperty().bind(looseScene.widthProperty());
        backgroundWin.fitHeightProperty().bind(looseScene.heightProperty());
        backgroundWin.setEffect(colorAdjust);
        backgroundWin.setPreserveRatio(false);
        
        
        ImageView lluviaGif = new ImageView(rutaImagenes.getLluvia());
        lluviaGif.setFitWidth(looseScene.getWidth());
        lluviaGif.setFitHeight(looseScene.getHeight());
        
        //Configuración sonido
        this.mediaDerrota = sonidos.getDerrota();
        this.mediaDerrota.play();
        
        Label l_game = new Label("GAME");
        l_game.setTranslateX(200);
        l_game.setTranslateY(200);
        l_game.setFont(Font.font(loseFont.getFamily(), FontWeight.BOLD, 80));
        l_game.setStyle("-fx-text-fill: orange;");
        
        Label l_over = new Label("OVER");
        l_over.setTranslateX(400);
        l_over.setTranslateY(300);
        l_over.setFont(Font.font(loseFont.getFamily(), FontWeight.BOLD, 80));
        l_over.setStyle("-fx-text-fill: orange;");
        
        Label l_playAgain =new Label("PLAY AGAIN");
        l_playAgain.setTranslateX(330);
        l_playAgain.setTranslateY(500);
        l_playAgain.setFont(Font.font(loseFont.getFamily(), FontWeight.BOLD, 25));
        l_playAgain.setStyle("-fx-text-fill: white;");
        
        
        Button b_yes = new Button("YES");
        b_yes.setPrefWidth(100);
        b_yes.setPrefHeight(40);
        b_yes.setTranslateX(300);
        b_yes.setTranslateY(550);
        b_yes.setStyle("-fx-background-color: transparent; -fx-text-fill: #FFD700;");
        b_yes.setFont(Font.font(loseFont.getFamily(), FontWeight.BOLD, 30));
        
        Button b_no = new Button("NO");
        b_no.setPrefWidth(100);
        b_no.setPrefHeight(40);
        b_no.setTranslateX(500);
        b_no.setTranslateY(550);
        b_no.setStyle("-fx-background-color: transparent; -fx-text-fill: #FF4500");
        b_no.setFont(Font.font(loseFont.getFamily(), FontWeight.BOLD, 30));
        
        //Animacion de parpadeo para los botones
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
        
        //Animación de parpadeo para el label de Game
        FadeTransition fadeGame = new FadeTransition();
        fadeGame.setDuration(Duration.millis(500));
        fadeGame.setFromValue(1.0);
        fadeGame.setToValue(0);
        fadeGame.setCycleCount(FadeTransition.INDEFINITE);
        fadeGame.setAutoReverse(true);
        fadeGame.setNode(l_game);
        fadeGame.play();
        
        //Animación de parpadeo para el label de Over
        FadeTransition fadeOver = new FadeTransition();
        fadeOver.setDuration(Duration.millis(500));
        fadeOver.setFromValue(1.0);
        fadeOver.setToValue(0);
        fadeOver.setCycleCount(FadeTransition.INDEFINITE);
        fadeOver.setAutoReverse(true);
        fadeOver.setNode(l_over);
        fadeOver.play();
        
        
        //Configuración en caso de que le demos al si
        b_yes.setOnAction((event) -> {
            
            PantallaPrincipal gameProject = new PantallaPrincipal();
            try{
                this.mediaDerrota.stop();
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
        root.getChildren().add(l_game);
        root.getChildren().add(l_over);
        root.getChildren().add(lluviaGif);
        root.getChildren().add(l_playAgain);
        root.getChildren().add(b_yes);
        root.getChildren().add(b_no);
        
        primaryStage.setScene(looseScene);
        primaryStage.show();
    }

    
}
