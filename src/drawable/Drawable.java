package drawable;


import src.WindowManager;

import java.awt.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Drawable {

    private static List<Drawable> updateQueue = Collections.synchronizedList(new ArrayList());
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

    static synchronized void addObjectToUpdateQueue(Drawable obj)
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

    public static synchronized void removeObjectFromUpdateQueue(Drawable objectToRemove)
    {
        if(Drawable.updateQueue.contains(objectToRemove))
        {
            Drawable.updateQueue.remove(objectToRemove);
            System.out.println("I ["+ LocalTime.now()+
                    "] Successfully deleted [."+objectToRemove.toString()+
                    "] from drawable updateQueue.");
        }
        else
        {
            System.out.println("E ["+ LocalTime.now()+
                    "] Could not delete [."+objectToRemove.toString()+
                    "] from drawable updateQueue, doesn't exist!");
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

//                        for (Iterator<Drawable> it = iterator; it.hasNext(); ) {
//                            Drawable d = it.next();
//                            d.onUpdate();
//
//                        }
//                        for(Drawable d : Drawable.updateQueue)
//                        {
//                            d.onUpdate();
//                        }
                        for(int i = 0; i < Drawable.updateQueue.size(); i++)
                        {
                            Drawable.updateQueue.get(i).onUpdate();
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
