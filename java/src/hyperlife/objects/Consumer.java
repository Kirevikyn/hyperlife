package hyperlife.objects;

public interface Consumer {
    public Class[] getFoodTypes();
    /**
     * @param l the lifeobject it's consuming
     * @return a lifeobject if it produces one after it consumes something. May return null if it produces nothing after consuming.
     */
    public LifeObject consume(LifeObject l);
    public default boolean wantsToConsume(LifeObject l){
        for(Class cl: getFoodTypes()){
            if(l.getClass().equals(cl)){
                return true;
            }
        }
        return false;
    }
}
