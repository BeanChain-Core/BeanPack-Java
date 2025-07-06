/**
 * ==============================================================
 *   BeanTeam™ Core Source
 *   https://github.com/BeanChain-Core
 *   Part of the BeanChain / WizKeys infrastructure stack
 *    (c) Outlandish Creative LLC — All Rights Delicious™
 * ==============================================================
 */
package com.beanpack.Wizard;

import java.io.File;
import java.util.Scanner;
import com.beanpack.beanify.Branding;
import com.beanpack.beanify.Color;
import wiz.crypt.CryptKeeper;
import com.beanpack.Wizard.wizard;

public class WizCryptHandler {
    public static CryptKeeper wizCrypt;

    //wizcrypt messages
    public static String wizCryptMessageFactory(String consoleLog, String status){
        String color = Color.PURPLE;

        switch(status){
            case "ERROR":
                color = Color.RED;
                break;
            default:
                break;
        }

        String statusForm = String.format("%s{WIZCrypt} [%-12s]", color, status);
        String message = consoleLog + Color.RESET + "\n";
        String bar = "-----------------";
        return String.format("%-14s %-18s %s", statusForm, bar, message);
    }

    public static void showLoadingBarDynamic(String label, int durationMs) {
        final int steps = 20;
        final int delay = durationMs / steps;

        System.out.print(label + ": ");
        for (int i = 0; i <= steps; i++) {
            int percent = (100 * i) / steps;
            String bar = "=".repeat(i) + " ".repeat(steps - i);
            System.out.print("\r" + label + ": [" + bar + "] " + percent + "%");
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println(); // move to next line
    }

    public static void waitForFile(String path, int timeoutMillis) throws InterruptedException {
        File file = new File(path);
        int waited = 0;
        int interval = 100; // ms between checks

        while (!file.exists()) {
            if (waited >= timeoutMillis) {
                System.out.println("Timeout waiting for file: " + path);
                return;
            }
            Thread.sleep(interval);
            waited += interval;
        }

        //System.out.println("📄 File detected: " + file.getAbsolutePath());
    }

    //config
    private static File configFolder = new File("config.docs/");
    private static String password;
    private static String wizFileEnc = "wiz.txt.enc";
    private static File secret;
    private static File keyFile;
    private static String keyPath;


    //setters 
    public static void setPassword(String pw) {
        password = pw;
    }

    public static void setConfigFolder(File folder) {
        if (folder != null) {
            configFolder.mkdirs();
            secret = new File(folder, "wiz.secret.key");
        }
    }

    public static void setEncryptedFileName(String fileName) {
        if (fileName != null && !fileName.isBlank()) {
            wizFileEnc = fileName;
        }
    }

    public static void setKeyFile(File unencryptedFile) {
        if (unencryptedFile != null && unencryptedFile.exists()) {
            keyFile = unencryptedFile;
        }
    }

    public static void setWizFileEnc(String wizFile) { wizFileEnc = wizFile;}
    public static void setKeyPath(String key) { keyPath = key;}


    public static boolean initializeWizCrypt(){
        try {
            CryptKeeper.initializeNewKey(password, secret);
            String secretPath = secret.getAbsolutePath();
            waitForFile(secretPath, 0);
            System.out.println(Color.PURPLE + Branding.wizCrypt + Color.RESET);
            System.out.println(wizCryptMessageFactory("WizCrypt Secret Initialized", "INIT" ));
            bootWizCrypt(password);
            return true;
        } catch (Exception keeperDropException){
            System.out.println(wizCryptMessageFactory("Failed to initialize", "ERROR"));
            //keeperDropException.printStackTrace();
            return false;
        }
    }

    public static void bootWizCrypt(String passAgain){
        try {
            WizCryptHandler.wizCrypt = CryptKeeper.loadFromPassword(passAgain, secret);
        } catch (Exception keeperDropException){
            System.out.println(wizCryptMessageFactory("Failed to Boot uP", "ERROR"));
            //keeperDropException.printStackTrace();
        }
    }

    public static boolean encryptWizKeySquared(){
        try {
            WizCryptHandler.wizCrypt.encryptFile(keyFile, configFolder);
            keyFile.delete();
            System.out.println(Color.PURPLE + Branding.wizCrypt + Color.RESET);
            System.out.println(wizCryptMessageFactory("WizCrypt Encryption/Decryption: SUCCESS", "ENCRYPT-WIZ"));
            return true;
        } catch (Exception keeperDropException){
            System.out.println(wizCryptMessageFactory("Failed to encrypt @WizKey", "ERROR"));
            //keeperDropException.printStackTrace();
            return false;
        }
    }

    public static boolean wizEncryptConfig(){
        try {
            WizCryptHandler.wizCrypt.encryptFile(keyFile, configFolder);
            keyFile.delete();
            System.out.println(wizCryptMessageFactory("WizCrypt Encryption/Decryption: SUCCESS", "ENCRYPT-WIZ"));
            return true;
        } catch (Exception keeperDropException){
            System.out.println(wizCryptMessageFactory("Failed to encrypt @WizConfig", "ERROR"));
            //keeperDropException.printStackTrace();
            return false;
        }
    }

    public static boolean decryptWizKeySquared(){
        try {
            WizCryptHandler.wizCrypt.decryptFile(new File(configFolder, wizFileEnc), configFolder);
            return true;
        } catch (Exception keeperDropException){
            System.out.println(wizCryptMessageFactory("Failed to decrypt @WizKey", "ERROR"));
            //keeperDropException.printStackTrace();
            return false;
        }
    }

    public static void encryptPrivateKeyHexRaw(String privateKeyHex){
        try{

            String encryptedL1WizKey = wizard.getEncryptedWizardKey(privateKeyHex, password);
            wizard.saveEncryptedWizKey(encryptedL1WizKey, keyPath);
            File keyFileInternal = new File(keyPath);
            setKeyFile(keyFileInternal);

            
        } catch (Exception keeperDropException){
            System.out.println(wizCryptMessageFactory("Failed to encrypt @WizKey @rawHex", "ERROR"));
            //keeperDropException.printStackTrace();
        }

        showLoadingBarDynamic("WIZZIN YOUR BEANS", 2000); //buffer for file saving 

        try{
            encryptWizKeySquared();
        } catch (Exception keeperDropException){
            System.out.println(wizCryptMessageFactory("Failed to encrypt @WizKey @FILE", "ERROR"));
            //keeperDropException.printStackTrace();
        }
    }

    public static String readL2EncWizKey(){
        try{

            if(!decryptWizKeySquared()){
                System.out.println(wizCryptMessageFactory("Failed to READ @squared", "ERROR"));
                return null;
            }

            showLoadingBarDynamic("WIZZIN YOUR BEANS", 2000);
            String l1EncKey = wizard.wizardRead(keyPath);
            File keyInternal = new File(keyPath);
            setKeyFile(keyInternal);
            encryptWizKeySquared();
            return wizard.decryptWizKey(l1EncKey, password);    
        } catch (Exception keeperDropException){
            System.out.println(wizCryptMessageFactory("Failed to READ @WizKey @L2Enc", "ERROR"));
            //keeperDropException.printStackTrace();
            return null;
            
        }
    }

    public static void encryptConfig(){
        File keyFileBackup = keyFile;
        keyFile = new File(configFolder, "beanchain.config.properties");
        wizFileEnc = "beanchain.config.properties.enc";
        bootWizCrypt(password);
        wizEncryptConfig();
        keyFile = keyFileBackup;
        wizFileEnc = "wiz.txt.enc";
        System.out.println(wizCryptMessageFactory("***Config Encrypted during RUNTIME***", "ENCRYPT")); 
    }

    public static void decryptConfig(){
        File keyFileBackup = keyFile;
        keyFile = new File(configFolder, "beanchain.config.properties");
        wizFileEnc = "beanchain.config.properties.enc";
        bootWizCrypt(password);
        decryptWizKeySquared();
        keyFile = keyFileBackup;
        wizFileEnc = "wiz.txt.enc"; 
        System.out.println(wizCryptMessageFactory("***Config Decrypted for OFFLINE access***", "DECRYPT"));   
    }

    public static void main(String [] args) throws Exception{
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Encryptor Pass");
        String input = scanner.nextLine();

        WizCryptHandler.setPassword(input);
        WizCryptHandler.setWizFileEnc("wiz.txt.enc");
        WizCryptHandler.setConfigFolder(new File("config.docs/"));
        WizCryptHandler.setKeyPath("config.docs/wiz.txt");
        WizCryptHandler.bootWizCrypt(input);

        boolean on = true;
        while(on){
            System.out.println("[1] Init new key");
            System.out.println("[2] Encrypt wizkey");
            System.out.println("[3] Decrypt wizkey");
            System.out.println("[4] Encrypt raw Key");
            System.out.println("[5] Retrieve raw Key");
            System.out.println("[6] EXIT");
            System.out.print("> ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> WizCryptHandler.initializeWizCrypt();
                case 2 -> {
                    WizCryptHandler.bootWizCrypt(input);
                    WizCryptHandler.encryptWizKeySquared();
                }
                case 3 -> {
                    WizCryptHandler.bootWizCrypt(input);
                    WizCryptHandler.decryptWizKeySquared();
                }
                case 4 -> {
                    System.out.println("Enter your PrivateKey");
                    String privateKey = scanner.nextLine();
                    encryptPrivateKeyHexRaw(privateKey);
                }
                case 5 -> {
                    System.out.println(readL2EncWizKey());
                }
                case 6 -> {
                    on = false;
                }
            }
            
        }
        scanner.close();
    }
}
