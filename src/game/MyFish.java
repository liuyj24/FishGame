package game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.List;

public class MyFish extends GameObject{
	
	boolean bU = false, bR = false, bD = false, bL = false;//四个方向的键是否被按下的布尔量
	public int scores = 0;//玩家的游戏积分
	
	/**
	 * 加载我方小鱼的图片
	 */
	private static Toolkit tk = Toolkit.getDefaultToolkit();//拿到系统的工具包
	private static Image myFish_left = null;//朝左的图片
	private static Image myFish_right = null;//朝右的图片
	
	//在静态代码区中完成对图片的加载
	static{
		myFish_left = tk.createImage(MyFish.class.getClassLoader().getResource("images/myFish/myfish_left.gif"));
		myFish_right = tk.createImage(MyFish.class.getClassLoader().getResource("images/myFish/myfish_right.gif"));
	}


	public MyFish(int x, int y, FishFrame ff) {
		super();
		this.x = x;
		this.y = y;
		this.xSpeed = 5;
		this.ySpeed = 5;
		this.dir = Direction.STOP;
		this.width = 40;
		this.height = 40;
		this.ff = ff;
	}

	public void draw(Graphics g) {
		if(!live) return;
		if(dir == Direction.U || dir == Direction.RU || dir == Direction.R || dir == Direction.RD){
			g.drawImage(myFish_right, x, y, width, height, null);
		}else{
			g.drawImage(myFish_left, x, y, width, height, null);
		}
		move();//每画一次就改变鱼的坐标
	}

	/**
	 * 按下键盘鱼的方向改变
	 * @param e
	 */
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch(key) {
		case KeyEvent.VK_W :
			bU = true;
			break;
		case KeyEvent.VK_D :
			bR = true;
			break;
		case KeyEvent.VK_S :
			bD = true;
			break;
		case KeyEvent.VK_A :
			bL = true;
			break;
		}
		getDirection();//获得鱼当前的方向
	}
	
	/**
	 * 释放键盘鱼的四个方向布尔值设置为false
	 * @param e
	 */
	public void keyRelease(KeyEvent e) {
		int key = e.getKeyCode();
		switch(key) {
		case KeyEvent.VK_W :
			bU = false;
			break;
		case KeyEvent.VK_D :
			bR = false;
			break;
		case KeyEvent.VK_S :
			bD = false;
			break;
		case KeyEvent.VK_A :
			bL = false;
			break;
		}
		getDirection();//获得鱼当前的方向
	}

	/**
	 * 根据四个方向的布尔量判断小鱼的方向
	 */
	private void getDirection() {
		if(bU && !bR && !bD && !bL) dir = Direction.U;
		else if(bU && bR && !bD && !bL) dir = Direction.RU;
		else if(!bU && bR && !bD && !bL) dir = Direction.R;
		else if(!bU && bR && bD && !bL) dir = Direction.RD;
		else if(!bU && !bR && bD && !bL) dir = Direction.D;
		else if(!bU && !bR && bD && bL) dir = Direction.LD;
		else if(!bU && !bR && !bD && bL) dir = Direction.L;
		else if(bU && !bR && !bD && bL) dir = Direction.LU;
		else if(!bU && !bR && !bD && !bL) dir = Direction.STOP;
	}
	
	/**
	 * 移动,即改变坐标
	 */
	private void move() {
		switch(dir) {
		case U :
			y -= ySpeed;
			break;
		case RU :
			y -= ySpeed;
			x += xSpeed;
			break;
		case R :
			x += xSpeed;
			break;
		case RD :
			y += ySpeed;
			x += xSpeed;
			break;
		case D :
			y += ySpeed;
			break;
		case LD :
			y += ySpeed;
			x -= xSpeed;
			break;
		case L :
			x -= xSpeed;
			break;
		case LU :
			y -= ySpeed;
			x -= xSpeed;
			break;
		}
		
		//防止鱼出界
		if(x < 2) x = 2;
		if(y < 31) y = 31;
		if(x > FishFrame.GAME_WIDTH - width) x = FishFrame.GAME_WIDTH - width;
		if(y > FishFrame.GAME_HEIGHT - height) y = FishFrame.GAME_HEIGHT - height;
	}

	/**
	 * 通过两条小鱼的碰撞检测判断是否吃掉小鱼
	 * @param ef
	 * @return
	 */
	public boolean eatFish(EnemyFish ef) {
		if(this.live && ef.live && this.getRectangle().intersects(ef.getRectangle()) 
				&& this.width > ef.width) {
			ef.live = false;
			int radius = r.nextInt(3) + 5;
			this.width += radius;
			this.height += radius;
			this.scores += r.nextInt(15);
			return true;
		}
		return false;
	}
	
	/**
	 * 吃掉多条小鱼
	 * @param list
	 * @return
	 */
	public boolean eatFishs(List<EnemyFish> list) {
		for(EnemyFish e : list) {
			if(this.eatFish(e)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 玩家的小鱼可以被吃掉
	 * @param list
	 * @return
	 */
	public boolean beEaten(List<EnemyFish> list) {
		for(EnemyFish e : list) {
			if(live && e.live && this.getRectangle().intersects(e.getRectangle()) 
					&& this.width <= e.width){
				this.live = false;
				return true;
			}
		}
		return false;
	}
}



