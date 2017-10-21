package smellychiz.projects.ogc.util;

import java.io.IOException;

import smellychiz.projects.ogc.util.graphics.Animation;
import smellychiz.projects.ogc.util.graphics.Texture;
import smellychiz.projects.ogc.util.graphics.TextureArea;
import smellychiz.projects.ogc.util.helpers.Vector3;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

public class Assets {

	// public Context mContext;

	// Textures
	boolean loaded = false;

	// HUD
	public static Texture tHUD, continueBg, tCont, tStats;

	public static Texture tPig, exit, arrow;
	public static Texture tom1, pick1;

	public static Texture tPlatform, btPlatform, overlay, signs, tCoin;

	public static MediaPlayer mp, shoot, sCoin, bgm, player_hit;

	public static Texture[] layers;

	public static Texture tButtons, tNums;

	static final int FARM = 0, LAB = 1, CITY = 2, BLUR = 5;

	public static TextureArea ball, seed, fSeed;

	// PLAYER RELATED
	public static TextureArea PlayerIdel, PlayerJump, PlayerLand;

	// NPC
	public static TextureArea slimeIdel;

	// Blocks
	public static TextureArea p123latform, mPlatform, baseLeft, baseMid,
			baseRight;

	public static TextureArea hContainer;

	public static TextureArea pside, pLeft, pRight;

	// ================================================

	// Texture Areas

	// HUD

	public static TextureArea green, red, BGstats;

	public static TextureArea beak;

	public static TextureArea lArrow, rArrow, jump, att, clArrow, crArrow,
			cjump, cAtt, pause, cPause, rapid, cRapid;

	public static TextureArea idle_body, survival, normal, psurvival, pnormal,
			pighunt, ppighunt, wave, pwave, back, pback, city, pCity, farm,
			pFarm;

	public static TextureArea pig_hit, pig_power;

	public static Animation pigIdle, pigLegs, pigAtt, coin, pigSummon;
	public static TextureArea mainHud;
	public static TextureArea[] sign, stats;

	public static TextureArea resume, cResume, quit, cQuit, options, cOptions;

	public static Animation tomAnim, tomIdle, tomFalling;

	public static Animation eyeRollFull, fireEyes;

	public static void finish() {
		tHUD.dispose();
		// for (int i = 0; i < layers.length; i++) {
		// if (layers[i] != null) {
		// layers[i].dispose();
		// layers[i] = null;
		// }
		// }
		// mp = null;
		// shoot = null;
		// sCoin = null;
		if (tButtons != null)
			tButtons.dispose();
		tButtons = null;
		if (tCoin != null)
			tCoin.dispose();
		tCoin = null;
		if (tNums != null)
			tNums.dispose();
		tNums = null;
		if (tom1 != null)
			tom1.dispose();
		tom1 = null;
		if (tPig != null)
			tPig.dispose();
		tPig = null;

	}

