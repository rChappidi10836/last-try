package com.gdp.backend.model;


import com.gdp.backend.dto.OpcoDto;

import javax.persistence.*;

@SqlResultSetMapping(name = "OpcoDto", classes = @ConstructorResult(targetClass = OpcoDto.class,
        columns = {@ColumnResult(name = "Id"),
                @ColumnResult(name = "OpcoName")}))

@Entity
public class Opco extends Base {

    public String getOpcoName() {
        return opcoName;
    }

    public void setOpcoName(String opcoName) {
        this.opcoName = opcoName;
    }

    @Column(name = "OpcoName")
    private String opcoName;

    @Column(name="OpcoEmailDomain")
    private String domainName;

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }
}
