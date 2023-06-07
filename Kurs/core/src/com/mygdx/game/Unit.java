package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

public class Unit {
    protected Vector2 position = new Vector2();
    protected Vector2 angle = new Vector2();
    protected Texture texture;
    protected TextureRegion textureRegion;
    protected float textureSize;
    protected float textureHalfSize;
    protected Circle bounds;

    public Vector2 getPosition()
    {
        return position;
    }

    public void render(Batch batch)
    {
        batch.draw(textureRegion, position.x, position.y, textureHalfSize, textureHalfSize, textureSize, textureSize, 1, 1, angle.angleDeg());
    }

    public void moveTo(Vector2 direction)
    {
        position.add(direction);
    }

    public void rotateTo(Vector2 mousePosition)
    {
        angle.set(mousePosition).sub(position.x + textureHalfSize, position.y + textureHalfSize);
    }

    public Circle getBounds()
    {
        return bounds;
    }

    public void dispose()
    {
        texture.dispose();
    }
}
