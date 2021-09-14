package drawable;

import src.RenderManager;
import src.WindowManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class HouseObjectSpawner {

    private int currentX = 0,
                currentY = 0;
    private int framesPassed = 0;
    Random rand = new Random();
    DestructableHouse temp;

    static List<DestructableHouse> currentHouses = Collections.synchronizedList(new ArrayList<>());

    public HouseObjectSpawner()
    {
        currentY = 410;
        startHouseThread();
    }

    private synchronized void spawnHouse(int x, int y)
    {
        temp = new DestructableHouse(x,y);
        currentHouses.add(temp);
        RenderManager.addObjectToRenderQueue(temp);

    }

    private void onUpdate()
    {
        ++framesPassed;

        if(framesPassed == 60)
        {
            spawnHouse(rand.nextInt(800),currentY);
            framesPassed = 0;
        }
    }

    public static synchronized void removeHouseFromList(DestructableHouse d)
    {
        currentHouses.remove(d);
        RenderManager.removeObjectFromRenderQueue(d);
        Drawable.removeObjectFromUpdateQueue(d);
    }

    private void startHouseThread()
    {
        new Thread(() ->
        {
            while(!WindowManager.exitStatus) {
                long startTime = System.currentTimeMillis();
                this.onUpdate();

                long deltaTime = System.currentTimeMillis()-startTime;

                if (deltaTime < 16) {
                    try {
                        Thread.sleep(16);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        }).start();
    }

}
