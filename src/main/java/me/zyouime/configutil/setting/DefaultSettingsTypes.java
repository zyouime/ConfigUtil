package me.zyouime.configutil.setting;

import com.google.common.reflect.TypeToken;

import java.awt.*;
import java.lang.reflect.Type;

public class DefaultSettingsTypes {

    public static final Type NUMBER = new TypeToken<Number>() {}.getType();
    public static final Type STRING = new TypeToken<String>() {}.getType();
    public static final Type BOOLEAN = new TypeToken<Boolean>() {}.getType();
    public static final Type COLOR = new TypeToken<Color>() {}.getType();
}
