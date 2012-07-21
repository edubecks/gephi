package org.gephi.legend.mouse;

import org.gephi.legend.api.LegendItem;
import static org.gephi.legend.api.LegendItem.TRANSFORMATION_ANCHOR_SIZE;
import org.gephi.legend.api.LegendManager;
import org.gephi.legend.properties.LegendProperty;
import org.gephi.preview.api.Item;
import org.gephi.preview.api.PreviewMouseEvent;
import org.gephi.preview.api.PreviewProperties;
import org.gephi.preview.spi.PreviewMouseListener;
import org.gephi.project.api.Workspace;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Eduardo Ramos<eduramiba@gmail.com>
 */
@ServiceProvider(service=PreviewMouseListener.class)
public class LegendMouseListener implements PreviewMouseListener {

    private float relativeX;
    private float relativeY;
    private float relativeAnchorX;
    private float relativeAnchorY;
    private int clickedAnchor;

    private int isClickingInAnchor(int pointX, int pointY, Item item, PreviewProperties previewProperties) {
        Integer itemIndex = item.getData(LegendItem.ITEM_INDEX);
        float realOriginX = previewProperties.getFloatValue(LegendManager.getProperty(LegendProperty.LEGEND_PROPERTIES, itemIndex, LegendProperty.USER_ORIGIN_X));
        float realOriginY = previewProperties.getFloatValue(LegendManager.getProperty(LegendProperty.LEGEND_PROPERTIES, itemIndex, LegendProperty.USER_ORIGIN_Y));
        int width = previewProperties.getIntValue(LegendManager.getProperty(LegendProperty.LEGEND_PROPERTIES, itemIndex, LegendProperty.WIDTH));
        int height = previewProperties.getIntValue(LegendManager.getProperty(LegendProperty.LEGEND_PROPERTIES, itemIndex, LegendProperty.HEIGHT));
        float[][] anchorLocations = {
            {-TRANSFORMATION_ANCHOR_SIZE / 2, -TRANSFORMATION_ANCHOR_SIZE / 2, TRANSFORMATION_ANCHOR_SIZE, TRANSFORMATION_ANCHOR_SIZE},
            {width - TRANSFORMATION_ANCHOR_SIZE / 2, -TRANSFORMATION_ANCHOR_SIZE / 2, TRANSFORMATION_ANCHOR_SIZE, TRANSFORMATION_ANCHOR_SIZE},
            {-TRANSFORMATION_ANCHOR_SIZE / 2, height - TRANSFORMATION_ANCHOR_SIZE / 2, TRANSFORMATION_ANCHOR_SIZE, TRANSFORMATION_ANCHOR_SIZE},
            {width - TRANSFORMATION_ANCHOR_SIZE / 2, height - TRANSFORMATION_ANCHOR_SIZE / 2, TRANSFORMATION_ANCHOR_SIZE, TRANSFORMATION_ANCHOR_SIZE}
        };

        pointX -= realOriginX;
        pointY -= realOriginY;

        for (int i = 0; i < anchorLocations.length; i++) {
            if ((pointX >= anchorLocations[i][0] && pointX < (anchorLocations[i][0] + anchorLocations[i][2]))
                    && (pointY >= anchorLocations[i][1] && pointY < (anchorLocations[i][1] + anchorLocations[i][3]))) {
                return i;
            }
        }
        return -1;
    }

    private boolean isClickingInLegend(int pointX, int pointY, Item item, PreviewProperties previewProperties) {
        Integer itemIndex = item.getData(LegendItem.ITEM_INDEX);
        float realOriginX = previewProperties.getFloatValue(LegendManager.getProperty(LegendProperty.LEGEND_PROPERTIES, itemIndex, LegendProperty.USER_ORIGIN_X));
        float realOriginY = previewProperties.getFloatValue(LegendManager.getProperty(LegendProperty.LEGEND_PROPERTIES, itemIndex, LegendProperty.USER_ORIGIN_Y));
        int width = previewProperties.getIntValue(LegendManager.getProperty(LegendProperty.LEGEND_PROPERTIES, itemIndex, LegendProperty.WIDTH));
        int height = previewProperties.getIntValue(LegendManager.getProperty(LegendProperty.LEGEND_PROPERTIES, itemIndex, LegendProperty.HEIGHT));
        if ((pointX >= realOriginX && pointX < (realOriginX + width))
                && (pointY >= realOriginY && pointY < (realOriginY + height))) {
            return true;
        }
        return false;
    }

