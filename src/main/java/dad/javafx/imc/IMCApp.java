package dad.javafx.imc;

import java.text.NumberFormat;

import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

public class IMCApp extends Application {

	private TextField pesoText;
	private TextField alturaText;
	private Label imcLabel;
	private Label estadoLabel;

	private DoubleProperty peso = new SimpleDoubleProperty();
	private DoubleProperty altura = new SimpleDoubleProperty();
	private StringProperty estado = new SimpleStringProperty();

	@Override
	public void start(Stage primaryStage) throws Exception {

		pesoText = new TextField();
		pesoText.setMaxWidth(100);

		alturaText = new TextField();
		alturaText.setMaxWidth(100);

		imcLabel = new Label();

		estadoLabel = new Label();

		HBox pesoBox = new HBox(5, new Label("Peso: "), pesoText, new Label(" kg"));
		pesoBox.setAlignment(Pos.CENTER);
		pesoBox.setFillHeight(false);

		HBox alturaBox = new HBox(5, new Label("Altura: "), alturaText, new Label(" cm"));
		alturaBox.setAlignment(Pos.CENTER);
		alturaBox.setFillHeight(false);

		HBox imcBox = new HBox(5, new Label("IMC: "), imcLabel);
		imcBox.setAlignment(Pos.CENTER);
		imcBox.setFillHeight(false);

		VBox root = new VBox(5, pesoBox, alturaBox, imcBox, estadoLabel);
		root.setAlignment(Pos.CENTER);
		root.setFillWidth(false);

		Scene scene = new Scene(root, 320, 200);

		primaryStage.setTitle("Sample6");
		primaryStage.setScene(scene);
		primaryStage.show();

		pesoText.textProperty().bindBidirectional(peso, new NumberStringConverter());

		alturaText.textProperty().bindBidirectional(altura, new NumberStringConverter());

		estadoLabel.textProperty().bindBidirectional(estado);

		imcLabel.textProperty().bind(peso.divide(altura.multiply(altura)).multiply(10000).asString("%.2f"));

		imcLabel.textProperty().addListener((o, ov, nv) -> {
			onEstados();
		});
	}

	private void onEstados() {
		try {
			double pesoTotal = NumberFormat.getInstance().parse(imcLabel.getText()).doubleValue();
			if (pesoTotal < 18.5) {
				estadoLabel.setText("Bajo peso");
			} else if (pesoTotal >= 18.5 && pesoTotal < 25) {
				estadoLabel.setText("Normal");
			} else if (pesoTotal >= 25 && pesoTotal < 30) {
				estadoLabel.setText("Sobrepeso");
			} else if (pesoTotal >= 30) {
				estadoLabel.setText("Obeso");
			}
		} catch (Exception e) {

		}
	}

	public static void main(String[] args) {
		launch(args);
	}

}
