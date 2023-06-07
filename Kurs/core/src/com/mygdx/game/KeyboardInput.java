package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;

public class KeyboardInput extends InputAdapter
{
    private boolean upKeyPressed;
    private boolean downKeyPressed;
    private boolean leftKeyPressed;
    private boolean rightKeyPressed;
    private boolean escKeyPressed;
    private boolean mousePressed;
    private final Vector2 direction = new Vector2();
    private final Vector2 mousePosition = new Vector2();

    @Override
    public boolean keyDown(int keycode)
    {
        if (keycode == Input.Keys.A) leftKeyPressed = true;
        if (keycode == Input.Keys.D) rightKeyPressed = true;
        if (keycode == Input.Keys.W) upKeyPressed = true;
        if (keycode == Input.Keys.S) downKeyPressed = true;
        if (keycode == Input.Keys.ESCAPE) escKeyPressed = true;
        return super.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode)
    {
        if (keycode == Input.Keys.A) leftKeyPressed = false;
        if (keycode == Input.Keys.D) rightKeyPressed = false;
        if (keycode == Input.Keys.W) upKeyPressed = false;
        if (keycode == Input.Keys.S) downKeyPressed = false;
        if (keycode == Input.Keys.ESCAPE) escKeyPressed = false;
        return super.keyUp(keycode);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
        if (button == Input.Buttons.LEFT) mousePressed = true;
        return super.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button)
    {
        if (button == Input.Buttons.LEFT) mousePressed = false;
        return super.touchUp(screenX, screenY, pointer, button);
    }

    public Vector2 getDirection()
    {
        direction.set(0, 0);
        if(leftKeyPressed) direction.add(-1, 0);
        if(rightKeyPressed) direction.add(1, 0);
        if(upKeyPressed) direction.add(0, 1);
        if(downKeyPressed) direction.add(0, -1);
        return direction;
    }

    public Vector2 getMousePosition()
    {
        return mousePosition;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY)
    {
        mousePosition.set(screenX, Gdx.graphics.getHeight() - screenY);
        return super.mouseMoved(screenX, screenY);
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer)
    {
        mousePosition.set(screenX, Gdx.graphics.getHeight() - screenY);
        return super.touchDragged(screenX, screenY, pointer);
    }

    public boolean getMousePressed()
    {
        return mousePressed;
    }

    public boolean getEscKeyPressed()
    {
        return escKeyPressed;
    }

    public void setEscKeyPressed(boolean state)
    {
        escKeyPressed = state;
    }
}
