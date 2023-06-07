package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

public class Enemy extends Unit
{
    private int health = 3;
    public Enemy(Vector2 pos)
    {
        texture = new Texture("Без имени.png");
        textureRegion = new TextureRegion(texture);
        bounds = new Circle();
        textureSize = texture.getHeight() * 0.5f;
        textureHalfSize = textureSize / 2;
        bounds.radius = textureHalfSize;
        bounds.setPosition(pos.x + textureHalfSize, pos.y + textureHalfSize);
        position.set(pos);
    }

    public void render(Batch batch, float delta, Vector2 playerPosition)
    {
        Vector2 playerPos = new Vector2();
        playerPos.set(playerPosition);
        playerPos.sub(position);
        playerPos.setLength(50 * delta);
        moveTo(playerPos);
        bounds.setPosition(position.x + textureHalfSize, position.y + textureHalfSize);
        batch.draw(textureRegion, position.x, position.y, textureHalfSize, textureHalfSize, textureSize, textureSize, 1, 1, 0);
    }

    public float getHealth()
    {
        return health;
    }

    public void damage(int damage)
    {
        health -= damage;
    }
}
