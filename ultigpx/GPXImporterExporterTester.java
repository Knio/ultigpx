package ultigpx;


import junit.framework.*;
import junit.extensions.*;

/**
 * Test Class for GPXImporter for Project of CPSC 301 <br>
 * Tests GPXImporter class
 * @author Jill Ainsworth
 */
public class GPXImporterExporterTester extends TestCase {
    public GPXImporterExporterTester() {
        super();
    } //end constructor
    
    public void testGPXGroup1() {
        boolean ExceptionHasBeenThrown = false;
        
        try {
            //Import file
            GPXImporter importer = new GPXImporter();
            Group file = new Group();
            importer.importGPX(file, "testFile4.GPX");
            
            //Export file
            GPXExporter exporter = new GPXExporter();
            exporter.exportToGPX(file, "ImportExport1.gpx");
        } //end try
        
        catch (Exception e) {
            System.out.println(e);
            ExceptionHasBeenThrown = true;
        } //end catch
        assertEquals(ExceptionHasBeenThrown, false);
    } //end testGPXGroup1
    
    public void testGPXGroup2() {
        boolean ExceptionHasBeenThrown = false;
        
        try {
            //Importer and file
            GPXImporter importer = new GPXImporter();
            Group file = new Group();
            
            //Populate file
            Track newTrack = new Track();
            newTrack.setName("Trial Track");
            TrackSegment segment1 = new TrackSegment();
            segment1.add(new Waypoint("waypoint1", "First Waypoint", 2, 2, 0, 100));
            segment1.add(new Waypoint("waypoint2", "Second Waypoint", 2.7, 2.5, 0, 2000));
            TrackSegment segment2 = new TrackSegment();
            segment2.add(new Waypoint("waypoint1", "First Waypoint", 3, 3, 1, 1000));
            segment2.add(new Waypoint("waypoint2", "Second Waypoint", 3.7, 3.5, 0, 4000));
            newTrack.add(segment1);
            newTrack.add(segment2);
            file.addTrack(newTrack);
            
            //Export file
            GPXExporter exporter = new GPXExporter();
            exporter.exportToGPX(file, "ImportExport2.gpx");
            
            //Import file
            Database file2 = new Database();
            importer.importGPX(file2, "ImportExport2.gpx");
            
            //Compare Track
            assertEquals(file2.getTrack(0).getName().equals("Trial Track"), true);
            assertEquals(file2.getTrack(0).getArray().get(0).get(0).getName().equals("waypoint1"), true);
            assertEquals(file2.getTrack(0).getArray().get(0).get(0).getDesc().equals("First Waypoint"), true);
            assertEquals(file2.getTrack(0).getArray().get(0).get(0).lat, 2, 0.1);
            assertEquals(file2.getTrack(0).getArray().get(0).get(0).lon, 2, 0.1);
            assertEquals(file2.getTrack(0).getArray().get(0).get(0).ele, 0, 0.1);
            //           assertEquals(file2.getTrack(0).getArray().get(0).get(0).time, 100);
            assertEquals(file2.getTrack(0).getArray().get(0).get(1).getName().equals("waypoint2"), true);
            assertEquals(file2.getTrack(0).getArray().get(0).get(1).getDesc().equals("Second Waypoint"), true);
            assertEquals(file2.getTrack(0).getArray().get(0).get(1).lat, 2.7, 0.1);
            assertEquals(file2.getTrack(0).getArray().get(0).get(1).lon, 2.5, 0.1);
            assertEquals(file2.getTrack(0).getArray().get(0).get(1).ele, 0, 0.1);
            //           assertEquals(file2.getTrack(0).getArray().get(0).get(1).time, 2000);
            assertEquals(file2.getTrack(0).getArray().get(1).get(0).getName().equals("waypoint1"), true);
            assertEquals(file2.getTrack(0).getArray().get(1).get(0).getDesc().equals("First Waypoint"), true);
            assertEquals(file2.getTrack(0).getArray().get(1).get(0).lat, 3, 0.1);
            assertEquals(file2.getTrack(0).getArray().get(1).get(0).lon, 3, 0.1);
            assertEquals(file2.getTrack(0).getArray().get(1).get(0).ele, 1, 0.1);
            //           assertEquals(file2.getTrack(0).getArray().get(1).get(0).time, 1000);
            assertEquals(file2.getTrack(0).getArray().get(1).get(1).getName().equals("waypoint2"), true);
            assertEquals(file2.getTrack(0).getArray().get(1).get(1).getDesc().equals("Second Waypoint"), true);
            assertEquals(file2.getTrack(0).getArray().get(1).get(1).lat, 3.7, 0.1);
            assertEquals(file2.getTrack(0).getArray().get(1).get(1).lon, 3.5, 0.1);
            assertEquals(file2.getTrack(0).getArray().get(1).get(1).ele, 0, 0.1);
            //           assertEquals(file2.getTrack(0).getArray().get(1).get(1).time, 4000);
            
        } //end try
        
        catch (Exception e) {
            System.out.println(e);
            ExceptionHasBeenThrown = true;
        } //end catch
        assertEquals(ExceptionHasBeenThrown, false);
    } //end testGPXGroup2
    
