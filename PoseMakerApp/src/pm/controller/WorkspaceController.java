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
    public static final String ELLIPSE_SELECTED = "ellipse";
    public static final String RECTANGLE_SELECTED = "rectangle";
    public static final String SELECTOR_SELECTED = "selector";
    public static final String NOTHING_SELECTED = "nothing";
    
    PoseMaker app;
    String buttonSelected;
    String shapeSelected;
    
    
    public WorkspaceController(PoseMaker initapp){
        app = initapp;
        buttonSelected = NOTHING_SELECTED;
        shapeSelected = NOTHING_SELECTED;
    }
    
    public void handleEllipseButtonPress(){
        buttonSelected = ELLIPSE_SELECTED;
        Workspace workspace = (Workspace) app.getWorkspaceComponent();
        workspace.reloadButtons(buttonSelected, shapeSelected);
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
