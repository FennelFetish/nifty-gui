package de.lessvoid.nifty.internal.layout;

import de.lessvoid.nifty.internal.Box;
import de.lessvoid.nifty.internal.BoxConstraints;


/**
 * Layoutable is the interface used in all layout related processes.
 * @author void
 */
public interface Layoutable {

  /**
   * Get the box of this LayoutPart.
   * @return the box
   */
  Box getLayoutPos();

  /**
   * Get the box constraints for this LayoutPart.
   * @return the box Constraints
   */
  BoxConstraints getBoxConstraints();
}