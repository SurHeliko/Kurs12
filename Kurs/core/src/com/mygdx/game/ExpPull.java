package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class ExpPull extends Unit
{
    private ArrayList<Experience> expPull;

    public ExpPull()
    {
        expPull = new ArrayList<Experience>();
    }

    public void create(Vector2 pos)
    {
        expPull.add(new Experience(pos));
    }

    public void render(Batch batch)
    {
        for (int i = 0; i < expPull.size(); i++)
        {
            if (expPull.get(i).getDel() == 0)
            {
                expPull.get(i).render(batch);
            } else
                {
                    expPull.remove(i);
                    i--;
                }
        }
    }

    public ArrayList<Experience> getExpPull()
    {
        return expPull;
    }

    public void dispose()
    {
        for (Experience exp: expPull)
        {
            exp.dispose();
        }
    }
}
