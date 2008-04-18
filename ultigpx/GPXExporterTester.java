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
    public void testGPXGroup1(){
        boolean ExceptionHasBeenThrown = false;
        
        try {
            GPXExporter exporter = new GPXExporter();
            exporter.exportToGPX((Group) null, null);
        } //end try
        
        catch (Exception e) {
            ExceptionHasBeenThrown = true;
        } //end catch
        assertEquals(ExceptionHasBeenThrown, true);
    } //end testGPXGroup1
    
    /**
     * testExport2 test the KML exporter when the input is not valid
     * No input, no output, throws no exceptions.
     */
    public void testGPXGroup2() {
        boolean ExceptionHasBeenThrown = false;
        
        try {
            Group file = new Group();
            file.addWaypoint(new Waypoint("waypoint1", "", 1, 1, 0, 0));
            GPXExporter exporter = new GPXExporter();
            exporter.exportToGPX(file, null);
        } //end try
        
        catch (Exception e) {
            ExceptionHasBeenThrown = true;
        } //end catch
        assertEquals(ExceptionHasBeenThrown, true);
    } //end testGPXGroup2
    
    /**
     * testExport3 test the KML exporter when the input is not valid
     * No input, no output, throws no exceptions.
     */
    public void testGPXGroup3() {
        boolean ExceptionHasBeenThrown = false;
        
        try {
            GPXExporter exporter = new GPXExporter();
            exporter.exportToGPX((Group) null, "exportGroup.gpx");
        } //end try
        
        catch (Exception e) {
            ExceptionHasBeenThrown = true;
        } //end catch
        assertEquals(ExceptionHasBeenThrown, true);
    } //end testGPXGroup3
    
    /**
     * testExport4 test the KML exporter when you want to export a waypoint
     * No input, no output, throws no exceptions.
     */
    public void testGPXGroup4() {
        boolean ExceptionHasBeenThrown = false;
        
        try {
            Group file = new Group();
            file.addWaypoint(new Waypoint("waypoint1", "", 1, 1, 0, 0));
            GPXExporter exporter = new GPXExporter();
            exporter.exportToGPX(file, "exportGroup1.gpx");
        } //end try
        
        catch (Exception e) {
            ExceptionHasBeenThrown = true;
        } //end catch
        assertEquals(ExceptionHasBeenThrown, false);
    } //end testGPXGroup4
    
    /**
     * testExport5 test the KML exporter when you want to export a route
     * No input, no output, throws no exceptions.
     */
    public void testGPXGroup5() {
        boolean ExceptionHasBeenThrown = false;
        
        try {
            Group file = new Group();
            Route newRoute = new Route();
            newRoute.setName("Trial Route");
            newRoute.add(new Waypoint("waypoint1", "First Waypoint", 2, 2, 0, 1000));
            newRoute.add(new Waypoint("waypoint2", "Second Waypoint", 2.7, 2.5, 0, 2000));
            file.addRoute(newRoute);
            GPXExporter exporter = new GPXExporter();
            exporter.exportToGPX(file, "exportGroup2.gpx");
        } //end try
        
        catch (Exception e) {
            ExceptionHasBeenThrown = true;
        } //end catch
        assertEquals(ExceptionHasBeenThrown, false);
    } //end testGPXGroup5
    
    /**
     * testExport6 test the KML exporter when you want to export a track
     * No input, no output, throws no exceptions.
     */
    public void testGPXGroup6() {
        boolean ExceptionHasBeenThrown = false;
        
        try {
            Group file = new Group();
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
            exporter.exportToGPX(file, "exportGroup3.gpx");
        } //end try
        
        catch (Exception e) {
            ExceptionHasBeenThrown = true;
            System.out.println(e);
        } //end catch
        assertEquals(ExceptionHasBeenThrown, false);
    } //end testGPXGroup6
    
        /**
     * testExport1 test the KML exporter when the input is not valid
     * No input, no output, throws no exceptions.
     */
    public void testGPXDatabase1(){
        boolean ExceptionHasBeenThrown = false;
        
        try {
            GPXExporter exporter = new GPXExporter();
            exporter.exportToGPX((Database) null, null);
        } //end try
        
        catch (Exception e) {
            ExceptionHasBeenThrown = true;
        } //end catch
        assertEquals(ExceptionHasBeenThrown, true);
    } //end testGPXDatabase1
    
    /**
     * testExport2 test the KML exporter when the input is not valid
     * No input, no output, throws no exceptions.
     */
    public void testGPXDatabase2() {
        boolean ExceptionHasBeenThrown = false;
        
        try {
            Database file = new Database();
            file.addWaypoint(new Waypoint("waypoint1", "", 1, 1, 0, 0));
            GPXExporter exporter = new GPXExporter();
            exporter.exportToGPX(file, null);
        } //end try
        
        catch (Exception e) {
            ExceptionHasBeenThrown = true;
        } //end catch
        assertEquals(ExceptionHasBeenThrown, true);
    } //end testGPXDatabase2
    
    /**
     * testExport3 test the KML exporter when the input is not valid
     * No input, no output, throws no exceptions.
     */
    public void testGPXDatabase3() {
        boolean ExceptionHasBeenThrown = false;
        
        try {
            GPXExporter exporter = new GPXExporter();
            exporter.exportToGPX((Database) null, "exportDatabase.gpx");
        } //end try
        
        catch (Exception e) {
            ExceptionHasBeenThrown = true;
        } //end catch
        assertEquals(ExceptionHasBeenThrown, true);
    } //end testGPXDatabase3
    
    /**
     * testExport4 test the KML exporter when you want to export a waypoint
     * No input, no output, throws no exceptions.
     */
    public void testGPXDatabase4() {
        boolean ExceptionHasBeenThrown = false;
        
        try {
            Database file = new Database();
            file.addWaypoint(new Waypoint("waypoint1", "", 1, 1, 0, 0));
            GPXExporter exporter = new GPXExporter();
            exporter.exportToGPX(file, "exportDatabase1.gpx");
        } //end try
        
        catch (Exception e) {
            ExceptionHasBeenThrown = true;
        } //end catch
        assertEquals(ExceptionHasBeenThrown, false);
    } //end testGPXDatabase4
    
    /**
     * testExport5 test the KML exporter when you want to export a route
     * No input, no output, throws no exceptions.
     */
    public void testGPXDatabase5() {
        boolean ExceptionHasBeenThrown = false;
        
        try {
            Database file = new Database();
            Route newRoute = new Route();
            newRoute.setName("Trial Route");
            newRoute.add(new Waypoint("waypoint1", "First Waypoint", 2, 2, 0, 1000));
            newRoute.add(new Waypoint("waypoint2", "Second Waypoint", 2.7, 2.5, 0, 2000));
            file.addRoute(newRoute);
            GPXExporter exporter = new GPXExporter();
            exporter.exportToGPX(file, "exportDatabase2.gpx");
        } //end try
        
        catch (Exception e) {
            ExceptionHasBeenThrown = true;
        } //end catch
        assertEquals(ExceptionHasBeenThrown, false);
    } //end testGPXDatabase5
    
    /**
     * testExport6 test the KML exporter when you want to export a track
     * No input, no output, throws no exceptions.
     */
    public void testGPXDatabase6() {
        boolean ExceptionHasBeenThrown = false;
        
        try {
            Database file = new Database();
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
            exporter.exportToGPX(file, "exportDatabase3.gpx");
        } //end try
        
        catch (Exception e) {
            ExceptionHasBeenThrown = true;
            System.out.println(e);
        } //end catch
        assertEquals(ExceptionHasBeenThrown, false);
    } //end testGPXDatabase6
    
        /**
     * testExport1 test the KML exporter when the input is not valid
     * No input, no output, throws no exceptions.
     */
    public void testUltiGPXGroup1(){
        boolean ExceptionHasBeenThrown = false;
        
        try {
            GPXExporter exporter = new GPXExporter();
            exporter.exportToUltiGPX((Group) null, null);
        } //end try
        
        catch (Exception e) {
            ExceptionHasBeenThrown = true;
        } //end catch
        assertEquals(ExceptionHasBeenThrown, true);
    } //end testUltiGPXGroup1
    
    /**
     * testExport2 test the KML exporter when the input is not valid
     * No input, no output, throws no exceptions.
     */
    public void testUltiGPXGroup2() {
        boolean ExceptionHasBeenThrown = false;
        
        try {
            Group file = new Group();
            file.addWaypoint(new Waypoint("waypoint1", "", 1, 1, 0, 0));
            GPXExporter exporter = new GPXExporter();
            exporter.exportToUltiGPX(file, null);
        } //end try
        
        catch (Exception e) {
            ExceptionHasBeenThrown = true;
        } //end catch
        assertEquals(ExceptionHasBeenThrown, true);
    } //end testUlitGPXGroup2
    
    /**
     * testExport3 test the KML exporter when the input is not valid
     * No input, no output, throws no exceptions.
     */
    public void testUltiGPXGroup3() {
        boolean ExceptionHasBeenThrown = false;
        
        try {
            GPXExporter exporter = new GPXExporter();
            exporter.exportToUltiGPX((Group) null, "exportGroup.ultigpx");
        } //end try
        
        catch (Exception e) {
            ExceptionHasBeenThrown = true;
        } //end catch
        assertEquals(ExceptionHasBeenThrown, true);
    } //end testUltiGPXGroup3
    
    /**
     * testExport4 test the KML exporter when you want to export a waypoint
     * No input, no output, throws no exceptions.
     */
    public void testUltiGPXGroup4() {
        boolean ExceptionHasBeenThrown = false;
        
        try {
            Group file = new Group();
            file.addWaypoint(new Waypoint("waypoint1", "", 1, 1, 0, 0));
            GPXExporter exporter = new GPXExporter();
            exporter.exportToUltiGPX(file, "exportGroup1.ultigpx");
        } //end try
        
        catch (Exception e) {
            ExceptionHasBeenThrown = true;
        } //end catch
        assertEquals(ExceptionHasBeenThrown, false);
    } //end testUltiGPXGroup4
    
    /**
     * testExport5 test the KML exporter when you want to export a route
     * No input, no output, throws no exceptions.
     */
    public void testUltiGPXGroup5() {
        boolean ExceptionHasBeenThrown = false;
        
        try {
            Group file = new Group();
            Route newRoute = new Route();
            newRoute.setName("Trial Route");
            newRoute.add(new Waypoint("waypoint1", "First Waypoint", 2, 2, 0, 1000));
            newRoute.add(new Waypoint("waypoint2", "Second Waypoint", 2.7, 2.5, 0, 2000));
            file.addRoute(newRoute);
            GPXExporter exporter = new GPXExporter();
            exporter.exportToUltiGPX(file, "exportGroup2.ultigpx");
        } //end try
        
        catch (Exception e) {
            ExceptionHasBeenThrown = true;
        } //end catch
        assertEquals(ExceptionHasBeenThrown, false);
    } //end testUltiGPXGroup5
    
    /**
     * testExport6 test the KML exporter when you want to export a track
     * No input, no output, throws no exceptions.
     */
    public void testUltiGPXGroup6() {
        boolean ExceptionHasBeenThrown = false;
        
        try {
            Group file = new Group();
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
            exporter.exportToUltiGPX(file, "exportGroup3.ultigpx");
        } //end try
        
        catch (Exception e) {
            ExceptionHasBeenThrown = true;
            System.out.println(e);
        } //end catch
        assertEquals(ExceptionHasBeenThrown, false);
    } //end testUltiGPXGroup6
    
        /**
     * testExport1 test the KML exporter when the input is not valid
     * No input, no output, throws no exceptions.
     */
    public void testUltiGPXDatabase1(){
        boolean ExceptionHasBeenThrown = false;
        
        try {
            GPXExporter exporter = new GPXExporter();
            exporter.exportToUltiGPX((Database) null, null);
        } //end try
        
        catch (Exception e) {
            ExceptionHasBeenThrown = true;
        } //end catch
        assertEquals(ExceptionHasBeenThrown, true);
    } //end testUltiGPXDatabase1
    
    /**
     * testExport2 test the KML exporter when the input is not valid
     * No input, no output, throws no exceptions.
     */
    public void testUltiGPXDatabase2() {
        boolean ExceptionHasBeenThrown = false;
        
        try {
            Database file = new Database();
            file.addWaypoint(new Waypoint("waypoint1", "", 1, 1, 0, 0));
            GPXExporter exporter = new GPXExporter();
            exporter.exportToGPX(file, null);
        } //end try
        
        catch (Exception e) {
            ExceptionHasBeenThrown = true;
        } //end catch
        assertEquals(ExceptionHasBeenThrown, true);
    } //end testUltiGPXDatabase2
    
    /**
     * testExport3 test the KML exporter when the input is not valid
     * No input, no output, throws no exceptions.
     */
    public void testUltiGPXDatabase3() {
        boolean ExceptionHasBeenThrown = false;
        
        try {
            GPXExporter exporter = new GPXExporter();
            exporter.exportToUltiGPX((Database) null, "exportDatabase.ultigpx");
        } //end try
        
        catch (Exception e) {
            ExceptionHasBeenThrown = true;
        } //end catch
        assertEquals(ExceptionHasBeenThrown, true);
    } //end testUltiGPXDatabase3
    
    /**
     * testExport4 test the KML exporter when you want to export a waypoint
     * No input, no output, throws no exceptions.
     */
    public void testUltiGPXDatabase4() {
        boolean ExceptionHasBeenThrown = false;
        
        try {
            Database file = new Database();
            file.addWaypoint(new Waypoint("waypoint1", "", 1, 1, 0, 0));
            GPXExporter exporter = new GPXExporter();
            exporter.exportToUltiGPX(file, "exportDatabase1.ultigpx");
        } //end try
        
        catch (Exception e) {
            ExceptionHasBeenThrown = true;
        } //end catch
        assertEquals(ExceptionHasBeenThrown, false);
    } //end testUltiGPXDatabase4
    
    /**
     * testExport5 test the KML exporter when you want to export a route
     * No input, no output, throws no exceptions.
     */
    public void testUltiGPXDatabase5() {
        boolean ExceptionHasBeenThrown = false;
        
        try {
            Database file = new Database();
            Route newRoute = new Route();
            newRoute.setName("Trial Route");
            newRoute.add(new Waypoint("waypoint1", "First Waypoint", 2, 2, 0, 1000));
            newRoute.add(new Waypoint("waypoint2", "Second Waypoint", 2.7, 2.5, 0, 2000));
            file.addRoute(newRoute);
            GPXExporter exporter = new GPXExporter();
            exporter.exportToUltiGPX(file, "exportDatabase2.ultigpx");
        } //end try
        
        catch (Exception e) {
            ExceptionHasBeenThrown = true;
        } //end catch
        assertEquals(ExceptionHasBeenThrown, false);
    } //end testUltiGPXDatabase5
    
    /**
     * testExport6 test the KML exporter when you want to export a track
     * No input, no output, throws no exceptions.
     */
    public void testUltiGPXDatabase6() {
        boolean ExceptionHasBeenThrown = false;
        
        try {
            Database file = new Database();
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
            exporter.exportToUltiGPX(file, "exportDatabase3.ultigpx");
        } //end try
        
        catch (Exception e) {
            ExceptionHasBeenThrown = true;
            System.out.println(e);
        } //end catch
        assertEquals(ExceptionHasBeenThrown, false);
    } //end testUltiGPXDatabase6
    
        /**
     * testExport6 test the KML exporter when you want to export a track
     * No input, no output, throws no exceptions.
     */
    public void testUltiGPXDatabase7() {
        boolean ExceptionHasBeenThrown = false;
        
        try {
            Database file = new Database();
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
            Group newGroup = new Group();
            newGroup.name = "Trial Group";
            newGroup.addTrack(newTrack);
            GPXExporter exporter = new GPXExporter();
            exporter.exportToUltiGPX(file, "exportDatabase3.ultigpx");
        } //end try
        
        catch (Exception e) {
            ExceptionHasBeenThrown = true;
            System.out.println(e);
        } //end catch
        assertEquals(ExceptionHasBeenThrown, false);
    } //end testUltiGPXDatabase7
    
        /**
     * testExport1 test the KML exporter when the input is not valid
     * No input, no output, throws no exceptions.
     */
    public void testKMLGroup1(){
        boolean ExceptionHasBeenThrown = false;
        
        try {
            GPXExporter exporter = new GPXExporter();
            exporter.exportToKML((Group) null, null);
        } //end try
        
        catch (Exception e) {
            ExceptionHasBeenThrown = true;
        } //end catch
        assertEquals(ExceptionHasBeenThrown, true);
    } //end testKMLGroup1
    
    /**
     * testExport2 test the KML exporter when the input is not valid
     * No input, no output, throws no exceptions.
     */
    public void testKMLGroup2() {
        boolean ExceptionHasBeenThrown = false;
        
        try {
            Group file = new Group();
            file.addWaypoint(new Waypoint("waypoint1", "", 1, 1, 0, 0));
            GPXExporter exporter = new GPXExporter();
            exporter.exportToKML(file, null);
        } //end try
        
        catch (Exception e) {
            ExceptionHasBeenThrown = true;
        } //end catch
        assertEquals(ExceptionHasBeenThrown, true);
    } //end testKMLGroup2
    
    /**
     * testExport3 test the KML exporter when the input is not valid
     * No input, no output, throws no exceptions.
     */
    public void testKMLGroup3() {
        boolean ExceptionHasBeenThrown = false;
        
        try {
            GPXExporter exporter = new GPXExporter();
            exporter.exportToKML((Group) null, "exportGroup.kml");
        } //end try
        
        catch (Exception e) {
            ExceptionHasBeenThrown = true;
        } //end catch
        assertEquals(ExceptionHasBeenThrown, true);
    } //end testKMLGroup3
    
    /**
     * testExport4 test the KML exporter when you want to export a waypoint
     * No input, no output, throws no exceptions.
     */
    public void testKMLGroup4() {
        boolean ExceptionHasBeenThrown = false;
        
        try {
            Group file = new Group();
            file.addWaypoint(new Waypoint("waypoint1", "", 1, 1, 0, 0));
            GPXExporter exporter = new GPXExporter();
            exporter.exportToKML(file, "exportGroup1.kml");
        } //end try
        
        catch (Exception e) {
            ExceptionHasBeenThrown = true;
        } //end catch
        assertEquals(ExceptionHasBeenThrown, false);
    } //end testKMLGroup4
    
    /**
     * testExport5 test the KML exporter when you want to export a route
     * No input, no output, throws no exceptions.
     */
    public void testKMLGroup5() {
        boolean ExceptionHasBeenThrown = false;
        
        try {
            Group file = new Group();
            Route newRoute = new Route();
            newRoute.setName("Trial Route");
            newRoute.add(new Waypoint("waypoint1", "First Waypoint", 2, 2, 0, 1000));
            newRoute.add(new Waypoint("waypoint2", "Second Waypoint", 2.7, 2.5, 0, 2000));
            file.addRoute(newRoute);
            GPXExporter exporter = new GPXExporter();
            exporter.exportToKML(file, "exportGroup2.kml");
        } //end try
        
        catch (Exception e) {
            ExceptionHasBeenThrown = true;
        } //end catch
        assertEquals(ExceptionHasBeenThrown, false);
    } //end testKMLGroup5
    
    /**
     * testExport6 test the KML exporter when you want to export a track
     * No input, no output, throws no exceptions.
     */
    public void testKMLGroup6() {
        boolean ExceptionHasBeenThrown = false;
        
        try {
            Group file = new Group();
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
            exporter.exportToKML(file, "exportGroup3.kml");
        } //end try
        
        catch (Exception e) {
            ExceptionHasBeenThrown = true;
            System.out.println(e);
        } //end catch
        assertEquals(ExceptionHasBeenThrown, false);
    } //end testKMLGroup6
    
        /**
     * testExport1 test the KML exporter when the input is not valid
     * No input, no output, throws no exceptions.
     */
    public void testKMLDatabase1(){
        boolean ExceptionHasBeenThrown = false;
        
        try {
            GPXExporter exporter = new GPXExporter();
            exporter.exportToKML((Database) null, null);
        } //end try
        
        catch (Exception e) {
            ExceptionHasBeenThrown = true;
        } //end catch
        assertEquals(ExceptionHasBeenThrown, true);
    } //end testKMLDatabase1
    
    /**
     * testExport2 test the KML exporter when the input is not valid
     * No input, no output, throws no exceptions.
     */
    public void testKMLDatabase2() {
        boolean ExceptionHasBeenThrown = false;
        
        try {
            Database file = new Database();
            file.addWaypoint(new Waypoint("waypoint1", "", 1, 1, 0, 0));
            GPXExporter exporter = new GPXExporter();
            exporter.exportToKML(file, null);
        } //end try
        
        catch (Exception e) {
            ExceptionHasBeenThrown = true;
        } //end catch
        assertEquals(ExceptionHasBeenThrown, true);
    } //end testKMLDatabase2
    
    /**
     * testExport3 test the KML exporter when the input is not valid
     * No input, no output, throws no exceptions.
     */
    public void testKMLDatabase3() {
        boolean ExceptionHasBeenThrown = false;
        
        try {
            GPXExporter exporter = new GPXExporter();
            exporter.exportToKML((Database) null, "exportDatabase.kml");
        } //end try
        
        catch (Exception e) {
            ExceptionHasBeenThrown = true;
        } //end catch
        assertEquals(ExceptionHasBeenThrown, true);
    } //end testKMLDatabase3
    
    /**
     * testExport4 test the KML exporter when you want to export a waypoint
     * No input, no output, throws no exceptions.
     */
    public void testKMLDatabase4() {
        boolean ExceptionHasBeenThrown = false;
        
        try {
            Database file = new Database();
            file.addWaypoint(new Waypoint("waypoint1", "", 1, 1, 0, 0));
            GPXExporter exporter = new GPXExporter();
            exporter.exportToKML(file, "exportDatabase1.kml");
        } //end try
        
        catch (Exception e) {
            ExceptionHasBeenThrown = true;
        } //end catch
        assertEquals(ExceptionHasBeenThrown, false);
    } //end testKMLDatabase4
    
    /**
     * testExport5 test the KML exporter when you want to export a route
     * No input, no output, throws no exceptions.
     */
    public void testKMLDatabase5() {
        boolean ExceptionHasBeenThrown = false;
        
        try {
            Database file = new Database();
            Route newRoute = new Route();
            newRoute.setName("Trial Route");
            newRoute.add(new Waypoint("waypoint1", "First Waypoint", 2, 2, 0, 1000));
            newRoute.add(new Waypoint("waypoint2", "Second Waypoint", 2.7, 2.5, 0, 2000));
            file.addRoute(newRoute);
            GPXExporter exporter = new GPXExporter();
            exporter.exportToKML(file, "exportDatabase2.kml");
        } //end try
        
        catch (Exception e) {
            ExceptionHasBeenThrown = true;
        } //end catch
        assertEquals(ExceptionHasBeenThrown, false);
    } //end testKMLDatabase5
    
    /**
     * testExport6 test the KML exporter when you want to export a track
     * No input, no output, throws no exceptions.
     */
    public void testKMLDatabase6() {
        boolean ExceptionHasBeenThrown = false;
        
        try {
            Database file = new Database();
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
            exporter.exportToKML(file, "exportDatabase3.kml");
        } //end try
        
        catch (Exception e) {
            ExceptionHasBeenThrown = true;
            System.out.println(e);
        } //end catch
        assertEquals(ExceptionHasBeenThrown, false);
    } //end testKMLDatabase6
    
    
    
} //end GPXExporterTester