/*
 * 
 */
package pm.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Cursor;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javax.imageio.ImageIO;
import pm.PoseMaker;
import pm.data.CustomEllipse;
import pm.data.CustomRectangle;
import pm.data.CustomShape;
import pm.data.DataManager;
import pm.gui.Workspace;
import properties_manager.PropertiesManager;
import saf.AppTemplate;
import static saf.settings.AppPropertyType.LOAD_ERROR_MESSAGE;
import static saf.settings.AppPropertyType.LOAD_ERROR_TITLE;
import static saf.settings.AppStartupConstants.PATH_WORK;
import saf.ui.AppMessageDialogSingleton;

/**
 *
 * @author Noah
 */
public class WorkspaceController {
    
    //TODO: Create handler methods, finish constructor which takes a PoseMaker element as its argument
    public static final String ELLIPSE = "ellipse";
    public static final String RECTANGLE = "rectangle";
    public static final String SELECTOR = "selector";
    public static final String NOTHING_SELECTED = "nothing";
    public static final Color HIGHLIGHT_COLOR = Color.YELLOW;
    public static final String SNAPSHOT_PATH = PATH_WORK + "Pose.png";
    public static final String SAVE_SNAPSHOT_ERROR_MESSAGE = "An error occurred saving your snapshot.";
    public static final String SAVE_SNAPSHOT_ERROR_TITLE = "Save Snapshot Error";
    
    PoseMaker app;
    
    //Keeps track of the button currently selected
    String buttonSelected;
    
    //Keeps track of whether or not a shape is currently selected
    private boolean isSelected;
    
    //If a shape is currently selected, keeps track of the reference
    private CustomShape selectedShape;
    
    //Keeps track of the selected shape's original stroke color, so it can be changed
    //back once the shape is no longer selected
    private Color originalStrokeColor;
    
    //Keeps track of whether or not the mouse has been clicked and not released, as well as original click coordinates
    private boolean isClicked;
    private int origX;
    private int origY;
    
    public WorkspaceController(PoseMaker initapp){
        app = initapp;
        buttonSelected = NOTHING_SELECTED;
        isSelected = false;
        selectedShape = null;
        originalStrokeColor = null;
    }
    
    /**
     * If ellipse button is pressed and a shape is already selected, deselect the shape
     */
    public void handleEllipseButtonPress(){
        buttonSelected = ELLIPSE;
        if(isSelected){
            deselectShape();
        }
        isClicked = false;
        Workspace workspace = (Workspace) app.getWorkspaceComponent();
        workspace.reloadButtons(buttonSelected, isSelected);
        workspace.reloadWorkspace();
    }
    
    /**
     * If rectangle button is pressed and a shape is already selected, deselect the shape
     */
    public void handleRectangleButtonPress() {
        buttonSelected = RECTANGLE;
        if(isSelected){
            deselectShape();
        }
        isClicked = false;
        Workspace workspace = (Workspace) app.getWorkspaceComponent();
        workspace.reloadButtons(buttonSelected, isSelected);
        workspace.reloadWorkspace();
    }

    public void handleSelectionButtonPress() {
        buttonSelected = SELECTOR;
        Workspace workspace = (Workspace) app.getWorkspaceComponent();
        workspace.reloadButtons(buttonSelected, isSelected);
    }

    public void handleRemoveButtonPress() {
        if(isSelected){
            DataManager data = (DataManager) app.getDataComponent();
            Workspace workspace = (Workspace) app.getWorkspaceComponent();
            
            //Remove the shape, deselect it, and reload the buttons and workspace
            data.getShapes().remove(selectedShape);
            deselectShape();
            workspace.reloadButtons(buttonSelected, isSelected);
            workspace.reloadWorkspace();
        }
    }

    public void handleBackButtonPress() {
        if(isSelected){
            DataManager data = (DataManager) app.getDataComponent();
            Workspace workspace = (Workspace) app.getWorkspaceComponent();
            
            data.getShapes().remove(selectedShape);
            data.getShapes().add(0, selectedShape);
            workspace.reloadWorkspace();
        }
    }

    public void handleFrontButtonPress() {
        if(isSelected){
            DataManager data = (DataManager) app.getDataComponent();
            Workspace workspace = (Workspace) app.getWorkspaceComponent();
            
            data.getShapes().remove(selectedShape);
            data.getShapes().add(selectedShape);
            workspace.reloadWorkspace();
        }
    }
    
    public void handleSnapshotButtonPress(Canvas canvas) {
        WritableImage image = canvas.snapshot(null, 
                new WritableImage(Workspace.CANVAS_WIDTH, Workspace.CANVAS_HEIGHT));
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        File pose = new File(SNAPSHOT_PATH);
        try{
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", pose);
        }
        catch (Exception e){
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(SAVE_SNAPSHOT_ERROR_TITLE, SAVE_SNAPSHOT_ERROR_MESSAGE);
        }
    }

    public void handleMouseEntered() {
        if(buttonSelected.equals(RECTANGLE) || buttonSelected.equals(ELLIPSE)){
            app.getWorkspaceComponent().getWorkspace().setCursor(Cursor.CROSSHAIR);
        }
    }

