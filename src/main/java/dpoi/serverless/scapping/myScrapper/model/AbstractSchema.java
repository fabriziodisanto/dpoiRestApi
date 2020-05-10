package dpoi.serverless.scapping.myScrapper.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public abstract class AbstractSchema implements Schemable, Schema {
    @JsonProperty
    private String id;
    private String name;
    private AbstractSchema propertiesExtended;
    private List<SchemaKeyValues> keyValues;

    public AbstractSchema(String name, AbstractSchema propertiesExtended, List<SchemaKeyValues> keyValues) {
        this.name = name;
        this.propertiesExtended = propertiesExtended;
        this.keyValues = keyValues;
        keyValues.add(new SchemaKeyValues("@type").addValue(name));
        keyValues.add(new SchemaKeyValues("@context").addValue("http://schema.org"));
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getType() {
        return getName();
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof AbstractSchema))return false;
        return name.equals(((Schemable) obj).getName());
    }

    public void addPropertyValue(String key, String value){
        for (SchemaKeyValues keyValue : keyValues) {
            if (keyValue.getName().equals(key)){
                keyValue.addValue(value);
                return;
            }
        }
        if (propertiesExtended != null) {
            propertiesExtended.addPropertyValue(key, value);
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        StringBuilder firstLine =  new StringBuilder(name + " : { \n");
        String resultTabbed = "";
        StringBuilder result = new StringBuilder();
        for (SchemaKeyValues keyValues : keyValues) {
           result.append(keyValues.toString());
        }
        if (propertiesExtended != null){
            result.append(propertiesExtended.toString());
        }
        resultTabbed = String.valueOf(result).replaceAll("(?m)^", "\t");
        resultTabbed += "}";
        return String.valueOf(firstLine.append(resultTabbed));
    }

    public void setPropertiesExtended(AbstractSchema propertiesExtended) {
        this.propertiesExtended = propertiesExtended;
    }

    public AbstractSchema getPropertiesExtended() {
        return propertiesExtended;
    }

    public List<SchemaKeyValues> getKeyValues() {
        return keyValues;
    }
}
