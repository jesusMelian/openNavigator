package com.jesusCompanySoft;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
	// write your code here
        openNavigatorWithParameters(2, "www.google.es");
    }

    private static void openNavigatorWithParameters(int numVeces, String url){
        String command = "firefox";

        try {
            ProcessBuilder processBuilder = new ProcessBuilder();

            processBuilder.command(command, "--new-tab", url);

            Process process = processBuilder.start();

            if(process.isAlive()){
                System.out.println("El proceso no ha terminado");
            }
            System.out.println("El proceso termino");

        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < numVeces; i++) {

        }
    }
}
