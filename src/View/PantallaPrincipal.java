/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package View;

import Model.Imagenes;
import Model.Sonidos;
import Model.Stats;
import java.awt.event.MouseEvent;
import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
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
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author antxon
 */
public class PantallaPrincipal extends Application {
    
    

    private Imagenes rutaImagenes = new Imagenes();
    private Stats stats = new Stats();
    private Sonidos sonidos = new Sonidos();
    
    private ImageView vida1;
    private ImageView vida2;
    private ImageView vida3;
    private ImageView explosion = new ImageView(rutaImagenes.getExplosion());
    private ImageView imagenAlien = new ImageView();
    private ImageView imageFondo = new ImageView();
    
    private Label scoreLabel = new Label();
    private Label nivelLabel = new Label();
    private Font defaultFont = Font.loadFont(("file:src\\Images\\customFont.ttf"),20);
    private Font winFont = Font.loadFont(("file:src\\Images\\customFont.ttf"),50);
    
    
    private boolean activarnivel2;
    private boolean activarnivel3;
    private boolean gameOver = false;
    private int margenReaparicion = 500;
            
    private TranslateTransition movimientoAlien = new TranslateTransition();
    private Scene scene;
    
    
    //Items de media
    private MediaPlayer mediaFondo;
    private MediaPlayer mediaDisparo;
    private MediaPlayer mediaVictoria;
    private MediaPlayer mediaFail;

    
    
