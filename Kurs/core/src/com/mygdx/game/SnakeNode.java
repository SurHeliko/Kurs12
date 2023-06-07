package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class SnakeNode extends Unit
{
    private  ArrayList<Projectile> bullets = new ArrayList<Projectile>();
    private float fireRateDelta = 0;
    private Texture bulletTexture;
    private TextureRegion bulletTextureRegion;
    private float bulletHalfSize;

    public SnakeNode(float x, float y)
    {
        texture = new Texture("Arrow.png");
        textureRegion = new TextureRegion(texture);
        bulletTexture = new Texture("1.png");
        bulletTextureRegion = new TextureRegion(bulletTexture);
        bulletHalfSize = bulletTexture.getHeight() * 0.025f;
        bounds = new Circle();
        textureSize = texture.getHeight() * 0.15f;
        textureHalfSize = textureSize / 2;
        bounds.radius = textureHalfSize;
        bounds.setPosition(x + textureHalfSize, y + textureHalfSize);
        position.set(x, y);
    }

    public void fire (Vector2 direction, float delta, float fireRate)
    {
        fireRateDelta += delta;
        if (fireRateDelta >= 1/fireRate)
        {
            bullets.add(new Projectile(position.x + textureHalfSize, position.y + textureHalfSize, new Vector2(direction).add(bulletHalfSize, bulletHalfSize)));
            fireRateDelta = 0;
        }
    }

    public void render(Batch batch, float delta)
    {
        bounds.setPosition(position.x  + textureHalfSize, position.y  + textureHalfSize);
        batch.draw(textureRegion, position.x, position.y, textureHalfSize, textureHalfSize, textureSize, textureSize, 1, 1, angle.angleDeg());
        for (int i = 0; i < bullets.size(); i++)
        {
            if (bullets.get(i).getTimeToLife() <= 0)
            {
                bullets.remove(i);
                i--;
            }
            else bullets.get(i).render(batch, delta);
        }
    }

    public void moveTo(Vector2 direction)
    {
        Vector2 temp = new Vector2(position);
        if (temp.add(direction).y < Gdx.graphics.getHeight() - textureHalfSize
            & temp.add(direction).x < Gdx.graphics.getWidth() - textureHalfSize
            & temp.add(direction).y > 0 - textureHalfSize
            & temp.add(direction).x > 0 - textureHalfSize)
        {
            position.add(direction);
        }
    }

    public ArrayList<Projectile> getBullets()
    {
        return bullets;
    }
    public float getRadius()
    {
        return textureSize;
    }
}
