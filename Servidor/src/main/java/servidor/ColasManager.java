package servidor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import modelo.EmpleadoTrigger;
import modelo.Estadisticas;
import modelo.Notificador;
import modelo.Registro;

public class ColasManager implements Registro,EmpleadoTrigger,Notificador,Estadisticas{

	
    private ArrayList<String> boxes=new ArrayList<String>();
    public ArrayList<String> dnis=new ArrayList<String>();
    private ArrayList<String> atendidos=new ArrayList<String>();
    private ArrayList<Integer> tiempoInicio=new ArrayList<Integer>();
    private ArrayList<Integer> tiempoFin=new ArrayList<Integer>();
    private static int indexBox=0;
    private static int indexDnis=0;
    private ArrayList<DatosConexion> teles=new ArrayList<DatosConexion>();
    private long tiempo = System.currentTimeMillis();
    
    public ColasManager() {
    }
    
    public ArrayList<String> obtener_boxes() {
    	return this.boxes;
    }
    
    public void newBox(DatosConexion datos) {
    	PrintWriter out;
		try {
			System.out.println("las cosas bien"+indexBox);
			out = new PrintWriter(datos.getSocket().getOutputStream(), true);
			out.println(String.valueOf(indexBox));
	    	this.nuevoEmpleado(String.valueOf(indexBox));
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public void newCliente(String Dni) {
    	dnis.add(Dni);
    	this.tiempoInicio.add((int) (System.currentTimeMillis()/1000));
    }
    
    public void llamaCliente(String box) {
    	//manda a el tele el box en cuestion y el dni
    	PrintWriter out;
    	if(dnis.size()>=1) {
    		//aca manda con el primer dni al box en cuestion
    		for(int i=0;i<teles.size();i++) {
    			try {
					out = new PrintWriter(teles.get(i).getSocket().getOutputStream(), true);
					out.println(box);
					out.println(dnis.get(0));
				} catch (IOException e) {
					e.printStackTrace();
				}
    		}		
    		atendidos.add(indexDnis, dnis.get(0));
    		this.tiempoFin.add(indexDnis, (int) (System.currentTimeMillis()/1000));
    		indexDnis++;
    		dnis.remove(0);
    	}
    	else {
    		System.out.println("No hay clientes");
    	}
    	
    	
    }
    
    protected String calculaTiempo() {
    	int segundos = (int) ((System.currentTimeMillis() - this.tiempo)/1000);
    	int minutos = segundos/60;
    	int horas = minutos/60;
    	String tiempoActual;
    	if (segundos > 60) {
			segundos = segundos%60;
		}if (minutos > 60) {
			minutos = minutos%60;
		}
		if (minutos < 10 && segundos < 10) {
    		tiempoActual = horas + ":0" + minutos + ":0" + segundos;
		} else if (minutos < 10) {
			tiempoActual = horas + ":0" + minutos + ":" + segundos;
		} else if (segundos < 10) {
    		tiempoActual = horas + ":" + minutos + ":0" + segundos;
    	}else {
    		tiempoActual = horas + ":" + minutos + ":" + segundos;
		}
    	return tiempoActual;
	}
	
    protected String calculaTiempoPromedio(int personas) {
    	String tiempoActual;
    	if (personas == 0) {
			tiempoActual = "0:00:00";
		}
    	else{
    		ArrayList<Integer> tiempoAux = new ArrayList<Integer>();
	    	long suma = 0;
	    	for (int i = 0; i < personas; i++) {
				tiempoAux.add(i, this.tiempoFin.get(i)-this.tiempoInicio.get(i));
				suma+=tiempoAux.get(i);
			}
	    	long promedio = suma/personas;
	    	int segundos = (int) (promedio);
	    	int minutos = segundos/60;
	    	int horas = minutos/60;
    		if (segundos > 60) {
    			segundos = segundos%60;
    		}if (minutos > 60) {
    			minutos = minutos%60;
    		}
    		if (minutos < 10 && segundos < 10) {
        		tiempoActual = horas + ":0" + minutos + ":0" + segundos;
    		} else if (minutos < 10) {
    			tiempoActual = horas + ":0" + minutos + ":" + segundos;
    		} else if (segundos < 10) {
        		tiempoActual = horas + ":" + minutos + ":0" + segundos;
        	}else {
        		tiempoActual = horas + ":" + minutos + ":" + segundos;
    		}
        	return tiempoActual;
    	}
		return tiempoActual;
	}
    
    public void creaTele(DatosConexion datos) {
    	teles.add(datos);
    }

	public ArrayList<String> getDnis() {
		return dnis;
	}

	public void setDnis(ArrayList<String> dnis) {
		this.dnis = dnis;
	}

	public ArrayList<String> getAtendidos() {
		return atendidos;
	}

	public void setAtendidos(ArrayList<String> atendidos) {
		this.atendidos = atendidos;
	}

	@Override
	public void nuevoEmpleado(String mensaje) {
		boxes.add(indexBox,mensaje);
    	indexBox++;
	}

	@Override
	public void CreaTelevisor() {

	}

	
	public void nuevoAdministrador(DatosConexion datos) {
		datos.out.println(this.getAtendidos().size());
    	datos.out.println(this.calculaTiempo());
    	datos.out.println(this.calculaTiempoPromedio(this.getAtendidos().size()));
    	datos.out.flush();
	}

	@Override
	public void nuevoAdministrador() {

	}
	
	public int obtener_index_dnis() {
    	return indexBox;
    }
    public int obtener_index_box() {
    	return indexBox;
    }
    public ArrayList<String> obtener_dnis(){
    	return this.dnis;
    }
    public ArrayList<String> obtener_atendidos() {
    	return this.atendidos;
    }
    public ArrayList<DatosConexion> obtener_teles(){
    	return this.teles;
    }
    public void agregarBox(String box) {
    	this.boxes.add(box);
    }
    public void agregarIndexBox(int indexBox) {
    	this.indexBox = indexBox;
    }
    public void agregarDnis(ArrayList<String> dnis_nuevo) {
    	//System.out.println("entro colas");
    	this.dnis = dnis_nuevo;
    }
    public void agregarBoxes(ArrayList<String> boxes) {
    	this.boxes = boxes;
    }
    public void agregarAtendidos(ArrayList<String> atendidos) {
    	this.atendidos = atendidos;
    }
    public void agregarTeles(ArrayList<DatosConexion> teles2) {
    	//System.out.println("entro teles");
    	this.teles = teles2;
    }
}
