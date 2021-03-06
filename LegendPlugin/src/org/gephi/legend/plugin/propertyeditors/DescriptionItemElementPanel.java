/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gephi.legend.plugin.propertyeditors;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Collection;
import org.gephi.legend.api.DescriptionItemElementValue;
import org.gephi.legend.plugin.builders.description.elements.CustomValue;
import org.gephi.legend.plugin.items.DescriptionItemElement;
import org.openide.util.Lookup;

/**
 *
 * @author edubecks
 */
public class DescriptionItemElementPanel extends javax.swing.JPanel implements ItemListener {

    /**
     * Creates new form DescriptionItemElementPanel
     */
    public DescriptionItemElementPanel() {
        initComponents();
        loadDescriptionItemElementValues();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        valueLabel = new javax.swing.JLabel();
        valueComboBox = new javax.swing.JComboBox();
        customValueTextField = new javax.swing.JTextField();

        setPreferredSize(new java.awt.Dimension(400, 100));

        valueLabel.setText(org.openide.util.NbBundle.getMessage(DescriptionItemElementPanel.class, "DescriptionItemElementPanel.valueLabel.text")); // NOI18N

        valueComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                valueComboBoxActionPerformed(evt);
            }
        });

        customValueTextField.setColumns(20);
        customValueTextField.setText(org.openide.util.NbBundle.getMessage(DescriptionItemElementPanel.class, "DescriptionItemElementPanel.customValueTextField.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(customValueTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 348, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(valueLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(valueComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(valueLabel)
                    .addComponent(valueComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(customValueTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(36, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void valueComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_valueComboBoxActionPerformed

        DescriptionItemElementValue descriptionItemElementValue = (DescriptionItemElementValue) valueComboBox.getSelectedItem();
        String value = descriptionItemElementValue.getValue();
        customValueTextField.setText(value);
        if (propertyEditor != null) {
            this.propertyEditor.setValue(new DescriptionItemElement(CUSTOM_VALUE, value));
        }

    }//GEN-LAST:event_valueComboBoxActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField customValueTextField;
    private javax.swing.JComboBox valueComboBox;
    private javax.swing.JLabel valueLabel;
    // End of variables declaration//GEN-END:variables
    private DescriptionItemElementPropertyEditor propertyEditor;

    public void setup(DescriptionItemElementPropertyEditor propertyEditor) {
        this.propertyEditor = propertyEditor;
        DescriptionItemElement descriptionItemElement = (DescriptionItemElement) propertyEditor.getValue();
        valueComboBox.setSelectedItem(descriptionItemElement.getGenerator());
        customValueTextField.setText(descriptionItemElement.getValue());
    }

    @Override
    public void itemStateChanged(ItemEvent ie) {
    }

    public final void loadDescriptionItemElementValues() {
        Collection<? extends DescriptionItemElementValue> values = Lookup.getDefault().lookupAll(DescriptionItemElementValue.class);
        for (DescriptionItemElementValue descriptionItemElementValue : values) {
            valueComboBox.addItem(descriptionItemElementValue);
        }
    }
    
    private static final DescriptionItemElementValue CUSTOM_VALUE = new CustomValue();

}
