/*
 *  This file is part of the Haven & Hearth game client.
 *  Copyright (C) 2009 Fredrik Tolf <fredrik@dolda2000.com>, and
 *                     Björn Johannessen <johannessen.bjorn@gmail.com>
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

import java.awt.Color;

public class CheckBox extends Widget {
    static Tex box, mark;
    public boolean a = false;
//	public static final Color cunchk = new Color(0,255,50);
//	public static final Color cchk = new Color(20,20,20);
    Text lbl;
	
    static {
	Widget.addtype("chk", new WidgetFactory() {
		public Widget create(Coord c, Widget parent, Object[] args) {
		    return(new CheckBox(c, parent, (String)args[0]));
		}
	    });
	box = Resource.loadtex("gfx/hud/chkboxi");
	box.sz().x = 19;
	mark = Resource.loadtex("gfx/hud/chkmarki");
	mark.sz().x = 19;
    }
	
    public CheckBox(Coord c, Widget parent, String lbl) {
	super(c, box.sz(), parent);
	this.lbl = Text.std.render(lbl, java.awt.Color.WHITE);
	sz = box.sz().add(this.lbl.sz());
    }
	
	public CheckBox(Coord c, Widget parent, String lbl, int size_x, int size_y) {
	super(c, box.sz(), parent);
	this.lbl = Text.std.render(lbl, java.awt.Color.WHITE);
	sz = box.sz().add(this.lbl.sz());
	if(size_x != -1)
			sz.x = size_x;
	if(size_y != -1)
			sz.y = size_y;
    }
	
    public boolean mousedown(Coord c, int button) {
	if(button != 1)
	    return(false);
	a = !a;
	changed(a);
	return(true);
    }

    public void draw(GOut g) {
	g.image(lbl.tex(), new Coord(box.sz().x, box.sz().y - lbl.sz().y));
//	g.chcolor(cchk);
	g.image(box, Coord.z);
	if(a) {
//		g.chcolor(cunchk);
	    g.image(mark, Coord.z);
	}
	super.draw(g);
    }
    
    public void changed(boolean val) {
	wdgmsg("ch", a);
    }
}
