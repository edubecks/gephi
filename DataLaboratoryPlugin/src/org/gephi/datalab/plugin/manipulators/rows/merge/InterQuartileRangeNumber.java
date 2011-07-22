/*
Copyright 2008-2011 Gephi
Authors : Eduardo Ramos <eduramiba@gmail.com>
Website : http://www.gephi.org

This file is part of Gephi.

Gephi is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

Gephi is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with Gephi.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.gephi.datalab.plugin.manipulators.rows.merge;

import java.math.BigDecimal;
import javax.swing.Icon;
import org.gephi.data.attributes.api.AttributeColumn;
import org.gephi.data.attributes.api.AttributeUtils;
import org.gephi.datalab.api.AttributeColumnsController;
import org.gephi.datalab.spi.ManipulatorUI;
import org.gephi.datalab.spi.rows.merge.AttributeRowsMergeStrategy;
import org.gephi.graph.api.Attributes;
import org.gephi.utils.StatisticsUtils;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;

/**
 * AttributeRowsMergeStrategy for any number or number list column that
 * calculates the inter quartile range of all the values.
 * @author Eduardo Ramos <eduramiba@gmail.com>
 */
public class InterQuartileRangeNumber implements AttributeRowsMergeStrategy {

    private Attributes[] rows;
    private AttributeColumn column;
    private BigDecimal result;

    public void setup(Attributes[] rows, Attributes selectedRow, AttributeColumn column) {
        this.rows = rows;
        this.column = column;
    }

    public Object getReducedValue() {
        return result;
    }

    public void execute() {
        BigDecimal  Q1, Q3;
        Number[] numbers=Lookup.getDefault().lookup(AttributeColumnsController.class).getRowsColumnNumbers(rows, column);
        Q3 = StatisticsUtils.quartile3(numbers);
        Q1 = StatisticsUtils.quartile1(numbers);
        if (Q3 != null && Q1 != null) {
            result = Q3.subtract(Q1);
        } else {
            result = null;
        }
    }

    public String getName() {
        return NbBundle.getMessage(FirstQuartileNumber.class, "InterQuartileRangeNumber.name");
    }

    public String getDescription() {
        return NbBundle.getMessage(FirstQuartileNumber.class, "InterQuartileRangeNumber.description");
    }

    public boolean canExecute() {
        return AttributeUtils.getDefault().isNumberOrNumberListColumn(column);
    }

    public ManipulatorUI getUI() {
        return null;
    }

    public int getType() {
        return 100;
    }

    public int getPosition() {
        return 400;
    }

    public Icon getIcon() {
        return null;
    }
}