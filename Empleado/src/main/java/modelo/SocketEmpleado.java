package modelo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SocketEmpleado implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 6196215119466082230L;
	int puerto = 5555;
    String ip = "localhost"; // Cambia esto a la dirección IP del servidor
	private Socket socket;
	private ObjectOutputStream oos;
	private BufferedReader in;
	private PrintWriter out;
	//private ObjectInputStream ois;

    public String envio(Object objeto,String mensaje){
    	String resp="";
    	try{
        	System.out.println(objeto);
        	this.abrirConexion(ip, puerto);
        	System.out.println("Conexion establecida");
            System.out.println("Enviando datos");
            resp=enviarDatos(objeto,mensaje);
        }catch(Exception e){
            
        }
    	cerrarConexion();
    	System.out.println("ESTO:"+resp);
        return resp;
    }
    
   public void llama(Object objeto,String mensaje){
	   try{
		   this.abrirConexion(ip, puerto);
		   //System.out.println("objeto EMPLEADO: " + objeto);
		   oos.writeObject(objeto);
		   oos.flush();
		   out.println(mensaje);
	   }catch(Exception e){
		   
       }finally{
       	cerrarConexion();
       }
   }

    
    private void abrirConexion(String ip,int puerto) throws IOException{
        this.socket=new Socket(ip,puerto);
        this.oos=new ObjectOutputStream(socket.getOutputStream());
        this.in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out=new PrintWriter(socket.getOutputStream(),true);
        //this.ois=new ObjectInputStream(socket.getInputStream());  
    }
    
    private void cerrarConexion(){
        try {
            socket.close();
            oos.close();
            in.close();
            out.close();
            //ois.close();
        } catch (IOException ex) {
            Logger.getLogger(Empleado.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private String enviarDatos(Object objeto,String mensaje) throws IOException{
        oos.writeObject(objeto);
        oos.flush();
        
        out.println(mensaje);
        System.out.println("datos enviados: objeto:"+objeto+"Mensaje:"+mensaje);
        
        return in.readLine();  
    }  
}
