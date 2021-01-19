package com.xzy.mvc.dto;

import lombok.Getter;
import lombok.ToString;

import java.util.Date;

/**
 * @author xzy
 * @date 2021-01-04 14:46
 * 说明：
 */
@Getter
@ToString
public class QueryConditionsDTO {
    private String strPar;
    private Integer intPar;
    private Date datePar;
    private Integer pageNumber;
    private Integer pageSize;

    public QueryConditionsDTO() {
        System.out.println("NoArgumentConstructor");
        this.strPar = "str";
        this.intPar = 0;
        this.datePar = new Date();
        this.pageNumber = 0;
        this.pageSize = 10;
    }

    public QueryConditionsDTO(String strPar, Integer intPar, Date datePar, Integer pageNumber, Integer pageSize) {
        System.out.println("AllArgumentConstructor");
        this.strPar = strPar;
        this.intPar = intPar;
        this.datePar = datePar;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    public void setStrPar(String strPar) {
        System.out.println("setStrPar(String strPar)");
        this.strPar = strPar;
    }

    public void setIntPar(Integer intPar) {
        System.out.println("setIntPar(Integer intPar)");
        this.intPar = intPar;
    }

    public void setDatePar(Date datePar) {
        System.out.println("setDatePar(Date datePar)");
        this.datePar = datePar;
    }

    public void setPageNumber(Integer pageNumber) {
        System.out.println("setPageNumber(Integer pageNumber)");
        this.pageNumber = (pageNumber != null && pageNumber >= 0) ? pageNumber : 0;
    }

    public void setPageSize(Integer pageSize) {
        System.out.println("setPageSize(Integer pageSize)");
        this.pageSize = (pageSize != null && pageSize > 0) ? pageSize : 10;
    }
}
