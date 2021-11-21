package com.jesusCompanySoft;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {
        //openNavigatorWithParametersEjer1(3);
        //openNavigatorWithParametersEjer2(3);
        openNavigatorWithParametersEjer3("/home/jesusmelian/Documentos/cambios.txt.txt");
    }


    private static void openNavigatorWithParametersEjer1(int numVeces) {
        String navigator = "chromium";
        String url = "www.google.es";
        int exitValue=2;
        try {
            //Creo el process Builder
            ProcessBuilder processBuilder = new ProcessBuilder().command(navigator, "--new-window", url);
            //Creo un ArrayList de procesos
            ArrayList<Process> arrProceso = new ArrayList<>();

            //Hago el bucle por el número de veces que introduce el usuario
            for (int i = 0; i < numVeces; i++) {
                //Meto el proceso iniciado en el ArrayList
                arrProceso.add(processBuilder.start());
            }
            //Recorro el arrayList de procesos
            for (int i = 0; i < arrProceso.size(); i++) {
                //Espero a que el proceso termine
                exitValue=arrProceso.get(i).waitFor();
                System.out.println(exitValue);
            }


        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void openNavigatorWithParametersEjer2(int numVeces) {
        String navigator = "chromium";
        String url = "www.google.es";

        try {
            //Creo el process Builder
            ProcessBuilder processBuilder = new ProcessBuilder().command(navigator);
            //Creo un ArrayList de procesos
            ArrayList<Process> arrProceso = new ArrayList<>();

            //Hago el bucle por el número de veces que introduce el usuario
            for (int i = 0; i < numVeces; i++) {

                //Meto el proceso iniciado en el ArrayList
                arrProceso.add(processBuilder.start());

                //Instancio la clase Calendar para obtener la hora
                Calendar calendario = new GregorianCalendar();
                String horaActual = (calendario.get(Calendar.HOUR_OF_DAY) + ":" + calendario.get(Calendar.MINUTE) + ":" + calendario.get(Calendar.SECOND) + ":" + calendario.get(Calendar.MILLISECOND)).toString();
                System.out.println("PID: "+arrProceso.get(i).pid() +" Proceso iniciado a las: "+horaActual);
            }
            //Recorro el arrayList de procesos
            for (int i = 0; i < arrProceso.size(); i++) {
                //Espero a que el proceso termine
                boolean exitValue=arrProceso.get(i).waitFor(500, TimeUnit.MILLISECONDS);

                //Instancio la clase Calendar para obtener la hora
                Calendar calendario = new GregorianCalendar();
                String horaActual = (calendario.get(Calendar.HOUR_OF_DAY) + ":" + calendario.get(Calendar.MINUTE) + ":" + calendario.get(Calendar.SECOND) + ":" + calendario.get(Calendar.MILLISECOND)).toString();
                System.out.println("PID: "+arrProceso.get(i).pid() +" Proceso finalizado a las: "+horaActual);
            }

            for (int i = 0; i < arrProceso.size(); i++) {
                //Manera de hacerlo modo ratón de laboratorio
                //ProcessBuilder processBuilder2 = new ProcessBuilder().command("kill", arrProceso.get(i).pid()+"");
                //Process proceso2 = processBuilder2.start();
                //proceso2.waitFor();


                //Modo de hacerlo "Elegante como un bogabante"
                arrProceso.get(i).destroy();
            }


        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void openNavigatorWithParametersEjer3(String ruta) {

        File file = new File(ruta);
        ProcessBuilder processBuilder = new ProcessBuilder();

        processBuilder.command("cat", file.toString(), "|", "wc", "-l");
        try{
            if(file.exists()){
                Process process = processBuilder.start();
                //.getInputStream, pero el desarrollador se lio, por que deberia ser output
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

                ProcessHandle.Info info = process.info();

                System.out.println(info.user());
                System.out.println(info.command());
                System.out.println(info.arguments());
                System.out.println(info.commandLine());
                System.out.println(info.startInstant());
                System.out.println(info.totalCpuDuration());

                String line;
                line = reader.readLine();
                System.out.println(line);
                while (line != null){
                    System.out.println(line);
                    line = reader.readLine();
                }
                int exitCode = process.waitFor();

                //System.out.println(exitCode);
            }else{
                System.out.println("El fichero no existe");
            }

        }catch (IOException e){
            e.printStackTrace();
        }catch (InterruptedException ex){
            ex.printStackTrace();
        }

    }
}
