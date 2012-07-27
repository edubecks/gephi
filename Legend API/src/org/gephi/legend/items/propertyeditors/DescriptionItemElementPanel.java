/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gephi.legend.items.propertyeditors;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 *
 * @author edubecks
 */
public class DescriptionItemElementPanel extends javax.swing.JPanel implements ItemListener{
    
    private final static String CUSTOM_VALUE= "Custom ...";
    
    // temp
    private final static String FUNCTION1= "Funcion 1";
    private final static String FUNCTION2= "Funcion 2";
    private final static String FUNCTION3= "Funcion 3";

    /**
     * Creates new form DescriptionItemElementPanel
     */
    public DescriptionItemElementPanel() {
        initComponents();
        valueComboBox.addItem(FUNCTION1);
        valueComboBox.addItem(FUNCTION2);
        valueComboBox.addItem(FUNCTION3);
        // custom value
        valueComboBox.addItem(CUSTOM_VALUE);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        keyLabel = new javax.swing.JLabel();
        valueLabel = new javax.swing.JLabel();
        keyTextField = new javax.swing.JTextField();
        valueComboBox = new javax.swing.JComboBox();
        customValueTextField = new javax.swing.JTextField();

        setLayout(new java.awt.GridBagLayout());

        keyLabel.setText(org.openide.util.NbBundle.getMessage(DescriptionItemElementPanel.class, "DescriptionItemElementPanel.keyLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        add(keyLabel, gridBagConstraints);

        valueLabel.setText(org.openide.util.NbBundle.getMessage(DescriptionItemElementPanel.class, "DescriptionItemElementPanel.valueLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        add(valueLabel, gridBagConstraints);

        keyTextField.setText(org.openide.util.NbBundle.getMessage(DescriptionItemElementPanel.class, "DescriptionItemElementPanel.keyTextField.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        add(keyTextField, gridBagConstraints);

        valueComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                valueComboBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        add(valueComboBox, gridBagConstraints);

        customValueTextField.setText(org.openide.util.NbBundle.getMessage(DescriptionItemElementPanel.class, "DescriptionItemElementPanel.customValueTextField.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        add(customValueTextField, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void valueComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_valueComboBoxActionPerformed
        if(valueComboBox.getSelectedItem()==CUSTOM_VALUE){
            customValueTextField.setText("");
        }
        else{
            customValueTextField.setText(valueComboBox.getSelectedItem().toString());
        }
        
        // switch others
        
        
    }//GEN-LAST:event_valueComboBoxActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField customValueTextField;
    private javax.swing.JLabel keyLabel;
    private javax.swing.JTextField keyTextField;
    private javax.swing.JComboBox valueComboBox;
    private javax.swing.JLabel valueLabel;
    // End of variables declaration//GEN-END:variables

    
    private DescriptionItemElementPropertyEditor propertyEditor;
    
    public void setup(DescriptionItemElementPropertyEditor propertyEditor) {
        this.propertyEditor = propertyEditor;
    }
    
    @Override
    public void itemStateChanged(ItemEvent ie) {
        
    }
}
