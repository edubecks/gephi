/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gephi.legend.builders;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import org.gephi.data.attributes.api.AttributeModel;
import org.gephi.graph.api.Graph;
import org.gephi.legend.api.CustomGroupsItemBuilder;
import org.gephi.legend.api.CustomLegendItemBuilder;
import org.gephi.legend.items.LegendItem;
import org.gephi.legend.manager.LegendManager;
import org.gephi.preview.api.Item;
import org.gephi.preview.spi.ItemBuilder;
import org.gephi.legend.items.GroupsItem;
import org.gephi.legend.properties.GroupsProperty;
import org.gephi.legend.properties.ImageProperty;
import org.gephi.legend.properties.LegendProperty;
import org.gephi.legend.properties.TextProperty;
import org.gephi.preview.api.PreviewProperties;
import org.gephi.preview.api.PreviewProperty;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author edubecks
 */
@ServiceProviders(value = {
    @ServiceProvider(service = ItemBuilder.class, position = 103),
    @ServiceProvider(service = LegendItemBuilder.class, position = 103)
})
public class GroupsItemBuilder extends LegendItemBuilder {

    @Override
    protected boolean setDefaultValues() {
        return false;
    }

    @Override
    protected boolean isBuilderForItem(Item item) {
        return item instanceof GroupsItem;
    }

    @Override
    public String getType() {
        return GroupsItem.LEGEND_TYPE;
    }

    @Override
    public String getTitle() {
        return NbBundle.getMessage(LegendManager.class, "GroupsItem.name");
    }

    private PreviewProperty createLegendProperty(Item item, int property, Object value) {
        PreviewProperty previewProperty = null;
        Integer itemIndex = item.getData(LegendItem.ITEM_INDEX);
        String propertyString = LegendManager.getProperty(GroupsProperty.OWN_PROPERTIES, itemIndex, property);

        switch (property) {


            case GroupsProperty.GROUPS_NUMBER_COLUMNS: {
                previewProperty = PreviewProperty.createProperty(
                        this,
                        propertyString,
                        Integer.class,
                        NbBundle.getMessage(LegendManager.class, "GroupsItem.property.numColumns.displayName"),
                        NbBundle.getMessage(LegendManager.class, "GroupsItem.property.numColumns.description"),
                        PreviewProperty.CATEGORY_LEGEND_PROPERTY).setValue(value);
                break;
            }

            case GroupsProperty.GROUPS_LABEL_POSITION: {
                previewProperty = PreviewProperty.createProperty(
                        this,
                        propertyString,
                        LegendItem.Direction.class,
                        NbBundle.getMessage(LegendManager.class, "GroupsItem.property.label.position.displayName"),
                        NbBundle.getMessage(LegendManager.class, "GroupsItem.property.label.position.description"),
                        PreviewProperty.CATEGORY_LEGEND_PROPERTY).setValue(value);
                break;
            }

            case GroupsProperty.GROUPS_LABEL_FONT: {
                previewProperty = PreviewProperty.createProperty(
                        this,
                        propertyString,
                        Font.class,
                        NbBundle.getMessage(LegendManager.class, "GroupsItem.property.label.font.displayName"),
                        NbBundle.getMessage(LegendManager.class, "GroupsItem.property.label.font.description"),
                        PreviewProperty.CATEGORY_LEGEND_PROPERTY).setValue(value);
                break;
            }

            case GroupsProperty.GROUPS_LABEL_FONT_COLOR: {
                previewProperty = PreviewProperty.createProperty(
                        this,
                        propertyString,
                        Color.class,
                        NbBundle.getMessage(LegendManager.class, "GroupsItem.property.label.font.color.displayName"),
                        NbBundle.getMessage(LegendManager.class, "GroupsItem.property.label.font.color.description"),
                        PreviewProperty.CATEGORY_LEGEND_PROPERTY).setValue(value);
                break;
            }

            case GroupsProperty.GROUPS_SHAPE: {
                previewProperty = PreviewProperty.createProperty(
                        this,
                        propertyString,
                        LegendItem.Shape.class,
                        NbBundle.getMessage(LegendManager.class, "GroupsItem.property.shape.displayName"),
                        NbBundle.getMessage(LegendManager.class, "GroupsItem.property.shape.description"),
                        PreviewProperty.CATEGORY_LEGEND_PROPERTY).setValue(value);
                break;
            }

            case GroupsProperty.GROUPS_PADDING_BETWEEN_TEXT_AND_SHAPE: {
                previewProperty = PreviewProperty.createProperty(
                        this,
                        propertyString,
                        Integer.class,
                        NbBundle.getMessage(LegendManager.class, "GroupsItem.property.paddingBetweenTextAndShape.displayName"),
                        NbBundle.getMessage(LegendManager.class, "GroupsItem.property.paddingBetweenTextAndShape.description"),
                        PreviewProperty.CATEGORY_LEGEND_PROPERTY).setValue(value);
                break;
            }

            case GroupsProperty.GROUPS_PADDING_BETWEEN_ELEMENTS: {
                previewProperty = PreviewProperty.createProperty(
                        this,
                        propertyString,
                        Integer.class,
                        NbBundle.getMessage(LegendManager.class, "GroupsItem.property.paddingBetweenElements.displayName"),
                        NbBundle.getMessage(LegendManager.class, "GroupsItem.property.paddingBetweenElements.description"),
                        PreviewProperty.CATEGORY_LEGEND_PROPERTY).setValue(value);
                break;
            }

            case GroupsProperty.GROUPS_SCALE_SHAPE: {
                previewProperty = PreviewProperty.createProperty(
                        this,
                        propertyString,
                        Boolean.class,
                        NbBundle.getMessage(LegendManager.class, "GroupsItem.property.scaleShape.displayName"),
                        NbBundle.getMessage(LegendManager.class, "GroupsItem.property.scaleShape.description"),
                        PreviewProperty.CATEGORY_LEGEND_PROPERTY).setValue(value);
                break;
            }


        }

        return previewProperty;
    }

