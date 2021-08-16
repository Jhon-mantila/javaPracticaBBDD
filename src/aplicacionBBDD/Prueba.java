package aplicacionBBDD;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Prueba {

	public static void main(String[] args) {
		// TODO Apéndice de método generado automáticamente
		
		
		Marco mimarco = new Marco();
		
		mimarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		

	    JFileChooser chooser = new JFileChooser();
	    
	    FileNameExtensionFilter filter = new FileNameExtensionFilter(
	        "Archivos .txt", "txt");
	    
	    chooser.setFileFilter(filter);
	    
	    int returnVal = chooser.showOpenDialog(mimarco);
	    
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	    	
	       System.out.println("You chose to open this file: " +
	            chooser.getSelectedFile().getAbsolutePath());
	       
	    }
	}
	


}

class Marco extends JFrame{
	
	public Marco() {
		
		setBounds(300,300,300,300);
		
		setVisible(true);
		
	}
}