    public void handleMouseExited() {
        if(buttonSelected.equals(RECTANGLE) || buttonSelected.equals(ELLIPSE)){
            app.getWorkspaceComponent().getWorkspace().setCursor(Cursor.DEFAULT);
        }
    }

    public void handleMousePressed(int xClick, int yClick) {
        
        DataManager data = (DataManager) app.getDataComponent();
        Workspace workspace = (Workspace) app.getWorkspaceComponent();
        if(buttonSelected.equals(RECTANGLE)){
            isClicked = true;
            CustomRectangle newRectangle = new CustomRectangle(xClick, yClick, 0, 0, workspace.getShapeFill(), 
                workspace.getLineFill(), workspace.getLineThinkness());
            data.getShapes().add(newRectangle);
            workspace.reloadWorkspace();
        }
        else if(buttonSelected.equals(ELLIPSE)){
            isClicked = true;
            CustomEllipse newEllipse = new CustomEllipse(xClick, yClick, 0, 0, workspace.getShapeFill(), 
                workspace.getLineFill(), workspace.getLineThinkness());
            data.getShapes().add(newEllipse);
            workspace.reloadWorkspace();
        }
        else if(buttonSelected.equals(SELECTOR)){
            for(int i = data.getShapes().size() - 1; i >= 0; i--){
                if(checkPosition(data.getShapes().get(i), xClick, yClick)){
                    if(isSelected){
                        deselectShape();
                    }
                    isClicked = true;
                    origX = xClick;
                    origY = yClick;
                    
                    selectedShape = data.getShapes().get(i);
                    isSelected = true;
                    originalStrokeColor = selectedShape.getStrokeColor();
                    selectedShape.setStrokeColor(HIGHLIGHT_COLOR);

                    workspace.reloadWorkspace();
                    break;
                }
            }
        }
    }

    public void handleMouseDragged(int xDrag, int yDrag) {
        if(isClicked){
            DataManager data = (DataManager) app.getDataComponent();
            Workspace workspace = (Workspace) app.getWorkspaceComponent();
            if(buttonSelected.equals(RECTANGLE) || buttonSelected.equals(ELLIPSE)){
                CustomShape toUpdate = data.getShapes().get(data.getShapes().size() - 1);
                toUpdate.setWidth(xDrag - toUpdate.getxValue());
                toUpdate.setHeight(yDrag - toUpdate.getyValue());
                workspace.reloadWorkspace();
            }
            
            if(buttonSelected.equals(SELECTOR) && isSelected){
                selectedShape.setxValue(selectedShape.getxValue() + (xDrag - origX));
                selectedShape.setyValue(selectedShape.getyValue() + (yDrag - origY));
                origX = xDrag;
                origY = yDrag;
                workspace.reloadWorkspace();
            }
        }
    }

    public void handleMouseReleased() {
        isClicked = false;
        origX = 0;
        origY = 0;
    }
    
    /**
     * Deselects the currently selected shape
     */
    private void deselectShape(){
        Workspace workspace = (Workspace) app.getWorkspaceComponent();
        selectedShape.setStrokeColor(originalStrokeColor);
        workspace.setLineFill(originalStrokeColor);
        isSelected = false;
        originalStrokeColor = null;
        selectedShape = null;
    }
    
    private boolean checkPosition(CustomShape c, int xMouse, int yMouse){
        if(c instanceof CustomRectangle){
            //Get the minimum and maximum x and y values for the rectangle
            int cXMin = c.getxValue();
            int cYMin = c.getyValue();
            int cXMax = cXMin + c.getWidth();
            int cYMax = cYMin + c.getHeight();
            //Check if the cursor is within them
            if(xMouse >= cXMin && xMouse <= cXMax && yMouse >= cYMin && yMouse <= cYMax)
                return true;
        }
        if(c instanceof CustomEllipse){
            //First we have to calculate the x and y axes
            int topLeftX = c.getxValue();
            int topLeftY = c.getyValue();
            double xAxis = (c.getWidth()) / 2;
            double yAxis = (c.getHeight()) / 2;
            
            //Now we have to calculate the center of the ellipse
            double centerX = (topLeftX + xAxis);
            double centerY = (topLeftY + yAxis);
            //Now we can use the formula to calculate whether or not the mouse click is within the bounds of the ellipse
            double pointCheck = (Math.pow(xMouse - centerX, 2))/(Math.pow(xAxis, 2)) + 
                    (Math.pow(yMouse - centerY, 2))/(Math.pow(yAxis, 2));
            if(pointCheck <= 1)
                return true;
        }
        return false;
    }
    
    public boolean getIsSelected(){
        return isSelected;
    }
    
    public CustomShape getSelectedShape(){
        return selectedShape;
    }
    
    public void setOriginalStrokeColor(Color c){
        originalStrokeColor = c;
    }
    
    public String getButtonSelected(){
        return buttonSelected;
    }
    
    public Color getOriginalStrokeColor(){
        return originalStrokeColor;
    }

}