    public void testGPXDatabase1() {
        boolean ExceptionHasBeenThrown = false;
        
        try {
            //Import file
            GPXImporter importer = new GPXImporter();
            Database file = new Database();
            importer.importGPX(file, "testFile4.GPX");
            
            //Export file
            GPXExporter exporter = new GPXExporter();
            exporter.exportToGPX(file, "ImportExport3.gpx");
        } //end try
        
        catch (Exception e) {
            System.out.println(e);
            ExceptionHasBeenThrown = true;
        } //end catch
        assertEquals(ExceptionHasBeenThrown, false);
    } //end testGPXDatabase1
    
    public void testGPXDatabase2() {
        boolean ExceptionHasBeenThrown = false;
        
        try {
            //Importer and file
            GPXImporter importer = new GPXImporter();
            Database file = new Database();
            
            //Populate file
            Track newTrack = new Track();
            newTrack.setName("Trial Track");
            TrackSegment segment1 = new TrackSegment();
            segment1.add(new Waypoint("waypoint1", "First Waypoint", 2, 2, 0, 100));
            segment1.add(new Waypoint("waypoint2", "Second Waypoint", 2.7, 2.5, 0, 2000));
            TrackSegment segment2 = new TrackSegment();
            segment2.add(new Waypoint("waypoint1", "First Waypoint", 3, 3, 1, 1000));
            segment2.add(new Waypoint("waypoint2", "Second Waypoint", 3.7, 3.5, 0, 4000));
            newTrack.add(segment1);
            newTrack.add(segment2);
            file.addTrack(newTrack);
            
            //Export file
            GPXExporter exporter = new GPXExporter();
            exporter.exportToGPX(file, "ImportExport4.gpx");
            
            //Import file
            Database file2 = new Database();
            importer.importGPX(file2, "ImportExport4.gpx");
            
            //Compare Track
            assertEquals(file2.getTrack(0).getName().equals("Trial Track"), true);
            assertEquals(file2.getTrack(0).getArray().get(0).get(0).getName().equals("waypoint1"), true);
            assertEquals(file2.getTrack(0).getArray().get(0).get(0).getDesc().equals("First Waypoint"), true);
            assertEquals(file2.getTrack(0).getArray().get(0).get(0).lat, 2, 0.1);
            assertEquals(file2.getTrack(0).getArray().get(0).get(0).lon, 2, 0.1);
            assertEquals(file2.getTrack(0).getArray().get(0).get(0).ele, 0, 0.1);
            //           assertEquals(file2.getTrack(0).getArray().get(0).get(0).time, 100);
            assertEquals(file2.getTrack(0).getArray().get(0).get(1).getName().equals("waypoint2"), true);
            assertEquals(file2.getTrack(0).getArray().get(0).get(1).getDesc().equals("Second Waypoint"), true);
            assertEquals(file2.getTrack(0).getArray().get(0).get(1).lat, 2.7, 0.1);
            assertEquals(file2.getTrack(0).getArray().get(0).get(1).lon, 2.5, 0.1);
            assertEquals(file2.getTrack(0).getArray().get(0).get(1).ele, 0, 0.1);
            //           assertEquals(file2.getTrack(0).getArray().get(0).get(1).time, 2000);
            assertEquals(file2.getTrack(0).getArray().get(1).get(0).getName().equals("waypoint1"), true);
            assertEquals(file2.getTrack(0).getArray().get(1).get(0).getDesc().equals("First Waypoint"), true);
            assertEquals(file2.getTrack(0).getArray().get(1).get(0).lat, 3, 0.1);
            assertEquals(file2.getTrack(0).getArray().get(1).get(0).lon, 3, 0.1);
            assertEquals(file2.getTrack(0).getArray().get(1).get(0).ele, 1, 0.1);
            //           assertEquals(file2.getTrack(0).getArray().get(1).get(0).time, 1000);
            assertEquals(file2.getTrack(0).getArray().get(1).get(1).getName().equals("waypoint2"), true);
            assertEquals(file2.getTrack(0).getArray().get(1).get(1).getDesc().equals("Second Waypoint"), true);
            assertEquals(file2.getTrack(0).getArray().get(1).get(1).lat, 3.7, 0.1);
            assertEquals(file2.getTrack(0).getArray().get(1).get(1).lon, 3.5, 0.1);
            assertEquals(file2.getTrack(0).getArray().get(1).get(1).ele, 0, 0.1);
            //           assertEquals(file2.getTrack(0).getArray().get(1).get(1).time, 4000);
            
        } //end try
        
        catch (Exception e) {
            System.out.println(e);
            ExceptionHasBeenThrown = true;
        } //end catch
        assertEquals(ExceptionHasBeenThrown, false);
    } //end testGPXDatabase2
    
