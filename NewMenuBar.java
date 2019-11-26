import java.awt.*;import java.awt.image.*;import java.applet.*;import java.util.*;public class NewMenuBar{	private Vector   myVector = new Vector();	public  Vector	 onScreenMenus = new Vector();	private Image    offscreenMenu;	private Graphics offscreenMenuGC;	private int		 MenuBarWidth,MenuBarHeight;    private int		 charPos[] = new int[255];	private int		 charWidth[] = new int[255];	private	boolean	 AnimationWasDisabled;	private boolean	 userIsSelectingMenu;		private MAE		   	applet;	private Desktop  	theDesktop;	private Application	theApplication;		public NewMenuBar(MAE app, Desktop Desk)	{		applet = app;		theApplication = app.ApplicationThread;		MenuBarWidth = app.size().width;		MenuBarHeight = 21;				Desk.MenuBar = this;		theDesktop = Desk;				offscreenMenu = app.createImage(MenuBarWidth,MenuBarHeight);		offscreenMenuGC = offscreenMenu.getGraphics();		offscreenMenuGC.setColor(Color.white);		offscreenMenuGC.fillRect(0,0,MenuBarWidth,MenuBarHeight);				userIsSelectingMenu = false;				DrawMenu();	}	public void DrawMenu()	{		int	        Position;		NewMenu     Temp;		Graphics	g;					offscreenMenuGC.setColor(Color.white);		offscreenMenuGC.fillRect(0,0,MenuBarWidth-1,MenuBarHeight-1);		Position=15;		for (Enumeration e = myVector.elements(); e.hasMoreElements();)		{			Temp = (NewMenu)e.nextElement();			Temp.MenuName.Draw(Position, 15, offscreenMenuGC, Temp.Disabled);			Position = Position+Temp.MenuName.Width()+13;		}		offscreenMenuGC.setColor(Color.black);		offscreenMenuGC.drawRect(0,0,MenuBarWidth-1,MenuBarHeight-1);		Redraw();  		g = theDesktop.getGraphics();		g.drawImage(offscreenMenu,0,0,null);	}		public void Redraw()	{		theDesktop.offScreenImageGC.drawImage(offscreenMenu,0,0,null);	}		public int MenuSelect(int x, int y)	{		int      		Position,i,j,k,y2,xPos,yPos,MenuID,MenuIt;		boolean			Test;		NewMenu  		Temp,Temp3;		NewMItem		Temp2;		Enumeration		e,f;		long     		millis;				userIsSelectingMenu = true;		Position = 0;		e = myVector.elements();		while (e.hasMoreElements())		{			Temp = (NewMenu)e.nextElement();			if (x>=Temp.Position & x<Temp.Position+Temp.MenuTitleWidth)			{				Temp.Hilite();				Temp.Draw(Temp.Position,MenuBarHeight-1);				this.onScreenMenus.addElement(Temp);				break; // Was return;			}		}		loop:	while (!theApplication.AFC.WaitMouseUp())		{			if (x != theApplication.AFC.xMousePos | y != theApplication.AFC.yMousePos)			{				x = theApplication.AFC.xMousePos;				y = theApplication.AFC.yMousePos;				if (y<MenuBarHeight & y>=0)				{					e = myVector.elements();			// if we are in the menu bar, make sure that no					while (e.hasMoreElements())			// MItem is still highlighted & only one menu is					{									// displayed						Temp = (NewMenu)e.nextElement();						f = Temp.myVector.elements();						while (f.hasMoreElements())						{							i = 0;							Temp2 = (NewMItem)f.nextElement();							if (Temp2.Highlighted() & !Temp.Disabled &!Temp2.Disabled) Temp.HiliteMenuItem(i);							Temp2.Highlighted = false;							i++;						}						j = onScreenMenus.size();						while (j>1)						{							Temp3=(NewMenu)onScreenMenus.elementAt(j-1);							Temp3.RestoreBackground();							onScreenMenus.removeElementAt(j-1);							j--;						}					}					Test = false;				// Flag to monitor if any menu is selected								e = myVector.elements();					while (e.hasMoreElements())					{						Temp = (NewMenu)e.nextElement();						if (x>=Temp.Position & x<Temp.Position+Temp.MenuTitleWidth)						{							if (!Temp.highlighted)							{								i = onScreenMenus.size();							    while (i > 0)							    {							    	Temp3=(NewMenu)onScreenMenus.elementAt(i-1);							    	Temp3.RestoreBackground();							    	i--;							    }							    onScreenMenus.removeAllElements();								Temp.Hilite();								Temp.Draw(Temp.Position,MenuBarHeight-1);								this.onScreenMenus.addElement(Temp);							}							Test = true;						}						else						{							if (Temp.highlighted)							{								Temp.UnHilite();							}						}					}					// Make sure that no menu is drawn if no one is selected					if (!Test)					{						i = onScreenMenus.size();						while (i > 0)						{							Temp3=(NewMenu)onScreenMenus.elementAt(i-1);							Temp3.RestoreBackground();							i--;						}						onScreenMenus.removeAllElements();					}				}				else // follow the cursor in active menu				{					i = onScreenMenus.size();					if (i==0) continue loop; //return;					while (i > 0)					{						Temp = (NewMenu)onScreenMenus.elementAt(i-1);						if (x>Temp.xBackPos & x<Temp.xBackPos+Temp.MenuWidth & y>=Temp.yBackPos & y<Temp.yBackPos+Temp.MenuHeight-3)						{							if (i == onScreenMenus.size())							{								y2 = (y - Temp.yBackPos)/18;								if (!Temp.GetMItem(y2).Highlighted())								{									j = 0;									f = Temp.myVector.elements();									while (f.hasMoreElements())									{										Temp2 = (NewMItem)f.nextElement();										if (Temp2.Highlighted())										{											if (!Temp2.Disabled & !Temp.Disabled) Temp.HiliteMenuItem(j);											Temp2.Highlighted = false;										}										j++;									}																if (!Temp.GetMItem(y2).Disabled & !Temp.Disabled)									{										Temp.HiliteMenuItem(y2);										if (Temp.GetMItem(y2).AttachedMenu!=null) // Display hierarchical menu										{											xPos = Temp.xBackPos+Temp.MenuWidth-5;											if (xPos + Temp.GetMItem(y2).AttachedMenu.MenuWidth > theDesktop.DesktopWidth)												xPos = Temp.xBackPos+5 - Temp.GetMItem(y2).AttachedMenu.MenuWidth;											yPos = (y2*18)+Temp.yBackPos-1;											if (yPos + Temp.GetMItem(y2).AttachedMenu.MenuHeight > theDesktop.DesktopHeight)												yPos = theDesktop.DesktopHeight-3-Temp.GetMItem(y2).AttachedMenu.MenuHeight;											if (yPos < MenuBarHeight+3)												yPos = MenuBarHeight+3;											Temp.GetMItem(y2).AttachedMenu.Draw(xPos,yPos);											this.onScreenMenus.addElement(Temp.GetMItem(y2).AttachedMenu);										}									}									Temp.GetMItem(y2).Highlighted = true;								}								continue loop; // return;							}							else							{								y2 = (y - Temp.yBackPos)/18;								if (!Temp.GetMItem(y2).Highlighted())								{									j = onScreenMenus.size();									while (j>i)									{										Temp3=(NewMenu)onScreenMenus.elementAt(j-1);										Temp3.RestoreBackground();										onScreenMenus.removeElementAt(j-1);										j--;									}									j = 0;									f = Temp.myVector.elements();									while (f.hasMoreElements())									{										Temp2 = (NewMItem)f.nextElement();										if (Temp2.Highlighted())										{											if (!Temp2.Disabled & !Temp.Disabled) Temp.HiliteMenuItem(j);											Temp2.Highlighted = false;										}										j++;									}												if (!Temp.GetMItem(y2).Disabled & !Temp.Disabled)									{										Temp.HiliteMenuItem(y2);										if (Temp.GetMItem(y2).AttachedMenu!=null) // Display hierarchical menu										{											xPos = Temp.xBackPos+Temp.MenuWidth-5;											if (xPos + Temp.GetMItem(y2).AttachedMenu.MenuWidth > theDesktop.DesktopWidth)												xPos = Temp.xBackPos+5 - Temp.GetMItem(y2).AttachedMenu.MenuWidth;											yPos = (y2*18)+Temp.yBackPos-1;											if (yPos + Temp.GetMItem(y2).AttachedMenu.MenuHeight > theDesktop.DesktopHeight)												yPos = theDesktop.DesktopHeight-3-Temp.GetMItem(y2).AttachedMenu.MenuHeight;											if (yPos < MenuBarHeight+3)												yPos = MenuBarHeight+3;											Temp.GetMItem(y2).AttachedMenu.Draw(xPos,yPos);											this.onScreenMenus.addElement(Temp.GetMItem(y2).AttachedMenu);										}									}									Temp.GetMItem(y2).Highlighted = true;								}								else								{									// Keep next menu but disable all its MItems									j = onScreenMenus.size();									while (j>i+1)									{										Temp3=(NewMenu)onScreenMenus.elementAt(j-1);										Temp3.RestoreBackground();										onScreenMenus.removeElementAt(j-1);										j--;									}									j = onScreenMenus.size();									Temp3=(NewMenu)onScreenMenus.elementAt(j-1);									j=0;									f = Temp3.myVector.elements();									while (f.hasMoreElements())									{										Temp2 = (NewMItem)f.nextElement();										if (Temp2.Highlighted())										{											if (!Temp2.Disabled & !Temp3.Disabled) Temp3.HiliteMenuItem(j);											Temp2.Highlighted = false;										}										j++;									}								}							}							continue loop; // return;						}						else						{							i--;							if (i==0)							{								// Cursor is not on an active menu								i = onScreenMenus.size();								Temp=(NewMenu)onScreenMenus.elementAt(i-1);								if (!Temp.passedOver & i>=2)								{									Temp.RestoreBackground();									onScreenMenus.removeElementAt(i-1);									Temp=(NewMenu)onScreenMenus.elementAt(i-2);									j=0;									f = Temp.myVector.elements();									while (f.hasMoreElements())									{										Temp2 = (NewMItem)f.nextElement();										if (Temp2.Highlighted())										{											if (!Temp2.Disabled & !Temp.Disabled) Temp.HiliteMenuItem(j);											Temp2.Highlighted = false;										}										j++;									}								}								else								{									j=0;									f = Temp.myVector.elements();									while (f.hasMoreElements())									{										Temp2 = (NewMItem)f.nextElement();										if (Temp2.Highlighted())										{											if (!Temp2.Disabled & !Temp.Disabled) Temp.HiliteMenuItem(j);											Temp2.Highlighted = false;										}										j++;									}								}								continue loop; // was return;							}						} 					}				}			}		}			x = theApplication.AFC.xMousePos;		y = theApplication.AFC.yMousePos;				MenuID = -1;		MenuIt = -1;		i = onScreenMenus.size();		while (i > 0)		{			Temp = (NewMenu)onScreenMenus.elementAt(i-1);			if (x>Temp.xBackPos & x<Temp.xBackPos+Temp.MenuWidth & y>=Temp.yBackPos & y<Temp.yBackPos+Temp.MenuHeight-3)			{				if (i==onScreenMenus.size())				{	// Blink MenuItem					j = 0;					e = Temp.myVector.elements();					while (e.hasMoreElements())					{						Temp2 = (NewMItem)e.nextElement();						if (Temp2.Highlighted()  & !Temp.Disabled & !Temp2.Disabled)						{							for (k = 0; k<6; k++)							{								Temp.HiliteMenuItem(j);								millis = System.currentTimeMillis();								while (System.currentTimeMillis()-millis<100)								{								}							}							MenuID = Temp.MenuID;							MenuIt = j;						}						j++;					}				}			}			Temp.RestoreBackground();			i--;		}		onScreenMenus.removeAllElements();		userIsSelectingMenu = false;				if (MenuID == -1)		{	// Restore Menu Bar			e = myVector.elements();			while (e.hasMoreElements())			{				Temp = (NewMenu)e.nextElement();				if (Temp.highlighted) Temp.UnHilite();			}			return 0;		}		else return MenuID*65536+MenuIt;	}	public int MenuBarHeight()		{			return MenuBarHeight;		}			public NewMenu GetMenu(int Order)	{		return (NewMenu)myVector.elementAt(Order);	}	public void AddMenu(NewMenu theMenu)	{		NewMenu temp;					if (this.myVector.size()==0)		{			theMenu.Position = 7;		}		else		{			temp = (NewMenu)this.myVector.lastElement();			theMenu.Position = temp.Position+temp.MenuTitleWidth;		}		this.myVector.addElement(theMenu);		theMenu.setMenuBarInfo(offscreenMenu,offscreenMenuGC);		DrawMenu();	}		public void DisableMenuBar()	{		Enumeration	e;		NewMenu		Temp;				e = myVector.elements();		while (e.hasMoreElements())		{			Temp = (NewMenu)e.nextElement();			Temp.Disable();		}		DrawMenu();	}	public void EnableMenuBar()	{		Enumeration	e;		NewMenu		Temp;				e = myVector.elements();		while (e.hasMoreElements())		{			Temp = (NewMenu)e.nextElement();			Temp.Enable();		}		DrawMenu();	}		public void HiliteMenu(int ID)	{		Enumeration	e;		NewMenu		Temp;				e = myVector.elements();		while (e.hasMoreElements())		{			Temp = (NewMenu)e.nextElement();			if (Temp.MenuID==ID) Temp.Hilite();			else Temp.UnHilite();		}		DrawMenu();	}		public boolean MenuIsBeingSelected()	{		return userIsSelectingMenu;	}}