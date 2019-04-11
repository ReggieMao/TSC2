package com.ebei.ui.Components.Paint;

import android.graphics.RectF;
import android.opengl.GLES20;

public class Utils {

    public static void HasGLError() {
        int error = GLES20.glGetError();
        if (error != 0) {

        }
    }

    public static void RectFIntegral(RectF rect) {
        rect.left = (int) Math.floor(rect.left);
        rect.top = (int) Math.floor(rect.top);
        rect.right = (int) Math.ceil(rect.right);
        rect.bottom = (int) Math.ceil(rect.bottom);
    }
}
