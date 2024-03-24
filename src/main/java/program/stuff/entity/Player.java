package program.stuff.entity;

import javax.imageio.ImageIO;
import java.util.Iterator;


import program.stuff.GamePanel;
import program.stuff.util.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class Player extends Entity {

    private final GamePanel gamePanel;
    private final KeyHandler keyHandler;
    private final int screenX;
    private final int screenY;
    private int numberOfKeys = 0;
    private ArrayList<Fireball> fireballs = new ArrayList<>();


    public Player(GamePanel gamePanel, KeyHandler keyHandler) {
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;

        this.screenX = gamePanel.getScreenWidth() / 2 - (gamePanel.getTileSize() / 2);
        this.screenY = gamePanel.getScreenHeight() / 2 - (gamePanel.getTileSize() / 2);

        setCollision();
        setDefaultValues();
        getPlayerImage();
    }

    private void setCollision() {
        setCollisionArea(new Rectangle(8, 16, 32, 32));
        setCollisionDefaultX(getCollisionArea().x);
        setCollisionDefaultY(getCollisionArea().y);
    }

    public void setDefaultValues() {
        setWorldX(gamePanel.getTileSize() * 12);
        setWorldY(gamePanel.getTileSize() * 18);
        setSpeed(4);
        setDirection("down");
    }

    public void getPlayerImage() {

        try {
            setUp1(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/images/player/boy_up_1.png"))));
            setUp2(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/images/player/boy_up_2.png"))));
            setDown1(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/images/player/boy_down_1.png"))));
            setDown2(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/images/player/boy_down_2.png"))));
            setLeft1(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/images/player/boy_left_1.png"))));
            setLeft2(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/images/player/boy_left_2.png"))));
            setRight1(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/images/player/boy_right_1.png"))));
            setRight2(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/images/player/boy_right_2.png"))));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {

        System.out.println("Player Position - X: " + getWorldX() + ", Y: " + getWorldY());

        if (keyHandler.isUpPressed() || keyHandler.isDownPressed()
                || keyHandler.isLeftPressed() || keyHandler.isRightPressed()) {

            if (keyHandler.isUpPressed()) {
                setDirection("up");
            }
            if (keyHandler.isDownPressed()) {
                setDirection("down");
            }
            if (keyHandler.isLeftPressed()) {
                setDirection("left");
            }
            if (keyHandler.isRightPressed()) {
                setDirection("right");
            }


            checkCollision();
            moveIfCollisionNotDetected();
            checkAndChangeSpriteAnimationImage();
        }

        if (keyHandler.isFirePressed()) {
            fire(); // Call a method to create a fireball
        }

        Iterator<Fireball> iterator = fireballs.iterator();
        while (iterator.hasNext()) {
            Fireball fireball = iterator.next();
            fireball.update();
            // Check for removal conditions, such as collision or going off-screen
            // if (shouldRemoveFireball) {
            //     iterator.remove();
            // }
            System.out.println("Fireball Position - X: " + fireball.getWorldX() + ", Y: " + fireball.getWorldY());

        }
    }

    /*
    private void fire() {
        int offset = 48; // Adjust this value as needed for visibility
        int fireballX = this.getWorldX() + offset; // Offset for visibility
        int fireballY = this.getWorldY();
        int moveX = 0;
        int moveY = 0;

        switch (getDirection()) {
            case "up":    moveY = -1; break;
            case "down":  moveY = 1; break;
            case "left":  moveX = -1; break;
            case "right": moveX = 1; break;
        }

        Fireball fireball = new Fireball(fireballX, fireballY, moveX, moveY);
        fireballs.add(fireball);
        System.out.println("Fireball fired: X=" + fireballX + " Y=" + fireballY); // Debugging fire action
    }*/

    private void fire() {
        // Adjust these values according to your actual sprite sizes
        int playerWidth = gamePanel.getTileSize(); // Width of the player sprite
        int playerHeight = gamePanel.getTileSize(); // Height of the player sprite
        int fireballWidth = 16; // Width of the fireball sprite
        int fireballHeight = 16; // Height of the fireball sprite
    
        // Calculate offsets to center the fireball relative to the player's facing direction
        int offsetX = (playerWidth - fireballWidth) / 2;
        int offsetY = (playerHeight - fireballHeight) / 2;
    
        // Initial fireball position - centered on the player
        int fireballX = this.getWorldX();
        int fireballY = this.getWorldY();
    
        // Direction of movement for the fireball
        int moveX = 0;
        int moveY = 0;
    
        // Adjust fireball starting position based on player direction
        switch (getDirection()) {
            case "up":
                moveY = -1;
                //fireballY = this.getWorldY() - fireballHeight + offsetY; // Adjust to spawn above and centered
                break;
            case "down":
                moveY = 1;
                //fireballY = this.getWorldY() + playerHeight - offsetY; // Adjust to spawn below and centered
                break;
            case "left":
                moveX = -1;
                //fireballX = this.getWorldX() - fireballWidth + offsetX; // Adjust to spawn to the left and centered
                break;
            case "right":
                moveX = 1;
                //fireballX = this.getWorldX() + playerWidth - offsetX; // Adjust to spawn to the right and centered
                break;
        }
    
        Fireball fireball = new Fireball(fireballX, fireballY, moveX, moveY);
        Fireball fireball2 = new Fireball(360, 280, moveX, moveY);
        fireballs.add(fireball2);
    }
    
    

    private void checkCollision() {
        setCollisionOn(false);
        gamePanel.getCollisionChecker().checkTile(this);
        int objectIndex = gamePanel.getCollisionChecker().checkObject(this, true);
        pickUpObject(objectIndex);
    }

    private void pickUpObject(int index) {

        if (index != 999) {
            String objectName = gamePanel.getObjects()[index].getName();

            switch (objectName) {
                case "Key" -> {
                    gamePanel.getObjects()[index] = null;
                    numberOfKeys++;
                    System.out.println("Keys: " + numberOfKeys);
                }
                case "Door" -> {
                    if (numberOfKeys > 0) {
                        gamePanel.getObjects()[index] = null;
                        numberOfKeys--;
                        System.out.println("Keys: " + numberOfKeys);
                    }
                }
            }
        }
    }

    private void moveIfCollisionNotDetected() {
        if (!isCollisionOn()) {
            switch (getDirection()) {
                case "up" -> setWorldY(getWorldY() - getSpeed());
                case "down" -> setWorldY(getWorldY() + getSpeed());
                case "left" -> setWorldX(getWorldX() - getSpeed());
                case "right" -> setWorldX(getWorldX() + getSpeed());
            }
        }
    }

    private void checkAndChangeSpriteAnimationImage() {
        setSpriteCounter(getSpriteCounter() + 1);
        if (getSpriteCounter() > 12) {
            if (getSpriteNumber() == 1) {
                setSpriteNumber(2);
            } else if (getSpriteNumber() == 2) {
                setSpriteNumber(1);
            }
            setSpriteCounter(0);
        }
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        graphics2D.drawImage(getDirectionalAnimationImage(), screenX, screenY, gamePanel.getTileSize(), gamePanel.getTileSize(), null);
        graphics2D.setColor(Color.RED); // Set color for visibility


        // Draw fireballs
        for (Fireball fireball : fireballs) {
            graphics2D.setColor(Color.BLUE); // Fireball color
            graphics2D.drawRect(fireball.getWorldX(), fireball.getWorldY(), 16, 16); // Assuming 16x16 fireballs
            fireball.draw(graphics2D); // Draw the actual fireball
        }
    }

    private BufferedImage getDirectionalAnimationImage() {
        BufferedImage image = null;

        switch (getDirection()) {
            case "up" -> {
                if (getSpriteNumber() == 1)
                    image = getUp1();
                if (getSpriteNumber() == 2)
                    image = getUp2();
            }
            case "down" -> {
                if (getSpriteNumber() == 1)
                    image = getDown1();
                if (getSpriteNumber() == 2)
                    image = getDown2();
            }
            case "left" -> {
                if (getSpriteNumber() == 1)
                    image = getLeft1();
                if (getSpriteNumber() == 2)
                    image = getLeft2();
            }
            case "right" -> {
                if (getSpriteNumber() == 1)
                    image = getRight1();
                if (getSpriteNumber() == 2)
                    image = getRight2();
            }
        }
        return image;
    }

    public int getScreenX() {
        return screenX;
    }

    public int getScreenY() {
        return screenY;
    }

    public int getNumberOfKeys() {
        return numberOfKeys;
    }

    public Player setNumberOfKeys(int numberOfKeys) {
        this.numberOfKeys = numberOfKeys;
        return this;
    }
}
