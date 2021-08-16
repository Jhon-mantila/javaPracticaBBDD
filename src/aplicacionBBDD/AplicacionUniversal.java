package aplicacionBBDD;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;
import java.util.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class AplicacionUniversal {

	public static void main(String[] args) {
		// TODO Apéndice de método generado automáticamente
		
		MarcoBBDD mimarco = new MarcoBBDD();
		
		mimarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		mimarco.setVisible(true);

	}

}

class MarcoBBDD extends JFrame{
	
	public MarcoBBDD() {
		
		setBounds (300,300,700,700);
		
		LaminaBBDD milamina = new LaminaBBDD();
		
		add(milamina);
		
	}
	
}

class LaminaBBDD extends JPanel{
	
	private JComboBox micombo;
	
	private JTextArea areaInfo;
	
	private JTextField input;
	
	private Connection miConexion = null;
	
	private FileReader entrada;
	
	
	public LaminaBBDD() {
		
		setLayout(new BorderLayout());
		
		micombo = new JComboBox();
		
		areaInfo = new JTextArea();
		
		
		ConectarBBDD();
		
		ObtenerTablas();
		
		micombo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Apéndice de método generado automáticamente
				String nombreTabla = (String)micombo.getSelectedItem();
				
				mostrarInfoTabla(nombreTabla);
			}
			
			
			
		});
		
		
		add(micombo, BorderLayout.NORTH);
		
	
		
		add(areaInfo, BorderLayout.CENTER);
		
		
	}
	
	
	public void ConectarBBDD() {
		
		String[] datos = new String[3];
		
		try {	
			
			
			entrada = new FileReader("C:/Users/Casa/Desktop/AppConfig.txt");
		
		}catch(IOException e) {
			
			JOptionPane.showMessageDialog(this,"Busca el archivo de Configuración .txt");		

		    JFileChooser chooser = new JFileChooser();
		    
		    FileNameExtensionFilter filter = new FileNameExtensionFilter(
		        "Archivos .txt", "txt");
		    
		    chooser.setFileFilter(filter);
		    
		    int returnVal = chooser.showOpenDialog(this);
		    
		    if(returnVal == JFileChooser.APPROVE_OPTION) {
		    	
		    	try {
					entrada = new FileReader(chooser.getSelectedFile().getAbsolutePath());
				} catch (FileNotFoundException e1) {
					// TODO Bloque catch generado automáticamente
					e1.printStackTrace();
				}
		  		       
		    }
			
			
		}
			
		try {	
			BufferedReader miBufer = new BufferedReader(entrada);
			
			for (int i=0; i<=2; i++ ) {
				
				datos[i] = miBufer.readLine();
			}
			
			miConexion = DriverManager.getConnection(datos[0], datos[1], datos[2]);
			
			entrada.close();
		}catch(Exception e) {
			
			e.printStackTrace();
		}
			
		}//LLave del método 
	


	
	public void ObtenerTablas() {
		
		ResultSet miResultSet = null;
		
		try {
			
			DatabaseMetaData datosDDBB = miConexion.getMetaData();
			
			miResultSet = datosDDBB.getTables(null, null, null, null);
			
			while (miResultSet.next()) {
				
				micombo.addItem(miResultSet.getString("TABLE_NAME"));
			}
			
		}catch(Exception e) {
			
			e.printStackTrace();
		}
			
	}
	
	public void mostrarInfoTabla(String nombreTabla) {
		
		ArrayList<String> campos = new ArrayList<String>();
		
		
		
		String consulta = "SELECT *  FROM " + nombreTabla;
		
		try {
			
			areaInfo.setText(" ");
			
			Statement miStatement = miConexion.createStatement();
			
			ResultSet miResultSet = miStatement.executeQuery(consulta);
			
			//Almacenadar lo datos metadatos del resulset persime consultas cuantas columnas tiene la tabla y los nombres de las columanas
			ResultSetMetaData rsBBDD = miResultSet.getMetaData();
			
			//Cuantas columnas hay en la tabla
			for (int i=1; i<=rsBBDD.getColumnCount(); i++) {
				
				//indica el nombre de las columnas y la i indica la posicion para el nombre 
				campos.add(rsBBDD.getColumnLabel(i));
				
				
				
			}
			
			while (miResultSet.next()) {
				
				for(String nom_campo:campos) {
					
					areaInfo.append(miResultSet.getString(nom_campo) + " " );
					
					
					
				}
				
				areaInfo.append("\n");
			}
			
			
		}catch(Exception e) {
			
			e.printStackTrace();
		}
		
	}
}
