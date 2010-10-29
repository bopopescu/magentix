package es.upv.dsic.gti_ia.organization;



import es.upv.dsic.gti_ia.core.BaseAgent;
import java.util.ArrayList;



/**
 * This class gives us the support to accede to the services of the OMS. The OMS
 * provides a group of services for registering or deregistering structural
 * components, specific roles, norms and units. It also offers services for
 * reporting on these components.
 * 
 * 
 * @author Joan Bellver Faus
 * 
 */
public class OMSProxy extends THOMASProxy{


	/**
	 * This class gives us the support to access to the services of the OMS
	 * 
	 *  @param agent
	 *            is a Magentix2 agent, this agent implemented the communication
	 *            protocol.
	 * @param OMSServiceDesciptionLocation
	 *            The URL where the owl's document is located.
	 */
	public OMSProxy(BaseAgent agent, String OMSServiceDesciptionLocation) {
		super(agent, "OMS",OMSServiceDesciptionLocation);
		
	}

	/**
	 * 
	 *  @param agent
	 *            is a Magentix2 Agent, this agent implemented the communication
	 *            protocol
	 *            
	 * This class gives us the support to accede to the services of the OMS,
	 * Checked that the data contained in the file configuration/Settings.xml, the URL
	 * ServiceDescriptionLocation is not empty and is the correct path.
	 */
	public OMSProxy(BaseAgent agent) {
		
		super(agent,"OMS");
		ServiceDescriptionLocation = c.getOMSServiceDesciptionLocation();
		
	}




	/**
	 * Service used for leaving a role inside a specific unit. The agent plays this role inside the unit.
	 * 
	 * @param RoleID
	 *            represent all required functionality needed in order to
	 *            achieve the unit goal.
	 * @param UnitID
	 *            organizational units (OUs), which represent groups of entities
	 *            (agents or other units)
	 * @return String Status ErrorValue
	 */
	public String leaveRole(String RoleID,
			String UnitID) {
		serviceName = "LeaveRoleProcess";
		call = ServiceDescriptionLocation + "LeaveRoleProcess.owl AgentID=" + agent.getAid().toString()
				+ " RoleID=" + RoleID + " UnitID=" + UnitID;
		return (String) this.sendInform();
	}

