package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

import javax.swing.*;
import java.io.IOException;
import java.net.*;


public class GameEngine extends ApplicationAdapter
{
	private SpriteBatch batch;
	private Snake player;
	private Swarm enemies;
	private ExpPull expPull;
	private KeyboardInput input;
	private BitmapFont font;
	private long sTime;
	private long time;
	private int enemyEncounter;
	private final int secondsToSpawn = 5;
	private long menuTime;
	private int menu = 1;
	private Stage stage, stage2, stage3;
	private String userName = "NoName";
	private InetAddress address;
	private DatagramSocket socket;
	private String[] leaders = new String[5];

	@Override
	public void create ()
	{
		try
		{
			address = InetAddress.getLocalHost();
			socket = new DatagramSocket(111);
			socket.setSoTimeout(10000);
		} catch (UnknownHostException | SocketException e)
			{
				Gdx.app.exit();
			}

		batch = new SpriteBatch();
		TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("atlasMap.txt"));
		font = new BitmapFont(Gdx.files.internal("font.fnt"), atlas.findRegion("font"));
		font.getData().setScale(1);
		NinePatchDrawable fondoPuntuaciones = new NinePatchDrawable(atlas.createPatch("fondoPuntuaciones"));
		TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle(fondoPuntuaciones, null, null, font);

		stage = new Stage();
		TextButton play = new TextButton("Play", textButtonStyle);
		final TextButton name = new TextButton("Name", textButtonStyle);
		TextButton board = new TextButton("Leaders", textButtonStyle);
		TextButton exit = new TextButton("Exit", textButtonStyle);
		play.setPosition(Gdx.graphics.getWidth() / 2f - 100,Gdx.graphics.getHeight() / 2f + 120);
		play.setSize(200,40);
		name.setPosition(Gdx.graphics.getWidth() / 2f - 100,Gdx.graphics.getHeight() / 2f + 60);
		name.setSize(200,40);
		board.setPosition(Gdx.graphics.getWidth() / 2f - 100,Gdx.graphics.getHeight() / 2f);
		board.setSize(200,40);
		exit.setPosition(Gdx.graphics.getWidth() / 2f - 100,Gdx.graphics.getHeight() / 2f - 60);
		exit.setSize(200,40);
		stage.addActor(play);
		stage.addActor(board);
		stage.addActor(exit);
		stage.addActor(name);

		play.addListener(new ChangeListener()
		{
			@Override
			public void changed(ChangeEvent event, Actor actor)
			{
				gameStart();
			}
		});
		name.addListener(new ChangeListener()
		{
			@Override
			public void changed(ChangeEvent event, Actor actor)
			{
				userName = JOptionPane.showInputDialog("Enter name", "NoName");
				name.setText(userName);
			}
		});
		board.addListener(new ChangeListener()
		{
			@Override
			public void changed(ChangeEvent event, Actor actor)
			{
				menu = 2;
				byte[] buffer = new byte[256];
				DatagramPacket packet;

				packet = new DatagramPacket("Leaderboard".getBytes(), "Leaderboard".length(), address, 115);
				try
				{
					socket.send(packet);
				} catch (IOException e)
					{
						menu = 1;
					}

				if (menu != 1)
				{
					for (int i = 4; i >= 0; i--)
					{
						packet = new DatagramPacket(buffer, buffer.length);
						try
						{
							socket.receive(packet);
						} catch (IOException e)
							{
								menu = 1;
								break;
							}
						leaders[i] = new String(packet.getData(), 0, packet.getLength());
					}
				}
				if(menu != 1)
				{
					Gdx.input.setInputProcessor(stage2);
				}
			}
		});
		exit.addListener(new ChangeListener()
		{
			@Override
			public void changed(ChangeEvent event, Actor actor)
			{
				Gdx.app.exit();
			}
		});


		stage2 = new Stage();
		TextButton back = new TextButton("Back", textButtonStyle);
		back.setPosition(Gdx.graphics.getWidth() / 2f - 100,Gdx.graphics.getHeight() / 2f - 180);
		back.setSize(200,40);
		stage2.addActor(back);

		back.addListener(new ChangeListener()
		{
			@Override
			public void changed(ChangeEvent event, Actor actor)
			{
				menu = 1;
				Gdx.input.setInputProcessor(stage);
			}
		});

		stage3 = new Stage();
		TextButton returnn = new TextButton("Return", textButtonStyle);
		TextButton menub = new TextButton("Menu", textButtonStyle);
		TextButton restart = new TextButton("Restart", textButtonStyle);
		TextButton exit2 = new TextButton("Exit", textButtonStyle);
		returnn.setPosition(Gdx.graphics.getWidth() / 2f - 100,Gdx.graphics.getHeight() / 2f + 120);
		returnn.setSize(200,40);
		restart.setPosition(Gdx.graphics.getWidth() / 2f - 100,Gdx.graphics.getHeight() / 2f + 60);
		restart.setSize(200,40);
		menub.setPosition(Gdx.graphics.getWidth() / 2f - 100,Gdx.graphics.getHeight() / 2f);
		menub.setSize(200,40);
		exit2.setPosition(Gdx.graphics.getWidth() / 2f - 100,Gdx.graphics.getHeight() / 2f - 60);
		exit2.setSize(200,40);
		stage3.addActor(returnn);
		stage3.addActor(restart);
		stage3.addActor(menub);
		stage3.addActor(exit2);

