import java.awt.*;import java.awt.image.*;import java.applet.*;import java.util.*;public class CloseBox extends Control{	private	static	Color		lighterGray = new Color(239,239,239);	public CloseBox(NewWindow win)	{		super();		local = false;		theWindow = win;		xPos = 9;		yPos = 4;		width = 11;		height = 11;		value = 0;		visible = true;		hiliteState = 255;	}		public void Draw()	{		if (hiliteState == 255)		{			theWindow.graphicsPortGC.setColor(lighterGray);			theWindow.graphicsPortGC.fillRect(xPos-1,4,13,11);			if (value == 0)				theWindow.graphicsPortGC.drawImage(theWindow.theDesktop.closeImage,xPos,yPos,null);			else				theWindow.graphicsPortGC.drawImage(theWindow.theDesktop.clickedImage,xPos,yPos,null);		}	}	public void SetControlValue(int theValue)	{		Graphics	g;				value = theValue;		Draw();		g = theWindow.theDesktop.getGraphics();		if (value == 0)		{			theWindow.theDesktop.offScreenImageGC.drawImage(theWindow.theDesktop.closeImage,xPos+theWindow.xPos,yPos+theWindow.yPos,null);			g.drawImage(theWindow.theDesktop.closeImage,xPos+theWindow.xPos,yPos+theWindow.yPos,null);		}		else		{			theWindow.theDesktop.offScreenImageGC.drawImage(theWindow.theDesktop.clickedImage,xPos+theWindow.xPos,yPos+theWindow.yPos,null);			g.drawImage(theWindow.theDesktop.clickedImage,xPos+theWindow.xPos,yPos+theWindow.yPos,null);		}	}}