	/**
	 * Request roles adopted by an agent
	 * 
	 * @param AgentID
	 *            entity,this agent is protocol://name@host:port
	 *            ej.qpid://clientagent2@localhost:8080 , we can extract this
	 *            inform with the method getAid().toString().
	 * @return ArrayLis<String>t RoleUnitList [<role, unit>,<role, unit>]
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<String> informAgentRole(String AgentID){
		serviceName = "InformAgentRoleProcess";
		listResults.clear();

		call = ServiceDescriptionLocation
				+ "InformAgentRoleProcess.owl RequestedAgentID=" + AgentID;
		return (ArrayList<String>) this.sendInform();
	}

	/**
	 * Indicates entities that are members of a specific unit. Optionally, it is possible to specify a role of this unit, 
	 * so then only members playing this role are detailed. A agent can make use of this service, depending on the type of unit 
	 *  (UnitID): if FLAT, the agent is allowed, if TEAM, he is only allowed if he is a member of this unit, if HIERARCHY, then he is
	 *  only allowed if he is a supervisor of this unit.
	 * 
	 * @param RoleID
	 *            represent all required functionality needed in order to
	 *            achieve the unit goal.
	 * @param UnitID
	 *            organizational units (OUs), which represent groups of entities
	 *            (agents or other units)
	 * @return ArrayList<String> EntityRoleList
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<String> informMembers(String RoleID,
			String UnitID){

		serviceName ="InformMembersProcess";
		listResults.clear();

		call = ServiceDescriptionLocation + "InformMembersProcess.owl RoleID="
				+ RoleID + " UnitID=" + UnitID;
	return (ArrayList<String>) this.sendInform();

	}

	/**
	 * Provides all norms addressed to a specific role
	 * 
	 * @param RoleID
	 *            represent all required functionality needed in order to
	 *            achieve the unit goal.
	 * 
	 * @return ArrayList<String> NormList
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<String> informRoleNorms(String RoleID)
		 {
		serviceName ="InformRoleNormsProcess";
		listResults.clear();

		call = ServiceDescriptionLocation + "InformRoleNormsProcess.owl RoleID="
				+ RoleID;
		return (ArrayList<String>) this.sendInform();

	}

	/**
	 * Request profiles associated to a specific role
	 * 
	 * @param UnitID
	 *            organizational units (OUs), which represent groups of entities
	 *            (agents or other units)
	 * @return ArrayList<String> ProfileList
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<String> informRoleProfiles(String UnitID)
			{
		serviceName ="InformRoleProfilesProcess";
		listResults.clear();

		call = ServiceDescriptionLocation + "InformRoleProfilesProcess.owl UnitID="
				+ UnitID;
		return (ArrayList<String>) this.sendInform();

	}

	/**
	 * Provides unit description
	 * 
	 * @param UnitID
	 *            organizational units (OUs), which represent groups of entities
	 *            (agents or other units)
	 * @return ArrayList<String> UnitType UnitGoal ParentID
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<String> informUnit(String UnitID)
			{
		serviceName ="InformUnitProcess";
		listResults.clear();

		call = ServiceDescriptionLocation + "InformUnitProcess.owl UnitID=" + UnitID;
		return (ArrayList<String>) this.sendInform();


	}

	/**
	 * Used for requesting the list of roles that have been registered inside a unit. Agent can make use of this service
	 * depending on the type of unit (UnitID):if FLAT, the agent is allowed, if TEAM or HIERARCHY he is only if he is a member
	 * of this unit.
	 * 
	 * 
	 * @param UnitID
	 *            organizational units (OUs), which represent groups of entities
	 *            (agents or other units)
	 * 
	 * @return ArrayList<String> RoleList
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<String> informUnitRoles(String UnitID)
			 {
		serviceName ="InformUnitRolesProcess";
		listResults.clear();

		call = ServiceDescriptionLocation + "InformUnitRolesProcess.owl UnitID="
				+ UnitID;
		return (ArrayList<String>) this.sendInform();

	}

	/**
	 * Provides the number of current members of a specific unit. Optionally, if a role is indicated then only the quantity of 
	 * members of a specific unit.
	 * 
	 * @param RoleID
	 *            if a role is indicated then only the quantity of members playing this roles is detailed.
	 * @param UnitID
	 *            organizational units (OUs), which represent groups of entities
	 *            (agents or other units)
	 * 
	 * @return Integer Quantity
	 */
	public int quantityMembers(String RoleID, String UnitID)
		 {
		serviceName ="QuantityMembersProcess";
		call = ServiceDescriptionLocation + "QuantityMembersProcess.owl RoleID="
				+ RoleID + " UnitID=" + UnitID;
		return Integer.parseInt(this.sendInform().toString());


	}

	/**
	 * Includes a new norm within a unit
	 * 
	 * @param NormID
	 *            norm for controlling role actions
	 * @param NormContent
	 *            The syntax of the rules of incompatibility is the following:
	 *            FORBIDDEN role1 REQUEST AcquireRole MESSAGE (CONTENT (role
	 *            'role2ID')) Applications for registration of a rule is
	 *            necessary to replace the spaces between the different words
	 *            for "_".
	 * @return String Status ErrorValue
	 */
	public String registerNorm(String NormID,
			String NormContent) {
		serviceName ="RegisterNormProcess";
		call = ServiceDescriptionLocation + "RegisterNormProcess.owl NormID="
				+ NormID + " NormContent=" + NormContent;
		return (String) this.sendInform();
	}

	/**
	 * Creates a new role within a unit
	 * 
	 * @param RoleID
	 *            is the identifier of the new role
	 * @param UnitID
	 *            is the identifier of the organizational unit in which the new
	 *            role is defined
	 * @param Accessibility
	 *            considers two types of roles: (internal) internal roles, which are
	 *            assigned to internal agents of the system platform; and (external)
	 *            external roles, which can be enacted by any agent. Default is a External.
	 * @param Position
	 *            determines its structural position inside the unit, such as
	 *            member, supervisor or subordinate. Default is a Member.
	 * @param Visibility
	 *            indicates whether agents can obtain information of this role
	 *            from outside the unit in which this role is defined (public)
	 *            or from inside (private). Default is a Public.
	 * @param Inheritance
	 *            is the identifier of the parent role in the role hierarchy. Default is a Member.
	 * @return String Status ErroValue
	 */
	public String registerRole(String RoleID, String UnitID,
			String Accessibility, String Position, String Visibility,
			String Inheritance){
		serviceName ="RegisterRoleProcess";
		call = ServiceDescriptionLocation + "RegisterRoleProcess.owl RoleID="
				+ RoleID + " UnitID=" + UnitID + " Accessibility="
				+ Accessibility + " Position=" + Position + " Visibility="
				+ Visibility + " Inheritance=" + Inheritance;
		return (String) this.sendInform();


	}

