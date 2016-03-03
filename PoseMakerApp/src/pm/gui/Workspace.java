package pm.gui;

import java.io.IOException;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import pm.PoseMaker;
import pm.PropertyType;
import pm.controller.PageEditController;
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

    static final int CREATION_BUTTON_WIDTH = 50;
    static final int ORDER_BUTTON_WIDTH = 100;
    static final int CAMERA_BUTTON_WIDTH = 200;
    
    // HERE'S THE APP
    AppTemplate app;

    // IT KNOWS THE GUI IT IS PLACED INSIDE
    AppGUI gui;
    
    //The PageEditController that we'll use to assign buttons their event handlers
    PageEditController pageEditController;
    
    //The BorderPane we will put our toolbars and rendering surface in
    BorderPane workspaceBP;
    
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
    Button[] buttons;

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
        
        //This will handle events for us
        pageEditController = new PageEditController((PoseMaker) app);
        
        //Create a new Pane for workspace
        workspace = new Pane();
        
        //Create a new BorderPane which we will put in workspace
        workspaceBP = new BorderPane();
        
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
        
        //Now initialize all the buttons being used
        buttons = new Button[7];
        for(int i = 0; i < 7; i++){
            //Buttons 0 - 3 will be part of creationBar
            if(i == 0){
                buttons[i] = gui.initChildButton(creationBar, PropertyType.ELLIPSE_ICON.toString(), 
                    PropertyType.ELLIPSE_TOOLTIP.toString(), false);
                buttons[i].setOnAction(e -> {
                    pageEditController.handleEllipseButtonPress();
                });
                buttons[i].setMaxWidth(CREATION_BUTTON_WIDTH);
                buttons[i].setMinWidth(CREATION_BUTTON_WIDTH);
                buttons[i].setPrefWidth(CREATION_BUTTON_WIDTH);
            }
            if(i == 1){
                buttons[i] = gui.initChildButton(creationBar, PropertyType.RECTANGLE_ICON.toString(), 
                    PropertyType.RECTANGLE_TOOLTIP.toString(), false);
                buttons[i].setOnAction(e -> {
                    pageEditController.handleRectangleButtonPress();
                });
                buttons[i].setMaxWidth(CREATION_BUTTON_WIDTH);
                buttons[i].setMinWidth(CREATION_BUTTON_WIDTH);
                buttons[i].setPrefWidth(CREATION_BUTTON_WIDTH);
            }
            if(i == 2){
                buttons[i] = gui.initChildButton(creationBar, PropertyType.REMOVE_ICON.toString(), 
                    PropertyType.REMOVE_TOOLTIP.toString(), false);
                buttons[i].setOnAction(e -> {
                    pageEditController.handleRemoveButtonPress();
                });
                buttons[i].setMaxWidth(CREATION_BUTTON_WIDTH);
                buttons[i].setMinWidth(CREATION_BUTTON_WIDTH);
                buttons[i].setPrefWidth(CREATION_BUTTON_WIDTH);
            }
            if(i == 3){
                buttons[i] = gui.initChildButton(creationBar, PropertyType.SELECTION_TOOL_ICON.toString(), 
                    PropertyType.SELECTION_TOOL_TOOLTIP.toString(), false);
                buttons[i].setOnAction(e -> {
                    pageEditController.handleSelectionButtonPress();
                });
                buttons[i].setMaxWidth(CREATION_BUTTON_WIDTH);
                buttons[i].setMinWidth(CREATION_BUTTON_WIDTH);
                buttons[i].setPrefWidth(CREATION_BUTTON_WIDTH);
            }
            
            //Buttons 4 and 5 will be part of orderBar
            //TODO: Get the button images for order up and order down buttons, add in handlers
            if(i == 4){
                buttons[i] = gui.initChildButton(orderBar, PropertyType.SELECTION_TOOL_ICON.toString(), 
                    PropertyType.SELECTION_TOOL_TOOLTIP.toString(), false);
                buttons[i].setOnAction(e -> {
                    pageEditController.handleSelectionButtonPress();
                });
                buttons[i].setMaxWidth(ORDER_BUTTON_WIDTH);
                buttons[i].setMinWidth(ORDER_BUTTON_WIDTH);
                buttons[i].setPrefWidth(ORDER_BUTTON_WIDTH);
            }
            if(i == 5){
                buttons[i] = gui.initChildButton(orderBar, PropertyType.SELECTION_TOOL_ICON.toString(), 
                    PropertyType.SELECTION_TOOL_TOOLTIP.toString(), false);
                buttons[i].setOnAction(e -> {
                    pageEditController.handleSelectionButtonPress();
                });
                buttons[i].setMaxWidth(ORDER_BUTTON_WIDTH);
                buttons[i].setMinWidth(ORDER_BUTTON_WIDTH);
                buttons[i].setPrefWidth(ORDER_BUTTON_WIDTH);
            }
            
            //Button 6 holds the camera button
            if(i == 6){
                buttons[i] = gui.initChildButton(screenshotBar, PropertyType.SELECTION_TOOL_ICON.toString(), 
                    PropertyType.SELECTION_TOOL_TOOLTIP.toString(), false);
                buttons[i].setOnAction(e -> {
                    pageEditController.handleSelectionButtonPress();
                });
                buttons[i].setMaxWidth(CAMERA_BUTTON_WIDTH);
                buttons[i].setMinWidth(CAMERA_BUTTON_WIDTH);
                buttons[i].setPrefWidth(CAMERA_BUTTON_WIDTH);
            }
        }
        
        //Add toolbars to the editing toolbar
        editToolbar.getChildren().add(creationBar);
        editToolbar.getChildren().add(orderBar);
        
        
        
        workspaceBP.setLeft(editToolbar);
        workspace.getChildren().add(workspaceBP);
    }
    
    /**
     * This function specifies the CSS style classes for all the UI components
     * known at the time the workspace is initially constructed. Note that the
     * tag editor controls are added and removed dynamicaly as the application
     * runs so they will have their style setup separately.
     */
    @Override
    public void initStyle() {
        creationBar.getStyleClass().add("bordered_pane");
        orderBar.getStyleClass().add("bordered_pane");
    }

    /**
     * This function reloads all the controls for editing tag attributes into
     * the workspace.
     */
    @Override
    public void reloadWorkspace() {

    }
}
