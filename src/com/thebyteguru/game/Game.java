package com.thebyteguru.game;


import com.thebyteguru.IO.Input;
import com.thebyteguru.display.Display;
import com.thebyteguru.graphics.Sprite;
import com.thebyteguru.graphics.SpriteSheet;
import com.thebyteguru.graphics.TextureAtlas;

import java.awt.*;
import java.awt.event.KeyEvent;

import static sun.audio.AudioPlayer.player;

public class Game implements Runnable {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    public static final String TITLE = "Tanks";
    public static final int CLEAR_COLOR = 0xff000000;
    public static final int NUM_BUFFERS = 3;
    public static final float UPDATE_RATE = 60.0f;
    public static final float UPDATE_INTERVAL = com.thebyteguru.utils.Time.SECOND / UPDATE_RATE;
    public static final long IDLE_TIME = 1;//для запуска нового срэда (продышка)

    public static final String ATLAS_FILE_NAME = "texture_atlas.png";
    private boolean running;
    private Thread gameThread;
    private Graphics2D graphics;
    private Input input;
    private TextureAtlas atlas;
    private Player player;
//    private SpriteSheet sheet;
//    private Sprite sprite;


//    //temp
//    float x = 350;
//    float y = 250;
//    float delta = 0;
//    float radius = 50;
//    float speed = 2;
//    //temp end


    public Game() {
        running = false;
        Display.create(WIDTH, HEIGHT, TITLE, CLEAR_COLOR, NUM_BUFFERS);
        graphics = Display.getGraphics();
        input = new Input();
        Display.addInputListener(input);
        atlas = new TextureAtlas(ATLAS_FILE_NAME);
        player =  new Player(300, 300, 2, 3, atlas);
//        sheet = new SpriteSheet(atlas.cut(1 * 16, 9 * 16, 16 * 2, 16), 2, 16);
//        sprite = new Sprite(sheet,1);

    }

    public synchronized void start() {
        if (running)
            return;
        running = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    public synchronized void stop() {
        if (!running)
            return;
        running = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        claenUp();
    }

    private void update() {
        player.update(input);
//
//        if (input.getKey(KeyEvent.VK_UP))
//            y -= speed;
//        if (input.getKey(KeyEvent.VK_DOWN))
//            y += speed;
//        if (input.getKey(KeyEvent.VK_LEFT))
//            x -= speed;
//        if (input.getKey(KeyEvent.VK_RIGHT))
//            x += speed;


    }

    private void render() {
        Display.clear();
        player.render(graphics);

        //  graphics.setColor(Color.white);
        // graphics.drawImage(atlas.cut(0,0,16,16), 300,300, null);

        //  sprite.render(graphics,x,y);

        Display.swapBuffers();

    }

    public void run() {
        int fps = 0;
        int upd = 0;
        int updl = 0;

        long count = 0;


        float delta = 0;

        long lastTime = com.thebyteguru.utils.Time.get();
        while (running) {
            long now = com.thebyteguru.utils.Time.get();
            long elapseTime = now - lastTime;

            count += elapseTime;

            boolean render = false;
            delta += (elapseTime / UPDATE_INTERVAL);
            while (delta > 1) {
                update();
                upd++;
                delta--;
                if (render) {
                    updl++;
                } else {
                    render = true;
                }
            }
            if (render) {
                render();
                fps++;
            } else {
                try {
                    Thread.sleep(IDLE_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (count >= com.thebyteguru.utils.Time.SECOND) {
                Display.setTitle(TITLE + "|| + Fps:" + fps + "| Upd:" + upd + " | Updl:" + updl);
                upd = 0;
                fps = 0;
                updl = 0;
                count = 0;
            }

        }


    }

    private void claenUp() {
        Display.destroy();
    }


}
