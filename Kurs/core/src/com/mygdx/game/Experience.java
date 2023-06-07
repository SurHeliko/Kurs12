package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

public class Experience extends Unit
{
    private int del = 0;
    public Experience(Vector2 pos)
    {
        texture = new Texture("Без имени.png");
        textureRegion = new TextureRegion(texture);
        bounds = new Circle();
        textureSize = texture.getHeight() * 0.1f;
        textureHalfSize = textureSize / 2;
        bounds.radius = textureHalfSize;
        bounds.setPosition(pos.x + textureHalfSize, pos.y + textureHalfSize);
        position.set(pos);
    }

    public void render(Batch batch)
    {
        batch.draw(textureRegion, position.x, position.y, textureHalfSize, textureHalfSize, textureSize, textureSize, 1, 1, 0);
    }

    public void setDel()
    {
        del = 1;
    }

    public int getDel()
    {
        return del;
    }
}
