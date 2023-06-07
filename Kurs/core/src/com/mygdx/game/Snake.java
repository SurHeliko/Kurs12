package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.g2d.Batch;
import java.util.ArrayList;

public class Snake
{
    private ArrayList<SnakeNode> tail;
    private final SnakeNode head;
    private final float fireRate = 3;
    private final float speed = 300;
    private int health = 10;
    private int exp = 0;

    public Snake(float x, float y)
    {
        tail = new ArrayList<SnakeNode> ();
        head = new SnakeNode(x, y);
        tail.add(new SnakeNode(x, y));
    }

    public void moveTo(Vector2 direction)
    {
        Vector2 temp = new Vector2();
        head.moveTo(direction);
        temp.set(head.getPosition());
        temp.sub(tail.get(0).getPosition());
        if (temp.len() < head.getRadius())
        {
            return;
        }
        temp.limit(temp.len() - head.getRadius());
        tail.get(0).moveTo(temp);
        for (int i = 1; i < tail.size(); i++)
        {
            temp.set(tail.get(i - 1).getPosition());
            temp.sub(tail.get(i).getPosition());
            if (temp.len() - tail.get(i - 1).getRadius() < 0)
            {
                return;
            }
            temp.limit(temp.len() - tail.get(i - 1).getRadius());
            tail.get(i).moveTo(temp);
        }
    }

    public void rotateTo(Vector2 mousePosition)
    {
        head.rotateTo(mousePosition);
        for (SnakeNode node : tail)
        {
            node.rotateTo(mousePosition);
        }
    }

    public void render(Batch batch, float delta)
    {
        for (int i = tail.size() - 1; i >= 0; i--)
        {
            tail.get(i).render(batch, delta);
        }
        head.render(batch, delta);
    }

    public void dispose()
    {
        head.dispose();
        for (SnakeNode node: tail)
        {
            node.dispose();
        }
    }

    public void fire (Vector2 direction, float delta)
    {
        head.fire(direction, delta, fireRate);
        for (SnakeNode node: tail)
        {
            node.fire(direction, delta, fireRate);
        }
    }

    public float getSpeed()
    {
        return speed;
    }

    public Vector2 getPosition()
    {
        Vector2 pos = new Vector2();
        float subber = head.getRadius() / 2;
        return pos.set(head.getPosition()).sub(subber, subber);
    }

    public SnakeNode getHead()
    {
        return head;
    }

    public ArrayList<SnakeNode> getTail()
    {
        return tail;
    }

    public void damage()
    {
        health--;
    }

    public void gainExp()
    {
        exp++;
        if (exp >= 10)
        {
            tail.add(new SnakeNode(tail.get(tail.size() - 1).getPosition().x, tail.get(tail.size() - 1).getPosition().y));
            exp = 0;
        }
    }

    public int getHealth()
    {
        return health;
    }
}
