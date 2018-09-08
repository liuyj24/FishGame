package game;

import java.util.Random;
/**
 * 从游戏窗口的左边添加小鱼的线程类,注释请看AddFishFromright类
 * @author liuyj
 *
 */
public class AddFishFromLeft implements Runnable {
	private FishFrame ff;
	private Random r = new Random();
	
	public AddFishFromLeft(FishFrame ff) {
		this.ff = ff;
	}

	public void run() {
		while(ff.myFish.scores < 420){
			//int position = r.nextInt(ff.GAME_HEIGHT);
			int width = getWidthByMyFish();
			int position = getPositionByWidth(width);
			int speed = getSpeedByMyFish();
			EnemyFish ef1 = new EnemyFish(0 - 300, position, width, width,
					ff, Direction.R, speed, ff.myFish, EnemyFish.images[r.nextInt(3) + 3]);
			ff.fishList_Left.add(ef1);
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		addBoss();
	}

	private int getPositionByWidth(int width) {
		return (r.nextInt(ff.GAME_HEIGHT - width - 31) + 31);
	}

	private void addBoss() {
		int radius = FishFrame.GAME_HEIGHT - 100;
		int speed = 5;
		EnemyFish ef = new EnemyFish(0 - FishFrame.GAME_WIDTH, 50, radius, radius,
				ff, Direction.R, speed, ff.myFish, EnemyFish.images[EnemyFish.images.length-1]);
		ff.fishList_Left.add(ef);

	}

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