		returnn.addListener(new ChangeListener()
		{
			@Override
			public void changed(ChangeEvent event, Actor actor)
			{
				menu = 0;
				sTime += TimeUtils.timeSinceMillis(menuTime);
				Gdx.input.setInputProcessor(input);
			}
		});
		restart.addListener(new ChangeListener()
		{
			@Override
			public void changed(ChangeEvent event, Actor actor)
			{
				gameStart();
			}
		});
		menub.addListener(new ChangeListener()
		{
			@Override
			public void changed(ChangeEvent event, Actor actor)
			{
				menu = 1;
				Gdx.input.setInputProcessor(stage);
			}
		});
		exit2.addListener(new ChangeListener()
		{
			@Override
			public void changed(ChangeEvent event, Actor actor)
			{
				Gdx.app.exit();
			}
		});

		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void render ()
	{
		switch (menu)
		{
			case 0:
			{
				gameRender();
				break;
			}
			case 1:
			{
				ScreenUtils.clear(1, 1, 1, 1);
				stage.draw();
				stage.act();
				break;
			}
			case 2:
			{
				int y = 180;
				ScreenUtils.clear(1, 1, 1, 1);
				batch.begin();
				for (int i = 0; i < 5; i++)
				{
					font.draw(batch, leaders[i], Gdx.graphics.getWidth() / 2f - 100,Gdx.graphics.getHeight() / 2f + y);
					y -= 60;
				}
				batch.end();
				stage2.draw();
				stage2.act();
				break;
			}
			case 3:
			{
				ScreenUtils.clear(1, 1, 1, 1);
				stage3.draw();
				stage3.act();
			}
		}
	}

	@Override
	public void dispose ()
	{
		batch.dispose();
		player.dispose();
		enemies.dispose();
		expPull.dispose();
		font.dispose();
	}

	private void collision()
	{
		for (int i = 0; i < enemies.getSwarm().size(); i++)
		{
			if (enemies.getSwarm().get(i).getBounds().overlaps(player.getHead().getBounds()))
			{
				player.damage();
				enemies.getSwarm().get(i).damage(1000);
			}
			for (int j = 0; j < player.getTail().size(); j++)
			{
				if(enemies.getSwarm().get(i).getBounds().overlaps(player.getTail().get(j).getBounds()))
				{
					player.damage();
					enemies.getSwarm().get(i).damage(1000);
				}
			}

			for (int j = 0; j < player.getHead().getBullets().size(); j++)
			{
				if (enemies.getSwarm().get(i).getBounds().overlaps(player.getHead().getBullets().get(j).getBounds()))
				{
					player.getHead().getBullets().remove(j);
					enemies.getSwarm().get(i).damage(1);
					j--;
				}
			}
			for (int k = 0; k < player.getTail().size(); k++)
			{
				for (int j = 0; j < player.getTail().get(k).getBullets().size(); j++)
				{
					if (enemies.getSwarm().get(i).getBounds().overlaps(player.getTail().get(k).getBullets().get(j).getBounds()))
					{
						player.getTail().get(k).getBullets().remove(j);
						enemies.getSwarm().get(i).damage(1);
						j--;
					}
				}
			}
		}

		for (int i = 0; i < expPull.getExpPull().size(); i++)
		{
			if (expPull.getExpPull().get(i).getBounds().overlaps(player.getHead().getBounds()))
			{
				player.gainExp();
				expPull.getExpPull().get(i).setDel();
			}
			for (int j = 0; j < player.getTail().size(); j++)
			{
				if(expPull.getExpPull().get(i).getBounds().overlaps(player.getTail().get(j).getBounds()))
				{
					player.gainExp();
					expPull.getExpPull().get(i).setDel();
				}
			}
		}

		if (player.getHealth() <= 0)
		{
			menu = 1;
			Gdx.input.setInputProcessor(stage);
			try
			{
				String msg = new String(userName.getBytes());
				msg += ":";
				msg += Long.toString(time);
				DatagramPacket packet = new DatagramPacket(msg.getBytes(), msg.length(), address, 115);
				socket.send(packet);


			} catch (IOException e)
				{
					System.exit(1);
				}
		}
	}

	private void gameRender()
	{
		if (input.getEscKeyPressed())
		{
			input.setEscKeyPressed(false);
			menuTime = TimeUtils.millis();
			menu = 3;
			Gdx.input.setInputProcessor(stage3);
		}
		float delta = Gdx.graphics.getDeltaTime();
		player.moveTo(input.getDirection().setLength(player.getSpeed() * delta));
		player.rotateTo(input.getMousePosition());
		if (input.getMousePressed())
		{
			player.fire(input.getMousePosition(), delta);
		}
		ScreenUtils.clear(1, 1, 1, 1);
		collision();
		time = TimeUtils.timeSinceMillis(sTime) / 1000;
		if (time > (long) enemyEncounter * secondsToSpawn)
		{
			enemies.create(enemyEncounter);
			enemyEncounter++;
		}
		batch.begin();
		expPull.render(batch);
		enemies.render(batch, delta, player.getPosition(), expPull);
		player.render(batch, delta);
		font.draw(batch, "Health", 20, Gdx.graphics.getHeight() - 15);
		font.draw(batch, Integer.toString(player.getHealth()), 130, Gdx.graphics.getHeight() - 15);
		font.draw(batch, Long.toString(time), Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() - 15);
		batch.end();
	}

	private void gameStart()
	{
		menu = 0;
		input = new KeyboardInput();
		player = new Snake(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f);
		enemies = new Swarm();
		expPull = new ExpPull();
		sTime = TimeUtils.millis();
		enemyEncounter = 1;
		Gdx.input.setInputProcessor(input);
	}
}
