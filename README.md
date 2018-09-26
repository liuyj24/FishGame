# FishGame 大鱼吃小鱼游戏
 
在学习完JavaSE基础后有幸观看了马士兵老师的坦克大战教学视频,并打算沿着马老师的思路写一款小游戏来巩固JavaSE知识  
本游戏的想法来源于小时候在4399玩的一款大鱼吃小鱼小游戏,没想到现在还能找到[点击这里跳转至该4399小游戏](http://www.4399.com/flash/1876.htm#search3)

本游戏的代码还有许多可以优化的地方  
目前代码实现能力有限,在开发的过程中有一些线程方面的难点没有解决,虽然不太明白是不是我的思路有问题,具体问题在开发思路的文件中  
游戏的结局设置了BOSS

### 思考

1)存储小鱼的容器选择了ArrayList是否合理?  
ArrayList底层用数组实现,读写遍历的速度比较快,但是增删的速度较慢,是否需要换成LinkedList,其底层用链表实现,增删的速度很快,但是读取遍历的速度较慢  
在线程创建小鱼的时候会往容器中增加小鱼,小鱼移动出屏幕或者被吃掉后会从容器中删除,按照这里的思路应该用LinkedList  
但是在我方小鱼吃敌方小鱼和我方小鱼被敌方小鱼吃的方法中都需要对容器进行遍历,按这里的思路应该用ArrayList  
如何判断用哪种容器更加合理呢?

游戏展示:  
![开场动画](https://raw.githubusercontent.com/liuyj24/FishGame/master/p1.png)  
![游戏中](https://raw.githubusercontent.com/liuyj24/FishGame/master/p2.png)
![boss来临](https://raw.githubusercontent.com/liuyj24/FishGame/master/p3.png)  

2018.9.9
