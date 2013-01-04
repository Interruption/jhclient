/*
 *  This file is part of the Haven & Hearth game client.
 *  Copyright (C) 2009 Fredrik Tolf <fredrik@dolda2000.com>, and
 *                     BjГ¶rn Johannessen <johannessen.bjorn@gmail.com>
 *
 *  Redistribution and/or modification of this file is subject to the
 *  terms of the GNU Lesser General Public License, version 3, as
 *  published by the Free Software Foundation.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  Other parts of this source tree adhere to other copying
 *  rights. Please see the file `COPYING' in the root directory of the
 *  source tree for details.
 *
 *  A copy the GNU Lesser General Public License is distributed along
 *  with the source tree of which this file is a part in the file
 *  `doc/LPGL-3'. If it is missing for any reason, please see the Free
 *  Software Foundation's website at <http://www.fsf.org/>, or write
 *  to the Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 *  Boston, MA 02111-1307 USA
 */

package haven;

import java.util.*;
import java.awt.font.TextAttribute;

public class OptWnd extends Window {
    public static final RichText.Foundry foundry = new RichText.Foundry(TextAttribute.FAMILY, "SansSerif", TextAttribute.SIZE, 10);
    private Tabs body;
    private String curcam;
    private Map<String, CamInfo> caminfomap = new HashMap<String, CamInfo>();
    private Map<String, String> camname2type = new HashMap<String, String>();
    private Map<String, String[]> camargs = new HashMap<String, String[]>();
    String[][] checkboxesList = {
		{"Bushes1 F4",         "gfx/tiles/wald"},
		{"Bushes2 F4",         "gfx/tiles/dwald"},
		{"Trees F5",           "gfx/terobjs/trees"},
		{"Stones F6",          "gfx/terobjs/bumlings"},
		{"Walls F7",           "gfx/arch/walls"},
		{"Gates F7",           "gfx/arch/gates"},
		{"Stone houses F8",    "gfx/arch/inn"},
		{"Wood houses F8",     "gfx/arch/cabin"},
		{"Plants F9",          "gfx/terobjs/plants"}
    };
	static String[][] cblist_kritter = {
		{"Aurochs",				"gfx/kritter/aurochs/neg"},
		{"Bear",				"gfx/kritter/bear/neg"},
		{"Boar",				"gfx/kritter/boar/neg"},
		{"Cow",					"gfx/kritter/cow/neg"},
		{"Deer",				"gfx/kritter/deer/neg"},
		{"Dragonfly",			"gfx/kritter/dragonfly/neg"},
		{"Dryad",				"gfx/kritter/dryad/neg"},
		{"Fox",					"gfx/kritter/fox/neg"},
		{"Frog",				"gfx/kritter/frog/neg"},
		{"Hare",				"gfx/kritter/hare/neg"},
		{"Hen",					"gfx/kritter/hen/neg"},
		{"Lady Bug",			"gfx/kritter/ladybug/neg"},
		{"Mufflon",				"gfx/kritter/mufflon/neg"},
		{"Pig",					"gfx/kritter/pig/neg"},
		{"Rat",					"gfx/kritter/rat/neg"},
		{"Sheep",				"gfx/kritter/sheep/neg"},
		{"Silkmoth",			"gfx/kritter/moth/neg"},
		{"Troll",				"gfx/kritter/troll/neg"},
    };
	static String[][] cblist_dkritter = {
		{"Aurochs (dead)",			"gfx/kritter/aurochs/cdv"},
		{"Bear (dead)",				"gfx/kritter/bear/cdv"},
		{"Boar (dead)",				"gfx/kritter/boar/cdv"},
		{"Cow (dead)",				"gfx/kritter/cow/cdv"},
		{"Deer (dead)",				"gfx/kritter/deer/cdv"},
		{"Fox (dead)",				"gfx/kritter/fox/cdv"},
		{"Hare (dead)",				"gfx/kritter/hare/cdv"},
		{"Hen (dead)",				"gfx/kritter/hen/cdv"},
		{"Mufflon (dead)",			"gfx/kritter/mufflon/cdv"},
		{"Pig (dead)",				"gfx/kritter/pig/cdv"},
		{"Sheep (dead)",			"gfx/kritter/sheep/cdv"},
		{"Troll (dead)",			"gfx/kritter/troll/cdv"}
    };
	static String[][] cblist_herbs = {		
		{"Bloated Bolete",			"gfx/terobjs/herbs/bloatedbolete"},
		{"Blood Stern",				"gfx/terobjs/herbs/bloodstern"},
		{"Blueberry",				"gfx/terobjs/herbs/blueberry"},
		{"Candleberry",				"gfx/terobjs/herbs/candleberry"},
		{"Cavebulb",				"gfx/terobjs/herbs/cavebulb"},
		{"Chantrelle",				"gfx/terobjs/herbs/chantrelle"},
		{"Chiming Bluebell",		"gfx/terobjs/herbs/chimingbluebell"},
		{"Dandelion",				"gfx/terobjs/herbs/dandelion"},
		{"Edelweiss",				"gfx/terobjs/herbs/edelweiss"},
		{"Feldspar",				"gfx/terobjs/herbs/feldspar"},
		{"Flotsam",					"gfx/terobjs/herbs/flotsam"},
		{"Four-Leaf Clover",		"gfx/terobjs/herbs/fourleafclover"},
		{"Frogscrown",				"gfx/terobjs/herbs/frogscrown"},
		{"Glimmermoss",				"gfx/terobjs/herbs/glimmermoss"},
		{"Inkweed",					"gfx/terobjs/herbs/inkweed"},
		{"Lady's Mantle",			"gfx/terobjs/herbs/ladysmantle"},
		{"Nettle",					"gfx/terobjs/herbs/nettle"},
		{"Royal Toadstool",			"gfx/terobjs/herbs/royaltoadstool"},
		{"Rustroot",				"gfx/terobjs/herbs/rustroot"},
		{"Spindly Taproot",			"gfx/terobjs/herbs/spindlytaproot"},
		{"Stalagoom",				"gfx/terobjs/herbs/stalagoom"},
		{"Stinging Nettle",			"gfx/terobjs/herbs/stingingnettle"},
		{"Tangled Bramble",			"gfx/terobjs/herbs/tangledbramble"},
		{"Thorny Thistle",			"gfx/terobjs/herbs/thornythistle"},
		{"Uncommon Snapdragon",		"gfx/terobjs/herbs/uncommonsnapdragon"},
		{"Washed-up Bladderwrack",	"gfx/terobjs/herbs/washedupbladderwrack"},
		{"Windweed",				"gfx/terobjs/herbs/windweed"}		
    };
	static String[][] cblist_other = {
		{"Ant Hill",				"gfx/terobjs/anthill"},
		{"Boat",					"gfx/kritter/boat"},
		{"Cave entrance",			"gfx/terobjs/ridges/cavein"},
		{"GEO-Water",				"gfx/terobjs/geo-water"},
		{"Gray Clay",				"gfx/terobjs/herbs/grayclay"},
		{"Hearth",					"gfx/terobjs/hearth"},
		{"Out of the Cave",			"gfx/terobjs/ridges/caveout"},
		{"Player",					"gfx/borka/neg"},
		{"Skeleton",				"gfx/terobjs/skeleton"},
		{"Log",						"gfx/terobjs/trees/log"},
		{"Well",					"gfx/terobjs/well"}
    };
    private List<CheckBox> hide_checkboxes = new ArrayList<CheckBox>();
	private List<CheckBox> ltok_checkboxes = new ArrayList<CheckBox>();
	private List<CheckBox> ltodk_checkboxes = new ArrayList<CheckBox>();
	private List<CheckBox> ltoh_checkboxes = new ArrayList<CheckBox>();
	private List<CheckBox> ltoo_checkboxes = new ArrayList<CheckBox>();
    private Comparator<String> camcomp = new Comparator<String>() {

	public int compare(String a, String b) {
	    if(a.startsWith("The ")) a = a.substring(4);
	    if(b.startsWith("The ")) b = b.substring(4);
	    return(a.compareTo(b));
	}
    };

