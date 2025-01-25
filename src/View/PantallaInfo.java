/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Antxon
 */
public class PantallaInfo extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
        
        Group root = new Group();
        Scene infoScene = new Scene(root, 900, 800);
        
        
        
        
        
        primaryStage.setScene(infoScene);
        primaryStage.show();
        
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
