package game;

import java.util.Random;

/**
 * 从游戏窗口的右边添加小鱼的线程类
 * @author liuyj
 *
 */
public class AddFishFromRight implements Runnable {
	private FishFrame ff;
	private Random r = new Random();
	
	public AddFishFromRight(FishFrame ff) {
		this.ff = ff;
	}

	public void run() {
		while(ff.myFish.scores < 420){
			int width = getWidthByMyFish();//通过玩家的小鱼来确定敌人小鱼的大小
			int speed = getSpeedByMyFish();//通过玩家的小鱼来确定敌人小鱼的速度
			int position = getPositionByWidth(width);
			EnemyFish ef1 = new EnemyFish(ff.GAME_WIDTH + 300, position, width, width,
					ff, Direction.L, speed, ff.myFish, EnemyFish.images[r.nextInt(3)]);
			ff.fishList_Right.add(ef1);
			try {
				Thread.sleep(1500);//每隔1.5s在右边添加一条小鱼
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 通过小鱼的宽度返回小鱼的出现位置,为的是不让小鱼产生在游戏窗口的边框上
	 * @param width
	 * @return
	 */
	private int getPositionByWidth(int width) {
		return (r.nextInt(ff.GAME_HEIGHT - width - 31) + 31);
	}
	/**
	 * 通过玩家的小鱼返回敌军小鱼的宽度,玩家的小鱼太小时,敌军小鱼也不能太大
	 * @return
	 */
	private int getWidthByMyFish() {
		//先获取当前MyFish的积分
		int score = ff.myFish.scores;//根据积分设置小鱼的大小
		if(score >= 0 && score <50) {
			return r.nextInt(90) + 10;
		}else if(score >= 50 && score < 100) {
			return r.nextInt(127) + 13;
		}else if(score >= 100 && score < 200){
			return r.nextInt(160) + 15;
		}else{
			return r.nextInt(230) + 25;
		}
	}

	/**
	 * 通过玩家的小鱼返回敌军小鱼的速度,玩家的小鱼太小时,敌军小鱼也不能太快
	 * @return
	 */
	private int getSpeedByMyFish() {
		//先获取当前MyFish的积分
		int score = ff.myFish.scores;//根据积分设置小鱼的速度
		if(score >= 0 && score <50) {
			return r.nextInt(5) + 1;
		}else if(score >= 50 && score < 100) {
			return r.nextInt(9) + 1;
		}else if(score >= 100 && score < 200){
			return r.nextInt(13) + 1;
		}else{
			return r.nextInt(18) + 1;
		}
	}
}
