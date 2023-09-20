package com.ha.app.data;

import com.ha.app.annotations.data.Entity;
import com.ha.app.annotations.data.ManyToOne;
import com.ha.app.constants.DataConstants;
import com.ha.app.helpers.ClassHelper;

import java.lang.reflect.Field;
import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * This class using simple Find-Union data structure and Quick Find algorithm to check if two entities classes are
 * already linked
 * Using this algorithm, the circular loading data issue could be solved
 */
public class RelationshipManager {

    HashMap<Class<?>, Integer> entityClassMap = new HashMap<>();
    HashMap<Class<?>, Class<?>> oldConnections = new HashMap<>();
    int[] classIds;

    public RelationshipManager(Set<Class<?>> entityClasses) {
        initializeEntityMap(entityClasses);
    }

    private void initializeEntityMap(Set<Class<?>> entityClasses) {
        Object[] classArray = entityClasses.toArray();
        this.classIds = new int[classArray.length];
        for (int i = 0; i < classArray.length; i++) {
            this.entityClassMap.put((Class<?>) classArray[i], i);
            this.classIds[i] = i;
        }
    }

    public void link(Class<?> sourceEntity, Class<?> targetEntity) {
        this.oldConnections.put(sourceEntity, targetEntity);
        int sourceId = this.entityClassMap.get(sourceEntity);
        int targetId = this.entityClassMap.get(targetEntity);
        for (int i = 0; i < this.classIds.length; i++) {
            if (this.classIds[i] == sourceId) {
                this.classIds[i] = targetId;
            }
        }
    }

    public boolean isConnected(Class<?> sourceEntity, Class<?> targetEntity) {
        int sourceId = this.entityClassMap.get(sourceEntity);
        int targetId = this.entityClassMap.get(targetEntity);
        return this.classIds[sourceId] == this.classIds[targetId];
    }

    public boolean isOldConnection(Class<?> sourceEntity, Class<?> targetEntity) {
        if (!this.oldConnections.containsKey(sourceEntity))
            return false;
        return this.oldConnections.get(sourceEntity).equals(targetEntity);
    }

    public void resetValidation() {
        this.oldConnections.clear();
        for (int i = 0; i < this.entityClassMap.size(); i++) {
            this.classIds[i] = i;
        }
    }
}
