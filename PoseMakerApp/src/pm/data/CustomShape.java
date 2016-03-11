/*
 * 
 */
package pm.data;

import javafx.scene.paint.Color;


/**
 *
 * @author Noah
 */
public class CustomShape{
    
    private int xValue;
    private int yValue;
    private int width;
    private int height;
    
    private Color fillColor;
    private Color strokeColor;
    private double lineWidth;

    /**
     * The constructor. Deal with it.
     */
    public CustomShape(int xValue, int yValue, int width, int height, Color fillColor, Color strokeColor, double lineWidth) {
        this.xValue = xValue;
        this.yValue = yValue;
        this.width = width;
        this.height = height;
        this.fillColor = fillColor;
        this.strokeColor = strokeColor;
        this.lineWidth = lineWidth;
    }
    
    /**
     * For the lazy.
     */
    public CustomShape(){
        this.xValue = 0;
        this.yValue = 0;
        this.width = 0;
        this.height = 0;
        this.fillColor = null;
        this.strokeColor = null;
        this.lineWidth = 0;
    }

    /**
     * @return the xValue
     */
    public int getxValue() {
        return xValue;
    }

    /**
     * @param xValue the xValue to set
     */
    public void setxValue(int xValue) {
        this.xValue = xValue;
    }

    /**
     * @return the yValue
     */
    public int getyValue() {
        return yValue;
    }

    /**
     * @param yValue the yValue to set
     */
    public void setyValue(int yValue) {
        this.yValue = yValue;
    }

    /**
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * @param width the width to set
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * @param height the height to set
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * @return the fillColor
     */
    public Color getFillColor() {
        return fillColor;
    }

    /**
     * @param fillColor the fillColor to set
     */
    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    /**
     * @return the strokeColor
     */
    public Color getStrokeColor() {
        return strokeColor;
    }

    /**
     * @param strokeColor the strokeColor to set
     */
    public void setStrokeColor(Color strokeColor) {
        this.strokeColor = strokeColor;
    }

    /**
     * @return the lineWidth
     */
    public double getLineWidth() {
        return lineWidth;
    }

    /**
     * @param lineWidth the lineWidth to set
     */
    public void setLineWidth(double lineWidth) {
        this.lineWidth = lineWidth;
    }
    
}
