package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FishFrame extends Frame {
	public static final int GAME_WIDTH = 1200;//游戏窗口的宽
	public static final int GAME_HEIGHT = 800;//游戏窗口的高
	private Image offScreenImage = null;//在paint方法进行画图前保存图画,以便一次画出,消除双缓冲现象
	public static Random r = new Random();//创建一个随机数生成器
	private boolean isStart = false;//判断游戏是否开始的变量
	MyFish myFish = new MyFish(GAME_WIDTH/2, GAME_HEIGHT/2, this);//创建一条我的小鱼
	List<EnemyFish> fishList_Left = new ArrayList<EnemyFish>();//装小鱼的集合
	List<EnemyFish> fishList_Right = new ArrayList<EnemyFish>();//装小鱼的集合

	/**
	 * 画出图片
	 */
	public void paint(Graphics g) {
		/*
		 * 通过isStart变量判断游戏是否开始,没有开始的话画出欢迎界面
		 */
		if(!isStart){
			draw_welcome(g);
			return;
		}
		
		//画出我方小鱼,如果我方小鱼吃掉了敌军小鱼会有GOOD字样显示
		myFish.draw(g);
		if(myFish.eatFishs(fishList_Left) || myFish.eatFishs(fishList_Right)){
			for(int i = 0; i < 5; i++){
				g.drawString("GOOD!", myFish.x, myFish.y);
			}
		}
		//设置我方小鱼可以被吃掉
		myFish.beEaten(fishList_Left);
		myFish.beEaten(fishList_Right);
		
		//画出敌方小鱼
		for(int i = 0; i < fishList_Left.size(); i++) {
			EnemyFish e = fishList_Left.get(i);
			e.draw(g);
		}
		
		for(int i = 0; i < fishList_Right.size(); i++) {
			EnemyFish e = fishList_Right.get(i);
			e.draw(g);
		}
		
		//画出当前小鱼数量和当前玩家积分字样
		g.setFont(new Font("宋体", Font.BOLD, 25));
		g.drawString("fish account : " + (fishList_Left.size()+fishList_Right.size()), 10, 50);
		g.drawString("you scores : " + myFish.scores, 10, 70);
		if(myFish.scores > 380) {
			g.drawString("你以为你真的能变成一条肥鱼吃遍天下吗? Too Young! Too Simple!", 200, 350);
		}
		
		//当我方小鱼阵亡时写出GAME OVER字样
		if(!myFish.live){
			g.setColor(Color.RED);
			g.setFont(new Font("宋体", Font.BOLD, 70));
			g.drawString("GAME OVER!!!", 300, 400);
		}
	}
	
	/**
	 * 画出欢迎界面
	 * @param g
	 */
	private void draw_welcome(Graphics g) {
		g.setColor(Color.RED);
		g.setFont(new Font("宋体", Font.BOLD, 75));
		g.drawString("Welcome to the FishGame!!!", 100, 150);
		g.setFont(new Font("宋体", Font.BOLD, 40));
		g.setColor(Color.YELLOW);
		g.drawString("How to play:", 200, 350);
		g.drawString("press W,A,S,D to move,", 200, 400);
		g.drawString("you can eat the samller ones,", 200, 450);
		g.drawString("but be careful to the big ones.", 200, 500);
		g.drawString("The boss is waiting for you, Let's go!", 200, 600);
		g.drawString("Press Enter to start...", 200, 650);
	}
	
	/**
	 * 游戏初始化
	 */
	private void launchFrame() {
		this.setLocation(50, 50);//设置游戏窗口的位置
		this.setSize(GAME_WIDTH, GAME_HEIGHT);//设置游戏窗口的宽高
		this.setVisible(true);//设置游戏窗口可见
		this.setResizable(false);//设置游戏窗口大小不可改变
		this.setTitle("FishGame");//设置游戏窗口标题
		
		this.addWindowListener(new WindowAdapter() {//添加窗口监听
			public void windowClosing(WindowEvent e) {//匿名内部类监听窗口关闭事件
				System.exit(0);//窗口关闭游戏退出
			}
		});
		this.addKeyListener(new KeyMonitorForStart());//添加键盘监听,看游戏是否开始
		this.addKeyListener(new KeyMonitor());//添加键盘监听
	
		new Thread(new RepaintThread()).start();//创建重画线程对象进行重画
	
		new Thread(new AddFishFromLeft(this)).start();//添加向右移动的小鱼
		new Thread(new AddFishFromRight(this)).start();//添加向左移动的小鱼
		
		//通过当前积分改变我方小鱼的移动速度
		changeMyFishSpeedByScores();
	}

	/**
	 * 根据积分改变稍微提高小鱼的速度
	 */
	private void changeMyFishSpeedByScores() {
		//先获取当前MyFish的积分
		int score = this.myFish.scores;//根据积分设置MyFish的速度
		if(score >= 0 && score <50) {
			addSpeed();
		}else if(score >= 50 && score < 100) {
			addSpeed();
		}else if(score >= 100 && score < 200){
			addSpeed();
		}else{
			addSpeed();
			addSpeed();
		}
	}
	/**
	 * 提高速度
	 */
	private void addSpeed() {
		myFish.xSpeed++;
		myFish.ySpeed++;
	}

	/**
	 * 设置游戏背景
	 */
	private void drawBackground(Graphics g) {
		Toolkit tk = Toolkit.getDefaultToolkit();//拿到当前系统默认的Toolkit
		Image background = tk.getImage(this.getClass()
				.getClassLoader().getResource("images/background/sea.jpg"));
		g.drawImage(background, 0, 0, null);
	}

	/**
	 * 消除双缓冲现象
	 */
	public void update(Graphics g) {
		if(null == offScreenImage) {//判断offScreenImage是否为空,为空则创建一张准备图片
			offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
		}
		Graphics og = offScreenImage.getGraphics();
		drawBackground(og);
		paint(og);//执行paint方法,把要画的图片画到缓冲的offScreenImage上
		g.drawImage(offScreenImage, 0, 0, null);//在屏幕上画出offScreenImage
	}

	/**
	 * 重画的线程类
	 * @author liuyj
	 *
	 */
	class RepaintThread implements Runnable{
		public void run() {
			while(true) {
				repaint();//启动线程后进行重画
				try {
					Thread.sleep(30);//每隔50毫秒重画一次
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 键盘监听内部类,用于改变鱼的位移,让鱼动起来
	 * @author liuyj
	 *
	 */
	class KeyMonitor extends KeyAdapter{
		public void keyPressed(KeyEvent e) {
			myFish.keyPressed(e);
		}

		public void keyReleased(KeyEvent e) {
			myFish.keyRelease(e);
		}
	}
	
	/**
	 * 键盘监听,监听是否按下了回车键,按下回车键设置isStart为true,游戏开始
	 * @author liuyj
	 *
	 */
	class KeyMonitorForStart extends KeyAdapter{
		
		public void keyPressed(KeyEvent e) {
			int key = e.getExtendedKeyCode();
			if(key == KeyEvent.VK_ENTER){
				isStart = true;
			}
		}
	}
	
	/**
	 * 主方法
	 * @param args
	 */
	public static void main(String[] args) {
		FishFrame fishFrame = new FishFrame();//创建本类对象
		fishFrame.launchFrame();//加载游戏
	}
}
