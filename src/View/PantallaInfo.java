/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import Model.Imagenes;
import Model.Sonidos;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author Antxon
 */
public class PantallaInfo extends Application{

    private Imagenes rutaImagenes = new Imagenes();
    private Sonidos sounds = new Sonidos();
    
    private Font inicioFont = Font.loadFont(("file:src\\Images\\Orbitron-ExtraBold.ttf"),20);
    
    private MediaPlayer s_fondo;
    
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        
        Group root = new Group();
        Scene infoScene = new Scene(root, 900, 800);
        
        //Configuración del background
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(-0.5);
        
        ImageView backgroundStart = new ImageView(rutaImagenes.getBackgroundPrincipal());
        backgroundStart.fitWidthProperty().bind(infoScene.widthProperty());
        backgroundStart.fitHeightProperty().bind(infoScene.heightProperty());
        backgroundStart.setEffect(colorAdjust);
        backgroundStart.setPreserveRatio(false);
        
        //Configuración del sonido
        this.s_fondo = sounds.getSonidoFondo();
        this.s_fondo.play();
        this.s_fondo.setVolume(0.10);
        
        //Elementos definidos
        Text l_info = new Text("HOW TO PLAY");
        l_info.setFont(Font.font(inicioFont.getFamily(),FontWeight.BOLD,FontPosture.REGULAR,60));
        l_info.setFill(Color.WHITE);
        l_info.setX(200);
        l_info.setY(130);
        
        // Texto definido por fragmentos por usar algunos en negrita y diferente tamaño de tipografia
        Text t1 = new Text("Este juego consiste en ir matando aliens constantemente, con cada acierto ");
        t1.setFont(Font.font(inicioFont.getFamily(), FontWeight.NORMAL, FontPosture.REGULAR, 15));
        t1.setFill(Color.WHITE);

        Text t2 = new Text("GANARÁS 10 PUNTOS ");
        t2.setFont(Font.font(inicioFont.getFamily(), FontWeight.BOLD, FontPosture.REGULAR, 20)); // Negrita
        t2.setFill(Color.WHITE);

        Text t3 = new Text("y cada vez que falles o desaparezca ");
        t3.setFont(Font.font(inicioFont.getFamily(), FontWeight.NORMAL, FontPosture.REGULAR, 15));
        t3.setFill(Color.WHITE);

        Text t4 = new Text("PERDERÁS 30 PUNTOS.\n\n");
        t4.setFont(Font.font(inicioFont.getFamily(), FontWeight.BOLD, FontPosture.REGULAR, 20)); // Negrita
        t4.setFill(Color.WHITE);

        Text t5 = new Text("Tienes 3 VIDAS, cada vez que llegues a 0 y vuelvas a fallar o desaparezca, perderás una vida.\n\n");
        t5.setFont(Font.font(inicioFont.getFamily(), FontWeight.NORMAL, FontPosture.REGULAR, 15));
        t5.setFill(Color.WHITE);

        Text t6 = new Text("Se divide en diferentes niveles (al llegar a x puntos cambias de nivel):\n\n");
        t6.setFont(Font.font(inicioFont.getFamily(), FontWeight.NORMAL, FontPosture.REGULAR, 15));
        t6.setFill(Color.WHITE);

        Text t7 = new Text("NIVEL 1: ");
        t7.setFont(Font.font(inicioFont.getFamily(), FontWeight.BOLD, FontPosture.REGULAR, 20)); // Negrita
        t7.setFill(Color.WHITE);

        Text t8 = new Text("Los aliens están quietos, reaparecen de manera aleatoria y si NO lo matas en menos de 3 segundos desaparece y perderás 30 puntos.\n\n");
        t8.setFont(Font.font(inicioFont.getFamily(), FontWeight.NORMAL, FontPosture.REGULAR, 15));
        t8.setFill(Color.WHITE);

        Text t9 = new Text("NIVEL 2: ");
        t9.setFont(Font.font(inicioFont.getFamily(), FontWeight.BOLD, FontPosture.REGULAR, 20)); // Negrita
        t9.setFill(Color.WHITE);

        Text t10 = new Text("Cuando llegues a 50 PUNTOS llegarás a este nivel, en este nivel los aliens se empiezan a mover de manera aleatoria y disminuye el tamaño de ellos, de igual manera pueden desaparecer.\n\n");
        t10.setFont(Font.font(inicioFont.getFamily(), FontWeight.NORMAL, FontPosture.REGULAR, 15));
        t10.setFill(Color.WHITE);

        Text t11 = new Text("NIVEL 3: ");
        t11.setFont(Font.font(inicioFont.getFamily(), FontWeight.BOLD, FontPosture.REGULAR, 20)); // Negrita
        t11.setFill(Color.WHITE);

        Text t12 = new Text("Cuando llegues a 100 PUNTOS llegarás a este nivel, en este nivel los aliens se mueven de manera aleatoria más rápido y disminuye su tamaño considerablemente, de igual manera pueden desaparecer.\n\n");
        t12.setFont(Font.font(inicioFont.getFamily(), FontWeight.NORMAL, FontPosture.REGULAR, 15));
        t12.setFill(Color.WHITE);
        
        Text t13 = new Text("Cuando llegues a 150 PUNTOS has ganado.");
        t13.setFont(Font.font(inicioFont.getFamily(), FontWeight.NORMAL, FontPosture.REGULAR, 20));
        t13.setFill(Color.WHITE);

        // Combina los textos creados anteriormente al text flow
        TextFlow textFlow = new TextFlow(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13);
        textFlow.setLayoutX(50);
        textFlow.setLayoutY(180);
        textFlow.setPrefWidth(800);
        
        
        Button b_volver = new Button("RETURN");
        b_volver.setFont(Font.font(inicioFont.getFamily(), FontWeight.BOLD, FontPosture.REGULAR, 20));
        b_volver.setStyle("-fx-background-color: white;");
        b_volver.setTranslateX(330);
        b_volver.setTranslateY(680);
        b_volver.setPrefWidth(250);
        b_volver.setPrefHeight(50);
        
        
        //Configuración de las animaciones y los eventos
        FadeTransition fadeParpadeo = new FadeTransition(Duration.millis(300));
        fadeParpadeo.setFromValue(1.0);
        fadeParpadeo.setToValue(0.5);
        fadeParpadeo.setCycleCount(FadeTransition.INDEFINITE);
        fadeParpadeo.setAutoReverse(true);
        
        b_volver.setOnMouseEntered(event -> {
        fadeParpadeo.setNode(b_volver);
        fadeParpadeo.play();
        });
        
        b_volver.setOnMouseExited(event -> {
            fadeParpadeo.stop();
            b_volver.setOpacity(1.0);
        });
        
        
        b_volver.setOnAction((event) -> {
        
            PantallaInicial pInicial = new PantallaInicial();
            
            try{
                this.s_fondo.stop();
                pInicial.start(primaryStage);
            }catch(Exception e){
                e.printStackTrace();
            }
        });

        
        //Añadimos los elementos al root
        root.getChildren().addAll(backgroundStart, l_info, textFlow, b_volver);
        
        
        primaryStage.setScene(infoScene);
        primaryStage.show();
        
    }
    
}