    @Override
    public void mouseClicked(PreviewMouseEvent event, PreviewProperties previewProperties, Workspace workspace) {
        LegendManager legendManager = previewProperties.getValue(LegendManager.LEGEND_PROPERTIES);
        boolean someItemStateChanged = false;
        for (Item item : legendManager.getLegendItems()) {
            Boolean isSelected = item.getData(LegendItem.IS_SELECTED);
            if (isClickingInLegend(event.x, event.y, item, previewProperties)) {
                //Unselect all other items:
                for (Item otherItem : legendManager.getLegendItems()) {
                    otherItem.setData(LegendItem.IS_SELECTED, Boolean.FALSE);
                }
                item.setData(LegendItem.IS_SELECTED, Boolean.TRUE);
                event.setConsumed(true);
                return;
            } else if (isSelected) {
                someItemStateChanged = someItemStateChanged || isSelected;
                item.setData(LegendItem.IS_SELECTED, Boolean.FALSE);
            }
        }
        if (someItemStateChanged) {
            event.setConsumed(true);
        }
    }

    @Override
    public void mousePressed(PreviewMouseEvent event, PreviewProperties previewProperties, Workspace workspace) {
        LegendManager legendManager = previewProperties.getValue(LegendManager.LEGEND_PROPERTIES);
        for (Item item : legendManager.getLegendItems()) {
            Boolean isSelected = item.getData(LegendItem.IS_SELECTED);
            if (!isSelected) {
                continue;
            }
            Integer itemIndex = item.getData(LegendItem.ITEM_INDEX);
            float realOriginX = previewProperties.getFloatValue(LegendManager.getProperty(LegendProperty.LEGEND_PROPERTIES, itemIndex, LegendProperty.USER_ORIGIN_X));
            float realOriginY = previewProperties.getFloatValue(LegendManager.getProperty(LegendProperty.LEGEND_PROPERTIES, itemIndex, LegendProperty.USER_ORIGIN_Y));
            int width = previewProperties.getIntValue(LegendManager.getProperty(LegendProperty.LEGEND_PROPERTIES, itemIndex, LegendProperty.WIDTH));
            int height = previewProperties.getIntValue(LegendManager.getProperty(LegendProperty.LEGEND_PROPERTIES, itemIndex, LegendProperty.HEIGHT));

            clickedAnchor = isClickingInAnchor(event.x, event.y, item, previewProperties);
            boolean isClickingInAnchor = clickedAnchor >= 0;
            if (isClickingInAnchor) {
                relativeX = event.x - realOriginX;
                relativeY = event.y - realOriginY;
                switch (clickedAnchor) {
                    // Top Left Anchor
                    case 0: {
                        relativeAnchorX = relativeX;
                        relativeAnchorY = relativeY;
                        break;
                    }
                    // Top Right
                    case 1: {
                        relativeAnchorX = relativeX - width;
                        relativeAnchorY = relativeY;
                        break;
                    }
                    // Bottom Left
                    case 2: {
                        relativeAnchorX = relativeX;
                        relativeAnchorY = relativeY - height;
                        break;
                    }
                    // Bottom Right
                    case 3: {
                        relativeAnchorX = relativeX - width;
                        relativeAnchorY = relativeY - height;
                        break;
                    }
                }

                event.setConsumed(true);
                return;
            } else if (isClickingInLegend(event.x, event.y, item, previewProperties)) {
                relativeX = event.x - realOriginX;
                relativeY = event.y - realOriginY;
                event.setConsumed(true);
                return;
            }
        }
    }

