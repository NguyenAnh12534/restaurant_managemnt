package com.ha.app.data;

import java.util.HashMap;
import java.util.Set;

/**
 * This class using simple Find-Union data structure and Quick Find algorithm to check if two entities classes are already linked
 * Using this algorithm, the circular loading data issue could be solved
 * In summary, when entity A load data of entity B, the isConnected has to be called first to validate if they are linked before.
 * If they are linked, the connection has a high chance of creating a circular relationship which causes a StackOverflow error.
 */
public class RelationshipManager {

    HashMap<Class<?>, Integer> entityClassMap = new HashMap<>();
    HashMap<Class<?>, Class<?>> oldConnections = new HashMap<>();
    int[] classIds;

    public RelationshipManager(Set<Class<?>> entityClasses) {
        initializeEntityMap(entityClasses);
    }

    /**
     * This method initialize base data structure for the Union-Find algorithm
     * Each entity is an element and attached with an ID
     * Then an array is created to contain parent of each key, by default each key is parent of itself
     * @param entityClasses all Entity classes
     */
    private void initializeEntityMap(Set<Class<?>> entityClasses) {
        Object[] classArray = entityClasses.toArray();
        this.classIds = new int[classArray.length];
        for (int i = 0; i < classArray.length; i++) {
            this.entityClassMap.put((Class<?>) classArray[i], i);
            this.classIds[i] = i;
        }
    }

    /**
     * This method links two entities
     * ID of targetEntity is set as parent of sourceEntity
     * @param sourceEntity The class of the source Entity
     * @param targetEntity The class of the target Entity
     */
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

    /**
     * This method check if two entity is already linked
     * @param sourceEntity The class of the source Entity
     * @param targetEntity The class of the target Entity
     * @return true - if two entities is linked before, otherwise return false
     */
    public boolean isConnected(Class<?> sourceEntity, Class<?> targetEntity) {
        int sourceId = this.entityClassMap.get(sourceEntity);
        int targetId = this.entityClassMap.get(targetEntity);
        return this.classIds[sourceId] == this.classIds[targetId];
    }

    /**
     * This method check if a reference from an Entity to another is already made before
     * @param sourceEntity The class of the source Entity
     * @param targetEntity The class of the target Entity
     * @return true - if two entities is a old reference, false if not
     */
    public boolean isOldConnection(Class<?> sourceEntity, Class<?> targetEntity) {
        if (!this.oldConnections.containsKey(sourceEntity))
            return false;
        return this.oldConnections.get(sourceEntity).equals(targetEntity);
    }

    /**
     * This method reset all parent-child relations of all entities
     */
    public void resetValidation() {
        this.oldConnections.clear();
        for (int i = 0; i < this.entityClassMap.size(); i++) {
            this.classIds[i] = i;
        }
    }
}
