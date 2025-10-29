package com.gdp.backend.service;

import com.gdp.backend.common.Queries;
import com.gdp.backend.dto.OpportunityDto;
import com.gdp.backend.dto.OpportunityResponseDto;
import com.gdp.backend.enums.EStatusCode;
import com.gdp.backend.exception.ActionFailureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * This Service class used for getting the list of Opportunity details.
 * @author gdp
 *
 */
@Service
public class OpportunityService {

    @Autowired
    EntityManager entityManager;

    /**
     * This Service class used for getting the list of Opportunity details.
     * @param pageNumber this is the number of current page.
     * @param pageSize this is the number of items which would be populate.
     * @return this method would be return opportunityResponseDto.
     * @throws ActionFailureException exception would be throws if Opportunity not found.
     */
    public OpportunityResponseDto getAllOpportunityValues(int pageNumber, int pageSize, String opcoId ) throws ActionFailureException {
        OpportunityResponseDto opportunityResponseDto = new OpportunityResponseDto();
        StringBuilder builder = new StringBuilder(Queries.OPPORTUNITY_QUERY);
        String countQueryString = "SELECT COUNT(*) "+builder.substring(builder.indexOf("FROM"), builder.indexOf("ORDER"));
        Query query = entityManager.createNativeQuery(Queries.OPPORTUNITY_QUERY).setParameter(1,opcoId);
        Query countQuery = entityManager.createNativeQuery(countQueryString).setParameter(1,opcoId);
        if (!(pageNumber == 0 && pageSize == 0)) {
            opportunityResponseDto.setTotalNumberOfElements( countQuery.getResultStream().count());
            query.setFirstResult((pageNumber) * pageSize);
            query.setMaxResults(pageSize);
        }
        List<Object[]> opportunityList;
        try {
            opportunityList = query.getResultList();
        } catch (Exception ex) {
            throw new ActionFailureException(EStatusCode.ERROR_ON_GET.name(), ex);
        }
        List<OpportunityDto> opportunityDtoList = new ArrayList<>(opportunityList.size());
        for (Object[] row : opportunityList) {
            opportunityDtoList.add(new OpportunityDto(row));
        }
        opportunityResponseDto.setOpportunityDtoList(opportunityDtoList);
        return opportunityResponseDto;
    }

}
