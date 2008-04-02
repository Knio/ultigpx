package ultigpx;

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
     * testExport1 test the KML exporter when the input is not valid
     * No input, no output, throws no exceptions.
     */
    public void testExport1() {
        boolean ExceptionHasBeenThrown = false;
        
        try {
            GPXExporter exporter = new GPXExporter();
            exporter.exportToKML(null, null);
        } //end try
        
        catch (Exception e) {
            ExceptionHasBeenThrown = true;
        } //end catch
        assertEquals(ExceptionHasBeenThrown, true);
    } //end testExport1
    
    /**
     * testExport2 test the KML exporter when the input is not valid
     * No input, no output, throws no exceptions.
     */
    public void testExport2() {
        boolean ExceptionHasBeenThrown = false;
        
        try {
            UGPXFile file = new UGPXFile();
            file.addWaypoint(new Waypoint("waypoint1", "", 1, 1, 0, 0));
            GPXExporter exporter = new GPXExporter();
            exporter.exportToKML(file, null);
        } //end try
        
        catch (Exception e) {
            ExceptionHasBeenThrown = true;
        } //end catch
        assertEquals(ExceptionHasBeenThrown, true);
    } //end testExport2
    
    /**
     * testExport3 test the KML exporter when the input is not valid
     * No input, no output, throws no exceptions.
     */
    public void testExport3() {
        boolean ExceptionHasBeenThrown = false;
        
        try {
            GPXExporter exporter = new GPXExporter();
            exporter.exportToKML(null, "export.kml");
        } //end try
        
        catch (Exception e) {
            ExceptionHasBeenThrown = true;
        } //end catch
        assertEquals(ExceptionHasBeenThrown, true);
    } //end testExport3
    
    /**
     * testExport4 test the KML exporter when you want to export a waypoint
     * No input, no output, throws no exceptions.
     */
    public void testExport4() {
        boolean ExceptionHasBeenThrown = false;
        
        try {
            UGPXFile file = new UGPXFile();
            file.addWaypoint(new Waypoint("waypoint1", "", 1, 1, 0, 0));
            GPXExporter exporter = new GPXExporter();
            exporter.exportToKML(file, "export1.kml");
        } //end try
        
        catch (Exception e) {
            ExceptionHasBeenThrown = true;
        } //end catch
        assertEquals(ExceptionHasBeenThrown, false);
    } //end testExport4
    
    /**
     * testExport5 test the KML exporter when you want to export a route
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
            exporter.exportToKML(file, "export2.kml");
        } //end try
        
        catch (Exception e) {
            ExceptionHasBeenThrown = true;
        } //end catch
        assertEquals(ExceptionHasBeenThrown, false);
    } //end testExport5
    
    /**
     * testExport6 test the KML exporter when you want to export a track
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
            exporter.exportToKML(file, "export3.kml");
        } //end try
        
        catch (Exception e) {
            ExceptionHasBeenThrown = true;
            System.out.println(e);
        } //end catch
        assertEquals(ExceptionHasBeenThrown, false);
    } //end testExport6
    
    /**
     * testExport7 test the GPX exporter when the input is not valid
     * No input, no output, throws no exceptions.
     */
    public void testExport7() {
        boolean ExceptionHasBeenThrown = false;
        
        try {
            GPXExporter exporter = new GPXExporter();
            exporter.exportToGPX(null, null);
        } //end try
        
        catch (Exception e) {
            ExceptionHasBeenThrown = true;
        } //end catch
        assertEquals(ExceptionHasBeenThrown, true);
    } //end testExport7
    
    /**
     * testExport8 test the GPX exporter when the input is not valid
     * No input, no output, throws no exceptions.
     */
    public void testExport8() {
        boolean ExceptionHasBeenThrown = false;
        
        try {
            UGPXFile file = new UGPXFile();
            file.addWaypoint(new Waypoint("waypoint1", "", 1, 1, 0, 0));
            GPXExporter exporter = new GPXExporter();
            exporter.exportToGPX(file, null);
        } //end try
        
        catch (Exception e) {
            ExceptionHasBeenThrown = true;
        } //end catch
        assertEquals(ExceptionHasBeenThrown, true);
    } //end testExport8
    
    /**
     * testExport9 test the GPX exporter when the input is not valid
     * No input, no output, throws no exceptions.
     */
    public void testExport9() {
        boolean ExceptionHasBeenThrown = false;
        
        try {
            GPXExporter exporter = new GPXExporter();
            exporter.exportToGPX(null, "export.gpx");
        } //end try
        
        catch (Exception e) {
            ExceptionHasBeenThrown = true;
        } //end catch
        assertEquals(ExceptionHasBeenThrown, true);
    } //end testExport9
    
    /**
     * testExport10 test the GPX exporter when you want to export a waypoint
     * No input, no output, throws no exceptions.
     */
    public void testExport10() {
        boolean ExceptionHasBeenThrown = false;
        
        try {
            UGPXFile file = new UGPXFile();
            file.addWaypoint(new Waypoint("waypoint1", "", 1, 1, 0, 0));
            GPXExporter exporter = new GPXExporter();
            exporter.exportToGPX(file, "export1.gpx");
        } //end try
        
        catch (Exception e) {
            ExceptionHasBeenThrown = true;
        } //end catch
        assertEquals(ExceptionHasBeenThrown, false);
    } //end testExport10
    
    /**
     * testExport11 test the GPX exporter when you want to export a route
     * No input, no output, throws no exceptions.
     */
    public void testExport11() {
        boolean ExceptionHasBeenThrown = false;
        
        try {
            UGPXFile file = new UGPXFile();
            Route newRoute = new Route();
            newRoute.setName("Trial Route");
            newRoute.add(new Waypoint("waypoint1", "First Waypoint", 2, 2, 0, 1000));
            newRoute.add(new Waypoint("waypoint2", "Second Waypoint", 2.7, 2.5, 0, 2000));
            file.addRoute(newRoute);
            GPXExporter exporter = new GPXExporter();
            exporter.exportToGPX(file, "export2.gpx");
        } //end try
        
        catch (Exception e) {
            ExceptionHasBeenThrown = true;
        } //end catch
        assertEquals(ExceptionHasBeenThrown, false);
    } //end testExport11
    
    /**
     * testExport12 test the GPX exporter when you want to export a track
     * No input, no output, throws no exceptions.
     */
    public void testExport12() {
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
            exporter.exportToGPX(file, "export3.gpx");
        } //end try
        
        catch (Exception e) {
            ExceptionHasBeenThrown = true;
            System.out.println(e);
        } //end catch
        assertEquals(ExceptionHasBeenThrown, false);
    } //end testExport12
    
} //end GPXExporterTester
