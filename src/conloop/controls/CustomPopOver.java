package conloop.controls;

import javafx.scene.Node;
import javafx.stage.Window;
import org.controlsfx.control.PopOver;

/**
 *
 * @author Patowhiz 01/03/2019 11:14 AM
 */
public class CustomPopOver {

    private PopOver popOver;

    public CustomPopOver(String title, Node content) {
        this();
        setTitle(title);
        setContentNode(content);
    }

    public CustomPopOver() {
        popOver = new PopOver();
        setArrowLocationToTopCenter();
    }

    public void setTitle(String title) {
        popOver.setTitle(title);
    }

    public CustomPopOver setArrowLocationToTopCenter() {
        popOver.setArrowLocation(PopOver.ArrowLocation.TOP_CENTER);
        return this;
    }

    public CustomPopOver setArrowLocationToLeftCenter() {
        popOver.setArrowLocation(PopOver.ArrowLocation.LEFT_CENTER);
        return this;
    }

    public void setContentNode(Node content) {
        popOver.setContentNode(content);
    }

    public void show(Node owner) {
        popOver.show(owner);
    }

    public void show(Window owner) {
        popOver.show(owner);

    }

    public void hide() {
        popOver.hide();

    }
}//end class
