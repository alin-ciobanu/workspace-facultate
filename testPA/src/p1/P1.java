package p1;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Arrays;


/**
 * @author Alin Rautoiu 322CC
 */
public class P1 {


	public static void main(String[] args) 
            throws FileNotFoundException, IOException {
 
        Integer n; // numarul de stalpi din care este format gardul
        Integer m; // numarul de stalpi care pot fi scosi
        Integer l; // lungimea gardului
        
        FileReader read = new FileReader(new File("date.in"));
        Scanner scan = new Scanner(read);
        
        n = scan.nextInt();
        m = scan.nextInt();
        l = scan.nextInt();
        
        Integer gard[] = new Integer[n];
        Integer gardStricat[] = new Integer[n]; // vector in care construim gardul final
        Integer lungimeFinala = n - m;      // numar de scanduri in gard stricat
        
        Integer k = 0;                      // variabila care contorizeaza numarul de  
                                        // scanduri ale gardului stricat
        
        for (int i = 0; i < n; i++) {
            gard[i] = scan.nextInt();
        }
        
        Integer lower = 0;
        Integer upper = l;
        Integer i = lower + (upper-lower) / 2;
        
        //Cautam folosind cautarea binara lungimea minima maximala care se poate
        //obtine scotand numarul de stalpi propusi
        
        while(true){            
            gardStricat = new Integer[n];
            gardStricat[0] = 0;
            k = 1;
            
            for(int j = 1 ; j < n; j++){
                if(gard[j] - gardStricat[k-1] >= i){
                    gardStricat[k++] = gard[j];
                }
            }

            //System.out.println(Arrays.toString(gardStricat));
            //System.out.println("i:" + i);
            if(upper - lower == 1){
                                
                FileWriter write = new FileWriter(new File("date.out"));
                BufferedWriter writer = new BufferedWriter(write);
                writer.write(String.valueOf(i) + "\n" + String.valueOf(k) + "\n");
                for(int p = 0; p < k; p++){
                    writer.write(String.valueOf(gardStricat[p]) + "\n");
                }
                
                //write.close();
                writer.close();
                break;
            }
            
            if(k > lungimeFinala){
                lower = i;
                i = lower + (upper - lower) / 2;
            }
            else {
                upper = i;
                i = lower + (upper - lower) / 2;
            }
        }
        scan.close();
        read.close();
    }
}