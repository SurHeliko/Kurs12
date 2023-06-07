package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class Swarm extends Unit
{
    private ArrayList<Enemy> swarm;
    public Swarm()
    {
        swarm = new ArrayList<Enemy>();
    }

    public void create (int count)
    {
        for (int i = 0; i < count; i++)
        {
            swarm.add(new Enemy(getEnemiesSpawnPoint()));
        }
    }

    private Vector2 getEnemiesSpawnPoint()
    {
        Vector2 position = new Vector2();
        switch ((int) (Math.random() * 4))
        {
            case 0:
            {
                position.set((int) (Math.random() * (Gdx.graphics.getWidth()+200)) - 200, Gdx.graphics.getHeight());
                break;
            }
            case 1:
            {
                position.set((int) (Math.random() * (Gdx.graphics.getWidth()+200)) - 200, -200);
                break;
            }
            case 2:
            {
                position.set(Gdx.graphics.getWidth(), (int) (Math.random() * (Gdx.graphics.getHeight()+200)) - 200);
                break;
            }
            case 3: {
                position.set( -200 , (int) (Math.random() * (Gdx.graphics.getHeight()+200)) - 200);
                break;
            }
        }
        return position;
    }

    public void render(Batch batch, float delta, Vector2 playerPos, ExpPull expPull)
    {
        for (int i = 0; i < swarm.size(); i++)
        {
            if (swarm.get(i).getHealth() > 0)
            {
                swarm.get(i).render(batch, delta, playerPos);
            } else
                {
                    expPull.create(swarm.get(i).getPosition().add(swarm.get(i).getBounds().radius,swarm.get(i).getBounds().radius));
                    swarm.remove(i);
                    i--;
                }
        }
    }

    public ArrayList<Enemy> getSwarm()
    {
        return swarm;
    }

    public void dispose()
    {
        for (Enemy enemy: swarm)
        {
            enemy.dispose();
        }
    }
}