	public static void loadBackground(int n, Context mContext) {

		switch (n) {
		case FARM:
			layers = null;
			layers = new Texture[5];
			layers[0] = new Texture(mContext, "backgrounds/farm/0.png");
			layers[1] = new Texture(mContext, "backgrounds/farm/1.png");
			layers[2] = new Texture(mContext, "backgrounds/farm/2.png");
			layers[3] = new Texture(mContext, "backgrounds/farm/3.png");
			layers[4] = new Texture(mContext, "backgrounds/farm/4.png");
			signs = new Texture(mContext, "textures/signsfarm.png");
			tPlatform = new Texture(mContext, "textures/platform1.png", 30, 92);
			btPlatform = new Texture(mContext, "textures/baseplatform.png", 37,
					121);
			pLeft = new TextureArea(tPlatform, new Vector3(0, 0, 60, 30));
			pside = new TextureArea(tPlatform, new Vector3(60, 0, 32, 30));
			pRight = new TextureArea(tPlatform, new Vector3(96, 0, 32, 32));
			baseLeft = new TextureArea(btPlatform, new Vector3(0, 0, 23, 37));
			baseMid = new TextureArea(btPlatform, new Vector3(23, 0, 75, 37));
			baseRight = new TextureArea(btPlatform, new Vector3(98, 0, 23, 37));
			initSigns();
			break;
		case BLUR:
			layers = null;
			layers = new Texture[5];
			layers[0] = new Texture(mContext, "backgrounds/blur/0.png");
			layers[1] = new Texture(mContext, "backgrounds/blur/1.png");
			layers[2] = new Texture(mContext, "backgrounds/blur/2.png");
			layers[3] = new Texture(mContext, "backgrounds/blur/3.png");
			layers[4] = new Texture(mContext, "backgrounds/blur/4.png");

			break;
		case LAB:
			layers = null;
			layers = new Texture[1];
			layers[0] = new Texture(mContext, "backgrounds/lab/0.png");
			tPlatform = new Texture(mContext, "textures/platform1.png", 30, 92);
			btPlatform = new Texture(mContext, "textures/baseplatform.png", 37,
					121);
			pLeft = new TextureArea(tPlatform, new Vector3(0, 0, 60, 30));
			pside = new TextureArea(tPlatform, new Vector3(60, 0, 32, 30));
			pRight = new TextureArea(tPlatform, new Vector3(96, 0, 32, 32));
			baseLeft = new TextureArea(btPlatform, new Vector3(0, 0, 23, 37));
			baseMid = new TextureArea(btPlatform, new Vector3(23, 0, 75, 37));
			baseRight = new TextureArea(btPlatform, new Vector3(98, 0, 23, 37));
			signs = new Texture(mContext, "textures/singscity.png");
			initSigns();
			break;
		case CITY:
			layers = null;
			layers = new Texture[3];
			layers[0] = new Texture(mContext, "backgrounds/city/0.png");
			layers[1] = new Texture(mContext, "backgrounds/city/1.png");
			layers[2] = new Texture(mContext, "backgrounds/city/2.png");
			tPlatform = new Texture(mContext, "textures/citypl.png", 30, 92);
			btPlatform = new Texture(mContext, "textures/citybase.png", 37, 121);
			pLeft = new TextureArea(tPlatform, new Vector3(0, 0, 60, 30));
			pside = new TextureArea(tPlatform, new Vector3(60, 0, 32, 30));
			pRight = new TextureArea(tPlatform, new Vector3(96, 0, 32, 32));
			baseLeft = new TextureArea(btPlatform, new Vector3(0, 0, 23, 37));
			baseMid = new TextureArea(btPlatform, new Vector3(23, 0, 75, 37));
			baseRight = new TextureArea(btPlatform, new Vector3(98, 0, 23, 37));
			signs = new Texture(mContext, "textures/singscity.png");
			initSigns();
			break;

		}

	}

	public static void initSigns() {
		sign = new TextureArea[5];
		sign[0] = new TextureArea(signs, new Vector3(0, 0, 73, 97));
		sign[2] = new TextureArea(signs, new Vector3(83, 0, 73, 97));
		sign[3] = new TextureArea(signs, new Vector3(150, 0, 73, 97));
		sign[1] = new TextureArea(signs, new Vector3(219, 0, 73, 97));
		sign[4] = new TextureArea(signs, new Vector3(294, 0, 73, 97));
	}

	public static Texture tPoof;
	public static Animation poof;

	public void initTextureAreas(Context mContext) {
		tPoof = new Texture(mContext, "textures/poof.png");
		poof = new Animation(8f,

		new TextureArea(tPoof, new Vector3(0, 0, 128, 128)),

		new TextureArea(tPoof, new Vector3(0, 128, 128, 128)),

		new TextureArea(tPoof, new Vector3(0, 256, 128, 128)),

		new TextureArea(tPoof, new Vector3(0, 384, 128, 128)),

		new TextureArea(tPoof, new Vector3(0, 512, 128, 128))

		);

		hContainer = new TextureArea(tHUD, new Vector3(117, 0, 35, 13));
		red = new TextureArea(tHUD, new Vector3(117, 28, 35, 13));
		loadHUDTextureArea();
		loadPlayerTextureAreas();
		loadBlocksTextureAreas();
		loadPig();
	}

	public void LoadAll(Context mContext) {
		loadTextures(mContext);

		initTextureAreas(mContext);
		loadAnimation();
		loadSounds(mContext);
	}

	public void loadAnimation() {

		loadPlayerAnimation();

	}

	public void loadBlocksTextureAreas() {
		// platform = new TextureArea(tPlatform, new Vector3(0, 0, 30, 59));
	}

	public static TextureArea cont, cCont;

