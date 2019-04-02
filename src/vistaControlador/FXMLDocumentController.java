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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
    private Label lbl;
    private TextField txt;
    private Button btn;
    int indiceFila;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        gpPanel.setVgap(1);
        gpPanel.setAlignment(Pos.TOP_CENTER);
        ponderaciones = new Ponderacion();
        indiceFila = 0;
    }

    @FXML
    private void agregarTeclado(KeyEvent event) {
    
    }
    @FXML
    private void agregar(ActionEvent event) {
         try {
            controlExcepcionRuntime();
            Button btn = new Button(txtPonderacion.getText());
            Label lbl = new Label(txtNota.getText());
            TextField txt = new TextField();
            btn.addEventHandler(MouseEvent.MOUSE_CLICKED, evt -> agregarDato(evt));
            lbl.addEventHandler(MouseEvent.MOUSE_CLICKED, evt -> agregarDato(evt));
            txt.addEventHandler(MouseEvent.MOUSE_CLICKED, evt -> agregarDato(evt));
            txt.addEventHandler(KeyEvent.KEY_PRESSED, evt -> operarTxT(evt));
            btn.setText(txtPonderacion.getText());
            lbl.setAlignment(Pos.CENTER_RIGHT);
            Fila fila = new Fila(btn, txt, lbl);
            fila.TXT.minWidth(100);
            controlesRuntime.put(btn, fila);
            gpPanel.addRow(indiceFila++,
                    controlesRuntime.get(btn).LBL,
                    controlesRuntime.get(btn).TXT,
                    controlesRuntime.get(btn).BTN);
            setControlesRuntime(btn);
            controlesRuntime.get(btn).LBL.setText("");
        } catch (Exception e) {
            controlExcepcionRuntime(e);
        }
    }
    private void agregar(MouseEvent event) {
       

    }

    private void controlExcepcionRuntime() throws Exception {
        Pattern p = Pattern.compile("^[0-9]");
        Matcher m = p.matcher(txtPonderacion.getText());
        if (!m.find()) {
            throw new Exception();
        }
    }

    private void operarTxT(KeyEvent evt) {
        try {
            if (evt.getCode().equals(KeyCode.ENTER)) {
                TextField txt = (TextField) evt.getSource();
                ponderaciones.add(new Nota(Double.parseDouble(this.txt.getText()),
                        Double.parseDouble(this.btn.getText())));
                txtNota.setText(String.valueOf(ponderaciones.calcularMedia()));
                this.lbl.setText(this.lbl.getText() + " " + this.txt.getText());
                this.txt.setText("");
            }
        } catch (Exception e) {
            controlExcepcionRuntime(e);
        }

    }

    private void agregarDato(MouseEvent evt) {
        System.out.println(evt.getTarget());
        System.out.println(evt.getSource());
        System.out.println(evt.getEventType());

        try {
            if (evt.getSource() instanceof Label) {
                crearDialogo(controlesRuntime.get(btn).BTN).showAndWait();
            } else if (evt.getSource() instanceof Button) {
                Button btn = (Button) evt.getSource();
                System.out.println(btn);
                controlesRuntime.get(btn).LBL.setText(
                        controlesRuntime.get(btn).LBL.getText()
                        + " " + controlesRuntime.get(btn).TXT
                                .getText());
                ponderaciones.add(new Nota(Double.parseDouble(
                        controlesRuntime.get(btn).TXT.getText()),
                        Double.parseDouble(controlesRuntime.get(btn).BTN.getText())));
                txtNota.setText(String.valueOf(ponderaciones.calcularMedia()));
            } else if (evt.getSource() instanceof TextField) {
                TextField txt = (TextField) evt.getSource();
                txt.setText("");
            }
        } catch (Exception e) {
            controlExcepcionRuntime(e);
        }

    }

    private void controlExcepcionRuntime(Exception e) {
        Dialog d = new Dialog();
        d.setTitle("Error");
        d.setHeaderText(e.toString());
        d.getDialogPane().getButtonTypes().add(ButtonType.OK);
        d.showAndWait();
        if (txt != null) {
            this.txt.setText("");
        }
    }

    private void setControlesRuntime(Button btn1) {
        this.btn = controlesRuntime.get(btn1).BTN;
        this.txt = controlesRuntime.get(btn1).TXT;
        this.lbl = controlesRuntime.get(btn1).LBL;
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
                    System.out.println(notasArrayString[i]);
                    controlesRuntime.get(btn)
                            .LBL.setText(controlesRuntime.get(btn)
                                    .LBL.getText()+" "+notasArrayString[i]);
                }

                ArrayList<Double> notasArrayDouble = (ArrayList<Double>) Arrays.stream(notasArrayString)
                        .map(Double::parseDouble)
                        .collect(Collectors.toList());

                Nota[] notasArrayNota = new Nota[notasArrayString.length];
                for (int i = 0; i < notasArrayNota.length; i++) {
                    notasArrayNota[i] = new Nota(notasArrayDouble.get(i),Double.parseDouble(btn.getText()) );
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
