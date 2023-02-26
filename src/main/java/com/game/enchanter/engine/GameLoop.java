package com.game.enchanter.engine;

import static com.game.enchanter.consts.Consts.FRAME_PERIOD;
import static com.game.enchanter.consts.Consts.MAX_FRAME_SKIPS;

import com.game.enchanter.entities.interfaces.Renderable;
import com.game.enchanter.graphics.Renderer;

public class GameLoop {

    private boolean running;
    private Thread thread;
	public Renderer<Renderable> renderer;	

    public void start() {
        running = true;
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                gameLoop();
            }
        });
        thread.start();
    }

    public void stop() {
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void gameLoop() {
        long beginTime = System.currentTimeMillis();
        long timeDiff;
        int sleepTime;
        int framesSkipped;        

		renderer = new Renderer<Renderable>(3);

        while (running) {
            framesSkipped = 0;

            // Update the game state
            updateGameState();

            // Render the game state
            renderGameState();

            // Calculate how long to sleep before the next frame
            timeDiff = System.currentTimeMillis() - beginTime;
            sleepTime = (int) (FRAME_PERIOD - timeDiff);

            // If we're behind schedule, skip some frames
            while (sleepTime < 0 && framesSkipped < MAX_FRAME_SKIPS) {
                updateGameState();
                sleepTime += FRAME_PERIOD;
                framesSkipped++;
            }

            // If we're still behind schedule, sleep for a bit
            if (sleepTime < 0) {
                sleepTime = 0;
            }

            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Record the start time of the next frame
            beginTime = System.currentTimeMillis();
        }
    }

    private void updateGameState() {
        // TODO: Implement game state update logic
    }

    private void renderGameState() {
        // TODO: Implement game state rendering logic
    }
}