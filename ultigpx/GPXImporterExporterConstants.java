package ultigpx;


/**
 * GPXImporterExporterConsants is an interface containing constants for the GPX importer and Exporter
 * @author jillainsworth
 */
public interface GPXImporterExporterConstants {
    //GPX Constants
    public static final String GPX = "gpx";
    public static final String WAYPOINT = "wpt";
    public static final String LATITUDE = "lat";
    public static final String LONGITUDE = "lon";
    public static final String NAME = "name";
    public static final String DEFAULT_NAME = "";
    public static final String DESCRIPTION = "desc";
    public static final String DEFAULT_DESCRIPTION = "";
    public static final String ELEVATION = "ele";
    public static final double DEFAULT_ELEVATION = 0;
    public static final String TIME = "time";
    public static final double DEFAULT_TIME = -1;
    public static final String ROUTE = "rte";
    public static final String ROUTE_POINT = "rtept";
    public static final String TRACK = "trk";
    public static final String TRACK_SEGMENT = "trkseg";
    public static final String TRACK_POINT = "trkpt";
    public static final String GPX_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>\n<gpx creator=\"UltiGPX\" version=\"1.1\">\n<metadata> metadataType </metadata>\n";
    
    //UlitGPX Constants
    //Note: most of UltiGPX files look similar to GPX files, so lots of the constants above are used in exporting UltiGPX
    public static final String ULTI_GPX = "UltiGPX";
    public static final String ULTI_GPX_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>\n";
    public static final String GROUP = "group";
    
    //KML Constants
    //I am thinking about adding more constants here
    public static final String KML_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<kml xmlns=\"http://earth.google.com/kml/2.2\">\n";
    public static final String KML_FOOTER = "</kml>";
    
} //end GPXImporterExporterConstants