package de.lessvoid.nifty.examples.usecase;

import de.lessvoid.nifty.api.ChildLayout;
import de.lessvoid.nifty.api.Nifty;
import de.lessvoid.nifty.api.NiftyCanvas;
import de.lessvoid.nifty.api.NiftyCanvasPainter;
import de.lessvoid.nifty.api.NiftyColor;
import de.lessvoid.nifty.api.NiftyNode;
import de.lessvoid.nifty.api.UnitValue;

/**
 * custom canvas painter.
 * @author void
 */
public class UseCase_b01_Canvas {

  public UseCase_b01_Canvas(final Nifty nifty) {
    NiftyNode niftyNode = nifty.createRootNode(UnitValue.px(400), UnitValue.px(400), ChildLayout.Center);
    niftyNode.setBackgroundColor(NiftyColor.GREEN());

    NiftyNode child = niftyNode.newChildNode(UnitValue.percent(50), UnitValue.percent(50));
    child.setCanvasPainter(new NiftyCanvasPainter() {
      @Override
      public void paint(final NiftyNode node, final NiftyCanvas canvas) {
        // fill the whole node content with a plain white color
        canvas.setFillStyle(NiftyColor.WHITE());
        canvas.fillRect(0, 0, node.getWidth(), node.getHeight());

        // create a funky black rectangle inside the white
        canvas.setFillStyle(NiftyColor.BLACK());
        canvas.fillRect(
            node.getWidth() / 2 - Math.random() * node.getWidth() / 2,
            node.getHeight() / 2 - Math.random() * node.getHeight() / 2,
            node.getWidth() - node.getWidth() / 2 + Math.random() * node.getWidth() / 2,
            node.getHeight() - node.getHeight() / 2 + Math.random() * node.getHeight() / 2);
      }
    });
    child.startAnimatedRedraw(0, 50);
  }

  public static void main(final String[] args) throws Exception {
    UseCaseRunner.run(UseCase_b01_Canvas.class, args);
  }
}