package J1;
import java.awt.*;
import java.applet.*;



public class J1 extends Applet implements Runnable{
  
	long startTime,endTime,TotalTime,FinalTime;//记录正在运行的时间以及最终时间；
	
	boolean Flag=true;//用于控制线程的运行与结束；
	
	int		planex=1,planey=1,
			movx=0,movy=0,
			width=1600,height=800,
			BottonFlag=0,
			arrsize=50,
			Missionfail=0;
	int	[] arrpositionx=new int [arrsize];
	int	[] arrpositiony=new int [arrsize];//存储小球的位置
	
	boolean [] arrup=new boolean [arrsize];
	boolean [] arrdown=new boolean [arrsize];//使小球活动在指定区域
	

	Button b1=new Button("生成飞机并计时"),
		   b2=new Button("生成子弹"),
		   b3=new Button("生成指定数量的子弹(功能正在开发，敬请期待)");
	Label lb1=new Label("输入你想要躲避的子弹数量，再点击生成，不输入时默认为20(功能正在开发，敬请期待)");
	Label lb2=new Label("练习阶段：尝试控制小黑球来躲避子弹");
	TextField tf1;
	
	Image im1,im2;
	
	Thread	trl=null;

 /*public void music() {

		try {//此try块用于播放音乐
			URI uri=f.toURI();
			URL url=uri.toURL();
			AudioClip audioClip=Applet.newAudioClip(url);

			
			System.out.println("开启音乐");//用于检测是否调用了音频
			
			audioClip.play();
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	 */
	public void init() 
	{
		lb2.setLocation(800, 800);	
		tf1=new TextField("  ");
	
		
		add(b1);
		add(b2);
		add(lb2);
		
		add(b3);		
		add(lb1);
		add(tf1);
	

	}
	
	public boolean action(Event e1,Object o1)
	{
	
		if (e1.target==b1)
		{
			BottonFlag=1;
			 startTime= System.currentTimeMillis();	//记录初始时间
			lb2.setVisible(false);
			
		}
		if (e1.target==b2)
		{
			for (int aa=0;aa<=arrsize-1;aa++)
			{
				arrpositionx[aa]=(int)(Math.random()*1600);
				arrpositiony[aa]=(int)(Math.random()*800);
				arrup[aa]=false;
				arrdown[aa]=false;
			}
		}
	/*	if (e1.target==b3)
		{
			double tmp=0;
			tmp=Double.valueOf(tf1.getText().trim());
			System.out.print(tmp);
		}*/
		
		
		
	/*	if	(e1.target==b4)
		{
			double tmp=0;
			tmp=Double.valueOf(tf1.getText().trim());
			System.out.print(tmp);
		}
		*/
		repaint();
		return true;	
	}
	
	public boolean keyDown(Event e1, int key)
	{
	
		if(key==e1.LEFT)
			movx=-5;
		if(key==e1.RIGHT)
			movx=5;
		if(key==e1.UP)
			movy=-5;
		if(key==e1.DOWN)
			movy=5;	

		repaint();
		return true;
	}//飞机飞行
		
	public void start() 

	{
		trl=new Thread(this);
		trl.start();//线程启动

		
	}

	public void paint(Graphics g) 
	{

		planex=planex+movx;
		planey=planey+movy;
		im1=getImage(getCodeBase(),"plane.png");
		im2=getImage(getCodeBase(),"background.jpg");
	//	g.drawImage(im2,0,0,1600,800,this);
		if (BottonFlag==1)
		{
			g.drawImage(im1,planex+800,planey+800,20,20,this);
			g.drawString("其实是↑", 885, 820);

		}
	
	
		if ((planex<(-800))||(planex>800))
		{
			planex=planex-movx;
		}
		if ((planey<(-750))||(planey>50))
		{
			planey=planey-movy;
		}
		//生成飞机
		
		long endTime   = System.currentTimeMillis(); 
		long TotalTime = (endTime - startTime)/1000;  
		g.drawString("你一共坚持了"+TotalTime+"秒", 850, 800);//计时
		

		for(int aa=0;aa<=arrsize-1;aa++)
		{

			if(arrdown[aa])	
				arrpositionx[aa]=arrpositionx[aa]+10;
			else
				arrpositionx[aa]=arrpositionx[aa]-10;
			if(arrpositionx[aa]<0)	arrdown[aa]=true;
			if(arrpositionx[aa]>width)	arrdown[aa]=false;
			
			if(arrup[aa])	
				arrpositiony[aa]=arrpositiony[aa]+10;
			else
				arrpositiony[aa]=arrpositiony[aa]-10;
			if(arrpositiony[aa]<0)	arrup[aa]=true;
			if(arrpositiony[aa]>height)	arrup[aa]=false;
	
			g.setColor(Color.red);	
			g.fillOval(arrpositionx[aa], arrpositiony[aa], 20, 20);
			
		}
		//使arrsize个小球在width和height内碰撞，
		for(int bb=0;bb<arrsize;bb++)
		{
			explosive(planex,planey);
		}
		g.setColor(Color.black);
		if(Missionfail==1)
		{
			g.drawRect(850, 850, 150, 100);
			g.drawString("任务失败", 900, 900);
			
			trl.stop();
		}
		g.fillOval(planex+805, planey+805, 10, 10);
		//此语句做测试用，用于定位飞机的位置，以用于判定子弹的碰撞

	}
	
	
	
	public void run() 
	
	{
		
		
		while (Flag=true)
		{
		
			repaint();
			try 
			{
				Thread.sleep(50); 

			}
				catch (InterruptedException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public void stop() 
	{
	//	FinalTime=TotalTime;
	//	x.drawString("你的最好成绩是"+FinalTime, 200, 100);//如何录入最好成绩
		trl.stop();
		
	}
	
    public void explosive(int x,int y)//用来判断是否爆炸
	{
	
		for (int fi=0;fi<arrsize;fi++)
		{
		
			if(((x+795)<=arrpositionx[fi])&&(arrpositionx[fi]<=(x+810))&&((y+795)<=arrpositiony[fi])&&(arrpositiony[fi]<=(y+810)))
				//加800是因为plane的初始位置是（800，800）,为了和他们对齐
				
				//为了解决小球“难吃”的问题，让判定的范围变大，限制在-5~10的范围内.
				
			{
				for(int fj=0;fj<arrsize;fj++)
				{
				arrpositionx[fj]=-1000;
				arrpositiony[fj]=-1000;
				}//使所有的球消失
				
				
				Missionfail=1;//判断是否失败
				
			}
		}
			
	}	
	
}
