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
     * testGPXGroup1 tests the importer when the file given does not exist
     * No input, no output, throws no exceptions.
     */
    public void testGPXGroup1() {
        boolean ExceptionHasBeenThrown = false;
        
        try {
            GPXImporter importer = new GPXImporter();
            Group newGroup = new Group();
            importer.importGPX(newGroup, "panda.txt");
        } //end try
        
        catch (Exception e) {
            ExceptionHasBeenThrown = true;
        } //end catch
        assertEquals(ExceptionHasBeenThrown, true);
    } //end testGPXGroup1
    
    /**
     * test2 test the importer when the input is null
     * No input, no output, throws no exceptions.
     */
    public void testGPXGroup2() {
        boolean ExceptionHasBeenThrown = false;
        
        try {
            GPXImporter importer = new GPXImporter();
            Group newGroup = new Group();
            importer.importGPX(newGroup, null);
        } //end try
        
        catch (Exception e) {
            ExceptionHasBeenThrown = true;
            
        } //end catch
        assertEquals(ExceptionHasBeenThrown, true);
    } //end testGPXGroup2
    
    /**
     * test2 test the importer when the input is null
     * No input, no output, throws no exceptions.
     */
    public void testGPXGroup3() {
        boolean ExceptionHasBeenThrown = false;
        
        try {
            GPXImporter importer = new GPXImporter();
            importer.importGPX((Group) null, null);
        } //end try
        
        catch (Exception e) {
            ExceptionHasBeenThrown = true;
            
        } //end catch
        assertEquals(ExceptionHasBeenThrown, true);
    } //end testGPXGroup3
    
    /**
     * test3 test the importer when the file given is junk
     * No input, no output, throws no exceptions.
     */
    public void testGPXGroup4() {
        boolean ExceptionHasBeenThrown = false;
        
        try {
            GPXImporter importer = new GPXImporter();
            importer.importGPX((Group) null, "junk.gpx");
        } //end try
        
        catch (Exception e) {
            ExceptionHasBeenThrown = true;
            
        } //end catch
        assertEquals(ExceptionHasBeenThrown, true);
    } //end testGPXGroup4
    
    /**
     * test4 test the importer when the file contains nothing
     * No input, no output, throws no exceptions.
     */
    public void testGPXGroup5() {
        boolean ExceptionHasBeenThrown = false;
        
        try {
            GPXImporter importer = new GPXImporter();
            Group newGroup = new Group();
            importer.importGPX(newGroup, "testFile1.gpx");
        } //end try
        
        catch (Exception e) {
            ExceptionHasBeenThrown = true;
        } //end catch
        assertEquals(ExceptionHasBeenThrown, false);
    } //end testGPXGroup5
    
    /**
     * test5 test the importer when the file contains a few waypoints
     * No input, no output, throws no exceptions.
     */
    public void testGPXGroup6() {
        boolean ExceptionHasBeenThrown = false;
        
        try {
            //Import file
            GPXImporter importer = new GPXImporter();
            Group file = new Group();
            importer.importGPX(file, "testFile2.gpx");
            
            //First waypoint
            assertEquals(file.getWaypoint(0).lat, 42.891719257);
            assertEquals(file.getWaypoint(0).lon, -0.114148362);
            assertEquals(file.getWaypoint(1).time, -1, 0.001);
            
            //Second waypoint
            assertEquals(file.getWaypoint(1).lat, 42.791342866);
            assertEquals(file.getWaypoint(1).lon, -0.144031858);
            assertEquals(file.getWaypoint(1).ele, 2144.810791);
            assertEquals(file.getWaypoint(1).getName().equals("GEAR"), true);
            assertEquals(file.getWaypoint(1).getDesc().equals("Pretty Waypoint"), true);
            assertEquals(0, file.getWaypoint(1).time, 1);
            
            //Third waypoint
            assertEquals(file.getWaypoint(2).lat, 50.852423497);
            assertEquals(file.getWaypoint(2).lon, 4.355069669);
            assertEquals(file.getWaypoint(2).ele, 13.584351);
            assertEquals(file.getWaypoint(2).getName().equals("H01"), true);
            assertEquals(file.getWaypoint(2).getDesc().equals("There is a a tree here"), true);
            assertEquals(file.getWaypoint(2).time, -1);
        } //end try
        
        catch (Exception e) {
            System.out.println(e);
        } //end catch
        assertEquals(ExceptionHasBeenThrown, false);
    } //end testGPXGroup6
    
    /**
     * test1 test the importer when the file given contains a few routes
     * No input, no output, throws no exceptions.
     */
    public void testGPXGroup7() {
        boolean ExceptionHasBeenThrown = false;
        
        try {
            //Import file
            GPXImporter importer = new GPXImporter();
            Group file = new Group();
            importer.importGPX(file, "testFile3.gpx");
            
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
            assertEquals(file.getRoute(2).get(0).time, -1);
            
            //Second waypoint
            assertEquals(file.getRoute(2).get(1).lat, 42.791342866);
            assertEquals(file.getRoute(2).get(1).lon, -0.144031858);
            assertEquals(file.getRoute(2).get(1).ele, 2144.810791);
            assertEquals(file.getRoute(2).get(1).getName().equals("GEAR"), true);
            assertEquals(file.getRoute(2).get(1).getDesc().equals("Pretty Waypoint"), true);
            assertEquals(0, file.getRoute(2).get(1).time, 1);
            
            //Third waypoint
            assertEquals(file.getRoute(2).get(2).lat, 50.852423497);
            assertEquals(file.getRoute(2).get(2).lon, 4.355069669);
            assertEquals(file.getRoute(2).get(2).ele, 13.584351);
            assertEquals(file.getRoute(2).get(2).getName().equals("H01"), true);
            assertEquals(file.getRoute(2).get(2).getDesc().equals("There is a a tree here"), true);
            assertEquals(file.getRoute(2).get(2).time, -1);
        } //end try
        
        catch (Exception e) {
            System.out.println(e);
        } //end catch
        assertEquals(ExceptionHasBeenThrown, false);
    } //end testGPXGroup7
    
    
    /**
     * test1 test the importer when the file contains a few tracks
     * No input, no output, throws no exceptions.
     */
    public void testGPXGroup8() {
        boolean ExceptionHasBeenThrown = false;
        
        try {
            //Import file
            GPXImporter importer = new GPXImporter();
            Group file = new Group();
            importer.importGPX(file, "testFile4.gpx");
            
            //First route
            assertEquals(file.getTrack(0).size(), 0);
            
            //Second route
            assertEquals(file.getTrack(1).size(), 1);
            assertEquals(file.getTrack(1).getArray().get(0).get(0).lat, 42.891719257);
            assertEquals(file.getTrack(1).getArray().get(0).get(0).lon, -0.114148362);
            
            //Third route
            assertEquals(file.getTrack(2).name.equals("06-SEP-05"), true);
            assertEquals(file.getTrack(2).getArray().get(0).get(0).lat, 42.891719257);
            assertEquals(file.getTrack(2).getArray().get(0).get(0).lon, -0.114148362);
            
            //Second waypoint
            assertEquals(file.getTrack(2).getArray().get(0).get(1).lat, 42.791342866);
            assertEquals(file.getTrack(2).getArray().get(0).get(1).lon, -0.144031858);
            assertEquals(file.getTrack(2).getArray().get(0).get(1).ele, 2144.810791);
            assertEquals(file.getTrack(2).getArray().get(0).get(1).getName().equals("GEAR"), true);
            
            //Third waypoint
            assertEquals(file.getTrack(2).getArray().get(0).get(2).lat, 50.852423497);
            assertEquals(file.getTrack(2).getArray().get(0).get(2).lon, 4.355069669);
            assertEquals(file.getTrack(2).getArray().get(0).get(2).ele, 13.584351);
            assertEquals(file.getTrack(2).getArray().get(0).get(2).getName().equals("H01"), true);
            assertEquals(file.getTrack(2).getArray().get(0).get(2).getDesc().equals("There is a a tree here"), true);
        } //end try
        
        catch (Exception e) {
            System.out.println(e);
        } //end catch
        assertEquals(ExceptionHasBeenThrown, false);
    } //end testGPXGroup8
    
    /**
     * testGPXGroup1 tests the importer when the file given does not exist
     * No input, no output, throws no exceptions.
     */
    public void testGPXDatabase1() {
        boolean ExceptionHasBeenThrown = false;
        
        try {
            GPXImporter importer = new GPXImporter();
            Database newGroup = new Database();
            importer.importGPX(newGroup, "panda.txt");
        } //end try
        
        catch (Exception e) {
            ExceptionHasBeenThrown = true;
        } //end catch
        assertEquals(ExceptionHasBeenThrown, true);
    } //end testGPXDatabase1
    
    /**
     * test2 test the importer when the input is null
     * No input, no output, throws no exceptions.
     */
    public void testGPXDatabase2() {
        boolean ExceptionHasBeenThrown = false;
        
        try {
            GPXImporter importer = new GPXImporter();
            Database newGroup = new Database();
            importer.importGPX(newGroup, null);
        } //end try
        
        catch (Exception e) {
            ExceptionHasBeenThrown = true;
            
        } //end catch
        assertEquals(ExceptionHasBeenThrown, true);
    } //end testGPXDatabase2
    
    /**
     * test2 test the importer when the input is null
     * No input, no output, throws no exceptions.
     */
    public void testGPXDatabase3() {
        boolean ExceptionHasBeenThrown = false;
        
        try {
            GPXImporter importer = new GPXImporter();
            importer.importGPX((Database) null, null);
        } //end try
        
        catch (Exception e) {
            ExceptionHasBeenThrown = true;
            
        } //end catch
        assertEquals(ExceptionHasBeenThrown, true);
    } //end testGPXDatabase3
    
    /**
     * test3 test the importer when the file given is junk
     * No input, no output, throws no exceptions.
     */
    public void testGPXDatabase4() {
        boolean ExceptionHasBeenThrown = false;
        
        try {
            GPXImporter importer = new GPXImporter();
            importer.importGPX((Database) null, "junk.gpx");
        } //end try
        
        catch (Exception e) {
            ExceptionHasBeenThrown = true;
            
        } //end catch
        assertEquals(ExceptionHasBeenThrown, true);
    } //end testGPXDatabase4
    
    /**
     * test4 test the importer when the file contains nothing
     * No input, no output, throws no exceptions.
     */
    public void testGPXDatabase5() {
        boolean ExceptionHasBeenThrown = false;
        
        try {
            GPXImporter importer = new GPXImporter();
            Database newGroup = new Database();
            importer.importGPX(newGroup, "testFile1.gpx");
        } //end try
        
        catch (Exception e) {
            ExceptionHasBeenThrown = true;
        } //end catch
        assertEquals(ExceptionHasBeenThrown, false);
    } //end testGPXDatabase5
    
    /**
     * test5 test the importer when the file contains a few waypoints
     * No input, no output, throws no exceptions.
     */
    public void testGPXDatabase6() {
        boolean ExceptionHasBeenThrown = false;
        
        try {
            //Import file
            GPXImporter importer = new GPXImporter();
            Database file = new Database();
            importer.importGPX(file, "testFile2.gpx");
            
            //First waypoint
            assertEquals(file.getWaypoint(0).lat, 42.891719257);
            assertEquals(file.getWaypoint(0).lon, -0.114148362);
            assertEquals(file.getWaypoint(1).time, -1, 0.001);
            
            //Second waypoint
            assertEquals(file.getWaypoint(1).lat, 42.791342866);
            assertEquals(file.getWaypoint(1).lon, -0.144031858);
            assertEquals(file.getWaypoint(1).ele, 2144.810791);
            assertEquals(file.getWaypoint(1).getName().equals("GEAR"), true);
            assertEquals(file.getWaypoint(1).getDesc().equals("Pretty Waypoint"), true);
            assertEquals(0, file.getWaypoint(1).time, 1);
            
            //Third waypoint
            assertEquals(file.getWaypoint(2).lat, 50.852423497);
            assertEquals(file.getWaypoint(2).lon, 4.355069669);
            assertEquals(file.getWaypoint(2).ele, 13.584351);
            assertEquals(file.getWaypoint(2).getName().equals("H01"), true);
            assertEquals(file.getWaypoint(2).getDesc().equals("There is a a tree here"), true);
            assertEquals(file.getWaypoint(2).time, -1);
        } //end try
        
        catch (Exception e) {
            System.out.println(e);
            ExceptionHasBeenThrown = true;
            
        } //end catch
        assertEquals(ExceptionHasBeenThrown, false);
    } //end testGPXDatabase6
    
    /**
     * test1 test the importer when the file given contains a few routes
     * No input, no output, throws no exceptions.
     */
    public void testGPXDatabase7() {
        boolean ExceptionHasBeenThrown = false;
        
        try {
            //Import file
            GPXImporter importer = new GPXImporter();
            Database file = new Database();
            importer.importGPX(file, "testFile3.gpx");
            
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
            assertEquals(file.getRoute(2).get(0).time, -1, 0.001);
            
            //Second waypoint
            assertEquals(file.getRoute(2).get(1).lat, 42.791342866);
            assertEquals(file.getRoute(2).get(1).lon, -0.144031858);
            assertEquals(file.getRoute(2).get(1).ele, 2144.810791);
            assertEquals(file.getRoute(2).get(1).getName().equals("GEAR"), true);
            assertEquals(file.getRoute(2).get(1).getDesc().equals("Pretty Waypoint"), true);
            assertEquals(0, file.getRoute(2).get(1).time, 1);
            
            //Third waypoint
            assertEquals(file.getRoute(2).get(2).lat, 50.852423497);
            assertEquals(file.getRoute(2).get(2).lon, 4.355069669);
            assertEquals(file.getRoute(2).get(2).ele, 13.584351);
            assertEquals(file.getRoute(2).get(2).getName().equals("H01"), true);
            assertEquals(file.getRoute(2).get(2).getDesc().equals("There is a a tree here"), true);
            assertEquals(file.getRoute(2).get(2).time, -1);
        } //end try
        
        catch (Exception e) {
            System.out.println(e);
            ExceptionHasBeenThrown = true;
        } //end catch
        assertEquals(ExceptionHasBeenThrown, false);
    } //end testGPXDatabase7
    
    
    /**
     * test1 test the importer when the file contains a few tracks
     * No input, no output, throws no exceptions.
     */
    public void testGPXDatabase8() {
        boolean ExceptionHasBeenThrown = false;
        
        try {
            //Import file
            GPXImporter importer = new GPXImporter();
            Database file = new Database();
            importer.importGPX(file, "testFile4.gpx");
            
            //First route
            assertEquals(file.getTrack(0).size(), 0);
            
            //Second route
            assertEquals(file.getTrack(1).size(), 1);
            assertEquals(file.getTrack(1).getArray().get(0).get(0).lat, 42.891719257);
            assertEquals(file.getTrack(1).getArray().get(0).get(0).lon, -0.114148362);
            
            //Third route
            assertEquals(file.getTrack(2).name.equals("06-SEP-05"), true);
            assertEquals(file.getTrack(2).getArray().get(0).get(0).lat, 42.891719257);
            assertEquals(file.getTrack(2).getArray().get(0).get(0).lon, -0.114148362);
            
            //Second waypoint
            assertEquals(file.getTrack(2).getArray().get(0).get(1).lat, 42.791342866);
            assertEquals(file.getTrack(2).getArray().get(0).get(1).lon, -0.144031858);
            assertEquals(file.getTrack(2).getArray().get(0).get(1).ele, 2144.810791);
            assertEquals(file.getTrack(2).getArray().get(0).get(1).getName().equals("GEAR"), true);
            
            //Third waypoint
            assertEquals(file.getTrack(2).getArray().get(0).get(2).lat, 50.852423497);
            assertEquals(file.getTrack(2).getArray().get(0).get(2).lon, 4.355069669);
            assertEquals(file.getTrack(2).getArray().get(0).get(2).ele, 13.584351);
            assertEquals(file.getTrack(2).getArray().get(0).get(2).getName().equals("H01"), true);
            assertEquals(file.getTrack(2).getArray().get(0).get(2).getDesc().equals("There is a a tree here"), true);
        } //end try
        
        catch (Exception e) {
            System.out.println(e);
            ExceptionHasBeenThrown = true;
        } //end catch
        assertEquals(ExceptionHasBeenThrown, false);
    } //end testGPXGroup8
    
    /**
     * testGPXGroup1 tests the importer when the file given does not exist
     * No input, no output, throws no exceptions.
     */
    public void testUltiGPXDatabase1() {
        boolean ExceptionHasBeenThrown = false;
        
        try {
            GPXImporter importer = new GPXImporter();
            Database newGroup = new Database();
            importer.importUltiGPX(newGroup, "panda.txt");
        } //end try
        
        catch (Exception e) {
            ExceptionHasBeenThrown = true;
        } //end catch
        assertEquals(ExceptionHasBeenThrown, true);
    } //end testUltiGPXDatabase1
    
    /**
     * test2 test the importer when the input is null
     * No input, no output, throws no exceptions.
     */
    public void testUltiGPXDatabase2() {
        boolean ExceptionHasBeenThrown = false;
        
        try {
            GPXImporter importer = new GPXImporter();
            Database newGroup = new Database();
            importer.importUltiGPX(newGroup, null);
        } //end try
        
        catch (Exception e) {
            ExceptionHasBeenThrown = true;
            
        } //end catch
        assertEquals(ExceptionHasBeenThrown, true);
    } //end testUltiGPXDatabase2
    
    /**
     * test2 test the importer when the input is null
     * No input, no output, throws no exceptions.
     */
    public void testTulitGPXDatabase3() {
        boolean ExceptionHasBeenThrown = false;
        
        try {
            GPXImporter importer = new GPXImporter();
            importer.importUltiGPX((Database) null, null);
        } //end try
        
        catch (Exception e) {
            ExceptionHasBeenThrown = true;
            
        } //end catch
        assertEquals(ExceptionHasBeenThrown, true);
    } //end testUltiGPXDatabase3
    
    /**
     * test3 test the importer when the file given is junk
     * No input, no output, throws no exceptions.
     */
    public void testUltiGPXDatabase4() {
        boolean ExceptionHasBeenThrown = false;
        
        try {
            GPXImporter importer = new GPXImporter();
            importer.importUltiGPX((Database) null, "junk.gpx");
        } //end try
        
        catch (Exception e) {
            ExceptionHasBeenThrown = true;
            
        } //end catch
        assertEquals(ExceptionHasBeenThrown, true);
    } //end testUltiGPXDatabase4
    
    /**
     * test4 test the importer when the file contains nothing
     * No input, no output, throws no exceptions.
     */
    public void testUltiGPXDatabase5() {
        boolean ExceptionHasBeenThrown = false;
        
        try {
            GPXImporter importer = new GPXImporter();
            Database newGroup = new Database();
            importer.importUltiGPX(newGroup, "testFile1.ultiGPX");
        } //end try
        
        catch (Exception e) {
            ExceptionHasBeenThrown = true;
        } //end catch
        assertEquals(ExceptionHasBeenThrown, false);
    } //end testUltiGPXDatabase5
    
    /**
     * test5 test the importer when the file contains a few waypoints
     * No input, no output, throws no exceptions.
     */
    public void testUltiGPXDatabase6() {
        boolean ExceptionHasBeenThrown = false;
        
        try {
            //Import file
            GPXImporter importer = new GPXImporter();
            Database file = new Database();
            importer.importUltiGPX(file, "testFile2.ultiGPX");
            
            //First waypoint
            assertEquals(file.getWaypoint(0).lat, 42.891719257);
            assertEquals(file.getWaypoint(0).lon, -0.114148362);
            assertEquals(file.getWaypoint(1).time, -1, 0.001);
            
            //Second waypoint
            assertEquals(file.getWaypoint(1).lat, 42.791342866);
            assertEquals(file.getWaypoint(1).lon, -0.144031858);
            assertEquals(file.getWaypoint(1).ele, 2144.810791);
            assertEquals(file.getWaypoint(1).getName().equals("GEAR"), true);
            assertEquals(file.getWaypoint(1).getDesc().equals("Pretty Waypoint"), true);
            assertEquals(0, file.getWaypoint(1).time, 1);
                        
            //Third waypoint
            assertEquals(file.getWaypoint(2).lat, 50.852423497);
            assertEquals(file.getWaypoint(2).lon, 4.355069669);
            assertEquals(file.getWaypoint(2).ele, 13.584351);
            assertEquals(file.getWaypoint(2).getName().equals("H01"), true);
            assertEquals(file.getWaypoint(2).getDesc().equals("There is a a tree here"), true);
            assertEquals(file.getWaypoint(2).time, -1);
        } //end try
        
        catch (Exception e) {
            System.out.println(e);
            ExceptionHasBeenThrown = true;
        } //end catch
        assertEquals(ExceptionHasBeenThrown, false);
    } //end testUltiGPXDatabase6
    
    /**
     * test1 test the importer when the file given contains a few routes
     * No input, no output, throws no exceptions.
     */
    public void testUltiGPXDatabase7() {
        boolean ExceptionHasBeenThrown = false;
        
        try {
            //Import file
            GPXImporter importer = new GPXImporter();
            Database file = new Database();
            importer.importUltiGPX(file, "testFile3.ultiGPX");
            
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
            assertEquals(file.getRoute(2).get(0).time, -1, 0.001);
            
            //Second waypoint
            assertEquals(file.getRoute(2).get(1).lat, 42.791342866);
            assertEquals(file.getRoute(2).get(1).lon, -0.144031858);
            assertEquals(file.getRoute(2).get(1).ele, 2144.810791);
            assertEquals(file.getRoute(2).get(1).getName().equals("GEAR"), true);
            assertEquals(file.getRoute(2).get(1).getDesc().equals("Pretty Waypoint"), true);
            assertEquals(0, file.getRoute(2).get(1).time, 1);
            
            //Third waypoint
            assertEquals(file.getRoute(2).get(2).lat, 50.852423497);
            assertEquals(file.getRoute(2).get(2).lon, 4.355069669);
            assertEquals(file.getRoute(2).get(2).ele, 13.584351);
            assertEquals(file.getRoute(2).get(2).getName().equals("H01"), true);
            assertEquals(file.getRoute(2).get(2).getDesc().equals("There is a a tree here"), true);
            assertEquals(file.getRoute(2).get(2).time, -1);
        } //end try
        
        catch (Exception e) {
            System.out.println(e);
            ExceptionHasBeenThrown = true;
        } //end catch
        assertEquals(ExceptionHasBeenThrown, false);
    } //end testUltiGPXDatabase7
    
    
    /**
     * test1 test the importer when the file contains a few tracks
     * No input, no output, throws no exceptions.
     */
    public void testUltiGPXDatabase8() {
        boolean ExceptionHasBeenThrown = false;
        
        try {
            //Import file
            GPXImporter importer = new GPXImporter();
            Database file = new Database();
            importer.importUltiGPX(file, "testFile5.ultiGPX");
            
            //First route
            assertEquals(file.getTrack(0).size(), 0);
            
            //Second route
            assertEquals(file.getTrack(1).size(), 1);
            assertEquals(file.getTrack(1).getArray().get(0).get(0).lat, 42.891719257);
            assertEquals(file.getTrack(1).getArray().get(0).get(0).lon, -0.114148362);
            
            //Third route
            assertEquals(file.getTrack(2).name.equals("06-SEP-05"), true);
            assertEquals(file.getTrack(2).getArray().get(0).get(0).lat, 42.891719257);
            assertEquals(file.getTrack(2).getArray().get(0).get(0).lon, -0.114148362);
            
            //Second waypoint
            assertEquals(file.getTrack(2).getArray().get(0).get(1).lat, 42.791342866);
            assertEquals(file.getTrack(2).getArray().get(0).get(1).lon, -0.144031858);
            assertEquals(file.getTrack(2).getArray().get(0).get(1).ele, 2144.810791);
            assertEquals(file.getTrack(2).getArray().get(0).get(1).getName().equals("GEAR"), true);
            
            //Third waypoint
            assertEquals(file.getTrack(2).getArray().get(0).get(2).lat, 50.852423497);
            assertEquals(file.getTrack(2).getArray().get(0).get(2).lon, 4.355069669);
            assertEquals(file.getTrack(2).getArray().get(0).get(2).ele, 13.584351);
            assertEquals(file.getTrack(2).getArray().get(0).get(2).getName().equals("H01"), true);
            assertEquals(file.getTrack(2).getArray().get(0).get(2).getDesc().equals("There is a a tree here"), true);
        } //end try
        
        catch (Exception e) {
            System.out.println(e);
            ExceptionHasBeenThrown = true;
        } //end catch
        assertEquals(ExceptionHasBeenThrown, false);
    } //end testUltiGPXGroup8
    
} //end GPXImporterTester
