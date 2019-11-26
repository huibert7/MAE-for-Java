import java.awt.*;import java.awt.image.*;import java.applet.*;import java.util.*;public class MovableModalWindow extends NewWindow{	protected	Image					titlePort;	protected	Graphics				titlePortGC;	public	MovableModalWindow()	{		super();		highlighted = true;		drag = true;		zoombox = false;		modal = true;	}		protected void CreatePorts(int theWidth,int theHeight)	{		graphicsPort = theApplet.createImage(theWidth-16,theHeight-31);		graphicsPortGC = graphicsPort.getGraphics();		graphicsPortGC.setFont(helvFont);		titlePort = theApplet.createImage(theWidth,18);		titlePortGC = titlePort.getGraphics();		titlePortGC.setFont(helvFont);	}	public void Redraw()	{		if (visible)		{			if (highlighted)			{				theDesktop.offScreenImageGC.setColor(lightGray);				theDesktop.offScreenImageGC.drawRect(xPos+2,yPos+18,width-5,height-20);				theDesktop.offScreenImageGC.setColor(darkBlue);				theDesktop.offScreenImageGC.drawLine(xPos+3,yPos+19,xPos+width-4,yPos+19);				theDesktop.offScreenImageGC.drawLine(xPos+3,yPos+20,xPos+3,yPos+height-3);				theDesktop.offScreenImageGC.drawLine(xPos+1,yPos+height-1,xPos+width-2,yPos+height-1);				theDesktop.offScreenImageGC.drawLine(xPos+width-2,yPos+17,xPos+width-2,yPos+height-2);				theDesktop.offScreenImageGC.setColor(lightBlue);				theDesktop.offScreenImageGC.drawLine(xPos+1,yPos+17,xPos+width-3,yPos+17);				theDesktop.offScreenImageGC.drawLine(xPos+1,yPos+18,xPos+1,yPos+height-2);				theDesktop.offScreenImageGC.drawLine(xPos+4,yPos+height-3,xPos+width-4,yPos+height-3);				theDesktop.offScreenImageGC.drawLine(xPos+width-4,yPos+20,xPos+width-4,yPos+height-4);				theDesktop.offScreenImageGC.setColor(Color.black);				theDesktop.offScreenImageGC.drawRect(xPos,yPos+16,width-1,height-16);				theDesktop.offScreenImageGC.drawRect(xPos+4,yPos+20,width-9,height-24);			}			else			{				theDesktop.offScreenImageGC.setColor(midGray);				theDesktop.offScreenImageGC.drawRect(xPos+3,yPos+19,width-7,height-22);				theDesktop.offScreenImageGC.drawRect(xPos+4,yPos+20,width-9,height-24);				theDesktop.offScreenImageGC.setColor(borderGray);				theDesktop.offScreenImageGC.drawRect(xPos,yPos+16,width-1,height-16);				theDesktop.offScreenImageGC.setColor(Color.white);				theDesktop.offScreenImageGC.drawRect(xPos+1,yPos+17,width-3,height-18);				theDesktop.offScreenImageGC.drawRect(xPos+2,yPos+18,width-5,height-20);			}			theDesktop.offScreenImageGC.drawImage(titlePort,xPos,yPos,null);			theDesktop.offScreenImageGC.setColor(Color.white);			theDesktop.offScreenImageGC.fillRect(xPos+5,yPos+21,width-10,height-25);			theDesktop.offScreenImageGC.drawImage(graphicsPort,xPos+8,yPos+24,null);			if (highlighted) theDesktop.offScreenImageGC.setColor(Color.black);			else theDesktop.offScreenImageGC.setColor(borderGray);			theDesktop.offScreenImageGC.drawRect(xPos,yPos,width-1,height);		}	}		protected void DrawGraphicsPort()	{		theDesktop.offScreenImageGC.drawImage(graphicsPort,xPos+8,yPos+24,null);	}		protected void DrawFrame()	{		DrawTitle();	}		protected void DrawTitle()	{		if (highlighted)		{			titlePortGC.setColor(lighterGray);			titlePortGC.fillRect(0,0,width-1,18);			titlePortGC.setColor(lightBlue);			titlePortGC.drawLine(1,1,width-3,1);			titlePortGC.drawLine(1,2,1,16);			titlePortGC.setColor(midBlue);			titlePortGC.drawLine(1,17,width-2,17);			titlePortGC.drawLine(width-2,1,width-2,16);			titlePortGC.setColor(midGray);					for (int i=0; i<6; i++)titlePortGC.drawLine(2,4+(2*i),width-3,4+(2*i));			titlePortGC.setColor(Color.black);			if (!title.equals(""))			{				titlePortGC.setColor(lighterGray);				titlePortGC.fillRect((width/2)-(titleWidth/2),4,titleWidth,13);				titlePortGC.setColor(Color.black);				titlePortGC.drawString(title,(width/2)-(titleWidth/2)+5,14);			}		}		else		{			titlePortGC.setColor(Color.white);			titlePortGC.fillRect(0,0,width-1,18);			titlePortGC.setColor(borderGray);						if (!title.equals(""))			{				titlePortGC.setColor(textGray);				titlePortGC.drawString(title,(width/2)-(titleWidth/2)+5,14);			}		}	}		public void Init()	{	}	public void DrawGrowIcon()	{	}	public void Draw()	{	}	public void Activate()	{		theApplet.theMenuBar.DisableMenuBar();		userActivate();	}		public void userActivate()	{	}		public void Deactivate()	{		Enumeration	e;		Control		TempControl;				e = theControls.elements();		while (e.hasMoreElements())		{			TempControl = (Control)e.nextElement();			TempControl.hiliteState = 0;			TempControl.Draw();		}		userDeactivate();	}		public void userDeactivate()	{	}		public void Close()	{		theApplet.theMenuBar.EnableMenuBar();	}	public Point GlobalToLocal(Point thePoint)	{		return new Point(thePoint.x - xPos - 8, thePoint.y - yPos - 24);	}	protected void DisposePorts()	{		graphicsPort.flush();		graphicsPort = null;		graphicsPortGC.dispose();		graphicsPortGC = null;		titlePort.flush();		titlePort = null;		titlePortGC.dispose();		titlePortGC = null;	}}