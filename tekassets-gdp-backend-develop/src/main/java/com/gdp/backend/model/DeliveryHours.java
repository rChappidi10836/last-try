package com.gdp.backend.model;

import com.gdp.backend.dto.DeliveryHoursDto;

import javax.persistence.*;
import java.util.Date;

@Entity
@SqlResultSetMapping(name = "DeliveryHoursMapping",
        classes = @ConstructorResult(
                targetClass = DeliveryHoursDto.class,
                columns = {@ColumnResult(name = "Id"),
                        @ColumnResult(name = "FirstName"),
                        @ColumnResult(name = "MiddleName"),
                        @ColumnResult(name = "LastName"),
                        @ColumnResult(name = "Hours"),
                        @ColumnResult(name = "WeekEndingDate"),
                        @ColumnResult(name = "ResourceId"),
                        @ColumnResult(name = "ResourceRole")
                }
        ))
public class DeliveryHours extends Base {

    @Column(name = "Hours")
    private Integer hours;

    @Column(name = "ProjectId")
    private Integer projectId;

    @Column(name = "ResourceId")
    private Integer resourceId;

    @Column(name = "WeekEndingDate")
    private Date weekEndingDate;

    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getResourceId() {
        return resourceId;
    }

    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }

    public Date getWeekEndingDate() {
        return weekEndingDate;
    }

    public void setWeekEndingDate(Date weekEndingDate) {
        this.weekEndingDate = weekEndingDate;
    }
}
