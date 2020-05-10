package dpoi.serverless.scapping.myScrapper.model;

import java.util.ArrayList;
import java.util.List;

public class SchemaKeyValues implements Schemable {
    private List<String> values = new ArrayList<>();
    private String name;

    public SchemaKeyValues(String name) {
        this.name = name;
    }

    public SchemaKeyValues addValue(String value){
        this.values.add(value);
        return this;
    }

    @Override
    public String getName() {
        return name;
    }

    public List<String> getValues() {
        return values;
    }

    @Override
    public String toString() {
        final int valuesLength = values.size();
//        if (valuesLength == 0){
//            return getName() + ", \n";
        if(valuesLength == 1){
            return getName() + " : " + values.get(0) + ",\n";
        }else if(valuesLength > 1){
            StringBuilder result = new StringBuilder(getName() + " : [");
            for (String value : values) {
                result.append("\n \t").append(value);
            }
            result.append("\n]\n");
            return result.toString();
        }
        return "";
    }

}
