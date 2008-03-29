package ultigpx;

import junit.framework.*;
import junit.extensions.*;

/**
 * Test Class for GPXImporter for Project of CPSC 301 <br>
 * Tests GPXImporter class
 * @author Jill Ainsworth
 */
public class GPXImporterTester extends TestCase {
    public GPXImporterTester() {
        super();
    } //end constructor
    
    /**
     * test1 test the importer when the file given does not exist
     * No input, no output, throws no exceptions.
     */
    public void testImport1() {
        boolean ExceptionHasBeenThrown = false;
        
        try {
            GPXImporter importer = new GPXImporter();
            UGPXFile file = importer.importGPX("panda.txt");
        } //end try
        
        catch (Exception e) {
            ExceptionHasBeenThrown = true;
        } //end catch
        assertEquals(ExceptionHasBeenThrown, true);
    } //end testImport1
    
    /**
     * test2 test the importer when the input is null
     * No input, no output, throws no exceptions.
     */
    public void testImport2() {
        boolean ExceptionHasBeenThrown = false;
        
        try {
            GPXImporter importer = new GPXImporter();
            UGPXFile file = importer.importGPX(null);
        } //end try
        
        catch (Exception e) {
            ExceptionHasBeenThrown = true;
            
        } //end catch
        assertEquals(ExceptionHasBeenThrown, true);
    } //end testImport2
    
    /**
     * test3 test the importer when the file given is junk
     * No input, no output, throws no exceptions.
     */
    public void testImport3() {
        boolean ExceptionHasBeenThrown = false;
        
        try {
            GPXImporter importer = new GPXImporter();
            UGPXFile file = importer.importGPX("junk.gpx");
        } //end try
        
        catch (Exception e) {
            ExceptionHasBeenThrown = true;
            
        } //end catch
        assertEquals(ExceptionHasBeenThrown, true);
    } //end testImport1
    
    /**
     * test4 test the importer when the file contains nothing
     * No input, no output, throws no exceptions.
     */
    public void testImport4() {
        boolean ExceptionHasBeenThrown = false;
        
        try {
            GPXImporter importer = new GPXImporter();
            UGPXFile file = importer.importGPX("testFile1.gpx");
        } //end try
        
        catch (Exception e) {
            ExceptionHasBeenThrown = true;
        } //end catch
        assertEquals(ExceptionHasBeenThrown, false);
    } //end testImport4
    
    /**
     * test5 test the importer when the file contains a few waypoints
     * No input, no output, throws no exceptions.
     */
    public void testImport5() {
        
        try {
            //Import file
            GPXImporter importer = new GPXImporter();
            UGPXFile file = importer.importGPX("testFile2.gpx");
            
            //First waypoint
            assertEquals(file.getWaypoint(0).lat, 42.891719257);
            assertEquals(file.getWaypoint(0).lon, -0.114148362);
            //assertEquals(file.getWaypoint(1).time, -1, 0.001);
            
            //Second waypoint
            assertEquals(file.getWaypoint(1).lat, 42.791342866);
            assertEquals(file.getWaypoint(1).lon, -0.144031858);
            assertEquals(file.getWaypoint(1).ele, 2144.810791);
            assertEquals(file.getWaypoint(1).getName().equals("GEAR"), true);
            assertEquals(file.getWaypoint(1).getDesc().equals("Pretty Waypoint"), true);
            //assertEquals(0, file.getWaypoint(1).time, 1);
            
            //Third waypoint
            assertEquals(file.getWaypoint(2).lat, 50.852423497);
            assertEquals(file.getWaypoint(2).lon, 4.355069669);
            assertEquals(file.getWaypoint(2).ele, 13.584351);
            assertEquals(file.getWaypoint(2).getName().equals("H01"), true);
            assertEquals(file.getWaypoint(2).getDesc().equals("There is a a tree here"), true);
            //assertEquals(file.getWaypoint(2).time, 0);
        } //end try
        
        catch (Exception e) {
            System.out.println(e);
        } //end catch
    } //end testImport5
    
