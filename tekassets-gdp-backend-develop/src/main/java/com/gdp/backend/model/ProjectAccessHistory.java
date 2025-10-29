package com.gdp.backend.model;

import com.gdp.backend.dto.ReportLogDto;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.SqlResultSetMapping;

@Entity
@SqlResultSetMapping(name = "ReportLogMapping",
        classes = @ConstructorResult(
                targetClass = ReportLogDto.class,
                columns = {@ColumnResult(name = "ModifiedAt"),
                        @ColumnResult(name = "ModifiedBy")}
        )
)

public class ProjectAccessHistory extends AccessHistory {

}
