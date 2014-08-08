package org.spacegame.shader;

import com.badlogic.gdx.graphics.g3d.Attribute;
import com.badlogic.gdx.graphics.g3d.attributes.IntAttribute;

/**
 *
 */
public final class SpecialAttribute extends IntAttribute {

    public static final int ENTITY = 0;
    public static final int SKY = 1;
    public static final int WATER_SURFACE = 2;
    public static final int WATER_UNDERSIDE = 3;
    public static final int AIR_BUBBLE = 4;
    public final static String ATTRIBUTE_ALIAS = "SpecialElement";
    public final static long ATTRIBUTE = register(ATTRIBUTE_ALIAS);

    public SpecialAttribute(long type, int value) {
        super(type, value);
    }

    public static SpecialAttribute entity() {
        return new SpecialAttribute(ATTRIBUTE, ENTITY);
    }

    public static SpecialAttribute sky() {
        return new SpecialAttribute(ATTRIBUTE, SKY);
    }

    public static SpecialAttribute waterSurface() {
        return new SpecialAttribute(ATTRIBUTE, WATER_SURFACE);
    }

    public static SpecialAttribute waterUnderside() {
        return new SpecialAttribute(ATTRIBUTE, WATER_UNDERSIDE);
    }

    public static SpecialAttribute airBubble() {
        return new SpecialAttribute(ATTRIBUTE, AIR_BUBBLE);
    }

    @Override public Attribute copy() {
        return new SpecialAttribute(type, value);
    }

    @Override
    public boolean equals(Attribute o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SpecialAttribute that = (SpecialAttribute) o;

        if (value != that.value) return false;

        return true;
    }

}
