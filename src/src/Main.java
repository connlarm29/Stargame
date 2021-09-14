package src;

import drawable.*;

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
        Tree tree1 = new Tree(200,300,-1);

        RenderManager.addObjectToRenderQueue(ground);
        //RenderManager.addObjectToRenderQueue(p);
        RenderManager.addObjectToRenderQueue(background);
        RenderManager.addObjectToRenderQueue(tree1);
        Drawable.spawnLogicThread();

        /*
        Starts the render manager in a new thread, allowing the window
        to be updated consitently while the logic code runs
        independently.
         */
        renderManager.startRenderThread(60);


    }

}