    private static class CamInfo {
	String name, desc;
	Tabs.Tab args;
	
	public CamInfo(String name, String desc, Tabs.Tab args) {
	    this.name = name;
	    this.desc = desc;
	    this.args = args;
	}
    }

    public OptWnd(Coord c, Widget parent) {
	super(c, new Coord(540, 420), parent, "Options");

	body = new Tabs(Coord.z, new Coord(540, 400), this) {
		public void changed(Tab from, Tab to) {
		    Utils.setpref("optwndtab", to.btn.text.text);
		    from.btn.c.y = 0;
		    to.btn.c.y = -2;
		}};
	Widget tab;
    ui.options_wnd = this;

	{ /* GENERAL TAB */
	    tab = body.new Tab(new Coord(0, 0), 60, "General");

	    new Button(new Coord(10, 40), 125, tab, "Quit") {
		public void click() {
		    HackThread.tg().interrupt();
		}};
	    new Button(new Coord(10, 70), 125, tab, "LogPrint out") {
		public void click() {
		    ui.sess.close();
		}};
	    new Button(new Coord(10, 100), 125, tab, "Toggle fullscreen") {
		public void click() {
		    if(ui.fsm != null) {
			if(ui.fsm.hasfs()) ui.fsm.setwnd();
			else               ui.fsm.setfs();
		    }
		}};

	    Widget editbox = new Frame(new Coord(450, 30), new Coord(90, 100), tab);
	    new Label(new Coord(20, 10), editbox, "Edit mode:");
	    RadioGroup editmode = new RadioGroup(editbox) {
		    public void changed(int btn, String lbl) {
			Utils.setpref("editmode", lbl.toLowerCase());
		    }};
	    editmode.add("Emacs", new Coord(10, 30));
	    editmode.add("PC",    new Coord(10, 60));
	    if(Utils.getpref("editmode", "pc").equals("emacs")) editmode.check("Emacs");
	    else                                                editmode.check("PC");

        CheckBox chk = new CheckBox(new Coord(10, 130), tab, "Toggle tracking ON when login") {
		public void changed(boolean val) {
		    Config.tracking = val;
            Config.saveOptions();
		}};
        chk.a = Config.tracking;

        chk = new CheckBox(new Coord(10, 160), tab, "Always show player nicks") {
		public void changed(boolean val) {
		    Config.always_show_nicks = val;
            Config.saveOptions();
		}};
        chk.a = Config.always_show_nicks;

        chk = new CheckBox(new Coord(10, 190), tab, "Show map grid") {
		public void changed(boolean val) {
		    Config.show_map_grid = val;
            Config.saveOptions();
		}};
        chk.a = Config.show_map_grid;

        chk = new CheckBox(new Coord(10, 220), tab, "Highlight objects by mouse") {
		public void changed(boolean val) {
		    Config.highlight_object_by_mouse = val;
            Config.saveOptions();
		}};
        chk.a = Config.highlight_object_by_mouse;

        chk = new CheckBox(new Coord(10, 250), tab, "Highlight hided objects") {
		public void changed(boolean val) {
		    Config.highlight_hided_objects = val;
            Config.saveOptions();
		}};	
        chk.a = Config.highlight_hided_objects;
        
        chk = new CheckBox(new Coord(10, 280), tab, "Gilbertus map dumper (need restart)") {
    		public void changed(boolean val) {
    		    Config.gilbertus_map_dump = val;
                Config.saveOptions();
    		}};
        chk.a = Config.gilbertus_map_dump;
		
		chk = new CheckBox(new Coord(10, 310), tab, "Show radius (don't work correct)") {
    		public void changed(boolean val) {
    		    Config.showRadius = val;
                Config.saveOptions();
    		}};
        chk.a = Config.showRadius;
		
		chk = new CheckBox(new Coord(10, 340), tab, "Enable LTO") {
    		public void changed(boolean val) {
    		    Config.enableLTO = val;
                Config.saveOptions();
    		}};
        chk.a = Config.enableLTO;
		
		chk = new CheckBox(new Coord(10, 370), tab, "Alternative name LTO") {
    		public void changed(boolean val) {
    		    Config.altnLTO = val;
                Config.saveOptions();
    		}};
        chk.a = Config.altnLTO;
            
	}

	{ /* CAMERA TAB */
	    curcam = Utils.getpref("defcam", "border");
	    tab = body.new Tab(new Coord(60, 0), 60, "Camera");

	    new Label(new Coord(10, 40), tab, "Camera type:");
	    final RichTextBox caminfo = new RichTextBox(new Coord(330, 70), new Coord(210, 180), tab, "", foundry);
	    caminfo.bg = new java.awt.Color(0, 0, 0, 64);
	    String dragcam = "\n\n$col[225,200,100,255]{You can drag and recenter with the middle mouse button.}";
	    String fscam = "\n\n$col[225,200,100,255]{Should be used in full-screen mode.}";
	    addinfo("orig",       "The Original",  "The camera centers where you left-click.", null);
	    addinfo("predict",    "The Predictor", "The camera tries to predict where your character is heading - Г  la Super Mario World - and moves ahead of your character. Works unlike a charm." + dragcam, null);
	    addinfo("border",     "Freestyle",     "You can move around freely within the larger area of the window; the camera only moves along to ensure the character does not reach the edge of the window. Boom chakalak!" + dragcam, null);
	    addinfo("fixed",      "The Fixator",   "The camera is fixed, relative to your character." + dragcam, null);
	    addinfo("kingsquest", "King's Quest",  "The camera is static until your character comes close enough to the edge of the screen, at which point the camera snaps around the edge.", null);
	    addinfo("cake",       "Pan-O-Rama",    "The camera centers at the point between your character and the mouse cursor. It's pantastic!", null);

	    final Tabs cambox = new Tabs(new Coord(100, 60), new Coord(300, 200), tab);
	    Tabs.Tab ctab;
	    /* clicktgt arg */
	    ctab = cambox.new Tab();
	    new Label(new Coord(45, 10),  ctab, "Fast");
	    new Label(new Coord(45, 180), ctab, "Slow");
	    new Scrollbar(new Coord(60, 20), 160, ctab, 0, 20) {
		    {
			val = Integer.parseInt(Utils.getpref("clicktgtarg1", "10"));
			setcamargs("clicktgt", calcarg());
		    }
		public boolean mouseup(Coord c, int button) {
		    if(super.mouseup(c, button)) {
			setcamargs(curcam, calcarg());
			setcamera(curcam);
			Utils.setpref("clicktgtarg1", String.valueOf(val));
			return(true);
		    }
		    return(false);
		}
		private String calcarg() {
		    return(String.valueOf(Math.cbrt(Math.cbrt(val / 24.0))));
		}};
	    addinfo("clicktgt", "The Target Seeker", "The camera recenters smoothly where you left-click." + dragcam, ctab);
	    /* fixedcake arg */
	    ctab = cambox.new Tab();
	    new Label(new Coord(45, 10),  ctab, "Fast");
	    new Label(new Coord(45, 180), ctab, "Slow");
	    new Scrollbar(new Coord(60, 20), 160, ctab, 0, 20) {
		    {
			val = Integer.parseInt(Utils.getpref("fixedcakearg1", "10"));
			setcamargs("fixedcake", calcarg());
		    }
		public boolean mouseup(Coord c, int button) {
		    if(super.mouseup(c, button)) {
			setcamargs(curcam, calcarg());
			setcamera(curcam);
			Utils.setpref("fixedcakearg1", String.valueOf(val));
			return(true);
		    }
		    return(false);
		}
		private String calcarg() {
		    return(String.valueOf(Math.pow(1 - (val / 20.0), 2)));
		}};
	    addinfo("fixedcake", "The Borderizer", "The camera is fixed, relative to your character unless you touch one of the screen's edges with the mouse, in which case the camera peeks in that direction." + dragcam + fscam, ctab);

	    final RadioGroup cameras = new RadioGroup(tab) {
		    public void changed(int btn, String lbl) {
			if(camname2type.containsKey(lbl))
			    lbl = camname2type.get(lbl);
			if(!lbl.equals(curcam)) {
			    if(camargs.containsKey(lbl))
				setcamargs(lbl, camargs.get(lbl));
			    setcamera(lbl);
			}
			CamInfo inf = caminfomap.get(lbl);
			if(inf == null) {
			    cambox.showtab(null);
			    caminfo.settext("");
			} else {
			    cambox.showtab(inf.args);
			    caminfo.settext(String.format("$size[12]{%s}\n\n$col[200,175,150,255]{%s}", inf.name, inf.desc));
			}
		    }};
	    List<String> clist = new ArrayList<String>();
	    for(String camtype : MapView.camtypes.keySet())
		clist.add(caminfomap.containsKey(camtype) ? caminfomap.get(camtype).name : camtype);
	    Collections.sort(clist, camcomp);
	    int y = 35;
	    for(String camname : clist)
		cameras.add(camname, new Coord(10, y += 25));
	    cameras.check(caminfomap.containsKey(curcam) ? caminfomap.get(curcam).name : curcam);
	}

	{ /* AUDIO TAB */
	    tab = body.new Tab(new Coord(120, 0), 60, "Audio");

	    new Label(new Coord(10, 40), tab, "Sound volume:");
	    new Frame(new Coord(10, 65), new Coord(20, 206), tab);
	    final Label sfxvol = new Label(new Coord(35, 69 + (int)(getsfxvol() * 1.86)),  tab, String.valueOf(100 - getsfxvol()) + " %");
	    new Scrollbar(new Coord(25, 70), 196, tab, 0, 100) {{ val = getsfxvol(); }
		public void changed() {
		    Audio.setvolume((100 - val) / 100.0);
		    sfxvol.c.y = 69 + (int)(val * 1.86);
		    sfxvol.settext(String.valueOf(100 - val) + " %");
		}
		public boolean mousewheel(Coord c, int amount) {
		    val = Utils.clip(val + amount, min, max);
		    changed();
		    return(true);
		}};
	    new CheckBox(new Coord(10, 280), tab, "Music enabled") {
		public void changed(boolean val) {
		    Music.enable(val);
		}};
    }
	{ /* HIDE OBJECTS TAB */
	    tab = body.new Tab(new Coord(180, 0), 80, "Hide Objects");

        int y = 0;
        for (final String[] checkbox : checkboxesList) {
            CheckBox chkbox = new CheckBox(new Coord(10, y += 30), tab, checkbox[0]) {

                public void changed(boolean val) {
                    if (val) {
                        Config.hideObjectList.add(checkbox[1]);
                    } else {
                        Config.hideObjectList.remove(checkbox[1]);
                    }
                    Config.saveOptions();
                }
            };
            hide_checkboxes.add(chkbox);
        }
        UpdateHideCheckBoxes();
	}
	{ /* TARGET LINE TO KRITTER OBJECT TAB */
	    tab = body.new Tab(new Coord(260, 0), 45, "LTO K");
		new Label(new Coord(10, 27), tab, "Kritter:");
        int y = 25;
		int x = 10;
        for (final String[] checkbox : cblist_kritter) {
            CheckBox chkbox;
			if (y > 360) {
				x = 130;
				y = 25;
			}
			chkbox = new CheckBox(new Coord(x, y += 25), tab, checkbox[0]) {
				public void changed(boolean val) {
					if (val) {
						Config.ltObjectList.add(checkbox[1]);
					} else {
						Config.ltObjectList.remove(checkbox[1]);
					}
				}
			};
            ltok_checkboxes.add(chkbox);
        }
		UpdateLTOCheckBoxes(cblist_kritter, ltok_checkboxes);
		x = 255;
		y = 25;
		for (final String[] checkbox : cblist_dkritter) {
            CheckBox chkbox;
			if (y > 360) {
				x = 380;
				y = 25;
			}
			chkbox = new CheckBox(new Coord(x, y += 25), tab, checkbox[0]) {
				public void changed(boolean val) {
					if (val) {
						Config.ltObjectList.add(checkbox[1]);
					} else {
						Config.ltObjectList.remove(checkbox[1]);
					}
				}
			};
            ltodk_checkboxes.add(chkbox);
        }
        UpdateLTOCheckBoxes(cblist_dkritter, ltodk_checkboxes);
	}
	{ /* TARGET LINE TO HERBS OBJECT TAB */
	    tab = body.new Tab(new Coord(305, 0), 45, "LTO H");
		new Label(new Coord(10, 27), tab, "Herbs:");
        int y = 25;
		int x = 10;
        for (final String[] checkbox : cblist_herbs) {
            CheckBox chkbox;
			if (y > 360) {
				if (x == 10) {x = 130;} else {x += 125;}
				y = 25;
			}
			chkbox = new CheckBox(new Coord(x, y += 25), tab, checkbox[0]) {
				public void changed(boolean val) {
					if (val) {
						Config.ltObjectList.add(checkbox[1]);
					} else {
						Config.ltObjectList.remove(checkbox[1]);
					}
				}
			};
            ltoh_checkboxes.add(chkbox);
        }
        UpdateLTOCheckBoxes(cblist_herbs, ltoh_checkboxes);
	}
	{ /* TARGET LINE TO OTHER OBJECT TAB */
	    tab = body.new Tab(new Coord(350, 0), 45, "LTO O");
		new Label(new Coord(10, 27), tab, "Other:");
        int y = 25;
		int x = 10;
        for (final String[] checkbox : cblist_other) {
            CheckBox chkbox;
			if (y > 360) {
				if (x == 10) {x = 130;} else {x += 125;}
				y = 25;
			}
			chkbox = new CheckBox(new Coord(x, y += 25), tab, checkbox[0]) {
				public void changed(boolean val) {
					if (val) {
						Config.ltObjectList.add(checkbox[1]);
					} else {
						Config.ltObjectList.remove(checkbox[1]);
					}
				}
			};
            ltoo_checkboxes.add(chkbox);
        }
        UpdateLTOCheckBoxes(cblist_other, ltoo_checkboxes);
	}
    //------------------------------------------------------------------------------------------------------------------

    new Frame(new Coord(-10, 20), new Coord(560, 410), this);
	String last = Utils.getpref("optwndtab", "");
	for(Tabs.Tab t : body.tabs) {
	    if(t.btn.text.text.equals(last))
		body.showtab(t);
	}
    }

	
	public void UpdateLTOCheckBoxes(String [][] strlist, List<CheckBox> cbl) {
        int i = 0;    
        for (final String[] checkbox : strlist) {
            CheckBox chkbox = cbl.get(i);
            chkbox.a = Config.ltObjectList.contains(checkbox[1]);
            i++;
        }
    }
	
