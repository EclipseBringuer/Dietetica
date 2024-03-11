package com.example.dietetica.controller;

import com.example.dietetica.Application;
import com.example.dietetica.Cliente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.swing.JRViewer;

import javax.swing.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private RadioButton radioMujer;
    @FXML
    private TextField txtNombre;
    @FXML
    private Spinner<Double> spinnerTalla;
    @FXML
    private Spinner<Integer> spinnerEdad;
    @FXML
    private ToggleGroup genero;
    @FXML
    private RadioButton radioHombre;
    @FXML
    private Spinner<Double> spinnerPeso;
    @FXML
    private ComboBox<String> comboActividad;
    @FXML
    private TextArea txtObservaciones;
    @FXML
    private Button btnGuardar;
    @FXML
    private Label info;
    @FXML
    private Button btnDescargar;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comboActividad.getItems().addAll("Sedentario", "Moderado", "Activo", "Muy Activo");
        comboActividad.getSelectionModel().select(1);

        spinnerEdad.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1, 1));
        spinnerPeso.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(1, 200, 50, 0.25));
        spinnerTalla.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(1, 250, 150, 1));
    }

    private boolean checkCampos() {
        boolean out = true;

        if (Objects.equals(txtNombre.getText(), "")) {
            out = false;
            Application.makeNewAlert(Alert.AlertType.INFORMATION,
                    "Error al añadir cliente",
                    "Rellena el campo del nombre",
                    "Pulsa aceptar para volver al formulario").showAndWait();
        }
        return out;
    }

    @FXML
    public void saveCliente(ActionEvent actionEvent) {
        if (checkCampos()) {
            Cliente c = new Cliente();
            c.setNombre(txtNombre.getText().trim());
            if (genero.selectedToggleProperty().getValue().equals(radioHombre)) {
                c.setGenero("hombre");
            } else {
                c.setGenero("mujer");
            }
            c.setEdad(spinnerEdad.getValue());
            c.setPeso(spinnerPeso.getValue());
            c.setTalla(spinnerTalla.getValue());
            c.setActividad(comboActividad.getValue());
            c.setObservaciones(txtObservaciones.getText().trim());
            c.setGer(calcularGer(c));
            c.setGet(calcularGet(c));
            info.setText("El cliente " + c.getNombre() + "tiene un GER de " + Math.round(c.getGer()) + " y un GET de " + Math.round(c.getGet()));
        }
    }

    private Double calcularGer(Cliente c) {
        Double out = 0.0;
        if (Objects.equals(c.getGenero(), "hombre")) {
            out = 66.473 + 13.751 * c.getPeso() + 5.0033 * c.getTalla() - 6.755 * c.getEdad();
        } else {
            out = 655.0955 + 9.463 * c.getPeso() + 1.8496 * c.getTalla() - 4.6756 * c.getEdad();
        }
        return out;
    }

    private Double calcularGet(Cliente c) {
        Double out = 0.0;
        if (Objects.equals(c.getGenero(), "hombre")) {
            out = switch (c.getActividad()) {
                case "Sedentario" -> c.getGer() * 1.3;
                case "Moderado" -> c.getGer() * 1.6;
                case "Activo" -> c.getGer() * 1.7;
                case "Muy Activo" -> c.getGer() * 2.1;
                default -> out;
            };
        } else {
            out = switch (c.getActividad()) {
                case "Sedentario" -> c.getGer() * 1.3;
                case "Moderado" -> c.getGer() * 1.5;
                case "Activo" -> c.getGer() * 1.6;
                case "Muy Activo" -> c.getGer() * 1.9;
                default -> out;
            };
        }
        return out;
    }

    @FXML
    public void getReport(ActionEvent actionEvent) throws JRException, SQLException {
        //Obtener conexion
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/interfaces", "root", "15112004");

        //Rellenar el formulario
        var jasperPrint = JasperFillManager.fillReport("Clientes.jasper", new HashMap<>(), connection);

        //Visualizar informe
        JRViewer viewer = new JRViewer(jasperPrint);
        JFrame frame = new JFrame("Listado de Clientes");
        frame.getContentPane().add(viewer);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.pack();
        frame.setVisible(true);

        //Descargar pdf
        JRPdfExporter exp = new JRPdfExporter();
        exp.setExporterInput(new SimpleExporterInput(jasperPrint));
        exp.setExporterOutput(new SimpleOutputStreamExporterOutput("alquileres.pdf"));
        exp.setConfiguration(new SimplePdfExporterConfiguration());
        exp.exportReport();
    }
}