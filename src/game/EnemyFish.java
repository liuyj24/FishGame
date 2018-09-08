package game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.Random;

/**
 * 敌方小鱼类
 * @author liuyj
 *
 */
public class EnemyFish extends GameObject {
	
	public MyFish OurEnemy;//敌军小鱼的敌人就是玩家的小鱼,用于判断是否能吃掉玩家的小鱼
	private Image image;//小鱼自己的图片
	
	/**
	 * 加载敌军小鱼的图片
	 */
	private static Random r = new Random();
	public static Image[] images = null;
	public static Toolkit tk = Toolkit.getDefaultToolkit();
	static {
		images = new Image[] {
			tk.createImage(EnemyFish.class.getClassLoader().getResource("images/enemyFish/fish1_l.gif")),
			tk.createImage(EnemyFish.class.getClassLoader().getResource("images/enemyFish/fish2_l.gif")),
			tk.createImage(EnemyFish.class.getClassLoader().getResource("images/enemyFish/fish3_l.gif")),
			tk.createImage(EnemyFish.class.getClassLoader().getResource("images/enemyFish/fish1_r.gif")),
			tk.createImage(EnemyFish.class.getClassLoader().getResource("images/enemyFish/fish2_r.gif")),
			tk.createImage(EnemyFish.class.getClassLoader().getResource("images/enemyFish/fish3_r.gif")),
			tk.createImage(EnemyFish.class.getClassLoader().getResource("images/enemyFish/boss.gif"))
		};
	}

	public EnemyFish(int x, int y, int width, int height,
			FishFrame ff, Direction dir, int speed, MyFish OurEnemy, Image image) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.ff = ff;
		this.dir = dir;
		this.xSpeed = speed;
		this.OurEnemy = OurEnemy;
		this.image = image;
	}
	
	/**
	 * 画出小鱼的方法
	 * @param g
	 */
	public void draw(Graphics g) {
		if(!live) {
			if(dir.equals(Direction.R)){
				ff.fishList_Left.remove(this);//如果小鱼死亡就从容器中移除
			}else{
				ff.fishList_Right.remove(this);//如果小鱼死亡就从容器中移除
			}
			return;//小鱼死亡则不再画出
		}
		
		g.drawImage(image, x, y, width, height, null);//画出自己的图片
		
		move();//小鱼位置变化
	}
	
	/**
	 * 小鱼移动的方法
	 */
	private void move() {
		if(dir.equals(Direction.L)) {
			x -= xSpeed;//如果小鱼的方向是朝左的,x坐标递减向左移动
		}else {
			x += xSpeed;//否则向右移动
		}
		
		//小鱼如果走出边界则判断为死亡
		if(dir.equals(Direction.R)){
			if(x > ff.GAME_WIDTH) this.live = false;//向右的小鱼,如果x坐标大于窗口宽度则死亡
		}else{
			if(x < 0) this.live = false;//向左的小鱼,如果x坐标小于窗口宽度则死亡
		}
	}
}