    @Override
    public void mouseDragged(PreviewMouseEvent event, PreviewProperties previewProperties, Workspace workspace) {
        LegendManager legendManager = previewProperties.getValue(LegendManager.LEGEND_PROPERTIES);
        for (Item item : legendManager.getLegendItems()) {
            Boolean isSelected = item.getData(LegendItem.IS_SELECTED);
            if (!isSelected) {
                continue;
            }
            Integer itemIndex = item.getData(LegendItem.ITEM_INDEX);
            float realOriginX = previewProperties.getFloatValue(LegendManager.getProperty(LegendProperty.LEGEND_PROPERTIES, itemIndex, LegendProperty.USER_ORIGIN_X));
            float realOriginY = previewProperties.getFloatValue(LegendManager.getProperty(LegendProperty.LEGEND_PROPERTIES, itemIndex, LegendProperty.USER_ORIGIN_Y));
            int width = previewProperties.getIntValue(LegendManager.getProperty(LegendProperty.LEGEND_PROPERTIES, itemIndex, LegendProperty.WIDTH));
            int height = previewProperties.getIntValue(LegendManager.getProperty(LegendProperty.LEGEND_PROPERTIES, itemIndex, LegendProperty.HEIGHT));

            boolean isClickingInAnchor = clickedAnchor >= 0;
            if (isClickingInAnchor) {
                float newOriginX = realOriginX;
                float newOriginY = realOriginY;
                float newWidth = width;
                float newHeight = height;

                switch (clickedAnchor) {
                    // Top Left Anchor
                    case 0: {
                        newOriginX = event.x - relativeAnchorX;
                        newOriginY = event.y - relativeAnchorY;
                        newWidth = realOriginX + width - newOriginX;
                        newHeight = realOriginY + height - newOriginY;
                        break;
                    }
                    // Top Right
                    case 1: {
                        newOriginX = realOriginX;
                        newOriginY = event.y - relativeAnchorY;
                        newWidth = event.x - relativeAnchorX - newOriginX;
                        newHeight = realOriginY + height - newOriginY;
                        break;
                    }
                    // Bottom Left
                    case 2: {
                        newOriginX = event.x - relativeAnchorX;
                        newOriginY = realOriginY;
                        newWidth = realOriginX + width - newOriginX;
                        newHeight = event.y - realOriginY - relativeAnchorY;
                        break;
                    }
                    // Bottom Right
                    case 3: {
                        newOriginX = realOriginX;
                        newOriginY = realOriginY;
                        newWidth = event.x - relativeAnchorX - newOriginX;
                        newHeight = event.y - relativeAnchorY - newOriginY;
                        break;
                    }
                }

                previewProperties.putValue(LegendManager.getProperty(LegendProperty.LEGEND_PROPERTIES, itemIndex, LegendProperty.USER_ORIGIN_X),newOriginX);
                previewProperties.putValue(LegendManager.getProperty(LegendProperty.LEGEND_PROPERTIES, itemIndex, LegendProperty.USER_ORIGIN_Y),newOriginY);
                previewProperties.putValue(LegendManager.getProperty(LegendProperty.LEGEND_PROPERTIES, itemIndex, LegendProperty.WIDTH),newWidth);
                previewProperties.putValue(LegendManager.getProperty(LegendProperty.LEGEND_PROPERTIES, itemIndex, LegendProperty.HEIGHT),newHeight);
            } else { // translate
                float newRealOriginX = event.x - relativeX;
                float newRealOriginY = event.y - relativeY;

                previewProperties.putValue(LegendManager.getProperty(LegendProperty.LEGEND_PROPERTIES, itemIndex, LegendProperty.USER_ORIGIN_X),newRealOriginX);
                previewProperties.putValue(LegendManager.getProperty(LegendProperty.LEGEND_PROPERTIES, itemIndex, LegendProperty.USER_ORIGIN_Y),newRealOriginY);
            }

            event.setConsumed(true);
            return;
        }
    }

    @Override
    public void mouseReleased(PreviewMouseEvent event, PreviewProperties previewProperties, Workspace workspace) {
        event.setConsumed(true);
    }
}
