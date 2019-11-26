import java.awt.*;import java.awt.image.*;import java.applet.*;import java.util.*;public class NewRadioButton extends Control{	private static	Font	helvFont = new Font("Helvetica", Font.BOLD, 12);	private			int		TitleWidth, TitleHeight;		public NewRadioButton()	{		super();	}		protected void SetSize()	{		FontMetrics	fm;		int			minWidth,minHeight;				theWindow.graphicsPortGC.setFont(helvFont);		fm = theWindow.graphicsPortGC.getFontMetrics(theWindow.graphicsPortGC.getFont());		TitleWidth = fm.stringWidth(Title);		minWidth = TitleWidth + 17;		TitleHeight = fm.getMaxAscent() + fm.getMaxDescent();		minHeight = TitleHeight;		if (width < minWidth) width = minWidth;		if (height < minHeight) height = minHeight;	}		public void SetControlValue(int theValue)	{		Enumeration	e;		Control		TempControl;				if (theValue != 0 & value == 0)		{			e = theWindow.theControls.elements();			while (e.hasMoreElements())			{				TempControl = (Control)e.nextElement();				if (TempControl instanceof NewRadioButton)				{					if (TempControl.maxValue == this.maxValue & TempControl != this)					{						TempControl.value=0;						TempControl.DrawIt();					}				}			}			value = theValue;			Draw();		}		else		{			if (theValue == 0 & value != 0)			{				value = theValue;				Draw();			}		}	}		protected void DrawIt()	{		if (hiliteState == 255) theWindow.graphicsPortGC.setColor(Color.black);		else theWindow.graphicsPortGC.setColor(Color.gray);		theWindow.graphicsPortGC.setFont(helvFont);		theWindow.graphicsPortGC.drawOval(xPos,yPos+1,11,11);		theWindow.graphicsPortGC.drawString(Title, xPos+17, yPos+11);				if (value != 0)		{			theWindow.graphicsPortGC.fillOval(xPos+3,yPos+4,6,6);		}		else		{			theWindow.graphicsPortGC.setColor(Color.white);			theWindow.graphicsPortGC.fillOval(xPos+3,yPos+4,6,6);		}	}		public int TrackControl(Point location)	{		int		x,y;		boolean	pushed;				pushed = false;				do		{			x = location.x;			y = location.y;			if (x >= xPos & x < xPos+width & y >= yPos & y < yPos+height)			{				if (!pushed)				{					push();					pushed = true;				}			}			else			{				if (pushed)				{					pull();					pushed = false;				}			} 			location.x = theWindow.AFC.xMousePos;			location.y = theWindow.AFC.yMousePos;			location = theWindow.GlobalToLocal(location);		}		while (!theWindow.AFC.WaitMouseUp());				if (pushed)		{			pull();			return 11;		}		else return 0;	}	public void push()	{		theWindow.graphicsPortGC.setColor(Color.black);			theWindow.graphicsPortGC.drawOval(xPos+1,yPos+2,9,9);		theWindow.UpdateGraphicsPort();	}	public void pull()	{		theWindow.graphicsPortGC.setColor(Color.white);			theWindow.graphicsPortGC.drawOval(xPos+1,yPos+2,9,9);		theWindow.UpdateGraphicsPort();	}}	