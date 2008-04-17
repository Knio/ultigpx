package ultigpx;
/*
 * need to make 2 changes to get it working
 *  1) make fill method return type as boolean in WayptView.java
 *  2) make return type of importGPX as Database...and "return file" at the end of method;
 *  make both the methods as public
 */
import java.io.IOException;

import org.jdom.JDOMException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * @author Bindu Varre
 *
 */
public class WayptViewTest extends TestCase {
	WayptView v = null;
	UltiGPX ugpx = null;

	public WayptViewTest(){
	log(" created");
	}

	public WayptViewTest(String str){
	super(str);
	log(str + " created");
	}
	

	/* optional setUp method for intialization*/
	public void setUp(){
	        try
	        {
	            Installer.install();
	        }
	        catch (IOException e)
	        {
	            System.out.println("Failed to install UltiGPX");
	            System.out.println(e);
	        }
	        
	ugpx = new UltiGPX();
	v= new WayptView(ugpx ); 

	}
	/* optional tearDown method to release resources*/
	public void tearDown(){
	}

	public static Test suite() {

		Test tsuite = new TestSuite(WayptViewTest.class);

		return tsuite;
	}
	
	
	public void test_importGPX() {
		Database file = null;
		try {
			file = ugpx.importGPX("C:/Documents and Settings/Bindu Varre/My Documents/301/example1.gpx");
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.assertNotNull(file);
	}	

	public void test_fill() {
		boolean result = v.fill();
		assertTrue(result == true);
	}
	
	
	

	public static void main (String[] args) {
		junit.textui.TestRunner.run(suite());
	}


	public void log(String s){
		System.out.println(s);
	}

}



	

	



