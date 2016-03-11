/*
 * 
 */
package pm.data;

import javafx.scene.paint.Color;

/**
 *
 * @author Noah
 */
public class CustomEllipse extends CustomShape{
    
    /**
     * The constructor. Deal with it.
     */
    public CustomEllipse(int xValue, int yValue, int width, int height, Color fillColor, Color strokeColor, double lineWidth) {
        super(xValue, yValue, width, height, fillColor, strokeColor, lineWidth);
    }  
    
    public CustomEllipse(){
        super();
    }
}

