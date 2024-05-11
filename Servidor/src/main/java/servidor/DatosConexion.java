/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servidor;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.Socket;

/**
 *
 * @author ignacio
 */
public class DatosConexion implements Serializable {
 
    private static final long serialVersionUID = 1L;
	//public ObjectOutputStream oos;
	public BufferedReader in;
    public PrintWriter out;
    public ObjectInputStream ois;
    public Socket socket;
    public DataInputStream dis;
    
    public DatosConexion(Socket s){
        this.socket=s;
        try{
        	this.ois = new ObjectInputStream(socket.getInputStream());
            //this.oos = new ObjectOutputStream(socket.getOutputStream());
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.dis = new DataInputStream(socket.getInputStream());
            this.out = new PrintWriter(socket.getOutputStream(),true);
        	/*
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.ois = new ObjectInputStream(socket.getInputStream());  
            this.out = new PrintWriter(socket.getOutputStream(),true);
            //this.oos = new ObjectOutputStream(socket.getOutputStream());*/
        }catch(IOException e){
            e.printStackTrace();
        }
        
    }
    
    public Socket getSocket() {
    	return this.socket;
    }
}
