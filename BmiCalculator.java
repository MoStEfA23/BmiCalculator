/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bmicalculator;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 *
 * @author MoStEfA23
 */
public class BmiCalculator extends Application {
    
    TextField weightTextField = new TextField();
    TextField heightTextField = new TextField();
    
    Button rigidBmiButton = new Button("Rigid BMI");
    Label rigidBmiLabel = new Label("0");
    Label dynamicBmiText = new Label("Dynamic BMI Label");
    Label dynamicBmiLabel = new Label("0");
    
    @Override
    public void start(Stage primaryStage) {
        GridPane grid = setupScene();
        
        setupAction();
        
        Scene scene = new Scene(grid, 600, 200);
        
        primaryStage.setTitle("Bmi Calculator");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    private GridPane setupScene() {
        GridPane grid = new GridPane();
       
        
        grid.setVgap(10);
        grid.setHgap(20);
       
        
        grid.add(weightTextField, 0, 0);
        grid.add(heightTextField, 1, 0);
        grid.add(rigidBmiButton, 0, 1);
        grid.add(rigidBmiLabel, 1, 1);
        grid.add(dynamicBmiText, 0, 2);
        grid.add(dynamicBmiLabel, 1, 2);
        
        weightTextField.setPromptText("Weight in lbs");
        heightTextField.setPromptText("Height in Centimeter");
        weightTextField.setFont(Font.font(20));
        heightTextField.setFont(Font.font(20));
        rigidBmiButton.setFont(Font.font(20));
        dynamicBmiText.setFont(Font.font(20));
        
        GridPane.setHalignment(grid, HPos.CENTER);
        grid.setAlignment(Pos.CENTER);
        
        return grid;
    }

    private void setupAction() {
        rigidBmiButton.setOnAction(event -> {
            rigidBmiLabel.setDisable(false);
            String weightString = weightTextField.getText().trim();
            String heightString = heightTextField.getText().trim();
            
            if (!weightString.isEmpty() && ! heightString.isEmpty())
            {
                double weight = Double.parseDouble(weightString);
                System.out.println(weight);
                double height = Double.parseDouble(heightString);
                height /= 100; //convert height from cm to m
                System.out.println(height);
                
                rigidBmiLabel.setText(String.format("%.2f", weight / (height * height)));
            }});
        DoubleBinding bmiBinding = new DoubleBinding() {
            //initialization block
            {
                super.bind(weightTextField.textProperty(), heightTextField.textProperty());
            }
            @Override
            protected double computeValue() {
                
            String weightString = weightTextField.getText().trim();
            String heightString = heightTextField.getText().trim();
            
            if (!weightString.isEmpty() && ! heightString.isEmpty())
            {
                double weight = Double.parseDouble(weightString);
                double height = Double.parseDouble(heightString) / 100;//convert from cm to m

                return weight / (height * height);
            }
            else{
                return 0;
            }
            }
        };
            
        dynamicBmiLabel.textProperty().bind(Bindings.format("%.2f", bmiBinding));
        bmiBinding.addListener((observable) -> rigidBmiLabel.setDisable(true));
    }
    
}