	public void loadPauseMenu() {
		cont = new TextureArea(tCont, new Vector3(1, 1, 96, 57));
		cCont = new TextureArea(tCont, new Vector3(100, 1, 96, 57));
		resume = new TextureArea(tHUD, new Vector3(170, 0, 46, 20));
		cResume = new TextureArea(tHUD, new Vector3(170, 20, 46, 20));
		quit = new TextureArea(tHUD, new Vector3(218, 0, 30, 20));
		cQuit = new TextureArea(tHUD, new Vector3(218, 20, 30, 20));
		options = new TextureArea(tHUD, new Vector3(208, 43, 46, 20));
		cOptions = new TextureArea(tHUD, new Vector3(208, 63, 46, 20));

	}

	public void loadHUDTextureArea() {

		att = new TextureArea(tHUD, new Vector3(3, 64, 32, 32));
		cAtt = new TextureArea(tHUD, new Vector3(3, 96, 32, 32));

		rapid = new TextureArea(tHUD, new Vector3(72, 64, 32, 32));
		cRapid = new TextureArea(tHUD, new Vector3(72, 96, 32, 32));

		jump = new TextureArea(tHUD, new Vector3(72, 0, 32, 32));
		cjump = new TextureArea(tHUD, new Vector3(72, 32, 32, 32));

		pause = new TextureArea(tHUD, new Vector3(38, 64, 32, 32));
		cPause = new TextureArea(tHUD, new Vector3(38, 96, 32, 32));

		green = new TextureArea(tHUD, new Vector3(121, 117, 113, 14));
		ball = new TextureArea(tHUD, new Vector3(132, 0, 32, 32));
		lArrow = new TextureArea(tHUD, new Vector3(3, 0, 32, 32));

		rArrow = new TextureArea(tHUD, new Vector3(37, 0, 32, 32));

		clArrow = new TextureArea(tHUD, new Vector3(3, 32, 32, 32));

		crArrow = new TextureArea(tHUD, new Vector3(37, 32, 32, 32));

		mainHud = new TextureArea(tHUD, new Vector3(4, 135, 193, 67));

		loadPauseMenu();

		coin = new Animation(4f,

		new TextureArea(tCoin, new Vector3(0, 0, 16, 16)),

		new TextureArea(tCoin, new Vector3(16, 0, 16, 16)),

		new TextureArea(tCoin, new Vector3(32, 0, 16, 16)),

		new TextureArea(tCoin, new Vector3(48, 0, 16, 16)),

		new TextureArea(tCoin, new Vector3(64, 0, 16, 16)),

		new TextureArea(tCoin, new Vector3(64 + 16, 0, 16, 16)),

		new TextureArea(tCoin, new Vector3(32, 0, 16, 16)),

		new TextureArea(tCoin, new Vector3(96, 0, 16, 16))

		);
	}

	public void loadPig() {

		int w = 50;
		int h = 59;

		pig_hit = new TextureArea(tPig, new Vector3(0, h + 2, w, h));
		pig_power = new TextureArea(tPig, new Vector3(0, 512 - 64, 64, 64));
		idle_body = new TextureArea(tPig, new Vector3(0, h * 2 + 4, w, h));

		pigIdle = new Animation(20f,

		new TextureArea(tPig, new Vector3(0, 0, w, h)),

		new TextureArea(tPig, new Vector3(w + 2, 0, w, h))

		);

		pigLegs = new Animation(7f,

		new TextureArea(tPig, new Vector3(0, 244, w, h)),

		new TextureArea(tPig, new Vector3(w + 2, 244, w, h)),

		new TextureArea(tPig, new Vector3(w * 2 + 4, 244, w, h)),

		new TextureArea(tPig, new Vector3(w * 3 + 6, 244, w, h))

		);

		pigAtt = new Animation(2f,

		new TextureArea(tPig, new Vector3(0, 183, w, h)),

		new TextureArea(tPig, new Vector3(w + 2, 183, w, h)),

		new TextureArea(tPig, new Vector3(w * 2 + 4, 183, w, h)),

		new TextureArea(tPig, new Vector3(w * 3 + 6, 183, w, h))

		);

		pigSummon = new Animation(8f,

		new TextureArea(tPig, new Vector3(0, 366, w, h)),

		new TextureArea(tPig, new Vector3(w + 2, 366, w, h)),

		new TextureArea(tPig, new Vector3(w * 2 + 4, 366, w, h))

		);
	}

