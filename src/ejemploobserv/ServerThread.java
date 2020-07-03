/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejemploobserv;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author david
 */
public class ServerThread extends Thread{
    private static List<HiloCliente> listClientes = new ArrayList<HiloCliente>();
    private boolean isStoped=false;
    private ServerSocket server=null;
    private Observer observer;
    
    public ServerThread(Observer observer) throws IOException {
        this.observer=observer;
        server=new ServerSocket(7700);      
    }

    public static List<HiloCliente> getListClientes() {
        return listClientes;
    }
    
    public synchronized void stoped(){
        isStoped=true;
    }
    @Override
    public void run(){
        Socket cliente=null;
        while(!isStoped){
            try {
                cliente = server.accept();
                HiloCliente hc= new HiloCliente(cliente);
                listClientes.add(hc);
                hc.addObserver(observer);
                Thread hilo=new Thread(hc);
                hilo.start();
                //hc.start();
                
            } catch (IOException ex) {
                stoped();
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
                
            }
        }
        super.run();
    }
}
