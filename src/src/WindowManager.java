package src;

import javax.swing.*;
import java.time.LocalTime;
import java.util.ArrayList;

public class WindowManager extends RenderManager
{

    static JFrame gameWindow = new JFrame();
    public static final int  WIN_WIDTH = 800;
    public static final int  WIN_HEIGHT = 600;

    public static boolean exitStatus = false;

    private static ArrayList<JComponent> activeComponents = new ArrayList<>();

    /*
    Initializes the window. This can be called from any class,
    but should only be called once by the main class.
     */
    static void initWindow()
    {
        gameWindow.setSize(WindowManager.WIN_WIDTH, WindowManager.WIN_HEIGHT);
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameWindow.setResizable(false);
        gameWindow.setTitle("I need sleep");
        gameWindow.setVisible(true);
        System.out.println("I ["+LocalTime.now()+
                "] Successfully initialized game window.");
    }
    /*
    Adds a component to the JFrame window.
    Can be called from any class.
     */
    static void addComponent(JComponent comp)
    {
        if (!WindowManager.activeComponents.contains(comp))
        {
            WindowManager.activeComponents.add(comp);
            gameWindow.add(comp);
            gameWindow.repaint();
            System.out.println("I ["+LocalTime.now()+"] Added component ["+
                    comp.getName()+"] to the game Window.");
        }else {System.out.println("E ["+LocalTime.now()+"] Failed to add component, already exists!");}
    }
    

    /*
    Removes a component to the JFrame window.
    Can be called from any class.
     */
    static void removeComponent(JComponent comp)
    {
        if (WindowManager.activeComponents.contains(comp))
            {System.out.println("E ["+LocalTime.now()+
                    "] Failed to remove component, component doesnt exist!");}
        else {
            WindowManager.activeComponents.remove(comp);
            gameWindow.remove(comp);
            gameWindow.repaint();
            System.out.println("I [" + LocalTime.now() + "] Removed component [" +
                    comp.getName() + "] from the game Window.");
        }
    }

    /*
    Allows program to retrieve active JComponents from outside
    this class.
     */
    static ArrayList<JComponent> getActiveComponents()
    {
        return WindowManager.activeComponents;
    }

    /*
    Refreshes the window and packs it to
    the size of its components.
    Can be called from any class.
     */
    static void refreshWindow()
    {
        gameWindow.pack();
        gameWindow.repaint();
    }

}
