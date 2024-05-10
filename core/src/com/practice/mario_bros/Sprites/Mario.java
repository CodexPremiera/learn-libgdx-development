package com.practice.mario_bros.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;
import com.practice.mario_bros.MarioBros;

public class Mario extends Sprite {
    public World world;
    public Body body;

    public Mario(World world) {
        this.world = world;
        defineMario();
    }

    private void defineMario() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(64 / MarioBros.PPM, 64 / MarioBros.PPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        body = world.createBody(bodyDef);
        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(10 / MarioBros.PPM);

        fixtureDef.shape = shape;
        body.createFixture(fixtureDef);
    }
}
