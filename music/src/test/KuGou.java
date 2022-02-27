package test;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author 糯米
 * @category 1
 */

public class KuGou {
	private boolean Song_INFO;
	private String songName;
	private String singerName;
	private String fileHash;
	private int failProcess;
	private String albumID;
	private String songINFO;
	private String lyrics;
	private long timelength;
	private String img;
	private String listscat;

	/**
	 * @author 糯米
	 * @param 提交歌曲名字
	 */
	public static String nlists(String str) {
		StringBuffer buff = new StringBuffer();
		String obj = Internet.openStream(combination(str));
		JSONObject object = new JSONObject(obj);
		String data = object.getJSONObject("data").toString();
		JSONObject objData = new JSONObject(data);
		if (!objData.get("lists").toString().contains("{}")) {
			JSONArray lists = objData.getJSONArray("lists");
			for (int i = 0; i < lists.length(); i++) {
				String SongName = lists.getJSONObject(i).getString("SongName");
				String SingerName = lists.getJSONObject(i).getString("SingerName");
				buff.append((i + 1) + "：" + SongName + "-" + SingerName).append("\n");
			}
		}
		return buff.toString();
	}

	/**
	 * @author 糯米
	 * @param 提交歌曲名字
	 * @param 提交歌曲的序号
	 */
	public KuGou(String songName, int num) {
		num -= 1;
		String obj = Internet.openStream(combination(songName));
		JSONObject object = new JSONObject(obj);
		String data = object.getJSONObject("data").toString();
		JSONObject objData = new JSONObject(data);
		if (!objData.get("lists").toString().contains("{}")) {
			JSONArray lists = objData.getJSONArray("lists");
			this.songName = lists.getJSONObject(num).getString("SongName");
			this.singerName = lists.getJSONObject(num).getString("SingerName");
			this.fileHash = lists.getJSONObject(num).getString("FileHash");
			this.failProcess = lists.getJSONObject(num).getInt("FailProcess");
			this.albumID = lists.getJSONObject(num).getString("AlbumID");
			this.Song_INFO = true;
		} else {
			System.out.println("没搜索到！");
			this.Song_INFO = false;
		}
	}

	public KuGou() {
		// TODO 自动生成的构造函数存根
	}

	public String getSongName() {
		return songName;
	}

	public String getSingerName() {
		return singerName;
	}

	public String getFileHash() {
		return fileHash;
	}

	public int getFailProcess() {
		return failProcess;
	}

	public String getAlbumID() {
		return albumID;
	}

	public String getLyrics() {
		return lyrics;
	}

	public String getTimelength() {
		long m = TimeUnit.MILLISECONDS.toMinutes(timelength);
		long s = TimeUnit.MILLISECONDS.toSeconds(timelength);
		return m + "." + s;
	}

	public String getImg() {
		return img;
	}

	public String getSongINFO() {
		return songINFO;
	}

	public String getListscat() {
		return listscat;
	}

	public String getsongINFO() {
		String out = "未找到该歌曲！";
		if (Song_INFO == true) {
			long timeString = System.currentTimeMillis();// 毫秒级时间戳
			String url = "https://wwwapi.kugou.com/yy/index.php?r=play/getdata&";
			ArrayList<String> arrayList = new ArrayList<>();
			arrayList.add("hash=" + fileHash);
			arrayList.add("dfid=0PZofE1FUzLu28SYGF2P7NaH");
			arrayList.add("appid=1005");
			arrayList.add("mid=-");
			arrayList.add("album_id=" + albumID);
			StringBuilder stringBuilder = new StringBuilder();
			for (String s : arrayList) {
				stringBuilder.append(s).append("&");
			}
			stringBuilder.append("_=" + timeString);

			String obj = Internet.nonekey(url + stringBuilder.toString());

			JSONObject all = new JSONObject(obj);
			JSONObject data = all.getJSONObject("data");
			String play_url = data.getString("play_url");
			this.lyrics = data.getString("lyrics");
			this.timelength = data.getLong("timelength");
			this.img = data.getString("img");

			out = "歌名：" + getSongName() + "\n" + "时长：" + getTimelength() + "s\n" + "歌者：" + getSingerName() + "\n"
					+ "链接：" + play_url;
//						+ "hash：" + getFileHash() + "\n"
//						+ "failProcess：" + getFailProcess() + "\n"
//						+ "AlbumID：" + getAlbumID() + "\n";
		} else if (Song_INFO == false) {
//			System.out.println("未找到！");
		}
		return out;
	}

