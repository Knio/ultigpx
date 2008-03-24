import junit.framework.*;
import junit.extensions.*;

/**
 * Test Class for GPXExporterTester for Project of CPSC 301 <br>
 * Tests GPXExporter class
 * @author Jill Ainsworth
 */
public class GPXExporterTester extends TestCase {
    public GPXExporterTester() {
        super();
    } //end constructor
    
    /**
     * testExport1 test the exporter when the input is not valid
     * No input, no output, throws no exceptions.
     */
    public void testExport1() {
        boolean ExceptionHasBeenThrown = false;
        
        try {
            GPXExporter exporter = new GPXExporter();
            exporter.exportGPX(null, null);
        } //end try
        
        catch (Exception e) {
            ExceptionHasBeenThrown = true;
        } //end catch
        assertEquals(ExceptionHasBeenThrown, true);
    } //end testExport1
    
    /**
     * testExport2 test the exporter when the input is not valid
     * No input, no output, throws no exceptions.
     */
    public void testExport2() {
        boolean ExceptionHasBeenThrown = false;
        
        try {
            UGPXFile file = new UGPXFile();
            file.addWaypoint(new Waypoint("waypoint1", "", 1, 1, 0, 0));
            GPXExporter exporter = new GPXExporter();
            exporter.exportGPX(file, null);
        } //end try
        
        catch (Exception e) {
            ExceptionHasBeenThrown = true;
        } //end catch
        assertEquals(ExceptionHasBeenThrown, true);
    } //end testExport2
    
    /**
     * testExport3 test the exporter when the input is not valid
     * No input, no output, throws no exceptions.
     */
    public void testExport3() {
        boolean ExceptionHasBeenThrown = false;
        
        try {
            GPXExporter exporter = new GPXExporter();
            exporter.exportGPX(null, "export.kml");
        } //end try
        
        catch (Exception e) {
            ExceptionHasBeenThrown = true;
        } //end catch
        assertEquals(ExceptionHasBeenThrown, true);
    } //end testExport3
    
    /**
     * testExport4 test the exporter when you want to export a waypoint
     * No input, no output, throws no exceptions.
     */
    public void testExport4() {
        boolean ExceptionHasBeenThrown = false;
        
        try {
            UGPXFile file = new UGPXFile();
            file.addWaypoint(new Waypoint("waypoint1", "", 1, 1, 0, 0));
            GPXExporter exporter = new GPXExporter();
            exporter.exportGPX(file, "export1.kml");
        } //end try
        
        catch (Exception e) {
            ExceptionHasBeenThrown = true;
        } //end catch
        assertEquals(ExceptionHasBeenThrown, false);
    } //end testExport4
    
    /**
     * testExport5 test the exporter when you want to export a route
     * No input, no output, throws no exceptions.
     */
    public void testExport5() {
        boolean ExceptionHasBeenThrown = false;
        
        try {
            UGPXFile file = new UGPXFile();
            Route newRoute = new Route();
            newRoute.setName("Trial Route");
            newRoute.add(new Waypoint("waypoint1", "First Waypoint", 2, 2, 0, 1000));
            newRoute.add(new Waypoint("waypoint2", "Second Waypoint", 2.7, 2.5, 0, 2000));
            file.addRoute(newRoute);
            GPXExporter exporter = new GPXExporter();
            exporter.exportGPX(file, "export2.kml");
        } //end try
        
        catch (Exception e) {
            ExceptionHasBeenThrown = true;
        } //end catch
        assertEquals(ExceptionHasBeenThrown, false);
    } //end testExport5
    
    /**
     * testExport6 test the exporter when you want to export a track
     * No input, no output, throws no exceptions.
     */
    public void testExport6() {
        boolean ExceptionHasBeenThrown = false;
        
        try {
            UGPXFile file = new UGPXFile();
            Track newTrack = new Track();
            newTrack.setName("Trial Track");
            TrackSegment segment1 = new TrackSegment();
            segment1.add(new Waypoint("waypoint1", "First Waypoint", 2, 2, 0, 1000));
            segment1.add(new Waypoint("waypoint2", "Second Waypoint", 2.7, 2.5, 0, 2000));
            TrackSegment segment2 = new TrackSegment();
            segment2.add(new Waypoint("waypoint1", "First Waypoint", 3, 3, 1, 1000));
            segment2.add(new Waypoint("waypoint2", "Second Waypoint", 3.7, 3.5, 0, 4000));
            newTrack.add(segment1);
            newTrack.add(segment2);
            file.addTrack(newTrack);
            GPXExporter exporter = new GPXExporter();
            exporter.exportGPX(file, "export3.kml");
        } //end try
        
        catch (Exception e) {
            ExceptionHasBeenThrown = true;
            System.out.println(e);
        } //end catch
        //assertEquals(ExceptionHasBeenThrown, false);
    } //end testExport6
    
} //end GPXExporterTester