    private PreviewProperty createLegendLabelProperty(Item item, int numberOfLabel, Object value) {

        Integer itemIndex = item.getData(LegendItem.ITEM_INDEX);
        String propertyString = GroupsProperty.getLabelProperty(itemIndex, numberOfLabel);

        PreviewProperty property = PreviewProperty.createProperty(
                this,
                propertyString,
                String.class,
                NbBundle.getMessage(LegendManager.class, "GroupsItem.property.labels.displayName") + " " + numberOfLabel,
                NbBundle.getMessage(LegendManager.class, "GroupsItem.property.labels.description") + " " + numberOfLabel,
                PreviewProperty.CATEGORY_LEGEND_PROPERTY).setValue(value);

        System.out.println("@Var: ReadingXML property: " + propertyString + " with value: " + value);


        return property;
    }

    @Override
    protected PreviewProperty[] createLegendOwnProperties(Item item) {

        

//        ArrayList<String> groupsProperties = LegendManager.getProperties(GroupsProperty.OWN_PROPERTIES, itemIndex);
//
//        PreviewProperty[] properties = {
//            PreviewProperty.createProperty(this,
//                                           groupsProperties.get(GroupsProperty.GROUPS_NUMBER_COLUMNS),
//                                           Integer.class,
//                                           NbBundle.getMessage(LegendManager.class, "GroupsItem.property.numColumns.displayName"),
//                                           NbBundle.getMessage(LegendManager.class, "GroupsItem.property.numColumns.description"),
//                                           PreviewProperty.CATEGORY_LEGEND_PROPERTY).setValue(defaultNumColumns),
//            PreviewProperty.createProperty(this,
//                                           groupsProperties.get(GroupsProperty.GROUPS_LABEL_POSITION),
//                                           LegendItem.Direction.class,
//                                           NbBundle.getMessage(LegendManager.class, "GroupsItem.property.label.position.displayName"),
//                                           NbBundle.getMessage(LegendManager.class, "GroupsItem.property.label.position.description"),
//                                           PreviewProperty.CATEGORY_LEGEND_PROPERTY).setValue(defaultLabelPosition),
//            PreviewProperty.createProperty(this,
//                                           groupsProperties.get(GroupsProperty.GROUPS_LABEL_FONT),
//                                           Font.class,
//                                           NbBundle.getMessage(LegendManager.class, "GroupsItem.property.label.font.displayName"),
//                                           NbBundle.getMessage(LegendManager.class, "GroupsItem.property.label.font.description"),
//                                           PreviewProperty.CATEGORY_LEGEND_PROPERTY).setValue(defaultLabelFont),
//            PreviewProperty.createProperty(this,
//                                           groupsProperties.get(GroupsProperty.GROUPS_LABEL_FONT_COLOR),
//                                           Color.class,
//                                           NbBundle.getMessage(LegendManager.class, "GroupsItem.property.label.font.color.displayName"),
//                                           NbBundle.getMessage(LegendManager.class, "GroupsItem.property.label.font.color.description"),
//                                           PreviewProperty.CATEGORY_LEGEND_PROPERTY).setValue(defaultLabelFontColor),
//            PreviewProperty.createProperty(this,
//                                           groupsProperties.get(GroupsProperty.GROUPS_SHAPE),
//                                           LegendItem.Shape.class,
//                                           NbBundle.getMessage(LegendManager.class, "GroupsItem.property.shape.displayName"),
//                                           NbBundle.getMessage(LegendManager.class, "GroupsItem.property.shape.description"),
//                                           PreviewProperty.CATEGORY_LEGEND_PROPERTY).setValue(defaultShape),
//            PreviewProperty.createProperty(this,
//                                           groupsProperties.get(GroupsProperty.GROUPS_PADDING_BETWEEN_TEXT_AND_SHAPE),
//                                           Integer.class,
//                                           NbBundle.getMessage(LegendManager.class, "GroupsItem.property.paddingBetweenTextAndShape.displayName"),
//                                           NbBundle.getMessage(LegendManager.class, "GroupsItem.property.paddingBetweenTextAndShape.description"),
//                                           PreviewProperty.CATEGORY_LEGEND_PROPERTY).setValue(defaultPaddingBetweenTextAndShape),
//            PreviewProperty.createProperty(this,
//                                           groupsProperties.get(GroupsProperty.GROUPS_PADDING_BETWEEN_ELEMENTS),
//                                           Integer.class,
//                                           NbBundle.getMessage(LegendManager.class, "GroupsItem.property.paddingBetweenElements.displayName"),
//                                           NbBundle.getMessage(LegendManager.class, "GroupsItem.property.paddingBetweenElements.description"),
//                                           PreviewProperty.CATEGORY_LEGEND_PROPERTY).setValue(defaultPaddingBetweenElements),
//            PreviewProperty.createProperty(this,
//                                           groupsProperties.get(GroupsProperty.GROUPS_SCALE_SHAPE),
//                                           Boolean.class,
//                                           NbBundle.getMessage(LegendManager.class, "GroupsItem.property.scaleShape.displayName"),
//                                           NbBundle.getMessage(LegendManager.class, "GroupsItem.property.scaleShape.description"),
//                                           PreviewProperty.CATEGORY_LEGEND_PROPERTY).setValue(defaultIsScalingShapes)
//        };
        
        // creating one property for each label
        ArrayList<StringBuilder> labelsGroup = item.getData(GroupsItem.LABELS_IDS);
        PreviewProperty[] labelProperties = new PreviewProperty[labelsGroup.size()];
        for (int i = 0; i < labelProperties.length; i++) {
            labelProperties[i] = createLegendLabelProperty(item, i, labelsGroup.get(i).toString());
        }

        int[] properties = GroupsProperty.LIST_OF_PROPERTIES;

        PreviewProperty[] previewProperties = new PreviewProperty[defaultValues.length];
        for (int i = 0; i < defaultValues.length; i++) {
            System.out.println("@Var: i: " + i);
            previewProperties[i] = createLegendProperty(item, properties[i], defaultValues[i]);
            System.out.println("@Var: previewProperties: " + previewProperties[i]);
        }


        PreviewProperty[] propertiesWithLabels = new PreviewProperty[labelProperties.length + previewProperties.length];
        System.arraycopy(labelProperties, 0, propertiesWithLabels, 0, labelProperties.length);
        System.arraycopy(previewProperties, 0, propertiesWithLabels, labelProperties.length, previewProperties.length);
        return propertiesWithLabels;

    }