	public void loadPlayerAnimation() {
		tomAnim = new Animation(3f,

		new TextureArea(tom1, new Vector3(0, 66, 64, 64)),

		new TextureArea(tom1, new Vector3(66, 66, 64, 64)),

		new TextureArea(tom1, new Vector3(132, 66, 64, 64)),

		new TextureArea(tom1, new Vector3(198, 66, 64, 64)),

		new TextureArea(tom1, new Vector3(264, 66, 64, 64)),

		new TextureArea(tom1, new Vector3(330, 66, 64, 64))

		);

		tomIdle = new Animation(30f,

		new TextureArea(tom1, new Vector3(0, 264, 64, 64)),

		new TextureArea(tom1, new Vector3(66, 264, 64, 64))

		);

		tomFalling = new Animation(2f,

		new TextureArea(tom1, new Vector3(66, 198, 64, 64)),

		new TextureArea(tom1, new Vector3(132, 198, 64, 64))

		);

		seed = new TextureArea(tom1, new Vector3(512 - 8, 2, 6, 8));
		fSeed = new TextureArea(tom1, new Vector3(512 - 15, 2, 6, 8));

		eyeRollFull = new Animation(1f,

		new TextureArea(tom1, new Vector3(198, 198, 64, 64)),

		new TextureArea(tom1, new Vector3(264, 198, 64, 64)),

		new TextureArea(tom1, new Vector3(330, 198, 64, 64)),

		new TextureArea(tom1, new Vector3(396, 198, 64, 64)),

		new TextureArea(tom1, new Vector3(198, 264, 64, 64)),

		new TextureArea(tom1, new Vector3(264, 264, 64, 64)),

		new TextureArea(tom1, new Vector3(330, 264, 64, 64)),

		new TextureArea(tom1, new Vector3(396, 264, 64, 64)),

		new TextureArea(tom1, new Vector3(198, 330, 64, 64))

		);

		fireEyes = new Animation(5f,

		new TextureArea(tom1, new Vector3(0, 330, 64, 64)),

		new TextureArea(tom1, new Vector3(66, 330, 64, 64)),

		new TextureArea(tom1, new Vector3(132, 330, 64, 64))

		);

	}

	// =======================

	// Animation

	// NPC

	public void loadPlayerTextureAreas() {
		PlayerIdel = new TextureArea(tom1, new Vector3(0, 0, 64, 64));
		PlayerJump = new TextureArea(tom1, new Vector3(0, 198, 64, 64));
		PlayerLand = new TextureArea(tom1, new Vector3(132, 198, 64, 64));
	}

