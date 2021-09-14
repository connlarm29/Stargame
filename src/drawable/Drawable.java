package drawable;


import src.WindowManager;

import java.awt.*;
import java.time.LocalTime;
import java.util.ArrayList;

public class Drawable {

    private static ArrayList<Drawable> updateQueue = new ArrayList<>();
    private static boolean updateThreadExists = false;
    private final int renderPriority;

    int     x = 0,
            y = 0,
            sizeX = 0,
            sizeY = 0;

    double rotation = 0.0;

    public Drawable(int renderPriority)
    {
        this.renderPriority = renderPriority;
    }


    public void toRender(Graphics2D objectGraphics)
    {

    }

    static void addObjectToUpdateQueue(Drawable obj)
    {
        if(!Drawable.updateQueue.contains(obj))
        {
            Drawable.updateQueue.add(obj);
            System.out.println("I ["+ LocalTime.now()+
                    "] Successfully added [."+obj.toString()+
                    "] to drawable updateQueue.");
        }
        else
        {
            System.out.println("E ["+ LocalTime.now()+
                    "] Could not add [."+obj.toString()+
                    "] to drawable updateQueue, already exists.");
        }
    }

    public void onUpdate()
    {

    }

    public int getRenderPriority()
    {
        return renderPriority;
    }
    public static void spawnLogicThread()
    {
        if(!Drawable.updateThreadExists)

        {
            Drawable.updateThreadExists = true;
            new Thread(() ->
                {
                    Thread.currentThread().setName("Object Logic Thread");
                    while (!WindowManager.exitStatus) {
                        long startTime = System.currentTimeMillis();

                        for (Drawable d : Drawable.updateQueue) {
                            d.onUpdate();
                        }

                        long deltaTime = System.currentTimeMillis() - startTime;

                        if (deltaTime < 16) {
                            try {
                                Thread.sleep(16);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            ).start();
        }
        else
        {
            System.out.println("E ["+ LocalTime.now()+
                    "] Stop! An drawable.Drawable updateThread already exists!");
        }
    }
}