    // DEFAULT PROPERTIES
    private Integer defaultNumColumns = 5;
    private LegendItem.Direction defaultLabelPosition = LegendItem.Direction.DOWN;
    private Color defaultLabelFontColor = Color.BLACK;
    private Font defaultLabelFont = new Font("Arial", Font.PLAIN, 15);
    private Integer defaultPaddingBetweenTextAndShape = 5;
    private Integer defaultPaddingBetweenElements = 5;
    private LegendItem.Shape defaultShape = LegendItem.Shape.RECTANGLE;
    private Boolean defaultIsScalingShapes = Boolean.FALSE;
    private final Object[] defaultValues = {
        defaultNumColumns,
        defaultShape,
        defaultIsScalingShapes,
        defaultLabelPosition,
        defaultLabelFont,
        defaultLabelFontColor,
        defaultPaddingBetweenTextAndShape,
        defaultPaddingBetweenElements
    };

    @Override
    protected Boolean hasDynamicProperties() {
        return Boolean.FALSE;
    }

    @Override
    protected Item buildCustomItem(CustomLegendItemBuilder builder, Graph graph, AttributeModel attributeModel) {

        Item item = createNewLegendItem(graph);


        ArrayList<StringBuilder> labels = new ArrayList<StringBuilder>();
        ArrayList<Color> colors = new ArrayList<Color>();
        ArrayList<Float> values = new ArrayList<Float>();

        CustomGroupsItemBuilder customGroupsBuilder = (CustomGroupsItemBuilder) builder;
        customGroupsBuilder.retrieveData(labels, colors, values);

        item.setData(GroupsItem.NUMBER_OF_GROUPS, labels.size());
        item.setData(GroupsItem.COLORS, colors);
        item.setData(GroupsItem.LABELS_IDS, labels);
        item.setData(GroupsItem.VALUES, values);
        return item;

    }

