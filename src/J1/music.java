package J1;

import java.applet.*;
import java.io.File;
import java.net.URI;
import java.net.URL;
import java.net.MalformedURLException;//������
public class music {

	
 	public  static void main(String[] args) {
		// TODO Auto-generated method stub
 
		try {//��try�����ڲ�������
			 File f=new File("src/J1/bgmm.wav");//���ֲ���
			URI uri=f.toURI();
			URL url=uri.toURL();
			AudioClip audioClip=Applet.newAudioClip(url);
			
			audioClip.play();
			System.out.println("��������");//���ڼ���Ƿ��������Ƶ
	
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
