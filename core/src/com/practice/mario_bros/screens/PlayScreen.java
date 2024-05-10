package com.practice.mario_bros.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.practice.mario_bros.MarioBros;
import com.practice.mario_bros.Scenes.Hud;

public class PlayScreen implements Screen {
    /* GAME ATTRIBUTES */
    private MarioBros game;
    private OrthographicCamera gameCam;
    private Viewport gamePort;
    private Hud hud;

    /* TILED MAP ATTRIBUTES */
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    /* BOX 2D ATTRIBUTES */
    private World world;
    private Box2DDebugRenderer box2DRenderer;


    public PlayScreen(MarioBros game) {
        this.game = game;

        // create the camera used to follow mario through the game world
        gameCam = new OrthographicCamera();

        // create a FitViewport to maintain virtual aspect ratio despite screen size
        gamePort = new FitViewport((float) MarioBros.V_WIDTH / 2, (float) MarioBros.V_HEIGHT / 2, gameCam);

        // create the hud for the game
        hud = new Hud(game.spriteBatch);

        // load the map and setup the renderer
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("world.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);

        // set the camera to the center of the viewport
        gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        // create the box2d world
        world = new World(new Vector2(0, 0), true);
        box2DRenderer = new Box2DDebugRenderer();

        // add bodies and fixtures to the world
        BodyDef bodyDef = new BodyDef();
        PolygonShape polygonShape = new PolygonShape();
        FixtureDef fixtureDef = new FixtureDef();
        Body body;

        // create the ground, pipes, bricks, coins bodies/fixtures
        for (int layerIndex = 2; layerIndex <= 5; layerIndex++) {
            for (MapObject object : map.getLayers().get(layerIndex).getObjects().getByType(RectangleMapObject.class)) {
                Rectangle rectangle = ((RectangleMapObject) object).getRectangle();

                bodyDef.type = BodyDef.BodyType.StaticBody;
                bodyDef.position.set(rectangle.getX() + rectangle.getWidth() / 2, rectangle.getY() + rectangle.getHeight() / 2);

                body = world.createBody(bodyDef);

                polygonShape.setAsBox(rectangle.getWidth() / 2, rectangle.getHeight() / 2);
                fixtureDef.shape = polygonShape;
                body.createFixture(fixtureDef);
            }
        }
    }

    @Override
    public void show() {

    }

    public void handleInput(float dt) {
        if (Gdx.input.isTouched()) {
            gameCam.position.x += 100 * dt;
        }
    }

    public void update(float dt) {
        handleInput(dt);
        gameCam.update();
        renderer.setView(gameCam);
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }

    @Override
    public void render(float delta) {
        // separate the update logic from the render logic
        update(delta);

        // clear the screen with a black color
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // render game map
        renderer.render();

        // render box2d world
        box2DRenderer.render(world, gameCam.combined);

        // draw the texture at the center of the screen
        game.spriteBatch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