    public void UpdateHideCheckBoxes() {
        int i = 0;    
        for (final String[] checkbox : checkboxesList) {
            CheckBox chkbox = hide_checkboxes.get(i);
            chkbox.a = Config.hideObjectList.contains(checkbox[1]);
            i++;
        }
    }

    public static void ToggleHide(String hide_name) {
        int i = 0;
        CheckBox chk = null;
        if (ark_bot.ui.options_wnd != null) {
            for (final String[] checkbox : ark_bot.ui.options_wnd.checkboxesList) {
                if (checkbox[1].equals(hide_name)) {
                    chk = ark_bot.ui.options_wnd.hide_checkboxes.get(i);
                }
                i++;
            }
        }
        if (Config.hideObjectList.contains(hide_name)) {
            Config.hideObjectList.remove(hide_name);
            if (chk != null)
                chk.a = false;
        } else {
            Config.hideObjectList.add(hide_name);
            if (chk != null)
                chk.a = true;
        }
    }

    private void setcamera(String camtype) {
	curcam = camtype;
	Utils.setpref("defcam", curcam);
	String[] args = camargs.get(curcam);
	if(args == null) args = new String[0];

	MapView mv = ui.root.findchild(MapView.class);
	if(mv != null) {
	    if     (curcam.equals("clicktgt"))   mv.cam = new MapView.OrigCam2(args);
	    else if(curcam.equals("fixedcake"))  mv.cam = new MapView.FixedCakeCam(args);
	    else {
		try {
		    mv.cam = MapView.camtypes.get(curcam).newInstance();
		} catch(InstantiationException e) {
		} catch(IllegalAccessException e) {}
	    }
	}
    }

    private void setcamargs(String camtype, String... args) {
	camargs.put(camtype, args);
	if(args.length > 0 && curcam.equals(camtype))
	    Utils.setprefb("camargs2", Utils.serialize(args));
    }

    private int getsfxvol() {
	return((int)(100 - Double.parseDouble(Utils.getpref("sfxvol", "1.0")) * 100));
    }

    private void addinfo(String camtype, String title, String text, Tabs.Tab args) {
	caminfomap.put(camtype, new CamInfo(title, text, args));
	camname2type.put(title, camtype);
    }

    public void wdgmsg(Widget sender, String msg, Object... args) {
	if(sender == cbtn)
	    super.wdgmsg(sender, msg, args);
    }

    public class Frame extends Widget {
	private IBox box;

	public Frame(Coord c, Coord sz, Widget parent) {
	    super(c, sz, parent);
	    box = new IBox("gfx/hud", "tl", "tr", "bl", "br", "extvl", "extvr", "extht", "exthb");
	}

	public void draw(GOut og) {
	    super.draw(og);
	    GOut g = og.reclip(Coord.z, sz);
	    g.chcolor(150, 200, 125, 255);
	    box.draw(g, Coord.z, sz);
	}
    }
}
