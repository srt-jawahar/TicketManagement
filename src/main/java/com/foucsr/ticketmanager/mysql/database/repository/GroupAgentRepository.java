package com.foucsr.ticketmanager.mysql.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.foucsr.ticketmanager.mysql.database.model.GroupAgents;

@Repository
public interface GroupAgentRepository extends JpaRepository<GroupAgents, Long>
{
	@Query(value="SELECT * FROM GROUP_AGENTS WHERE GROUPAGENT_ID=:groupAgentId",nativeQuery = true)
	GroupAgents findGroupAgentsById(@Param("groupAgentId") Long groupAgentId);
	
	@Query(value="SELECT * FROM GROUP_AGENTS WHERE GROUPAGENT_NAME=:groupAgentName",nativeQuery = true)
	GroupAgents findGroupAgentsByName(@Param("groupAgentName") String groupAgentName);
}
