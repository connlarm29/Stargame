/*
todo:
    Add Keylistener
    Add Player
    Add house growing function
 */

package src;

import drawable.*;
import sun.security.krb5.internal.crypto.Des;

public class Main
{

    public static void main(String[] args)
    {
        RenderManager renderManager = new RenderManager();
        WindowManager.initWindow();

        //Adds the canvas on which to render
        WindowManager.addComponent(renderManager);
        WindowManager.refreshWindow();

        //adds example drawable
        //SpinningMeme p = new SpinningMeme();
        Background background = new Background();
        Ground ground = new Ground();
        ShootingStar shootingStar1 = new ShootingStar(400,64,-1);

        DestructableHouse houseTest = new DestructableHouse(400,410);
        RenderManager.addObjectToRenderQueue(houseTest);

        RenderManager.addObjectToRenderQueue(ground);
        RenderManager.addObjectToRenderQueue(background);
        RenderManager.addObjectToRenderQueue(shootingStar1);
        Drawable.spawnLogicThread();

        /*
        Starts the render manager in a new thread, allowing the window
        to be updated consitently while the logic code runs
        independently.
         */
        renderManager.startRenderThread(60);


    }

}