	public boolean isSong_INFO() {
		return Song_INFO;
	}

	public static String combination(String sname) {
		long timeString = System.currentTimeMillis();// 毫秒级时间戳
		ArrayList<String> arrayList = new ArrayList<>();
		arrayList.add("bitrate=0");
//		arrayList.add("callback=callback123");
		arrayList.add("clienttime=" + timeString);
		arrayList.add("clientver=2000");
		arrayList.add("dfid=0jIqXn09wSOI3HzAHP1u498s");
		arrayList.add("inputtype=0");
		arrayList.add("iscorrection=1");
		arrayList.add("isfuzzy=0");
		arrayList.add("keyword=" + sname);
		arrayList.add("mid=" + timeString);
		arrayList.add("page=1");
		arrayList.add("pagesize=30");
		arrayList.add("platform=AndroidFilter");
		arrayList.add("privilege_filter=0");
		arrayList.add("srcappid=2919");
		arrayList.add("tag=em");
		arrayList.add("token=9634c319a32859796c343e10b35f0402bbb4a134d61d7d96ef1a23614daa4cce");
		arrayList.add("userid=0");
		arrayList.add("uuid=" + timeString);
		arrayList.sort(String::compareTo);
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("NVPh5oo715z5DIWAeQlhMDsWXXQV4hwt");
		for (String s : arrayList) {
			stringBuilder.append(s);
		}
		stringBuilder.append("NVPh5oo715z5DIWAeQlhMDsWXXQV4hwt");

		String signature = getMD5(stringBuilder.toString());
		stringBuilder.setLength(0);
		for (String s : arrayList) {
			stringBuilder.append(s).append("&");
		}
		stringBuilder.append("signature=").append(signature);
		String encode = "";
		try {
			encode = URLEncoder.encode(sname, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String url = "https://complexsearch.kugou.com/v2/search/song?"
				+ stringBuilder.toString().replace(sname, encode);
		return url;
	}

	public static String getMD5(String str) {
		StringBuilder stringBuilder = new StringBuilder();
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			byte[] bytes = messageDigest.digest(str.getBytes());
			for (int i = 0; i < bytes.length; i++) {
				stringBuilder.append(String.format("%02x", bytes[i] & 0xFF));

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stringBuilder.toString().toUpperCase();
	}

	@Override
	public String toString() {
		return getClass().getName() + "[Song_INFO=" + Song_INFO + ", songName=" + songName + ", singerName="
				+ singerName + ", fileHash=" + fileHash + ", failProcess=" + failProcess + ", albumID=" + albumID
				+ ", songINFO=" + songINFO + ", lyrics=" + lyrics + ", timelength=" + timelength + ", img=" + img
				+ ", listscat=" + listscat + ", getSongName()=" + getSongName() + ", getSingerName()=" + getSingerName()
				+ ", getFileHash()=" + getFileHash() + ", getFailProcess()=" + getFailProcess() + ", getAlbumID()="
				+ getAlbumID() + ", getLyrics()=" + getLyrics() + ", getTimelength()=" + getTimelength() + ", getImg()="
				+ getImg() + ", getSongINFO()=" + getSongINFO() + ", getListscat()=" + getListscat()
				+ ", getsongINFO()=" + getsongINFO() + ", isSong_INFO()=" + isSong_INFO() + "]";
	}

}
