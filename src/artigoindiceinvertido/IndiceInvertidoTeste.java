package artigoindiceinvertido;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

public class IndiceInvertidoTeste {

    public static void main(String[] args) throws Exception {

        HashMap<String, ArrayList<Integer>> dicionario = (HashMap<String, ArrayList<Integer>>) get("dicionario.obj");
        if (dicionario == null) {
            dicionario = new HashMap<>(5000);
        }

        ArrayList documentos = (ArrayList) get("documentos.obj");;
        if (documentos == null) {
            documentos = new ArrayList(10000);
        }

        File pasta = new File("arquivos");
        File[] listFiles = pasta.listFiles();
        Arrays.sort(listFiles);
        //Indexar Arquivos
        long time = new Date().getTime();
        int i = 0;
        for (File arquivo : listFiles) {
            int idx = -1;
            //se Ja li esse pulo
            if (documentos.contains(arquivo.getName())) {
                continue;
            } else {
                documentos.add(arquivo.getName());
                idx = documentos.indexOf(arquivo.getName());
            }

            Scanner input = new Scanner(arquivo);
            while (input.hasNext()) {
                String palavra = input.next();

                ArrayList<Integer> indice = dicionario.get(palavra);
                if (indice == null) {
                    indice = new ArrayList<>(10);
                    dicionario.put(palavra, indice);
                }
                if (!indice.contains(idx)) {
                    indice.add(idx);
                }
            }
            input.close();
            System.out.println("idx: " + arquivo.getName());
            if (i > 1000) {
                //persiste          
//                save(documentos, "documentos.obj");
//                save(dicionario, "dicionario.obj");
                System.gc();
                i = 0;
            } else {
                i++;            
            }

        }
        System.out.println("Time Indexar: " + ((new Date().getTime() - time)) + "ms");

        String[] palavras = new String[]{
            "abacate", "abacaxi", "amora", "banana", "maça",
            "morango", "pera", "salada", "mista", "geleia",};
        int t = 10;
        ArrayList<Long> tempos = new ArrayList(t);
        for (int j = 0; j < t; j++) {
            time = new Date().getTime();
            //Busca Palavras
            ArrayList<Integer> indices = dicionario.get(palavras[j]);
//            if (indices != null) {
//                indices.forEach((indice) -> {
//                    System.out.println(indice);
//                });
//            }
            tempos.add(new Date().getTime() - time);
        }
        long totalTempo = 0;
        for (int j = 0; j < tempos.size(); j++) {
            System.out.println("Tempo " + j + " = " + tempos.get(j));
            totalTempo += tempos.get(j);
        }
        System.out.println("Total = " + totalTempo);

    }

    private static void save(Object obj, String name) throws Exception {
        FileOutputStream fout = new FileOutputStream(name, false);
        ObjectOutputStream oos = new ObjectOutputStream(fout);
        oos.writeObject(obj);

        oos.close();
        fout.close();
    }

    private static Object get(String name) {
        FileInputStream fin = null;
        try {
            fin = new FileInputStream(name);
            ObjectInputStream ois = new ObjectInputStream(fin);
            Object obj = ois.readObject();
            ois.close();
            fin.close();
            return obj;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        } finally {
            try {
                fin.close();
            } catch (Exception ex) {
                ex.printStackTrace();;
                return null;
            }
        }
    }

}