	public void loadSounds(Context mContext) {

		AssetFileDescriptor afd;
		mp = new MediaPlayer();
		shoot = new MediaPlayer();
		sCoin = new MediaPlayer();
		bgm = new MediaPlayer();
		player_hit = new MediaPlayer();

		try {
			afd = mContext.getAssets().openFd("sfx/jump.ogg");
			mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(),
					afd.getLength());
			mp.prepare();
			afd = mContext.getAssets().openFd("sfx/shoot.ogg");
			shoot.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(),
					afd.getLength());
			shoot.prepare();
			afd = mContext.getAssets().openFd("sfx/coin.ogg");
			sCoin.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(),
					afd.getLength());
			sCoin.prepare();
			afd = mContext.getAssets().openFd("sfx/chickhit.ogg");
			player_hit.setDataSource(afd.getFileDescriptor(),
					afd.getStartOffset(), afd.getLength());
			player_hit.prepare();
			afd = mContext.getAssets().openFd("sfx/song.mp3");
			bgm.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(),
					afd.getLength());
			bgm.setLooping(true);
			bgm.prepare();
			bgm.start();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Player Related

	// public static Animation Player_WR, Player_Whip_ATT;

	public static TextureArea[] buttons = new TextureArea[32];
	public static TextureArea[] nums = new TextureArea[10];

	public static void stopMusic() {
		if (bgm != null) {
			if (bgm.isPlaying()) {
				bgm.stop();
			}
		}

	}

	public void loadTextures(Context mContext) {
		arrow = new Texture(mContext, "textures/arrow.png");
		tCont = new Texture(mContext, "textures/continue.png", 59, 196);
		continueBg = new Texture(mContext, "textures/stats.png");
		tStats = new Texture(mContext, "textures/mstats.png");
		BGstats = new TextureArea(tStats, new Vector3(469, 0, 233, 180));
		stats = new TextureArea[2];
		stats[0] = new TextureArea(tStats, new Vector3(0, 0, 233, 180));
		stats[1] = new TextureArea(tStats, new Vector3(229, 0, 233, 180));
		// HUD TEXTURE
		tHUD = new Texture(mContext, "textures/gui.png", 256, 256);
		plus = new TextureArea(tHUD, new Vector3(134, 92, 18, 17));
		pPlus = new TextureArea(tHUD, new Vector3(163, 92, 18, 17));
		// PLAYER TEXTURE
		tom1 = new Texture(mContext, "tom/spritesheet.png", 512, 512);
		// ENEMY TEXTURE
		tPig = new Texture(mContext, "textures/pig.png");
		// PLATFORM TEXTURES

		overlay = new Texture(mContext, "textures/black.png", 1, 1);

		tCoin = new Texture(mContext, "textures/coin.png", 16, 117);

		tNums = new Texture(mContext, "textures/numbers.png", 50, 100);

		lvlLabel = new TextureArea(tNums, new Vector3(2, 29, 13, 9));

		for (int l = 0; l < 2; l++) {
			for (int i = 0; i < 5; i++) {
				nums[i + (l * 5)] = new TextureArea(tNums, new Vector3((8 * i)
						+ 2 + (2 * i), (2 * l) + 2 + (12 * (l)), 8, 12));
				System.out.println(i + (l * 8));
			}
		}
	}

	public static TextureArea lvlLabel;

	public void print(int i) {
		i++;
		System.out.println(i);

	}

	public static Texture logo1, settings, tBack, gear, cityfarm;

	public static TextureArea plus, pPlus, levelSelect;

	public void LoadPicker(Context mContext) {
		int w = 84, h = 76;
		AssetFileDescriptor afd;
		bgm = new MediaPlayer();

		try {
			afd = mContext.getAssets().openFd("sfx/song1.mp3");
			bgm.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(),
					afd.getLength());
			bgm.setLooping(true);
			bgm.prepare();
			bgm.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		logo1 = new Texture(mContext, "textures/logo.png");
		pick1 = new Texture(mContext, "textures/mbuttons.png", 380, 342);

		gear = new Texture(mContext, "textures/settings.png");

		tButtons = new Texture(mContext, "textures/bottuns.png", 339, 727);
		for (int l = 0; l < 4; l++) {
			for (int i = 0; i < 8; i++) {
				buttons[i + (l * 8)] = new TextureArea(tButtons, new Vector3(
						(5 * i) + 10 + (w * i), (5 * l) + 10 + (h * (l)), w, h));
				System.out.println(i + (l * 8));
			}
		}

		exit = new Texture(mContext, "textures/exit.png");

		psurvival = new TextureArea(pick1, new Vector3(2, 2, 171, 74));
		pnormal = new TextureArea(pick1, new Vector3(1, 77, 171, 74));
		survival = new TextureArea(pick1, new Vector3(173, 2, 171, 74));
		normal = new TextureArea(pick1, new Vector3(172, 78, 171, 74));

		cityfarm = new Texture(mContext, "textures/cityfarm.png");

		city = new TextureArea(cityfarm, new Vector3(17, 31, 168, 68));
		pCity = new TextureArea(cityfarm, new Vector3(17, 136, 168, 68));
		farm = new TextureArea(cityfarm, new Vector3(215, 34, 182, 65));
		pFarm = new TextureArea(cityfarm, new Vector3(215, 137, 182, 65));

		wave = new TextureArea(pick1, new Vector3(172, 229, 171, 74));
		pwave = new TextureArea(pick1, new Vector3(1, 229, 171, 74));
		pighunt = new TextureArea(pick1, new Vector3(173, 151, 171, 74));
		ppighunt = new TextureArea(pick1, new Vector3(2, 151, 171, 74));
		levelSelect = new TextureArea(pick1, new Vector3(19, 310, 135, 62));

		tBack = new Texture(mContext, "textures/backbutton.png");

		back = new TextureArea(tBack, new Vector3(5, 4, 86, 86));
		pback = new TextureArea(tBack, new Vector3(95, 4, 86, 86));
	}
}
