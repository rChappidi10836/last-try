package com.gdp.backend.service;
import com.gdp.backend.dto.FilterDto;
import com.gdp.backend.dto.GDPIdDto;
import com.gdp.backend.enums.EStatusCode;
import com.gdp.backend.exception.ActionFailureException;
import com.gdp.backend.model.GDPIds;
import com.gdp.backend.model.Project;
import com.gdp.backend.repository.GDPRepository;
import com.gdp.backend.repository.ProjectsRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.*;
@Service
public class DoSoftDeleteService {
    @Autowired
    private ProjectsRepository projectsRepository;
    @Autowired
    private GDPRepository gdpRepository;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    @Qualifier("logger")
    Logger logger;
    /**
     * This method would be use for updating the engagement record status to closed based on GDP IDs.
     * @param Ids is an ArrayList.
     */
    @Transactional
    public ArrayList<String> performSoftDelete(ArrayList<Integer> Ids)  {
        // create dynamic query for requested tab
        ArrayList<Integer> deletedData= new ArrayList<>();
        for (Integer row : Ids) {
            deletedData.add(row);
            logger.debug(String.valueOf(row));
            projectsRepository.setProjectInActive(row);
        }
        ArrayList<String> showDeletedData=gdpRepository.GDPSearchDeletedValues(Ids);

        logger.debug("Engagement has been moved to closed state successfully");
        return showDeletedData;
    }


    /**
     * This method would be use for getting list of all the GDP IDs that are in active state.
     * @throws ActionFailureException exception would be throws, if ID is not found.
     * @return this method would return list of GDP IDs.
     */
    public GDPIdDto getGDPDetails() throws ActionFailureException {
        GDPIdDto GDPFilterValues = new GDPIdDto();
        List<GDPIds> gdpId;
        List <FilterDto> filterDtos= new ArrayList<>();
        try {
            logger.debug("GDP Ids are going to be fetched from DB");
            gdpId=gdpRepository.searchValues();
            logger.debug("GDP IDs are fetched successfully");
        } catch (Exception ex) {
            throw new ActionFailureException(EStatusCode.ERROR_ON_GET.name(), ex);
        }
        for (GDPIds row : gdpId) {
            FilterDto dto=new FilterDto();
            dto.setId(row.getId());
            dto.setName(row.getGDPId());
            filterDtos.add(dto);
            logger.debug("GDP IDs are "+filterDtos);
        }
        GDPFilterValues.setGdpIdList(filterDtos);
        return GDPFilterValues;
    }
}