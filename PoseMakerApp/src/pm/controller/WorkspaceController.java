/*
 * 
 */
package pm.controller;

import javafx.scene.paint.Color;
import pm.PoseMaker;
import pm.gui.Workspace;
import saf.AppTemplate;

/**
 *
 * @author Noah
 */
public class WorkspaceController {
    
    //TODO: Create handler methods, finish constructor which takes a PoseMaker element as its argument
    Workspace workspace;
    
    public WorkspaceController(PoseMaker app){
        workspace = (Workspace) app.getWorkspaceComponent();
    }
    
    public void handleEllipseButtonPress(){
        //TODO: Add functionality to handle the ellipse button being pressed
        //Button should be disabled after being pressed, while enabling the rectangle and select buttons and 
        //disabling the remove button, and the cursor should change to a crosshairs while user is enabled 
        //to draw ellipses on the rendering surface
    }
    
    public void handleRectangleButtonPress() {
        //TODO: Add functionality to handle the ellipse button being pressed
        //Functionality is similar to the handleEllipseButtonPress method above, but user can draw rectangles instead
    }

    public void handleSelectionButtonPress() {
        //TODO: Add functionality to handle the selection button being pressed
        //Upon pressing the selection button, the selection button should be disabled while the rectangle and ellipse buttons are
        //enabled. The remove button should only be enabled when a shape is actually selected.
    }

    public void handleRemoveButtonPress() {
        //TODO: Add functionality to handle the remove button being pressed
        //If a shape is currently selected, pressing the remove button should remove it from the rendering surface
    }
    
}
