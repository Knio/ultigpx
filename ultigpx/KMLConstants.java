package ultigpx;

/**
 * KMLConsants is an interface containing constants for the KML Exporter
 * @author Jill Ainsworth
 */
public interface KMLConstants {
    
    //KML Constants
    public static final String KML_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<kml xmlns=\"http://earth.google.com/kml/2.2\">\n";
    public static final String KML_FOOTER = "</kml>";
    
    //Waypoint
    public static final String KML_WAYPOINT = "Placemark";
    public static final String KML_WAYPOINT_NAME = "name";
    public static final String KML_WAYPOINT_DESCRIPTION = "description";
    public static final String KML_WAYPOINT_POINT = "Point";
    public static final String KML_WAYPOINT_COORDINATES = "coordinates";
    
    //Track and Route
    public static final String KML_TRACK_ROUTE_HEADER = "Document";
    public static final String KML_TRACK_ROUTE_NAME = "name";
    public static final String KML_TRACK_ROUTE_DESCRIPTION = "description";
    public static final String KML_TRACK_ROUTE_STYLE_ID = "Style id";
    public static final String KML_TRACK_ROUTE_STYLE = "Style";
    public static final String KML_TRACK_ROUTE_DEFAULT_STYLE = "yellowLineGreePoly";
    public static final String KML_TRACK_ROUTE_LINE_STYLE = "LineStyle";
    public static final String KML_TRACK_ROUTE_COLOR = "color";
    public static final String KML_TRACK_ROUTE_LINE_WIDTH = "width";
    public static final int KML_TRACK_ROUTE_DEFAULT_WIDTH = 4;
    public static final String KML_TRACK_ROUTE_POLYGON_STYLE = "PolyStyle";
    public static final String KML_TRACK_ROUTE_POLYGON_COLOR = "color";
    public static final String KML_TRACK_ROUTE_POINT_STYLE = "styleUrl";
    public static final String KML_TRACK_ROUTE_POINT_DEFAULT_STYLE = "#yellowLineGreenPoly";
    public static final String KML_TRACK_ROUTE_POINT_LINE = "LineString";
    public static final String KML_TRACK_ROUTE_POINT_EXTRUDE = "extrude";
    public static final int KML_TRACK_ROUTE_POINT_DEFAULT_EXTRUDE = 1;
    public static final String KML_TRACK_ROUTE_POINT_TESSELLATE = "tessellate";
    public static final int KML_TRACK_ROUTE_POINT_DEFAULT_TESSELLATE = 1;
    public static final String KML_TRACK_ROUTE_POINT_ALTITUDE = "altitudeMode";
    public static final String KML_TRACK_ROUTE_POINT_DEFAULT_ALTITUDE = "absolute";
    public static final String KML_TRACK_ROUTE_POINT_COORDINATES = "coordinates";
    
    
} //end GPXImporterExporterConstants