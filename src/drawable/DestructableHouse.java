package drawable;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class DestructableHouse extends Drawable{

    static ArrayList<DestructableHouse> existingHouses = new ArrayList<>();

    BufferedImage currentImage, spriteSheet;
    private Random rand = new Random();

    private boolean destroyed = false;

    private final int   IMG_HEIGHT = 32,
                        IMG_WIDTH = 32,
                        offsetX = 32,
                        offsetY = 32;

    private int maxSheetX,
                maxSheetY,
                xImgPos = 0,
                yImgPos = 0;

    private int framesPassed = 0;


    public DestructableHouse(int x, int y)
    {
        super(4);

        this.sizeX = IMG_WIDTH*4;
        this.sizeY = IMG_HEIGHT*4;
        this.x = x-(sizeX/2);
        this.y = y-(sizeY/2);
        xImgPos = rand.nextInt(4);
        yImgPos = rand.nextInt(1);

        try {
            spriteSheet = ImageIO.read(this.getClass().getResource("housesheet.png"));

            maxSheetX = spriteSheet.getWidth();
            maxSheetY = spriteSheet.getHeight();

            currentImage = getCurrentSubImage();

        } catch (IOException e) {
            e.printStackTrace();
        }

        Drawable.addObjectToUpdateQueue(this);

    }

    @Override
    public void toRender(Graphics2D g)
    {
        g.drawImage(currentImage,x,y,sizeX,sizeY,null);
    }

    @Override
    public void onUpdate()
    {
        ++framesPassed;

        if(framesPassed == 60)
        {
            DestructableHouse.existingHouses.add(
                    new DestructableHouse(
                            rand.nextInt(800),
                            y-16
                    )
            );
            framesPassed = 0;
        }

    }

    private BufferedImage getCurrentSubImage()
    {
        return spriteSheet.getSubimage(offsetX*xImgPos,offsetY*yImgPos,IMG_WIDTH,IMG_HEIGHT);
    }
}
