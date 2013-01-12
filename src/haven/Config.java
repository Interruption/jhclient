/*
 *  This file is part of the Haven & Hearth game client.
 *  Copyright (C) 2009 Fredrik Tolf <fredrik@dolda2000.com>, and
 *                     Bjorn Johannessen <johannessen.bjorn@gmail.com>
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

import java.io.*;
import java.net.URL;
import java.util.HashSet;
import java.util.Properties;
import java.util.Map;
//import java.util.Set;
import java.util.HashMap;

import static haven.ark_bot.*;
import static haven.Utils.getprop;

import ender.CurioInfo;

public class Config {
    public static byte[] authck;
    public static String authuser;
    public static String authserv;
    public static String defserv;
    public static URL resurl, mapurl;
    public static boolean fullscreen;
    public static boolean debug_flag = false;
    public static boolean bounddb = true;
    public static boolean profile;
    public static boolean nolocalres;
    public static String resdir;
    public static String mapdir;
    public static boolean nopreload;
    public static String loadwaited, allused;

    public static int ark_window_width = 800;
    public static int ark_window_height = 600;
    public static boolean tracking = true; // включить трекинг криминал актс. чтобы показывало следы. врубается при логине если стоит
    public static boolean always_show_nicks = true; // всегда показывать ники над персонажами
    public static boolean show_map_grid = true; // показывать сетку на карте
    public static boolean highlight_object_by_mouse = true; // подсвечивать объект под мышью
    public static boolean highlight_hided_objects = true; // подсвечивать ли скрытые объекты
    public static boolean assign_to_tile = false;
    public static String bot_name1;
    public static String bot_name2;
    public static boolean dump_all_res = false;

    public static boolean ark_debug_drawto_console = false;
    public static boolean render_enable = true;
    
    // quick login
    public static boolean quick_login = false; // быстрый логин дефолт чаром
    public static boolean ark_state_activate_char = false; // стадия аткивации чара
    public static int ark_button_activate_char = 0; // ид кнопки которую надо нажать
    public static String auto_start_script = ""; // имя скрипта запускаемого после логина
    public static boolean keep_connect = false; // подерживать ли подключение. (реконнекты)
    
	// public static String auth_server = "";
    public static boolean FirstLogin = true; // первый ли запуск клиента
    public static boolean inactive_exit = false; // закрывать клиент при неактивности (нет новых виджетов) 

    // arksu: from gilbertus
    public static boolean xray;
    public static boolean hide;
    public static HashSet<String> hideObjectList;
	public static String currentCharName;
    public static Properties options;
    public static boolean gilbertus_map_dump = true;
	
	// interruption:
	public static HashSet<String> ltObjectList;
	public static int lto_label_distance = 250;
	public static boolean showRadius = false;
	public static HashSet<String> radiusList;
	public static boolean showq;
	public static Map<String, Map<String, Float>> FEPMap = new HashMap<String, Map<String, Float>>();
	public static Map<String, CurioInfo> curios = new HashMap<String, CurioInfo>();
	public static boolean enableLTO = true;
	public static boolean altnLTO = true;
	public static int sfxVol;
	public static int musicVol;
	public static boolean isMusicOn = false;
	public static boolean isSoundOn = false;
			
    static {
	try {
	    String p;
	    if((p = getprop("haven.authck", null)) != null)
		authck = Utils.hex2byte(p);
	    authuser = getprop("haven.authuser", null);
	    authserv = getprop("haven.authserv", null);
	    defserv = getprop("haven.defserv", null);
	    if(!(p = getprop("haven.resurl", "https://www.havenandhearth.com/res/")).equals(""))
		resurl = new URL(p);
	    if(!(p = getprop("haven.mapurl", "http://www.havenandhearth.com/mm/")).equals(""))
		mapurl = new URL(p);
	    fullscreen = getprop("haven.fullscreen", "off").equals("on");
	    loadwaited = getprop("haven.loadwaited", null);
	    allused = getprop("haven.allused", null);
//	    debug_flag = getprop("haven.debug_flag", "off").equals("on");
	    // bounddb = getprop("haven.bounddb", "off").equals("on");
	    profile = getprop("haven.profile", "on").equals("on");
	    nolocalres = getprop("haven.nolocalres", "").equals("yesimsure");
	    resdir = getprop("haven.resdir", "./res");
        mapdir = getprop("haven.mapdir", "./map");
	    nopreload = getprop("haven.nopreload", "no").equals("yes");
        xray = false;
        hide = false;
        currentCharName = "";
        options = new Properties();
        hideObjectList = new HashSet<String>();
		ltObjectList  = new HashSet<String>();
		radiusList  = new HashSet<String>();
        loadOptions();
		loadFEP();
	    loadCurios();
    } catch(java.net.MalformedURLException e) {
	    throw(new RuntimeException(e));
	}
    }

    private static boolean getopt_bool(String key, boolean def_val) {
        String str_def_val = "false";
        if (def_val) str_def_val = "true";
        String val = options.getProperty(key, str_def_val);
        return val.equals("true");
    }
    private static int getopt_int(String key, int def_val) {
        String val = options.getProperty(key, Integer.toString(def_val));
        return Integer.parseInt(val);
    }
    private static String getopt_str(String key, String def_val) {
        return options.getProperty(key, def_val);
    }
    private static void setopt_str(String key, String val) {
        options.setProperty(key, val);
    }
    private static void setopt_int(String key, int val) {
       options.setProperty(key, Integer.toString(val));
    }
    private static void setopt_bool(String key, boolean val) {
       if (val)
           options.setProperty(key, "true");
       else
           options.setProperty(key, "false");
    }

    public static boolean IsHideable(String ResName) {
        if (hide) {
            for (String objectName : hideObjectList) {
                if (ResName.indexOf(objectName) != -1) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void ParseNewWdg(int id, String type, Object[] args, Coord c, int parent) {
        if (ark_state_activate_char) {
            if ((type.equals("btn")) && (((String)args[1]).equals("Where you logged out"))) {
                ark_button_activate_char = id;
                ark_state_activate_char = false;
                Widget wdg = ui.widgets.get(ark_button_activate_char);
                if (wdg instanceof Button) {
                    Button btn = (Button)wdg;
                    btn.click();
                    // только если не надо поддерживать подключение
                    if (!keep_connect)
                    	Config.quick_login = false;
                }
            }
        }
    }
	
	private static void loadCurios() {
		try {
			FileInputStream fstream;
			fstream = new FileInputStream("curio.conf");
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream, "UTF-8"));
			String strLine;
			while ((strLine = br.readLine()) != null)   {
				CurioInfo curio = new CurioInfo();
				String [] tmp = strLine.split(":");
				String name = tmp[0].toLowerCase();
				curio.LP = Integer.parseInt(tmp[1]);
				curio.time = (int) (60*Float.parseFloat(tmp[2]));
				curio.weight = Integer.parseInt(tmp[3]);
				curios.put(name, curio);
			}
			br.close();
			fstream.close();
		} catch (Exception e) {}
    }

	private static void loadFEP() {
	try {
	    FileInputStream fstream;
	    fstream = new FileInputStream("fep.conf");
	    BufferedReader br = new BufferedReader(new InputStreamReader(fstream, "UTF-8"));
	    String strLine;
	    while ((strLine = br.readLine()) != null)   {
		Map<String, Float> fep = new HashMap<String, Float>();
		String [] tmp = strLine.split("=");
		String name;
		name = tmp[0].toLowerCase();
		if(name.charAt(0)=='@'){
		    name = name.substring(1);
		    fep.put("isItem",(float) 1.0);
		}
		tmp = tmp[1].split(" ");
		for(String itm : tmp){
		    String tmp2[] = itm.split(":");
		    fep.put(tmp2[0], Float.valueOf(tmp2[1]).floatValue());
		}
		FEPMap.put(name, fep);
	    }
	    br.close();
	    fstream.close();
	} catch (FileNotFoundException e) {
	} catch (IOException e) {
	}
    }
	
	private static void loadONList() {
		try {
			FileInputStream fstream;
			fstream = new FileInputStream("onlist.conf");
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream, "UTF-8"));
			String strLine;
			int con = 0;
			while ((strLine = br.readLine()) != null)   {
				String [] res = strLine.split(";");
				MapView.objects_name_list.put(res[2], res[0]+":"+res[1]);
				con++;
			}
			System.out.print("\nloaded \"onlist.conf\" file ... item: "+con+"\n");
			br.close();
			fstream.close();
		} catch (Exception e) {}
    }
	
	private static void loadRList() {
		try {
			FileInputStream fstream;
			fstream = new FileInputStream("rlist.conf");
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream, "UTF-8"));
			String strLine;
			int con = 0;
			while ((strLine = br.readLine()) != null)   {
				radiusList.add(strLine);
				con++;
			}
			System.out.print("\nloaded \"rlist.conf\" file ... item: "+con+"\n");
			br.close();
			fstream.close();
		} catch (Exception e) {}
    }
	
	private static void loadLTOList() {
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream("ltolist.conf"));
			ltObjectList = (HashSet<String>) ois.readObject();
			ois.close();
		} catch (Exception e) {}
	}
	
	
	private static void writeLTOList() {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("ltolist.conf"));
			oos.writeObject(ltObjectList);
			oos.close();
		} catch (Exception e) {}
    }
	
    private static void usage(PrintStream out) {
	out.println("usage: haven.jar [-hdf] [-u USER] [-C HEXCOOKIE] [-r RESDIR] [-U RESURL] [-A AUTHSERV] [-i INACTIVE EXIT] [-m MAPDIR] [-q QUICK LOGIN ON] [-b SCRIPT NAME] [-k KEEP CONNECT] [SERVER]");
    }

    public static void cmdline(String[] args) {
	PosixArgs opt = PosixArgs.getopt(args, "hiqdU:fr:A:m:u:b:k:C");
	if(opt == null) {
	    usage(System.err);
	    System.exit(1);
	}
	for(char c : opt.parsed()) {
	    switch(c) {
	    case 'h':
		usage(System.out);
		System.exit(0);
		break;
	    case 'd':
		debug_flag = true;
		break;
	    case 'f':
		fullscreen = true;
		break;
	    case 'r':
		resdir = opt.arg;
		break;
        case 'm':
            mapdir = opt.arg;
            break;
	    case 'A':
		authserv = opt.arg;
		break;
	    case 'U':
		try {
		    resurl = new URL(opt.arg);
		} catch(java.net.MalformedURLException e) {
		    System.err.println(e);
		    System.exit(1);
		}
		break;
	    case 'u':
		authuser = opt.arg;
		break;
	    case 'C':
		authck = Utils.hex2byte(opt.arg);
		break;
        case 'q':
        quick_login = true;
        break;
        case 'b':
        	auto_start_script = opt.arg;
        break;
        case 'k' :
        	keep_connect = true;
        break;
        }
	}
	if(opt.rest.length > 0)
	    defserv = opt.rest[0];
    }

	public static double getSFXVolume()
    {
    	return isSoundOn?((double)sfxVol/100):0;
    }
    
    public static double getMusicVolume()
    {
    	return isMusicOn?((double)musicVol/100):0;
    }
	
    private static void loadOptions() {
        File inputFile = new File("haven.conf");
        if (!inputFile.exists()) {
            return;
        }
        try {
            options.load(new FileInputStream("haven.conf"));
        }
        catch (IOException e) {
            System.out.println(e);
        }
        String hideObjects = getopt_str("hideObjects", "");
        hideObjectList.clear();
        if (!hideObjects.isEmpty()) {
            for (String objectName : hideObjects.split(",")) {
                if (!objectName.isEmpty()) {
                    hideObjectList.add(objectName);
                }
            }
        }
        ark_window_width = getopt_int("window_width", 800);
        ark_window_height = getopt_int("window_height", 600);
        hide = getopt_bool("hide_objects", false);
        xray = getopt_bool("use_xray", false);
        tracking = getopt_bool("tracking_on_login", false);
        always_show_nicks = getopt_bool("always_show_nicks", true);
        show_map_grid = getopt_bool("show_map_grid", true);
        highlight_object_by_mouse = getopt_bool("highlight_object_by_mouse", true);
        highlight_hided_objects = getopt_bool("highlight_hided_objects", true);
        assign_to_tile = getopt_bool("assign_to_tile", false);
        inactive_exit = getopt_bool("inactive_exit", false);
        bot_name1 = getopt_str("bot_name1", "empty_bot1");
        bot_name2 = getopt_str("bot_name2", "empty_bot2");
        gilbertus_map_dump = getopt_bool("gilbertus_map", false);
		showq = getopt_bool("showq", true);
		lto_label_distance = getopt_int("lto_label_distance", 250);
		showRadius = getopt_bool("showRadius", false);
		enableLTO = getopt_bool("use_lto", true);
		altnLTO = getopt_bool("altname_lto", false);
		isMusicOn = getopt_bool("music_on", true);
		isSoundOn = getopt_bool("sound_on", true);
		sfxVol = getopt_int("sfx_vol", 0);
		musicVol = getopt_int("music_vol", 0);
		loadLTOList();
		loadONList();
		loadRList();
    }

    public static void saveOptions() {
        String hideObjects = "";
        for (String objectName : hideObjectList) {
            hideObjects += objectName+",";
        }
        
        setopt_str("hideObjects", hideObjects);
//        setopt_str("auth_server", auth_server);
        setopt_int("window_width", ark_window_width);
        setopt_int("window_height", ark_window_height);
        setopt_bool("hide_objects", hide);
        setopt_bool("use_xray", xray);
        setopt_bool("tracking_on_login", tracking);
        setopt_bool("always_show_nicks", always_show_nicks);
        setopt_bool("show_map_grid", show_map_grid);
        setopt_bool("highlight_object_by_mouse", highlight_object_by_mouse);
        setopt_bool("highlight_hided_objects", highlight_hided_objects);
        setopt_bool("assign_to_tile", assign_to_tile);
        setopt_bool("inactive_exit", inactive_exit);
        setopt_str("bot_name1", bot_name1);
        setopt_str("bot_name2", bot_name2);
        setopt_bool("gilbertus_map", gilbertus_map_dump);
		setopt_bool("showq", showq);
		setopt_bool("showRadius", showRadius);
		setopt_bool("use_lto", enableLTO);
		setopt_bool("altname_lto", altnLTO);
		setopt_int("lto_label_distance", lto_label_distance);
		setopt_int("sfx_vol", sfxVol);
		setopt_int("music_vol", musicVol);
		setopt_bool("music_on", isMusicOn);
		setopt_bool("sound_on", isSoundOn);
		writeLTOList();
        try {
            options.store(new FileOutputStream("haven.conf"), "Custom config options");
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
