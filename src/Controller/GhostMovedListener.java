package Controller;

import Model.Ghost;


/**
 * interface for classes interested in Ghost movements
 */
public interface GhostMovedListener {

    void ghostMoved(int prevX, int prevY, Ghost ghost);
    void collisionDetected();
}