    @Override
    public ArrayList<CustomLegendItemBuilder> getAvailableBuilders() {
        Collection<? extends CustomGroupsItemBuilder> customBuilders = Lookup.getDefault().lookupAll(CustomGroupsItemBuilder.class);
        ArrayList<CustomLegendItemBuilder> availableBuilders = new ArrayList<CustomLegendItemBuilder>();
        for (CustomGroupsItemBuilder customBuilder : customBuilders) {
            availableBuilders.add((CustomLegendItemBuilder) customBuilder);
        }
        return availableBuilders;
    }

    @Override
    public void writeXMLFromData(XMLStreamWriter writer, Item item) throws XMLStreamException {

        String name = null;
        String text = null;

        // write number of items
        Integer numberOfGroups = item.getData(GroupsItem.NUMBER_OF_GROUPS);
        text = numberOfGroups.toString();
        name = GroupsItem.NUMBER_OF_GROUPS;
        writer.writeStartElement(LegendItem.DATA);
        writer.writeAttribute(XML_NAME, name);
        writer.writeCharacters(text);
        writer.writeEndElement();

        // writing labels
        ArrayList<StringBuilder> labels = item.getData(GroupsItem.LABELS_IDS);
        text = labels.toString();
        name = GroupsItem.LABELS_IDS;
        writer.writeStartElement(LegendItem.DATA);
        writer.writeAttribute(XML_NAME, name);
        writer.writeCharacters(text);
        writer.writeEndElement();


        // writing colors
        ArrayList<Color> colors = item.getData(GroupsItem.COLORS);
        name = GroupsItem.COLORS;
        ArrayList<Integer> colorsIntegerArrayList = new ArrayList<Integer>();
        for (Color color : colors) {
            colorsIntegerArrayList.add(color.getRGB());
        }
        text = colorsIntegerArrayList.toString();
        writer.writeStartElement(LegendItem.DATA);
        writer.writeAttribute(XML_NAME, name);
        writer.writeCharacters(text);
        writer.writeEndElement();

        // writing values
        ArrayList<Float> values = item.getData(GroupsItem.VALUES);
        text = values.toString();
        name = GroupsItem.VALUES;
        writer.writeStartElement(LegendItem.DATA);
        writer.writeAttribute(XML_NAME, name);
        writer.writeCharacters(text);
        writer.writeEndElement();

    }