	/**
	 * Creates a new unit within a specific organization
	 * @param UnitID
	 *            is the identifier of the new unit
	 * @param Type
	 *            indicates the topology of the new unit: (i) Hierarchy, in
	 *            which a supervisor agent has control over other members; (ii)
	 *            Team, which are groups of agents that share a common goal,
	 *            collaborating and cooperating between them; and (iii) Flat, in
	 *            which there is none agent with control over other members. Default is a Flat.
	 * @param Goal
	 *            describes goals pursued by the unit
	 * @param ParentUnitID
	 *            is the identifier of the parent unit which contains the new
	 *            unit. Default is a Virtual.
	 * @return String Status ErrorValue
	 */
	public String registerUnit(String UnitID, String Type,
			String Goal, String ParentUnitID) {
		serviceName ="RegisterUnitProcess";
		call = ServiceDescriptionLocation + "RegisterUnitProcess.owl  UnitID="
				+ UnitID + " Type=" + Type + " Goal=" + Goal + " ParentUnitID="
				+ ParentUnitID;
	 return 	(String) this.sendInform();


	}

	/**
	 * Removes a specific norm.
	 * 
	 * @param NormID
	 *            norm name
	 * @return String Status ErrorValue
	 */
	public String deregisterNorm(String NormID)
		 {
		serviceName ="DeregisterNormProcess";
		call = ServiceDescriptionLocation + "DeregisterNormProcess.owl  NormID="
				+ NormID;
	return 	(String) this.sendInform();


	}

	/**
	 * Removes a specific role description from a unit. There must not be any agent playing this role nor any norm 
	 * addressed to it. The agent can make use of this service, depending on the type of unit (UnitID). if FLAT, the agent
	 * is allowed; if TEAM, he is only allowed if he is a member of this unit; if HIERARCHY, then he is only allowed if he is
	 * a supervisor of this unit.
	 * @param RoleID
	 *            represent all required functionality needed in order to
	 *            achieve the unit goal.
	 * @param UnitID
	 *            organizational units (OUs), which represent groups of entities
	 *            (agents or other units)
	 * @return String Status ErrorValue
	 */
	public String deregisterRole(String RoleID, String UnitID)
		 {
		serviceName = "DeregisterRoleProcess";
		call = ServiceDescriptionLocation + "DeregisterRoleProcess.owl  RoleID="
				+ RoleID + " UnitID=" + UnitID;
	 return	(String) this.sendInform();


	}

	/**
	 * Removes a unit from an organization
	 * 
	 * @param UnitID
	 *            organizational units (OUs), which represent groups of entities
	 *            (agents or other units)
	 * @return String Status ErrorValue
	 */
	public String deregisterUnit(String UnitID)
			 {
		serviceName ="DeregisterUnitProcess";
		call = ServiceDescriptionLocation + "DeregisterUnitProcess.owl  UnitID="
				+ UnitID;
	return (String) this.sendInform();


	}

	/**
	 * Forces an agent to leave a specific role
	 * 
	 * @param ExpulseAgentID
	 *            entity,this agent is protocol://name@host:port
	 *            ej.qpid://clientagent2@localhost:8080 , we can extract this
	 *            inform with the method getAid().toString() for example.
	 * @param RoleID
	 *            represent all required functionality needed in order to
	 *            achieve the unit goal.
	 * @param UnitID
	 *            organizational units (OUs), which represent groups of entities
	 *            (agents or other units)
	 * @return String Status ErrorValue
	 */
	public String expulse(String ExpulseAgentID, String RoleID,
			String UnitID) {
		serviceName ="ExpulseProcess";
		call = ServiceDescriptionLocation + "ExpulseProcess.owl ExpulsedAgentID=" + ExpulseAgentID
				+ " RoleID=" + RoleID + " UnitID=" + UnitID;
	return (String) this.sendInform();
	

	}

	/**
	 * Requests the adoption of a specific role within a unit
	 * 
	 * @param RoleID
	 *            Role that the agent acquires inside the organization
	 * @param UnitID
	 *            Unit of which the agent was forming a part with the previous
	 *            role
	 * @return String Status ErrorValue
	 */
	public String acquireRole(String RoleID, String UnitID)
			{
		serviceName ="AcquireRoleProcess";
		call = ServiceDescriptionLocation + "AcquireRoleProcess.owl RoleID=" + RoleID
				+ " UnitID=" + UnitID;
	return (String) this.sendInform();
		

	}

}