    @Override
    public void start(Stage primaryStage) {
        Group root = new Group(); 
        scene = new Scene(root, 900, 800);
        

        //Configuración del fondo de pantalla, se va adaptando segun agrandemos el scene
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(-0.3);
        
        imageFondo = new ImageView(rutaImagenes.getBackground1());
        imageFondo.fitWidthProperty().bind(scene.widthProperty());
        imageFondo.fitHeightProperty().bind(scene.heightProperty());
        imageFondo.setEffect(colorAdjust);
        imageFondo.setPreserveRatio(false);
        
        //Configuración de la vida inicial
        
        vida1 = new ImageView(rutaImagenes.getVidaLlena());
        vida2 = new ImageView(rutaImagenes.getVidaLlena());
        vida3 = new ImageView(rutaImagenes.getVidaLlena());
        
        vida1.setFitWidth(50);
        vida1.setFitHeight(50);
        vida1.setTranslateX(30);
        vida1.setTranslateY(10);
        
        vida2.setFitWidth(50);
        vida2.setFitHeight(50);
        vida2.setTranslateX(80);
        vida2.setTranslateY(10);
        
        vida3.setFitWidth(50);
        vida3.setFitHeight(50);
        vida3.setTranslateX(130);
        vida3.setTranslateY(10);

        
        //Configuración del Score
        scoreLabel = new Label("SCORE: " + stats.getScore());
        scoreLabel.setTranslateX(650);
        scoreLabel.setTranslateY(20);
        scoreLabel.setFont(defaultFont);
        scoreLabel.setStyle("-fx-text-fill: white;");
        
        //Connfiguración del Level
        nivelLabel = new Label("LEVEL: " + stats.getLevel());
        nivelLabel.setTranslateX(350);
        nivelLabel.setTranslateY(20);
        nivelLabel.setFont(defaultFont);
        nivelLabel.setStyle("-fx-text-fill: white;");
        
        //Configuracion de los aliens
        imagenAlien = new ImageView(rutaImagenes.getAlien());
        imagenAlien.setFitWidth(200);
        imagenAlien.setFitHeight(200);
        posicionAleatoriaAlien();
        
        //Configuración de los sonidos
        this.mediaFondo = sonidos.getSonidoFondo();
        this.mediaFondo.play();
        this.mediaFondo.setCycleCount(Timeline.INDEFINITE);
        this.mediaFondo.setVolume(0.10);
        
        this.mediaDisparo = sonidos.getDisparo();
        this.mediaDisparo.setVolume(0.1);
        
        this.mediaFail = sonidos.getFail();
        this.mediaFail.setVolume(0.6);
        
        this.mediaVictoria = sonidos.getSonidoVictoria();
        this.mediaVictoria.setVolume(0.15);
        
        //Configuración del contador de 3 segundos, para que desaparezca el alien
        javafx.animation.Timeline timeline = new javafx.animation.Timeline(
            new javafx.animation.KeyFrame(
                Duration.seconds(3), //El alien se muestra durante 3 segundos
                e -> {
                    // Si no se hace clic, se resta el puntuaje
                    stats.restarScore();
                    scoreLabel.setText("SCORE: " + stats.getScore());

                    // Verificar si el puntuaje es menor a 0
                    if (stats.getScore() < 0) {
                        stats.perderVida();
                        stats.resetScore();
                        scoreLabel.setText("SCORE: " + stats.getScore());
                        actualizarVidas(vida1, vida2, vida3, primaryStage);
                    }

                    // Generar una nueva posición del alien
                    posicionAleatoriaAlien();
                }
            )
        );
        timeline.setCycleCount(javafx.animation.Timeline.INDEFINITE);
        timeline.play();
        
        //Evento del click cuando impacta al alien
        imagenAlien.setOnMouseClicked(event -> {
            stats.incrementarAciertos();
            stats.setScore();
            scoreLabel.setText("SCORE: " + stats.getScore());
            this.mediaDisparo.seek(Duration.ZERO);
            this.mediaDisparo.play();
            imagenAlien.setUserData("clicked");
            
            
            //Coge las coordenadas absolutas del alien para generar la explosion en ese mismo punto
            javafx.geometry.Point2D alienPosition = imagenAlien.localToScene(imagenAlien.getX(), imagenAlien.getY());
            
            this.explosion.setFitWidth(50);
            this.explosion.setFitHeight(50);
            this.explosion.setX(alienPosition.getX());
            this.explosion.setY(alienPosition.getY());
        
            if(root.getChildren().contains(explosion)){
                root.getChildren().remove(explosion);
            }
            ((Group)scene.getRoot()).getChildren().add(explosion);
        
        //Configuración del tiempo de explosion
            javafx.animation.Timeline removeExplosion = new javafx.animation.Timeline(
            new javafx.animation.KeyFrame(Duration.millis(500), e -> ((Group) scene.getRoot()).getChildren().remove(explosion))
            );
            removeExplosion.setCycleCount(1);
            removeExplosion.play();
            
            
            //Configuración del nivel 2
            if(stats.getScore() >= 50 && !activarnivel2){
                activarnivel2 = true;
                imageFondo.setImage(rutaImagenes.getBackground2());
                //Aumentamos dificultado haciendo mas pequeños los aliens
                imagenAlien.setFitWidth(130);
                imagenAlien.setFitHeight(130);
                stats.incrementarLevel();
                nivelLabel.setText("LEVEL: " + stats.getLevel());
            }
            
            //Configuración del nivel 3
            if(stats.getScore() >= 100 && !activarnivel3){
                activarnivel3 = true;
                imageFondo.setImage(rutaImagenes.getBackground3());
                imagenAlien.setFitWidth(90);
                imagenAlien.setFitHeight(90);
                stats.incrementarLevel();
                nivelLabel.setText("LEVEL: " + stats.getLevel());
            }
            
            //Si llega  150 nos lleva a la pantalla de victoria
            if(stats.getScore() >= 150){
                PantallaWin pWin = new PantallaWin();
                try{
                    this.mediaFondo.stop();
                    pWin.start(primaryStage);                    
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            
            //Cuando se active los niveles 2 o 3 comenzaran los movimientos.
            if(activarnivel2){
                configurarMovimientoNivel2();
            }
            
            if(activarnivel3){
                configurarMovimientoNivel3();
            }
            
            posicionAleatoriaAlien();
            timeline.playFromStart(); // Reinicia el temporizador cuando le das click
            event.consume();
            imagenAlien.setImage(rutaImagenes.getAlien());
        });
        
        //Evento del click al scene, en caso de que falles
        scene.setOnMouseClicked(event -> {
            this.mediaFail.seek(Duration.ZERO);
            this.mediaFail.play();
            stats.restarScore();
            scoreLabel.setText("SCORE: " + stats.getScore());
            
            if (stats.getScore() < 0) {
                stats.perderVida();
                stats.resetScore();
                scoreLabel.setText("SCORE: " + stats.getScore());
                //Llama al metodo cuando tiene menos de 0 de score
                actualizarVidas(vida1, vida2, vida3, primaryStage);
            }
            
            //Actualiza la imagen del alien
            imagenAlien.setImage(rutaImagenes.getAlien());
        });
        
        
        root.getChildren().add(imageFondo);
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
    
    //DEFINICIÓN DE LOS METODOS USADOS:
    
    
    //Metodo para generar el alien en una posicion aleatoria
    private void posicionAleatoriaAlien() {
        double maxX = scene.getWidth();
        double maxY = scene.getHeight();
        double x = (Math.random() * (maxX - margenReaparicion));
        double y = (Math.random() * (maxY - margenReaparicion));
        imagenAlien.setX(x);
        imagenAlien.setY(y);
    }
    
    //Metodo para verificar las vidas que tiene, en caso de que tenga 0 ha perdido
    private void actualizarVidas(ImageView vida1, ImageView vida2, ImageView vida3, Stage primaryStage){
        int vidas = stats.getLifes();
        
        vida1.setVisible(vidas >= 1);
        vida2.setVisible(vidas >= 2);
        vida3.setVisible(vidas >= 3);
        
        if(vidas == 0){
            PantallaGameOver pGameOver = new PantallaGameOver();
                System.out.println("Game Over");
                try{
                  this.mediaFondo.stop();
                  pGameOver.start(primaryStage);
                }catch(Exception e){
                    e.printStackTrace();
                }
        }
    }
    
    //Metodo para configurar el movimiento del nivel 2
    private void configurarMovimientoNivel2() {
        if(movimientoAlien != null){
            movimientoAlien.stop();
        }
        
        double maxX = scene.getWidth();
        double maxY = scene.getHeight();
        double x = (Math.random() * (maxX - margenReaparicion));
        double y = (Math.random() * (maxY - margenReaparicion));
        
        movimientoAlien = new TranslateTransition(Duration.seconds(3), imagenAlien);
        movimientoAlien.setFromX(imagenAlien.getX());
        movimientoAlien.setFromY(imagenAlien.getY());
        movimientoAlien.setToX(x);
        movimientoAlien.setToY(y);
        movimientoAlien.setCycleCount(1);
        
        
        movimientoAlien.setOnFinished(e -> {
            //Cuando acaba el movimiento, cambia de posicion y pierdes puntos
            posicionAleatoriaAlien();
            configurarMovimientoNivel2();
        });
        
        movimientoAlien.play();
    }
    
    //Funcion para configurar el movimiento del nivel 3, es mas rapido
    private void configurarMovimientoNivel3(){
        if(movimientoAlien != null){
            movimientoAlien.stop();
        }
        
        double maxX = scene.getWidth();
        double maxY = scene.getHeight();
        double x = (Math.random() * (maxX - margenReaparicion));
        double y = (Math.random() * (maxY - margenReaparicion));
        
        movimientoAlien = new TranslateTransition(Duration.seconds(1.5), imagenAlien);
        movimientoAlien.setFromX(imagenAlien.getX());
        movimientoAlien.setFromY(imagenAlien.getY());
        movimientoAlien.setToX(x);
        movimientoAlien.setToY(y);
        movimientoAlien.setCycleCount(1);
        
        
        movimientoAlien.setOnFinished(e -> {
            //Cuando acaba el movimiento, cambia de posicion y pierdes puntos
            posicionAleatoriaAlien();
            configurarMovimientoNivel2();
        });
        
        movimientoAlien.play();
    }
    

}
