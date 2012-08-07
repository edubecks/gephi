/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gephi.legend.builders.group;

import java.awt.Color;
import java.util.ArrayList;
import org.gephi.legend.api.CustomGroupsItemBuilder;
import org.gephi.legend.api.CustomLegendItemBuilder;
import org.gephi.partition.api.NodePartition;
import org.gephi.partition.api.Part;
import org.gephi.partition.api.PartitionController;
import org.gephi.partition.api.PartitionModel;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author edubecks
 */
@ServiceProvider(service = CustomGroupsItemBuilder.class, position = 2)
public class NumberOfNodesInPartition extends CustomLegendItemBuilder implements CustomGroupsItemBuilder {

    @Override
    public String getDescription() {
        return NbBundle.getMessage(NumberOfNodesInPartition.class, "NumberOfNodesInPartition.description");
    }

    @Override
    public String getTitle() {
        return NbBundle.getMessage(NumberOfNodesInPartition.class, "NumberOfNodesInPartition.title");
    }

    @Override
    public boolean isAvailableToBuild() {
        PartitionController partitionController = Lookup.getDefault().lookup(PartitionController.class);
        PartitionModel partitionModel = partitionController.getModel();
        if (partitionModel != null) {
            if (partitionModel.getSelectedPartition() != null) {
                return (partitionModel.getSelectedPartition().getPartsCount() > 1);
            }
        }
        return false;
    }

    @Override
    public String stepsNeededToBuild() {
        return NbBundle.getMessage(NumberOfNodesInPartition.class, "NumberOfNodesInPartition.stepsNeeded");
    }

    @Override
    public void retrieveData(ArrayList<StringBuilder> labels, ArrayList<Color> colors, ArrayList<Float> values) {
        PartitionController partitionController = Lookup.getDefault().lookup(PartitionController.class);
        PartitionModel partitionModel = partitionController.getModel();
        if (partitionModel != null) {
            if (partitionModel.getSelectedPartition() != null) {
                Part[] parts = partitionModel.getSelectedPartition().getParts();

                for (Part part : parts) {
                    // APPEND LABEL
                    StringBuilder label = new StringBuilder(part.getDisplayName());
                    labels.add(label);
                    
                    // APPEND VALUE
                    values.add((float)part.getObjects().length);
                    
                    // APPEND COLOR
                    colors.add(part.getColor());
                    
                }
                
                
            }
        }
    }

}
