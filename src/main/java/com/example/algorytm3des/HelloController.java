package com.example.algorytm3des;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

public class HelloController {

    @FXML
    private TextField Klucz1;
    @FXML
    private TextField Klucz2;
    @FXML
    private TextField Klucz3;
    @FXML
    private Button zapiszTekstJawnyDoPliku;
    @FXML
    private Button zapiszZaszyfrowanyTekstDoPliku;
    @FXML
    private Button generator;
    @FXML
    private Button szyfrowanie;
    @FXML
    private Button deszyfrowanie;
    @FXML
    private TextArea input;
    @FXML
    private TextArea output;
    @FXML
    private Button wczytajPlik;

    private byte[] tekstJawny;
    private byte[] szyfrogram;
    private byte[][] klucze = new byte[3][];

    private TripleDES algorytm = new TripleDES();

    private Konwerter konwerter = new Konwerter();


    public void initialize() {
        generate();
    }

    @FXML
    public void generate() {
        Klucz1.setText(konwerter.bytesToHexString(generateKey(16)));
        Klucz2.setText(konwerter.bytesToHexString(generateKey(16)));
        Klucz3.setText(konwerter.bytesToHexString(generateKey(16)));
    }

    private byte[] generateKey(int keyLengthBytes) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] keyBytes = new byte[keyLengthBytes];
        secureRandom.nextBytes(keyBytes);
        return keyBytes;
    }

    @FXML
    public void szyfruj() throws Exception {
        this.klucze[0] = konwerter.hexStringToByteArray(Klucz1.getText());
        this.klucze[1] = konwerter.hexStringToByteArray(Klucz2.getText());
        this.klucze[2] = konwerter.hexStringToByteArray(Klucz3.getText());

        String wiadomosc = input.getText();
        String wynik = Encrypt(wiadomosc, klucze, true);
        output.setText(wynik);
    }

    @FXML
    public void deszyfruj() throws Exception {
        this.klucze[0] = konwerter.hexStringToByteArray(Klucz1.getText());
        this.klucze[1] = konwerter.hexStringToByteArray(Klucz2.getText());
        this.klucze[2] = konwerter.hexStringToByteArray(Klucz3.getText());

        String szyfr = output.getText();
        String wynik = Encrypt(szyfr, klucze, false);
        input.setText(wynik);
    }

    @FXML
    public void dodajPlik() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Wybierz plik");
        Stage stage = (Stage) wczytajPlik.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            try {
                byte[] fileBytes = FileUtils.readFileToByteArray(selectedFile);
                tekstJawny = fileBytes;
                String fileContent = new String(fileBytes, StandardCharsets.UTF_8);
                input.setText(fileContent);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String Encrypt(String message, byte[][] keys, boolean encrypt) throws Exception {
        byte[] bytes = message.getBytes(StandardCharsets.UTF_8);
        byte[] encryptedBytes;
        byte[] decryptedBytes;

        if (encrypt) {
            if (tekstJawny != null) {
                szyfrogram = algorytm.TripleDES_Encrypt(bytes, keys);
            }
            encryptedBytes = algorytm.TripleDES_Encrypt(bytes, keys);
            return konwerter.bytesToHexString(encryptedBytes);
        } else {
            if (szyfrogram != null) {
                tekstJawny = algorytm.TripleDES_Decrypt(szyfrogram, keys);
            }
            decryptedBytes = algorytm.TripleDES_Decrypt(konwerter.hexStringToByteArray(message), keys);
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        }
    }

    @FXML
    public void zapiszSzyfrDoPliku() {
        zapiszDoPliku(szyfrogram);
    }

    @FXML
    public void zapiszJawnyDoPliku() {
        zapiszDoPliku(tekstJawny);
    }

    private void zapiszDoPliku(byte[] dane) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Zapisz plik");
        Stage stage = (Stage) zapiszZaszyfrowanyTekstDoPliku.getScene().getWindow();
        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.write(dane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}