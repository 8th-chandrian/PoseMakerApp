package pm.gui;

import java.io.IOException;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pm.PropertyType;
import saf.ui.AppGUI;
import saf.AppTemplate;
import saf.components.AppWorkspaceComponent;

/**
 * This class serves as the workspace component for this application, providing
 * the user interface controls for editing work.
 *
 * @author Richard McKenna
 * @author ?
 * @version 1.0
 */
public class Workspace extends AppWorkspaceComponent {

    static final int CREATION_BUTTON_WIDTH = 25;
    // HERE'S THE APP
    AppTemplate app;

    // IT KNOWS THE GUI IT IS PLACED INSIDE
    AppGUI gui;
    
    //The left sidebar containing all workspace controls
    VBox editToolbar;
    
    //The various toolbars within the left sidebar, in order from top to bottom
    HBox creationBar;
    HBox orderBar;
    VBox bColorBar;
    VBox fColorBar;
    VBox oColorBar;
    VBox oThicknessBar;
    VBox screenshotBar;
    
    //The array of buttons for creationBar
    Button[] creationButtons;

    /**
     * Constructor for initializing the workspace, note that this constructor
     * will fully setup the workspace user interface for use.
     *
     * @param initApp The application this workspace is part of.
     *
     * @throws IOException Thrown should there be an error loading application
     * data for setting up the user interface.
     */
    public Workspace(AppTemplate initApp) throws IOException {
	// KEEP THIS FOR LATER
	app = initApp;

	// KEEP THE GUI FOR LATER
	gui = app.getGUI();
        
        //Create a new borderpane
        workspace = new BorderPane();
        
        //Create the left sidebar
        editToolbar = new VBox();
        
        //Initialize the toolbars within the left toolbar
        creationBar = new HBox();
        orderBar = new HBox();
        bColorBar = new VBox();
        fColorBar = new VBox();
        oColorBar = new VBox();
        oThicknessBar = new VBox();
        screenshotBar = new VBox();
        
        //Now initialize the array of buttons in creationBar
        creationButtons = new Button[4];
        for(int i = 0; i < 4; i++){
            if(i == 0){
                creationButtons[i] = gui.initChildButton(creationBar, PropertyType.ELLIPSE_ICON.toString(), 
                    PropertyType.ELLIPSE_TOOLTIP.toString(), false);
            }
            if(i == 1){
                creationButtons[i] = gui.initChildButton(creationBar, PropertyType.RECTANGLE_ICON.toString(), 
                    PropertyType.RECTANGLE_TOOLTIP.toString(), false);
            }
            if(i == 2){
                creationButtons[i] = gui.initChildButton(creationBar, PropertyType.REMOVE_ICON.toString(), 
                    PropertyType.REMOVE_TOOLTIP.toString(), false);
            }
            if(i == 3){
                creationButtons[i] = gui.initChildButton(creationBar, PropertyType.SELECTION_TOOL_ICON.toString(), 
                    PropertyType.SELECTION_TOOL_TOOLTIP.toString(), false);
            }
            
            creationButtons[i].setMaxWidth(CREATION_BUTTON_WIDTH);
            creationButtons[i].setMinWidth(CREATION_BUTTON_WIDTH);
            creationButtons[i].setPrefWidth(CREATION_BUTTON_WIDTH);
        }
        
        
    }
    
    /**
     * This function specifies the CSS style classes for all the UI components
     * known at the time the workspace is initially constructed. Note that the
     * tag editor controls are added and removed dynamicaly as the application
     * runs so they will have their style setup separately.
     */
    @Override
    public void initStyle() {
	// NOTE THAT EACH CLASS SHOULD CORRESPOND TO
	// A STYLE CLASS SPECIFIED IN THIS APPLICATION'S
	// CSS FILE
    }

    /**
     * This function reloads all the controls for editing tag attributes into
     * the workspace.
     */
    @Override
    public void reloadWorkspace() {

    }
}
