/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistaControlador;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
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
import modelo.Nota;
import modelo.Ponderacion;

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
            this.TXT.setAlignment(Pos.CENTER_RIGHT);
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
    private Ponderacion ponderaciones;
    private Fila fila;
    int indiceFila;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        gpPanel.setVgap(1);
        gpPanel.setAlignment(Pos.TOP_CENTER);
        ponderaciones = new Ponderacion();
        indiceFila = 0;
        txtPonderacion.requestFocus();
    }


    @FXML
    private void agregar(ActionEvent event) {
        try {
            controlExcepcionRuntime();
            Button btn = new Button(txtPonderacion.getText());
            Label lbl = new Label(txtNota.getText());
            TextField txt = new TextField();
            btn.addEventHandler(ActionEvent.ACTION, evt -> agregarDato(evt));
            lbl.addEventHandler(MouseEvent.MOUSE_CLICKED, evt -> llamarDialogo(evt));
            txt.addEventHandler(ActionEvent.ACTION, evt -> agregarDato(evt));
            btn.setText(txtPonderacion.getText());
            lbl.setAlignment(Pos.CENTER_RIGHT);
            fila = new Fila(btn, txt, lbl);
            fila.TXT.minWidth(100);
            controlesRuntime.put(btn, fila);
            gpPanel.addRow(indiceFila++,
                    controlesRuntime.get(btn).LBL,
                    controlesRuntime.get(btn).TXT,
                    controlesRuntime.get(btn).BTN);
            controlesRuntime.get(btn).LBL.setText("");
            controlesRuntime.get(btn).TXT.requestFocus();
            txtPonderacion.setText("");
        } catch (Exception e) {
            controlExcepcionRuntime(e);
        }
    }

    private void controlExcepcionRuntime() throws Exception {
        Pattern p = Pattern.compile("^[0-9]");
        Matcher m = p.matcher(txtPonderacion.getText());
        if (!m.find()) {
            throw new Exception();
        }
    }
    private void agregarDato(ActionEvent evt) {
        try {
            if (evt.getSource() instanceof Label) {
                crearDialogo(controlesRuntime.get(fila.BTN).BTN).showAndWait();
            } else if (evt.getSource() instanceof Button || evt.getSource() instanceof TextField) {
                Button btn = fila.BTN;
                System.out.println(btn);
                controlesRuntime.get(btn).LBL.setText(
                        controlesRuntime.get(btn).LBL.getText()
                        + " " + controlesRuntime.get(btn).TXT
                                .getText());
                ponderaciones.add(new Nota(Double.parseDouble(
                        controlesRuntime.get(btn).TXT.getText()),
                        Double.parseDouble(controlesRuntime.get(btn).BTN.getText())));
                txtNota.setText(String.valueOf(ponderaciones.calcularMedia()));
                fila.TXT.setText("");
            }
        }catch (Exception e) {
            controlExcepcionRuntime(e);
        }

        }

    private void llamarDialogo(MouseEvent evt){
        Button btn = fila.BTN;
        crearDialogo(controlesRuntime.get(btn).BTN).showAndWait();
    }

    private void controlExcepcionRuntime(Exception e) {
        Dialog d = new Dialog();
        d.setTitle("Error");
        d.setHeaderText(e.toString());
        d.getDialogPane().getButtonTypes().add(ButtonType.OK);
        d.showAndWait();
        if (fila.TXT != null) {
            controlesRuntime.get(fila.BTN).TXT.setText("");
        }
    }

    private Dialog crearDialogo(Button btn) {
        Dialog dialogo = new Dialog();
        dialogo.setTitle("Notas ponderadas");
        dialogo.setHeaderText("ver/editar notas de la ponderación elegida");
        dialogo.getDialogPane()
                .getButtonTypes()
                .addAll(ButtonType.OK, ButtonType.CANCEL);
        TextArea txaNotas = new TextArea(
                controlesRuntime.get(btn).LBL.getText().substring(1));
        dialogo.getDialogPane().setContent(txaNotas);
        dialogo.setResultConverter(button -> {
            String resultado = null;
            if (button == ButtonType.OK) {
                controlesRuntime.get(btn).LBL.setText("");
                System.out.println(btn.getText());
                ponderaciones.remove(Double.parseDouble(btn.getText()));
                String[] notasArrayString = txaNotas.getText().split("\\s+");
                for (int i = 0; i < notasArrayString.length; i++) {
                    controlesRuntime.get(btn)
                            .LBL.setText(controlesRuntime.get(btn)
                                    .LBL.getText() + " " + notasArrayString[i]);
                }

                ArrayList<Double> notasArrayDouble = (ArrayList<Double>) Arrays.stream(notasArrayString)
                        .map(Double::parseDouble)
                        .collect(Collectors.toList());

                Nota[] notasArrayNota = new Nota[notasArrayString.length];
                for (int i = 0; i < notasArrayNota.length; i++) {
                    notasArrayNota[i] = new Nota(notasArrayDouble.get(i), Double.parseDouble(btn.getText()));
                }
                ponderaciones.add(notasArrayNota);
                resultado = String.valueOf(ponderaciones.calcularMedia());
                txtNota.setText(resultado);
            }
            return resultado;
        });
        return dialogo;
    }
}
