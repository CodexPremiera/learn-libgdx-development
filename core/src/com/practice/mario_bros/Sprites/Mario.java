package com.practice.mario_bros.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.practice.mario_bros.MarioBros;
import com.practice.mario_bros.screens.PlayScreen;

public class Mario extends Sprite {
    public World world;
    public Body body;
    private TextureRegion marioStand;

    public Mario(World world, PlayScreen screen) {
        // get sprite map from screen
        super(screen.getAtlas().findRegion("little_mario"));

        this.world = world;
        defineMario();
        marioStand = new TextureRegion(getTexture(), 0, 0, 16, 32);
        setBounds(0, 0, 16 / MarioBros.PPM, 16 / MarioBros.PPM);
        setRegion(marioStand);
    }

    private void defineMario() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(64 / MarioBros.PPM, 64 / MarioBros.PPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        body = world.createBody(bodyDef);
        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(8 / MarioBros.PPM);

        fixtureDef.shape = shape;
        body.createFixture(fixtureDef);
    }

    public void update(float dt) {
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
    }
}
