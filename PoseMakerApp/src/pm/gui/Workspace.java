package pm.gui;

import java.io.IOException;
import java.util.ArrayList;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import pm.PoseMaker;
import pm.PropertyType;
import pm.controller.WorkspaceController;
import static pm.controller.WorkspaceController.HIGHLIGHT_COLOR;
import pm.data.CustomEllipse;
import pm.data.CustomRectangle;
import pm.data.CustomShape;
import pm.data.DataManager;
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
    static final int CAMERA_BUTTON_WIDTH = 240;
    public static final int CANVAS_WIDTH = 1000;
    public static final int CANVAS_HEIGHT = 635;
    
    // HERE'S THE APP
    AppTemplate app;

    // IT KNOWS THE GUI IT IS PLACED INSIDE
    AppGUI gui;
    
    //The WorkspaceController that we'll use to assign buttons their event handlers
    WorkspaceController workspaceController;
    
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
    
    //The arraylist to hold the toolbars and make iterating through them easier
    ArrayList<Pane> toolbars;
    
    //The array of buttons
    ArrayList<Button> buttons;
    
    //The slider for picking outline thickness
    Slider thickness;
    
    //The array of text labels
    ArrayList<Text> labels;
    
    //The array of color pickers
    ArrayList<ColorPicker> colorPickers;
    
    //Rendering objects
    Canvas canvas;
    GraphicsContext gc;

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
        workspaceController = new WorkspaceController((PoseMaker) app);
        
        //Create a new Pane for workspace
        workspace = new Pane();
        
        //Create a new BorderPane which we will put in workspace
        workspaceBP = new BorderPane();

        //Create the left sidebar
        editToolbar = new VBox();
        
        //Initialize the toolbars within the left toolbar
        toolbars = new ArrayList<Pane>();
        
        creationBar = new HBox();
        toolbars.add(creationBar);
        orderBar = new HBox();
        toolbars.add(orderBar);
        bColorBar = new VBox();
        toolbars.add(bColorBar);
        fColorBar = new VBox();
        toolbars.add(fColorBar);
        oColorBar = new VBox();
        toolbars.add(oColorBar);
        oThicknessBar = new VBox();
        toolbars.add(oThicknessBar);
        screenshotBar = new VBox();
        toolbars.add(screenshotBar);
        
        //Now initialize all the buttons being used
        buttons = new ArrayList<Button>();
        for(int i = 0; i < 7; i++){
            //Buttons 0 - 3 will be part of creationBar
            if(i == 0){
                buttons.add(gui.initChildButton(creationBar, PropertyType.ELLIPSE_ICON.toString(), 
                    PropertyType.ELLIPSE_TOOLTIP.toString(), false));
                buttons.get(i).setOnAction(e -> {
                    workspaceController.handleEllipseButtonPress();
                });
                buttons.get(i).setMaxWidth(CREATION_BUTTON_WIDTH);
                buttons.get(i).setMinWidth(CREATION_BUTTON_WIDTH);
                buttons.get(i).setPrefWidth(CREATION_BUTTON_WIDTH);
            }
            if(i == 1){
                buttons.add(gui.initChildButton(creationBar, PropertyType.RECTANGLE_ICON.toString(), 
                    PropertyType.RECTANGLE_TOOLTIP.toString(), false));
                buttons.get(i).setOnAction(e -> {
                    workspaceController.handleRectangleButtonPress();
                });
                buttons.get(i).setMaxWidth(CREATION_BUTTON_WIDTH);
                buttons.get(i).setMinWidth(CREATION_BUTTON_WIDTH);
                buttons.get(i).setPrefWidth(CREATION_BUTTON_WIDTH);
            }
            if(i == 2){
                buttons.add(gui.initChildButton(creationBar, PropertyType.REMOVE_ICON.toString(), 
                    PropertyType.REMOVE_TOOLTIP.toString(), false));
                buttons.get(i).setOnAction(e -> {
                    workspaceController.handleRemoveButtonPress();
                });
                buttons.get(i).setMaxWidth(CREATION_BUTTON_WIDTH);
                buttons.get(i).setMinWidth(CREATION_BUTTON_WIDTH);
                buttons.get(i).setPrefWidth(CREATION_BUTTON_WIDTH);
            }
            if(i == 3){
                buttons.add(gui.initChildButton(creationBar, PropertyType.SELECTION_TOOL_ICON.toString(), 
                    PropertyType.SELECTION_TOOL_TOOLTIP.toString(), false));
                buttons.get(i).setOnAction(e -> {
                    workspaceController.handleSelectionButtonPress();
                });
                buttons.get(i).setMaxWidth(CREATION_BUTTON_WIDTH);
                buttons.get(i).setMinWidth(CREATION_BUTTON_WIDTH);
                buttons.get(i).setPrefWidth(CREATION_BUTTON_WIDTH);
            }
            
            //Buttons 4 and 5 will be part of orderBar
            //TODO: Get the button images for order up and order down buttons, add in handlers
            if(i == 4){
                buttons.add(gui.initChildButton(orderBar, PropertyType.MOVE_TO_BACK_ICON.toString(), 
                    PropertyType.MOVE_TO_BACK_TOOLTIP.toString(), false));
                buttons.get(i).setOnAction(e -> {
                    workspaceController.handleBackButtonPress();
                });
                buttons.get(i).setMaxWidth(ORDER_BUTTON_WIDTH);
                buttons.get(i).setMinWidth(ORDER_BUTTON_WIDTH);
                buttons.get(i).setPrefWidth(ORDER_BUTTON_WIDTH);
            }
            if(i == 5){
                buttons.add(gui.initChildButton(orderBar, PropertyType.MOVE_TO_FRONT_ICON.toString(), 
                    PropertyType.MOVE_TO_FRONT_TOOLTIP.toString(), false));
                buttons.get(i).setOnAction(e -> {
                    workspaceController.handleFrontButtonPress();
                });
                buttons.get(i).setMaxWidth(ORDER_BUTTON_WIDTH);
                buttons.get(i).setMinWidth(ORDER_BUTTON_WIDTH);
                buttons.get(i).setPrefWidth(ORDER_BUTTON_WIDTH);
            }
            
            //Button 6 holds the camera button
            //TODO: Add in handler for snapshot button press
            if(i == 6){
                buttons.add(gui.initChildButton(screenshotBar, PropertyType.SNAPSHOT_ICON.toString(), 
                    PropertyType.SNAPSHOT_TOOLTIP.toString(), false));
                buttons.get(i).setOnAction(e -> {
                    workspaceController.handleSnapshotButtonPress(canvas);
                });
                buttons.get(i).setMaxWidth(CAMERA_BUTTON_WIDTH);
                buttons.get(i).setMinWidth(CAMERA_BUTTON_WIDTH);
                buttons.get(i).setPrefWidth(CAMERA_BUTTON_WIDTH);
            }
        }
        
        //Initialize the labels and add them to the arraylist
        labels = new ArrayList<Text>();
        
        Text backgroundColor = new Text("Background Color");
        labels.add(backgroundColor);
        Text fillColor = new Text("Fill Color");
        labels.add(fillColor);
        Text outlineColor = new Text("Outline Color");
        labels.add(outlineColor);
        Text outlineThickness = new Text("Outline Thickness");
        labels.add(outlineThickness);
        
        //Initialize the color pickers and add them to the arraylist
        colorPickers = new ArrayList<ColorPicker>();
        
        ColorPicker backgroundPicker = new ColorPicker();
        backgroundPicker.setOnAction(e -> {
            DataManager data = (DataManager) app.getDataComponent();
            data.setBackgroundColor(backgroundPicker.getValue());
            reloadWorkspace();
        });
        colorPickers.add(backgroundPicker);
        ColorPicker fillPicker = new ColorPicker();
        fillPicker.setOnAction(e -> {
           if(workspaceController.getIsSelected()){
               workspaceController.getSelectedShape().setFillColor(fillPicker.getValue());
               reloadWorkspace();
           }
        });
        colorPickers.add(fillPicker);
        ColorPicker outlinePicker = new ColorPicker(Color.BLACK);
        outlinePicker.setOnAction(e -> {
           if(workspaceController.getIsSelected()){
               workspaceController.setOriginalStrokeColor(outlinePicker.getValue());
           }
        });
        colorPickers.add(outlinePicker);
        
        for(int i = 0; i < 3; i++){
            if(i == 0){
                bColorBar.getChildren().add(labels.get(i));
                bColorBar.getChildren().add(colorPickers.get(i));
            }
            if(i == 1){
                fColorBar.getChildren().add(labels.get(i));
                fColorBar.getChildren().add(colorPickers.get(i));
            }
            if(i == 2){
                oColorBar.getChildren().add(labels.get(i));
                oColorBar.getChildren().add(colorPickers.get(i));
            }
        }
        
        oThicknessBar.getChildren().add(labels.get(3));
        
        //Initialize the thickness slider and add to oThicknessBar
        thickness = new Slider(1, 10, 1);
        thickness.setOnMouseDragged(e -> {
           if(workspaceController.getIsSelected()){
               workspaceController.getSelectedShape().setLineWidth(thickness.getValue());
               reloadWorkspace();
           }
        });
        oThicknessBar.getChildren().add(thickness);
        
        //Add toolbars to the editing toolbar
        for(int i = 0; i < toolbars.size(); i++){
            editToolbar.getChildren().add(toolbars.get(i));
        }
        
        //Create a new Canvas; this will be our render surface
        canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
        
        //Initialize the mouse events
        canvas.setOnMouseEntered(e -> {
            workspaceController.handleMouseEntered();
        });
        canvas.setOnMouseExited(e -> {
            workspaceController.handleMouseExited();
        });
        canvas.setOnMousePressed(e -> {
            int x = (int) e.getX();
            int y = (int) e.getY();
            workspaceController.handleMousePressed(x, y);
        });
        canvas.setOnMouseDragged(e -> {
            int x = (int) e.getX();
            int y = (int) e.getY();
            workspaceController.handleMouseDragged(x, y);
        });
        canvas.setOnMouseReleased(e -> {
            workspaceController.handleMouseReleased();
        });
        
        //Set the canvas to the center pane of the workspace BorderPane
        workspaceBP.setCenter(canvas);
        
        //Initialize gc to the canvas object's GraphicsContext
        gc = canvas.getGraphicsContext2D();
        
        workspaceBP.setLeft(editToolbar);
        workspace.getChildren().add(workspaceBP);
        reloadWorkspace();
    }
    
    /**
     * This function specifies the CSS style classes for all the UI components
     * known at the time the workspace is initially constructed. Note that the
     * tag editor controls are added and removed dynamicaly as the application
     * runs so they will have their style setup separately.
     */
    @Override
    public void initStyle() {
        for(int i = 0; i < toolbars.size(); i++){
            toolbars.get(i).getStyleClass().add("left_toolbar");
        }
        for(int i = 0; i < labels.size(); i++){
            labels.get(i).getStyleClass().add("heading_label");
        }
    }

    /**
     * This function reloads all the controls for editing tag attributes into
     * the workspace.
     */
    @Override
    public void reloadWorkspace() {
        DataManager data = (DataManager) app.getDataComponent();
        
        //Draw a new rectangle with our background color
        gc.setFill(data.getBackgroundColor());
        gc.fillRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
        
        for(int i = 0; i < data.getShapes().size(); i++){
            drawShape(data.getShapes().get(i));
        }
        
        reloadButtons(workspaceController.getButtonSelected(), workspaceController.getIsSelected());
        if(workspaceController.getIsSelected()){
            reloadControls(workspaceController.getSelectedShape());
            colorPickers.get(2).setValue(workspaceController.getOriginalStrokeColor());
        }
        if(workspaceController.getButtonSelected() == null){
            reloadControls(null);
        }
        
    }
    
    public void reloadButtons(String selectedButton, boolean selectedShape){
        if(selectedButton.equals(workspaceController.ELLIPSE)){
            buttons.get(0).setDisable(true); //Disable the ellipse button since it is already selected
            buttons.get(1).setDisable(false); //Enable the rectantgle button
            buttons.get(2).setDisable(true); //Disable the remove button because no shapes are selected
            buttons.get(3).setDisable(false); //Enable the selection button
            buttons.get(4).setDisable(true);//Disable both order switching buttons since no shapes are selected
            buttons.get(5).setDisable(true);
            buttons.get(6).setDisable(false); //Enable the snapshot button
        }
        else if(selectedButton.equals(workspaceController.RECTANGLE)){
            buttons.get(0).setDisable(false); //Enable the ellipse button
            buttons.get(1).setDisable(true); //Disable the rectantgle button since it is already selected
            buttons.get(2).setDisable(true); //Disable the remove button because no shapes are selected
            buttons.get(3).setDisable(false); //Enable the selection button
            buttons.get(4).setDisable(true);//Disable both order switching buttons since no shapes are selected
            buttons.get(5).setDisable(true);
            buttons.get(6).setDisable(false); //Enable the snapshot button
        }
        else if(selectedButton.equals(workspaceController.SELECTOR)){
            buttons.get(0).setDisable(false); //Enable the ellipse button
            buttons.get(1).setDisable(false); //Enable the rectangle button
            buttons.get(2).setDisable(!selectedShape); //Disable the remove button if no shape is selected
            buttons.get(3).setDisable(true); //Disable the selection button
            buttons.get(4).setDisable(!selectedShape);//Disable both order switching buttons if no shape is selected
            buttons.get(5).setDisable(!selectedShape);
            buttons.get(6).setDisable(false); //Enable the snapshot button
        }
        //If no button is currently selected, reloadButtons defaults to this
        else{
            buttons.get(0).setDisable(false); //Enable the ellipse button
            buttons.get(1).setDisable(false); //Enable the rectangle button
            buttons.get(2).setDisable(true); //Disable the remove button since no shape is selected
            buttons.get(3).setDisable(false); //Enable the selection button
            buttons.get(4).setDisable(true);//Disable both order switching buttons since no shape is selected
            buttons.get(5).setDisable(true);
            buttons.get(6).setDisable(false); //Enable the snapshot button
        }
    }
    
    public void reloadControls(CustomShape s){
        if(s == null){
            colorPickers.get(1).setValue(Color.WHITE);
            colorPickers.get(2).setValue(Color.BLACK);
            thickness.setValue(1);
        }
        else{
            colorPickers.get(1).setValue(s.getFillColor());
            colorPickers.get(2).setValue(s.getStrokeColor());
            thickness.setValue(s.getLineWidth());
        }
    }
    
    /**
     * Draws a given shape on the canvas
     * @param s 
     *      The shape to draw
     */
    public void drawShape(CustomShape s){
        gc.setFill(s.getFillColor());
        gc.setStroke(s.getStrokeColor());
        gc.setLineWidth(s.getLineWidth());
        if(s instanceof CustomRectangle){
            gc.fillRect(s.getxValue(), s.getyValue(), s.getWidth(), s.getHeight());
            gc.strokeRect(s.getxValue(), s.getyValue(), s.getWidth(), s.getHeight());
        }
        if(s instanceof CustomEllipse){
            gc.fillOval(s.getxValue(), s.getyValue(), s.getWidth(), s.getHeight());
            gc.strokeOval(s.getxValue(), s.getyValue(), s.getWidth(), s.getHeight());
        }
    }
    
    /**
     * Gets the current shape fill as indicated by fillPicker
     * @return the Color
     */
    public Color getShapeFill(){
        return colorPickers.get(1).getValue();
    }
    
    /**
     * Gets the current line fill as indicated by outlinePicker
     * @return the Color
     */
    public Color getLineFill(){
        return colorPickers.get(2).getValue();
    }
    
    /**
     * Gets the current line thickness as indicated by the thickness slider
     * @return the line thickness
     */
    public double getLineThinkness(){
        return thickness.getValue();
    }
    
    /**
     * Sets the current line fill
     * @param c 
     *      the Color being set
     */
    public void setLineFill(Color c){
        colorPickers.get(2).setValue(c);
    }
    
    public void setBackground(Color c){
        colorPickers.get(0).setValue(c);
    }
    
    public void tempDeselect(){
        workspaceController.getSelectedShape().setStrokeColor(workspaceController.getOriginalStrokeColor());
    }
    
    public void reselect(){
        workspaceController.getSelectedShape().setStrokeColor(HIGHLIGHT_COLOR);
    }
    
    
    /**
     * Converts a Color object to a hex string (for use with ColorPicker objects)
     * @param c
     *      The color being converted
     * @return 
     *      The 7-character hex string beginning with a '#'
     */
    /*public static String toHex(Color c){
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
    } */
}
