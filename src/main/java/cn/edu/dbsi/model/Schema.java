package cn.edu.dbsi.model;

import java.util.List;

public class Schema {
    private Integer id;

    private String name;

    private String tableNames;

    private Integer cubeId;

    private String defaultMeasureName;

    private String address;

    private String isdelete;

    private List<SchemaMeasureGroup> schemaMeasureGroups;

    private List<SchemaDimension> schemaDimensions;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTableNames() {
        return tableNames;
    }

    public void setTableNames(String tableNames) {
        this.tableNames = tableNames;
    }

    public Integer getCubeId() {
        return cubeId;
    }

    public void setCubeId(Integer cubeId) {
        this.cubeId = cubeId;
    }

    public String getDefaultMeasureName() {
        return defaultMeasureName;
    }

    public void setDefaultMeasureName(String defaultMeasureName) {
        this.defaultMeasureName = defaultMeasureName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(String isdelete) {
        this.isdelete = isdelete;
    }

    public List<SchemaMeasureGroup> getSchemaMeasureGroups() {
        return schemaMeasureGroups;
    }

    public void setSchemaMeasureGroups(List<SchemaMeasureGroup> schemaMeasureGroups) {
        this.schemaMeasureGroups = schemaMeasureGroups;
    }

    public List<SchemaDimension> getSchemaDimensions() {
        return schemaDimensions;
    }

    public void setSchemaDimensions(List<SchemaDimension> schemaDimensions) {
        this.schemaDimensions = schemaDimensions;
    }
}