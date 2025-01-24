/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package videogameproject_antxonmoço;

import java.awt.event.MouseEvent;
import javafx.animation.FadeTransition;
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
public class VideoGameProject_AntxonMoço extends Application {
    
    

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
    private MediaPlayer mediaDerrota;
    
    
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
        
        //Configuración de la vida inicial, puntuación y niveles
        
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
        this.mediaFondo.setAutoPlay(true);
        this.mediaFondo.setCycleCount(100);
        this.mediaFondo.setVolume(0.15);
        
        this.mediaDisparo = sonidos.getDisparo();
        this.mediaDisparo.setVolume(0.1);
        
        this.mediaVictoria = sonidos.getSonidoVictoria();
        this.mediaVictoria.setVolume(0.15);
        
        //Animación del contador de 3 segundos
        
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
            
            
            //Coge las coordenadas absolutas del alien
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
                mostrarPantallaVictoria(primaryStage);
                this.mediaFondo.stop();
                this.mediaVictoria.play();
                return;
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
        
        //Evento del click, en caso de que falles
        
        scene.setOnMouseClicked(event -> {
            stats.restarScore();
            scoreLabel.setText("SCORE: " + stats.getScore());
            
            if (stats.getScore() < 0) {
                stats.perderVida();
                stats.resetScore();
                scoreLabel.setText("SCORE: " + stats.getScore());
                actualizarVidas(vida1, vida2, vida3, primaryStage);
            }
            
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

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    
    //Metodo para generar el alien en una posicion aleatoria
    private void posicionAleatoriaAlien() {
        double maxX = scene.getWidth();
        double maxY = scene.getHeight();
        double x = (Math.random() * (maxX - margenReaparicion));
        double y = (Math.random() * (maxY - margenReaparicion));
        imagenAlien.setX(x);
        imagenAlien.setY(y);
    }
    
    
    private void actualizarVidas(ImageView vida1, ImageView vida2, ImageView vida3, Stage primaryStage){
        int vidas = stats.getLifes();
        
        vida1.setVisible(vidas >= 1);
        vida2.setVisible(vidas >= 2);
        vida3.setVisible(vidas >= 3);
        
        if(vidas == 0){
            if(!gameOver){
                System.out.println("Game Over");
                this.mediaDerrota = sonidos.getDerrota();
                this.mediaDerrota.setVolume(0.15);
                this.mediaFondo.stop();
                this.mediaDerrota.play();
                mostrarPantallaDerrota(primaryStage);
                gameOver = true;
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
    
    
    //Mostrara la pantalla de victoria
    private void mostrarPantallaVictoria(Stage primaryStage){
        Group root = new Group();
        Scene winScene = new Scene(root, 900, 800);
        
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(-0.5);
        
        ImageView backgroundWin = new ImageView(rutaImagenes.getBackground3());
        backgroundWin.fitWidthProperty().bind(winScene.widthProperty());
        backgroundWin.fitHeightProperty().bind(winScene.heightProperty());
        backgroundWin.setEffect(colorAdjust);
        backgroundWin.setPreserveRatio(false);
        
        
        ImageView gifTrofeo = new ImageView(rutaImagenes.getTrofeo());
        gifTrofeo.setFitWidth(225);
        gifTrofeo.setFitHeight(250);
        gifTrofeo.setX(330);
        gifTrofeo.setY(170);
        
        ImageView gifConfetti = new ImageView(rutaImagenes.getConfetti());
        gifConfetti.setFitWidth(900);
        gifConfetti.setFitHeight(800);
//        gifConfetti.setX(225);
//        gifConfetti.setY(170);
        
        Label enhorabuenaLabel = new Label("!YOU WIN!");
        enhorabuenaLabel.setTranslateX(225);
        enhorabuenaLabel.setTranslateY(500);
        enhorabuenaLabel.setFont(Font.font(winFont.getFamily(), FontWeight.BOLD, 50));
        enhorabuenaLabel.setStyle("-fx-text-fill: yellow;");
        
        FadeTransition fade = new FadeTransition();
        fade.setDuration(Duration.millis(500));
        fade.setFromValue(1.0);
        fade.setToValue(0);
        fade.setCycleCount(FadeTransition.INDEFINITE);
        fade.setAutoReverse(true);
        fade.setNode(enhorabuenaLabel);
        fade.play();
        
        
        root.getChildren().add(backgroundWin);
        root.getChildren().add(gifTrofeo);
        root.getChildren().add(enhorabuenaLabel);
        root.getChildren().addAll(gifConfetti);
        
        primaryStage.setScene(winScene);
        primaryStage.show();
    }
    
    
    //Mostrara la pantalla de derrota
        private void mostrarPantallaDerrota(Stage primaryStage){
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
        
        
        
        Label l_game = new Label("GAME");
        l_game.setTranslateX(200);
        l_game.setTranslateY(200);
        l_game.setFont(Font.font(winFont.getFamily(), FontWeight.BOLD, 80));
        l_game.setStyle("-fx-text-fill: orange;");
        
        Label l_over = new Label("OVER");
        l_over.setTranslateX(400);
        l_over.setTranslateY(300);
        l_over.setFont(Font.font(winFont.getFamily(), FontWeight.BOLD, 80));
        l_over.setStyle("-fx-text-fill: orange;");
        
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
        
        
        //Configuración en caso de que le demos al no
        
        b_yes.setOnAction((event) -> {
            try {
                // Reiniciar todos los valores
                stats.resetScore();
                stats.reiniciarVidas();  // Asegúrate de tener un método resetLifes en Stats para restablecer las vidas

                // Volver a mostrar los corazones
                vida1.setVisible(true);
                vida2.setVisible(true);
                vida3.setVisible(true);

                // Resetear el alien, puntuación, nivel, etc.
                imagenAlien.setImage(rutaImagenes.getAlien());
                imagenAlien.setFitWidth(200);
                imagenAlien.setFitHeight(200);
                posicionAleatoriaAlien();

                // Resetear el fondo (por ejemplo, si cambió de fondo en el nivel 2 o 3)
                imageFondo.setImage(rutaImagenes.getBackground1());

                // Resetear la puntuación y el nivel
                scoreLabel.setText("SCORE: " + stats.getScore());
                nivelLabel.setText("LEVEL: " + stats.getLevel());

                start(primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        
        
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
