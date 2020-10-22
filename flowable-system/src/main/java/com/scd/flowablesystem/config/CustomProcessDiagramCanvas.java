package com.scd.flowablesystem.config;

import org.flowable.image.impl.DefaultProcessDiagramCanvas;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 * TODO
 *
 * @author shang
 * @version 1.0
 * @date 2020-10-22 16:43
 */
public class CustomProcessDiagramCanvas extends DefaultProcessDiagramCanvas {
  public CustomProcessDiagramCanvas(int width, int height, int minX, int minY, String imageType,
                                    String activityFontName, String labelFontName, String annotationFontName,
                                    ClassLoader customClassLoader) {
    super(width, height, minX, minY, imageType, activityFontName, labelFontName, annotationFontName,
        customClassLoader);
  }

  public void drawCustomHighLight(int x, int y, int width, int height, Paint paint) {
    Paint originalPaint = g.getPaint();
    Stroke originalStroke = g.getStroke();

    g.setPaint(paint);
    g.setStroke(THICK_TASK_BORDER_STROKE);
    RoundRectangle2D rect = new RoundRectangle2D.Double(x, y, width, height, 20, 20);
    g.draw(rect);

    g.setPaint(originalPaint);
    g.setStroke(originalStroke);
  }
}
