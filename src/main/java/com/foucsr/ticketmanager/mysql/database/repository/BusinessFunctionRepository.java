package com.foucsr.ticketmanager.mysql.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.foucsr.ticketmanager.mysql.database.model.BusinessFunctions;
import com.foucsr.ticketmanager.mysql.database.model.BusinessFunctions;

@Repository
public interface BusinessFunctionRepository extends JpaRepository<BusinessFunctions, Long>
{
	@Query(value="SELECT * FROM GROUPBUSINESS_FUNCTIONS WHERE BUSINESS_ID =:businessId",nativeQuery = true)
	BusinessFunctions findBusinessFunctionsById(@Param("businessId") Long businessId);
}
