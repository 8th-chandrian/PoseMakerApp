package pm.data;

import java.util.ArrayList;
import javafx.scene.paint.Color;
import saf.components.AppDataComponent;
import saf.AppTemplate;

/**
 * This class serves as the data management component for this application.
 *
 * @author Richard McKenna
 * @author ?
 * @version 1.0
 */
public class DataManager implements AppDataComponent {
    // THIS IS A SHARED REFERENCE TO THE APPLICATION
    AppTemplate app;
    
    public static final Color DEFAULT_BACKGROUND_COLOR = Color.WHITE;
    
    private ArrayList<CustomShape> shapes;
    private Color backgroundColor;

    /**
     * This constructor creates the data manager and sets up the ArrayList of CustomShape objects.
     * The ArrayList will contain all of the shapes we create, in order from bottom to top.
     *
     * @param initApp The application within which this data manager is serving.
     */
    public DataManager(AppTemplate initApp) throws Exception {
	// KEEP THE APP FOR LATER
	app = initApp;
        shapes = new ArrayList<CustomShape>();
        backgroundColor = DEFAULT_BACKGROUND_COLOR;
    }
    
    /**
     * Getter for shapes ArrayList
     * @return shapes
     */
    public ArrayList<CustomShape> getShapes(){
        return shapes;
    }
    
    public void setShapes(ArrayList<CustomShape> shapes){
        this.shapes = shapes;
    }
    
    public Color getBackgroundColor(){
        return backgroundColor;
    }
    
    public void setBackgroundColor(Color c){
        backgroundColor = c;
    }

    /**
     * This function clears out the ArrayList
     */
    @Override
    public void reset() {
        shapes = new ArrayList<CustomShape>();
        backgroundColor = DEFAULT_BACKGROUND_COLOR;
    }
}
