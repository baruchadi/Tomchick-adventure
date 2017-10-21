package smellychiz.projects.ogc.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Date;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import smellychiz.projects.ogc.objects.world.Level;
import smellychiz.projects.ogc.util.views.renderers.PickerRenderer;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

public class IO extends AsyncTask<String, Void, Integer> {

	public String fileName;

	// public static Context mContext;

	public static String lvlDownload = "level5.tom", manifest = "manifest.tom";

	static String player = "player";

	static String platform = "platform";

	static String enemy = "enemy";

	static String boss = "boss";

	static String coin = "coin";

	static String sign = "sign";

	static String fsign = "fsign";

	public static boolean downloadLevel(Context mContext) {
		File dir2 = new File(mContext.getFilesDir().getPath().toString()
				+ "/tomchick");
		dir2.mkdirs();
		try {
			new DefaultHttpClient()
					.execute(
							new HttpGet("http://isolatedpixel.com/download/"
									+ lvlDownload)).getEntity()
					.writeTo(new FileOutputStream(new File(dir2, lvlDownload)));
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return false;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		return true;

	}

	public static boolean downloadLevel(String s, Context mContext) {
		File dir2 = new File(mContext.getFilesDir().getPath().toString()
				+ "/tomchick" + "/map1");
		dir2.mkdirs();
		try {
			new DefaultHttpClient()
					.execute(
							new HttpGet(
									"http://isolatedpixel.com/download/map1/"
											+ s)).getEntity()
					.writeTo(new FileOutputStream(new File(dir2, s)));
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return false;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		return true;

	}

	public static boolean downloadLevel(String s, String folder,
			Context mContext) {
		File dir2 = new File(mContext.getFilesDir().getPath().toString() + "/"
				+ "/tomchick" + "/" + folder);
		dir2.mkdirs();
		try {
			new DefaultHttpClient()
					.execute(
							new HttpGet("http://isolatedpixel.com/download/"
									+ folder + "/" + s)).getEntity()
					.writeTo(new FileOutputStream(new File(dir2, s)));
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return false;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		return true;

	}

	public static boolean downloadManifest(Context mContext) {

		File dir2 = new File(mContext.getFilesDir().getPath().toString()
				+ "/tomchick" + "/map1");
		dir2.mkdirs();
		try {
			new DefaultHttpClient()
					.execute(
							new HttpGet(
									"http://isolatedpixel.com/download/map1/manifest.tom"))
					.getEntity()
					.writeTo(new FileOutputStream(new File(dir2, manifest)));
			File file = new File(dir2 + manifest);
			BufferedReader in = new BufferedReader(new FileReader(file));
			String s = "";
			do {
				s = in.readLine();
				if (s != null) {
					downloadLevel(s, mContext);
				}
			} while (in.ready());
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return false;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public static boolean loadLevel(Level l, Context c) {
		return loadLevel(l, lvlDownload, c);
	}

	public static boolean loadLevel(Level l, String name, Context c) {
		return loadLevel(l, "map1", name, c);
	}

	public static boolean loadLevel(Level l, String folder, String name,
			Context mContext) {
		String s;
		int num[] = new int[7];
		int read[] = new int[10];
		String[] str;
		int chose = 0;
		int lineNum = 0;
		int numObjects = 0;
		Log.d("working...", "loading...");

		try {
			String filePath = mContext.getFilesDir().getPath().toString()
					+ "/tomchick" + "/" + folder + "/" + name;
			File file = new File(filePath);
			System.out.println("f: " + folder + ", " + name);
			if (file.exists()) {
				BufferedReader in = new BufferedReader(new FileReader(file));

				do {
					s = in.readLine();
					if (s != null) {
						System.out.println(s);
						if (lineNum == 0) {
							str = s.trim().split("\\s+");
							System.out.println("str " + str[0] + ", " + str[1]
									+ ", " + str[2]);

							Assets.loadBackground(Integer.parseInt(str[0]),
									mContext);

							l.setWidth(Integer.parseInt(str[1]));
							l.setHeight(Integer.parseInt(str[2]));
							lineNum++;
						} else if (lineNum == 1) {

							str = s.trim().split("\\s+");

							for (int i = 0; i < str.length; i++) {
								num[i] = Integer.parseInt(str[i]);
								System.out.println(num[i]);
								numObjects += num[i];
							}
							numObjects += num.length;
							lineNum++;
						} else {
							System.out.println(s);
							if (s.equals(player)) {
								System.out.println(player);
								for (int i = 0; i < num[0]; i++) {
									String lol = in.readLine();
									System.out.println(lol);
									str = lol.trim().split("\\s+");
									lineNum++;

									for (int m = 0; m < str.length; m++) {

										read[m] = Integer.parseInt(str[m]);
									}
									l.spawnPlayer(read[0], read[1]);

								}
							} else if (s.equals(platform)) {
								for (int i = 0; i < num[1]; i++) {
									String lol = in.readLine();
									str = lol.trim().split("\\s+");
									lineNum++;
									for (int m = 0; m < str.length; m++) {
										read[m] = Integer.parseInt(str[m]);
									}
									l.addPlatform(read[0], read[1], read[2]);

								}

							} else if (s.equals(enemy)) {

								for (int i = 0; i < num[2]; i++) {
									String lol = in.readLine();
									str = lol.trim().split("\\s+");
									lineNum++;
									for (int m = 0; m < str.length; m++) {
										read[m] = Integer.parseInt(str[m]);
									}

									l.addEnemy(read[0], read[1]);

								}

							} else if (s.equals(boss)) {

								for (int i = 0; i < num[3]; i++) {
									String lol = in.readLine();
									str = lol.trim().split("\\s+");
									lineNum++;
									for (int m = 0; m < str.length; m++) {
										read[m] = Integer.parseInt(str[m]);
									}

									l.addBoss(read[0], read[1]);

								}

							} else if (s.equals(coin)) {

								for (int i = 0; i < num[4]; i++) {
									String lol = in.readLine();
									str = lol.trim().split("\\s+");
									lineNum++;
									for (int m = 0; m < str.length; m++) {
										read[m] = Integer.parseInt(str[m]);
									}

									l.addCoin(read[0], read[1]);

								}

							} else if (s.equals(sign)) {

								for (int i = 0; i < num[5]; i++) {
									String lol = in.readLine();
									str = lol.trim().split("\\s+");
									lineNum++;
									for (int m = 0; m < str.length; m++) {
										read[m] = Integer.parseInt(str[m]);
									}
									l.addSign(read[0], read[1], read[2]);

								}

							} else if (s.equals(fsign)) {

								for (int i = 0; i < num[6]; i++) {
									String lol = in.readLine();
									str = lol.trim().split("\\s+");
									lineNum++;
									for (int m = 0; m < str.length; m++) {
										read[m] = Integer.parseInt(str[m]);
									}
									System.out.println("lol fSign");
									l.addFSign(read[0], read[1]);

								}

							}

						}

					} else
						break;
				}

				while (in.ready());
				return true;
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

	public static void save() {
	}

	public IO(String fileName) {
		this.fileName = fileName;
	}

	public IO() {

	}

	public final static String key = "TOMCHICK", oldDate = "DATEUPDATE";

	@Override
	protected Integer doInBackground(String... arg0) {
		done = false;

		if (isNetworkAvailable()) {
			SharedPreferences prefs = PickerRenderer.contx
					.getSharedPreferences(key, Context.MODE_PRIVATE);

			long date1 = prefs.getLong(oldDate, 0);
			Date dateOnline = null;
			URL url;
			try {
				url = new URL(
						"http://www.isolatedpixel.com/download/map1/manifest.tom");
				HttpURLConnection httpConnection = (HttpURLConnection) url
						.openConnection();
				httpConnection.setRequestMethod("HEAD");
				httpConnection.connect();
				long lastModified = httpConnection.getLastModified();
				if (lastModified != 0) {
					dateOnline = new Date(lastModified);
				} else {
					System.out.println("Last-Modified not returned");
				}
				httpConnection.disconnect();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (date1 != 0) {

				Date dateOld = new Date(date1);

				if (dateOnline != null) {
					if (dateOld.compareTo(dateOnline) != 0) {
						System.out.println("*************************");
						System.out.println(dateOnline.getTime());
						System.out.println(dateOld.getTime());
						System.out.println("*************************");
						SharedPreferences.Editor editor = prefs.edit();
						downloadManifest("farm", PickerRenderer.contx);
						downloadManifest("city", PickerRenderer.contx);
						downloadManifest("survival", PickerRenderer.contx);
						downloadManifest("wave", PickerRenderer.contx);
						editor.putLong(oldDate, dateOnline.getTime());
						editor.commit();

					}
				}

			} else {
				if (dateOnline != null) {
					SharedPreferences.Editor editor = prefs.edit();

					editor.putLong(oldDate, dateOnline.getTime());
					editor.commit();
				}
				downloadManifest("farm", PickerRenderer.contx);
				downloadManifest("city", PickerRenderer.contx);
				downloadManifest("survival", PickerRenderer.contx);
				downloadManifest("wave", PickerRenderer.contx);

			}

		} else {
			online = false;
		}

		// downloadManifest("survival", PickerRenderer.contx);
		// downloadManifest("wave", PickerRenderer.contx);
		// downloadManifest("farm", PickerRenderer.contx);
		// downloadManifest("city", PickerRenderer.contx);
		done = true;
		return 0;
	}

	static boolean online = true;

	public static boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager) PickerRenderer.contx
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}

	public boolean downloadManifest(String folder, Context mContext) {
		File dir2 = new File(mContext.getFilesDir().getPath().toString()
				+ "/tomchick" + "/" + folder);
		dir2.mkdirs();
		try {
			new DefaultHttpClient()
					.execute(
							new HttpGet("http://isolatedpixel.com/download/"
									+ folder + "/manifest.tom")).getEntity()
					.writeTo(new FileOutputStream(new File(dir2, manifest)));
			File file = new File(dir2 + "/" + manifest);
			BufferedReader in = new BufferedReader(new FileReader(file));
			String s = in.readLine();
			do {
				s = in.readLine();
				if (s != null) {
					Log.e("lvl", s);
					downloadLevel(s, folder, mContext);
				}
			} while (in.ready());
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return false;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public static boolean done = true;

	// public boolean downloadManifest(Context mContext) {
	// File dir2 = new File(mContext.getFilesDir().getPath().toString()
	// + "/tomchick" + "/map1");
	// dir2.mkdirs();
	// try {
	// new DefaultHttpClient()
	// .execute(
	// new HttpGet(
	// "http://isolatedpixel.com/download/map1/manifest.tom"))
	// .getEntity()
	// .writeTo(new FileOutputStream(new File(dir2, manifest)));
	// File file = new File(dir2 + "/" + manifest);
	// BufferedReader in = new BufferedReader(new FileReader(file));
	// String s = "";
	// do {
	// s = in.readLine();
	// if (s != null) {
	// downloadLevel(s);
	// }
	// } while (in.ready());
	// } catch (ClientProtocolException e) {
	// e.printStackTrace();
	// return false;
	// } catch (FileNotFoundException e) {
	// e.printStackTrace();
	// return false;
	// } catch (IOException e) {
	// e.printStackTrace();
	// return false;
	// }
	//
	// return true;
	// }

	public void initTimes(String world, Context mContext) {
		File dir = new File(mContext.getFilesDir().getPath().toString()
				+ "/tomchick/" + world + "/times.tom");
		if (!dir.exists()) {
			dir.mkdirs();
			PrintStream out = null;
			try {
				out = new PrintStream(new FileOutputStream(dir));
				for (int i = 0; i < 16; i++) {
					out.println("0.00 0.00");
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (out != null)
					out.close();
			}
		}
	}

	public int ManifestLines(Context mContext) {
		File dir2 = new File(mContext.getFilesDir().getPath().toString()
				+ "/tomchick" + "/map1/" + manifest);
		BufferedReader in;
		int lines;
		try {
			in = new BufferedReader(new FileReader(dir2));
			lines = Integer.parseInt(in.readLine());
			System.out.println(lines);
			// while (in.ready()) {
			// System.out.println(in.readLine());
			// }
			in.close();
			return lines;

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}

	public boolean updateNeeded() {
		// long date;
		// try {
		// String url =
		// "http://www.isolatedpixel.com/download/map1/manifest.tom";
		// HttpURLConnection.setFollowRedirects(false);
		// HttpURLConnection con;
		// con = (HttpURLConnection) new URL(url).openConnection();
		// date = con.getLastModified();
		// if (date == 0)
		// System.out.println("No last-modified information.");
		// else
		// System.out.println("Last-Modified: " + new Date(date));
		// } catch (MalformedURLException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		return true;

	}

	public Manifest readManifest(String folder, Context mContext) {

		File dir2 = new File(mContext.getFilesDir().getPath().toString()
				+ "/tomchick" + "/" + folder + "/" + manifest);
		if (dir2.exists()) {
			BufferedReader in;
			int lines;
			String[] s;
			try {
				in = new BufferedReader(new FileReader(dir2));
				lines = Integer.parseInt(in.readLine());
				s = new String[lines];

				for (int i = 0; i < s.length; i++) {
					s[i] = in.readLine();
				}
				in.close();
				Manifest m = new Manifest(lines, s);
				m.ready = true;
				return m;
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			BufferedReader in;
			int lines;
			String[] s;
			try {
				in = new BufferedReader(new FileReader(dir2));
				lines = Integer.parseInt(in.readLine());
				s = new String[lines];

				for (int i = 0; i < s.length; i++) {
					s[i] = in.readLine();
				}
				in.close();
				Manifest m = new Manifest(lines, s);
				m.ready = true;
				return m;
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;

	}

	public Manifest readManifest(Context mContext) {
		File dir2 = new File(mContext.getFilesDir().getPath().toString()
				+ "/tomchick" + "/map1/" + manifest);
		BufferedReader in;
		int lines;
		String[] s;
		try {
			in = new BufferedReader(new FileReader(dir2));
			lines = Integer.parseInt(in.readLine());
			s = new String[lines];

			for (int i = 0; i < s.length; i++) {
				s[i] = in.readLine();
			}
			in.close();
			return new Manifest(lines, s);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

}