       public void testUltiGPXDatabase1() {
        boolean ExceptionHasBeenThrown = false;
        
        try {
            //Import file
            GPXImporter importer = new GPXImporter();
            Database file = new Database();
            importer.importUltiGPX(file, "testFile5.ultiGPX");
            
            //Export file
            GPXExporter exporter = new GPXExporter();
            exporter.exportToUltiGPX(file, "ImportExport5.ultiGPX");
        } //end try
        
        catch (Exception e) {
            System.out.println(e);
            ExceptionHasBeenThrown = true;
        } //end catch
        assertEquals(ExceptionHasBeenThrown, false);
    } //end testUltiGPXDatabase1
    
    public void testUltiGPXDatabase2() {
        boolean ExceptionHasBeenThrown = false;
        
        try {
            //Importer and file
            GPXImporter importer = new GPXImporter();
            Database file = new Database();
            
            //Populate file
            Track newTrack = new Track();
            newTrack.setName("Trial Track");
            TrackSegment segment1 = new TrackSegment();
            segment1.add(new Waypoint("waypoint1", "First Waypoint", 2, 2, 0, 100));
            segment1.add(new Waypoint("waypoint2", "Second Waypoint", 2.7, 2.5, 0, 2000));
            TrackSegment segment2 = new TrackSegment();
            segment2.add(new Waypoint("waypoint1", "First Waypoint", 3, 3, 1, 1000));
            segment2.add(new Waypoint("waypoint2", "Second Waypoint", 3.7, 3.5, 0, 4000));
            newTrack.add(segment1);
            newTrack.add(segment2);
            file.addTrack(newTrack);
            
            //Export file
            GPXExporter exporter = new GPXExporter();
            exporter.exportToUltiGPX(file, "ImportExport6.gpx");
            
            //Import file
            Database file2 = new Database();
            importer.importUltiGPX(file2, "ImportExport6.gpx");
            
            //Compare Track
            assertEquals(file2.getTrack(0).getName().equals("Trial Track"), true);
            assertEquals(file2.getTrack(0).getArray().get(0).get(0).getName().equals("waypoint1"), true);
            assertEquals(file2.getTrack(0).getArray().get(0).get(0).getDesc().equals("First Waypoint"), true);
            assertEquals(file2.getTrack(0).getArray().get(0).get(0).lat, 2, 0.1);
            assertEquals(file2.getTrack(0).getArray().get(0).get(0).lon, 2, 0.1);
            assertEquals(file2.getTrack(0).getArray().get(0).get(0).ele, 0, 0.1);
            //           assertEquals(file2.getTrack(0).getArray().get(0).get(0).time, 100);
            assertEquals(file2.getTrack(0).getArray().get(0).get(1).getName().equals("waypoint2"), true);
            assertEquals(file2.getTrack(0).getArray().get(0).get(1).getDesc().equals("Second Waypoint"), true);
            assertEquals(file2.getTrack(0).getArray().get(0).get(1).lat, 2.7, 0.1);
            assertEquals(file2.getTrack(0).getArray().get(0).get(1).lon, 2.5, 0.1);
            assertEquals(file2.getTrack(0).getArray().get(0).get(1).ele, 0, 0.1);
            //           assertEquals(file2.getTrack(0).getArray().get(0).get(1).time, 2000);
            assertEquals(file2.getTrack(0).getArray().get(1).get(0).getName().equals("waypoint1"), true);
            assertEquals(file2.getTrack(0).getArray().get(1).get(0).getDesc().equals("First Waypoint"), true);
            assertEquals(file2.getTrack(0).getArray().get(1).get(0).lat, 3, 0.1);
            assertEquals(file2.getTrack(0).getArray().get(1).get(0).lon, 3, 0.1);
            assertEquals(file2.getTrack(0).getArray().get(1).get(0).ele, 1, 0.1);
            //           assertEquals(file2.getTrack(0).getArray().get(1).get(0).time, 1000);
            assertEquals(file2.getTrack(0).getArray().get(1).get(1).getName().equals("waypoint2"), true);
            assertEquals(file2.getTrack(0).getArray().get(1).get(1).getDesc().equals("Second Waypoint"), true);
            assertEquals(file2.getTrack(0).getArray().get(1).get(1).lat, 3.7, 0.1);
            assertEquals(file2.getTrack(0).getArray().get(1).get(1).lon, 3.5, 0.1);
            assertEquals(file2.getTrack(0).getArray().get(1).get(1).ele, 0, 0.1);
            //           assertEquals(file2.getTrack(0).getArray().get(1).get(1).time, 4000);
            
        } //end try
        
        catch (Exception e) {
            System.out.println(e);
            ExceptionHasBeenThrown = true;
        } //end catch
        assertEquals(ExceptionHasBeenThrown, false);
    } //end testUltiGPXDatabase2
    
} //end GPXImporterTester