    @Override
    public void readXMLToData(XMLStreamReader reader, Item item) throws XMLStreamException {
        // read number of items
        reader.next();
        Integer numberOfItems = Integer.parseInt(reader.getElementText());
        System.out.println("@Var: numberOfItems: " + numberOfItems);

        // reading labels
        reader.next();
        String labelsString = reader.getElementText();
        String[] labelsArray = labelsString.replace("[", "").replace("]", "").split(", ");
        ArrayList<StringBuilder> labels= new ArrayList<StringBuilder>();
        for (int i = 0; i < numberOfItems; i++) {
            labels.add(new StringBuilder(labelsArray[i]));
        }
        System.out.println("@Var: labels: "+labels);
        
        // reading colors
        reader.next();
        String colorsString = reader.getElementText();
        String[] colorsArray = colorsString.replace("[", "").replace("]", "").split(", ");
        ArrayList<Color> colors= new ArrayList<Color>();
        for (int i = 0; i < numberOfItems; i++) {
            colors.add(new Color(Integer.parseInt(colorsArray[i])));
        }
        System.out.println("@Var: colors: "+colors);
        
        
        // reading values
        reader.next();
        String valuesString = reader.getElementText();
        String[] valuesArray = valuesString.replace("[", "").replace("]", "").split(", ");
        ArrayList<Float> values= new ArrayList<Float>();
        for (int i = 0; i < numberOfItems; i++) {
            values.add(Float.parseFloat(valuesArray[i]));
        }
        System.out.println("@Var: values: "+values);
        
        
        
        item.setData(GroupsItem.NUMBER_OF_GROUPS, labels.size());
        item.setData(GroupsItem.COLORS, colors);
        item.setData(GroupsItem.LABELS_IDS, labels);
        item.setData(GroupsItem.VALUES, values);
        

    }

    @Override
    public Item createNewLegendItem(Graph graph) {
        return new GroupsItem(graph);
    }

    @Override
    public void writeXMLFromItemOwnProperties(XMLStreamWriter writer, Item item) throws XMLStreamException {

        // write number of items
        Integer numberOfGroups = item.getData(GroupsItem.NUMBER_OF_GROUPS);
        String text = numberOfGroups.toString();
        String name = GroupsItem.NUMBER_OF_GROUPS;
        writer.writeStartElement(LegendItem.DATA);
        writer.writeAttribute(XML_NAME, name);
        writer.writeCharacters(text);
        writer.writeEndElement();

        PreviewProperty[] ownProperties = item.getData(LegendItem.OWN_PROPERTIES);
        for (PreviewProperty property : ownProperties) {
            writeXMLFromSingleProperty(writer, property);
        }
    }

    @Override
    protected ArrayList<PreviewProperty> readXMLToOwnProperties(XMLStreamReader reader, Item item) throws XMLStreamException {

        ArrayList<PreviewProperty> properties = new ArrayList<PreviewProperty>();

        // read number of items
        reader.next();
        Integer numberOfItems = Integer.parseInt(reader.getElementText());
        System.out.println("@Var: numberOfItems: " + numberOfItems);

        // reading labels
        for (int i = 0; i < numberOfItems; i++) {
            reader.next();
            String valueString = reader.getElementText();
            PreviewProperty property = createLegendLabelProperty(item, i, valueString);
            properties.add(property);
        }

        // own properties
        boolean end = false;
        while (reader.hasNext() && !end) {
            int type = reader.next();

            switch (type) {
                case XMLStreamReader.START_ELEMENT: {
                    PreviewProperty property = readXMLToSingleOwnProperty(reader, item);
                    properties.add(property);
                    break;
                }
                case XMLStreamReader.CHARACTERS: {
                    break;
                }
                case XMLStreamReader.END_ELEMENT: {
                    end = true;
                    break;
                }
            }
        }

        return properties;


    }

    @Override
    protected ArrayList<PreviewProperty> readXMLToDynamicProperties(XMLStreamReader reader, Item item) throws XMLStreamException {
        reader.nextTag();
        return new ArrayList<PreviewProperty>();
    }

    @Override
    protected PreviewProperty readXMLToSingleOwnProperty(XMLStreamReader reader, Item item) throws XMLStreamException {
        String propertyName = reader.getAttributeValue(null, XML_NAME);
        String valueString = reader.getElementText();
        System.out.println("@Var: ReadingXML property: "+propertyName+" with value: "+valueString);
        int propertyIndex = GroupsProperty.getInstance().getProperty(propertyName);
        Class valueClass = defaultValues[propertyIndex].getClass();
        Object value = PreviewProperties.readValueFromText(valueString, valueClass);
        if (value == null) {
            value = readValueFromText(valueString, valueClass);
        }
        System.out.println("@Var: ReadingXML property: " + propertyName + " with value: " + value);
        PreviewProperty property = createLegendProperty(item, propertyIndex, value);
        return property;
    }

}
