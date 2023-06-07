package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

public class Projectile extends Unit {
    private float timeToLife = 2;
    private float speed = 300;

    public Projectile(float x, float y, Vector2 mousePosition)
    {
        texture = new Texture("1.png");
        textureRegion = new TextureRegion(texture);
        bounds = new Circle();
        textureSize = texture.getHeight() * 0.05f;
        textureHalfSize = textureSize / 2;
        bounds.radius = textureHalfSize;
        bounds.setPosition(x, y);
        position.set(x, y);
        angle.set(mousePosition).sub(position.x + textureHalfSize, position.y + textureHalfSize);
        angle.setLength(speed);
    }

    public void render(Batch batch, float delta)
    {
        timeToLife -= delta;
        position.add(angle.x * delta, angle.y * delta);
        bounds.setPosition(position.x, position.y);
        batch.draw(textureRegion, position.x - textureHalfSize, position.y - textureHalfSize, textureHalfSize, textureHalfSize, textureSize, textureSize, 1, 1, angle.angleDeg());
    }

    public float getTimeToLife()
    {
        return timeToLife;
    }


}
