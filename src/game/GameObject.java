package game;

import java.awt.Rectangle;
import java.util.Random;

/**
 * 游戏物体父类
 * @author liuyj
 *
 */
public class GameObject {
	public int x, y;//游戏物体的坐标
	public Direction dir;//游戏物体的方向
	public int xSpeed, ySpeed;//游戏物体在x,y方向上位移的速度
	public int width, height;//游戏物体的宽和高
	public boolean live = true;//游戏物体的生命,初始为true
	public FishFrame ff;//游戏主框架
	public static Random r = new Random();//提供给本类使用的随机数产生器
	
	public GameObject() {
	}

	public GameObject(int x, int y, Direction dir, int xSpeed, int ySpeed, int width, int height, boolean live) {
		super();
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
		this.width = width;
		this.height = height;
		this.live = live;
	}
	
	/**
	 * 获得当前对象的矩形,用于碰撞检测
	 * @return
	 */
	public Rectangle getRectangle() {
		return new Rectangle(x, y, width, height);
	}
}
