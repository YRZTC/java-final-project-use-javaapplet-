package J1;

import java.applet.*;
import java.io.File;
import java.net.URI;
import java.net.URL;
import java.net.MalformedURLException;//放音乐
public class music {

	
 	public  static void main(String[] args) {
		// TODO Auto-generated method stub
 
		try {//此try块用于播放音乐
			 File f=new File("src/J1/bgmm.wav");//音乐播放
			URI uri=f.toURI();
			URL url=uri.toURL();
			AudioClip audioClip=Applet.newAudioClip(url);
			
			audioClip.play();
			System.out.println("开启音乐");//用于检测是否调用了音频
	
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
