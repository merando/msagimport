/*
 * Copyright 2017 TraderJoe95 <johannes.leupold@schie-le.de>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package one.p_f.testing.msagimport.grouping.aggregation;

import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Collectors;
import org.gradoop.common.model.impl.properties.PropertyValue;
import org.gradoop.flink.model.impl.operators.grouping.functions.aggregation.PropertyValueAggregator;

/**
 *
 * @author TraderJoe95 <johannes.leupold@schie-le.de>
 */
public class AttributeSetAggregator extends PropertyValueAggregator {

    private HashSet<String> attributeSet;

    public AttributeSetAggregator() {
        super("attributes", "attributes");
    }

    @Override
    protected boolean isInitialized() {
        return attributeSet != null;
    }

    @Override
    protected void initializeAggregate(PropertyValue in) {
        String str = in.getString();
        attributeSet = new HashSet<>(Arrays.asList(str.split(";")));
    }

    @Override
    protected void aggregateInternal(PropertyValue in) {
        String str = in.getString();
        HashSet<String> newAttributeSet
                = new HashSet<>(Arrays.asList(str.split(";")));
        attributeSet.addAll(newAttributeSet);
    }

    @Override
    protected PropertyValue getAggregateInternal() {
        return PropertyValue.create(attributeSet.stream()
                .collect(Collectors.joining(";")));
    }

    @Override
    public void resetAggregate() {
        if (isInitialized()) {
            attributeSet.clear();
        }
    }

}