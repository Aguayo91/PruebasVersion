package com.gruposalinas.elektra.sociomas.UI.Controls.Vision;

import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.face.Face;

/**
 * Created by oemy9 on 22/08/2017.
 */

public class BlinkTracker extends Tracker<Face>  {
    private final float OPEN_THRESHOLD = 0.85f;
    private final float CLOSE_THRESHOLD = 0.4f;
    private int state = 0;
    private CallBackBlink callback;
    public void setCallback(CallBackBlink callback) {
        this.callback = callback;
    }
    public void removeCallBacks(){
        this.callback=null;
    }
    public void onUpdate(Detector.Detections<Face> detections, Face face) {
        float left = face.getIsLeftEyeOpenProbability();
        float right = face.getIsRightEyeOpenProbability();
        if ((left == Face.UNCOMPUTED_PROBABILITY) ||
                (right == Face.UNCOMPUTED_PROBABILITY)) {
            return;
        }
        float value=  Math.min(left, right);

        switch (state) {
            case 0:
                if (value > OPEN_THRESHOLD) {
                    callback.onEyesDetected(true);
                    state = 1;
                }
                break;

            case 1:
                if (value < CLOSE_THRESHOLD) {
                    // Both eyes become closed
                    if(callback!=null) {
                        callback.onBlinkCreated(true);
                    }
                    state = 2;
                }
                break;
            case 2:
                if (value > OPEN_THRESHOLD) {
                    if(callback!=null) {
                        callback.onBlinkCreated(true);
                    }
                    state = 0;
                }
                break;
        }
    }

}
