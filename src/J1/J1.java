package J1;
import java.awt.*;
import java.applet.*;



public class J1 extends Applet implements Runnable{
  
	long startTime,endTime,TotalTime,FinalTime;//��¼�������е�ʱ���Լ�����ʱ�䣻
	
	boolean Flag=true;//���ڿ����̵߳������������
	
	int		planex=1,planey=1,
			movx=0,movy=0,
			width=1600,height=800,
			BottonFlag=0,
			arrsize=50,
			Missionfail=0;
	int	[] arrpositionx=new int [arrsize];
	int	[] arrpositiony=new int [arrsize];//�洢С���λ��
	
	boolean [] arrup=new boolean [arrsize];
	boolean [] arrdown=new boolean [arrsize];//ʹС����ָ������
	

	Button b1=new Button("���ɷɻ�����ʱ"),
		   b2=new Button("�����ӵ�"),
		   b3=new Button("����ָ���������ӵ�(�������ڿ����������ڴ�)");
	Label lb1=new Label("��������Ҫ��ܵ��ӵ��������ٵ�����ɣ�������ʱĬ��Ϊ20(�������ڿ����������ڴ�)");
	Label lb2=new Label("��ϰ�׶Σ����Կ���С����������ӵ�");
	TextField tf1;
	
	Image im1,im2;
	
	Thread	trl=null;

 /*public void music() {

		try {//��try�����ڲ�������
			URI uri=f.toURI();
			URL url=uri.toURL();
			AudioClip audioClip=Applet.newAudioClip(url);

			
			System.out.println("��������");//���ڼ���Ƿ��������Ƶ
			
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
			 startTime= System.currentTimeMillis();	//��¼��ʼʱ��
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
	}//�ɻ�����
		
	public void start() 

	{
		trl=new Thread(this);
		trl.start();//�߳�����

		
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
			g.drawString("��ʵ�ǡ�", 885, 820);

		}
	
	
		if ((planex<(-800))||(planex>800))
		{
			planex=planex-movx;
		}
		if ((planey<(-750))||(planey>50))
		{
			planey=planey-movy;
		}
		//���ɷɻ�
		
		long endTime   = System.currentTimeMillis(); 
		long TotalTime = (endTime - startTime)/1000;  
		g.drawString("��һ�������"+TotalTime+"��", 850, 800);//��ʱ
		

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
		//ʹarrsize��С����width��height����ײ��
		for(int bb=0;bb<arrsize;bb++)
		{
			explosive(planex,planey);
		}
		g.setColor(Color.black);
		if(Missionfail==1)
		{
			g.drawRect(850, 850, 150, 100);
			g.drawString("����ʧ��", 900, 900);
			
			trl.stop();
		}
		g.fillOval(planex+805, planey+805, 10, 10);
		//������������ã����ڶ�λ�ɻ���λ�ã��������ж��ӵ�����ײ

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
	//	x.drawString("�����óɼ���"+FinalTime, 200, 100);//���¼����óɼ�
		trl.stop();
		
	}
	
    public void explosive(int x,int y)//�����ж��Ƿ�ը
	{
	
		for (int fi=0;fi<arrsize;fi++)
		{
		
			if(((x+795)<=arrpositionx[fi])&&(arrpositionx[fi]<=(x+810))&&((y+795)<=arrpositiony[fi])&&(arrpositiony[fi]<=(y+810)))
				//��800����Ϊplane�ĳ�ʼλ���ǣ�800��800��,Ϊ�˺����Ƕ���
				
				//Ϊ�˽��С���ѳԡ������⣬���ж��ķ�Χ���������-5~10�ķ�Χ��.
				
			{
				for(int fj=0;fj<arrsize;fj++)
				{
				arrpositionx[fj]=-1000;
				arrpositiony[fj]=-1000;
				}//ʹ���е�����ʧ
				
				
				Missionfail=1;//�ж��Ƿ�ʧ��
				
			}
		}
			
	}	
	
}
