/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gephi.legend.renderers;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.util.ArrayList;
import org.gephi.legend.api.LegendItem;
import org.gephi.legend.api.LegendManager;
import org.gephi.legend.properties.TextProperty;
import org.gephi.legend.builders.TextItemBuilder;
import org.gephi.legend.items.TextItem;
import org.gephi.preview.api.*;
import org.gephi.preview.spi.ItemBuilder;
import org.gephi.preview.spi.Renderer;
import org.openide.util.NbBundle;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author edubecks
 */
//@ServiceProvider(service = Renderer.class, position = 400)
public class TextItemRenderer extends LegendItemRenderer {

    @Override
    public String getDisplayName() {
        return NbBundle.getMessage(TextItemRenderer.class, "TextItemRenderer.name");
    }
    
    
    @Override
    public void readOwnPropertiesAndValues(Item item, PreviewProperties properties) {
        
        int workspaceIndex = item.getData(LegendItem.WORKSPACE_INDEX);
        int itemIndex = item.getData(LegendItem.ITEM_INDEX);
        
        
        body = item.getData(TextItem.BODY);
        bodyFont = properties.getFontValue(LegendManager.getProperty(TextProperty.OWN_PROPERTIES, workspaceIndex, itemIndex, TextProperty.TEXT_BODY_FONT));
        bodyFontColor = properties.getColorValue(LegendManager.getProperty(TextProperty.OWN_PROPERTIES, workspaceIndex, itemIndex, TextProperty.TEXT_BODY_FONT_COLOR));
    }
    



    @Override
    public boolean isRendererForitem(Item item, PreviewProperties properties) {
        return item instanceof TextItem;
    }

    @Override
    public boolean needsItemBuilder(ItemBuilder itemBuilder, PreviewProperties properties) {
        return itemBuilder instanceof TextItemBuilder;
    }
    

    @Override
    public void renderToGraphics(Graphics2D graphics2D, AffineTransform origin, int width, int height) {

        graphics2D.setTransform(origin);
        graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        FontMetrics bodyFontMetrics = graphics2D.getFontMetrics(bodyFont);
        int bodyFontHeight = bodyFontMetrics.getHeight();

        FontMetrics titleFontMetrics = graphics2D.getFontMetrics(titleFont);
        int titleFontHeight = titleFontMetrics.getHeight();

        FontMetrics descriptionFontMetrics = graphics2D.getFontMetrics(descriptionFont);
        int descriptionFontHeight = descriptionFontMetrics.getHeight();

        int actualLine = 0;

        // title 
        if (isDisplayingTitle) {
            actualLine += titleFontHeight;
            graphics2D.setColor(titleFontColor);
            graphics2D.setFont(titleFont);
            graphics2D.drawString(title, 0, actualLine);
        }
        
        //drawing body
        graphics2D.setColor(bodyFontColor);
        graphics2D.setFont(bodyFont);
        
        AttributedString styledText = new AttributedString(body);
        AttributedCharacterIterator m_iterator = styledText.getIterator();
        int start = m_iterator.getBeginIndex();
        int end = m_iterator.getEndIndex();
        
        
        FontRenderContext frc = graphics2D.getFontRenderContext();

        LineBreakMeasurer measurer = new LineBreakMeasurer(m_iterator, frc);
        measurer.setPosition(start);

        float x = 0;
        while (measurer.getPosition() < end)
        {
            TextLayout layout = measurer.nextLayout(width);

            actualLine += layout.getAscent();
            float dx = layout.isLeftToRight() ?
                    0 : width - layout.getAdvance();

            layout.draw(graphics2D, x + dx, actualLine);
            actualLine += layout.getDescent() + layout.getLeading();
        }

//        actualLine+= actualLine;
        
//        graphics2D.legendDrawString(body, 0, actualLine);
//        System.out.println("@Var: body: " + body);
        
        
        // description
        if (isDisplayingDescription) {
            actualLine += descriptionFontHeight;
            graphics2D.setColor(descriptionFontColor);
            graphics2D.setFont(descriptionFont);
            graphics2D.drawString(description, 0, actualLine);
        }


    }
    

    
    // OWN PROPERTIES
    private String body;
    private Font bodyFont;
    private Color bodyFontColor;

}