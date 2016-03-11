package pm.file;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.control.TreeItem;
import javafx.scene.paint.Color;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;
import pm.data.CustomRectangle;
import pm.data.CustomShape;
import pm.data.DataManager;
import saf.components.AppDataComponent;
import saf.components.AppFileComponent;

/**
 * This class serves as the file management component for this application,
 * providing all I/O services.
 *
 * @author Richard McKenna
 * @author ?
 * @version 1.0
 */
public class FileManager implements AppFileComponent {

    public static final String JSON_BACKGROUND_CONTENT = "background_content";
    public static final String JSON_SHAPES_ARRAY = "shapes_array";
    
    public static final String JSON_SHAPE_TYPE = "shape_type";
    public static final String JSON_TYPE_RECTANGLE = "rectangle";
    public static final String JSON_TYPE_ELLIPSE = "ellipse";
    public static final String JSON_X_COORDINATE = "x_coordinate";
    public static final String JSON_Y_COORDINATE = "y_coordinate";
    public static final String JSON_WIDTH = "width";
    public static final String JSON_HEIGHT = "height";
    
    public static final String JSON_LINE_WIDTH = "line_width";
    public static final String JSON_LINE_COLOR = "line_color";
    public static final String JSON_FILL_COLOR = "fill_color";
    
    /**
     * This method is for saving user work, which in the case of this
     * application means the data that constitutes the page DOM.
     * 
     * @param data The data management component for this application.
     * 
     * @param filePath Path (including file name/extension) to where
     * to save the data to.
     * 
     * @throws IOException Thrown should there be an error writing 
     * out data to the file.
     */
    @Override
    public void saveData(AppDataComponent data, String filePath) throws IOException {
        StringWriter sw = new StringWriter();

	// BUILD THE HTMLTags ARRAY
	DataManager dataManager = (DataManager)data;

	// THEN THE TREE
	JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        fillArrayWithShapes(dataManager.getShapes(), arrayBuilder);
	JsonArray shapesArray = arrayBuilder.build();
	
	// THEN PUT IT ALL TOGETHER IN A JsonObject
	JsonObject dataManagerJSO = Json.createObjectBuilder()
		.add(JSON_SHAPES_ARRAY, shapesArray)
		.add(JSON_BACKGROUND_CONTENT, toHex(dataManager.getBackgroundColor()))
		.build();
	
	// AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING
	Map<String, Object> properties = new HashMap<>(1);
	properties.put(JsonGenerator.PRETTY_PRINTING, true);
	JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
	JsonWriter jsonWriter = writerFactory.createWriter(sw);
	jsonWriter.writeObject(dataManagerJSO);
	jsonWriter.close();

	// INIT THE WRITER
	OutputStream os = new FileOutputStream(filePath);
	JsonWriter jsonFileWriter = Json.createWriter(os);
	jsonFileWriter.writeObject(dataManagerJSO);
	String prettyPrinted = sw.toString();
	PrintWriter pw = new PrintWriter(filePath);
	pw.write(prettyPrinted);
	pw.close();
    }
    
    private void fillArrayWithShapes(ArrayList<CustomShape> list, JsonArrayBuilder arrayBuilder){
        for(int i = 0; i < list.size(); i++){
            JsonObject shapeObject = makeShapeJsonObject(list.get(i));
            arrayBuilder.add(shapeObject);
        }
    }
    
    /**
     * Private method for making Json objects from CustomShape objects
     * @param s
     *      The CustomShape being converted
     * @return 
     *      The JsonObject created from s
     */
    private JsonObject makeShapeJsonObject(CustomShape s){
        int xValue = s.getxValue();
        int yValue = s.getyValue();
        int width = s.getWidth();
        int height = s.getHeight();
        
        double lineWidth = s.getLineWidth();
        String lineColor = toHex(s.getStrokeColor());
        String fillColor = toHex(s.getFillColor());
        
        if(s instanceof CustomRectangle){
            JsonObject jsoRectangle = Json.createObjectBuilder()
                    .add(JSON_SHAPE_TYPE, JSON_TYPE_RECTANGLE)
                    .add(JSON_X_COORDINATE, xValue)
                    .add(JSON_Y_COORDINATE, yValue)
                    .add(JSON_WIDTH, width)
                    .add(JSON_HEIGHT, height)
                    .add(JSON_LINE_WIDTH, lineWidth)
                    .add(JSON_LINE_COLOR, lineColor)
                    .add(JSON_FILL_COLOR, fillColor)
                    .build();
            return jsoRectangle;
        }
        else{
            JsonObject jsoEllipse = Json.createObjectBuilder()
                    .add(JSON_SHAPE_TYPE, JSON_TYPE_ELLIPSE)
                    .add(JSON_X_COORDINATE, xValue)
                    .add(JSON_Y_COORDINATE, yValue)
                    .add(JSON_WIDTH, width)
                    .add(JSON_HEIGHT, height)
                    .add(JSON_LINE_WIDTH, lineWidth)
                    .add(JSON_LINE_COLOR, lineColor)
                    .add(JSON_FILL_COLOR, fillColor)
                    .build();
            return jsoEllipse;
        }
    }
      
    /**
     * This method loads data from a JSON formatted file into the data 
     * management component and then forces the updating of the workspace
     * such that the user may edit the data.
     * 
     * @param data Data management component where we'll load the file into.
     * 
     * @param filePath Path (including file name/extension) to where
     * to load the data from.
     * 
     * @throws IOException Thrown should there be an error reading
     * in data from the file.
     */
    @Override
    public void loadData(AppDataComponent data, String filePath) throws IOException {

    }

    // HELPER METHOD FOR LOADING DATA FROM A JSON FORMAT
    private JsonObject loadJSONFile(String jsonFilePath) throws IOException {
	InputStream is = new FileInputStream(jsonFilePath);
	JsonReader jsonReader = Json.createReader(is);
	JsonObject json = jsonReader.readObject();
	jsonReader.close();
	is.close();
	return json;
    }
    
    /**
     * This method exports the contents of the data manager to a 
     * Web page including the html page, needed directories, and
     * the CSS file.
     * 
     * @param data The data management component.
     * 
     * @param filePath Path (including file name/extension) to where
     * to export the page to.
     * 
     * @throws IOException Thrown should there be an error writing
     * out data to the file.
     */
    @Override
    public void exportData(AppDataComponent data, String filePath) throws IOException {

    }
    
    /**
     * This method is provided to satisfy the compiler, but it
     * is not used by this application.
     */
    @Override
    public void importData(AppDataComponent data, String filePath) throws IOException {
	// NOTE THAT THE Web Page Maker APPLICATION MAKES
	// NO USE OF THIS METHOD SINCE IT NEVER IMPORTS
	// EXPORTED WEB PAGES
    }
    
    /**
     * Converts a Color object to a hex string
     * @param c
     *      The color being converted
     * @return 
     *      The 7-character hex string beginning with a '#'
     */
    private static String toHex(Color c){
        //First convert to their integer value
        int redInt = (int)(c.getRed() * 255);
        int greenInt = (int)(c.getGreen() * 255);
        int blueInt = (int)(c.getBlue() * 255);
        
        //Then convert their int values to hex strings
        String redString = Integer.toHexString(redInt);
        String greenString = Integer.toHexString(greenInt);
        String blueString = Integer.toHexString(blueInt);
        
        if(redInt == 0)
            redString = "00";
        if(greenInt == 0)
            greenString = "00";
        if(blueInt == 0)
            blueString = "00";
        
        //Then concatenate
        String hexString = "#" + redString + greenString + blueString;
        return hexString;
    }
}
