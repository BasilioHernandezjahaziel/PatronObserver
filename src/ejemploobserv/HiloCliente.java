/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejemploobserv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author david
 */
public class HiloCliente extends Observable implements Runnable {
    
    private boolean isStoped = false;
    private Socket cliente=null;
    private BufferedReader recptor=null;
    private PrintWriter emisor=null;
    
    public HiloCliente(Socket cliente){
        try {
            this.cliente=cliente;
            InputStreamReader isr=new InputStreamReader(cliente.getInputStream());
            recptor = new BufferedReader(isr);
            emisor = new PrintWriter(cliente.getOutputStream());
        } catch (IOException ex ){
         Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public synchronized void stoped(){
        isStoped=true;
    }
    
    public void escribir(String smg){
        emisor.write(smg);
        emisor.write("\n");
        emisor.flush();
    }    
    @Override
    public void run(){
        while(!isStoped){
            try {
                setChanged();
//                emisor.write("Hola mundo\n");
//                emisor.flush();
                notifyObservers("Valor");
                Thread.sleep(2000);
            } catch (InterruptedException ex){
                Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
