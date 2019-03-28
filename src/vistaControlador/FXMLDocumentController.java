/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistaControlador;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import jdk.nashorn.internal.runtime.ListAdapter;
import modelo.Nota;
import modelo.Ponderaciones;

/**
 *
 * @author daw
 */
public class FXMLDocumentController implements Initializable {

    class Fila {

        private final Button BTN;
        private final TextField TXT;
        private final Label LBL;

        public Fila(Button btn, TextField txt, Label lbl) {
            this.BTN = btn;
            this.TXT = txt;
            this.LBL = lbl;
        }
    }
    @FXML
    private TextField txtNota;
    @FXML
    private TextField txtPonderacion;
    @FXML
    private Button btnAgregar;
    HashMap<Button, Fila> controlesRuntime = new HashMap<>();
    @FXML
    private GridPane gpPanel;
    private Ponderaciones ponderaciones;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        gpPanel.setVgap(1);
        gpPanel.setAlignment(Pos.TOP_CENTER);
        ponderaciones = new Ponderaciones();
    }
    int indiceFila = 0;

    @FXML
    private void agregar(MouseEvent event) {
        Button btn = new Button(txtPonderacion.getText());
        Label lbl = new Label(txtNota.getText());
        TextField txt = new TextField();
        btn.addEventHandler(MouseEvent.MOUSE_CLICKED, evt -> agregarDato(evt));
        lbl.addEventHandler(MouseEvent.MOUSE_CLICKED, evt -> agregarDato(evt));
        btn.setText(txtPonderacion.getText());
        lbl.setAlignment(Pos.CENTER_RIGHT);
        Fila fila = new Fila(btn, txt, lbl);
        fila.TXT.minWidth(100);
        controlesRuntime.put(btn, fila);
        gpPanel.addRow(indiceFila++,
                 controlesRuntime.get(btn).LBL,
                 controlesRuntime.get(btn).TXT,
                 controlesRuntime.get(btn).BTN);
    }

    private void agregarDato(MouseEvent evt) {

        if (evt.getSource() instanceof Label) {
            crearDialogo().showAndWait();
        } else if (evt.getSource() instanceof Button) {
            Button btn = (Button) evt.getSource();
            controlesRuntime.get(btn).LBL.setText(
                    controlesRuntime.get(btn).LBL.getText() 
                            + " " + controlesRuntime.get(btn).TXT
                    .getText());
            ponderaciones.add(new Nota(Double.parseDouble(
                    controlesRuntime.get(btn).TXT.getText()),
                     Double.parseDouble(controlesRuntime.get(btn).BTN.getText())));
            txtNota.setText(String.valueOf(ponderaciones.calcularMedia()));
        }
    }

    private Dialog crearDialogo() {
        Dialog dialogo = new Dialog();
        dialogo.setTitle("Notas ponderadas");
        dialogo.setHeaderText("ver/editar notas de la ponderación elegida");
        dialogo.getDialogPane().getButtonTypes().addAll(ButtonType.OK,
                ButtonType.CANCEL);
        TextArea txaNotas = new TextArea();
        dialogo.setResultConverter(button -> {
            String resultado = null;
            if (button == ButtonType.OK) {
                resultado = txaNotas.getText();
            }
            return resultado;
        });
        return dialogo;
    }
}
