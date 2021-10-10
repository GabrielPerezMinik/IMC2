package imc;

import javafx.application.Application;
import javafx.beans.binding.Binding;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

public class IMC extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		TextField pesoTextField;
		TextField alturaTextField;
		
		pesoTextField = new TextField();
		
		alturaTextField = new TextField();
		
		
		Label clasificacionLabel = new Label();
		
		
		
		Label estadoLabel = new Label();
		clasificacionLabel.setText("2");
		
		Label pesoLabel;
		HBox pesoHbox = new HBox(pesoLabel = new Label("Peso "), pesoTextField = new TextField(), pesoLabel = new Label("kg"));
        pesoHbox.setAlignment(Pos.CENTER);


        Label alturaLabel;
		HBox alturaHbox = new HBox(alturaLabel = new Label("Altura "), alturaTextField = new TextField(), alturaLabel = new Label("cm"));
        alturaHbox.setAlignment(Pos.CENTER);

        Label clasificaionLabel = new Label();
        clasificaionLabel.setAlignment(Pos.CENTER);
        Label estadoLabel1 = new Label();
        estadoLabel1.setAlignment(Pos.CENTER);
		
		VBox root = new VBox(5);
        root.setAlignment(Pos.CENTER);
		root.getChildren().addAll(pesoHbox,alturaHbox,clasificacionLabel,estadoLabel1);
		
		Scene escena = new Scene(root, 300, 100);
		primaryStage.setScene(escena);
		primaryStage.setTitle("IMC.fxml");
		primaryStage.show();
		
		SimpleDoubleProperty peso = new SimpleDoubleProperty();
		Bindings.bindBidirectional(pesoTextField.textProperty(),peso, new NumberStringConverter());
		
		SimpleDoubleProperty altura = new SimpleDoubleProperty();
		Bindings.bindBidirectional(alturaTextField.textProperty(),altura, new NumberStringConverter());
		
		
		DoubleBinding operacion = altura.divide(100);
		operacion=peso.divide(operacion.multiply(operacion));
		
		SimpleDoubleProperty resultado;
		resultado = new SimpleDoubleProperty();
		resultado.bind(operacion);
		
		clasificacionLabel.textProperty().bind(Bindings.concat("IMC: ").
		concat(Bindings.when(altura.isEqualTo(0)).then("peso * altura^2").otherwise(resultado.asString("%.2f"))));
		
		resultado.addListener((o, ov, nv)->{
			double IMC=nv.doubleValue();
			if(IMC < 18.5) {
				estadoLabel1.setText("Bajo Peso");
			}else if(IMC >= 18.5 && IMC <25){
				estadoLabel1.setText("normal");
			}else if(IMC >= 25 && IMC <30){
				estadoLabel1.setText("sobrepeso");
			}else {
				estadoLabel1.setText("Obeso");
			}
			
		});
		
	}

	public static void main(String[] args) {

		launch(args);

	}

}