    /**
     * test1 test the importer when the file given contains a few routes
     * No input, no output, throws no exceptions.
     */
    public void testImport6() {
        
        try {
            //Import file
            GPXImporter importer = new GPXImporter();
            UGPXFile file = importer.importGPX("testFile3.gpx");
            
            //First route
            assertEquals(file.getRoute(0).size(), 0);
            
            //Second route
            assertEquals(file.getRoute(1).size(), 1);
            assertEquals(file.getRoute(1).get(0).lat, 42.891719257);
            assertEquals(file.getRoute(1).get(0).lon, -0.114148362);
            assertEquals(file.getRoute(1).name.equals("06-SEP-05"), true);
            assertEquals(file.getRoute(1).desc.equals("Route for running"), true);
            
            //Third route
            assertEquals(file.getRoute(2).name.equals("06-SEP-05"), true);
            assertEquals(file.getRoute(2).desc.equals("Route for running"), true);
            assertEquals(file.getRoute(2).get(0).lat, 42.891719257);
            assertEquals(file.getRoute(2).get(0).lon, -0.114148362);
            //assertEquals(file.getRoute(2).getWaypoint(1).time, -1, 0.001);
            
            //Second waypoint
            assertEquals(file.getRoute(2).get(1).lat, 42.791342866);
            assertEquals(file.getRoute(2).get(1).lon, -0.144031858);
            assertEquals(file.getRoute(2).get(1).ele, 2144.810791);
            assertEquals(file.getRoute(2).get(1).getName().equals("GEAR"), true);
            assertEquals(file.getRoute(2).get(1).getDesc().equals("Pretty Waypoint"), true);
            //assertEquals(0, file.getRoute(2).get(1).time, 1);
            
            //Third waypoint
            assertEquals(file.getRoute(2).get(2).lat, 50.852423497);
            assertEquals(file.getRoute(2).get(2).lon, 4.355069669);
            assertEquals(file.getRoute(2).get(2).ele, 13.584351);
            assertEquals(file.getRoute(2).get(2).getName().equals("H01"), true);
            assertEquals(file.getRoute(2).get(2).getDesc().equals("There is a a tree here"), true);
            //assertEquals(file.getRoute(2).get(2).time, 0);
        } //end try
        
        catch (Exception e) {
            System.out.println(e);
        } //end catch
    } //end testImport6
   
    
    /**
     * test1 test the importer when the file contains a few tracks
     * No input, no output, throws no exceptions.
     */
    public void testImport7() {
        
        try {
            //Import file
            GPXImporter importer = new GPXImporter();
            UGPXFile file = importer.importGPX("testFile4.gpx");
            
            //First route
            assertEquals(file.getTrack(0).size(), 0);
            
            //Second route
            assertEquals(file.getTrack(1).size(), 1);
            assertEquals(file.getTrack(1).get(0).get(0).lat, 42.891719257);
            assertEquals(file.getTrack(1).get(0).get(0).lon, -0.114148362);
            
            //Third route
            assertEquals(file.getTrack(2).name.equals("06-SEP-05"), true);
            assertEquals(file.getTrack(2).get(0).get(0).lat, 42.891719257);
            assertEquals(file.getTrack(2).get(0).get(0).lon, -0.114148362);
            
            //Second waypoint
            assertEquals(file.getTrack(2).get(0).get(1).lat, 42.791342866);
            assertEquals(file.getTrack(2).get(0).get(1).lon, -0.144031858);
            assertEquals(file.getTrack(2).get(0).get(1).ele, 2144.810791);
            assertEquals(file.getTrack(2).get(0).get(1).getName().equals("GEAR"), true);
            
            //Third waypoint
            assertEquals(file.getTrack(2).get(0).get(2).lat, 50.852423497);
            assertEquals(file.getTrack(2).get(0).get(2).lon, 4.355069669);
            assertEquals(file.getTrack(2).get(0).get(2).ele, 13.584351);
            assertEquals(file.getTrack(2).get(0).get(2).getName().equals("H01"), true);
            assertEquals(file.getTrack(2).get(0).get(2).getDesc().equals("There is a a tree here"), true);
        } //end try
        
        catch (Exception e) {
            System.out.println(e);
        } //end catch
    } //end testImport7
    
} //end SimpleRecurrenceTester
