package artigoindiceinvertido;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;


/**
 *
 * @author manchini
 */
public class GeradorDocumentos {
    
    
    public static void main(String[] args) throws Exception{
        
          BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("wordlist.txt"))));
        
        ArrayList<String> palavras = new ArrayList<>();
        
        while(br.ready()){
            palavras.add(br.readLine().trim());
        }
        int n = palavras.size();
        
        int maxsize = 524288;
        int maxArq = 10000;
        Random random = new Random();
        for (int i = 0; i < maxArq; i++) {
            File arquivo = new File("arquivos/"+i+".txt");          
            FileWriter fw = new FileWriter(arquivo);
             BufferedWriter bw = new BufferedWriter(fw);
            while(arquivo.length()<maxsize){
                bw.append(palavras.get(random.nextInt(n))).append(" ");
            }
            
            bw.close();
            fw.close();
            
        }
        
        
        
        
        
    }
    
    

    
}
