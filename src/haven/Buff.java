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

public class Buff {
    public static final Text.Foundry nfnd = new Text.Foundry("SansSerif", 10);
    int id;
    Indir<Resource> res;
    String tt = null;
    int ameter = -1;	// РїРѕР»РѕСЃРєР° РїРѕРґ Р±Р°С„РѕРј РѕС‚ 0 РґРѕ 100
    int nmeter = -1;	// РЅРµРєРёР№ СЃС‡РµС‚С‡РёРє С…Р·???
    int cmeter = -1;	// РїСЂРѕРіСЂРµСЃСЃ Р±Р°С„Р°. РєСЂСѓРіР»РµС€РѕРє РѕС‚ 0 РґРѕ 100
    int cticks = -1;	// РєРѕР»РёС‡РµСЃС‚РІРѕ С‚РёРєРѕРІ РґР»СЏ РїРѕР»РЅРѕРіРѕ РєСЂСѓРіР»РµС€РєР° РѕРїСЂРµРґРµР»СЏРµС‚ РІСЂРµРјСЏ Р·Р° РєРѕС‚РѕСЂРѕРµ СЃРїР°РґРµС‚ РєСЂСѓРіР»РµС€РѕРє. РЅРµРєРёР№ РєРµС€ РЅР° РєР»РёРµРЅС‚Рµ
    // С‡С‚РѕР±С‹ РїРѕСЃС‚РѕСЏРЅРЅРѕ РЅРµ РѕРїСЂР°С€РёРІР°С‚СЊ СЃРµСЂРІРµСЂ Рѕ РїСЂРѕРіСЂРµСЃСЃРµ РєСЂСѓРіР»РµС€РєР°
    long gettime;
    Tex ntext = null;
    boolean major = false;
    
    public Buff(int id, Indir<Resource> res) {
	this.id = id;
	this.res = res;
    }
    
    Tex nmeter() {
	if(ntext == null)
	    ntext = new TexI(Utils.outline2(nfnd.render(Integer.toString(nmeter), Color.WHITE).img, Color.BLACK));
	return(ntext);
    }
    
    // arksu 19.12.2010 lets rock it!
    // РїРѕР»СѓС‡РёС‚СЊ С‚СѓР» С‚РёРї РІ РІРёРґРµ С‚РµРєСЃС‚Р°
    public String GetName() {
    	Resource.Tooltip tt;
    	if((res.get() != null) && ((tt = res.get().layer(Resource.tooltip)) != null))
    		return tt.t;
    	else
    		return "";
    }
    
    // РїРѕР»СѓС‡РёС‚СЊ РІСЂРµРјСЏ РґРѕ РєРѕРЅС†Р° Р±Р°С„Р° РѕС‚ 0 РґРѕ 100
    public int GetTimeLeft() {
	    if(cmeter >= 0) {
	    	long now = System.currentTimeMillis();
	    	double m = cmeter / 100.0;
			if(cticks >= 0) {
			    double ot = cticks * 0.06;
			    double pt = ((double)(now - gettime)) / 1000.0;
			    m *= (ot - pt) / ot;
			}
			return (int) Math.round(m*100);
	    }
	    return 0;
    }
}
